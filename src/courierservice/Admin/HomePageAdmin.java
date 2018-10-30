/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;

import courierservice.Database.Employee;
import courierservice.Database.Order;
import courierservice.Database.User;
import courierservice.HomePage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author Admin
 */
public class HomePageAdmin extends Application {
 
    // Three tabs at the top of home page
    Tab newOrder;
    Tab history;
    Tab tracking;
    Tab clients;
    Tab packetEstimation;
    Tab addEmp;
    
    static Connection primaryConn;
    static Statement st;
    static PreparedStatement pStmt;
	
    static List<User> list_clients=new ArrayList<>();
	static ObservableList<User> data_clients;
	static List<Order> list_orders=new ArrayList<>();
	static ObservableList<Order> data_order;
	static List<Employee> list_employee=new ArrayList<>();
	private static ObservableList<Employee> data_emp;
    
    
    
    //Creating an object to pass to different scenes
    //But they should have the same stage otherwise
    //there will be multiple windows
    Stage primaryStage;
    
    
    
    //Borderpane stores the layout of the scene
    BorderPane borderPane;
    
    //Hashmap is used to transfer the primaryStage and borderPane
    //across the scenes.
    HashMap<Stage, BorderPane> mapStagePane = new HashMap();
    
    double buttonRadius = 15;
    
    public GridPane sampleGridPane()
    {
        GridPane gridPane = new GridPane();
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        /*
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        */
        
        //Add a css class to so that all tabs will have the same effect 
        gridPane.getStyleClass().add("grid-pane");
        
        return gridPane;
    }
    
    public GridPane getGridPaneHelpTypePackage()
    {
        /*
            This function is used to create grid for
            the help button (?) button.
        */
        GridPane gridPaneHelpTypePackage = new GridPane();
        Label labelFragile = new Label("Fragile: Increase in cost for padding and protection");
        Label labelDurable = new Label("Durable: Decrease in cost for reducing padding and protection");
        Label labelOther = new Label("Other: Normal delivery with sufficient padding and protection");
        
        VBox vBoxLabel = new VBox();
        vBoxLabel.getChildren().addAll(
                      labelFragile,
                      labelDurable,
                      labelOther
        );
        gridPaneHelpTypePackage.getChildren().addAll(vBoxLabel);
       // gridPaneHelpTypePackage.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-hgap:3;-fx-vgap:5;");
        return gridPaneHelpTypePackage;
    }
    
    public GridPane getGridPaneHelpTypeDelivery()
    {
        /*
            This function is used to create grid for
            the help button (?) button.
        */
        
        GridPane gridPaneHelpTypeDelivery = new GridPane();
        Label labelNextDay = new Label("Next Day: Increase in cost for next day delivery");
        Label labelSpeed = new Label("Speed: Increase in cost for speed delivery");
        Label labelNormal = new Label("Normal: Normal delivery with no guarantee of speed delivery");
        
        VBox vBoxLabel = new VBox();
        vBoxLabel.getChildren().addAll(
                      labelNextDay,
                      labelSpeed,
                      labelNormal
        );
        gridPaneHelpTypeDelivery.getChildren().addAll(vBoxLabel);
      //  gridPaneHelpTypeDelivery.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-hgap:3;-fx-vgap:5;"); 
        return gridPaneHelpTypeDelivery;
    }
   
    public GridPane  getConfirmOrderGridPane(JFXButton buttonConfirmOrder, JFXButton buttonAddObject, JFXButton buttonClose )
    {
       GridPane gridPane = new GridPane();
       gridPane.setPadding(new Insets(10, 10 ,10 ,10));
       Label labelConfirm = new Label("Do you want to confirm or add another object?");
      
       Separator separator = new Separator();

	HBox hBox = new HBox();
	hBox.setSpacing(10);
        hBox.getChildren().addAll(
	         buttonConfirmOrder,
		 buttonAddObject,
		 buttonClose
	);
       gridPane.add(labelConfirm, 0, 0);
       gridPane.add(separator, 0, 1);
       gridPane.add(hBox, 0, 2);
       return gridPane;
    }    
        
