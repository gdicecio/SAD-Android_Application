package com.lightingorder.View.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.OrderedItem;
import com.lightingorder.R;

import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<Order> {

    public Activity context;

    public OrderListAdapter(Activity context, ArrayList<Order> list){
        super(context, R.layout.item_list_order);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent){
        Order local_order = getItem(position);

        view = LayoutInflater.from(context).inflate(R.layout.item_list_order, parent, false);

        TextView order_id = (TextView) view.findViewById(R.id.order_id);
        TextView user_id = (TextView) view.findViewById(R.id.user_id_order);
        TextView products = (TextView) view.findViewById(R.id.menu_item_order);
        TextView state = (TextView) view.findViewById(R.id.state_order);

        String product_list = "";
        for(OrderedItem i : local_order.getOrderedItems()){
            product_list += i.getItem();
            product_list += " - ";
        }

        order_id.setText(local_order.getOrderID());
        user_id.setText(local_order.getUserID());
        products.setText(product_list);
        state.setText(local_order.getOrderState());

        return view;
    }
}
