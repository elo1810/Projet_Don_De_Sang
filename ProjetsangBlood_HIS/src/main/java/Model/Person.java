/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Adela
 */
@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = "Person.findByFirstName", query = "SELECT p FROM Person p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Person.findByLastName", query = "SELECT p FROM Person p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Person.findByDateOfBirth", query = "SELECT p FROM Person p WHERE p.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Person.findByWeight", query = "SELECT p FROM Person p WHERE p.weight = :weight"),
    @NamedQuery(name = "Person.findByHeight", query = "SELECT p FROM Person p WHERE p.height = :height"),
    @NamedQuery(name = "Person.findByBloodType", query = "SELECT p FROM Person p WHERE p.bloodType = :bloodType"),
    @NamedQuery(name = "Person.findBySickness", query = "SELECT p FROM Person p WHERE p.sickness = :sickness"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
    @NamedQuery(name = "Person.findByFlag", query = "SELECT p FROM Person p WHERE p.flag = :flag"),
    @NamedQuery(name = "Person.findElibigility", query = "SELECT p FROM Person p WHERE p.weight > :weight AND p.height > :height AND p.sickness = false AND p.dateOfBirth < :datelimitemax AND p.dateOfBirth > :datelimitemin" ),
    @NamedQuery(name = "Person.findByLogIn", query = "SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.password = :password" )})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Basic(optional = false)
    @Column(name = "Weight")
    private int weight;
    @Basic(optional = false)
    @Column(name = "Height")
    private int height;
    @Basic(optional = false)
    @Column(name = "BloodType")
    private String bloodType;
    @Basic(optional = false)
    @Column(name = "Sickness")
    private boolean sickness;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "Flag")
    private boolean flag;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson")
    private Collection<Woman> womanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson")
    private Collection<Man> manCollection;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String firstName, String lastName, Date dateOfBirth, int weight, int height, String bloodType, boolean sickness, String password, boolean flag) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.sickness = sickness;
        this.password = password;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean getSickness() {
        return sickness;
    }

    public void setSickness(boolean sickness) {
        this.sickness = sickness;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Collection<Woman> getWomanCollection() {
        return womanCollection;
    }

    public void setWomanCollection(Collection<Woman> womanCollection) {
        this.womanCollection = womanCollection;
    }

    public Collection<Man> getManCollection() {
        return manCollection;
    }

    public void setManCollection(Collection<Man> manCollection) {
        this.manCollection = manCollection;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Person[ id=" + id + " ]";
    }
    
}
