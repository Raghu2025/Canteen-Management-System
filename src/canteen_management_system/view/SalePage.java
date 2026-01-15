/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package canteen_management_system.view;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.controller.FoodItemController;
import canteen_management_system.controller.OrderController;
import canteen_management_system.model.CategoryModel;
import canteen_management_system.model.FoodItemModel;
import canteen_management_system.model.OrderItemModel;
import canteen_management_system.model.OrderModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class SalePage extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalePage.class.getName());
    private CategoryController categoryController = new CategoryController();
    private FoodItemController foodController = new FoodItemController();
    private OrderController orderController = new OrderController();

    private String selectedCategoryFilter = null;

    /**
     * Creates new form SalePage
     */
    public SalePage() {
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        // Add sample food items for testing
        this.foodController.addFoodItem("Coca Cola", CategoryController.findByName("Drinks"), 2.5, 50, "Refreshing cold drink");
        this.foodController.addFoodItem("Chips", CategoryController.findByName("Snacks"), 1.5, 100, "Crispy potato chips");
        this.foodController.addFoodItem("Burger", CategoryController.findByName("Fast Food"), 5.0, 30, "Delicious beef burger");
        this.initComponents();
        this.orderController.addOrder();

        //Initialize category
        this.setCategoryList();
        // Initialize food table with all items
        this.initFoodItem();
        // Add search functionality to search field
        this.initSearchInput();
        
    }

    private void initSearchInput() {
        foodSearchInput.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (foodSearchInput.getText().equals("Search")) {
                    foodSearchInput.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (foodSearchInput.getText().trim().isEmpty()) {
                    foodSearchInput.setText("Search");
                }
            }
        });

        foodSearchInput.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                setFoodItemTable(foodSearchInput.getText());
            }
        });
    }

    private void initFoodItem() {
        setFoodItemTable("");
        foodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    int row = foodTable.rowAtPoint(e.getPoint());
                    int foodIds = (int) foodTable.getValueAt(row, 0);
                    FoodItemModel foods = foodController.findById(row);
                    OrderModel order = OrderController.peek();
                    String mess = orderController.addOrderItem(order.getId(), foodIds, 1);
                    
                    if (mess == null || mess.trim().isEmpty()) {
                        // Success message
                        JOptionPane.showMessageDialog(
                                mainSalesWrapper,
                                "Item added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        refreshOrderTable();
                    } else {
                        // Warning or error message
                        JOptionPane.showMessageDialog(
                                mainSalesWrapper,
                                mess,
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
            }
                }
            }
        });
    }
    

