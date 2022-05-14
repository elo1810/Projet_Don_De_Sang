/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Adela
 */
@Entity
@Table(name = "woman")
@NamedQueries({
    @NamedQuery(name = "Woman.findAll", query = "SELECT w FROM Woman w"),
    @NamedQuery(name = "Woman.findById", query = "SELECT w FROM Woman w WHERE w.id = :id"),
    @NamedQuery(name = "Woman.findByIsPregnant", query = "SELECT w FROM Woman w WHERE w.isPregnant = :isPregnant")})
public class Woman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "IsPregnant")
    private boolean isPregnant;
    @JoinColumn(name = "IdPerson", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Person idPerson;

    public Woman() {
    }

    public Woman(Integer id) {
        this.id = id;
    }

    public Woman(Integer id, boolean isPregnant) {
        this.id = id;
        this.isPregnant = isPregnant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Woman)) {
            return false;
        }
        Woman other = (Woman) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Woman[ id=" + id + " ]";
    }
    
}
