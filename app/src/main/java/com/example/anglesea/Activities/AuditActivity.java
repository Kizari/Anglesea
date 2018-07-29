package com.example.anglesea.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Dialogs.SendPDFDialog;
import com.example.anglesea.Entities.Audit;
import com.example.anglesea.Entities.AuditAdapter;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AuditActivity extends BaseActivity
{
    LinearLayoutManager mManager;
    RecyclerView mRecycler;
    TextView textEmpty;
    EditText editChartNumber;
    Button buttonSearch;

    public long mChartId;

    @Override
    protected void onResume()
    {
        super.onResume();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ArrayList<String> files = Helper.getPDFDeleteList(mContext);
                for(String path : files)
                {
                    File file = new File(path);
                    if (file.exists())
                        file.delete();
                }
                Helper.clearPDFDeleteList(mContext);
            }
        }, 1000 * 30);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.audit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.item_audit:
                onSendClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onSendClicked()
    {
        SendPDFDialog.Create(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        textEmpty = findViewById(R.id.textEmpty);
        editChartNumber = findViewById(R.id.editChartNumber);

        mRecycler = findViewById(R.id.recycler);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);

        if(getIntent().getExtras() != null)
        {
            LinearLayout searchBar = findViewById(R.id.layoutSearchBar);
            searchBar.setVisibility(View.GONE);

            mChartId = getIntent().getExtras().getLong("chartId");
            setList();
        }
        else
        {
            mRecycler.setVisibility(View.GONE);
        }

        // Set up colours
        mColorPrimary = new BaseColor(37, 73, 120);
        mColorAccent = new BaseColor(239, 65, 53);
        mColorText = new BaseColor(102, 102, 102);

        // Set up fonts from assets
        try
        {
            mFont = BaseFont.createFont("font/lato.xml", "UTF-8", BaseFont.EMBEDDED);
        }
        catch (Exception ex)
        {
        }

        // Create a line separator
        mSeparator = new LineSeparator();
        mSeparator.setLineColor(mColorText);
    }

    private void setList()
    {
        List<Administration> administrations = mDatabase.administration().getByChart(mChartId);

        if(administrations == null || administrations.size() < 1)
        {
            textEmpty.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
            return;
        }

        textEmpty.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);

        ArrayList<Audit> audits = new ArrayList<>();
        for (Administration a : administrations) {
            audits.add(new Audit(a, this));
        }

        AuditAdapter adapter = new AuditAdapter(this);
        adapter.setHeader("Audit #" + (10000 + mChartId));
        adapter.setItems(audits);
        mRecycler.setAdapter(adapter);
    }

    public void onSearch(View v)
    {
        try
        {
            long chartId = Long.parseLong(editChartNumber.getText().toString());
            mChartId = chartId - 10000;
        }
        catch(Exception ex)
        {
            Helper.toast(this, "Audit number must be numeric");
            return;
        }

        setList();
    }

    BaseColor mColorPrimary;
    BaseColor mColorAccent;
    BaseColor mColorText;
    BaseFont mFont;
    LineSeparator mSeparator;

    public Uri saveToPdf() throws BadElementException, DocumentException, SVGParseException, IOException
    {
        List<Administration> administrations = mDatabase.administration().getByChart(mChartId);

        String dest = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Anglesea-Audit" + (mChartId + 10000) + ".pdf";
        File file = new File(dest);

        // Delete the pdf if it already exists
        if(file.exists())
            file.delete();

        // Create and open the PDF
        Document document = new Document();
        file.createNewFile();
        PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        document.open();

        // Set up the document
        document.setPageSize(PageSize.A4);
        document.addCreationDate();
        document.addAuthor("Anglesea Dosage Calculator");
        document.addCreator("Anglesea Hospital");

        // Create the heading
        Font headingFont = new Font(mFont, 20.0f, Font.NORMAL, mColorPrimary);
        Chunk heading = new Chunk("Visit Summary", headingFont);
        Paragraph headingParagraph = new Paragraph(heading);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);

        PdfPCell cell = new PdfPCell(headingParagraph);
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(4);
        table.addCell(cell);

        newLabelTextCell(table, createLabel("Audit No."));
        newLabelTextCell(table, createLabel("Patient NHI"));
        newLabelTextCell(table, createLabel("Patient Name"));

        DateFormat genDf = new SimpleDateFormat("dd MMM yyyy");
        cell = new PdfPCell(createRegularText("Generated " + genDf.format(System.currentTimeMillis())));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(4);
        table.addCell(cell);

        String nhi = mDatabase.chart().getById(mChartId).getNHI();
        newTextCell(table, createStandardHeading("#" + (mChartId + 10000)));
        newTextCell(table, createStandardHeading(nhi));
        newTextCell(table, createStandardHeading(mDatabase.patient().getByNHI(nhi).getFullName()));

        document.add(table);
        createSeparator(document);

        // Timestamp, room, drug (name, strength, dosage), nurse (name, RN), signature
        PdfPTable admins = new PdfPTable(4);
        admins.setWidthPercentage(100.0f);
        for(Administration a : administrations)
        {
            DateFormat tf = new SimpleDateFormat("h:mma");
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            newTextCell(admins, createLargeText(tf.format(a.getTimestamp())));

            Drug drug = mDatabase.drug().getById(a.getDrugId());
            newTextCell(admins, createLargeText(drug.getName()));

            Room room = mDatabase.room().getById(a.getRoomId());
            newTextCell(admins, createLargeText("Room " + room.getRoomName()));

            SVG svg = SVG.getFromString(a.getSignature());
            Image image = null;
            if(svg.getDocumentWidth() != -1)
            {
                Bitmap bm = Bitmap.createBitmap((int)Math.ceil(svg.getDocumentWidth()),
                        (int)Math.ceil(svg.getDocumentHeight()),
                        Bitmap.Config.ARGB_8888);

                Canvas bmc = new Canvas(bm);
                bmc.drawRGB(255, 255, 255);
                svg.renderToCanvas(bmc);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                image = Image.getInstance(byteArray);
                image.scaleAbsolute(150, 70);
            }

            PdfPCell sigCell;

            if(image != null)
                sigCell = new PdfPCell(image);
            else
                sigCell = new PdfPCell(new Phrase(" "));

            sigCell.setRowspan(2);
            sigCell.setBorder(Rectangle.NO_BORDER);
            sigCell.setPaddingBottom(4);
            admins.addCell(sigCell);

            newTextCell(admins, createRegularText(df.format(a.getTimestamp())));
            newTextCell(admins, createRegularText(drug.getMg() + "mg / " + drug.getMl() + "ml"));

            Nurse nurse = mDatabase.nurse().getByRN(a.getRN());
            newTextCell(admins, createRegularText(nurse.getFullName() + " (" + nurse.getRN() + ")"));
        }
        document.add(admins);

        document.close();

        // Save this to the list of files that need to be deleted
        Helper.appendPDFDeleteList(this, dest);

        return FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".com.example.anglesea.provider", file);
    }

    private void createSeparator(Document document) throws DocumentException
    {
        document.add(new Paragraph(" "));
        document.add(new Chunk(mSeparator));
        document.add(new Paragraph(" "));
    }

    private void newTextCell(PdfPTable table, Paragraph paragraph) throws BadElementException
    {
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(4);
        table.addCell(cell);
    }

    private void newLabelTextCell(PdfPTable table, Paragraph paragraph) throws BadElementException
    {
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(4);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(cell);
    }

    private Paragraph createLabel(String text) throws BadElementException
    {
        Font font = new Font(mFont, 10.0f, Font.NORMAL, mColorAccent);
        Chunk chunk = new Chunk(text, font);
        return new Paragraph(chunk);
    }

    private Paragraph createStandardHeading(String text) throws BadElementException
    {
        Font font = new Font(mFont, 16.0f, Font.NORMAL, mColorText);
        Chunk chunk = new Chunk(text, font);
        return new Paragraph(chunk);
    }

    private Paragraph createRegularText(String text) throws BadElementException
    {
        Font font = new Font(mFont, 10.0f, Font.NORMAL, mColorText);
        Chunk chunk = new Chunk(text, font);
        return new Paragraph(chunk);
    }

    private Paragraph createLargeText(String text) throws BadElementException
    {
        Font font = new Font(mFont, 14.0f, Font.NORMAL, mColorText);
        Chunk chunk = new Chunk(text, font);
        return new Paragraph(chunk);
    }

    private String getAppPath(Context context)
    {
        File dir = new File(android.os.Environment.getDataDirectory()
                + File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);

        if (!dir.exists())
        {
            dir.mkdir();
        }

        return dir.getPath() + File.separator;
    }
}