    private String[] getLocation(Order order)
    {
        //Write the part which gets location history 
        //from database
        /*
               ======================
               Database support here!
               ======================
        */
        String[] locHistory = {"Dispatched", "At the stop", "Reached"};
        return locHistory;
    }
    
    
    public GridPane getGridNotifications(JFXDrawersStack drawersStack, JFXDrawer rightDrawerNotifications)
    {
    	/*
		Returns the grid for the notficaton
		side drawer pane
	*/
        GridPane gridPaneNotifications = new GridPane();
        
        JFXCheckBox checkShipment = new JFXCheckBox("Shipment Notifications");
        JFXCheckBox checkAlerts = new JFXCheckBox("Alert notifications");
        JFXCheckBox checkNewsLetters = new JFXCheckBox("Email notifications");
       
       	//Add a separator (a line) in between 
	//checkboxes and close button
        Separator separator = new Separator();
        
        JFXButton buttonClose = new JFXButton("Close");
        
        buttonClose.setOnAction( e -> {
            drawersStack.toggle(rightDrawerNotifications);
        });
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(
                        checkShipment,
                        checkAlerts,
                        checkNewsLetters,
                        separator,
                        buttonClose
        );
        
        gridPaneNotifications.getChildren().addAll(vBox);
        return gridPaneNotifications;
    }
            
    public GridPane getGridSettings(JFXDrawersStack drawersStack, JFXDrawer rightDrawerSettings, JFXDrawer rightDrawerNotifications)
    {
    	/*
		Returns the grid for the settings pane 

	*/
        GridPane gridPaneSettings = new GridPane();
        
        JFXButton buttonSignOut = new JFXButton("Sign Out");
        JFXButton buttonNotifications = new JFXButton("Notifications");
        
       	//Add a separator (a line) in between 
	//checkboxes and close button
        Separator seperator = new Separator();
        
        JFXButton buttonClose = new JFXButton("Close");
        
        buttonSignOut.setOnAction(e -> {
            Login login = new Login();
            HashMap<Stage, BorderPane> mapStPn = login.makeScene(primaryStage);
            Stage stage = (Stage)mapStPn.keySet().toArray()[0];
            primaryStage.getScene().setRoot(mapStPn.get(stage));
        });
        buttonNotifications.setOnAction( e -> {
	    // Toggle the notification drawer
            drawersStack.toggle(rightDrawerNotifications);
        });
        buttonClose.setOnAction( e -> {
	    // Toggle the settings  drawer
            drawersStack.toggle(rightDrawerSettings);
        });
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(buttonSignOut, 
                                  buttonNotifications,
                                  seperator,
                                  buttonClose                         
                );
        
        gridPaneSettings.getChildren().addAll(vBox);
        return gridPaneSettings;
    }
    
    public void clientsTabScene()  {

        
    	try {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");

    	Label labelTracking = new Label("Track your current order here!");
    	
    	/*query here for all user */
        st=primaryConn.createStatement();
        ResultSet myRs=st.executeQuery("select * from users");
		//myStmt.executeQuery("CREATE TABLE IF NOT EXISTS `test` (id int ) ");
        System.out.println("query executed");
		while(myRs.next() ) {
			String n=myRs.getString("username");
			String e=myRs.getString("email");
			String p=myRs.getString("phone");
			String loc=myRs.getString("sourceaddress");
			HomePageAdmin.list_clients.add(new User(n,e,"",loc,p,null));
			
			System.out.println(  "name: "+n/*(new java.util.Date(myRs.getTimestamp("created_at").getTime())).toString()*/ );
		}
		HomePageAdmin.data_clients= FXCollections.observableArrayList(HomePageAdmin.list_clients);
    	TableView<User> table = new TableView<>();
    	
        
        final HBox hb = new HBox();
    	  	
    	final Label label = new Label("Details of all Clients ");
    	label.setFont(new Font("Arial", 20));

    	//table.setEditable(true);

    	TableColumn firstNameCol = new TableColumn("First Name");
    	firstNameCol.setMinWidth(100);
    	firstNameCol.setCellValueFactory(
    			new PropertyValueFactory<>("username"));

    	TableColumn phoneCol = new TableColumn("phone");
    	phoneCol.setMinWidth(100);
    	phoneCol.setCellValueFactory(
    			new PropertyValueFactory<>("phone"));

    	TableColumn emailCol = new TableColumn("Email");
    	emailCol.setMinWidth(200);
    	emailCol.setCellValueFactory(
    			new PropertyValueFactory<>("email"));
    	
    	TableColumn addrCol = new TableColumn("Address");
    	addrCol.setMinWidth(200);
    	addrCol.setCellValueFactory(
    			new PropertyValueFactory<>("sourceAddress"));

    	table.setItems(HomePageAdmin.data_clients);
    	table.getColumns().addAll(firstNameCol, phoneCol, emailCol,addrCol);
    	

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	
    	
    	vbox.getChildren().addAll(label,table, hb);

    	/*make a grid pane and add all vbox */
    	gPane.getChildren().addAll(vbox);
    	clients.setContent(table);
    	
    	} catch(Exception e) {
    		
    	}
    }
    
