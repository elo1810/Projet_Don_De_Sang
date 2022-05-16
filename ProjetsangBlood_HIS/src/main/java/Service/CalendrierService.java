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
    
    public CalendrierService(){
        
    }
    
    public Date MakeDifference(int i){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,-i);
        return cal.getTime();
    }
}
