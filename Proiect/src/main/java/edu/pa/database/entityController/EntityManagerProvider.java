package edu.pa.database.entityController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("primary");

    private  static final EntityManagerProvider singleton = new EntityManagerProvider();
    private EntityManagerProvider() {}
    public static EntityManagerProvider getInstance(){
        return singleton;
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory(){
        if(entityManagerFactory.isOpen() || entityManagerFactory != null){
            entityManagerFactory.close();
        }
        entityManagerFactory = null;
    }
}

