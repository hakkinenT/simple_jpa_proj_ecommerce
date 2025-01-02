package com.example.repositories;

import jakarta.persistence.EntityManager;

import com.example.models.CartItem;
import com.example.models.pk.CartItemPK;
import com.example.repositories.baserepository.BaseRepository;

public class CartItemRepository extends BaseRepository<CartItem, CartItemPK>{
    public CartItemRepository(EntityManager entityManager){
        super(CartItem.class, CartItemPK.class, entityManager);
    }
}
