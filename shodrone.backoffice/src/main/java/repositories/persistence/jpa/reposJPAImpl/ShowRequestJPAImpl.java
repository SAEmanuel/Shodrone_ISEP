/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories.persistence.jpa.reposJPAImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import repositories.persistence.jpa.interfaces.ShowRequestRepository;
import repositories.persistence.jpa.interfaces.exampleRepository;


public class ShowRequestJPAImpl implements ShowRequestRepository {

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("DEMO_ORMPU");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }


    @Override
    public void add(Object repo){
        if(repo == null){
            throw new IllegalArgumentException();
        }
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(repo);
        tx.commit();
        em.close();
        //return automovel;
    }

}