/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;


import canteen_management_system.model.OrderModel;
import canteen_management_system.model.CustomerModel;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class OrderController {
    
    private static LinkedList<OrderModel> allOrders = new LinkedList<>();
    
    public OrderController() {
    }
    
    public String addOrder(CustomerModel customer, double total) {
        int newId = allOrders.size() + 1;
        OrderModel newOrder = new OrderModel(newId, customer, total);
        allOrders.add(newOrder);
        return "Order successfully added!";
    }
    
    public LinkedList<OrderModel> getAllOrderList() {
        return allOrders;
    }
    
    public boolean updateOrder(OrderModel updatedOrder) {
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getId() == updatedOrder.getId()) {
                allOrders.set(i, updatedOrder);
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteOrder(int id) {
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getId() == id) {
                allOrders.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static OrderModel findById(int id) {
        for (OrderModel order : allOrders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }
}
