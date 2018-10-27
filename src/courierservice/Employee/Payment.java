/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Employee;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author vitrioil
 */
public final class Payment extends RecursiveTreeObject<Payment>{
    final StringProperty orderID;
    final StringProperty price;
    final StringProperty time;
    final StringProperty customerID;

    public Payment(String orderID, String price, String time, String customerID) {
        this.orderID = new SimpleStringProperty(orderID);
        this.price = new SimpleStringProperty(price);
        this.time = new SimpleStringProperty(time);
        this.customerID = new SimpleStringProperty(customerID);
    }
   
}
