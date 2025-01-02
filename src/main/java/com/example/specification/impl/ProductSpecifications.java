package com.example.specification.impl;

import com.example.models.Product;
import com.example.specification.Specification;

import jakarta.persistence.criteria.Join;

public class ProductSpecifications {
    public static Specification<Product> hasName(String name){
        return (root, query, cb) -> 
            name == null || name.isEmpty() ?
            cb.conjunction() :
            cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasCategory(String category){
        return (root, query, cb) -> {
            if (category == null || category.isEmpty()) {
                return cb.conjunction();
            }

            Join<Object, Object> categories = root.join("categories");
            return cb.equal(categories.get("name"), category);
        };
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice){
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return cb.conjunction();
            }

            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }

            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }

            return cb.between(root.get("price"), minPrice, maxPrice);
        };
    }
}
