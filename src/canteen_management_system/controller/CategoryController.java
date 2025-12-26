/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.CategoryModel;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CategoryController {
    private LinkedList<CategoryModel> categoryList = new LinkedList<CategoryModel>();
    
    public CategoryController() {
    }
    
    public LinkedList<CategoryModel> getAllCategoryList(){
        return categoryList;
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
