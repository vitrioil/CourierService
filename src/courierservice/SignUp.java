/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courierservice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vitrioil
 */
public class SignUp extends Application{
    Tab login;
    BorderPane borderPane;
    Stage primaryStage;
    
    public BorderPane getRootPane()
    {
            return borderPane;
    }
    
    public SignUp()
    {
        borderPane = new BorderPane();
    }
    
    public BorderPane makeScene(Stage newStage)
    {
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
//        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setMaxWidth(Double.MAX_VALUE);

        Label labelRegister = new Label("Register");
        labelRegister.setMaxWidth(Double.MAX_VALUE);
        
        JFXTextField textFieldEmail = new JFXTextField();
        textFieldEmail.setPromptText("Email");
        textFieldEmail.setMaxWidth(Double.MAX_VALUE);

        JFXTextField textFieldUserName = new JFXTextField();
        textFieldUserName.setPromptText("User Name");
        textFieldUserName.setMaxWidth(Double.MAX_VALUE);        
        
        JFXPasswordField textFieldPassword = new JFXPasswordField();
        textFieldPassword.setPromptText("Password");
     
        JFXPasswordField textFieldConfirmPassword = new JFXPasswordField();
        textFieldConfirmPassword.setPromptText("Confirm Password");

        JFXTextField textFieldAddress = new JFXTextField();
        textFieldAddress.setPromptText("Source Address");
        textFieldAddress.setMaxWidth(Double.MAX_VALUE);
        
        JFXButton buttonRegister = new JFXButton("Register");
        
        buttonRegister.setOnAction(e -> {
                //Validate here
                HomePage homePage = new HomePage();
                primaryStage.getScene().setRoot(homePage.makeScene(newStage));
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
                        textFieldAddress,
                        buttonRegister
        );
        
        gridPane.getChildren().addAll(vBox);
        borderPane.setTop(label);
        borderPane.setCenter(gridPane);
        return borderPane;
    }
    
    @Override
    public void start(Stage stage) { 
        primaryStage = stage;
        borderPane = makeScene(primaryStage);
        Scene scene = new Scene(borderPane, 500, 500);
        scene.getStylesheets().add(HomePage.class.getResource("HomePage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
           launch(args);
    }
}
