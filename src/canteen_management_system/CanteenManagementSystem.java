/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package canteen_management_system;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.view.Login;

/**
 *
 * @author User
 */
public class CanteenManagementSystem {
    private CategoryController Category = new CategoryController();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
        System.out.println("canteen_management_system.CanteenManagementSystem.main()");
        // TODO code application logic here
    }
    
}
