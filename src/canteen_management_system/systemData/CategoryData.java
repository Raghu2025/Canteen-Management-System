/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.systemData;

import canteen_management_system.model.CategoryModel;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CategoryData {
    private static LinkedList<CategoryModel> categoryList = new LinkedList<CategoryModel>();
    
    public static CategoryModel addCategory(CategoryModel category) {
        categoryList.add(category);
        return category;
    }
    
    public static LinkedList<CategoryModel> getAllCategory() {
        return categoryList;
    }
}
