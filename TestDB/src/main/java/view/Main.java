/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.mycompany.testdb.StockdesangJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Stockdesang;

/**
 *
 * @author Elo
 */
public class Main {
    public static void main(String[] args){
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("test_PU");
        StockdesangJpaController ctrl = new StockdesangJpaController(emfac);
        Stockdesang stock = new Stockdesang();
        stock.setGroupe("A");
        stock.setQuantite(2);
        stock.setRhesus("+");
        
        ctrl.create(stock);
    }
}