// Add these instance variables at the top of the class
// Method to populate food items table based on category filter and search
    public void setFoodItemTable(String searchText) {
        // Define table columns
        String[] columns = {"ID", "Name", "Description", "Price", "Quantity", "Category"};

        // Get filtered food items
        java.util.LinkedList<canteen_management_system.model.FoodItemModel> filteredItems
                = new java.util.LinkedList<>();

        for (canteen_management_system.model.FoodItemModel food : foodController.getAllFoodItemList()) {
            // Apply category filter
            if (selectedCategoryFilter != null
                    && !food.getCategory().getCategoryName().equals(selectedCategoryFilter)) {
                continue;
            }

            // Apply search filter (search by name, description, or ID)
            if (searchText != null && !searchText.trim().isEmpty()
                    && !searchText.equalsIgnoreCase("Search")) {
                String search = searchText.toLowerCase().trim();
                boolean matches = food.getFoodItemName().toLowerCase().contains(search)
                        || food.getDescription().toLowerCase().contains(search)
                        || String.valueOf(food.getId()).contains(search);
                if (!matches) {
                    continue;
                }
            }

            filteredItems.add(food);
        }

        // Prepare table data
        Object[][] data = new Object[filteredItems.size()][6];
        int row = 0;
        for (canteen_management_system.model.FoodItemModel food : filteredItems) {
            data[row][0] = food.getId();
            data[row][1] = food.getFoodItemName();
            data[row][2] = food.getDescription();
            data[row][3] = food.getPrice();
            data[row][4] = food.getQuantity();
            data[row][5] = food.getCategory().getCategoryName();
            row++;
        }

        // Update table
        foodTable.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

// Update setCategoryList method to add click listeners
    public void setCategoryList() {
        categoryPanelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        categoryPanelWrapper.removeAll();

        for (CategoryModel c : categoryController.getAllCategoryList()) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BorderLayout());

            JLabel label = new JLabel(c.getCategoryName(), JLabel.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 14));

            productPanel.add(label, BorderLayout.CENTER);
            productPanel.setBackground(Color.BLUE);
            productPanel.setPreferredSize(new Dimension(180, 120));

            // Add click listener to filter by category
            productPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    selectedCategoryFilter = c.getCategoryName();
                    setFoodItemTable(foodSearchInput.getText());
                    resetCategoryColors();
                    productPanel.setBackground(Color.DARK_GRAY);
                    label.setForeground(Color.WHITE);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!productPanel.getBackground().equals(Color.DARK_GRAY)) {
                        productPanel.setBackground(Color.CYAN);
                        label.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!productPanel.getBackground().equals(Color.DARK_GRAY)) {
                        productPanel.setBackground(Color.BLUE);
                        label.setForeground(Color.WHITE);
                    }
                }
            });

            categoryPanelWrapper.add(productPanel);
        }

        int cols = Math.max(1, categoryPanelWrapper.getWidth() / 180);
        int rows = (int) Math.ceil((double) categoryController.getAllCategoryList().size() / cols);
        int totalHeight = rows * 125;

        categoryPanelWrapper.setPreferredSize(new Dimension(
                categoryPanelWrapper.getWidth(),
                totalHeight
        ));

        categoryPanelWrapper.revalidate();
        categoryPanelWrapper.repaint();
    }

    private void resetCategoryColors() {
        for (java.awt.Component comp : categoryPanelWrapper.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(Color.BLUE);
            }
        }
    }

    public void clearCategoryFilter() {
        selectedCategoryFilter = null;
        resetCategoryColors();
        setFoodItemTable(foodSearchInput.getText());
    }

    public void refreshOrderTable() {
        String[] columns = {"Order ID", "Name", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        OrderModel order = OrderController.peek();
        totalAmountLabel.setText(Double.toString(order.getTotal()));
        LinkedList<OrderItemModel> orderItems = order.getOrderItems();
        for (int i = 0; i < order.size(); i++) {
            OrderItemModel orderI = orderItems.get(i);

            Object[] rowData = {
                orderI.getId(),
                orderI.getFoodItem().getFoodItemName(),
                orderI.getQuantity(),
                orderI.getTotal()
            };

            model.addRow(rowData);
        }

        orderTable.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSalesWrapper = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        navigationWrapper = new javax.swing.JPanel();
        foodSearchInput = new javax.swing.JTextField();
        filterClearButton = new javax.swing.JButton();
        removeOrder = new javax.swing.JButton();
        mainFoodAndOrderWrapper = new javax.swing.JSplitPane();
        orderWrapper = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        totalAmountLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        payment = new javax.swing.JButton();
        foodWrapper = new javax.swing.JPanel();
        categoryScrollPanel = new javax.swing.JScrollPane();
        categoryPanelWrapper = new javax.swing.JPanel();
        foodTableWrapper = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(50);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.2);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(100, 232));

        navigationWrapper.setBackground(new java.awt.Color(209, 220, 226));
        navigationWrapper.setPreferredSize(new java.awt.Dimension(200, 232));

        foodSearchInput.setText("Search");
        foodSearchInput.setPreferredSize(new java.awt.Dimension(73, 30));

        filterClearButton.setBackground(new java.awt.Color(255, 0, 51));
        filterClearButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        filterClearButton.setForeground(new java.awt.Color(255, 255, 255));
        filterClearButton.setText("Clear Filter");
        filterClearButton.setPreferredSize(new java.awt.Dimension(75, 30));
        filterClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterClearButtonActionPerformed(evt);
            }
        });

        removeOrder.setBackground(new java.awt.Color(255, 0, 51));
        removeOrder.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        removeOrder.setForeground(new java.awt.Color(255, 255, 255));
        removeOrder.setText("Remove Order");
        removeOrder.setPreferredSize(new java.awt.Dimension(111, 30));
        removeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigationWrapperLayout = new javax.swing.GroupLayout(navigationWrapper);
        navigationWrapper.setLayout(navigationWrapperLayout);
        navigationWrapperLayout.setHorizontalGroup(
            navigationWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigationWrapperLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
                .addComponent(foodSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        navigationWrapperLayout.setVerticalGroup(
            navigationWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigationWrapperLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(navigationWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(navigationWrapper);

        mainFoodAndOrderWrapper.setDividerLocation(300);
        mainFoodAndOrderWrapper.setDividerSize(0);

        orderWrapper.setBackground(new java.awt.Color(209, 220, 226));
        orderWrapper.setPreferredSize(new java.awt.Dimension(452, 600));
        orderWrapper.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(209, 220, 226));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Orders");
        jLabel1.setToolTipText("");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMaximumSize(new java.awt.Dimension(44, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(44, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(38, 20));
        orderWrapper.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 300));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 400));

        orderTable.setBackground(new java.awt.Color(209, 220, 226));
        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(orderTable);

        orderWrapper.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(209, 220, 226));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 75));

        totalAmountLabel.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        totalAmountLabel.setForeground(new java.awt.Color(209, 220, 226));
        totalAmountLabel.setText("0.0");

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Total Amount");

        payment.setBackground(new java.awt.Color(0, 204, 51));
        payment.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        payment.setForeground(new java.awt.Color(255, 255, 255));
        payment.setText("Proceed to Payment");
        payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(payment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalAmountLabel))
                .addGap(18, 18, 18)
                .addComponent(payment)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        orderWrapper.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        mainFoodAndOrderWrapper.setLeftComponent(orderWrapper);

        foodWrapper.setLayout(new java.awt.BorderLayout());

        categoryScrollPanel.setMaximumSize(new java.awt.Dimension(32767, 250));
        categoryScrollPanel.setPreferredSize(new java.awt.Dimension(199, 250));

        categoryPanelWrapper.setBackground(new java.awt.Color(209, 220, 226));
        categoryPanelWrapper.setPreferredSize(new java.awt.Dimension(197, 300));
        categoryPanelWrapper.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        categoryScrollPanel.setViewportView(categoryPanelWrapper);

        foodWrapper.add(categoryScrollPanel, java.awt.BorderLayout.PAGE_START);

        foodTableWrapper.setBackground(new java.awt.Color(255, 204, 204));
        foodTableWrapper.setPreferredSize(new java.awt.Dimension(635, 200));

        jScrollPane1.setBackground(new java.awt.Color(209, 220, 226));

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(foodTable);

        javax.swing.GroupLayout foodTableWrapperLayout = new javax.swing.GroupLayout(foodTableWrapper);
        foodTableWrapper.setLayout(foodTableWrapperLayout);
        foodTableWrapperLayout.setHorizontalGroup(
            foodTableWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        foodTableWrapperLayout.setVerticalGroup(
            foodTableWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );

        foodWrapper.add(foodTableWrapper, java.awt.BorderLayout.CENTER);

        mainFoodAndOrderWrapper.setRightComponent(foodWrapper);

        jSplitPane1.setRightComponent(mainFoodAndOrderWrapper);

        javax.swing.GroupLayout mainSalesWrapperLayout = new javax.swing.GroupLayout(mainSalesWrapper);
        mainSalesWrapper.setLayout(mainSalesWrapperLayout);
        mainSalesWrapperLayout.setHorizontalGroup(
            mainSalesWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
        );
        mainSalesWrapperLayout.setVerticalGroup(
            mainSalesWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );

        getContentPane().add(mainSalesWrapper);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filterClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterClearButtonActionPerformed
        this.clearCategoryFilter();
    }//GEN-LAST:event_filterClearButtonActionPerformed

    private void removeOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderActionPerformed
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }

        int orderItemId = (int) orderTable.getValueAt(selectedRow, 0);
        String item = (String) orderTable.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the item: " + item + "?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean removed = orderController.peek().removeOrderItemById(orderItemId);
        if (removed) {
            System.out.println("Item removed successfully.");
            refreshOrderTable();
        } else {
            System.out.println("Item not found.");
        }
    }//GEN-LAST:event_removeOrderActionPerformed

    private void paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentActionPerformed
        OrderModel order = OrderController.peek();
        int size = order.getOrderItems().size();
        if (size == 0) {
            JOptionPane.showMessageDialog(
                    this, // parent component (your JFrame or JPanel)
                    "Cannot delete item. The order is empty!",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        PaymentView payment = new PaymentView(order);
        JDialog paymentDailog = new JDialog(this, "Update Category", true);
        paymentDailog.setContentPane(payment);
        paymentDailog.pack();
        paymentDailog.setVisible(true);
        System.out.println("after" + payment.isPaymentSuccess());
        if(payment.isPaymentSuccess()) {
            orderController.addOrder();
            refreshOrderTable();
            clearCategoryFilter();
            
        }

    }//GEN-LAST:event_paymentActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SalePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel categoryPanelWrapper;
    private javax.swing.JScrollPane categoryScrollPanel;
    private javax.swing.JButton filterClearButton;
    private javax.swing.JTextField foodSearchInput;
    private javax.swing.JTable foodTable;
    private javax.swing.JPanel foodTableWrapper;
    private javax.swing.JPanel foodWrapper;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane mainFoodAndOrderWrapper;
    private javax.swing.JPanel mainSalesWrapper;
    private javax.swing.JPanel navigationWrapper;
    private javax.swing.JTable orderTable;
    private javax.swing.JPanel orderWrapper;
    private javax.swing.JButton payment;
    private javax.swing.JButton removeOrder;
    private javax.swing.JLabel totalAmountLabel;
    // End of variables declaration//GEN-END:variables
}
