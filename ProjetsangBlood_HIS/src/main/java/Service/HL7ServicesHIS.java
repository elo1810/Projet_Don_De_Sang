/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.datatype.EI;
import ca.uhn.hl7v2.model.v23.group.ORM_O01_ORDER;
import ca.uhn.hl7v2.model.v23.message.ORM_O01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.NTE;
import ca.uhn.hl7v2.model.v23.segment.ORC;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gracejost
 */


public class HL7ServicesHIS {
   
  public void startServer()
    {
        int port = 54321; // The port où on écouter différent de celui utilisé pour DICOM 
        boolean useTls = false; // Should we use TLS/SSL?
        HapiContext context = new DefaultHapiContext();
        HL7Service server = context.newServer(port, useTls); //Instance de HL7Service 
        
         //Création de la class ReceivingApplication
        ReceivingApplication<Message> handler = (ReceivingApplication<Message>) new ORMReceiverApplication();
        server.registerApplication("ORM", "O01", handler);
        try {
            server.startAndWait();
        } catch (InterruptedException ex) {
            Logger.getLogger(HL7ServicesHIS.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }  

    
}
