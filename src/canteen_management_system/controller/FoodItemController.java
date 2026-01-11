/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.CategoryModel;
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

    public String addFoodItem(String name, CategoryModel category, double price, int quantity, String description) {
        int newId = FoodItemData.getAllFoodItem().size() + 1;
        FoodItemData.addFoodItem(new FoodItemModel(newId, name, description, price, quantity, category));
        return "Food item successfully added!";
    }

    public LinkedList<FoodItemModel> getAllFoodItemList() {
        return FoodItemData.getAllFoodItem();
    }

    public boolean updateFoodItem(FoodItemModel updatedFoodItem) {
        return FoodItemData.updateFoodItem(updatedFoodItem);
    }

    public boolean deleteFoodItem(int id) {
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

}
