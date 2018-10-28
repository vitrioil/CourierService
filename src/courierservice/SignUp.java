/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import courierservice.Database.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 *
 * @author vitrioil
 */
public class SignUp extends Application{
    Tab login;
    BorderPane borderPane;
    Stage primaryStage;
    
    JFXTextField textFieldEmail;
    JFXTextField textFieldUserName;
    JFXPasswordField textFieldPassword;
    JFXPasswordField textFieldConfirmPassword;
    JFXTextField textFieldAddress;
    JFXTextField textFieldPhone;
    JFXButton buttonRegister;
    
    HBox hBoxAddress;
    JFXButton buttonHelp;
    JFXButton buttonExit;
    
    double buttonRadius = 15.0;
    
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
    Pattern  pattern = Pattern.compile(emailRegex);

    String urlConn="jdbc:mysql://localhost:3306/db2 ";
    String userConn="student";
    String passwordConn="student";
    
    Connection primaryConn;
    
    public BorderPane getRootPane()
    {
            return borderPane;
    }
    
    public SignUp()
    {
        borderPane = new BorderPane();
        
    }
    
    boolean verifySignUp(String userEmail,String userName,String userPassword, String userConfirmPassword,String userAddress)
    {
        
        
        if (userEmail.trim().isEmpty())
        {
            JFXPopup popup = Login.showPopup("Enter Email ID"); 
            popup.show(textFieldEmail, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if (userName.trim().isEmpty())
        {
            JFXPopup popup = Login.showPopup("Enter User name"); 
            popup.show(textFieldUserName, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if (userPassword.trim().isEmpty())
        {
            JFXPopup popup = Login.showPopup("Enter Password"); 
            popup.show(textFieldPassword, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if (userConfirmPassword.trim().isEmpty())
        {
           JFXPopup popup = Login.showPopup("Enter Confirm Password"); 
           popup.show(textFieldConfirmPassword, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
           return false;
        }
        if (userAddress.trim().isEmpty())
        {
           JFXPopup popup = Login.showPopup("Enter address"); 
           popup.show(textFieldAddress, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
           return false;
        }
        if (textFieldPhone.getText().trim().isEmpty())
        {
           JFXPopup popup = Login.showPopup("Enter Phone number"); 
           popup.show(textFieldPhone, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
           return false;
        }
        if(!userPassword.equals(userConfirmPassword))
        {
            JFXPopup popup = Login.showPopup("Passwords do not match");
            popup.show(textFieldConfirmPassword, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if(!pattern.matcher(userEmail).matches())
        {
            JFXPopup popup = Login.showPopup("Invalid email address");
            popup.show(textFieldEmail, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        return true;
    }
    
    public BorderPane makeScene(Stage newStage, Connection newConn)
    {
        primaryConn = newConn;
        primaryStage = newStage;
                //top right bottom left
        //borderPane.setPadding(new Insets(100, 100, 100, 100));

        GridPane gridPane = new GridPane();
        ColumnConstraints cConstraints = new ColumnConstraints();
        cConstraints.setHalignment(HPos.CENTER);
        cConstraints.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(cConstraints, cConstraints);
        gridPane.setPadding(new Insets(100, 100, 100, 100));     
        gridPane.setVgap(10);

        Label label = new Label("Courier Services");
        label.setStyle("-fx-background-color: #003333; -fx-text-fill: white");
        label.setPrefSize(2000, 50);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);

        Label labelRegister = new Label("Register");
        labelRegister.setMaxWidth(Double.MAX_VALUE);
        
        textFieldEmail = new JFXTextField();
        textFieldEmail.setPromptText("Email");
        textFieldEmail.setMaxWidth(Double.MAX_VALUE);

        textFieldUserName = new JFXTextField();
        textFieldUserName.setPromptText("User Name");
        textFieldUserName.setMaxWidth(Double.MAX_VALUE);        
        
        textFieldPassword = new JFXPasswordField();
        textFieldPassword.setPromptText("Password");
     
        textFieldConfirmPassword = new JFXPasswordField();
        textFieldConfirmPassword.setPromptText("Confirm Password");

        textFieldAddress = new JFXTextField();
        textFieldAddress.setPromptText("Source Address");
        textFieldAddress.setMaxWidth(Double.MAX_VALUE);
        textFieldAddress.setPrefWidth(500);

        textFieldPhone = new JFXTextField();
        textFieldPhone.setPromptText("Phone number");
        textFieldPhone.setMaxWidth(Double.MAX_VALUE);
        textFieldPhone.setPrefWidth(500);
        textFieldPhone.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, 
            String newValue) {
            if (!newValue.matches("\\d*")) {
                textFieldPhone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }
        });
                   
        buttonHelp = new JFXButton("?");
        buttonHelp.setShape(new Circle(buttonRadius));
        buttonHelp.setMinSize(2*buttonRadius, 2*buttonRadius);
        buttonHelp.setMaxSize(2*buttonRadius, 2*buttonRadius);
        
        hBoxAddress = new HBox();
        hBoxAddress.setSpacing(10);
        hBoxAddress.getChildren().addAll(textFieldAddress, buttonHelp);
        
        GridPane gridPaneHelpAddress = new GridPane();
        Label labelHelpAddress = new Label("Source address can be modified while ordering");
        labelHelpAddress.setWrapText(true);
        gridPaneHelpAddress.getChildren().addAll(labelHelpAddress);
        JFXPopup popupAddress = new JFXPopup(gridPaneHelpAddress);
        
        buttonHelp.setOnAction(e -> {
                popupAddress.show(textFieldAddress, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
        });
        
        buttonRegister = new JFXButton("Register");
        buttonExit = new JFXButton("Exit");
        buttonRegister.setOnAction(e -> {
                String userEmail = textFieldEmail.getText();
                String userName = textFieldUserName.getText();
                String userPassword = textFieldPassword.getText();
                String userConfirmPassword = textFieldConfirmPassword.getText();
                String userAddress = textFieldAddress.getText();
                String userPhone = textFieldPhone.getText();
                
                boolean signUpEnter = verifySignUp(userEmail,userName,userPassword,userConfirmPassword,userAddress);
                User user;
                try{
                    user = new User(userName, userEmail,userPassword,userAddress,userPhone,primaryConn);
                    if (signUpEnter)
                    {
                        user.insertUser();
                        HomePage homePage = new HomePage();
                        primaryStage.getScene().setRoot(homePage.makeScene(newStage, user, primaryConn));
                    }
                }
                catch(Exception exc){
                    exc.printStackTrace();
                }
                    
        });
        
        buttonExit.setOnAction(e -> {
            Stage stage = (Stage) buttonExit.getScene().getWindow();
            stage.close();
        });
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        
        VBox.setVgrow(textFieldUserName, Priority.ALWAYS);
        VBox.setVgrow(textFieldPassword, Priority.ALWAYS);
        VBox.setVgrow(buttonRegister, Priority.ALWAYS);
        
        vBox.getChildren().addAll(labelRegister,
                        textFieldEmail,
                        textFieldUserName,
                        textFieldPassword,
                        textFieldConfirmPassword,
                        hBoxAddress,
                        textFieldPhone,
                        buttonRegister
        );
        
        gridPane.getChildren().addAll(vBox);
        borderPane.setTop(label);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttonExit);
        
        return borderPane;
    }
    
    @Override
    public void start(Stage stage) { 
        primaryStage = stage;
        borderPane = makeScene(primaryStage, primaryConn);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
           launch(args);
    }
}
