/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package canteen_management_system.view;

import canteen_management_system.controller.CustomerController;
import canteen_management_system.controller.OrderController;
import canteen_management_system.model.CustomerModel;
import canteen_management_system.model.OrderModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author User
 */
public class PaymentView extends javax.swing.JPanel {

    CustomerModel selectedCustomer = null;
    private CustomerController customerController;
    private OrderModel currentOrder;
    private boolean paymentSuccess = false;

    /**
     * Creates new form PaymentView
     */
    private javax.swing.JRadioButton balanceRadio;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton cashRadio;
    private javax.swing.JLabel customerBalanceLabel;
    private javax.swing.JTextField customerIdField;
    private javax.swing.JLabel customerIdLabel;
    private javax.swing.JComboBox<String> customerListCombo;
    private javax.swing.JLabel customerListLabel;
    private javax.swing.JComboBox<String> customerTypeCombo;
    private javax.swing.JLabel customerTypeLabel;
    private javax.swing.JPanel customerTypePanel;
    private javax.swing.JTextField guestNameField;
    private javax.swing.JLabel guestNameLabel;
    private javax.swing.JPanel guestPanel;
    private javax.swing.JTextField guestPhoneField;
    private javax.swing.JLabel guestPhoneLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel paymentMethodLabel;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JButton proceedButton;
    private javax.swing.JPanel registeredPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel totalAmountLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JPanel totalPanel;
    private javax.swing.ButtonGroup paymentMethodGroup;

    private void customerTypeComboActionPerformed(java.awt.event.ActionEvent evt) {
        String selected = (String) customerTypeCombo.getSelectedItem();
        if ("Registered".equals(selected)) {
            showRegisteredCustomerPanel();
        }
    }

    private void showRegisteredCustomerPanel() {
        registeredPanel.setVisible(true);
        guestPanel.setVisible(false);
        balanceRadio.setEnabled(true);
        customerBalanceLabel.setText("0.00");
        selectedCustomer = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Payment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new PaymentView(OrderController.peek()));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Constructor with order
    public PaymentView(OrderModel order) {
        this.customerController = new CustomerController();
        this.currentOrder = order;
        initComponents();
        setupInitialState();
    }

    private void setupInitialState() {
        this.customerController.addCustomer("John Doe", 500);
        this.customerController.addCustomer("Alice Smith", 1200);
        this.customerController.addCustomer("Michael Brown", 300);
        this.customerController.addCustomer("Sophia Johnson", 750);
        this.customerController.addCustomer("David Wilson", 0);
        setLayout(new BorderLayout());
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(488, 60));
        headerPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel("Payment", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.PAGE_START);

//       MAIN FORM 
        mainFormWrapper = new JPanel(new GridBagLayout());
        mainFormWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

//        CUSTOMER TYPE
        customerTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerTypePanel.setBorder(BorderFactory.createTitledBorder("Customer Type"));

        customerTypeLabel = new JLabel("Select Type:");
        customerTypeCombo = new JComboBox<>(new String[]{"Registered", "Guest"});
        customerTypeCombo.setPreferredSize(new Dimension(300, 30));
        customerTypeCombo.addActionListener(e -> toggleCustomerPanels());

        customerTypePanel.add(customerTypeLabel);
        customerTypePanel.add(customerTypeCombo);

        gbc.gridy = 0;
        mainFormWrapper.add(customerTypePanel, gbc);

//      REGISTERED PANEL
        registeredPanel = new JPanel(new GridBagLayout());
        registeredPanel.setBorder(BorderFactory.createTitledBorder("Registered Customer"));
        GridBagConstraints rbc = new GridBagConstraints();
        rbc.insets = new Insets(5, 10, 5, 10);
        rbc.anchor = GridBagConstraints.WEST;

        customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField(10);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCustomerById();
            }
        });

        customerListLabel = new JLabel("Or Select:");
        customerListCombo = new JComboBox<>(new String[]{"Select Customer"});
        customerListCombo.addActionListener(e -> {
            String selectedName = (String) customerListCombo.getSelectedItem();
            if (selectedName != null && !selectedName.equals("Select Customer")) {
                System.out.println("Selected Customer: " + selectedName);
                CustomerModel customer = customerController.findByName(selectedName);
                if (customer != null) {
                    customerBalanceLabel.setText(Double.toString(customer.getBalance()));
                }
            } else {
                customerBalanceLabel.setText("0.00");
            }
        });
        System.out.println(customerController.getAllCustomer().size() + " Customere Size");
        for (CustomerModel customer : customerController.getAllCustomer()) {
            customerListCombo.addItem(customer.getFullName());
        }

        balanceLabel = new JLabel("Balance:");
        customerBalanceLabel = new JLabel("0.00");
        customerBalanceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        customerBalanceLabel.setForeground(new Color(76, 175, 80));

        rbc.gridx = 0;
        rbc.gridy = 0;
        registeredPanel.add(customerIdLabel, rbc);
        rbc.gridx = 1;
        registeredPanel.add(customerIdField, rbc);
        rbc.gridx = 2;
        registeredPanel.add(searchButton, rbc);

        rbc.gridx = 0;
        rbc.gridy = 1;
        registeredPanel.add(customerListLabel, rbc);
        rbc.gridx = 1;
        rbc.gridwidth = 2;
        registeredPanel.add(customerListCombo, rbc);

        rbc.gridx = 0;
        rbc.gridy = 2;
        rbc.gridwidth = 1;
        registeredPanel.add(balanceLabel, rbc);
        rbc.gridx = 1;
        registeredPanel.add(customerBalanceLabel, rbc);

        gbc.gridy = 1;
        mainFormWrapper.add(registeredPanel, gbc);

//        GUEST PANEL
        guestPanel = new JPanel(new GridBagLayout());
        guestPanel.setBorder(BorderFactory.createTitledBorder("Guest Customer"));
        GridBagConstraints gbcGuest = new GridBagConstraints();
        gbcGuest.insets = new Insets(10, 10, 10, 10);
        gbcGuest.anchor = GridBagConstraints.WEST;

        guestNameLabel = new JLabel("Name:");
        guestNameField = new JTextField(20);

        guestPhoneLabel = new JLabel("Phone:");
        guestPhoneField = new JTextField(20);

        gbcGuest.gridx = 0;
        gbcGuest.gridy = 0;
        guestPanel.add(guestNameLabel, gbcGuest);
        gbcGuest.gridx = 1;
        guestPanel.add(guestNameField, gbcGuest);

        gbcGuest.gridx = 0;
        gbcGuest.gridy = 1;
        guestPanel.add(guestPhoneLabel, gbcGuest);
        gbcGuest.gridx = 1;
        guestPanel.add(guestPhoneField, gbcGuest);

        gbc.gridy = 2;
        mainFormWrapper.add(guestPanel, gbc);

//      PAYMENT METHOD
        paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Method"));

        paymentMethodLabel = new JLabel("Select:");
        cashRadio = new JRadioButton("Cash", true);
        balanceRadio = new JRadioButton("Balance");

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadio);
        paymentGroup.add(balanceRadio);

        paymentPanel.add(paymentMethodLabel);
        paymentPanel.add(cashRadio);
        paymentPanel.add(balanceRadio);

        gbc.gridy = 3;
        mainFormWrapper.add(paymentPanel, gbc);

//      TOTAL
        totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        totalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        totalLabel = new JLabel("Total Amount:");
        totalLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        totalAmountLabel = new JLabel("0.00");
        totalAmountLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        totalAmountLabel.setForeground(new Color(33, 150, 243));

        totalPanel.add(totalLabel);
        totalPanel.add(totalAmountLabel);

        gbc.gridy = 4;
        mainFormWrapper.add(totalPanel, gbc);

