/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

import java.util.LinkedList;
import java.util.Date;

/**
 *
 * @author User
 */
public class OrderModel {
    private int id;
    private CustomerModel customer = null;
    private LinkedList<OrderItemModel> orderItems = new LinkedList<>();
    private double total = 0;
    Date createdDate;
    private String paymentMethod;
    private String orderStatus;
    private Date paidDate;

    public OrderModel(int id) {
        this.id = id;
        this.createdDate = new Date();
        this.orderStatus = "PENDING";
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

    public LinkedList<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    // Enqueue - Add item to the end of the queue
    public void enqueue(OrderItemModel order) {
        int ifExistIndex = -1;
        for (int i = 0; i < this.orderItems.size(); i++) {
            if (order.getFoodItem().getId() == this.orderItems.get(i).getFoodItem().getId()) {
                ifExistIndex = i;
                break;
            }
        }
        if (ifExistIndex >= 0) {
            this.orderItems.get(ifExistIndex).setQuantity(
                    this.orderItems.get(ifExistIndex).getQuantity() + 1
            );
        } else {
            this.orderItems.addLast(order); // Add to end of queue
        }
        this.setTotal();
    }

    // Dequeue - Remove and return item from the front of the queue
    public OrderItemModel dequeue() {
        if (this.orderItems.isEmpty()) {
            return null;
        }
        OrderItemModel removed = this.orderItems.removeFirst(); // Remove from front of queue
        this.setTotal();
        return removed;
    }

    // Peek - View the front item without removing it
    public OrderItemModel peek() {
        if (this.orderItems.isEmpty()) {
            return null;
        }
        return this.orderItems.getFirst();
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return this.orderItems.isEmpty();
    }

    // Get queue size
    public int size() {
        return this.orderItems.size();
    }

    // Legacy method for backward compatibility
    public void setOrderItems(OrderItemModel order) {
        enqueue(order);
    }

    public boolean deleteLastItems() {
        if (this.orderItems.isEmpty()) {
            return false;
        }
        this.orderItems.removeLast();
        this.setTotal();
        return true;
    }

    public boolean removeOrderItemById(int orderItemId) {
        for (int i = 0; i < this.orderItems.size(); i++) {
            if (this.orderItems.get(i).getId() == orderItemId) {
                this.orderItems.remove(i);
                this.setTotal(); // recalculate total
                return true; // removed successfully
            }
        }
        return false; // item not found
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        double tempTotal = 0;
        for (OrderItemModel item : orderItems) {
            tempTotal += item.getTotal();
        }
        this.total = tempTotal;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void markAsPaid() {
        this.paidDate = new Date();
        this.orderStatus = "PAID";
    }
}
