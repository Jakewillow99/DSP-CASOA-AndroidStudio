package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class zAddNewStaff extends AppCompatActivity {

    EditText mAddUser;
    EditText mAddPass;
    EditText mAddName;
    Button mCreate;

    public String role;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_add_new_staff);
        Spinner spinner = (Spinner) findViewById(R.id.add_role);


        db = new DatabaseHelper(this);
        mAddUser = (EditText)findViewById(R.id.add_username);
        mAddPass = (EditText)findViewById(R.id.add_password);
        mAddName = (EditText)findViewById(R.id.add_name);
        mCreate = (Button)findViewById(R.id.add_button);



        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mAddUser.getText().toString().trim();
                String pword = mAddPass.getText().toString().trim();
                String name = mAddName.getText().toString().trim();

                int spinner_pos = spinner.getSelectedItemPosition();
                String[] values = getResources().getStringArray(R.array.role_selection);
                role = String.valueOf(values[spinner_pos]);

                Boolean res = db.checkUsername(user);
                if (res == true) {
                    long val = db.addStaff(user, pword, name, role);
                    if (val > 0) {
                        Toast.makeText(zAddNewStaff.this, "Successful Addition", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(zAddNewStaff.this, AdminDashboard.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(zAddNewStaff.this, "Failed Addition", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(zAddNewStaff.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}