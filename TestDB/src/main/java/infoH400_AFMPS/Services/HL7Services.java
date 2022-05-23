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

/*  Création du message de type ORM_O01 
    C'est AFMPS qui envoie le message 
    On identifie le message qu'on est en train d'envoyer pour, 
    lorsque l'on reçoit la réponse, on sache à quel message on fait référence
    Le message contient 3 segements : MSH , NTE et ORC 
    La fonction retourne un orm 
*/

public class HL7Services {
    public ORM_O01 create_ORM_O01(Stockdesang stockdesang){
        ORM_O01 orm = null;
        try {
            orm = new ORM_O01();
            orm.initQuickstart("ORM", "O01", "P");
            MSH mshSegment = orm.getMSH();
            mshSegment.getSendingApplication().getNamespaceID().setValue("AFMPS");
            mshSegment.getSequenceNumber().setValue("");
            NTE nte = orm.getNTE();
            nte.getNte3_Comment(0).setValue(stockdesang.getGroupe()+" "+stockdesang.getRhesus()+" est en manque");
            ORM_O01_ORDER orm_O01_order = orm.getORDER();
            ORC orc = orm_O01_order.getORC();
            orc.getOrc1_OrderControl().setValue("NW");    
        } catch (HL7Exception ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    return orm;
    }
    
/*
    Fonction qui envoie le message de type ORM_O01 créé au dessus , sur un certain port
    On utilise un server HL7 pour l'envoie du message
    La connexion est créée ( et elle n'est pas sécurisée), on y donne les informations pour une bonne connexion
    Ensuite, la connection est initiée 
    On envoie le message de type ORM et on récupère la réponse - sous forme de string
    Enfin, on affiche la réponse dans le terminal 
    
*/
    public void sendORM_O01(ORM_O01 orm, String host, int port)
    {
        try {
            HapiContext context = new DefaultHapiContext();
            Connection connection = context.newClient(host, port, false);
            Initiator initiator = connection.getInitiator();
            Message response = initiator.sendAndReceive(orm);
            Parser p = context.getPipeParser();
            String responseString = p.encode(response);
            System.out.println("Received response:\n" + responseString);
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
