/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Controller.ManJpaController;
import Controller.PersonJpaController;
import Controller.WomanJpaController;
import Controller.exceptions.NonexistentEntityException;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.datatype.FT;
import ca.uhn.hl7v2.model.v23.message.ORM_O01;
import ca.uhn.hl7v2.model.v23.segment.NTE;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import Model.Person;
import Model.Woman; 
import Model.Man; 

/**
 *
 * @author Elo
 */

//class to display the received ADT_A01 message on the terminal.
public class ORMReceiverApplication implements ReceivingApplication<Message> {

    
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final ManJpaController manCtrl = new ManJpaController(emfac);
    private final WomanJpaController womanCtrl = new WomanJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    
    @Override
    public Message processMessage(Message t, Map<String, Object> map) throws ReceivingApplicationException, HL7Exception {
        String encodedMessage = new DefaultHapiContext().getPipeParser().encode(t);
        System.out.println("Received message:\n" + encodedMessage + "\n\n"); 


        //On caste le message ORM O01 (car class lié directement à ce type de message) - message class au dessus de tous les messages 
        ORM_O01 message = (ORM_O01) t; 
        //Récupérer les différents champs comme qd on a crée ORM_O01 , le plus important NTE 
        NTE nte = message.getNTE();
        String alert = nte.getNte3_Comment(0).getValue(); 

        String groupe = "";
        char rhesus ; 
        String groupeRhesus = "";
        if(alert.charAt(1)=='B')
        {
            groupe += alert.charAt(0);
            groupe += alert.charAt(1);
            rhesus = alert.charAt(3);
            
            groupeRhesus = groupe + rhesus ; 
        }
        
        if(alert.charAt(1)==' ')
        {
            groupe += alert.charAt(0);
            rhesus = alert.charAt(2);
            
            groupeRhesus = groupe + rhesus ; 
        }
        System.out.println(groupeRhesus);
       
        personCtrl.resetFlags(); //Reset de tous les flags pour simuler un dynamisme - plus de besoin des autres quand besoin d'un certain groupe (sinon en quelques queries tout le monde est à un) 
        List<Person> allbloodtype = personCtrl.findBloodType(groupeRhesus); //recherche de toutes les personnes ayant le groupe sanguin désiré 
        EligibilityServices es = new EligibilityServices(); 
        
        //Changement de flag de toutes les personne ayant le bon groupe sanguin et étant éligibles
        //Un flag signifie que la personn, à sa connection, doit recevoir un message pour dire que son groupe de sang est nécessaire. 
        //Le flag est donc set à 1 lors de la réception d'un message HL7 pour toutes les personnes de ce groupe sanguin respectant les critères l'éligibilité
        for (int i=0; i<allbloodtype.size(); i++){
            Person p = allbloodtype.get(i); 
            es.checkEligibility(p);
        }
 
        try {
            return t.generateACK(); //retour du message d'ackowledgement
        } catch (IOException ex) {
            Logger.getLogger(ORMReceiverApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public boolean canProcess(Message t) {
        return true; 
    }
   
    
}
