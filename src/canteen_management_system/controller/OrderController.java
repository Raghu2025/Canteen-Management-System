/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;


import canteen_management_system.model.FoodItemModel;
import canteen_management_system.model.OrderItemModel;
import canteen_management_system.model.OrderModel;

/**
 *
 * @author User
 */
public class OrderController {
    private static int top = -1;
    private static final int INITIAL_CAPACITY = 10;
    private static OrderModel[] stack = new OrderModel[INITIAL_CAPACITY];
    private FoodItemController foodController = new FoodItemController();

    public OrderController() {
    }

    private static void push(OrderModel order) {
        if (top == stack.length - 1) {
            resize();
        }
        stack[++top] = order;
    }

    private static OrderModel pop() {
        if (isEmpty()) {
            return null;
        }
        OrderModel order = stack[top];
        stack[top--] = null;
        return order;
    }

    private static OrderModel get(int index) {
        if (index < 0 || index > top) {
            return null;
        }
        return stack[index];
    }

    private static void set(int index, OrderModel order) {
        if (index >= 0 && index <= top) {
            stack[index] = order;
        }
    }

    private static OrderModel remove(int index) {
        if (index < 0 || index > top) {
            return null;
        }
        OrderModel removed = stack[index];
        for (int i = index; i < top; i++) {
            stack[i] = stack[i + 1];
        }
        stack[top--] = null;
        return removed;
    }

    public static int size() {
        return top + 1;
    }

    private static boolean isEmpty() {
        return top == -1;
    }
    
    public static OrderModel peek() {
    if (isEmpty()) {
        return null;
    }
    return stack[top];
}

    private static void resize() {
        OrderModel[] newArray = new OrderModel[stack.length * 2];
        for (int i = 0; i < stack.length; i++) {
            newArray[i] = stack[i];
        }
        stack = newArray;
    }


    public String addOrder() {
        int newId = size() + 1;
        OrderModel newOrder = new OrderModel(newId);
        push(newOrder);
        return "Order successfully added!";
    }
    
    public String addOrderItem(int orderId, int foodId, int quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero";
        }
        OrderModel order = OrderController.findById(orderId);
        if (order == null) {
            return "Order not found";
        }

        FoodItemModel food = foodController.findById(foodId);
        if (food == null) {
            return "Food item not found";
        }
        for (OrderItemModel item : order.getOrderItems()) {
            if (item.getFoodItem().getId() == foodId) {
                item.setQuantity(item.getQuantity() + 1);
                order.setTotal(); 
                return null;
            }
        }
        int newId = order.getOrderItems().size() + 1;
        OrderItemModel newOrderItem = new OrderItemModel(newId, food, quantity);
        order.enqueue(newOrderItem);
        return null;
    }



    public boolean updateOrder(OrderModel updatedOrder) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getId() == updatedOrder.getId()) {
                set(i, updatedOrder);
                return true;
            }
        }
        return false;
    }

    public boolean deleteOrder(int id) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getId() == id) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public static OrderModel findById(int id) {
        for (int i = 0; i < size(); i++) {
            OrderModel order = get(i);
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }
}