    public void packetEstimationTabScene() {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");
    	Label labelTracking = new Label("Track your current order here!");
    	
    	TableView<Order> table = new TableView<>();
    	
    	/*query db for the list of all orders of every user*/
    	try{
    		st=primaryConn.createStatement();
            ResultSet myRs=st.executeQuery("select * from Orders where assigned=0 and paid = 0");
    		//myStmt.executeQuery("CREATE TABLE IF NOT EXISTS `test` (id int ) ");
            System.out.println("query executed");
    		while(myRs.next() ) {
    			String s=myRs.getString("source");String d=myRs.getString("destination");
    			String dtype=myRs.getString("deliveryType");
    			String details=myRs.getString("details");
    			int pr=0;
    			int id=myRs.getInt("orderid");int u_id=myRs.getInt("userid");
    			Order o=new Order(s,d,dtype,details,primaryConn);
    			o.setOrderid(id);
    			o.setUserid(u_id);
    			HomePageAdmin.list_orders.add(o);
    		}
    		HomePageAdmin.data_order=FXCollections.observableArrayList(list_orders);
	    	
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	final HBox hb = new HBox();
    	final Label label = new Label("Details of all Orders, edit the packet cost ");
    	label.setFont(new Font("Arial", 20));

    	table.setEditable(true);

    	TableColumn<Order,String> sourceCol = new TableColumn<>("Source");
    	sourceCol.setMinWidth(200);
    	sourceCol.setCellValueFactory(
    			new PropertyValueFactory<>("source"));
    	

    	TableColumn<Order,String> destCol = new TableColumn<>("Destination");
    	destCol.setMinWidth(200);
    	destCol.setCellValueFactory(
    			new PropertyValueFactory<>("destination"));
    	
    	TableColumn<Order,String> delTypeCol = new TableColumn<>("Delivery Type");
    	delTypeCol.setMinWidth(150);
    	delTypeCol.setCellValueFactory(
    			new PropertyValueFactory<>("deliveryType"));
    	
    	TableColumn<Order,String> detCol = new TableColumn<>("Details");
    	detCol.setMinWidth(250);
    	detCol.setCellValueFactory(
    			new PropertyValueFactory<>("details"));
    	
    	TableColumn<Order, Integer> typeCountCol=new TableColumn("Counts of each Object Types");
    	TableColumn fragileCol = new TableColumn("Fragile");
    	fragileCol.setMinWidth(50);
    	fragileCol.setCellValueFactory(new PropertyValueFactory<>("fragileCount"));
    	TableColumn<Order, Integer> durableCol = new TableColumn("Durable");
    	durableCol.setMinWidth(50);
    	durableCol.setCellValueFactory(new PropertyValueFactory<>("durableCount"));
    	TableColumn<Order, Integer> otherCol = new TableColumn("Other");
    	otherCol.setMinWidth(50);
    	otherCol.setCellValueFactory(new PropertyValueFactory<>("otherCount"));

    	typeCountCol.getColumns().addAll(fragileCol, durableCol, otherCol );

    	TableColumn<Order,Integer> priceCol = new TableColumn<>("Price");
    	priceCol.setMinWidth(200);
    	priceCol.setCellValueFactory(
    			new PropertyValueFactory<>("price"));
    	priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	priceCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Order, Integer>>() {
    			@Override
    			public void handle(CellEditEvent<Order, Integer> t) {
    				(t.getTableView().getItems().get(
        					t.getTablePosition().getRow())
        					).setPrice(t.getNewValue());
    				Order o=(t.getTableView().getItems().get(
        					t.getTablePosition().getRow())
        					);
    				
    				/* assign this packet to any available employee
    				 * if any employee is unavailable then bring a pop-up showing all employees are busy.
    				 *  */
    				try {
    				String sql="select * from employee where available=1";
    				ResultSet rs = st.executeQuery(sql);
    				int e_id=-1;String e_name="";
    			      //STEP 5: Extract data from result set
    				while(rs.next()){
    					//Retrieve by column name
    					e_id  = rs.getInt("employeeid");
    					e_name=rs.getString("employeename");
    					break;
    				}
    			    if(e_id!=-1) {
    			    	/*insert query in tracking*/
    			    	pStmt=primaryConn.prepareStatement("insert into tracking(userid,orderid,employeeid,trackingdetail) values (?,?,?,?)");
    			    	System.out.println("inserting in tracking - userid: "+o.getUserid());
        		        pStmt.setInt(1, o.getUserid());
        		        pStmt.setInt(2, o.getOrderid());
        		        pStmt.setInt(3, e_id);
        		        pStmt.setString(4, "dispatched");
        				pStmt.executeUpdate();
        				
        				pStmt=primaryConn.prepareStatement("update userhistory set price = ? where orderid = ? ;");
        				pStmt.setInt(1,t.getNewValue());
        				pStmt.setInt(2,o.getOrderid());
        				pStmt.executeUpdate();
        				
        				pStmt=primaryConn.prepareStatement("update Orders set assigned = 1 where orderid = ? ;");
        				pStmt.setInt(1,o.getOrderid());
        				pStmt.executeUpdate();
        				
        				pStmt=primaryConn.prepareStatement("update employee set available = 0 where employeeid = ? ;");
        				pStmt.setInt(1,e_id);
        				pStmt.executeUpdate();
        				
        				final Stage dialog = new Stage();
	                    dialog.initModality(Modality.APPLICATION_MODAL);
	                    dialog.initOwner(primaryStage);
	                    VBox dialogVbox = new VBox(20);
	                    dialogVbox.getChildren().add(new Text("Successfully alloted to employee: "+e_name));
	                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
	                    dialog.setScene(dialogScene);
	                    dialog.show();	                    
	                    return;
        				
    			    }else {
    			    	/*error that all employees are currently unavailable try later*/
    			    	final Stage dialog = new Stage();
	                    dialog.initModality(Modality.APPLICATION_MODAL);
	                    dialog.initOwner(primaryStage);
	                    VBox dialogVbox = new VBox(20);
	                    dialogVbox.getChildren().add(new Text("ERROR:\n All employees are currently unavailable, try later !!"));
	                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
	                    dialog.setScene(dialogScene);
	                    dialog.show();
	                    return;
    			    }
    				}catch(Exception e) {e.printStackTrace();}
    				}
    			
    		}
    	);

    	table.setItems(HomePageAdmin.data_order);
    	table.getColumns().addAll(sourceCol,destCol,delTypeCol,detCol,typeCountCol,priceCol);

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	
    	vbox.getChildren().addAll(label, table, hb);


    	gPane.getChildren().addAll(vbox);
    	packetEstimation.setContent(table);
    }
    
