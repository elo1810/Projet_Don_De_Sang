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
        //Récupérer les différents champs comme qd on a crée ORM_AO1 , le plus important NTE 
        NTE nte = message.getNTE();
        String alert = nte.getNte3_Comment(0).getValue(); 
        
        //char groupe ;
        //groupe = alert.charAt(0); 
        
        //char rhesus; 
        //rhesus = alert.charAt(2); 
        
        System.out.println(alert); 
        
        //On va vérifier si notre personne existe déjà
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
        /*
        PersonJpaController personCtrl = new PersonJpaController (emfac); 
        Utiliser la méthode findDuplicate pour vérifier si la personne qu'on vient de recevoir existe déjà
        Person duplicate = personCtrl.findDuplicate(p); 
        if(duplicate == null)
        {
            PatientJpaController patientCtrl = new PatientJpaController(emfac); 

            //la personne existe pas encore donc on peut mtn l'ajouter 
            personCtrl.create(p);
            Patient pat = new Patient();
            pat.setIdperson(p); 
            pat.setStatus("active");
            patientCtrl.create(pat); 
        }
        
        //String encodedMessage = new DefaultHapiContext().getPipeParser().encode(t);
        //On affiche le message qu'on a recu et qu'on a bien traité le message 
        //System.out.println("Received message:\n" + encodedMessage + "\n\n"); 
         try {
          	return t.generateACK();
         } catch (IOException e) {
              throw new HL7Exception(e);
         }
*/
         //Sinon on pourrait renvoyer un message d'erreur si ca ne correspond pas à ce qu'on voudrait recevoir
         
         
         return null; 
         //return t.generateACK();
    }

    @Override
    public boolean canProcess(Message t) {
        return true; 
    }
   
    
}
