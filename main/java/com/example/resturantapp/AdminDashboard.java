package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDashboard extends AppCompatActivity {

    //Set Variables Used.
    DatabaseHelper db;
    Connection connect;
    String connectionResult = "";
    Button mButtonManage;
    Button mButtonNewOrder;
    Button mButtonCurrent, mButtonComplete, mButtonEditMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        db = new DatabaseHelper(this);

        //Find Manage Staff Accounts Button and move to page on click
        mButtonManage = (Button) findViewById(R.id.admin_manageStaff);
        mButtonManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToManage();
            }
        });

        //Find New order Button and move to page on click
        mButtonNewOrder = (Button) findViewById(R.id.admin_placeOrder);
        mButtonNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNewOrder();
            }
        });

        //Find Edit Stock Button and move to page on click
        mButtonEditMenu = (Button) findViewById(R.id.admin_editStock);
        mButtonEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEditMenu();
            }
        });

        //Find View Current Order Button and move to page on click
        mButtonCurrent = (Button) findViewById(R.id.admin_viewCurrent);
        mButtonCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCurrent();
            }
        });

        //Find View Complete Order Button and move to page on click
        mButtonComplete = (Button) findViewById(R.id.admin_viewComplete);
        mButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToComplete();
            }
        });
    }

    public void moveToManage() {
        Intent moveToManage = new Intent(getApplicationContext(), zManageStaffAccounts.class);
        startActivity(moveToManage);
    }

    public void moveToEditMenu() {
        Intent moveToEditMenu = new Intent(getApplicationContext(), zEditMenu.class);
        startActivity(moveToEditMenu);
    }

    public void moveToCurrent() {
        Intent moveToCurrent = new Intent(getApplicationContext(), zCurrentOrders.class);
        startActivity(moveToCurrent);
    }

    public void moveToComplete() {
        Intent moveToComplete = new Intent(getApplicationContext(), zCompleteOrders.class);
        startActivity(moveToComplete);
    }

    public void moveToNewOrder() {
        Intent moveToNewOrder = new Intent(getApplicationContext(), yViewMenu.class);
        startActivity(moveToNewOrder);
    }

}