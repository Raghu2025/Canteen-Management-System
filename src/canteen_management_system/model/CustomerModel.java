/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.model;

/**
 *
 * @author User
 */
public class CustomerModel {

    private int id;
    private String fullName;
    private String contactNumber;
    private double balance;
    private boolean isRegistered = true;

    public CustomerModel(int id, String fullName, double balance) {
        this.id = id;
        this.fullName = fullName;
        this.balance = balance;
    }

    public CustomerModel(int id, String name) {
        this.fullName = name;
        this.contactNumber = null;
        this.balance = 0.0;
        this.isRegistered = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean updateBalance(double balance) {
        if (this.balance + balance < 0) {
            return false;
        }
        this.balance += balance;
        return true;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

}
