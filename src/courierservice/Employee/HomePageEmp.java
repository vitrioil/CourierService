/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import courierservice.Database.Employee;
import courierservice.Database.Tracking;
import courierservice.HomePage;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vitrioil
 */
public class HomePageEmp extends Application{
    Stage primaryStage;
    Employee primaryEmployee;
    Connection primaryConn;
    
    BorderPane borderPane;
    
    Tab updateShipment, pendingPayment;
    
    int intMaxCharLimit = 200;
    
        
    public static GridPane sampleGridPane()
    {
        GridPane gridPane = new GridPane();
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        //Add a css class to so that all tabs will have the same effect 
        gridPane.getStyleClass().add("grid-pane");
        
        return gridPane;
    }
    
    static JFXPopup showPopup(String message)
    {
        GridPane gridPane = new GridPane();
        Label labelMessage = new Label(message);
        gridPane.add(labelMessage, 0, 0);
        gridPane.setStyle("-fx-background-color: red;");
        JFXPopup popup = new JFXPopup(gridPane);
        
        return popup;
    }
    
    void updateShipmentTabScene()
    {
        GridPane gridPane = sampleGridPane();

        JFXTextField textFieldOrderID = new JFXTextField();
        textFieldOrderID.setPromptText("Enter order ID");
        textFieldOrderID.setPrefWidth(500);
        
        JFXTextField textFieldUserID = new JFXTextField();
        textFieldUserID.setPromptText("Enter user ID");
        textFieldUserID.setPrefWidth(500);
        
        JFXTextField textFieldLocation = new JFXTextField();
        textFieldLocation.setPromptText("Location reached");
        textFieldLocation.setPrefWidth(500);
        
        JFXTextArea textAreaOtherDetails = new JFXTextArea();
        textAreaOtherDetails.setPrefSize(500, 50);
        textAreaOtherDetails.setMaxHeight(50);
        textAreaOtherDetails.setPromptText("Any other details");
        textAreaOtherDetails.setTextFormatter(new TextFormatter<String>(change -> 
                change.getControlNewText().length() <= intMaxCharLimit ? change : null));
        
        JFXCheckBox checkReached = new JFXCheckBox("Reached the destination");
        checkReached.setSelected(false);
        checkReached.setAllowIndeterminate(false);
        
        JFXButton buttonConfirm = new JFXButton("Confirm");
        
        buttonConfirm.setOnAction(e -> {
                boolean sendFlag = true;
               
                String userIDText = textFieldUserID.getText();
                String orderIDText = textFieldOrderID.getText();
                String location = textFieldLocation.getText();
                String otherDetails = textAreaOtherDetails.getText();
                LocalDateTime now = LocalDateTime.now();
                boolean reached = checkReached.isSelected();
                if (reached)
                {
                    location += "-> Reached!";
                }
                /*
                int hour = now.getHour();
                int minute = now.getMinute();
                String month = now.getMonth().toString();
                int day = now.getDayOfMonth();
                int year = now.getYear();
                */
                
                
                if (orderIDText.trim().isEmpty()) {
                    JFXPopup popup = showPopup("Order ID cannot be empty");
                    popup.show(textFieldOrderID, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                    sendFlag = false;
                }
                if (location.trim().isEmpty()) {
                    JFXPopup popup = showPopup("Location cannot be empty");
                    popup.show(textFieldLocation, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                    sendFlag = false;
                }
                if(sendFlag)
                {
                    System.out.println("Sending");
                    int userID = Integer.parseInt(userIDText);
                    int orderID = Integer.parseInt(orderIDText);
                    Tracking tracking = new Tracking(userID, orderID, primaryConn);
                    System.out.println(location);
                    tracking.setTrackingdetail(location);
                    tracking.setEmployeeid(primaryEmployee.getEmployeeid());
                    tracking.setReached(reached);
                    tracking.updateTracking();
                }
                
        });
        
        VBox vBox = new VBox(5);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(
                    textFieldOrderID,
                    textFieldUserID,
                    textFieldLocation,
                    checkReached,
                    textAreaOtherDetails,
                    buttonConfirm
        );
        
        gridPane.getChildren().addAll(vBox);
        
        updateShipment.setContent(gridPane);
    }
    
    ObservableList<Payment> getPendingPayment()
    {
        ObservableList<Payment> listPayments = FXCollections.observableArrayList(primaryEmployee.getPendingPayments());
        
        return listPayments;
    }
    
    void pendingPaymentTabScene()
    {
        GridPane gridPane = sampleGridPane();
        
        JFXTreeTableColumn<Payment, String> tableColumnOrderID = new JFXTreeTableColumn<>("Order ID");
        tableColumnOrderID.setPrefWidth(300);
        tableColumnOrderID.setStyle("-fx-background-color: black;-fx-text-fill:white");
        tableColumnOrderID.setCellValueFactory( (TreeTableColumn.CellDataFeatures<Payment, String> param) -> {
                if (tableColumnOrderID.validateValue(param)) {
                    return param.getValue().getValue().orderID;
            } else {
                return tableColumnOrderID.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Payment, String> tableColumnPrice = new JFXTreeTableColumn<>("Price");
        tableColumnPrice.setPrefWidth(300);
        tableColumnPrice.setStyle("-fx-background-color: black;-fx-text-fill:white");
        tableColumnPrice.setCellValueFactory( (TreeTableColumn.CellDataFeatures<Payment, String> param) -> {
            if (tableColumnPrice.validateValue(param)) {
                return param.getValue().getValue().price;
            } else {
                return tableColumnPrice.getComputedValue(param);
            }
        });
        
        JFXTreeTableColumn<Payment, String> tableColumnTime = new JFXTreeTableColumn<>("Time");
        tableColumnTime.setPrefWidth(300);
        tableColumnTime.setStyle("-fx-background-color: black;-fx-text-fill:white");
        tableColumnTime.setCellValueFactory( (TreeTableColumn.CellDataFeatures<Payment, String> param) -> {
                if (tableColumnTime.validateValue(param)) {
                    return param.getValue().getValue().time;
            } else {
                return tableColumnTime.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Payment, String> tableColumnCustomerID = new JFXTreeTableColumn<>("Customer ID");
        tableColumnCustomerID.setPrefWidth(300);
        tableColumnCustomerID.setStyle("-fx-background-color: black;-fx-text-fill:white");
        tableColumnCustomerID.setCellValueFactory( (TreeTableColumn.CellDataFeatures<Payment, String> param) -> {
            if (tableColumnCustomerID.validateValue(param)) {
                return param.getValue().getValue().customerID;
            } else {
                return tableColumnCustomerID.getComputedValue(param);
            }
        });
        
        TreeItem<Payment> root = new RecursiveTreeItem<Payment>(getPendingPayment(), RecursiveTreeObject::getChildren);
        
        JFXTreeTableView<Payment> tablePayment = new JFXTreeTableView<>(root);
        tablePayment.setPadding(new Insets(10, 10, 10, 10));
        tablePayment.setPrefWidth(500);
        tablePayment.getColumns().setAll(tableColumnOrderID, tableColumnPrice, tableColumnTime, tableColumnCustomerID);
        tablePayment.setShowRoot(false);
        tablePayment.setEditable(false);
        
        pendingPayment.setContent(tablePayment);
    }
    
    GridPane getGridSettings(JFXDrawersStack drawersStack,JFXDrawer rightDrawerSettings)
    {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        JFXButton buttonSignOut = new JFXButton("Sign Out");
        JFXButton buttonNotifications = new JFXButton("Notifications");
        
       	//Add a separator (a line) in between 
	//checkboxes and close button
        Separator seperator = new Separator();
        
        JFXButton buttonClose = new JFXButton("Close");
        
        buttonSignOut.setOnAction(e -> {
            LoginEmp login = new LoginEmp();
            HashMap<Stage, BorderPane> mapStPn = login.makeScene(primaryStage);
            Stage stage = (Stage)mapStPn.keySet().toArray()[0];
            primaryStage.getScene().setRoot(mapStPn.get(stage));
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
        
        gridPane.getChildren().addAll(vBox);
        
        return gridPane;
    }
    
    BorderPane makeScene(Stage newStage, Employee newEmployee, Connection newConnection)
    {
        primaryStage = newStage;
        primaryEmployee = newEmployee;
        primaryConn = newConnection;
        
        BorderPane borderPane = new BorderPane();
        
        updateShipment = new Tab("Update Shipment");
        pendingPayment = new Tab("Pending Payments");
        
        updateShipmentTabScene();
        pendingPaymentTabScene();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        tabPane.getTabs().addAll(updateShipment, pendingPayment);

        JFXButton buttonSettings = new JFXButton("Settings");
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(2));

        hBox.getChildren().addAll(buttonSettings);
       
        // Anchor pane is used to keep tabpane and settings button together
	// HBox cannot be used because button will occupy all space 
	// below it and it will remain empty
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
        
        rightDrawerSettings.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        rightDrawerSettings.setDefaultDrawerSize(200);
        rightDrawerSettings.setSidePane(rightDrawerPaneSettings);
        rightDrawerSettings.setOverLayVisible(false);
        rightDrawerSettings.setResizableOnDrag(true);
        
	//Add the notifications drawer
        StackPane rightDrawerPaneNotifications = new StackPane();
        rightDrawerNotifications.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        rightDrawerNotifications.setDefaultDrawerSize(200);
        rightDrawerNotifications.setSidePane(rightDrawerPaneNotifications);
        rightDrawerNotifications.setOverLayVisible(false);
        rightDrawerNotifications.setResizableOnDrag(true);

        
        JFXDrawersStack drawersStack = new JFXDrawersStack();
                
        drawersStack.setContent(anchorPane);
        GridPane gridSettings = getGridSettings(drawersStack, rightDrawerSettings);
       // GridPane gridNotifications = getGridNotifications(drawersStack, rightDrawerNotifications);
        rightDrawerPaneSettings.getChildren().add(gridSettings);
        //rightDrawerPaneNotifications.getChildren().add(gridNotifications);
            
        buttonSettings.setOnAction(e -> {
            drawersStack.toggle(rightDrawerSettings);
        });

        borderPane.setCenter(drawersStack);
        
        return borderPane;
        
    }
    
    @Override
    public void start(Stage stage) { 
        primaryStage = stage;
        borderPane = makeScene(primaryStage, primaryEmployee, primaryConn);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
           launch(args);
    }
}
