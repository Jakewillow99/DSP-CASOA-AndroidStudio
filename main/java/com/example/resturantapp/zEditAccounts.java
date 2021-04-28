package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class zEditAccounts extends AppCompatActivity {

    DatabaseHelper db;
    zManageStaffAccounts manage;

    EditText mEditUser;
    EditText mEditPass;
    EditText mEditName;
    Button mUpdate;
    String user = manage.selected;
    public String role;

    int id = db.getIDFromName(user);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_edit_accounts);
        Spinner spinner = (Spinner) findViewById(R.id.edit_role);

        db = new DatabaseHelper(this);
        mEditUser = (EditText)findViewById(R.id.edit_username);
        mEditPass = (EditText)findViewById(R.id.edit_password);
        mEditName = (EditText)findViewById(R.id.edit_name);
        mUpdate = (Button)findViewById(R.id.edit_button);

        mEditUser.setText(db.getUNAMEFromID(id));
        mEditPass.setText(db.getPWORDFromID(id));
        mEditName.setText(user);

        int spinner_pos = spinner.getSelectedItemPosition();
        String[] values = getResources().getStringArray(R.array.role_selection);
        role = String.valueOf(values[spinner_pos]);

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mEditUser.getText().toString().trim();
                String pword = mEditPass.getText().toString().trim();
                String name = mEditName.getText().toString().trim();

                Boolean res = db.checkUsername(user);
                if (res == true) {
                    boolean val = db.updateAccount(id, user, pword, name, role);
                    if (val == true) {
                        Toast.makeText(zEditAccounts.this, "Successful Update", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(zEditAccounts.this, AdminDashboard.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(zEditAccounts.this, "Failed Update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(zEditAccounts.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}