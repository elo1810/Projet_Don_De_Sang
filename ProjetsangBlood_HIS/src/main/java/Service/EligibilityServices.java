/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Controller.PersonJpaController;
import Controller.WomanJpaController;
import Controller.exceptions.NonexistentEntityException;
import Model.Person;
import Model.Woman;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adela
 */
public class EligibilityServices {
    
    /*
    Cette classe va servir à vérifier si les volontaires sont éligibles au don de sang. Grace à cette classe, pour ajouter des critère, tout est centré ici.
    */
    
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final WomanJpaController womanCtrl = new WomanJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    
    
    //critères d'éligibilité
    private final int weight = 50;
    private final int height = 150;
    private final boolean sickness = false; 
    private final Date datelimitemax = CalendrierService.MakeDifference(18);
    private final Date datelimitemin = CalendrierService.MakeDifference(66);
    
    
    //Fonction servant à vérifier l'éligibilité d'une personne et à retourner un boolean 
    public boolean findElibigility(Person person){ 
        
        //vérification de l'égibilité de la personne
        if (person.getWeight()> weight & person.getHeight()>height & person.getSickness()==sickness & person.getDateOfBirth().after(datelimitemin) & person.getDateOfBirth().before(datelimitemax)){
            //vérification si la femme correspondante à la personne est aussi éligible via les critères féminins
            //(pourrait être élargi aux critères spécifiques aux hommes si il y en a par la suite
            Woman w = womanCtrl.findByIdPerson(person);
                if (w != null){
                    return (!w.getIsPregnant()); //si c'est une femme, on vérifie si elle est enceinte

                }
                else {
                    return true; //Si ce n'est pas une femme, alors on est bon parce que pour le moment pas de critère pour les hommes 

                }
        }
        else{
            return false; //si la personne n'est pas éligible, return false
        }  
    }
    
    /*fonction pour vérifier l'éginbilité et changer le flag de la personne si elle est éligible. Cette fonction est utilisée dans l'ORMRReceiverApplication pour changer le flag des personnes 
    dont le groupe sanguin est demandé. Même idée qu'au dessus mais changement du flag au lieu de retourner un booleen 
    */
    public void checkEligibility(Person person){
        if (person.getWeight()> weight & person.getHeight()>height & person.getSickness()==sickness & person.getDateOfBirth().after(datelimitemin) & person.getDateOfBirth().before(datelimitemax)){
            Woman w = womanCtrl.findByIdPerson(person);
            if (w != null){
                if(w.getIsPregnant()){
                    person.setFlag(false);
                }
                else {
                    person.setFlag(true);
                }
            }
            else {
                person.setFlag(true);
            }
             
        }
        else {
            person.setFlag(false);
        }
        
        try {
            personCtrl.edit(person); //misé à jour du flag de la personne dans la database. 
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EligibilityServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EligibilityServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
