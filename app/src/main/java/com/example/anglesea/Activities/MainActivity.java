package com.example.anglesea.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

public class MainActivity extends AppCompatActivity
{

    //Creating variables for the widgets
    private TextView RNNumber;
    private TextView Password;
    private TextView Login;

    protected Helper mHelper;
    protected Context mContext;
    protected DB mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SystemClock.sleep(1000);
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        // Create instances of our class properties
        mHelper = new Helper(this);
        mContext = this;
        mDatabase = DB.get(this);

        //Setting the above variables with id's availabe in the xml

        RNNumber = (TextView)findViewById(R.id.txt_RNnumber);
        Password = (TextView)findViewById(R.id.txt_Password);
        Login = (Button)findViewById(R.id.btn_Login);

        //sets the button with a listener so that it can perform when its clicked
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext, AuditActivity.class);
                //startActivity(intent);
                validate(RNNumber.getText().toString(), Password.getText().toString());
            }
        });
    }

    //Checking if the username and password are right
    private  void validate(String userName, String userPassword){//replace with database check

        if(userName.equals(""))
        {
            Toast.makeText(this, "Please enter your RN number", Toast.LENGTH_LONG).show();
            return;
        }

        Nurse nurse = mDatabase.nurse().getByRN(userName);

        if(nurse == null)
        {
            // If it gets here, there was no nurse matching the RN the user entered
            // Show a message to the user to let them know the RN is wrong
            Toast.makeText(this, "Incorrect login details", Toast.LENGTH_LONG).show();

            // Stop validating because there is no nurse to validate
            return;
        }

        // We must use .equals() instead of == when comparing strings in Java
        // This is because == will compare the memory addresses of the two strings being compared, so even if the value is equal, it will return false
        // Where .equals() will compare the string value and will return true
        if(userPassword.equals(nurse.getPassword()))
        {
            // Password matched so go to next activity
            Intent intent  = new Intent(this, HomeActivity.class);
            intent.putExtra("rn", nurse.getRN());
            startActivity(intent);
            finish();
        }
        else if(userPassword.equals(""))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Password didn't match so tell the user it was wrong
            Toast.makeText(this, "Incorrect login details", Toast.LENGTH_LONG).show();
        }
    }

    public void startRegistration(View v)
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

}
