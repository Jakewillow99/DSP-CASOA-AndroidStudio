package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Connection;
import java.util.ArrayList;

import android.widget.Toast;

public class zManageStaffAccounts extends AppCompatActivity {

    DatabaseHelper db;
    Connection connect;
    String connectionResult = "";
    Button mButtonDelete;
    Button mButtonEdit;
    Button mButtonAdd;
    public String selected = "NOTHING";
    ArrayList<String> staffNames;

    ArrayAdapter adapter;

    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_manage_staff_accounts);
        mButtonDelete = (Button) findViewById(R.id.manage_delete);
        mButtonAdd = (Button) findViewById(R.id.manage_createAccount);
        mButtonEdit = (Button) findViewById(R.id.manage_edit);
        userList = (ListView) findViewById(R.id.user_list);

        db = new DatabaseHelper(this);

        staffNames = new ArrayList<>();

        viewData();

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.deleteAccount(selected);
                if (result == true){
                    staffNames.clear();
                    viewData();
                }
                else {
                    Toast.makeText(zManageStaffAccounts.this, "Failed to Delete,", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAdd();
            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToEdit();
            }
        });


        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = userList.getItemAtPosition(position).toString();
                int count = userList.getChildCount();
                for (int i = 0; i < count; i++){
                    if (position == i) {
                        userList.getChildAt(i).setBackgroundColor(Color.BLUE);
                    } else {
                        userList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);

                    }
                }
                Toast.makeText(zManageStaffAccounts.this, ""+selected, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData(){
        Cursor c = db.viewData();
        if (c.getCount() == 0){

            Toast.makeText(this, "No Accounts Found", Toast.LENGTH_SHORT).show();

        } else {
            while (c.moveToNext()){
                staffNames.add(c.getString(4));

            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, staffNames);

            userList.setAdapter(adapter);

        }

    }

    public void moveToAdd()
    {
        Intent moveToAdd = new Intent(getApplicationContext(),zAddNewStaff.class);
        startActivity(moveToAdd);
    }

    public void moveToEdit() {
        if (selected == "NOTHING") {
            Toast.makeText(this, "No Account Selected.", Toast.LENGTH_SHORT).show();

        } else {
            Intent moveToEdit = new Intent(getApplicationContext(),zEditAccountFix.class);
            moveToEdit.putExtra("name", selected);
            startActivity(moveToEdit);
        }
    }

    public String getCurrentUser(){
        return selected;
    }


    }