//        BUTTONS
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        proceedButton = new JButton("Proceed Payment");
        cancelButton = new JButton("Cancel");

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });

        JPanel _this = this;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                Window window = SwingUtilities.getWindowAncestor(_this);
                if (window != null) {
                    window.dispose(); // closes the JDialog
                }
            }
        });

        buttonPanel.add(proceedButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 5;
        mainFormWrapper.add(buttonPanel, gbc);

        totalAmountLabel.setText(Double.toString(currentOrder.getTotal()));

        add(mainFormWrapper, BorderLayout.CENTER);

//       INITIAL STATE
        registeredPanel.setVisible(true);
        guestPanel.setVisible(false);
    }
    
    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    private void toggleCustomerPanels() {
        boolean isRegistered = customerTypeCombo.getSelectedItem().equals("Registered");
        registeredPanel.setVisible(isRegistered);
        guestPanel.setVisible(!isRegistered);
        revalidate();
        repaint();
    }

    private void clearForm() {
        customerTypeCombo.setSelectedIndex(0);
        customerIdField.setText("");
        customerListCombo.setSelectedIndex(0);
        guestNameField.setText("");
        guestPhoneField.setText("");
        cashRadio.setSelected(true);
        selectedCustomer = null;
        customerBalanceLabel.setText("0.00");
        totalAmountLabel.setText("0.00");
    }

    private boolean validatePayment() {
        // Check if order exists and has items
        if (currentOrder == null || currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No order to process!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Check customer type
        String customerType = (String) customerTypeCombo.getSelectedItem();

        if ("Registered".equals(customerType)) {
            // Validate registered customer
            if (selectedCustomer == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a customer!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // If balance payment, check sufficient balance
            if (balanceRadio.isSelected()) {
                if (selectedCustomer.getBalance() < currentOrder.getTotal()) {
                    JOptionPane.showMessageDialog(this,
                            String.format("Insufficient balance!\nBalance: %.2f\nRequired: %.2f",
                                    selectedCustomer.getBalance(),
                                    currentOrder.getTotal()),
                            "Payment Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

        } else {
            // Validate guest customer
            String name = guestNameField.getText().trim();
            String phone = guestPhoneField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter customer name!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter phone number!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Guest customers can only pay with cash
            if (!cashRadio.isSelected()) {
                cashRadio.setSelected(true);
            }
        }

        return true;
    }

    private void processPayment() {
        if (!validatePayment()) {
            return;
        }

        String customerType = (String) customerTypeCombo.getSelectedItem();
        String paymentMethod = cashRadio.isSelected() ? "CASH" : "Balance";

        // Handle customer
        if ("Guest".equals(customerType)) {
            // Create guest customer
            String name = guestNameField.getText().trim();
            String phone = guestPhoneField.getText().trim();

            selectedCustomer = customerController.addCustomer(name, phone);
        }

        // Process payment based on method
        if ("Balance".equals(paymentMethod)) {
            // Deduct from wallet
            if (!selectedCustomer.updateBalance(-currentOrder.getTotal())) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient wallet balance!",
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Mark order as paid
        currentOrder.setCustomer(selectedCustomer);
        currentOrder.markAsPaid();
        CustomerModel after = customerController.findById(selectedCustomer.getId());
        System.out.println(after.getFullName() + " " + after.getBalance());
        paymentSuccess = true;
        // Clear form
        clearForm();
    }

    private void searchCustomerById() {
        String idText = customerIdField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter Customer ID!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int customerId = Integer.parseInt(idText);
            selectedCustomer = CustomerController.findById(customerId);
            if (selectedCustomer == null) {
                JOptionPane.showMessageDialog(this,
                        "Customer not found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                customerBalanceLabel.setText("0.00");
            }

            customerListCombo.setSelectedItem(selectedCustomer.getFullName());
            // Update balance display
            customerBalanceLabel.setText(String.format("%.2f", selectedCustomer.getBalance()));

            JOptionPane.showMessageDialog(this,
                    "Customer Found: " + selectedCustomer.getFullName(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Customer ID!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mainFormWrapper = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        customerSelect = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(488, 50));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setText("Payment");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jLabel2)
                .addContainerGap(231, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.PAGE_START);

        mainFormWrapper.setLayout(new java.awt.BorderLayout());

        jPanel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(488, 80));
        jPanel4.setLayout(new java.awt.BorderLayout());

        customerSelect.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        customerSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        customerSelect.setMinimumSize(new java.awt.Dimension(74, 30));
        customerSelect.setPreferredSize(new java.awt.Dimension(74, 30));
        customerSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerSelectActionPerformed(evt);
            }
        });
        jPanel4.add(customerSelect, java.awt.BorderLayout.PAGE_END);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("Customer Type");
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);

        mainFormWrapper.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        add(mainFormWrapper, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void customerSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerSelectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> customerSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel mainFormWrapper;
    // End of variables declaration//GEN-END:variables

}
