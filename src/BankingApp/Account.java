/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

public class Account {
    private String accountHolderName;
    private double balance;
    private User user;
    private String accountNumber;

    public Account(User user) {
        this.user = user;
        this.accountHolderName = user.getUsername();
        this.balance = user.getBalance();
        this.accountNumber = user.getAccountNumber();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            user.setBalance(balance);
            user.addTransaction(String.format("Deposit: $%.2f", amount));
            UserDataHandler.updateUserBalance(user);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            user.setBalance(balance);
            user.addTransaction(String.format("Withdrawal: $%.2f", amount));
            UserDataHandler.updateUserBalance(user);
            return true;
        }
        return false;
    }

    public boolean transact(String recipientAccountNumber, double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        User recipient = UserDataHandler.getUserByAccountNumber(recipientAccountNumber);
        if (recipient == null) {
            return false;
        }

        this.balance -= amount;
        user.setBalance(balance);
        
        recipient.setBalance(recipient.getBalance() + amount);
        
        String senderTransaction = String.format("Sent $%.2f to %s (%s)", amount, 
            recipient.getUsername(), recipientAccountNumber);
        String recipientTransaction = String.format("Received $%.2f from %s (%s)", amount, 
            this.accountHolderName, this.accountNumber);
        
        user.addTransaction(senderTransaction);
        recipient.addTransaction(recipientTransaction);
        
        UserDataHandler.updateUserBalance(user);
        UserDataHandler.updateUserBalance(recipient);
        
        return true;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public User getUser() {
        return user;
    }
}