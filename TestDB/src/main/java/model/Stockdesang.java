/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Elo
 */

/*
    On retouve les noms des colonnes de notre base de données
*/
@Entity
@Table(name = "stockdesang")
@NamedQueries({
    @NamedQuery(name = "Stockdesang.findAll", query = "SELECT s FROM Stockdesang s"),
    @NamedQuery(name = "Stockdesang.findById", query = "SELECT s FROM Stockdesang s WHERE s.id = :id"),
    @NamedQuery(name = "Stockdesang.findByGroupe", query = "SELECT s FROM Stockdesang s WHERE s.groupe = :groupe"),
    @NamedQuery(name = "Stockdesang.findByRhesus", query = "SELECT s FROM Stockdesang s WHERE s.rhesus = :rhesus"),
    @NamedQuery(name = "Stockdesang.findByQuantite", query = "SELECT s FROM Stockdesang s WHERE s.quantite = :quantite")})

public class Stockdesang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "groupe")
    private String groupe;
    @Basic(optional = false)
    @Column(name = "rhesus")
    private String rhesus;
    @Basic(optional = false)
    @Column(name = "quantite")
    private int quantite;

    public Stockdesang() {
    }

    public Stockdesang(Integer id) {
        this.id = id;
    }

    public Stockdesang(Integer id, String groupe, String rhesus, int quantite) {
        this.id = id;
        this.groupe = groupe;
        this.rhesus = rhesus;
        this.quantite = quantite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Stockdesang)) {
            return false;
        }
        Stockdesang other = (Stockdesang) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
/*
    Cette méthode permet d'imprimer les éléments de la data base sous le format : 
    Groupe Rhésus Quantité 
    Exemple : A+ (Quantité : 256)
    
*/
    @Override
    public String toString() {
        //return "model.Stockdesang[ id=" + id + " ]" + groupe + rhesus + "(" + quantite + ")";
        return groupe + rhesus + " ( Quantité : " + quantite + ")";
    }
    
}
