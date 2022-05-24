/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Person;
import Model.Woman;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Adela
 */
public class WomanJpaController implements Serializable {

    public WomanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Woman woman) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person idPerson = woman.getIdPerson();
            if (idPerson != null) {
                idPerson = em.getReference(idPerson.getClass(), idPerson.getId());
                woman.setIdPerson(idPerson);
            }
            em.persist(woman);
            if (idPerson != null) {
                idPerson.getWomanCollection().add(woman);
                idPerson = em.merge(idPerson);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Woman woman) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Woman persistentWoman = em.find(Woman.class, woman.getId());
            Person idPersonOld = persistentWoman.getIdPerson();
            Person idPersonNew = woman.getIdPerson();
            if (idPersonNew != null) {
                idPersonNew = em.getReference(idPersonNew.getClass(), idPersonNew.getId());
                woman.setIdPerson(idPersonNew);
            }
            woman = em.merge(woman);
            if (idPersonOld != null && !idPersonOld.equals(idPersonNew)) {
                idPersonOld.getWomanCollection().remove(woman);
                idPersonOld = em.merge(idPersonOld);
            }
            if (idPersonNew != null && !idPersonNew.equals(idPersonOld)) {
                idPersonNew.getWomanCollection().add(woman);
                idPersonNew = em.merge(idPersonNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = woman.getId();
                if (findWoman(id) == null) {
                    throw new NonexistentEntityException("The woman with id " + id + " no longer exists.");
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
            Woman woman;
            try {
                woman = em.getReference(Woman.class, id);
                woman.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The woman with id " + id + " no longer exists.", enfe);
            }
            Person idPerson = woman.getIdPerson();
            if (idPerson != null) {
                idPerson.getWomanCollection().remove(woman);
                idPerson = em.merge(idPerson);
            }
            em.remove(woman);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Woman> findWomanEntities() {
        return findWomanEntities(true, -1, -1);
    }

    public List<Woman> findWomanEntities(int maxResults, int firstResult) {
        return findWomanEntities(false, maxResults, firstResult);
    }

    private List<Woman> findWomanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Woman.class));
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

    public Woman findWoman(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Woman.class, id);
        } finally {
            em.close();
        }
    }

    public int getWomanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Woman> rt = cq.from(Woman.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
//Cette fonction permer de trouver la femme das la table woman qui correspond à une personne de la table personne, si celle-ci existe
//Grâce à une query crée dans le modèle woman
    public Woman findByIdPerson(Person p){
        EntityManager em = getEntityManager();
        List<Woman> res = em.createNamedQuery("Woman.findByIdPerson").setParameter("idPerson", p).getResultList();
        if (res.isEmpty()){ //si pas de femme associée à cette personne, c'est que c'est un homme, et on retourne null
            return null;
        }
        return res.get(0);//si il y a un résultat, on retourne la femme trouvée : comme idperson est une primary key, on est sûr qu'il n'y aura qu'une femme avec cette key si il y en a.
    }
    
}
