/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package BankingApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking System");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(670);
        
        

        Button registerBtn = new Button("Register");
        Button loginBtn = new Button("Login");
        
        // Apply theme
        String buttonStyle = "-fx-background-color: #0080ff; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 400px;-fx-pref-height: 60px;";
        registerBtn.setStyle(buttonStyle);
        loginBtn.setStyle(buttonStyle);
        
        registerBtn.setOnAction(e -> new RegistrationPage().start(new Stage()));
        loginBtn.setOnAction(e -> new LoginPage().start(new Stage()));

        VBox layout = new VBox(40, registerBtn, loginBtn);
        layout.setPadding(new Insets(35));
        layout.setStyle("-fx-background-color: #001a33; -fx-alignment: center;");
        
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}