/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Dashboard {
    private final Account account;

    public Dashboard(Stage primaryStage, Account account) {
        this.account = account;

        primaryStage.setTitle("Banking Dashboard");
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #001a33;");

        Label welcomeLabel = new Label("Welcome, " + account.getAccountHolderName());
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button transactionBtn = new Button("Transaction");
        Button balanceBtn = new Button("Check Balance");
        Button logoutBtn = new Button("Logout");

        // Apply button styling
        String buttonStyle = "-fx-background-color: #0080ff; -fx-text-fill: white; -fx-font-weight: bold;";
        for (Button btn : new Button[]{depositBtn, withdrawBtn, transactionBtn, balanceBtn, logoutBtn}) {
            btn.setStyle(buttonStyle);
            btn.setPrefWidth(200);
        }

        // Set button actions
        depositBtn.setOnAction(e -> showDepositDialog());
        withdrawBtn.setOnAction(e -> showWithdrawDialog());
        transactionBtn.setOnAction(e -> showTransactionDialog());
        balanceBtn.setOnAction(e -> showBalanceDialog());
        logoutBtn.setOnAction(e -> {
            primaryStage.close();
            new LoginPage().start(new Stage());
        });

        VBox buttonBox = new VBox(15, depositBtn, withdrawBtn, transactionBtn, balanceBtn, logoutBtn);
        buttonBox.setPadding(new Insets(20));

        root.getChildren().addAll(welcomeLabel, buttonBox);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Dialog methods implementation
    private void showDepositDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter deposit amount:");
        dialog.getDialogPane().setStyle("-fx-background-color: #001a33;");
        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                account.deposit(amount);
                showAlert("Deposit Successful", "Amount deposited: $" + amount);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid amount.");
            }
        });
    }

    private void showWithdrawDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Enter amount to withdraw:");
        dialog.getDialogPane().setStyle("-fx-background-color: #001a33;");
        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (account.withdraw(amount)) {
                    showAlert("Withdrawal Successful", "Amount withdrawn: $" + amount);
                } else {
                    showAlert("Failed", "Insufficient balance.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid amount.");
            }
        });
    }

    private void showTransactionDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Transaction");
        dialog.getDialogPane().setStyle("-fx-background-color: #001a33;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Label nameLabel = new Label("Recipient Account:");
        nameLabel.setStyle("-fx-text-fill: white;");
        TextField nameField = new TextField();
        nameField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        Label amountLabel = new Label("Amount:");
        amountLabel.setStyle("-fx-text-fill: white;");
        TextField amountField = new TextField();
        amountField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(amountLabel, 0, 1);
        grid.add(amountField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    String recipient = nameField.getText().trim();
                    double amount = Double.parseDouble(amountField.getText());
                    if (account.transact(recipient, amount)) {
                        showAlert("Transaction Successful", "Sent $" + amount + " to " + recipient);
                    } else {
                        showAlert("Transaction Failed", "Insufficient balance.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Enter a valid amount.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showBalanceDialog() {
        showAlert("Current Balance", "Your current balance is: $" + account.getBalance());
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        
        // Apply dark theme to alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #001a33;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white;");
        
        alert.showAndWait();
    }
}