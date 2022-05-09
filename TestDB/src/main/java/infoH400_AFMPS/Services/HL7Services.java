/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infoH400_AFMPS.Services;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Stockdesang;

/**
 *
 * @author gracejost
 */


public class HL7Services {
    public ORM_O01 send_ORM_O01(Stockdesang stockdesang){
        ORM_O01 orm = null;
        try {
            orm = new ORM_O01();
            orm.initQuickstart("ORM", "O01", "P");
            MSH mshSegment = orm.getMSH();
            mshSegment.getSendingApplication().getNamespaceID().setValue("AFMPS");//on dit qui envoie le message
            mshSegment.getSequenceNumber().setValue("MSG00001");//identifie le message qu'on est en train d'envoyer pour, lorsque l'on reçoit la réponse, on sache à quel message on fait référence
            
            NTE nte = orm.getNTE();
            nte.getNte3_Comment(0).setValue(stockdesang.getGroupe()+" "+stockdesang.getRhesus()+" est en manque");
            
            ORM_O01_ORDER orm_O01_order = orm.getORDER();
            ORC orc = orm_O01_order.getORC();
            orc.getOrc3_FillerOrderNumber().getEi3_UniversalID().setValue("test"); //.setValue("NW"); // Comment peut on remplir le segment ORC du message et que veut dire conditionnel ?
            
            //Sinon manuellement     
            
        } catch (HL7Exception ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    return orm;
    }
    
    public void sendORM_O01(ORM_O01 orm, String host, int port)
    {
        try {
            //On commence par créer le context HL7 :
            HapiContext context = new DefaultHapiContext();
            // A connection object represents a socket attached to an HL7 server
            
            Connection connection = context.newClient(host, port, false);
            // le troisième argument indique si on utilise une connexion sécurisée avec TLS ou non, ici ce n'est pas le cas donc on a just mis False
            //faire le new client c'est l'équivalent de ce que l'on faisait dans le test pannel quand on crée la sending connexion ( une connexion vers le recieving connexion : on donne les informations de connexion
            //c à d le host, le port que l'on a passé en paramètre de la fonction send ORM_O01 le host ici sera localhost
            
            // The initiator is used to transmit unsolicited messages
            //une fois la connexion créée : on initie la connexion : on vérifie que l'on peut bien se connecter au serveur 
            Initiator initiator = connection.getInitiator();
            //on envoie le message ADT et on récupère la réponse
            Message response = initiator.sendAndReceive(orm);
            Parser p = context.getPipeParser();//le parseur p est ce qu'on a besoin pour afficher la réponse dans le teminal
            String responseString = p.encode(response);//encode récupère le string
            System.out.println("Received response:\n" + responseString);
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
            //On va afficher cette réponse dans le terminal
        }
 
    }
    
}
