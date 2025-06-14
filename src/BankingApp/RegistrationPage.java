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
        Label titleLabel = new Label("Registration Form");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField mobileField = new TextField();
        mobileField.setPromptText("Mobile Number");

        TextField countryField = new TextField();
        countryField.setPromptText("Country");

        Button registerBtn = new Button("Register");

        Label messageLabel = new Label();

        registerBtn.setOnAction(e -> {
            String fName = firstNameField.getText();
            String lName = lastNameField.getText();
            String mobile = mobileField.getText();
            String country = countryField.getText();

            if (fName.isEmpty() || lName.isEmpty() || mobile.isEmpty() || country.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            String accountNumber = UUID.randomUUID().toString().substring(0, 8);
            User user = new User(fName, lName, mobile, country, accountNumber);
            UserDataHandler.saveUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Registration Complete");
            alert.setContentText("Your account number is: " + accountNumber + "\nUsername: " + user.getUsername());
            alert.showAndWait();

            stage.close();
            new LoginPage().start(new Stage());
        });

        VBox layout = new VBox(10, titleLabel, firstNameField, lastNameField, mobileField, countryField, registerBtn, messageLabel);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }


}