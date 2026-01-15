/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.controller;

import canteen_management_system.model.CustomerModel;
import canteen_management_system.model.CustomerData;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CustomerController {
    
    public String addCustomer(String fullName, double balance) {
        int newId = CustomerData.getAllCustomer().size() + 1;
        CustomerData.addCustomer(new CustomerModel(newId, fullName, balance));
        return "Successfully added";
    }
    
    public CustomerModel addCustomer(String fullName,String phone) {
        int newId = CustomerData.getAllCustomer().size() + 1;
        CustomerModel cus = new CustomerModel(newId, fullName, phone);
        CustomerData.addCustomer(cus);
        return cus;
    }

    public LinkedList<CustomerModel> getAllCustomer() {
        return CustomerData.getAllCustomer();
    }

    
    public boolean updateCustomer(CustomerModel updatedCustomer) {
        return CustomerData.updateCustomer(updatedCustomer);
    }

    public boolean deleteCustomer(int id) {
        return CustomerData.deleteCustomer(id);
    }

    public static CustomerModel findById(int id) {
        for (CustomerModel c : CustomerData.getAllCustomer()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }


    public static CustomerModel findByName(String name) {
        for (CustomerModel c : CustomerData.getAllCustomer()) {
            if (c.getFullName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
