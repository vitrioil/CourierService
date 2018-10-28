package courierservice.Database;

import java.sql.*;

//import database.*;

public class Driver {
	
	public static void main(String[] args) {
		try {
			String url="jdbc:mysql://localhost:3306/Courier";
			String user="vitrioil";
			String password="vitrioil";
			Class.forName("org.mariadb.jdbc.Driver");

			Connection myConn=DriverManager.getConnection(url,user,password);
			
			/*Statement myStmt=myConn.createStatement();
			
			String sql="insert into users (username,email,password,sourceaddress,phone) "
					+ "values ('vinayak','abc@xyz.com','abcdefgh','mumbai','0123456789')";
			
			
			myStmt.executeUpdate(sql);
			ResultSet myRs=myStmt.executeQuery("select * from users");*/
			PreparedStatement myStmt=myConn.prepareStatement("insert into users (username,email,password,sourceaddress,phone)" 
					+ "values (?,?,?,?,?)" );
			myStmt.setString(1, "tushar");
			myStmt.setString(2, "abc@xyz.com");
			myStmt.setString(3, "abcdefgh");
			myStmt.setString(4, "mumbai");
			myStmt.setString(5, "0123456789");
			myStmt.executeUpdate();
			
			ResultSet myRs=myStmt.executeQuery("select * from users");
			//myStmt.executeQuery("CREATE TABLE IF NOT EXISTS `test` (id int ) ");
			while(myRs.next() ) {
				System.out.println(  (new java.util.Date(myRs.getTimestamp("created_at").getTime())).toString() );
			}
			
			User u=new User("tushar","tushar@tushar.com","abcdefgh","mumbai","1234567890",myConn);
		//	u.insertUser();
			System.out.println("user inserted");
			
			//Order o=new Order("india","usa","sensitive","vinayak's gift card","12:00",myConn);
		//	o.insertOrder();
			
			Employee e=new Employee("prem","0987654321","mumbai","male", true ,myConn);
		//	e.insertEmployee();
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
