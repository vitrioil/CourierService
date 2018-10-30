package courierservice.Database;

import static courierservice.Database.User.myStmt;
import courierservice.Employee.Payment;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Employee {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int employeeid;
	private String employeename,phone,address,gender,email,password;
	private Boolean available;
	
        static final String salt = "173c9d968"; 
	
	public Employee(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Employee(String employeename, String phone,String address,Boolean available, Connection myConn) {
		this(myConn);
		this.setEmployeename(employeename);this.setPhone(phone);this.setAddress(address);
		this.available=available;
	}
	
        
        
        public Employee(String email,String password, Connection myConn) throws UserNotFoundException{
	
                password = get_SHA_512_SecurePassword(password, this.salt);
		ResultSet myRs;
                this.myConn = myConn;
		try {
			String query="select employeeid,employeename,email,"
                                + "password,address,phone,available,gender"
                                + " from employee where email = ?";
			myStmt=this.myConn.prepareStatement(query);
			myStmt.setString(1, email);
			myRs = myStmt.executeQuery();
			while(myRs.next() ) {
				this.employeeid = (int)myRs.getLong("employeeid");
				this.employeename = myRs.getString("employeename");
				this.email = myRs.getString("email");
				this.password = myRs.getString("password");
				this.address = myRs.getString("address");
				this.phone = myRs.getString("phone");
                                this.available  =myRs.getBoolean("available");
                                this.gender = myRs.getString("gender");
			}
                        if(this.email == null)
                        {
                            throw new UserNotFoundException("Email ID: " + email +" not found!" + "\nDid you enter correct email Address?");
                        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                if(!this.password.equals(password))
                {
                    throw new UserNotFoundException("Wrong email id or password");
                }
                System.out.println("Welcome " + this.employeename+" "+this.email);
	}
        
	public Boolean insertEmployee() {
		try {
			myStmt = myConn.prepareStatement("insert into employee (empolyeename,phone,address,gender,available)" 
					+ "values (?,?,?,?,?)" );
			
			myStmt.setString(1, this.getEmployeename());
			myStmt.setString(2, this.getPhone());
			myStmt.setString(3, this.getAddress());
			myStmt.setString(4, this.getGender());
			myStmt.setBoolean(5, this.getAvailable());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

        
        public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
            String generatedPassword = null;
            try {
                 MessageDigest md = MessageDigest.getInstance("SHA-512");
                 md.update(salt.getBytes(StandardCharsets.UTF_8));
                 byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
                 StringBuilder sb = new StringBuilder();
                 for(int i=0; i< bytes.length ;i++){
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                 }
                 generatedPassword = sb.toString();
                } 
               catch (NoSuchAlgorithmException e){
                e.printStackTrace();
               }
            return generatedPassword;
        }
        
       public ArrayList<Payment> getPendingPayments()
       {
        ResultSet myRs;
        ArrayList<Payment> listPayment = new ArrayList<>();
        try{
            String query = "select distinct userhistory.price,userhistory.couriertime,userhistory.userid,userhistory.orderid from"
                    + " userhistory inner join Orders inner join tracking where tracking.employeeid = ? and Orders.paid = 0";
            myStmt = this.myConn.prepareStatement(query);
            myStmt.setInt(1, this.getEmployeeid());
            myRs = myStmt.executeQuery();
            int i = 0;
            while(myRs.next())
            {
                String orderID = Integer.toString(myRs.getInt("orderid"));
                String price = Integer.toString(myRs.getInt("price"));
                String time = myRs.getTime("couriertime").toString();
                String customerID = Integer.toString(myRs.getInt("userid"));
                Payment p = new Payment(orderID,price,time,customerID);
                listPayment.add(i, p);
                i += 1;
                System.out.println(i);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return listPayment;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
