/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.datatype.FT;
import ca.uhn.hl7v2.model.v23.message.ORM_O01;
import ca.uhn.hl7v2.model.v23.segment.NTE;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Elo
 */

//class to display the received ADT_A01 message on the terminal.
public class ORMReceiverApplication implements  ReceivingApplication<Message> {

    @Override
    public Message processMessage(Message t, Map<String, Object> map) throws ReceivingApplicationException, HL7Exception {
        String encodedMessage = new DefaultHapiContext().getPipeParser().encode(t);
        System.out.println("Received message:\n" + encodedMessage + "\n\n"); 


        //On caste le message ORM O01 (car class lié directement à ce type de message) - message class au dessus de tous les messages 
        ORM_O01 message = (ORM_O01) t; 
        //Récupérer les différents champs comme qd on a crée ORM_O01 , le plus important NTE 
        NTE nte = message.getNTE();
        String alert = nte.getNte3_Comment(0).getValue(); 
        System.out.println(alert); 

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
        //creer une query dans le controller patient (model) qui va find tous les patients avec le bon groupe et le bon rhésus
        //ajouter mfac ici pour pouvoir utiliser le controller du patient pour appeler cette query 
        
        System.out.println(alert); 
 
        try {
            return t.generateACK();
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
