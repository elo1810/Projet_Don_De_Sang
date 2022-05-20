/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Woman;
import java.util.ArrayList;
import java.util.Collection;
import Model.Man;
import Model.Person;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Adela
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) {
        if (person.getWomanCollection() == null) {
            person.setWomanCollection(new ArrayList<Woman>());
        }
        if (person.getManCollection() == null) {
            person.setManCollection(new ArrayList<Man>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Woman> attachedWomanCollection = new ArrayList<Woman>();
            for (Woman womanCollectionWomanToAttach : person.getWomanCollection()) {
                womanCollectionWomanToAttach = em.getReference(womanCollectionWomanToAttach.getClass(), womanCollectionWomanToAttach.getId());
                attachedWomanCollection.add(womanCollectionWomanToAttach);
            }
            person.setWomanCollection(attachedWomanCollection);
            Collection<Man> attachedManCollection = new ArrayList<Man>();
            for (Man manCollectionManToAttach : person.getManCollection()) {
                manCollectionManToAttach = em.getReference(manCollectionManToAttach.getClass(), manCollectionManToAttach.getId());
                attachedManCollection.add(manCollectionManToAttach);
            }
            person.setManCollection(attachedManCollection);
            em.persist(person);
            for (Woman womanCollectionWoman : person.getWomanCollection()) {
                Person oldIdPersonOfWomanCollectionWoman = womanCollectionWoman.getIdPerson();
                womanCollectionWoman.setIdPerson(person);
                womanCollectionWoman = em.merge(womanCollectionWoman);
                if (oldIdPersonOfWomanCollectionWoman != null) {
                    oldIdPersonOfWomanCollectionWoman.getWomanCollection().remove(womanCollectionWoman);
                    oldIdPersonOfWomanCollectionWoman = em.merge(oldIdPersonOfWomanCollectionWoman);
                }
            }
            for (Man manCollectionMan : person.getManCollection()) {
                Person oldIdPersonOfManCollectionMan = manCollectionMan.getIdPerson();
                manCollectionMan.setIdPerson(person);
                manCollectionMan = em.merge(manCollectionMan);
                if (oldIdPersonOfManCollectionMan != null) {
                    oldIdPersonOfManCollectionMan.getManCollection().remove(manCollectionMan);
                    oldIdPersonOfManCollectionMan = em.merge(oldIdPersonOfManCollectionMan);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person persistentPerson = em.find(Person.class, person.getId());
            Collection<Woman> womanCollectionOld = persistentPerson.getWomanCollection();
            Collection<Woman> womanCollectionNew = person.getWomanCollection();
            Collection<Man> manCollectionOld = persistentPerson.getManCollection();
            Collection<Man> manCollectionNew = person.getManCollection();
            List<String> illegalOrphanMessages = null;
            for (Woman womanCollectionOldWoman : womanCollectionOld) {
                if (!womanCollectionNew.contains(womanCollectionOldWoman)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Woman " + womanCollectionOldWoman + " since its idPerson field is not nullable.");
                }
            }
            for (Man manCollectionOldMan : manCollectionOld) {
                if (!manCollectionNew.contains(manCollectionOldMan)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Man " + manCollectionOldMan + " since its idPerson field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Woman> attachedWomanCollectionNew = new ArrayList<Woman>();
            for (Woman womanCollectionNewWomanToAttach : womanCollectionNew) {
                womanCollectionNewWomanToAttach = em.getReference(womanCollectionNewWomanToAttach.getClass(), womanCollectionNewWomanToAttach.getId());
                attachedWomanCollectionNew.add(womanCollectionNewWomanToAttach);
            }
            womanCollectionNew = attachedWomanCollectionNew;
            person.setWomanCollection(womanCollectionNew);
            Collection<Man> attachedManCollectionNew = new ArrayList<Man>();
            for (Man manCollectionNewManToAttach : manCollectionNew) {
                manCollectionNewManToAttach = em.getReference(manCollectionNewManToAttach.getClass(), manCollectionNewManToAttach.getId());
                attachedManCollectionNew.add(manCollectionNewManToAttach);
            }
            manCollectionNew = attachedManCollectionNew;
            person.setManCollection(manCollectionNew);
            person = em.merge(person);
            for (Woman womanCollectionNewWoman : womanCollectionNew) {
                if (!womanCollectionOld.contains(womanCollectionNewWoman)) {
                    Person oldIdPersonOfWomanCollectionNewWoman = womanCollectionNewWoman.getIdPerson();
                    womanCollectionNewWoman.setIdPerson(person);
                    womanCollectionNewWoman = em.merge(womanCollectionNewWoman);
                    if (oldIdPersonOfWomanCollectionNewWoman != null && !oldIdPersonOfWomanCollectionNewWoman.equals(person)) {
                        oldIdPersonOfWomanCollectionNewWoman.getWomanCollection().remove(womanCollectionNewWoman);
                        oldIdPersonOfWomanCollectionNewWoman = em.merge(oldIdPersonOfWomanCollectionNewWoman);
                    }
                }
            }
            for (Man manCollectionNewMan : manCollectionNew) {
                if (!manCollectionOld.contains(manCollectionNewMan)) {
                    Person oldIdPersonOfManCollectionNewMan = manCollectionNewMan.getIdPerson();
                    manCollectionNewMan.setIdPerson(person);
                    manCollectionNewMan = em.merge(manCollectionNewMan);
                    if (oldIdPersonOfManCollectionNewMan != null && !oldIdPersonOfManCollectionNewMan.equals(person)) {
                        oldIdPersonOfManCollectionNewMan.getManCollection().remove(manCollectionNewMan);
                        oldIdPersonOfManCollectionNewMan = em.merge(oldIdPersonOfManCollectionNewMan);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = person.getId();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Woman> womanCollectionOrphanCheck = person.getWomanCollection();
            for (Woman womanCollectionOrphanCheckWoman : womanCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Woman " + womanCollectionOrphanCheckWoman + " in its womanCollection field has a non-nullable idPerson field.");
            }
            Collection<Man> manCollectionOrphanCheck = person.getManCollection();
            for (Man manCollectionOrphanCheckMan : manCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Man " + manCollectionOrphanCheckMan + " in its manCollection field has a non-nullable idPerson field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Person findPerson(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    public Person findByLogIn(String firstName, String lastName, String password){
        EntityManager em = getEntityManager();
        List<Person> res = em.createNamedQuery("Person.findByLogIn").setParameter("firstName", firstName).setParameter("lastName", lastName).setParameter("password", password).getResultList();
        if (res.isEmpty()){
            return null;
        }
        return res.get(0);
    }
    
    public void findElibigility(Person person, int weight, int height, boolean sickness, Date datelimitemax,Date datelimitemin){
        EntityManager em = getEntityManager();
        List<Person> res = em.createNamedQuery("Person.findElibigility").setParameter("id", person.getId()).setParameter("weight", weight).setParameter("height", height).setParameter("sickness", sickness).setParameter("datelimitemin", datelimitemin).setParameter("datelimitemax", datelimitemax).getResultList();
        if (res.isEmpty()){
            person.setFlag(false);
        }
        else{
            person.setFlag(true);
        }
        
    }
}
