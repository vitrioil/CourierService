/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Database;

public class UserNotFoundException extends Exception 
{ 
    public UserNotFoundException(String s) 
    { 
        // Call constructor of parent Exception 
        super(s); 
    } 
} 