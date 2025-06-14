/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import BankingApp.Account;
import BankingApp.Dashboard;

import javafx.scene.layout.VBox;

public class LoginPage {
    public void start(Stage stage) {
        Label titleLabel = new Label("Login Page");

        TextField accountField = new TextField();
        accountField.setPromptText("Account Number");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        Button loginBtn = new Button("Login");
        Label messageLabel = new Label();

        loginBtn.setOnAction(e -> {
            String accountNum = accountField.getText();
            String username = usernameField.getText();

            User user = UserDataHandler.getUser(accountNum, username);
            if (user != null) {
                // Create account and open dashboard
                Account account = new Account(user.getUsername());
                stage.close();
                new Dashboard(new Stage(), account);
            } else {
                messageLabel.setText("Login failed. Please try again.");
            }
        });

        VBox layout = new VBox(10, titleLabel, accountField, usernameField, loginBtn, messageLabel);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }


}
