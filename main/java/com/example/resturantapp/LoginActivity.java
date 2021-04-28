package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Set Variables Used.
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        //find items from layout and save them as varibales
        mTextUsername = (EditText)findViewById(R.id.login_username);
        mTextPassword = (EditText)findViewById(R.id.login_password);
        mButtonLogin = (Button)findViewById(R.id.login_button);
        mTextViewLogin = (TextView)findViewById(R.id.login_register);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on click takes user to login page
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //checks if username and passwords are on database
                String user = mTextUsername.getText().toString().trim();
                String pword = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pword);

                if (res == true){
                    Intent moveToAdmin = new Intent(getApplicationContext(),AdminDashboard.class);
                    startActivity(moveToAdmin);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                }

            }

        });
        
    }
}