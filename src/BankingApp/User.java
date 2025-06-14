/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;


public class User {
    private String firstName;
    private String lastName;
    private String mobile;
    private String country;
    private String accountNumber;

    public User(String firstName, String lastName, String mobile, String country, String accountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.country = country;
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return firstName + lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String toDataString() {
        return firstName + "," + lastName + "," + mobile + "," + country + "," + accountNumber;
    }

    public static User fromDataString(String data) {
        String[] parts = data.split(",");
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}

