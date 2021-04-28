package com.example.resturantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class zEditMenu extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    DatabaseHelper db;
    public ArrayList cMenuList;
    public String sectionSelect = "Starter";
    public String updateSelect = "Starter";
    public String addSelect = "Starter";
    public int itemSelect = 0;
    public int menuCount;

    Button bViewMenu, bAddMenu;

    ArrayAdapter adapter;

    Button bEditMClose;
    ListView displayCMenu;
    Spinner sMSelector;
    EditText mItemName, mItemDesc, mItemPrice;
    Spinner sItemSelector;
    Button bEDMClose, bUpdate, bDelete;
    EditText mAddName, mAddDesc, mAddPrice;
    Spinner sAddSelector;
    Button bAddClose, bAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_edit_menu);
        db = new DatabaseHelper(this);

        menuCount = db.getMenuCount();
        cMenuList = new ArrayList<>();

        bViewMenu = (Button)findViewById(R.id.viewMenu);
        bViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEditMDialog();
            }
        });

        bAddMenu = (Button)findViewById(R.id.addMenu);
        bAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddMDialog();
            }
        });
    }

    public void displaySelected(){
        cMenuList.clear();
        for (int i = 1; i <= menuCount; i++){
            boolean check = db.getSectionbyID(i, sectionSelect);
            if (check == true){
                cMenuList.add(db.getMenuNameByID(i));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cMenuList);
        displayCMenu.setAdapter(adapter);

    }

    public void createEditMDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View EditMPopupView = getLayoutInflater().inflate(R.layout.edit_menu_popup, null);
        bEditMClose = (Button)EditMPopupView.findViewById(R.id.editM_close);
        sMSelector = (Spinner)EditMPopupView.findViewById(R.id.editM_selection);
        displayCMenu = (ListView)EditMPopupView.findViewById(R.id.editM_display);

        displaySelected();

        dialogBuilder.setView(EditMPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        bEditMClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sMSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionSelect = parent.getItemAtPosition(position).toString();
                displaySelected();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        displayCMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName = displayCMenu.getItemAtPosition(position).toString();
                itemSelect = db.getMenuID(itemName, "MENU");
                int count = displayCMenu.getChildCount();
                for (int i = 0; i < count; i++){
                    if (position == i) {
                        displayCMenu.getChildAt(i).setBackgroundColor(Color.BLUE);
                    } else {
                        displayCMenu.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                Toast.makeText(zEditMenu.this, ""+itemName, Toast.LENGTH_SHORT).show();
                createEDMDialog();

            }
        });

    }

    public void createEDMDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View EDMPopupView = getLayoutInflater().inflate(R.layout.editdelete_menu_popup, null);
        mItemName = (EditText) EDMPopupView.findViewById(R.id.EDM_itemName);
        mItemDesc = (EditText) EDMPopupView.findViewById(R.id.EDM_itemDesc);
        mItemPrice = (EditText) EDMPopupView.findViewById(R.id.EDM_itemPrice);
        sItemSelector = (Spinner) EDMPopupView.findViewById(R.id.EDM_selection);
        bEDMClose = (Button) EDMPopupView.findViewById(R.id.EDM_close);
        bUpdate = (Button) EDMPopupView.findViewById(R.id.EDM_update);
        bDelete = (Button) EDMPopupView.findViewById(R.id.EDM_delete);

        MenuItem thisItem;
        thisItem = db.getItemsByID(itemSelect);

        mItemName.setHint(thisItem.getItemName());
        mItemDesc.setHint(thisItem.getItemDesc());
        mItemPrice.setHint(thisItem.getItemPrice());

        dialogBuilder.setView(EDMPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        bEditMClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long res = db.deleteFromMenu(itemSelect, "MENU");

                if (res > 0) {
                    Toast.makeText(zEditMenu.this, "Successful Delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(zEditMenu.this, "Failed Delete", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(getIntent());
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = mItemName.getText().toString();
                String itemDesc = mItemDesc.getText().toString();
                String itemPrice = mItemPrice.getText().toString();

                long res = db.updateMenu(itemSelect, itemName, itemDesc, itemPrice, updateSelect);

                if (res > 0) {
                    Toast.makeText(zEditMenu.this, "Successful Update", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(zEditMenu.this, "Failed Update", Toast.LENGTH_SHORT).show();

                }
                finish();
                startActivity(getIntent());

            }
        });

        sMSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSelect = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void createAddMDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View AddMPopupView = getLayoutInflater().inflate(R.layout.add_menu_popup, null);
        mAddName = (EditText) AddMPopupView.findViewById(R.id.addM_itemName);
        mAddDesc = (EditText) AddMPopupView.findViewById(R.id.addM_itemDesc);
        mAddPrice = (EditText) AddMPopupView.findViewById(R.id.addM_itemPrice);
        sAddSelector = (Spinner) AddMPopupView.findViewById(R.id.addM_selection);
        bAddClose = (Button) AddMPopupView.findViewById(R.id.addM_close);
        bUpdate = (Button) AddMPopupView.findViewById(R.id.addM_add);

        dialogBuilder.setView(AddMPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        bAddClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = mAddName.getText().toString();
                String itemDesc = mAddDesc.getText().toString();
                String itemPrice = mAddPrice.getText().toString();

                long res = db.addMenuItem(itemName, itemDesc, itemPrice, addSelect);

                if (res > 0) {
                    Toast.makeText(zEditMenu.this, "Successful Addition", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(zEditMenu.this, "Failed Addition", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(getIntent());
            }
        });

        sAddSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addSelect = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}