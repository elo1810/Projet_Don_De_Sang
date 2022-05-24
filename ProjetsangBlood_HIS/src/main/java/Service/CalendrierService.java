/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import java.util.Calendar;
import java.util.Date;



/**
 *
 * @author Adela
 */
public class CalendrierService {
    //Classe servant à anipuler les dates. Permet de calculer les dates de naissances respectant les critères d'éligibilité pour les comparer à la date de 
    //naissance des volontaires. 
    
    public static Date MakeDifference(int i){
        Calendar cal = Calendar.getInstance(); //instance d'un calendrier 
        cal.add(Calendar.YEAR,-i); //enlever i années depuis la date d'aujourd'hui. 
        return cal.getTime(); //Return de la date différence. 
    }
}