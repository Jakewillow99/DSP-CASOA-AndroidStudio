package com.example.resturantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class menuItemAdapter extends ArrayAdapter<MenuItem> {

    Context mCtx;
    int resource;
    List<MenuItem> menuList;
    public List<BaksetItem> baskList;
    public yViewMenu vm = new yViewMenu();

    public int menuID;
    public String menuName;
    public String menuPrice;


    public menuItemAdapter(Context mCtx, int resource, List<MenuItem> menuList){
        super(mCtx, resource, menuList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.menuList = menuList;

        baskList = new ArrayList<>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource, null);

        TextView txtName = view.findViewById(R.id.select_name);
        TextView txtPrice = view.findViewById(R.id.select_price);

        MenuItem menuItem = menuList.get(position);
        menuID = menuItem.getItemID();
        menuName = menuItem.getItemName();
        txtName.setText(menuName);
        menuPrice = menuItem.getItemPrice();
        txtPrice.setText(menuPrice);

        view.findViewById(R.id.item_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItem menuItem = menuList.get(position);
                menuID = menuItem.getItemID();
                menuName = menuItem.getItemName();
                menuPrice = menuItem.getItemPrice();
                vm.addToBasket("User", menuID, menuName, menuPrice);
            }
        });

        return view;
    }

    public ArrayList<BaksetItem> getBasketContents(){

        return (ArrayList<BaksetItem>) baskList;
    }


}
