package com.lightingorder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TablesAdapter extends ArrayAdapter<Table>{

        private final Activity context;

        public TablesAdapter(Activity context, ArrayList<Table> t){
            super(context, R.layout.tables_item_list, t);
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent){
            Table tab = getItem(position);

            view = LayoutInflater.from(context).inflate(R.layout.tables_item_list, parent, false);

            TextView iconText = (TextView) view.findViewById(R.id.icon);

            iconText.setText(tab.getText());
            iconText.setBackgroundColor(tab.getColor());

            return view;
        }
}
