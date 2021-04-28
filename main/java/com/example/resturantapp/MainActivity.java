package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //yeah this page isnt needed

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(this);

        Context context = getApplicationContext();


            Intent appIntent = new Intent(MainActivity.this, Register.class);
            startActivity(appIntent);


    }
}