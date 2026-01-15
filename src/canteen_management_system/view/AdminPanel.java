/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package canteen_management_system.view;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.controller.CustomerController;
import canteen_management_system.controller.FoodItemController;
import canteen_management_system.controller.UserController;
import canteen_management_system.enums.Role;
import canteen_management_system.model.CategoryModel;
import canteen_management_system.model.CustomerModel;
import canteen_management_system.model.FoodItemModel;
import canteen_management_system.model.UserModel;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class AdminPanel extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminPanel.class.getName());
    public CategoryController categoryController = new CategoryController();
    public FoodItemController foodItemController = new FoodItemController();
    public UserController userController = new UserController();
    public CustomerController customerController = new CustomerController();

    /**
     * Creates new form AdminPanel
     */
    public AdminPanel() {
        initComponents();
        
//        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
//        categoryTable.setRowSorter(sorter);
        
        JTableHeader header = categoryTable.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked
            (MouseEvent e) {
        int col = categoryTable.columnAtPoint(e.getPoint());
                System.out.println("Header clicked: column " + col);

                // Call your custom sort here
                customSortColumn(col);
            }
        }
        );
        


        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");

        this.foodItemController.addFoodItem("Coca Cola", this.categoryController.getAllCategoryList().get(0), 1.50, 50, "Chilled soft drink");
        this.foodItemController.addFoodItem("Potato Chips", this.categoryController.getAllCategoryList().get(1), 2.00, 40, "Crispy salted chips");
        this.foodItemController.addFoodItem("Burger", this.categoryController.getAllCategoryList().get(2), 5.99, 30, "Beef burger with cheese");
        this.foodItemController.addFoodItem("Chocolate Cake", this.categoryController.getAllCategoryList().get(3), 4.50, 20, "Rich chocolate dessert");
        this.foodItemController.addFoodItem("Apple", this.categoryController.getAllCategoryList().get(4), 0.80, 100, "Fresh red apples");

        // Add sample users
        this.userController.addUser(
                "Admin User",
                "admin@example.com",
                "admin123",
                "1234567890",
                Role.ADMIN.toString()
        );

        this.userController.addUser(
                "John Doe",
                "john@example.com",
                "john123",
                "0987654321",
                Role.SALES_PERSON.toString()
        );

        this.userController.addUser(
                "Jane Smith",
                "jane@example.com",
                "jane123",
                "1112223333",
                Role.SALES_PERSON.toString()
        );

        this.userController.addUser(
                "Manager Mike",
                "mike@example.com",
                "mike123",
                "4445556666",
                Role.ADMIN.toString()
        );

        this.userController.addUser("Alice Brown","alice@example.com","alice123","7778889999",Role.SALES_PERSON.toString());

        this.customerController.addCustomer("John Doe", 500);
        this.customerController.addCustomer("Alice Smith", 1200);
        this.customerController.addCustomer("Michael Brown", 300);
        this.customerController.addCustomer("Sophia Johnson", 750);
        this.customerController.addCustomer("David Wilson", 0);

    }
    
    private void customSortColumn(int columnIndex) {
    DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();

    int rowCount = model.getRowCount();
    Object[][] data = new Object[rowCount][model.getColumnCount()];
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < model.getColumnCount(); j++) {
            data[i][j] = model.getValueAt(i, j);
        }
    }

    // Example: bubble sort on this column
    for (int i = 0; i < data.length - 1; i++) {
        for (int j = 0; j < data.length - 1 - i; j++) {
            Comparable o1 = (Comparable) data[j][columnIndex];
            Comparable o2 = (Comparable) data[j + 1][columnIndex];
            if (o1.compareTo(o2) > 0) { // ascending
                Object[] temp = data[j];
                data[j] = data[j + 1];
                data[j + 1] = temp;
            }
        }
    }

    // Clear and refill table
    model.setRowCount(0);
    for (Object[] row : data) {
        model.addRow(row);
    }
}


    private void refreshCategoryTable() {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0);
        for (CategoryModel c : categoryController.getAllCategoryList()) {
            Object[] row = {index++, c.getId(), c.getCategoryName(), c.getDescription()};
            model.addRow(row);
        }
    }

    private void refreshFoodTable() {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) foodTable.getModel();
        model.setRowCount(0);
        for (FoodItemModel f : foodItemController.getAllFoodItemList()) {
            Object[] row = {index++, f.getId(), f.getFoodItemName(),
                f.getCategory().getCategoryName(), f.getDescription(),
                f.getQuantity(), f.getPrice()};
            model.addRow(row);
        }
    }

    private void refreshUserTable() {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);
        for (UserModel f : userController.getAllUser()) {
            Object[] row = {index++, f.getId(), f.getFullName(),
                f.getRole(), f.getEmail(),
                f.getPhoneNumber()};
            model.addRow(row);
        }
    }

    private void refreshCustomerTable() {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        model.setRowCount(0);
        for (CustomerModel f : customerController.getAllCustomer()) {
            Object[] row = {index++, f.getId(), f.getFullName(),
                f.getContactNumber(), f.getBalance()
            };
            model.addRow(row);
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

        adminPanelWrapper = new javax.swing.JPanel();
        welcomepage = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        categoryPanel = new javax.swing.JPanel();
        editCatgory = new javax.swing.JButton();
        deleteCategory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTable = new javax.swing.JTable();
        categorySearch = new javax.swing.JTextField();
        foodItemPanel = new javax.swing.JPanel();
        editFood = new javax.swing.JButton();
        deleteFood = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();
        foodSearch = new javax.swing.JTextField();
        userPanel = new javax.swing.JPanel();
        editUser = new javax.swing.JButton();
        deleteUser = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        searchUser = new javax.swing.JTextField();
        customerPanel = new javax.swing.JPanel();
        editCustomer = new javax.swing.JButton();
        deleteCustomer = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        customerSearch = new javax.swing.JTextField();
        adminPanelMenu = new javax.swing.JMenuBar();
        categoryViewItem = new javax.swing.JMenu();
        listCatgorySubMenu = new javax.swing.JMenuItem();
        addCatgorySubMenu = new javax.swing.JMenuItem();
        foodItemMenu = new javax.swing.JMenu();
        viewFoodSubMenu = new javax.swing.JMenuItem();
        addFoodSubMenu = new javax.swing.JMenuItem();
        userMenu = new javax.swing.JMenu();
        listUserSubMenu = new javax.swing.JMenuItem();
        addUserSubMenu = new javax.swing.JMenuItem();
        customerMenu = new javax.swing.JMenu();
        listCustomerSubMenu = new javax.swing.JMenuItem();
        addCustomer = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        adminPanelWrapper.setBackground(new java.awt.Color(201, 214, 222));
        adminPanelWrapper.setPreferredSize(new java.awt.Dimension(640, 350));
        adminPanelWrapper.setLayout(new java.awt.CardLayout());

        welcomepage.setBackground(new java.awt.Color(201, 214, 222));
        welcomepage.setPreferredSize(new java.awt.Dimension(640, 350));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel2.setText("Welcome to Admin Panel");

        javax.swing.GroupLayout welcomepageLayout = new javax.swing.GroupLayout(welcomepage);
        welcomepage.setLayout(welcomepageLayout);
        welcomepageLayout.setHorizontalGroup(
            welcomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomepageLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );
        welcomepageLayout.setVerticalGroup(
            welcomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomepageLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel2)
                .addContainerGap(176, Short.MAX_VALUE))
        );

        adminPanelWrapper.add(welcomepage, "welComePage");

        editCatgory.setText("Edit");
        editCatgory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCatgoryActionPerformed(evt);
            }
        });

        deleteCategory.setText("Delete");
        deleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryActionPerformed(evt);
            }
        });

        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "S.N", "ID", "Name", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(categoryTable);
        if (categoryTable.getColumnModel().getColumnCount() > 0) {
            categoryTable.getColumnModel().getColumn(0).setResizable(false);
            categoryTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        categorySearch.setToolTipText("Search by name and description");

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(categoryPanelLayout.createSequentialGroup()
                        .addComponent(categorySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(editCatgory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCategory)
                        .addGap(14, 14, 14))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        categoryPanelLayout.setVerticalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(categoryPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteCategory)
                            .addComponent(editCatgory)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(categorySearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminPanelWrapper.add(categoryPanel, "categoryPanel");

        editFood.setText("Edit");
        editFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFoodActionPerformed(evt);
            }
        });

        deleteFood.setText("Delete");
        deleteFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFoodActionPerformed(evt);
            }
        });

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "S.N", "ID", "Name", "Category", "description", "quantity", "price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(foodTable);
        if (foodTable.getColumnModel().getColumnCount() > 0) {
            foodTable.getColumnModel().getColumn(0).setResizable(false);
            foodTable.getColumnModel().getColumn(0).setPreferredWidth(5);
            foodTable.getColumnModel().getColumn(5).setHeaderValue("quantity");
            foodTable.getColumnModel().getColumn(6).setHeaderValue("price");
        }

        foodSearch.setToolTipText("Search by name and description");
        foodSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout foodItemPanelLayout = new javax.swing.GroupLayout(foodItemPanel);
        foodItemPanel.setLayout(foodItemPanelLayout);
        foodItemPanelLayout.setHorizontalGroup(
            foodItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodItemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(foodItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(foodItemPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(foodItemPanelLayout.createSequentialGroup()
                        .addComponent(foodSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(editFood)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteFood)
                        .addGap(14, 14, 14))))
        );
        foodItemPanelLayout.setVerticalGroup(
            foodItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodItemPanelLayout.createSequentialGroup()
                .addGroup(foodItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(foodItemPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(foodItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteFood)
                            .addComponent(editFood)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodItemPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(foodSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminPanelWrapper.add(foodItemPanel, "foodItemPanel");

        editUser.setText("Edit");
        editUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserActionPerformed(evt);
            }
        });

        deleteUser.setText("Delete");
        deleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserActionPerformed(evt);
            }
        });

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "S.N", "ID", "Full Name", "Role", "email", "Phone Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setResizable(false);
            userTable.getColumnModel().getColumn(0).setPreferredWidth(5);
            userTable.getColumnModel().getColumn(5).setHeaderValue("Phone Number");
        }

        searchUser.setToolTipText("Search by name and description");
        searchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addComponent(searchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(editUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteUser)
                        .addGap(14, 14, 14))))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteUser)
                            .addComponent(editUser)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminPanelWrapper.add(userPanel, "userPanel");

        editCustomer.setText("Edit");
        editCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCustomerActionPerformed(evt);
            }
        });

        deleteCustomer.setText("Delete");
        deleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCustomerActionPerformed(evt);
            }
        });

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "S.N", "ID", "Full Name", "Phone Number", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(customerTable);
        if (customerTable.getColumnModel().getColumnCount() > 0) {
            customerTable.getColumnModel().getColumn(0).setResizable(false);
            customerTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        customerSearch.setToolTipText("Search by name and description");
        customerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customerPanelLayout = new javax.swing.GroupLayout(customerPanel);
        customerPanel.setLayout(customerPanelLayout);
        customerPanelLayout.setHorizontalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addComponent(customerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(editCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCustomer)
                        .addGap(14, 14, 14))))
        );
        customerPanelLayout.setVerticalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteCustomer)
                            .addComponent(editCustomer)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(customerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminPanelWrapper.add(customerPanel, "customerPanel");

        categoryViewItem.setText("Category");
        categoryViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryViewItemActionPerformed(evt);
            }
        });

        listCatgorySubMenu.setText("List");
        listCatgorySubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listCatgorySubMenuActionPerformed(evt);
            }
        });
        categoryViewItem.add(listCatgorySubMenu);

        addCatgorySubMenu.setText("Add");
        addCatgorySubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCatgorySubMenuActionPerformed(evt);
            }
        });
        categoryViewItem.add(addCatgorySubMenu);

        adminPanelMenu.add(categoryViewItem);

        foodItemMenu.setText("Food item");
        foodItemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodItemMenuActionPerformed(evt);
            }
        });

        viewFoodSubMenu.setText("List");
        viewFoodSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewFoodSubMenuActionPerformed(evt);
            }
        });
        foodItemMenu.add(viewFoodSubMenu);

        addFoodSubMenu.setText("Add");
        addFoodSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFoodSubMenuActionPerformed(evt);
            }
        });
        foodItemMenu.add(addFoodSubMenu);

        adminPanelMenu.add(foodItemMenu);

        userMenu.setText("User");

        listUserSubMenu.setText("List");
        listUserSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listUserSubMenuActionPerformed(evt);
            }
        });
        userMenu.add(listUserSubMenu);

        addUserSubMenu.setText("Add");
        addUserSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserSubMenuActionPerformed(evt);
            }
        });
        userMenu.add(addUserSubMenu);

        adminPanelMenu.add(userMenu);

        customerMenu.setText("Customer");

        listCustomerSubMenu.setText("List");
        listCustomerSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listCustomerSubMenuActionPerformed(evt);
            }
        });
        customerMenu.add(listCustomerSubMenu);

        addCustomer.setText("Add");
        addCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCustomerActionPerformed(evt);
            }
        });
        customerMenu.add(addCustomer);

        adminPanelMenu.add(customerMenu);

        setJMenuBar(adminPanelMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminPanelWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminPanelWrapper, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addCatgorySubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCatgorySubMenuActionPerformed
        JDialog addCategory = new JDialog(this, "Add Category", true);
        JPanel categoryPanel = new AddCategoryForm();
        addCategory.setContentPane(categoryPanel);
        addCategory.pack();
        addCategory.setVisible(true);
        this.refreshCategoryTable();
    }//GEN-LAST:event_addCatgorySubMenuActionPerformed

    private void listCatgorySubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCatgorySubMenuActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "categoryPanel");
        this.refreshCategoryTable();
    }//GEN-LAST:event_listCatgorySubMenuActionPerformed

    private void editCatgoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCatgoryActionPerformed
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }
        System.out.println(selectedRow);
        int categoryId = (int) categoryTable.getValueAt(selectedRow, 1);
        JDialog addCategory = new JDialog(this, "Update Category", true);
        JPanel categoryPanel = new AddCategoryForm(categoryId);
        addCategory.setContentPane(categoryPanel);
        addCategory.pack();
        addCategory.setVisible(true);
        this.refreshCategoryTable();
    }//GEN-LAST:event_editCatgoryActionPerformed

    private void categoryViewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryViewItemActionPerformed

    }//GEN-LAST:event_categoryViewItemActionPerformed

    private void foodItemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodItemMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodItemMenuActionPerformed

    private void viewFoodSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewFoodSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "foodItemPanel");
        this.refreshFoodTable();
    }//GEN-LAST:event_viewFoodSubMenuActionPerformed

    private void addCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustomerActionPerformed
        JDialog addCustomer = new JDialog(this, "Add Customer", true);
        JPanel customerPanel = new CustomerForm();
        addCustomer.setContentPane(customerPanel);
        addCustomer.pack();
        addCustomer.setLocationRelativeTo(this); // optional, center dialog
        addCustomer.setVisible(true);
        this.refreshCustomerTable();
    }//GEN-LAST:event_addCustomerActionPerformed

    private void deleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryActionPerformed
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }

        int categoryId = (int) categoryTable.getValueAt(selectedRow, 1);
        String categoryName = (String) categoryTable.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the category: " + categoryName + "?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = this.categoryController.deleteCategory(categoryId);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Category deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Could not delete category.");
        }
        this.refreshCategoryTable();
    }//GEN-LAST:event_deleteCategoryActionPerformed

    private void addFoodSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFoodSubMenuActionPerformed
        JDialog addFoodItem = new JDialog(this, "Add FoodItem", true);
        JPanel foodItem = new FoodItem();
        addFoodItem.setContentPane(foodItem);
        addFoodItem.pack();
        addFoodItem.setVisible(true);
        this.refreshFoodTable();
    }//GEN-LAST:event_addFoodSubMenuActionPerformed

    private void editFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFoodActionPerformed
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int foodId = (int) foodTable.getValueAt(selectedRow, 1);

        JDialog editFood = new JDialog(this, "Update Food Item", true);
        JPanel foodPanel = new FoodItem(foodId);
        editFood.setContentPane(foodPanel);
        editFood.pack();
        editFood.setLocationRelativeTo(this);
        editFood.setVisible(true);
        this.refreshFoodTable();
    }//GEN-LAST:event_editFoodActionPerformed

    private void deleteFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFoodActionPerformed
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int foodId = (int) foodTable.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this food item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            foodItemController.deleteFoodItem(foodId);
            this.refreshFoodTable();
        }
    }//GEN-LAST:event_deleteFoodActionPerformed

    private void foodSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodSearchActionPerformed

    private void addUserSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserSubMenuActionPerformed
        JDialog addUser = new JDialog(this, "Add User", true);
        JPanel userPanel = new userPanel();
        addUser.setContentPane(userPanel);
        addUser.pack();
        addUser.setVisible(true);
        this.refreshUserTable();
    }//GEN-LAST:event_addUserSubMenuActionPerformed

    private void editUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserActionPerformed
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 1);
        JDialog addUser = new JDialog(this, "Update User", true);
        JPanel userPanel = new userPanel(userId);
        addUser.setContentPane(userPanel);
        addUser.pack();
        addUser.setVisible(true);
        this.refreshUserTable();
    }//GEN-LAST:event_editUserActionPerformed

    private void deleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserActionPerformed
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this User?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            userController.deleteUser(userId);
            this.refreshUserTable();
        }
    }//GEN-LAST:event_deleteUserActionPerformed

    private void searchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchUserActionPerformed

    private void listUserSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listUserSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "userPanel");
        this.refreshUserTable();
    }//GEN-LAST:event_listUserSubMenuActionPerformed

    private void listCustomerSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCustomerSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "customerPanel");
        this.refreshCustomerTable();
    }//GEN-LAST:event_listCustomerSubMenuActionPerformed

    private void editCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCustomerActionPerformed
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int customerId = (int) customerTable.getValueAt(selectedRow, 1);

        JDialog editCustomer = new JDialog(this, "Update Customer", true);
        JPanel customerPanel = new CustomerForm(customerId);
        editCustomer.setContentPane(customerPanel);
        editCustomer.pack();
        editCustomer.setLocationRelativeTo(this);
        editCustomer.setVisible(true);

        this.refreshCustomerTable();
    }//GEN-LAST:event_editCustomerActionPerformed

    private void deleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCustomerActionPerformed
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        int customerId = (int) customerTable.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this Customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            customerController.deleteCustomer(customerId);
            this.refreshCustomerTable();
        }
    }//GEN-LAST:event_deleteCustomerActionPerformed

    private void customerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerSearchActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new AdminPanel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addCatgorySubMenu;
    private javax.swing.JMenuItem addCustomer;
    private javax.swing.JMenuItem addFoodSubMenu;
    private javax.swing.JMenuItem addUserSubMenu;
    private javax.swing.JMenuBar adminPanelMenu;
    private javax.swing.JPanel adminPanelWrapper;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JTextField categorySearch;
    private javax.swing.JTable categoryTable;
    private javax.swing.JMenu categoryViewItem;
    private javax.swing.JMenu customerMenu;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JTextField customerSearch;
    private javax.swing.JTable customerTable;
    private javax.swing.JButton deleteCategory;
    private javax.swing.JButton deleteCustomer;
    private javax.swing.JButton deleteFood;
    private javax.swing.JButton deleteUser;
    private javax.swing.JButton editCatgory;
    private javax.swing.JButton editCustomer;
    private javax.swing.JButton editFood;
    private javax.swing.JButton editUser;
    private javax.swing.JMenu foodItemMenu;
    private javax.swing.JPanel foodItemPanel;
    private javax.swing.JTextField foodSearch;
    private javax.swing.JTable foodTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem listCatgorySubMenu;
    private javax.swing.JMenuItem listCustomerSubMenu;
    private javax.swing.JMenuItem listUserSubMenu;
    private javax.swing.JTextField searchUser;
    private javax.swing.JMenu userMenu;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTable userTable;
    private javax.swing.JMenuItem viewFoodSubMenu;
    private javax.swing.JPanel welcomepage;
    // End of variables declaration//GEN-END:variables
}
