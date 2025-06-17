/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
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
        Button transactionBtn = new Button("Transfer");
        Button balanceBtn = new Button("Check Balance");
        Button historyBtn = new Button("Transaction History");
        Button logoutBtn = new Button("Logout");

        String buttonStyle = "-fx-background-color: #0080ff; -fx-text-fill: white; -fx-font-weight: bold;";
        for (Button btn : new Button[]{depositBtn, withdrawBtn, transactionBtn, balanceBtn, historyBtn, logoutBtn}) {
            btn.setStyle(buttonStyle);
            btn.setPrefWidth(200);
        }

        depositBtn.setOnAction(e -> showDepositDialog());
        withdrawBtn.setOnAction(e -> showWithdrawDialog());
        transactionBtn.setOnAction(e -> showTransactionDialog());
        balanceBtn.setOnAction(e -> showBalanceDialog());
        historyBtn.setOnAction(e -> showTransactionHistory());
        logoutBtn.setOnAction(e -> {
            primaryStage.close();
            new LoginPage().start(new Stage());
        });

        VBox buttonBox = new VBox(15, depositBtn, withdrawBtn, transactionBtn, balanceBtn, historyBtn, logoutBtn);
        buttonBox.setPadding(new Insets(20));

        root.getChildren().addAll(welcomeLabel, buttonBox);

        Scene scene = new Scene(root, 1280, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
        dialog.setTitle("Transfer Money");
        dialog.getDialogPane().setStyle("-fx-background-color: #001a33;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Label recipientLabel = new Label("Recipient Account:");
        recipientLabel.setStyle("-fx-text-fill: white;");
        
        ComboBox<String> accountCombo = new ComboBox<>();
        accountCombo.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        List<String> accountNumbers = new ArrayList<>();
        for (User user : UserDataHandler.getAllUsers()) {
            if (!user.getAccountNumber().equals(account.getAccountNumber())) {
                accountNumbers.add(user.getAccountNumber() + " - " + user.getUsername());
            }
        }
        accountCombo.setItems(FXCollections.observableArrayList(accountNumbers));

        Label amountLabel = new Label("Amount:");
        amountLabel.setStyle("-fx-text-fill: white;");
        TextField amountField = new TextField();
        amountField.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");

        grid.add(recipientLabel, 0, 0);
        grid.add(accountCombo, 1, 0);
        grid.add(amountLabel, 0, 1);
        grid.add(amountField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    String selected = accountCombo.getSelectionModel().getSelectedItem();
                    if (selected == null || selected.isEmpty()) {
                        showAlert("Error", "Please select a recipient account.");
                        return null;
                    }
                    
                    String recipientAccountNumber = selected.split(" ")[0];
                    double amount = Double.parseDouble(amountField.getText());
                    
                    if (account.transact(recipientAccountNumber, amount)) {
                        showAlert("Success", String.format(
                            "Transferred $%.2f to account %s", 
                            amount, recipientAccountNumber));
                    } else {
                        showAlert("Failed", "Transaction failed. Check balance or account number.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid amount.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showTransactionHistory() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Transaction History");
        dialog.getDialogPane().setStyle("-fx-background-color: #001a33;");

        ListView<String> listView = new ListView<>();
        listView.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        listView.getItems().addAll(account.getUser().getTransactionHistory());

        if (listView.getItems().isEmpty()) {
            listView.getItems().add("No transactions yet");
        }

        dialog.getDialogPane().setContent(listView);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
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
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #001a33;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white;");
        
        alert.showAndWait();
    }
}