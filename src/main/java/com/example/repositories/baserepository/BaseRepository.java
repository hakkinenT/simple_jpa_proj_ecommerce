package com.example.repositories.baserepository;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public abstract class BaseRepository<T, ID> {
    private final Class<T> entityClass;

    @SuppressWarnings("unused")
    private final Class<ID> idClass;

    private final EntityManager entityManager;


    public BaseRepository(Class<T> entityClass, Class<ID> idClass, EntityManager entityManager){
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.entityManager = entityManager;
    }

    protected T executeInsideTransaction(EntityManagerConsumer<T> action){
        
        EntityTransaction transaction = null;

        try {
            
            transaction = entityManager.getTransaction();

            transaction.begin();

            T result = action.apply(entityManager);

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Erro ao executar a operação no banco de dados: " + e.getMessage());
        }
    }

    public T save(T entity){
        return executeInsideTransaction(em -> em.merge(entity));
    }

    public Optional<T> findById(ID id){
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public T update(T entity){
        return save(entity);
    }

    public void deleteById(ID id){
        executeInsideTransaction(em -> {
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            return null;
        });
    }


    @FunctionalInterface
    public interface EntityManagerConsumer<T>{
        T apply(EntityManager em) throws Exception;
    }
}
