package com.example.anglesea.Entities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.anglesea.Activities.AuditActivity;
import com.example.anglesea.Activities.MainActivity;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.R;

public class BaseActivity extends AppCompatActivity
{
    protected Helper mHelper;
    protected Context mContext;
    protected DB mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create the title for this activity using the Anglesea font
        SpannableString s = new SpannableString(getTitle());
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the above font
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        // We exclude the back button from the activities in the if statement
        if(!getClass().getSimpleName().equals("MainActivity") && !getClass().getSimpleName().equals("MainActivity") && !getClass().getSimpleName().equals("HomeActivity"))
        {
            // Add the back button to the action bar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Create instances of our class properties
        mHelper = new Helper(this);
        mContext = this;
        mDatabase = DB.get(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if(!getClass().getSimpleName().equals("MainActivity") && !getClass().getSimpleName().equals("RegistrationActivity") && !getClass().getSimpleName().equals("AuditActivity"))
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.item_audit:
                Intent intent = new Intent(this, AuditActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_logout:
                Intent loginIntent = new Intent(this, MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        // Call the back option when the back button is pressed
        onBackPressed();
        return true;
    }
}