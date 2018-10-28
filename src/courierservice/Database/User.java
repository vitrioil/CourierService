package courierservice.Database;

import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	static Connection myConn;
	static PreparedStatement myStmt;
	
        static final String salt = "173c9d968"; 
	private int userid;
	private String username,email,password,createdAt,sourceAddress,phone;
	
	public User(Connection myConn) {
		this.myConn=myConn;
	}
	
	public User(String username,String email,String password,String sourceAddress,String phone, Connection myConn) {
                this.myConn = myConn;
		this.username=username;
                this.email=email;
                this.password=get_SHA_512_SecurePassword(password, this.salt);
                this.sourceAddress=sourceAddress;
		this.phone=phone;
                System.out.println("Welcome " + this.username+" "+this.email);
	}
		
	public boolean insertUser() {
                if(checkUserExists(this.email))
                {
                    return false;
                }
		try {
			
			myStmt = myConn.prepareStatement("insert into users (username,email,password,sourceaddress,phone) values (?,?,?,?,?)" );

			myStmt.setString(1, this.getUsername());
			myStmt.setString(2, this.getEmail());
			myStmt.setString(3, this.getPassword());
			myStmt.setString(4, this.getSourceAddress());
			myStmt.setString(5, this.getPhone());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
        boolean checkUserExists(String email)
        {
            ResultSet myRs;
            try {
                    String query="select userid,username,email,password,sourceAddress,phone"
                            + " from users where email = ?";
                    myStmt=this.myConn.prepareStatement(query);
                    myStmt.setString(1, email);
                    myRs = myStmt.executeQuery();
                    while(myRs.next() ) {
                            this.userid=(int)myRs.getLong("userid");
                            this.username=myRs.getString("username");
                            this.email=myRs.getString("email");
                            this.password=myRs.getString("password");
                            this.sourceAddress=myRs.getString("sourceaddress");
                            this.phone=myRs.getString("phone") ;
                    }
                    if(this.email == null)
                    {
                        return false;
                    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                return true;
        }
        
	public User(String email,String password, Connection myConn) throws UserNotFoundException{
		/*convert password string to hash pass here */
                password = get_SHA_512_SecurePassword(password, this.salt);
		ResultSet myRs;
                this.myConn = myConn;
		try {
			String query="select userid,username,email,password,sourceAddress,phone"
                                + " from users where email = ?";
			myStmt=this.myConn.prepareStatement(query);
			myStmt.setString(1, email);
			myRs = myStmt.executeQuery();
			while(myRs.next() ) {
				this.userid=(int)myRs.getLong("userid");
				this.username=myRs.getString("username");
				this.email=myRs.getString("email");
				this.password=myRs.getString("password");
				this.sourceAddress=myRs.getString("sourceaddress");
				this.phone=myRs.getString("phone") ;
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
                System.out.println("Welcome " + this.username+" "+this.email);
	}



        public String get_SHA_512_SecurePassword(String passwordToHash, String   salt){
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
        
         public ArrayList<Order> getOrder()
        {
            ResultSet rs;
            ArrayList<Order> arrayListOrder = new ArrayList<>();
            try {
			String query = "select orderid,source,destination,deliverytype,details,pickuptime"
                                + " from Orders inner join users where users.userid = ?" ;
                        myStmt = this.myConn.prepareStatement(query);
                        myStmt.setInt(1, this.getUserid());
                        rs = myStmt.executeQuery();
			while(rs.next())
                        {
                            Order order = new Order(rs.getString("source"),
                                              rs.getString("destination"),
                                              rs.getString("deliverytype"),
                                              rs.getString("details"),
                                              rs.getTime("pickuptime"),
                                              this.myConn
                            );
                            order.setOrderid(rs.getInt("orderid"));
                            arrayListOrder.add(order);
                        }
                } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                 return arrayListOrder;
        }
        
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}



