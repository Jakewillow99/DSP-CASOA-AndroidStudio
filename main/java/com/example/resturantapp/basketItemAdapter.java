package com.example.resturantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class basketItemAdapter extends ArrayAdapter<BaksetItem> {

    //Set Variables Used.
    Context mCtx;
    int resource;
    List<BaksetItem> basketList;
    public yViewMenu vm = new yViewMenu();

    public basketItemAdapter(Context mCtx, int resource, List<BaksetItem> basketList){
        super(mCtx, resource, basketList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.basketList = basketList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource, null);

        TextView txtName = view.findViewById(R.id.current_display);
        TextView txtPrice = view.findViewById(R.id.currentID);

        BaksetItem baskItem = basketList.get(position);
        txtName.setText(baskItem.getBaskName());
        txtPrice.setText(baskItem.getBaskPrice());

        view.findViewById(R.id.bask_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaksetItem baskItem = basketList.get(position);
                int baskID = baskItem.getBaskID();
                vm.deleteFromBasket(baskID, position);

            }
        });
        return view;
    }
}
