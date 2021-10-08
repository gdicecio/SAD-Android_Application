package com.lightingorder;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Functionality> {

    private final Activity context;

    public ListAdapter(Activity context, ArrayList<Functionality> f){
        super(context, R.layout.item_list, f);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent){
        Functionality fun = getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        TextView titleText = (TextView) view.findViewById(R.id.title);
        TextView iconText = (TextView) view.findViewById(R.id.icon);

        titleText.setText(fun.getText());
        iconText.setBackgroundColor(fun.getColor());

        return view;
    }
}
