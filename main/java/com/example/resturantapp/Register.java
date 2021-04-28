package com.example.resturantapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextRePassword;
    EditText mTextPhoneNo;
    EditText mTextEmail;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);

        mTextUsername = (EditText)findViewById(R.id.register_name);
        mTextPassword = (EditText)findViewById(R.id.register_password);
        mTextRePassword = (EditText)findViewById(R.id.register_repassword);
        mTextPhoneNo = (EditText)findViewById(R.id.register_phoneno);
        mTextEmail = (EditText)findViewById(R.id.register_email);
        mButtonRegister = (Button)findViewById(R.id.register_button);
        mTextViewLogin = (TextView)findViewById(R.id.register_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Register.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pword = mTextPassword.getText().toString().trim();
                String repword = mTextRePassword.getText().toString().trim();
                String phoneno = mTextPhoneNo.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();

                Boolean res = db.checkUsername(user);
                if (res == true){


                    if(pword.equals(repword)){
                        long val = db.addCustomer(user,pword,phoneno,email);
                        if(val > 0) {
                            Toast.makeText(Register.this, "Successful Register", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(Register.this, CustomerDashboard.class);
                            startActivity(moveToLogin);
                        } else {
                            Toast.makeText(Register.this, "Failed Register", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(Register.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}