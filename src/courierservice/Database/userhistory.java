package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userhistory {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int userid,orderid,price;
	private String courierTime;
	
	public userhistory(Connection myConn) {
		this.myConn=myConn;
	}
	
	public userhistory(int userid, int orderid, int price, Connection myConn) {
		this(myConn);
		this.userid=userid;this.orderid=orderid;this.price=price;
	}
	
	public boolean insertUserHistory() {
		try {
			myStmt = myConn.prepareStatement("insert into userhistory (userid,orderid,price)" 
					+ "values (?,?,?)" );
			
			myStmt.setInt(1, this.getUserid());
			myStmt.setInt(2, this.getOrderid());
			myStmt.setInt(3, this.getPrice());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getCourierTime() {
		return courierTime;
	}

	public void setCourierTime(String courierTime) {
		this.courierTime = courierTime;
	}
	
	
}
