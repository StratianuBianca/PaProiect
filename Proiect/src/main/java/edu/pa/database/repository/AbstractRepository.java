package edu.pa.database.repository;


import edu.pa.database.entityController.EntityManagerProvider;
import edu.pa.database.model.City;

import javax.persistence.EntityManager;

public class AbstractRepository {
    protected final EntityManager entityManager = EntityManagerProvider.getEntityManagerFactory().createEntityManager();

    public void create (City item){
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }
}
