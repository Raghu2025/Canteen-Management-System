/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author User
 */
public class OrderModel {
    
    private int id;
    private CustomerModel customer;
    private ArrayList<OrderItemModel> orderItems = new ArrayList();
    private double total;
    Date createdDate;
    
    public OrderModel(int id, CustomerModel customer, double total) {
        this.id = id;
        this.customer = customer;
        this.createdDate = new Date();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public CustomerModel getCustomer() {
        return customer;
    }
    
    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }
    
    public ArrayList<OrderItemModel> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(OrderItemModel order) {
        int ifExistindex = -1;
        for (int i = 0; i < this.orderItems.size(); i++) {
            if (order.getFoodItem().getId() == this.orderItems.get(i).getFoodItem().getId()) {
                ifExistindex = i;
                break;
            }
        }
        if (ifExistindex >= 0) {
            this.orderItems.add(order);
        } else {
            this.orderItems.get(ifExistindex).setQuantity(1);
        }
        this.setTotal();
    }
    
    public boolean deleteLastItems() {
        if (this.orderItems.size() == 0) {
            return false;
        }
        this.orderItems.remove(this.orderItems.size() - 1);
        return true;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal() {
        double tempTotal = 0;
        for (OrderItemModel item : orderItems) {
            tempTotal += item.getQuantity() * item.getFoodItem().getPrice();
        }
        this.total = tempTotal;
    }
    
}
