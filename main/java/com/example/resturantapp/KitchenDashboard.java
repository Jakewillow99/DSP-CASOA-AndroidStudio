package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KitchenDashboard extends AppCompatActivity {

    Button mButtonCurrent, mButtonComplete, mButtonEditMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_dashboard);

        //Find Edit Stock Button and move to page on click
        mButtonEditMenu = (Button) findViewById(R.id.kitch_editStock);
        mButtonEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEditMenu();
            }
        });

        //Find View Current Order Button and move to page on click
        mButtonCurrent = (Button) findViewById(R.id.kitch_viewCurrent);
        mButtonCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCurrent();
            }
        });

        //Find View Complete Order Button and move to page on click
        mButtonComplete = (Button) findViewById(R.id.kitch_viewComplete);
        mButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToComplete();
            }
        });

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
}