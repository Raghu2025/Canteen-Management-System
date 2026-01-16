/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.UserModel;
import canteen_management_system.model.UserData;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class UserController {
    public static UserModel authenticatedUser = null;

    public String addUser(String fullName, String email, String password, String phoneNumber, String role) {
        int newId = UserData.getAllUser().size() + 1;
        UserData.addUser(new UserModel(newId, fullName, email, password, phoneNumber, role));
        return "Successfully added";
    }
    
    public UserModel login(String email, String password) {
        if (email == null || password == null) {
            return null; // invalid input
        }

        for (UserModel user : getAllUser()) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                UserController.authenticatedUser = user;
                return user;
            }
        }
        return null;
    }

    public LinkedList<UserModel> getAllUser() {
        return UserData.getAllUser();
    }

    public boolean updateUser(UserModel updatedUser) {
        return UserData.updateUser(updatedUser);
    }

    public boolean deleteUser(int id) {
        return UserData.deleteUser(id);
    }

    public static UserModel findById(int id) {
        for (UserModel c : UserData.getAllUser()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean isEmailExists(String email, Integer userId) {
        for (UserModel user : getAllUser()) {
            // Skip the current user during update
            if (userId != null && user.getId() == userId) {
                continue;
            }
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static UserModel findByName(String name) {
        for (UserModel c : UserData.getAllUser()) {
            if (c.getFullName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
