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

        Button registerBtn = new Button("Register");
        Button loginBtn = new Button("Login");
        
        registerBtn.setStyle("-fx-font-size: 14pt; -fx-pref-width: 200px;");
        loginBtn.setStyle("-fx-font-size: 14pt; -fx-pref-width: 200px;");
        
        registerBtn.setOnAction(e -> {
            new RegistrationPage().start(new Stage());
        });
        
        loginBtn.setOnAction(e -> {
            new LoginPage().start(new Stage());
        });

        VBox layout = new VBox(20, registerBtn, loginBtn);
        layout.setPadding(new Insets(30));
        
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}