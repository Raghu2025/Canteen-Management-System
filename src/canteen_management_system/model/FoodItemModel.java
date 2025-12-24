/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

/**
 *
 * @author User
 */
public class FoodItemModel {

    private int id;
    private String foodItemName;
    private String description;
    private double price;
    private int quantity;
    private boolean available;
    private CategoryModel category;

    public FoodItemModel(int id, String foodItemName, String description, double price, int quantity, CategoryModel category) {
        this.id = id;
        this.foodItemName = foodItemName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.setAvailable();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.setAvailable();
    }

    public boolean updateQuantity(int quantity) {
        if(this.quantity + quantity < 0) return false; 
        this.quantity += quantity;
        return true;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable() {
        this.available = this.quantity > 0;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

}
