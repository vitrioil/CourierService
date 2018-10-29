package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Tracking {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int userid,orderid,employeeid;
	private String trackingdetail;
        private Timestamp dispatchTime;

        private boolean reached = false;
        
	public Tracking(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Tracking(int userid, int orderid , Connection myConn) {
		this(myConn);
		this.setUserid(userid);
                this.setOrderid(orderid);
	}

        public HashMap<String, String> getOrderDetails()
        {
            ResultSet myRs;
            HashMap<String, String> mapTracking = new HashMap<>();
            try {
                    String query="select employeeid, trackingdetail, dispatchtime from "
                            + "tracking where orderid = ? and userid = ? order by dispatchtime";
                    myStmt=this.myConn.prepareStatement(query);
                    myStmt.setInt(1, this.getOrderid());
                    myStmt.setInt(2, this.getUserid());
                    myRs = myStmt.executeQuery();
                    int i = 0;
                    while(myRs.next() ) {
                           mapTracking.put("Employee Name:"+Integer.toString(i) , myRs.getString("employeeid"));
                           mapTracking.put("Details:"+Integer.toString(i), myRs.getString("trackingdetail"));
                           mapTracking.put("Dispatch Time:"+Integer.toString(i), myRs.getString("dispatchtime"));
                           i += 1;
                    }
                    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            finally{
                return mapTracking;
            }
        }
        
        public void updateTracking()
        {
         
            Timestamp now = new Timestamp(System.currentTimeMillis());
            setDispatchTime(now);
            System.out.println(now);
            try{
                String query = "insert into tracking (userid,orderid,employeeid,"
                        + "trackingdetail,dispatchtime) values(?,?,?,?,?)";
                myStmt=this.myConn.prepareStatement(query);
                myStmt.setInt(1, this.getUserid());
                myStmt.setInt(2, this.getOrderid());
                myStmt.setInt(3, this.getEmployeeid());
                myStmt.setString(4, this.getTrackingdetail());
                myStmt.setTimestamp(5, this.getDispatchTime());
                myStmt.executeUpdate();
                
                query = "update Orders set delivered = ? where orderid = ?";
                myStmt=this.myConn.prepareStatement(query);
                myStmt.setBoolean(1, this.isReached());
                myStmt.setInt(2, this.getOrderid());
                myStmt.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        public boolean isReached() {
            return reached;
        }

        public void setReached(boolean reached) {
            this.reached = reached;
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
        
        public static Connection getMyConn() {
            return myConn;
        }

        public static void setMyConn(Connection myConn) {
            Tracking.myConn = myConn;
        }

        public static PreparedStatement getMyStmt() {
            return myStmt;
        }

        public static void setMyStmt(PreparedStatement myStmt) {
            Tracking.myStmt = myStmt;
        }

        public Timestamp getDispatchTime() {
            return dispatchTime;
        }

        public void setDispatchTime(Timestamp dispatchTime) {
            this.dispatchTime = dispatchTime;
        }
}
