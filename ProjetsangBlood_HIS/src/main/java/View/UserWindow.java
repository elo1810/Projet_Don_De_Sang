/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.ManJpaController;
import Controller.PersonJpaController;
import Controller.WomanJpaController;
import Model.Man;
import Model.Person;
import Model.Woman;
import Service.CalendrierService;
import Service.EligibilityServices;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adela
 */
public class UserWindow extends javax.swing.JFrame {

    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final ManJpaController manCtrl = new ManJpaController(emfac);
    private final WomanJpaController womanCtrl = new WomanJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    private Person person;
    /**
     * 
     * Creates new form UserWindow
     */
    public UserWindow(Person p) {
        initComponents();
        this.person=p;
        EligibilityServices es = new EligibilityServices(); 
        
        if (person.getFlag() & es.findElibigility(person)){ //double précaution de check d'éligibilité au cas ou édit du profil depuis les flags
            messageLabel.setText("Hi " +person.getFirstName()+ "! Your blood is needed ! Go give it ! ");
        }
        
        if (es.findElibigility(person) & !person.getFlag()){
            messageLabel.setText("Hi " +person.getFirstName()+ "! Your blood type is not critical but you are eligible to give it ! ");
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

        editprofilButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        editprofilButton.setText("Edit Profil");
        editprofilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editprofilButtonActionPerformed(evt);
            }
        });

        messageLabel.setText("You are not eligible to give your blood so far ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(271, Short.MAX_VALUE)
                .addComponent(editprofilButton)
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(messageLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(editprofilButton)
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addGap(56, 56, 56)
                .addComponent(messageLabel)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editprofilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editprofilButtonActionPerformed
        this.dispose();
        Man m = manCtrl.findByIdPerson(person);
        Woman w =womanCtrl.findByIdPerson(person);
        if (m != null){
            AddmanWindow amw = new AddmanWindow();
            amw.setMan(m);
            amw.setVisible(true);
        }
        
        if (w != null){
            AddwomanWindow aww = new AddwomanWindow();
            aww.setWoman(w);
            aww.setVisible(true);
        }
    }//GEN-LAST:event_editprofilButtonActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editprofilButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel messageLabel;
    // End of variables declaration//GEN-END:variables
}
