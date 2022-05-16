/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Man;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Adela
 */
public class ManJpaController implements Serializable {

    public ManJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Man man) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person idPerson = man.getIdPerson();
            if (idPerson != null) {
                idPerson = em.getReference(idPerson.getClass(), idPerson.getId());
                man.setIdPerson(idPerson);
            }
            em.persist(man);
            if (idPerson != null) {
                idPerson.getManCollection().add(man);
                idPerson = em.merge(idPerson);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Man man) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Man persistentMan = em.find(Man.class, man.getId());
            Person idPersonOld = persistentMan.getIdPerson();
            Person idPersonNew = man.getIdPerson();
            if (idPersonNew != null) {
                idPersonNew = em.getReference(idPersonNew.getClass(), idPersonNew.getId());
                man.setIdPerson(idPersonNew);
            }
            man = em.merge(man);
            if (idPersonOld != null && !idPersonOld.equals(idPersonNew)) {
                idPersonOld.getManCollection().remove(man);
                idPersonOld = em.merge(idPersonOld);
            }
            if (idPersonNew != null && !idPersonNew.equals(idPersonOld)) {
                idPersonNew.getManCollection().add(man);
                idPersonNew = em.merge(idPersonNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = man.getId();
                if (findMan(id) == null) {
                    throw new NonexistentEntityException("The man with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Man man;
            try {
                man = em.getReference(Man.class, id);
                man.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The man with id " + id + " no longer exists.", enfe);
            }
            Person idPerson = man.getIdPerson();
            if (idPerson != null) {
                idPerson.getManCollection().remove(man);
                idPerson = em.merge(idPerson);
            }
            em.remove(man);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Man> findManEntities() {
        return findManEntities(true, -1, -1);
    }

    public List<Man> findManEntities(int maxResults, int firstResult) {
        return findManEntities(false, maxResults, firstResult);
    }

    private List<Man> findManEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Man.class));
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

    public Man findMan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Man.class, id);
        } finally {
            em.close();
        }
    }

    public int getManCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Man> rt = cq.from(Man.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Man findByIdPerson(Person p){
        EntityManager em = getEntityManager();
        List<Man> res = em.createNamedQuery("Person.findByIdPerson").setParameter("IdPerson", p.getId()).getResultList();
        if (res.isEmpty()){
            return null;
        }
        return res.get(0);
    }
    
}
