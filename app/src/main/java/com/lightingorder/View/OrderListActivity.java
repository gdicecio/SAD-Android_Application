package com.lightingorder.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.Model.Data;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.R;
import com.lightingorder.View.Adapters.OrderListAdapter;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    ListView order_list;
    TextView table_number;
    TextView room_number;
    Button addOrder;

    ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        orderList = new ArrayList<Order>();

        //TODO Popolare orderList dal modello
        orderList = Data.getData().getOrdersList();

        OrderListAdapter adapter = new OrderListAdapter(this, orderList);

        order_list = (ListView) findViewById(R.id.orderlist);
        table_number = (TextView) findViewById(R.id.table_number_order_list);
        room_number = (TextView) findViewById(R.id.room_number_orderlist);
        addOrder = (Button) findViewById(R.id.aggiungi_orderlist);

        order_list.setAdapter(adapter);
    }
}
