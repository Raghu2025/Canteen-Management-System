/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

import canteen_management_system.model.CategoryModel;
import java.util.LinkedList;
import java.util.Iterator;

public class CategoryData {
    private static LinkedList<CategoryModel> categoryList = new LinkedList<>();

    public static CategoryModel addCategory(CategoryModel category) {
        categoryList.add(category);
        return category;
    }

    public static LinkedList<CategoryModel> getAllCategory() {
        return categoryList;
    }

    public static boolean updateCategory(CategoryModel updatedCategory) {
        for (int i = 0; i < categoryList.size(); i++) {
            CategoryModel existing = categoryList.get(i);
            if (existing.getId() == updatedCategory.getId()) {
                categoryList.set(i, updatedCategory);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteCategory(int categoryId) {
        Iterator<CategoryModel> iterator = categoryList.iterator();
        while (iterator.hasNext()) {
            CategoryModel category = iterator.next();
            if (category.getId() == categoryId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
