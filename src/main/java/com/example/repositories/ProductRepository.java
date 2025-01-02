package com.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

import java.util.List;


import com.example.models.Product;
import com.example.repositories.baserepository.BaseRepository;
import com.example.specification.Specification;

public class ProductRepository extends BaseRepository<Product, Long>{

    private EntityManager entityManager;

    public ProductRepository(EntityManager entityManager){
        super(Product.class, Long.class, entityManager);
        this.entityManager = entityManager;
    }

    public List<Product> findAll(){
        String jpqlQuery = """
                SELECT p 
                FROM Product p 
                JOIN FETCH p.categories
            """;
        TypedQuery<Product> query = entityManager.createQuery(jpqlQuery,Product.class);
        List<Product> result = query.getResultList();

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Product> findAllPaginated(int pageNumber, int pageSize){
        String jpqlQuery = """
                SELECT p 
                FROM Product p 
                JOIN p.categories
            """;
        Query query = entityManager.createQuery(jpqlQuery, Product.class);

        int firstResult = (pageNumber - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<Product> products = query.getResultList();
        

        //Evita o uso do JOIN FETCH que nesse caso tava dando o seguinte warning:
        // WARN: HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
        products.forEach(product -> product.getCategories().size());
        return products;
    }

    public long getTotalCount(){
        String jpqlQuery = "SELECT COUNT(p) FROM Product p";
        Query query = entityManager.createQuery(jpqlQuery);
        return (long) query.getSingleResult();
    }

    public List<Product> filterProducts(Specification<Product> specification){
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();

            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);

            Predicate predicate = specification.toPredicate(root, query, cb);
            query.where(predicate);

            transaction.commit();

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Erro ao executar a operação no banco de dados: " + e.getMessage());
        }
    }

}
