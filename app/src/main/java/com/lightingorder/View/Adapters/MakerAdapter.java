package com.lightingorder.View.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lightingorder.R;

import java.util.ArrayList;

public class MakerAdapter extends ArrayAdapter<OrderObjectAdapter> {

    private final Activity context;

    public MakerAdapter(Activity context, ArrayList<OrderObjectAdapter> resource) {
        super(context, R.layout.item_list_order_maker, resource);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent){
        OrderObjectAdapter o = getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_list_order_maker, parent, false);

        TextView table_id = (TextView) view.findViewById(R.id.table_id_maker);
        TextView room_number = (TextView) view.findViewById(R.id.room_number_maker);
        TextView order_id = (TextView) view.findViewById(R.id.order_id_maker);
        TextView menu_item = (TextView) view.findViewById(R.id.menu_item_maker);
        TextView line_order = (TextView) view.findViewById(R.id.line_order_maker);
        TextView priority = (TextView) view.findViewById(R.id.priority_maker);
        TextView user_id = (TextView) view.findViewById(R.id.user_id_maker);

        table_id.setText(o.getTable_number());
        room_number.setText(o.getTable_room_number());
        order_id.setText(o.getOrder_id());
        menu_item.setText(o.getProduct());
        line_order.setText(o.getLine_order());
        priority.setText(o.getPriority());
        user_id.setText(o.getUser_id());

        return view;
    }
}
