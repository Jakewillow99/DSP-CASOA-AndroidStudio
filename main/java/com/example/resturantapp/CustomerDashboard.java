package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerDashboard extends AppCompatActivity {

    //Set Variables Used.
    Button mButtonNewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        //Find View Menu Button and move to page on click
        mButtonNewOrder = (Button) findViewById(R.id.cust_placeOrder);
        mButtonNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNewOrder();
            }
        });
    }

    public void moveToNewOrder()
    {
        Intent moveToNewOrder = new Intent(getApplicationContext(),yViewMenu.class);
        startActivity(moveToNewOrder);
    }
}