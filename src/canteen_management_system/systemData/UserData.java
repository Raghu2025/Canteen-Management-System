/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.systemData;

import canteen_management_system.model.UserModel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class UserData {

    private static LinkedList<UserModel> userList = new LinkedList<>();

    public static UserModel addUser(UserModel user) {
        userList.add(user);
        return user;
    }

    public static LinkedList<UserModel> getAllUser() {
        return userList;
    }

    public static boolean updateUser(UserModel user) {
        for (int i = 0; i < userList.size(); i++) {
            UserModel existing = userList.get(i);
            if (existing.getId() == user.getId()) {
                userList.set(i, user);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUser(int userId) {
        Iterator<UserModel> iterator = userList.iterator();
        while (iterator.hasNext()) {
            UserModel user = iterator.next();
            if (user.getId() == userId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
