package com.example.repositories;

import jakarta.persistence.EntityManager;

import com.example.models.Cart;
import com.example.repositories.baserepository.BaseRepository;

public class CartRepository extends BaseRepository<Cart, Long>{
    public CartRepository(EntityManager entityManager){
        super(Cart.class, Long.class, entityManager);
    }
}
