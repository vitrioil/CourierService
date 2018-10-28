package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Tracking {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int userid,orderid,employeeid;
	private String trackingdetail;
	
	public Tracking(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Tracking(int userid, int orderid ,Connection myConn) {
		this(myConn);
		this.setUserid(userid);
                this.orderid=orderid;
	}

        public HashMap<String, String> getOrderDetails()
        {
            ResultSet myRs;
            HashMap<String, String> mapTracking = new HashMap<>();
            try {
                    String query="select employeeid, trackingdetail, dispatchtime from "
                            + "tracking where orderid = ? and userid = ?";
                    myStmt=this.myConn.prepareStatement(query);
                    myStmt.setInt(1, this.getOrderid());
                    myStmt.setInt(2, this.getUserid());
                    myRs = myStmt.executeQuery();
                    while(myRs.next() ) {
                           mapTracking.put("Employee Name", myRs.getString("employeeid"));
                           mapTracking.put("Details", myRs.getString("trackingdetail"));
                           mapTracking.put("Dispatch Time", myRs.getString("dispatchtime"));
                           
                    }
                    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            finally{
                return mapTracking;
            }
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
