package courierservice.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Package {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int objectid,orderid;
	private String packageType,packageName;
	
	public Package(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Package(String packageType,String packageName,Connection myConn) {
		this(myConn);
                this.setPackageType(packageType);this.setPackageName(packageName);
	}
	
	public Boolean insertObject() {
		try {
			myStmt = myConn.prepareStatement("insert into object (orderid, packagetype,packagename)" 
					+ "values (?, ?, ?)" );
			
			myStmt.setInt(1, this.getOrderid());
			myStmt.setString(2, this.getPackageType());
			myStmt.setString(3, this.getPackageName());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	Integer getObjectCount(int orderId) {
		try {
			myStmt = myConn.prepareStatement("select count(*) from object where orderid = ?" );
			myStmt.setInt(1, orderId);
			ResultSet myRs = myStmt.executeQuery();
			myStmt.executeUpdate();
			
			while(myRs.next() ) {
				return myRs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public Package[] getObject(int orderID) {
		try {
			myStmt = myConn.prepareStatement("select * from object where orderid = ?" );
			myStmt.setInt(1, orderID);
			ResultSet myRs = myStmt.executeQuery();
			myStmt.executeUpdate();
			Package[] obj_arr=new Package[getObjectCount(orderID)];
			
			int i=0;
			while(myRs.next() ) {
				obj_arr[i].objectid= (int)myRs.getInt("objectid");
				obj_arr[i].orderid=myRs.getInt("orderid");
				obj_arr[i].packageName=myRs.getString("packagename");
				obj_arr[i].packageType=myRs.getString("packageType");
                                i += 1;
                        }
			return obj_arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

        public static ArrayList<Package> getPackages(int orderID, Connection myConn)
        {
            ArrayList<Package> listPackages = new ArrayList<>();
            try {
			myStmt = myConn.prepareStatement("select objectid, packageType,packageName from object where orderid=?" );
			myStmt.setInt(1, orderID);
			ResultSet myRs = myStmt.executeQuery();
			myStmt.executeUpdate();
			
			int i=0;
			while(myRs.next() ) {
                                Package p = new Package(myRs.getString("packageType"), myRs.getString("packagename"), myConn);
				listPackages.add(i, p);
                                i += 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            
            
            return listPackages;
        }
        
	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public int getObjectid() {
		return objectid;
	}

	public void setObjectid(int objectid) {
		this.objectid = objectid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	

}
