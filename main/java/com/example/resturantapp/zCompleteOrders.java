package com.example.resturantapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class zCompleteOrders extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    DatabaseHelper db;
    ArrayList<String> TableNos;
    ArrayList<String> OrderCont;
    ArrayAdapter adapter;
    ListView orderList;

    public String selected;

    TextView mTableNo, mOrderID, mPlacedBy, mTime, mTimeComp;
    Button bCurrentClose;
    ListView selectedDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_complete_orders);
        db = new DatabaseHelper(this);

        orderList = (ListView) findViewById(R.id.comp_list);
        TableNos = new ArrayList<>();
        OrderCont = new ArrayList<>();

        viewOrders();

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = orderList.getItemAtPosition(position).toString();
                int count = orderList.getChildCount();
                for (int i = 0; i < count; i++){
                    if (position == i) {
                        orderList.getChildAt(i).setBackgroundColor(Color.BLUE);
                    } else {
                        orderList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                createOrderDialog();
                Toast.makeText(zCompleteOrders.this, ""+selected, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewOrders(){
        Cursor c = db.viewOrderComp();
        if (c.getCount() == 0){
            Toast.makeText(this, "No Accounts Found", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()){
                TableNos.add(c.getString(0));
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TableNos);
            orderList.setAdapter(adapter);
        }

    }

    public void createOrderDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View CompletePopupView = getLayoutInflater().inflate(R.layout.complete_popup, null);
        mTableNo = (TextView)CompletePopupView.findViewById(R.id.comp_table);
        mOrderID = (TextView)CompletePopupView.findViewById(R.id.comp_id);
        mPlacedBy = (TextView)CompletePopupView.findViewById(R.id.comp_name);
        mTime = (TextView)CompletePopupView.findViewById(R.id.comp_time);
        mTimeComp = (TextView)CompletePopupView.findViewById(R.id.comp_timecomp);
        bCurrentClose = (Button)CompletePopupView.findViewById(R.id.comp_close);
        selectedDisplay = (ListView)CompletePopupView.findViewById(R.id.comp_display);

        OrderCont = db.getOrderContents(selected, "COMPLETEORDERS");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, OrderCont);
        selectedDisplay.setAdapter(adapter);

        mOrderID.setText(selected);
        mTableNo.setText(db.getOrderTableNo(selected, "COMPLETEORDERS"));
        mTime.setText(db.getOrderTime(selected, "COMPLETEORDERS"));
        mTimeComp.setText(db.getOrderTimeComp(selected, "COMPLETEORDERS"));
        mPlacedBy.setText(db.getOrderPlacedBy(selected, "COMPLETEORDERS"));

        dialogBuilder.setView(CompletePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        bCurrentClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}