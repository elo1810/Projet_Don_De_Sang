/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.PersonJpaController;
import Controller.WomanJpaController;
import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Model.Woman;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Adela
 */
public class AddwomanWindow extends javax.swing.JFrame {

    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final WomanJpaController womanCtrl = new WomanJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    
    private static final Logger LOGGER = LogManager.getLogger(AddwomanWindow.class.getName());
    
    Woman wmn = null;
    
    /**
     * Creates new form AddwomanWindow
     */
    public AddwomanWindow() {
        initComponents();
    }
    
    public void setWoman(Woman wmn){
        this.wmn= wmn;
        
        addPersonPannel.setPerson(wmn.getIdPerson());
        enceinteCheckbox.setSelected(wmn.getIsPregnant());
        
    }
    
    public Woman getWoman(){
        updateWoman();
                
        return wmn;
    }
    
    public void updateWoman(){
        if( wmn == null ){
            wmn = new Woman();
        }
        
        wmn.setIdPerson(addPersonPannel.getPerson());
        wmn.setIsPregnant(enceinteCheckbox.isSelected());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        enceinteCheckbox = new javax.swing.JCheckBox();
        saveButton = new javax.swing.JButton();
        addPersonPannel = new View.AddPersonPannel();

        jCheckBox2.setText("jCheckBox2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel5.setText("Enceinte");

        enceinteCheckbox.setText("Enceinte");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(enceinteCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(saveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(addPersonPannel, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addPersonPannel, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(enceinteCheckbox)
                    .addComponent(saveButton))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        updateWoman();

        // Create person if necessary:
        if(wmn.getIdPerson().getId() == null ){
            personCtrl.create(wmn.getIdPerson());
            LOGGER.debug("Created new person (id = %d)".formatted(wmn.getIdPerson().getId()));
        }
        // Create patient if necessary
        if( wmn.getId() == null ){
            womanCtrl.create(wmn);
            LOGGER.debug("Created new woman (id = %d)".formatted(wmn.getId()));
        }

        // Save changes
        try {
            personCtrl.edit(wmn.getIdPerson());
            womanCtrl.edit(wmn);
            LOGGER.debug("Edited woman (id = %d)".formatted(wmn.getId()));
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            LOGGER.error("Couldn't edit woman", ex);
        } catch (Exception ex){
            LOGGER.error("Couldn't edit woman", ex);
        }

        this.dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddwomanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddwomanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddwomanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddwomanWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddwomanWindow().setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private View.AddPersonPannel addPersonPannel;
    private javax.swing.JCheckBox enceinteCheckbox;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
