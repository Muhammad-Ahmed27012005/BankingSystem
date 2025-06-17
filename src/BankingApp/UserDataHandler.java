/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankingApp;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public static User getUserByAccountNumber(String accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromDataString(line);
                if (user != null && user.getAccountNumber().equals(accountNumber)) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromDataString(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void updateUserBalance(User updatedUser) {
        try {
            File inputFile = new File(FILE_PATH);
            File tempFile = new File("temp_users.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromDataString(line);
                if (user != null && user.getAccountNumber().equals(updatedUser.getAccountNumber())) {
                    writer.write(updatedUser.toDataString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            writer.close();
            reader.close();

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}