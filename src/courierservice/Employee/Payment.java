/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Employee;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    Connection myConn;
    static PreparedStatement myStmt;
    
    
    public Payment(String orderID, String price, String time, String customerID) {
        this.orderID = new SimpleStringProperty(orderID);
        this.price = new SimpleStringProperty(price);
        this.time = new SimpleStringProperty(time);
        this.customerID = new SimpleStringProperty(customerID);
    }
    

    public Connection getMyConn() {
        return myConn;
    }

    public void setMyConn(Connection myConn) {
        this.myConn = myConn;
    }
   
}
