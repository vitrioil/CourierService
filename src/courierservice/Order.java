/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import java.util.HashMap;
import javafx.scene.control.Label;

/**
 *
 * @author vitrioil
 */
public class Order {
    
    private String orderID;
    private int numberOfItems;
    private int price;
    private String productName;
    private String timestamp;
    
    
    public Order(String productName, String timestamp, String orderID, int numberOfItems, int price)
    {
        this.productName = productName;
        this.timestamp = timestamp;
        this.orderID = orderID;
        this.numberOfItems = numberOfItems;
        this.price = price;
    }
    
    public String getName()
    {
        return productName;
    }
    
    public HashMap getDetails()
    {
        HashMap<String, Label> details = new HashMap();
        
        details.put("productName", new Label("Product Name:  " + productName));
        details.put("timestamp", new Label("Time:   " + timestamp));
        details.put("orderID", new Label("Courier Number:   " + orderID));
        details.put("numberOfItems", new Label("Total number shipped: " + Integer.toString(numberOfItems)));
        details.put("price", new Label("Price:    " + Integer.toString(price)));
        
        return details;
    }
 
    
}
