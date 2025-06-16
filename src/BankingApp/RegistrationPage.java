/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.UUID;

public class RegistrationPage {
    public void start(Stage stage) {
        // Create UI components
        Label titleLabel = new Label("Registration Form");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white;");
        
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        TextField mobileField = new TextField();
        mobileField.setPromptText("Mobile Number");
        mobileField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        TextField countryField = new TextField();
        countryField.setPromptText("Country");
        countryField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: #0080ff; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #ff9999;");

        // Registration handler
        registerBtn.setOnAction(e -> {
            String fName = firstNameField.getText().trim();
            String lName = lastNameField.getText().trim();
            String mobile = mobileField.getText().trim();
            String country = countryField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validation
            if (fName.isEmpty() || lName.isEmpty() || mobile.isEmpty() || 
                country.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            // Generate account number
            String accountNumber = "DAT" + UUID.randomUUID().toString().substring(0, 8);
            
            // Create and save user
            User user = new User(fName, lName, mobile, country, accountNumber, password);
            UserDataHandler.saveUser(user);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Registration Completed");
            alert.setContentText("Your Account number is: " + accountNumber + 
                               "\nUsername: " + user.getUsername());
            alert.showAndWait();

            stage.close();
            new LoginPage().start(new Stage());
        });

        // Layout
        VBox layout = new VBox(10, titleLabel, firstNameField, lastNameField, 
                              mobileField, countryField, passwordField, 
                              confirmPasswordField, registerBtn, messageLabel);
        layout.setStyle("-fx-padding: 40; -fx-background-color: #001a33;");
        Scene scene = new Scene(layout, 1280,720);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }
}