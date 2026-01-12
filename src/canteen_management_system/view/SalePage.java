/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package canteen_management_system.view;

import canteen_management_system.controller.CategoryController;
import canteen_management_system.model.CategoryModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author User
 */
public class SalePage extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalePage.class.getName());
    private CategoryController categoryController = new CategoryController();

    /**
     * Creates new form SalePage
     */
    public SalePage() {
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
                this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        this.categoryController.addCategory("Drinks", "");
        this.categoryController.addCategory("Snacks", "");
        this.categoryController.addCategory("Fast Food", "");
        this.categoryController.addCategory("Desserts", "");
        this.categoryController.addCategory("Fruits", "");
        initComponents();
        this.setCategoryList();
    }

    public void setCategoryList() {
    // Make sure layout is vertical
    categoryPanelWrapper.setLayout(new BoxLayout(categoryPanelWrapper, BoxLayout.Y_AXIS));

    // Remove adding scroll pane here — it should already be in CENTER
    // foodWrapper.add(categoryScrollPanel, BorderLayout.CENTER);

    // Scrollbar policy
    categoryScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Clear existing categories first (optional)
    categoryPanelWrapper.removeAll();

    // Add category panels
    for (CategoryModel c : categoryController.getAllCategoryList()) {
        JPanel productPanel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(c.getCategoryName());
        label.setForeground(Color.WHITE);
        productPanel.add(label);
        productPanel.setBackground(Color.BLUE);

        // Let BoxLayout control height — no fixed preferred size
        productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        categoryPanelWrapper.add(productPanel);
        categoryPanelWrapper.add(Box.createRigidArea(new Dimension(0, 5))); // optional spacing

    }
        // Refresh the panel
    categoryPanelWrapper.revalidate();
    categoryPanelWrapper.repaint();
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
        mainFoodAndOrderWrapper = new javax.swing.JSplitPane();
        orderWrapper = new javax.swing.JPanel();
        foodWrapper = new javax.swing.JPanel();
        categoryScrollPanel = new javax.swing.JScrollPane();
        categoryPanelWrapper = new javax.swing.JPanel();
        foodTableWrapper = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(50);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.2);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(100, 232));

        navigationWrapper.setBackground(new java.awt.Color(204, 204, 255));
        navigationWrapper.setPreferredSize(new java.awt.Dimension(200, 232));

        javax.swing.GroupLayout navigationWrapperLayout = new javax.swing.GroupLayout(navigationWrapper);
        navigationWrapper.setLayout(navigationWrapperLayout);
        navigationWrapperLayout.setHorizontalGroup(
            navigationWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 935, Short.MAX_VALUE)
        );
        navigationWrapperLayout.setVerticalGroup(
            navigationWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(navigationWrapper);

        mainFoodAndOrderWrapper.setDividerLocation(300);
        mainFoodAndOrderWrapper.setDividerSize(0);

        orderWrapper.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout orderWrapperLayout = new javax.swing.GroupLayout(orderWrapper);
        orderWrapper.setLayout(orderWrapperLayout);
        orderWrapperLayout.setHorizontalGroup(
            orderWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        orderWrapperLayout.setVerticalGroup(
            orderWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        mainFoodAndOrderWrapper.setLeftComponent(orderWrapper);

        foodWrapper.setLayout(new java.awt.BorderLayout());

        categoryScrollPanel.setMaximumSize(new java.awt.Dimension(32767, 300));

        categoryPanelWrapper.setBackground(new java.awt.Color(255, 153, 153));
        categoryPanelWrapper.setPreferredSize(new java.awt.Dimension(197, 300));
        categoryPanelWrapper.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        categoryScrollPanel.setViewportView(categoryPanelWrapper);

        foodWrapper.add(categoryScrollPanel, java.awt.BorderLayout.PAGE_START);

        foodTableWrapper.setBackground(new java.awt.Color(255, 204, 204));
        foodTableWrapper.setPreferredSize(new java.awt.Dimension(635, 200));

        javax.swing.GroupLayout foodTableWrapperLayout = new javax.swing.GroupLayout(foodTableWrapper);
        foodTableWrapper.setLayout(foodTableWrapperLayout);
        foodTableWrapperLayout.setHorizontalGroup(
            foodTableWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        foodTableWrapperLayout.setVerticalGroup(
            foodTableWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        foodWrapper.add(foodTableWrapper, java.awt.BorderLayout.CENTER);

        mainFoodAndOrderWrapper.setRightComponent(foodWrapper);

        jSplitPane1.setRightComponent(mainFoodAndOrderWrapper);

        javax.swing.GroupLayout mainSalesWrapperLayout = new javax.swing.GroupLayout(mainSalesWrapper);
        mainSalesWrapper.setLayout(mainSalesWrapperLayout);
        mainSalesWrapperLayout.setHorizontalGroup(
            mainSalesWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainSalesWrapperLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainSalesWrapperLayout.setVerticalGroup(
            mainSalesWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainSalesWrapperLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(mainSalesWrapper);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JPanel foodTableWrapper;
    private javax.swing.JPanel foodWrapper;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane mainFoodAndOrderWrapper;
    private javax.swing.JPanel mainSalesWrapper;
    private javax.swing.JPanel navigationWrapper;
    private javax.swing.JPanel orderWrapper;
    // End of variables declaration//GEN-END:variables
}
