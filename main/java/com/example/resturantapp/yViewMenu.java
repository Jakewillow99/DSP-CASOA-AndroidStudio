package com.example.resturantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class yViewMenu extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button baskClose, BaskSetTable, tableClose, tableSelect;
    private TextView baskTotal;
    private ListView baskDisplay, tableDisplay;



    DatabaseHelper db;

    public List<MenuItem> menuItemList;
    public static List<BaksetItem> baskList;
    ListView listView;
    List<String> sections;
    public int menuCount;
    public String sectionSelect = "Starter";
    TextView starter;
    TextView pasta;
    TextView grill;
    TextView deserts;
    Button basket;
    public static int basketID = 101;
    public static ArrayList<String> tables;
    public static String selected = "NOTHING";

    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_view_menu);
        db = new DatabaseHelper(this);
        List<String> sections = new ArrayList<>();

        starter = (TextView)findViewById(R.id.view_starter);
        pasta = (TextView)findViewById(R.id.view_pasta);
        grill = (TextView)findViewById(R.id.view_grill);
        deserts = (TextView)findViewById(R.id.view_deserts);
        basket = (Button)findViewById(R.id.basket);

        menuCount = db.getMenuCount();

        menuItemList = new ArrayList<>();

        baskList = new ArrayList<>();

        tables = new ArrayList<>();

        displaySelected();


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionSelect = "Starter";
                displaySelected();
            }
        });
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionSelect = "Pasta & Salads";
                displaySelected();
            }
        });
        grill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionSelect = "Grill";
                displaySelected();
            }
        });
        deserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionSelect = "Deserts";
                displaySelected();
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewBasketDialog();
            }
        });




    }
    public void addToBasket(String user, int menuID, String itemName, String itemPrice){
        baskList.add(new BaksetItem(basketID, menuID, user, itemName, itemPrice));
        basketID = basketID + 1;
    }
    public void deleteFromBasket(int baskID, int position){
        baskList.remove(position);

        }



    public void displaySelected(){
        menuItemList.clear();
        for (int i = 1; i <= menuCount; i++){
            boolean check = db.getSectionbyID(i, sectionSelect);
            if (check == true){
                menuItemList.add(db.getItemsByID(i));
            }
        }
        listView = (ListView)findViewById(R.id.list_view);
        menuItemAdapter adapter2 = new menuItemAdapter(this, R.layout.menu_item, menuItemList);
        listView.setAdapter(adapter2);

    }

    private void viewTables(){
        Cursor c = db.viewTables();
        if (c.getCount() == 0){

            Toast.makeText(this, "No Accounts Found", Toast.LENGTH_SHORT).show();

        } else {
            while (c.moveToNext()){
                String check = c.getString(0);
                tables.add(c.getString(0));

            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tables);

            tableDisplay.setAdapter(adapter);

        }

    }

    public void createNewBasketDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View BasketPopupView = getLayoutInflater().inflate(R.layout.basket_popup, null);
        baskClose = (Button)BasketPopupView.findViewById(R.id.bask_close);
        BaskSetTable = (Button)BasketPopupView.findViewById(R.id.bask_set_table);
        baskTotal = (TextView)BasketPopupView.findViewById(R.id.bask_total);
        baskDisplay = (ListView)BasketPopupView.findViewById(R.id.bask_display);
        BaskSetTable = (Button)BasketPopupView.findViewById(R.id.bask_set_table);

        basketItemAdapter adapter3 = new basketItemAdapter(this, R.layout.basket_item, baskList);
        baskDisplay.setAdapter(adapter3);

        dialogBuilder.setView(BasketPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        baskClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        BaskSetTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTableDialog();
            }
        });

    }

    public void createTableDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View TablePopupView = getLayoutInflater().inflate(R.layout.table_popup, null);
        tableClose = (Button)TablePopupView.findViewById(R.id.table_close);
        tableDisplay = (ListView)TablePopupView.findViewById(R.id.table_display);
        tableSelect = (Button)TablePopupView.findViewById(R.id.table_select);

        viewTables();

        dialogBuilder.setView(TablePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        tableClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tableDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = tableDisplay.getItemAtPosition(position).toString();
                int count = tableDisplay.getChildCount();

                Toast.makeText(yViewMenu.this, ""+selected, Toast.LENGTH_SHORT).show();
            }
        });

        tableSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> itemIDS = new ArrayList<>();
                for (int i = 0; i < baskList.size(); i++){
                    BaksetItem baskItem = baskList.get(i);
                    itemIDS.add(baskItem.getMenuID());
                }

                long val = db.addOrderToCurrent("User", selected, itemIDS);

                if (val > 0) {
                    Toast.makeText(yViewMenu.this, "Successful Addition", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(yViewMenu.this, "Failed Addition", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}