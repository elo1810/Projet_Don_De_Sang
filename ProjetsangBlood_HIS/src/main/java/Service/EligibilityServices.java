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
    
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("com.mycompany_ProjetsangBlood_HIS_jar_1.0-SNAPSHOTPU");
    private final WomanJpaController womanCtrl = new WomanJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    
    private final int weight = 50;
    private final int height = 150;
    private final boolean sickness = false; 
    private final Date datelimitemax = CalendrierService.MakeDifference(18);
    private final Date datelimitemin = CalendrierService.MakeDifference(66);
    
    public boolean findElibigility(Person person){ 

        if (person.getWeight()> weight & person.getHeight()>height & person.getSickness()==sickness & person.getDateOfBirth().after(datelimitemin) & person.getDateOfBirth().before(datelimitemax)){
            Woman w = womanCtrl.findByIdPerson(person);
                if (w != null){
                    return (!w.getIsPregnant());
                }
                else {
                    return true;
                }
        }
        else{
            return false; 
        }  
    }
    
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
            personCtrl.edit(person);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EligibilityServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EligibilityServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
