/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.CategoryModel;
import canteen_management_system.systemData.CategoryData;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CategoryController {
    
    
    public CategoryController() {
    }
    
    public String addCategory(String name, String description) {
        LinkedList<CategoryModel> allCategory = CategoryData.getAllCategory();
        for (CategoryModel c : allCategory) {
            if (c.getCategoryName().equalsIgnoreCase(name.trim())) {
                return "Category already exists!";
            }
        }
        int newId = allCategory.size() + 1;
        CategoryData.addCategory(new CategoryModel(newId, name, description));
        return "Successfully added";
    }
    
    public LinkedList<CategoryModel> getAllCategoryList(){
        return CategoryData.getAllCategory();
    }

    public boolean updateCategory(CategoryModel updatedCategory) {
        LinkedList<CategoryModel> allCategory = CategoryData.getAllCategory();
        for (CategoryModel c : allCategory) {
            if (c.getCategoryName().equalsIgnoreCase(updatedCategory.getCategoryName().trim())) {
                return false;
            }
        }
        return CategoryData.updateCategory(updatedCategory);
    }
    
    public boolean deleteCategory(int id){
        return CategoryData.deleteCategory(id);
    }
    
    public static CategoryModel findById(int id) {
        for (CategoryModel c : CategoryData.getAllCategory()) {
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
