package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Order {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int orderid;
	private String source,destination,deliveryType,details,orderName;

        
	private Time pickupTime;
        
        private boolean assigned=false,delivered=false,paid=false;
        
	public Order(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Order(String source, String destination, String deliveryType, String details,
                Time pickupTime, String OrderName, Connection myConn) {
		this(myConn);
		this.setSource(source);this.setDestination(destination);this.setPickupTime(pickupTime);
		this.setDeliveryType(deliveryType);this.setDetails(details);this.setOrderName(OrderName);
	}
	
	public boolean insertOrder(int userid) {
                int orderid = getMaxOrderID() + 1;
                this.setOrderid(orderid);
		try {
			myStmt = myConn.prepareStatement("insert into Orders (userid, orderid, destination,source,"
                                + "deliverytype,details,pickuptime,ordername)" 
					+ "values (?,?,?,?,?,?,?,?)" );
			myStmt.setInt(1, userid);
			myStmt.setInt(2, this.getOrderid());
			myStmt.setString(3, this.getDestination());
			myStmt.setString(4, this.getSource());
			myStmt.setString(5, this.getDeliveryType());
			myStmt.setString(6, this.getDetails());
			myStmt.setTime(7, this.getPickupTime());
			myStmt.setString(8, this.getOrderName());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
        int getMaxOrderID()
        {
            int orderid = -1;
            ResultSet rs;
            try {
			String query = "select max(orderid) as orderid from Orders" ;
                        myStmt = this.myConn.prepareStatement(query);
                        rs = myStmt.executeQuery();
			while(rs.next())
                        {
                            orderid = rs.getInt("orderid");
                        }
                        
                } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                 finally{
                return orderid;
            }
        }
        
        public static int getPrice(int orderid, Connection myConn)
        {
            int price = -1;
            ResultSet myRs;
            try{
                String query = "select price from userhistory where orderid = ?";
                myStmt = myConn.prepareStatement(query);
                myStmt.setInt(1, orderid);
                myRs = myStmt.executeQuery();
                while(myRs.next())
                {
                    price = myRs.getInt("price");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            return price;
        }
        
	/*public Order(String email,String password) {
		/*convert password string to hash pass here 
		ResultSet myRs;
		try {
			String query="select * from users where username = ? and password = ?";
			myStmt=myConn.prepareStatement(query);
			myStmt.setString(1, email);
			myStmt.setString(2, password);
			myRs = myStmt.executeQuery();
			while(myRs.next() ) {
				this.userid=(int)myRs.getLong("userid");
				this.username=myRs.getString("username");
				this.email=myRs.getString("email");
				this.password=myRs.getString("password");
				this.sourceAddress=myRs.getString("sourceaddress");
				this.phone=myRs.getString("phone") ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
       
        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }
	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
        
	public Time getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Time pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
        public boolean isAssigned() {
            return assigned;
        }

        public void setAssigned(boolean assigned) {
            this.assigned = assigned;
        }

        public boolean isDelivered() {
            return delivered;
        }

        public void setDelivered(boolean delivered) {
            this.delivered = delivered;
        }

        public boolean isPaid() {
            return paid;
        }

        public void setPaid(boolean paid) {
            this.paid = paid;
        }
}
