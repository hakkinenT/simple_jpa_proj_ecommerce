package com.example.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//Design Pattern - Singleton
public class JPAUtils {
    private static EntityManagerFactory entityManagerFactory;

    private static final String PERSISTENCE_UNIT_NAME = "ecommercePU";

    static{
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        } catch (Exception e) {
            System.out.println("Falha ao iniciar o EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError("EntityManagerFactory não pode ser criado");
        }
    }

    public static EntityManager getEntityManager(){
        System.out.println("Inicializando em");
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory(){
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public static void closeEntityManager(EntityManager entityManager){
        if(entityManager != null && entityManager.isOpen()){
            entityManager.close();
        }
    }
}
