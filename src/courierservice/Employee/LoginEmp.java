/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import courierservice.Database.Employee;
import courierservice.Database.UserNotFoundException;
import courierservice.HomePage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vitrioil
 */
public class LoginEmp extends Application{
    
    Tab login;
    //Similar to homepage create a borderpane that stores the layout
    //Stage that stores the window info
    BorderPane borderPane;
    Stage primaryStage;
    //Hashmap to pass mapping from stage to borderpane
    HashMap<Stage, BorderPane> mapStagePane = new HashMap<Stage, BorderPane>();
    
    JFXTextField textFieldEmail;
    JFXPasswordField textFieldPassword;
    JFXButton buttonLogin;
    JFXButton buttonSign;
    JFXButton buttonExit;
    
    Connection primaryConn;
    
    String urlConn="jdbc:mysql://localhost:3306/Courier";
    String userConn="vitrioil";
    String passwordConn="vitrioil";
    
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$";
    Pattern  pattern = Pattern.compile(emailRegex);

            
    static JFXPopup showPopup(String message)
    {
        GridPane gridPane = new GridPane();
        Label labelMessage = new Label(message);
        gridPane.add(labelMessage, 0, 0);
        gridPane.setStyle("-fx-background-color: red;");
        JFXPopup popup = new JFXPopup(gridPane);
        return popup;
    }
    
    boolean verifyLogin(String userEmail, String password)
    {
        System.out.println("Username:"+userEmail+" welcome");
        if(!pattern.matcher(userEmail).matches())
        {
            JFXPopup popup = showPopup("Invalid email address");
            popup.show(textFieldEmail, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if (userEmail.trim().isEmpty())
        {
            JFXPopup popup = showPopup("No user name entered");
            popup.show(textFieldEmail, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
            return false;
        }
        if(password.trim().isEmpty())
        {
          JFXPopup popup = showPopup("Please enter password");
          popup.show(textFieldPassword, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
          return false;
        }
        return true;
    }
    
    public HashMap<Stage, BorderPane>  makeScene(Stage newStage)
    {
    	/*
		Make scene is used to create the scene 
		and pass the borderpane
	*/
	//Important assignment that preserves the same window
        primaryStage = newStage;
        borderPane = new BorderPane();
                                        //top right bottom left
        //borderPane.setPadding(new Insets(100, 100, 100, 100));

        GridPane gridPane = new GridPane();
        ColumnConstraints cConstraints = new ColumnConstraints();
        cConstraints.setHalignment(HPos.CENTER);
        cConstraints.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(cConstraints, cConstraints);
        gridPane.setPadding(new Insets(100, 100, 100, 100));     
        gridPane.setVgap(10);

        Label label = new Label("Courier Services::Employee");
        label.setStyle("-fx-background-color: #003333; -fx-text-fill: white");
        label.setPrefSize(2000, 50);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);

        Label labelLogin = new Label("Log in");
        labelLogin.setMaxWidth(Double.MAX_VALUE);
        
        textFieldEmail = new JFXTextField();
        textFieldEmail.setPromptText("Email");
        textFieldEmail.setMaxWidth(Double.MAX_VALUE);
        
        textFieldPassword = new JFXPasswordField();
        textFieldPassword.setPromptText("Password");
        
        buttonLogin = new JFXButton("Log in");
        
        buttonExit = new JFXButton("Exit");
        
        buttonLogin.setOnAction( e -> {
                String employeeEmail = textFieldEmail.getText();
                String employeePassword = textFieldPassword.getText();
                boolean loginEnter = verifyLogin(employeeEmail, employeePassword);
                
                // verify password
                if (loginEnter)
                {
                    Employee employee;
                    try{
                        employee = new Employee(employeeEmail, employeePassword, primaryConn);
                        HomePageEmp homePage = new HomePageEmp();
                        primaryStage.getScene().setRoot(homePage.makeScene(primaryStage, employee, primaryConn));
                    }
                    catch(UserNotFoundException uExc)
                    {
                        JFXPopup errPopup = showPopup(uExc.getMessage());
                        errPopup.show(textFieldEmail,  JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                    }
                    catch(Exception exc){
                        exc.printStackTrace();
                    }
                    
                }
                
        });
        
        buttonExit.setOnAction(e-> {
            Stage stage = (Stage) buttonExit.getScene().getWindow();
            stage.close();
        });
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        
        VBox.setVgrow(textFieldEmail, Priority.ALWAYS);
        VBox.setVgrow(textFieldPassword, Priority.ALWAYS);
        VBox.setVgrow(buttonLogin, Priority.ALWAYS);
        
        vBox.getChildren().addAll(labelLogin,
                    textFieldEmail,
                    textFieldPassword,
                    buttonLogin
        );
        gridPane.getChildren().addAll(vBox);
        borderPane.setTop(label);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttonExit);
        mapStagePane.clear();
        mapStagePane.put(newStage, borderPane);
        return mapStagePane;
    }
    
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            primaryConn=DriverManager.getConnection(urlConn,userConn,passwordConn);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        mapStagePane = makeScene(primaryStage);
        Scene scene = new Scene(mapStagePane.get(primaryStage), 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
           launch(args);
    }
}
