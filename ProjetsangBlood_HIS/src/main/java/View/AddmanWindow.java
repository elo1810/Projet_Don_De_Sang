/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.PersonJpaController;
import Controller.ManJpaController;
import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Model.Man;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Adela
 */
public class AddmanWindow extends javax.swing.JFrame {

    
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final ManJpaController manCtrl = new ManJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    
    private static final Logger LOGGER = LogManager.getLogger(AddwomanWindow.class.getName());
    
    Man man = null;
    /**
     * Creates new form AddmanWindow
     */
    public AddmanWindow() {
        initComponents();
    }

    
    public void setMan(Man man){
        this.man= man;
        
        addPersonPannel.setPerson(man.getIdPerson());
    }
    
    public Man getMan(){
        updateMan();
                
        return man;
    }
    
    public void updateMan(){
        if( man == null ){
            man = new Man();
        }
        
        man.setIdPerson(addPersonPannel.getPerson());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveButton = new javax.swing.JButton();
        addPersonPannel = new View.AddPersonPannel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(addPersonPannel, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addPersonPannel, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        updateMan();

        // Create person if necessary:
        if(man.getIdPerson().getId() == null ){
            personCtrl.create(man.getIdPerson());
            LOGGER.debug("Created new person (id = %d)".formatted(man.getIdPerson().getId()));
        }
        // Create patient if necessary
        if( man.getId() == null ){
            manCtrl.create(man);
            LOGGER.debug("Created new man (id = %d)".formatted(man.getId()));
        }

        // Save changes
        try {
            personCtrl.edit(man.getIdPerson());
            manCtrl.edit(man);
            LOGGER.debug("Edited man (id = %d)".formatted(man.getId()));
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            LOGGER.error("Couldn't edit man", ex);
        } catch (Exception ex){
            LOGGER.error("Couldn't edit man", ex);
        }

        UserWindow userWindow = new UserWindow(man.getIdPerson());
        userWindow.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private View.AddPersonPannel addPersonPannel;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
