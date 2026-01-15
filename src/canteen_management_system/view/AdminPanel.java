/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package canteen_management_system.view;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.controller.CustomerController;
import canteen_management_system.controller.FoodItemController;
import canteen_management_system.controller.OrderController;
import canteen_management_system.controller.UserController;
import canteen_management_system.model.CategoryModel;
import canteen_management_system.model.CustomerModel;
import canteen_management_system.model.FoodItemModel;
import canteen_management_system.model.UserModel;
import java.awt.CardLayout;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
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

        DefaultTableModel customerSort = (DefaultTableModel) customerTable.getModel();
        TableRowSorter<DefaultTableModel> sorter1 = new TableRowSorter<>(customerSort);
        customerTable.setRowSorter(sorter1);
        
        DefaultTableModel userSorter = (DefaultTableModel) userTable.getModel();
        TableRowSorter<DefaultTableModel> sorter2 = new TableRowSorter<>(userSorter);
        userTable.setRowSorter(sorter2);
        
        DefaultTableModel foodSorter = (DefaultTableModel) foodTable.getModel();
        TableRowSorter<DefaultTableModel> sorter3 = new TableRowSorter<>(foodSorter);
        foodTable.setRowSorter(sorter3);
        
        DefaultTableModel categorySorter = (DefaultTableModel) categoryTable.getModel();
        TableRowSorter<DefaultTableModel> sorter4 = new TableRowSorter<>(categorySorter);
        categoryTable.setRowSorter(sorter4);
        
        this.dCatgoryTotal.setText(Integer.toString(categoryController.getAllCategoryList().size()));
        this.dUserTotal.setText(Integer.toString(userController.getAllUser().size()));
        this.dFoodTotal.setText(Integer.toString(foodItemController.getAllFoodItemList().size()));
        this.dCustomerTotal.setText(Integer.toString(customerController.getAllCustomer().size()));
        this.dOrderTotal.setText(Integer.toString(new OrderController().size()));


    }

    private void refreshCategoryTable(String searchText) {
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0);

        // Copy list to avoid mutating controller data
       List<CategoryModel> categories = new ArrayList<>(categoryController.getAllCategoryList());


        // Sort by category name (required for binary search)
        categories.sort(Comparator.comparing(
                CategoryModel::getCategoryName,
                String.CASE_INSENSITIVE_ORDER
        ));

        // Show all if search is empty
        if (searchText == null || searchText.isBlank()) {
            fillTable(model, categories);
            return;
        }

        searchText = searchText.trim();

        int index = binarySearchByName(categories, searchText);

        if (index == -1) {
            // No exact match → show nothing (or show all if you prefer)
            return;
        }

        // Collect all matching entries (partial match)
        int left = index;
        int right = index;

        while (left - 1 >= 0
                && categories.get(left - 1).getCategoryName()
                        .toLowerCase().contains(searchText.toLowerCase())) {
            left--;
        }

        while (right + 1 < categories.size()
                && categories.get(right + 1).getCategoryName()
                        .toLowerCase().contains(searchText.toLowerCase())) {
            right++;
        }

        int serial = 1;
        for (int i = left; i <= right; i++) {
            CategoryModel c = categories.get(i);
            model.addRow(new Object[]{
                serial++, c.getId(), c.getCategoryName(), c.getDescription()
            });
        }
    }

    private void fillTable(DefaultTableModel model, List<CategoryModel> categories) {
        for (int i = 0; i < categories.size(); i++) {
            CategoryModel c = categories.get(i);
            model.addRow(new Object[]{
                i + 1, c.getId(), c.getCategoryName(), c.getDescription()
            });
        }
    }

    private int binarySearchByName(List<CategoryModel> categories, String name) {
        int left = 0;
        int right = categories.size() - 1;
        name = name.toLowerCase();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midName = categories.get(mid)
                    .getCategoryName()
                    .toLowerCase();

            if (midName.contains(name)) {
                return mid;
            } else if (midName.compareTo(name) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }


    private void refreshFoodTable(String searchText) {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) foodTable.getModel();
        model.setRowCount(0);

        for (FoodItemModel f : foodItemController.getAllFoodItemList()) {

            if (searchText != null && !searchText.isBlank()) {
                String search = searchText.toLowerCase();

                String name = f.getFoodItemName();
                String desc = f.getDescription();
                String id = String.valueOf(f.getId());
                boolean match
                        = (name != null && name.toLowerCase().contains(search))
                        || (desc != null && desc.toLowerCase().contains(search))
                        || id.contains(search);

                if (!match) {
                    continue;
                }
            }

            Object[] row = {
                index++,
                f.getId(),
                f.getFoodItemName(),
                f.getCategory().getCategoryName(),
                f.getDescription(),
                f.getQuantity(),
                f.getPrice()
            };
            model.addRow(row);
        }
    }

    private void refreshUserTable(String searchText) {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);
        for (UserModel u : userController.getAllUser()) {
            if (searchText != null && !searchText.isBlank()) {
                String search = searchText.toLowerCase();

                String name = u.getFullName();
                String role = String.valueOf(u.getRole());
                String email = u.getEmail();
                String phone = u.getPhoneNumber();
                String id = String.valueOf(u.getId());

                boolean match
                        = (name != null && name.toLowerCase().contains(search))
                        || (role != null && role.toLowerCase().contains(search))
                        || (email != null && email.toLowerCase().contains(search))
                        || (phone != null && phone.toLowerCase().contains(search))
                        || id.contains(search);

                if (!match) {
                    continue;
                }
            }

            Object[] row = {
                index++,
                u.getId(),
                u.getFullName(),
                u.getRole(),
                u.getEmail(),
                u.getPhoneNumber()
            };
            model.addRow(row);
        }
    }

    private void refreshCustomerTable(String searchText) {
        int index = 1;
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        model.setRowCount(0);
        for (CustomerModel c : customerController.getAllCustomer()) {
            if (searchText != null && !searchText.isBlank()) {
                String search = searchText.toLowerCase();

                String name = c.getFullName();
                String contact = c.getContactNumber();
                String id = String.valueOf(c.getId());
                String balance = String.valueOf(c.getBalance());

                boolean match
                        = (name != null && name.toLowerCase().contains(search))
                        || (contact != null && contact.toLowerCase().contains(search))
                        || id.contains(search)
                        || balance.contains(search);

                if (!match) {
                    continue;
                }
            }
            Object[] row = {
                index++,
                c.getId(),
                c.getFullName(),
                c.getContactNumber(),
                c.getBalance()
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
        java.awt.GridBagConstraints gridBagConstraints;

        adminPanelWrapper = new javax.swing.JPanel();
        welcomepage = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dCatgoryTotal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dFoodTotal = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dUserTotal = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dCustomerTotal = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        dOrderTotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
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
        navigate = new javax.swing.JMenu();
        backToSalePage = new javax.swing.JMenuItem();
        logOut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));

        adminPanelWrapper.setBackground(new java.awt.Color(201, 214, 222));
        adminPanelWrapper.setPreferredSize(new java.awt.Dimension(640, 350));
        adminPanelWrapper.setLayout(new java.awt.CardLayout());

        welcomepage.setBackground(new java.awt.Color(201, 214, 222));
        welcomepage.setPreferredSize(new java.awt.Dimension(640, 350));
        welcomepage.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(209, 220, 226));
        jPanel1.setPreferredSize(new java.awt.Dimension(1304, 200));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 5));

        jPanel3.setBackground(new java.awt.Color(101, 158, 167));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Category");

        dCatgoryTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dCatgoryTotal.setForeground(new java.awt.Color(255, 255, 255));
        dCatgoryTotal.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dCatgoryTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(dCatgoryTotal)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(101, 158, 167));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total Food Item");

        dFoodTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dFoodTotal.setForeground(new java.awt.Color(255, 255, 255));
        dFoodTotal.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dFoodTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(dFoodTotal)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(101, 158, 167));
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total User");

        dUserTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dUserTotal.setForeground(new java.awt.Color(255, 255, 255));
        dUserTotal.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(dUserTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dUserTotal)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(101, 158, 167));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Customer");

        dCustomerTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dCustomerTotal.setForeground(new java.awt.Color(255, 255, 255));
        dCustomerTotal.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dCustomerTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(dCustomerTotal)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(101, 158, 167));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Order");

        dOrderTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        dOrderTotal.setForeground(new java.awt.Color(255, 255, 255));
        dOrderTotal.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dOrderTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(dOrderTotal)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7);

        welcomepage.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(209, 220, 226));

        jLabel2.setBackground(new java.awt.Color(209, 220, 226));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Welcome to Admin Panel");
        jLabel2.setPreferredSize(new java.awt.Dimension(403, 100));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1304, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        welcomepage.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        adminPanelWrapper.add(welcomepage, "welComePage");

        categoryPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                categoryPanelKeyPressed(evt);
            }
        });

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
        categorySearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                categorySearchCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(categoryPanelLayout.createSequentialGroup()
                        .addComponent(categorySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
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
                "S.N", "ID", "Name", "Category", "Description", "Quantity", "Price"
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
        }

        foodSearch.setToolTipText("Search by name and description");
        foodSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                foodSearchCaretUpdate(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
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
        searchUser.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchUserCaretUpdate(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
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
        customerSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                customerSearchCaretUpdate(evt);
            }
        });
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
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

        navigate.setText("Navigate");

        backToSalePage.setText("Sales page");
        backToSalePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToSalePageActionPerformed(evt);
            }
        });
        navigate.add(backToSalePage);

        logOut.setText("Log out");
        logOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutActionPerformed(evt);
            }
        });
        navigate.add(logOut);

        adminPanelMenu.add(navigate);

        setJMenuBar(adminPanelMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminPanelWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE)
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
        addCategory.setLocationRelativeTo(null);
        addCategory.setVisible(true);
        this.refreshCategoryTable("");
    }//GEN-LAST:event_addCatgorySubMenuActionPerformed

    private void listCatgorySubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCatgorySubMenuActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "categoryPanel");
        this.refreshCategoryTable("");
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
        addCategory.setLocationRelativeTo(null);
        addCategory.setVisible(true);
        this.refreshCategoryTable("");
    }//GEN-LAST:event_editCatgoryActionPerformed

    private void categoryViewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryViewItemActionPerformed

    }//GEN-LAST:event_categoryViewItemActionPerformed

    private void foodItemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodItemMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodItemMenuActionPerformed

    private void viewFoodSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewFoodSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "foodItemPanel");
        this.refreshFoodTable("");
    }//GEN-LAST:event_viewFoodSubMenuActionPerformed

    private void addCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustomerActionPerformed
        JDialog addCustomer = new JDialog(this, "Add Customer", true);
        JPanel customerPanel = new CustomerForm();
        addCustomer.setContentPane(customerPanel);
        addCustomer.pack();
        addCustomer.setLocationRelativeTo(this); // optional, center dialog
        addCustomer.setLocationRelativeTo(null);
        addCustomer.setVisible(true);
        this.refreshCustomerTable("");
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
        this.refreshCategoryTable("");
    }//GEN-LAST:event_deleteCategoryActionPerformed

    private void addFoodSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFoodSubMenuActionPerformed
        JDialog addFoodItem = new JDialog(this, "Add FoodItem", true);
        JPanel foodItem = new FoodItem();
        addFoodItem.setContentPane(foodItem);
        addFoodItem.pack();
        addFoodItem.setLocationRelativeTo(null);
        addFoodItem.setVisible(true);
        this.refreshFoodTable("");
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
        editFood.setLocationRelativeTo(null);
        editFood.setVisible(true);
        this.refreshFoodTable("");
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
            this.refreshFoodTable("");
        }
    }//GEN-LAST:event_deleteFoodActionPerformed

    private void addUserSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserSubMenuActionPerformed
        JDialog addUser = new JDialog(this, "Add User", true);
        JPanel userPanel = new userPanel();
        addUser.setContentPane(userPanel);
        addUser.pack();
        addUser.setLocationRelativeTo(null);
        addUser.setVisible(true);
        this.refreshUserTable("");
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
        addUser.setLocationRelativeTo(null);
        addUser.setVisible(true);
        this.refreshUserTable("");
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
            this.refreshUserTable("");
        }
    }//GEN-LAST:event_deleteUserActionPerformed

    private void listUserSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listUserSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "userPanel");
        this.refreshUserTable("");
    }//GEN-LAST:event_listUserSubMenuActionPerformed

    private void listCustomerSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCustomerSubMenuActionPerformed
        CardLayout cl = (CardLayout) adminPanelWrapper.getLayout();
        cl.show(adminPanelWrapper, "customerPanel");
        this.refreshCustomerTable("");
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
        editCustomer.setLocationRelativeTo(null);
        editCustomer.setVisible(true);

        this.refreshCustomerTable("");
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
            this.refreshCustomerTable("");
        }
    }//GEN-LAST:event_deleteCustomerActionPerformed

    private void customerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerSearchActionPerformed

    private void backToSalePageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToSalePageActionPerformed
        SalePage salePage = new SalePage();
        salePage.setLocationRelativeTo(null);
        salePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backToSalePageActionPerformed

    private void logOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutActionPerformed
        Login login = new Login();
        UserController.authenticatedUser = null;
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logOutActionPerformed

    private void categoryPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_categoryPanelKeyPressed
        
    }//GEN-LAST:event_categoryPanelKeyPressed

    private void foodSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_foodSearchCaretUpdate
        String searchText = foodSearch.getText();
        refreshFoodTable(searchText);
    }//GEN-LAST:event_foodSearchCaretUpdate

    private void categorySearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_categorySearchCaretUpdate
        String searchText = categorySearch.getText();
        refreshCategoryTable(searchText);
    }//GEN-LAST:event_categorySearchCaretUpdate

    private void searchUserCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchUserCaretUpdate
        String searchText = searchUser.getText();
        refreshUserTable(searchText);
    }//GEN-LAST:event_searchUserCaretUpdate

    private void customerSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_customerSearchCaretUpdate
        String searchText = customerSearch.getText();
        refreshCustomerTable(searchText);
    }//GEN-LAST:event_customerSearchCaretUpdate

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
    private javax.swing.JMenuItem backToSalePage;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JTextField categorySearch;
    private javax.swing.JTable categoryTable;
    private javax.swing.JMenu categoryViewItem;
    private javax.swing.JMenu customerMenu;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JTextField customerSearch;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel dCatgoryTotal;
    private javax.swing.JLabel dCustomerTotal;
    private javax.swing.JLabel dFoodTotal;
    private javax.swing.JLabel dOrderTotal;
    private javax.swing.JLabel dUserTotal;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem listCatgorySubMenu;
    private javax.swing.JMenuItem listCustomerSubMenu;
    private javax.swing.JMenuItem listUserSubMenu;
    private javax.swing.JMenuItem logOut;
    private javax.swing.JMenu navigate;
    private javax.swing.JTextField searchUser;
    private javax.swing.JMenu userMenu;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTable userTable;
    private javax.swing.JMenuItem viewFoodSubMenu;
    private javax.swing.JPanel welcomepage;
    // End of variables declaration//GEN-END:variables
}
