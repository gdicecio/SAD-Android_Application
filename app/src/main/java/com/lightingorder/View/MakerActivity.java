package com.lightingorder.View;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.Model.Data;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.OrderedItem;
import com.lightingorder.R;
import com.lightingorder.View.Adapters.MakerAdapter;
import com.lightingorder.View.Adapters.OrderObjectAdapter;

import java.util.ArrayList;

public class MakerActivity extends AppCompatActivity {

    ListView order_area_list;
    private ArrayList<Order> total_order;
    private ArrayList<OrderObjectAdapter> order_item_list;
    private UserSessionController user_contr = new UserSessionController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker);
        order_area_list = (ListView) findViewById(R.id.order_area_list);

        order_item_list = getOrdersArea();

        MakerAdapter adapter = new MakerAdapter(this, order_item_list);
        order_area_list.setAdapter(adapter);
    }

    public ArrayList<OrderObjectAdapter> getOrdersArea(){
        order_item_list = new ArrayList<OrderObjectAdapter>();
        total_order = new ArrayList<Order>();
        total_order = Data.getData().getOrdersList(user_contr.getCurrentRole());
        for(Order o : total_order){
            for(OrderedItem oi : o.getOrderedItems()){
                OrderObjectAdapter object_adapter = new OrderObjectAdapter(oi, o);
                order_item_list.add(object_adapter);
            }
        }
        return order_item_list;
    }


    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);
    }
}

