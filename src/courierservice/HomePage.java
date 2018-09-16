/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToggleGroup;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


/**
 *
 * @author Admin
 */
public class HomePage extends Application {
    
    Tab newOrder;
    Tab history;
    Tab tracking;
    
    JFXSnackbar snackbar;
    
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
        gridPane.getStyleClass().add("grid-pane");
        
        return gridPane;
    }
    
    
    public void newOrderTabScene()
    {
        GridPane gridPane = sampleGridPane();
        

        // Add address
        JFXTextField textFieldDestinationAddress = new JFXTextField();
        textFieldDestinationAddress.setPromptText("Destination Address");
        textFieldDestinationAddress.setPrefWidth(500);
        
        
        //Add type of package
        Label labelTypePackage = new Label("Type of package: ");
        labelTypePackage.setPadding(new Insets(10, 0, 0, 20));
        final ToggleGroup groupTypePackage = new ToggleGroup();

        JFXRadioButton radioFragileTypePackage = new JFXRadioButton("Fragile");
        radioFragileTypePackage.setPadding(new Insets(10));
        radioFragileTypePackage.setToggleGroup(groupTypePackage);

        JFXRadioButton radioDurableTypePackage = new JFXRadioButton("Durable");
        radioDurableTypePackage.setPadding(new Insets(10));
        radioDurableTypePackage.setToggleGroup(groupTypePackage);
        
        JFXRadioButton radioOtherTypePackage = new JFXRadioButton("Other");
        radioOtherTypePackage.setPadding(new Insets(10));
        radioOtherTypePackage.setToggleGroup(groupTypePackage);
        radioOtherTypePackage.setSelected(true);
        
        
        HBox hBoxRadioButtonsTypePackage = new HBox();
        hBoxRadioButtonsTypePackage.getChildren().addAll(radioFragileTypePackage, radioDurableTypePackage, radioOtherTypePackage);
        
        
        //Add type of delivery
        Label labelTypeDelivery = new Label("Type of delivery: ");
        labelTypeDelivery.setPadding(new Insets(10, 0, 0, 20));
        final ToggleGroup groupTypeDelivery = new ToggleGroup();

        JFXRadioButton radioNextDayTypeDelivery = new JFXRadioButton("Next day");
        radioNextDayTypeDelivery.setPadding(new Insets(10));
        radioNextDayTypeDelivery.setToggleGroup(groupTypeDelivery);

        JFXRadioButton radioSpeedTypeDelivery = new JFXRadioButton("Speed");
        radioSpeedTypeDelivery.setPadding(new Insets(10));
        radioSpeedTypeDelivery.setToggleGroup(groupTypeDelivery);
        
        JFXRadioButton radioNormalTypeDelivery = new JFXRadioButton("Normal");
        radioNormalTypeDelivery.setPadding(new Insets(10));
        radioNormalTypeDelivery.setToggleGroup(groupTypeDelivery);
        radioNormalTypeDelivery.setSelected(true);

        HBox hBoxRadioButtonsTypeDelivery = new HBox();
        hBoxRadioButtonsTypeDelivery.getChildren().addAll(radioNextDayTypeDelivery, radioSpeedTypeDelivery, radioNormalTypeDelivery);
        
        //Add everything to grid
        VBox vBox = new VBox(5);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(
                textFieldDestinationAddress, 
                labelTypePackage, 
                hBoxRadioButtonsTypePackage,
                labelTypeDelivery,
                hBoxRadioButtonsTypeDelivery
                );
        
        gridPane.getChildren().addAll(vBox);
        newOrder.setContent(gridPane);
    }
    
    public GridPane getOrderDetails(Order orderSelected, JFXButton showDetails)
    {
        
        GridPane gridOrderDetails = new GridPane();
        
        HashMap<String, Label> orderDetails = orderSelected.getDetails();
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        
        //Setting drawer pane
        
        for (HashMap.Entry< String,Label> m:orderDetails.entrySet())
        {
           vBox.getChildren().add(m.getValue());
        }
        
        
       
        gridOrderDetails.getChildren().addAll(vBox);
        return gridOrderDetails;
    }
    
    public void historyTabScene()
    {
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        Label labelHistory = new Label("Check your history of couriers here!");
        
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        orderList.add(new Order("Phone", "123456789", "1", 1, 2));
        orderList.add(new Order("Letter", "123456789", "2", 1, 3));
        orderList.add(new Order("Document", "123456789", "3", 2, 1));
        ListView<Order> listViewOrder = new ListView<Order>(orderList);
        listViewOrder.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        listViewOrder.setPrefSize(500, 200);

        JFXButton buttonGetDetails = new JFXButton("Find");        
        
        FlowPane content = new FlowPane();
        content.getChildren().addAll(labelHistory, listViewOrder, buttonGetDetails);
        content.setMaxSize(200, 200);
        
        JFXDrawer rightDrawer = new JFXDrawer();
        StackPane rightDrawerPane = new StackPane();
        rightDrawerPane.getStyleClass().add("blue-400");
        
        rightDrawer.setDirection(DrawerDirection.RIGHT);
        rightDrawer.setDefaultDrawerSize(150);
        rightDrawer.setSidePane(rightDrawerPane);
        rightDrawer.setOverLayVisible(false);
        rightDrawer.setResizableOnDrag(true);

        JFXDrawersStack drawersStack = new JFXDrawersStack();
        drawersStack.setContent(content);

        
        buttonGetDetails.setOnAction(e -> {
            System.out.println("Getting the order details");
            Order orderSelected = listViewOrder.getSelectionModel().getSelectedItem();
            GridPane gridShowOrderDetail = getOrderDetails(orderSelected, buttonGetDetails);
            
            rightDrawerPane.getChildren().removeAll();
            rightDrawerPane.getChildren().add(gridShowOrderDetail);
            drawersStack.toggle(rightDrawer);
//            JFXPopup popup = new JFXPopup(gridShowOrderDetail); 
//           popup.setPrefHeight(100);
//            popup.show(buttonGetDetails, PopupVPosition.TOP, PopupHPosition.LEFT);
        });
        
        
        gPane.getChildren().addAll(drawersStack);

        history.setContent(gPane);
    }
        
    public void trackingTabScene()
    {
        GridPane gPane = sampleGridPane();
        gPane.getStyleClass().add("grid-pane");

        VBox vBox = new VBox();
        gPane.getChildren().addAll(vBox);

        tracking.setContent(gPane);
    }
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        
        newOrder = new Tab("New Order");
        history = new Tab("History");
        tracking = new Tab("Tracking");
        
        newOrderTabScene();
        historyTabScene();
        trackingTabScene();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        String RIGHT="Settings";
        FlowPane content = new FlowPane();
        JFXButton settingsButton = new JFXButton(RIGHT);
        content.getChildren().addAll(settingsButton);
        content.setMaxSize(200, 200);

        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().addAll(tabPane);//, settingsButton);
        AnchorPane.setTopAnchor(settingsButton, 3.0);
        AnchorPane.setRightAnchor(settingsButton, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);

        tabPane.getTabs().addAll(newOrder, history, tracking);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener( (ov, oldTab, newTab) -> {
                System.out.println(oldTab.getText() + " changed to " + newTab.getText());
                tabPane.getSelectionModel().select(newTab);
        });
           
        
        JFXDrawer rightDrawer = new JFXDrawer();
        StackPane rightDrawerPane = new StackPane();
        rightDrawerPane.getStyleClass().add("blue-400");
        //Setting drawer pane
        
        
        rightDrawerPane.getChildren().add(new JFXButton("Right Content"));
        rightDrawer.setDirection(DrawerDirection.RIGHT);
        rightDrawer.setDefaultDrawerSize(150);
        rightDrawer.setSidePane(rightDrawerPane);
        rightDrawer.setOverLayVisible(false);
        rightDrawer.setResizableOnDrag(true);

        JFXDrawersStack drawersStack = new JFXDrawersStack();
        drawersStack.setContent(content);

        rightDrawer.setId(RIGHT);
        
        settingsButton.addEventHandler(MOUSE_PRESSED, e -> drawersStack.toggle(rightDrawer));

        borderPane.setCenter(anchor);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
           launch(args);
    }
}
