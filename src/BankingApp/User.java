/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String mobile;
    private String country;
    private String accountNumber;
    private String password;
    private double balance;
    private List<String> transactionHistory;

    public User(String firstName, String lastName, String mobile, String country, 
                String accountNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.country = country;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return firstName + lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public String toDataString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(",")
          .append(lastName).append(",")
          .append(mobile).append(",")
          .append(country).append(",")
          .append(accountNumber).append(",")
          .append(password).append(",")
          .append(balance);
        
        for (String transaction : transactionHistory) {
            sb.append(";").append(transaction);
        }
        
        return sb.toString();
    }

    public static User fromDataString(String data) {
        String[] parts = data.split(",", 7);
        if (parts.length < 7) return null;
        
        User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        
        String balanceAndTransactions = parts[6];
        String[] balanceParts = balanceAndTransactions.split(";", 2);
        
        try {
            double balance = Double.parseDouble(balanceParts[0]);
            user.setBalance(balance);
            
            if (balanceParts.length > 1) {
                String[] transactions = balanceParts[1].split(";");
                for (String transaction : transactions) {
                    if (!transaction.isEmpty()) {
                        user.addTransaction(transaction);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing balance: " + balanceParts[0]);
            return null;
        }
        
        return user;
    }
}