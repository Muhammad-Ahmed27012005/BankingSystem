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
        root.setStyle("-fx-background-color: white;");

        Label welcomeLabel = new Label("Welcome, " + account.getAccountHolderName());
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #005BAC;");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button transactionBtn = new Button("Transaction");
        Button balanceBtn = new Button("Check Balance");

        for (Button btn : new Button[]{depositBtn, withdrawBtn, transactionBtn, balanceBtn}) {
            btn.setPrefWidth(200);
            btn.setStyle("-fx-background-color: #005BAC; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        depositBtn.setOnAction(e -> showDepositDialog());
        withdrawBtn.setOnAction(e -> showWithdrawDialog());
        transactionBtn.setOnAction(e -> showTransactionDialog());
        balanceBtn.setOnAction(e -> showBalanceDialog());

        VBox buttonBox = new VBox(15, depositBtn, withdrawBtn, transactionBtn, balanceBtn);
        buttonBox.setPadding(new Insets(10));

        root.getChildren().addAll(welcomeLabel, buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDepositDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter deposit amount:");
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

        Label nameLabel = new Label("Recipient Name:");
        TextField nameField = new TextField();

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        VBox content = new VBox(10, nameLabel, nameField, amountLabel, amountField);
        dialog.getDialogPane().setContent(content);
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
        alert.showAndWait();
    }
}

