/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.FoodItemModel;
import canteen_management_system.systemData.FoodItemData;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class FoodItemController {

    public FoodItemController() {
    }

public String addFoodItem(String name, String category, String priceStr, String quantityStr, String description) {
    name = name.trim();
    category = category.trim();
    description = description.trim();

    if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
        return "Please fill in all required fields!";
    }

    LinkedList<FoodItemModel> allFoodItem = FoodItemData.getAllFoodItem();
    for (FoodItemModel c : allFoodItem) {
        if (c.getFoodItemName().equalsIgnoreCase(name)) {
            return "Food item already exists!";
        }
    }

    double price;
    int quantity;
    try {
        price = Double.parseDouble(priceStr);
        if (price < 0) return "Price cannot be negative!";
    } catch (NumberFormatException e) {
        return "Invalid price! Please enter a number.";
    }

    try {
        quantity = Integer.parseInt(quantityStr);
        if (quantity < 0) return "Quantity cannot be negative!";
    } catch (NumberFormatException e) {
        return "Invalid quantity! Please enter a whole number.";
    }

    // 4️⃣ Generate new ID (safe even if deletions occurred)
    int newId = allFoodItem.stream().mapToInt(FoodItemModel::getId).max().orElse(0) + 1;

    // 5️⃣ Add new food item
    FoodItemData.addFoodItem(new FoodItemModel(newId, name, category, price, quantity, description));

    return "Food item successfully added!";
}


    public LinkedList<FoodItemModel> getAllFoodItemList() {
        return FoodItemData.getAllFoodItem();
    }

    public boolean updateCategory(FoodItemModel updatedCategory) {
        LinkedList<FoodItemModel> allCategory = FoodItemData.getAllFoodItem();
        for (FoodItemModel c : allCategory) {
            if (c.getFoodItemName().equalsIgnoreCase(updatedCategory.getCategoryName().trim())) {
                return false;
            }
        }
        return FoodItemData.updateFoodItem(updatedCategory);
    }

    public boolean deleteCategory(int id) {
        return FoodItemData.deleteFoodItem(id);
    }

    public static FoodItemModel findById(int id) {
        for (FoodItemModel c : FoodItemData.getAllFoodItem()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
//    public String[][] getTableValue(){
//        String[] header = {"category Name", "Description"};
//        String[][] row = new String;
//        
//       
//        
//       return {header, row};
//    }

}
