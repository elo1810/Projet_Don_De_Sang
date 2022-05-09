/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testdb;

import com.mycompany.testdb.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Stockdesang;

/**
 *
 * @author Elo
 */
public class StockdesangJpaController implements Serializable {

    public StockdesangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stockdesang stockdesang) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(stockdesang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stockdesang stockdesang) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            stockdesang = em.merge(stockdesang);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stockdesang.getId();
                if (findStockdesang(id) == null) {
                    throw new NonexistentEntityException("The stockdesang with id " + id + " no longer exists.");
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
            Stockdesang stockdesang;
            try {
                stockdesang = em.getReference(Stockdesang.class, id);
                stockdesang.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stockdesang with id " + id + " no longer exists.", enfe);
            }
            em.remove(stockdesang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stockdesang> findStockdesangEntities() {
        return findStockdesangEntities(true, -1, -1);
    }

    public List<Stockdesang> findStockdesangEntities(int maxResults, int firstResult) {
        return findStockdesangEntities(false, maxResults, firstResult);
    }

    private List<Stockdesang> findStockdesangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stockdesang.class));
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

    public Stockdesang findStockdesang(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stockdesang.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockdesangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stockdesang> rt = cq.from(Stockdesang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
