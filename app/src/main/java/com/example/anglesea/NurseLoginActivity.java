package com.example.anglesea;

import android.arch.persistence.room.Query;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anglesea.Activities.MainActivity;
import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.Entities.BaseActivity;

import java.util.List;

public class NurseLoginActivity extends BaseActivity {

    //Creating variables for the widgets
    private TextView RNNumber;
    private TextView Password;
    private TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_login);

        // Create the test nurse account if it doesn't exist
        if(mDatabase.nurse().getByRN("ABC123") == null)
        {
            Nurse nurse = new Nurse("ABC123", "Aamina", "Ahmed", "password");
            mDatabase.nurse().insert(nurse);
        }

        //Setting the above variables with id's availabe in the xml

        RNNumber = (TextView)findViewById(R.id.txt_RNnumber);
        Password = (TextView)findViewById(R.id.txt_Password);
        Login = (Button)findViewById(R.id.btn_Login);

        //sets the button with a listener so that it can perform when its clicked
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(RNNumber.getText().toString(), Password.getText().toString());
            }
        });
    }

    //Checking if the username and password are right
    private  void validate(String userName, String userPassword){//replace with database check

        if(userName.equals(""))
        {
            Toast.makeText(this, "Please enter your RN", Toast.LENGTH_LONG).show();
            return;
        }

        Nurse nurse = mDatabase.nurse().getByRN(userName);

        if(nurse == null)
        {
            // If it gets here, there was no nurse matching the RN the user entered
            // Show a message to the user to let them know the RN is wrong
            Toast.makeText(this, "No nurse found for this RN.", Toast.LENGTH_LONG).show();

            // Stop validating because there is no nurse to validate
            return;
        }

        // We must use .equals() instead of == when comparing strings in Java
        // This is because == will compare the memory addresses of the two strings being compared, so even if the value is equal, it will return false
        // Where .equals() will compare the string value and will return true
        if(userPassword.equals(nurse.getPassword()))
        {
            // Password matched so go to next activity
            Toast.makeText(this, "Password is correct!", Toast.LENGTH_LONG).show();
            //Intent intent  = new Intent(MainActivity.this, SecondActivity.class);
            //startActivity(intent);
        }
        else if(userPassword.equals(""))
        {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
        }
        else
        {
            // Password didn't match so tell the user it was wrong
            Toast.makeText(this, "Incorrect password.", Toast.LENGTH_LONG).show();
        }
    }

}