    /*----------------------------------------------------------------------------------------------------------------------------------*/
    
    public void addEmpTabScene() {
    	GridPane gPane = sampleGridPane();
    	gPane.getStyleClass().add("grid-pane");

    	Label labelTracking = new Label("Add Employees here");


    	TableView<Employee> table = new TableView<>();
    	
    	/*query db for the list of all orders of every user*/
		try {
			ResultSet myRs = st.executeQuery("select * from employee ");
			while(myRs.next() ) {
				String s=myRs.getString("employeename");String p=myRs.getString("password");
				String ph=myRs.getString("phone");
				String addr=myRs.getString("address");
				Employee e=new Employee(s,ph,addr,myRs.getBoolean("available"),null);
				HomePageAdmin.list_employee.add(e);
				//System.out.println(  "name: "+n/*(new java.util.Date(myRs.getTimestamp("created_at").getTime())).toString()*/ );
			}
			HomePageAdmin.data_emp=FXCollections.observableArrayList(list_employee);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
    	final HBox hb = new HBox();
    	final Label label = new Label("Adding and Deleting Employee ");
    	label.setFont(new Font("Arial", 20));

    	table.setEditable(true);

    	TableColumn<Employee,String> sourceCol = new TableColumn<>("Employee Name");
    	sourceCol.setMinWidth(250);
    	sourceCol.setCellValueFactory(
    			new PropertyValueFactory<>("employeename"));
    	sourceCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	sourceCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				String temp=t.getNewValue();
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setEmployeename(temp);
    				System.out.println("employee name changed: "+temp);
    			}
    		}
    	);
    	
    	TableColumn<Employee,String> passCol = new TableColumn<>("Password");
    	passCol.setMinWidth(250);
    	passCol.setCellValueFactory(
    			new PropertyValueFactory<>("password"));
    	passCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	passCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setPassword(t.getNewValue());
    			}
    		}
    	);

    	TableColumn<Employee,String> destCol = new TableColumn<>("Phone");
    	destCol.setMinWidth(250);
    	destCol.setCellValueFactory(
    			new PropertyValueFactory<>("phone"));
    	destCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	destCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				((Employee)t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setPhone(t.getNewValue());
    			}
    		}
    	);
    	
    	TableColumn<Employee,String> addrCol = new TableColumn<>("Address");
    	addrCol.setMinWidth(250);
    	addrCol.setCellValueFactory(
    			new PropertyValueFactory<>("address"));
    	addrCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	addrCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, String>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, String> t) {
    				(t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setAddress(t.getNewValue());
    			}
    		}
    	);
    	
    	TableColumn<Employee,Boolean> detCol = new TableColumn<>("Available");
    	detCol.setMinWidth(250);
    	detCol.setCellValueFactory(
    			new PropertyValueFactory<>("available"));
    	detCol.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter() ));
    	detCol.setOnEditCommit(
    		new EventHandler<CellEditEvent<Employee, Boolean>>() {
    			@Override
    			public void handle(CellEditEvent<Employee, Boolean> t) {
    				(t.getTableView().getItems().get(
    					t.getTablePosition().getRow())
    					).setAvailable(t.getNewValue());
    			}
    		}
    	);

    	table.setItems(HomePageAdmin.data_emp);
    	table.getColumns().addAll(sourceCol,passCol,destCol,addrCol,detCol);

    	final TextField e_name_tf = new TextField();
    	e_name_tf.setPromptText("Name");
    	e_name_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField pass_tf = new TextField();
    	pass_tf.setPromptText("password");
    	pass_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField phone_tf = new TextField();
    	phone_tf.setPromptText("Phone");
    	phone_tf.setMaxWidth(sourceCol.getPrefWidth());
    	final TextField addr_tf = new TextField();
    	addr_tf.setPromptText("Address");
    	addr_tf.setMaxWidth(sourceCol.getPrefWidth());
    	


    	/* uncomment if adding row needed */
    	final Button addButton = new Button("Add");
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if( (e_name_tf.getText().toString().length()==0) || (pass_tf.getText().toString().length()==0) ||
    				(phone_tf.getText().toString().length()==0) || (addr_tf.getText().toString().length()==0) ) {
	    				final Stage dialog = new Stage();
	                    dialog.initModality(Modality.APPLICATION_MODAL);
	                    dialog.initOwner(primaryStage);
	                    VBox dialogVbox = new VBox(20);
	                    dialogVbox.getChildren().add(new Text("ERROR:\n Enter all the fields for Employee"));
	                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
	                    dialog.setScene(dialogScene);
	                    dialog.show();
	                    return;				
    			}
    			HomePageAdmin.data_emp.add(new Employee(e_name_tf.getText().toString(), phone_tf.getText().toString(), addr_tf.getText().toString(),
    						true,primaryConn));
    			try{
    		    	st=primaryConn.createStatement();
    		        pStmt=primaryConn.prepareStatement("insert into employee(employeename,password,phone,address,available) values (?,?,?,?,?)");
    		        pStmt.setString(1, e_name_tf.getText().toString());
    		        pStmt.setString(2, pass_tf.getText().toString());
    		        pStmt.setString(3, phone_tf.getText().toString());
    		        pStmt.setString(4, addr_tf.getText().toString());
    		        pStmt.setBoolean(5, true);
    				pStmt.executeUpdate();
    				
    	    	}catch (Exception ee) {
    	    		ee.printStackTrace();
    	    	}
    			
    			e_name_tf.clear();
    			pass_tf.clear();
    			phone_tf.clear();
    			addr_tf.clear();
    		}
    	});

    	hb.getChildren().addAll(e_name_tf,pass_tf,phone_tf,addr_tf,addButton);
    	hb.setSpacing(3);

    	final VBox vbox = new VBox();
    	vbox.setSpacing(5);
    	vbox.setPadding(new Insets(10, 0, 0, 10));
    	
    	vbox.getChildren().addAll(label , table, hb);


    	gPane.getChildren().addAll(vbox);
    	addEmp.setContent(gPane);
    }
    
    public BorderPane makeScene(Stage newStage,Connection mConn)
    {
    	System.out.println("inside makeScene of homepageadmin");
    	try {			
			System.out.println("making conn with server");
			
			primaryConn=mConn;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	/*
		This function creates the whole scene
		Also this function is called from different
		Classes to switch between scenes
	*/
	//Important assignment, this 
	//assignment preserves only one window
        primaryStage = newStage;
        BorderPane borderPane = new BorderPane();
        
        //newOrder = new Tab("New Order");
        //history = new Tab("History");
        //tracking = new Tab("Tracking");
        clients = new Tab("Clients");
        packetEstimation=new Tab("Packet Estimation");
        addEmp=new Tab("Add Employee");
        
        clientsTabScene();
        packetEstimationTabScene();
        addEmpTabScene();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        tabPane.getTabs().addAll( clients, packetEstimation,addEmp);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener( (ov, oldTab, newTab) -> {
                System.out.println(oldTab.getText() + " changed to " + newTab.getText());
		//Switch tab when the corresponding tab is pressed.
                tabPane.getSelectionModel().select(newTab);
        });        
        
        
        JFXButton buttonSettings = new JFXButton("Settings ");
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(buttonSettings);
        
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(buttonSettings, 6.0);
        AnchorPane.setRightAnchor(buttonSettings, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);

        anchorPane.getChildren().addAll(tabPane, buttonSettings);
        
	// Add the settings drawer
        JFXDrawer rightDrawerSettings = new JFXDrawer();
        JFXDrawer rightDrawerNotifications = new JFXDrawer();
        StackPane rightDrawerPaneSettings = new StackPane();
        rightDrawerPaneSettings.getStyleClass().add("blue-400");
        
        rightDrawerSettings.setDirection(DrawerDirection.RIGHT);
        rightDrawerSettings.setDefaultDrawerSize(200);
        rightDrawerSettings.setSidePane(rightDrawerPaneSettings);
        rightDrawerSettings.setOverLayVisible(false);
        rightDrawerSettings.setResizableOnDrag(true);
        
	//Add the notifications drawer
        StackPane rightDrawerPaneNotifications = new StackPane();
        rightDrawerNotifications.setDirection(DrawerDirection.RIGHT);
        rightDrawerNotifications.setDefaultDrawerSize(200);
        rightDrawerNotifications.setSidePane(rightDrawerPaneNotifications);
        rightDrawerNotifications.setOverLayVisible(false);
        rightDrawerNotifications.setResizableOnDrag(true);

        
        JFXDrawersStack drawersStack = new JFXDrawersStack();
                
        drawersStack.setContent(anchorPane);
        GridPane gridSettings = getGridSettings(drawersStack, rightDrawerSettings, rightDrawerNotifications);
        GridPane gridNotifications = getGridNotifications(drawersStack, rightDrawerNotifications);
        rightDrawerPaneSettings.getChildren().add(gridSettings);
        rightDrawerPaneNotifications.getChildren().add(gridNotifications);
            
        buttonSettings.setOnAction(e -> {
            drawersStack.toggle(rightDrawerSettings);
        });

        borderPane.setCenter(drawersStack);
        System.out.println("end of makeScene of homepageadmin");
        return borderPane;
    }
    
    public static void refresh_clients() throws SQLException {
    	
    }
    public static void refresh_orders() throws SQLException {
    	
    	
    }
    public static void refresh_employee() throws SQLException {
    	
		
    }
    
    
    @Override
    public void start(Stage stage) {
    	/*
  		The stage argument is the one that
		creates the window and should be passed
		along different scenes using makeScene's argument
	*/
    	
        primaryStage = stage;
        borderPane = makeScene(primaryStage,primaryConn);
        mapStagePane.put(primaryStage, borderPane);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
           launch(args);
    }
}
