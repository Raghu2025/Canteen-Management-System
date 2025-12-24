/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

/**
 *
 * @author User
 */
public class OrderItemModel {

    private int id;
    private FoodItemModel foodItem;
    private int quantity;

    public OrderItemModel(int id, FoodItemModel foodItem, int quantity) {
        this.id = id;
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FoodItemModel getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemModel foodItem) {
        this.foodItem = foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean updateQuantity(int quantity) {
        if (this.quantity + quantity < 0) {
            return false;
        }
        this.quantity += quantity;
        return true;
    }

}
