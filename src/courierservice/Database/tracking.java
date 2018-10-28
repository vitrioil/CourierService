package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class tracking {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int userid,orderid,employeeid;
	private String trackingdetail;
	
	public tracking(Connection myConn) {
		this.myConn=myConn;
	}
	
	public tracking(int userid, int orderid, int employeeid,Connection myConn) {
		this(myConn);
		this.setUserid(userid);this.orderid=orderid;this.employeeid=employeeid;
	}

	public String getTrackingdetail() {
		return trackingdetail;
	}

	public void setTrackingdetail(String trackingdetail) {
		this.trackingdetail = trackingdetail;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

}
