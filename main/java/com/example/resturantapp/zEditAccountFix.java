package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class zEditAccountFix extends AppCompatActivity {
    DatabaseHelper db;
    zManageStaffAccounts manage;

    EditText mEditUser;
    EditText mEditPass;
    EditText mEditName;
    Button mUpdate;
    public String role;
    public String user;
    public int id;
    String ogUName;
    public boolean res = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_edit_account_fix);
        Intent i = getIntent();
        user = i.getStringExtra("name");
        db = new DatabaseHelper(this);
        id = db.getIDFromName(user);

        Spinner spinner = (Spinner) findViewById(R.id.edit_role);

        mEditUser = (EditText)findViewById(R.id.edit_username);
        mEditPass = (EditText)findViewById(R.id.edit_password);
        mEditName = (EditText)findViewById(R.id.edit_name);
        mUpdate = (Button)findViewById(R.id.edit_button);

        mEditUser.setText(db.getUNAMEFromID(id));
        ogUName = db.getUNAMEFromID(id);
        mEditPass.setText(db.getPWORDFromID(id));
        mEditName.setText(user);

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mEditUser.getText().toString().trim();
                String pword = mEditPass.getText().toString().trim();
                String name = mEditName.getText().toString().trim();

                int spinner_pos = spinner.getSelectedItemPosition();
                String[] values = getResources().getStringArray(R.array.role_selection);
                role = String.valueOf(values[spinner_pos]);

                if (user.equals(ogUName)) {
                    updateAccount(id, user, pword, name, role);
                } else {
                    res = db.checkUsername(user);
                    if (res == true) {
                        updateAccount(id, user, pword, name, role);
                    } else {
                        Toast.makeText(zEditAccountFix.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }

    public void updateAccount(int id, String user, String pword, String name, String role){
        boolean val = db.updateAccount(id, user, pword, name, role);
        if (val == true) {
            Toast.makeText(zEditAccountFix.this, "Successful Update", Toast.LENGTH_SHORT).show();
            Intent moveToAdmin = new Intent(zEditAccountFix.this, AdminDashboard.class);
            startActivity(moveToAdmin);
        } else {
            Toast.makeText(zEditAccountFix.this, "Failed Update", Toast.LENGTH_SHORT).show();
        }
    }
}