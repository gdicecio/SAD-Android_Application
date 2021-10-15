package com.lightingorder.Model.MenuAndWareHouseArea;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**JSON Format:
 * {
 *      name : "name_value"
 *      description : "description_value"
 *      price : "price_value"
 *      inStock : "inStock_value"
 *      area : "area_value"
 *      goodsID : ["goodsID1", "goodsID2", ... , "goodsIDn"]
 * }
 * */
public class MenuItem {

    @Expose
    private String description;
    @Expose
    protected String name;
    @Expose
    protected double price;
    @Expose
    private boolean inStock;
    @Expose
    protected String area;
    @Expose
    protected List<String> goodsID;

    /**
     * Constructor for ordered item
     * @param item
     */

    public MenuItem(){

    }

    protected MenuItem(MenuItem item) {
        this.name=item.name;
        this.price=item.price;
        this.goodsID=new ArrayList<String>();
        this.area=item.area;
    }

    public MenuItem(String description, String name, double price, String area, boolean inStock){
        this.description = description;
        this.name = name;
        this.price = price;
        this.area = area;
        this.inStock = inStock;
    }


    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getName() { return name;}

    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price;}

    public boolean isInStock() { return inStock; }

    public void setInStock() { this.inStock = true;}

    public void setOutOfStock(){this.inStock = false;}



}
