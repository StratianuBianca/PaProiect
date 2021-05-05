package database.repository;

import database.entityController.EntityManagerProvider;
import database.model.City;

import javax.persistence.EntityManager;

public class AbstractRepository {
    protected final EntityManager entityManager = EntityManagerProvider.getEntityManagerFactory().createEntityManager();

    public void create (City item){
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }
}
