package com.example.resturantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class zCurrentOrders extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    DatabaseHelper db;
    ArrayList<String> TableNos;
    ArrayList<String> OrderCont;
    ArrayAdapter adapter;
    ListView orderList;

    public String selected;

    TextView mTableNo, mOrderID, mPlacedBy, mTime;
    Button bCurrentClose, bCurrentComplete;
    ListView selectedDisplay;

    public String currOTime, currOPlaced, currOTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_current_orders);
        db = new DatabaseHelper(this);

        orderList = (ListView) findViewById(R.id.current_list);
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
                Toast.makeText(zCurrentOrders.this, ""+selected, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void viewOrders(){
        Cursor c = db.viewOrder();
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
        final View CurrentPopupView = getLayoutInflater().inflate(R.layout.current_popup, null);
        mTableNo = (TextView)CurrentPopupView.findViewById(R.id.current_table);
        mOrderID = (TextView)CurrentPopupView.findViewById(R.id.current_id);
        mPlacedBy = (TextView)CurrentPopupView.findViewById(R.id.current_name);
        mTime = (TextView)CurrentPopupView.findViewById(R.id.current_time);
        bCurrentClose = (Button)CurrentPopupView.findViewById(R.id.current_close);
        bCurrentComplete = (Button)CurrentPopupView.findViewById(R.id.current_complete);
        selectedDisplay = (ListView)CurrentPopupView.findViewById(R.id.current_display);

        OrderCont = db.getOrderContents(selected, "CURRENTORDER");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, OrderCont);
        selectedDisplay.setAdapter(adapter);

        mOrderID.setText(selected);

        currOTable = db.getOrderTableNo(selected, "CURRENTORDER");
        currOPlaced = db.getOrderPlacedBy(selected, "CURRENTORDER");
        currOTime = db.getOrderTime(selected, "CURRENTORDER");

        mTableNo.setText(currOTable);
        mTime.setText(currOTime);
        mPlacedBy.setText(currOPlaced);

        dialogBuilder.setView(CurrentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        bCurrentClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bCurrentComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long val = db.addOrderToComplete(selected,currOTable,currOTime,currOPlaced);

                if (val > 0) {
                    Toast.makeText(zCurrentOrders.this, "Successful Addition", Toast.LENGTH_SHORT).show();
                    db.deleteFromCurrent(selected, "CURRENTORDER");
                } else {
                    Toast.makeText(zCurrentOrders.this, "Failed Addition", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}