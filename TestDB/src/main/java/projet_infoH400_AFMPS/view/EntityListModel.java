/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_infoH400_AFMPS.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Adrien Foucart
 */
/*
Classe du package View qui est une sous classe de abstract list model
Elle sert à : donner la taille de la liste (getSize), donner l'élément trouvé à 
un certain indexe (getElement) : en gros ça ce comporte comme une liste dans laquelle
on peut trouver des choses plus complexes que des strings car cette liste peut prendre
différents types d'objets car elle utilise un template <T> : en effet, le T peut être remplacé par n'importe quelle classe au moment 
où on crée une instance d'un objet EntityListModel : par exemple, la classe Patient
*/
public class EntityListModel<T> extends AbstractListModel {
    
    private List<T> entities;
    /*
    NB : dès qu'on essaye de convertir quelque chose en chaîne de caractère en Java,
    on appelle la méthode tostring : c'est pourquoi dans toutes les classes du 
    modèle, on a toujours une méthode toString() qui va déterminer comment est-ce
    que cet objet va être représenté par une chaîne de caractères
    */
    public EntityListModel(List<T> entities){
        if( entities == null ){
            entities = new ArrayList();
        }
        this.entities = entities;
    }
    
    public void setList(List<T> entities){
        this.entities = entities;
    }
    
    public List<T> getList(){
        return entities;
    }
    
    @Override//override : méthodes devant être implémentées dans la classe du à l'héritage
    public int getSize() {
        return entities.size();
    }

    @Override
    public Object getElementAt(int index) {
        return entities.get(index);
    }
    
}
