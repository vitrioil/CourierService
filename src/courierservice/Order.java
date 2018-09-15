/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

/**
 *
 * @author vitrioil
 */
public class Order {
    
    String orderID;
    int numberOfItems;
    int price;
    String productName;
    String timestamp;
    
    public Order(String productName, String timestamp)
    {
        this.productName = productName;
        this.timestamp = timestamp;
    }
    
    public String getOrderRepresentation()
    {
        return productName;
    }
    
}
