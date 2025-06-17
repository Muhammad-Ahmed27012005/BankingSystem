/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {
    public void start(Stage stage) {
        Label titleLabel = new Label("Login Page");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        
        TextField accountField = new TextField();
        accountField.setPromptText("Account Number");
        accountField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #0080ff; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #ff9999;");
        
        Button registerBtn = new Button("Don't have an account? Register");
        registerBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #0080ff; -fx-border-color: transparent;");
        
        loginBtn.setOnAction(e -> {
            String accountNum = accountField.getText().trim();
            String password = passwordField.getText();
            
            if (accountNum.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both fields.");
                return;
            }

            User user = UserDataHandler.getUser(accountNum, password);
            if (user != null) {
                Account account = new Account(user);
                stage.close();
                new Dashboard(new Stage(), account);
            } else {
                messageLabel.setText("Invalid account number or password.");
            }
        });
        
        registerBtn.setOnAction(e -> {
            stage.close();
            new RegistrationPage().start(new Stage());
        });

        VBox layout = new VBox(10, titleLabel, accountField, passwordField, 
                              loginBtn, messageLabel, registerBtn);
        layout.setStyle("-fx-padding: 50; -fx-background-color: #001a33;");
        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}