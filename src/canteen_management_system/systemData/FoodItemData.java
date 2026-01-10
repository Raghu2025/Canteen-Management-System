/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.systemData;

import canteen_management_system.model.FoodItemModel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class FoodItemData {

    private static LinkedList<FoodItemModel> foodItemList = new LinkedList<>();

    public static FoodItemModel addFoodItem(FoodItemModel FoodItem) {
        foodItemList.add(FoodItem);
        return FoodItem;
    }

    public static LinkedList<FoodItemModel> getAllFoodItem() {
        return foodItemList;
    }

    public static boolean updateFoodItem(FoodItemModel updatedFoodItem) {
        for (int i = 0; i < foodItemList.size(); i++) {
            FoodItemModel existing = foodItemList.get(i);
            if (existing.getId() == updatedFoodItem.getId()) {
                foodItemList.set(i, updatedFoodItem);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteFoodItem(int FoodItemId) {
        Iterator<FoodItemModel> iterator = foodItemList.iterator();
        while (iterator.hasNext()) {
            FoodItemModel FoodItem = iterator.next();
            if (FoodItem.getId() == FoodItemId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    
}
