/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canteen_management_system.systemData;

import canteen_management_system.model.CustomerModel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CustomerData {

    private static LinkedList<CustomerModel> customerList = new LinkedList<>();

    public static CustomerModel addCustomer(CustomerModel customer) {
        customerList.add(customer);
        return customer;
    }

    public static LinkedList<CustomerModel> getAllCustomer() {
        return customerList;
    }

    public static boolean updateCustomer(CustomerModel customer) {
        for (int i = 0; i < customerList.size(); i++) {
            CustomerModel existing = customerList.get(i);
            if (existing.getId() == customer.getId()) {
                customerList.set(i, customer);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteCustomer(int customerId) {
        Iterator<CustomerModel> iterator = customerList.iterator();
        while (iterator.hasNext()) {
            CustomerModel customer = iterator.next();
            if (customer.getId() == customerId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public static CustomerModel findById(int customerId) {
        for (CustomerModel customer : customerList) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }
}
