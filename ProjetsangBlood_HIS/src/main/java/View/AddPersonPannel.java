/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Model.Person;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Adela
 */
public class AddPersonPannel extends javax.swing.JPanel {

    /**
     * Creates new form AddPersonPannel
     */
    
    private Person person = null;
    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    
    private static final Logger LOGGER = LogManager.getLogger(AddPersonPannel.class.getName());
    
    /**
     * Creates new form AddPersonPanel
     */
    public AddPersonPannel() {
        initComponents();
    }
    
    public void setPerson(Person person){
        this.person = person;
        
        lastnameTextField.setText(person.getLastName());
        firstnameTextField.setText(person.getFirstName());
        dobTextField.setText(fmt.format(person.getDateOfBirth()));
        heightTextField.setText(String.valueOf(person.getHeight()));
        weightTextField.setText(String.valueOf(person.getWeight())); 
        vihCheckBox.setSelected(person.getSickness()); 
        btTextField.setText(person.getBloodType());
        passwordTextField.setText(person.getPassword());
        
    }
    
    public Person getPerson(){
        if( person == null ){
            person = new Person();
        }
        
        person.setLastName(lastnameTextField.getText());
        person.setFirstName(firstnameTextField.getText());
        person.setHeight(Integer.valueOf(heightTextField.getText()));
        person.setWeight(Integer.valueOf(weightTextField.getText()));
        person.setBloodType(btTextField.getText());
        person.setSickness(vihCheckBox.isSelected());
        person.setPassword(passwordTextField.getText()); 
        
        try {
            person.setDateOfBirth(fmt.parse(dobTextField.getText()));
        } catch (ParseException ex) {
            LOGGER.error("Error setting person date of birth", ex);
        }
        
        return person;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lastnameTextField = new javax.swing.JTextField();
        firstnameTextField = new javax.swing.JTextField();
        dobTextField = new javax.swing.JTextField();
        heightTextField = new javax.swing.JTextField();
        weightTextField = new javax.swing.JTextField();
        vihCheckBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        btTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        passwordTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setText("Nom");

        jLabel2.setText("Prénom");

        jLabel3.setText("Date de naissance");

        jLabel4.setText("Taille");

        jLabel5.setText("Poids");

        jLabel6.setText("Souffrez-vous d'une maladie ?");

        vihCheckBox.setText("VIH");

        jLabel7.setText("Groupe Sanguin ");

        jLabel8.setText("Mot de passe");

        jLabel9.setText("(yyyy-mm-jj)");

        jLabel10.setText("(cm)");

        jLabel11.setText("(kg)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(vihCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(firstnameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                    .addComponent(dobTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(heightTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(weightTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lastnameTextField)))
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lastnameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(firstnameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dobTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(heightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(vihCheckBox))
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField btTextField;
    private javax.swing.JTextField dobTextField;
    private javax.swing.JTextField firstnameTextField;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField lastnameTextField;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JCheckBox vihCheckBox;
    private javax.swing.JTextField weightTextField;
    // End of variables declaration//GEN-END:variables
}
