package com.lightingorder.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.ConnectivityController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.Model.Data;
import com.lightingorder.Model.Functionality;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.R;
import com.lightingorder.StdTerms;
import com.lightingorder.View.Adapters.OrderListAdapter;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    ListView order_list;
    TextView table_number;
    TextView room_number;
    Button addOrder;
    ArrayList<Order> orderList = new ArrayList<Order>();
    UserSessionController user_contr = new UserSessionController();
    String tableID;
    int roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        tableID = getIntent().getStringExtra("tableID");
        roomNumber = getIntent().getIntExtra("roomNumber",1);

        orderList = Data.getData().getTableOrders(tableID,roomNumber);

        OrderListAdapter adapter = new OrderListAdapter(this, orderList);

        order_list = (ListView) findViewById(R.id.orderlist);
        table_number = (TextView) findViewById(R.id.table_number_order_list);
        room_number = (TextView) findViewById(R.id.room_number_orderlist);
        addOrder = (Button) findViewById(R.id.aggiungi_orderlist);
        table_number.setText(tableID);
        room_number.setText(Integer.toString(roomNumber));

        order_list.setAdapter(adapter);
        order_list.setOnLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(OrderListAdapter parent, View view, int position, long id) {
                int i  = 0;
                return true;
            }
        });
    }

    public void addOrder(View view){
        Intent i = new Intent(getApplicationContext(), OrderActivity.class);
        i.putExtra("tableID", tableID);
        i.putExtra("roomNumber", roomNumber);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);
    }
}
