package com.example.repositories;

import java.util.List;
import java.util.Optional;

import com.example.models.views.ProductReport;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProductReportRepository {
    private EntityManager entityManager;

    public ProductReportRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProductReport> findAll(){
        String jpqlString = "SELECT p FROM ProductReport p";

        TypedQuery<ProductReport> query = entityManager.createQuery(jpqlString, ProductReport.class);
        return query.getResultList();
    }

    public Optional<ProductReport> findById(long id){
        return Optional.ofNullable(entityManager.find(ProductReport.class, id));
    }
}
