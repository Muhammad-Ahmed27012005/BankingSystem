/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;


import java.io.*;

public class UserDataHandler {
    private static final String FILE_PATH = "users.txt";

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toDataString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String accountNumber, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromDataString(line);
                if (user != null && 
                    user.getAccountNumber().equals(accountNumber) && 
                    user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}