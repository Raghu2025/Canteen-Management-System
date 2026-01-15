/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package canteen_management_system;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.controller.CustomerController;
import canteen_management_system.controller.FoodItemController;
import canteen_management_system.controller.UserController;
import canteen_management_system.enums.Role;
import canteen_management_system.view.Login;

/**
 *
 * @author User
 */
public class CanteenManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        addDummyData();
        Login login = new Login();
        login.setVisible(true);
        System.out.println("canteen_management_system.CanteenManagementSystem.main()");
    }
    
    public static void addDummyData() {
        CategoryController categoryController = new CategoryController();
        FoodItemController foodItemController = new FoodItemController();
        UserController userController = new UserController();
        CustomerController customerController = new CustomerController();
        
        categoryController.addCategory("Drinks", "");
        categoryController.addCategory("Snacks", "");
        categoryController.addCategory("Fast Food", "");
        categoryController.addCategory("Desserts", "");
        categoryController.addCategory("Fruits", "");

        foodItemController.addFoodItem("Coca Cola", categoryController.getAllCategoryList().get(0), 1.50, 50, "Chilled soft drink");
        foodItemController.addFoodItem("Potato Chips", categoryController.getAllCategoryList().get(1), 2.00, 40, "Crispy salted chips");
        foodItemController.addFoodItem("Burger", categoryController.getAllCategoryList().get(2), 5.99, 30, "Beef burger with cheese");
        foodItemController.addFoodItem("Chocolate Cake", categoryController.getAllCategoryList().get(3), 4.50, 20, "Rich chocolate dessert");
        foodItemController.addFoodItem("Apple", categoryController.getAllCategoryList().get(4), 0.80, 100, "Fresh red apples");

        // Add sample users
        userController.addUser("Admin User","admin@example.com","admin123","1234567890",Role.ADMIN.toString());
        userController.addUser("John Doe","john@example.com","john123","654321",Role.SALES_PERSON.toString());
        userController.addUser("Jane Smith","jane@example.com","jane123","1112223333",Role.SALES_PERSON.toString());
        userController.addUser("Manager Mike","mike@example.com","mike123","4445556666",Role.ADMIN.toString());
        userController.addUser("Alice Brown","alice@example.com","alice123","7778889999",Role.SALES_PERSON.toString());

        customerController.addCustomer("John Doe", 500);
        customerController.addCustomer("Alice Smith", 1200);
        customerController.addCustomer("Michael Brown", 300);
        customerController.addCustomer("Sophia Johnson", 750);
        customerController.addCustomer("David Wilson", 0);
    }
    
}
