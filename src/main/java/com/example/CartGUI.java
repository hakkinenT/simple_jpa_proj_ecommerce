package com.example;

import jakarta.persistence.EntityManager;

import com.example.models.dto.CartDTO;
import com.example.models.dto.CartItemDTO;
import com.example.models.dto.ProductDTO;
import com.example.repositories.CartItemRepository;
import com.example.repositories.CartRepository;
import com.example.repositories.ProductRepository;
import com.example.services.CartService;
import com.example.utils.JPAUtils;

import java.util.*;


public class CartGUI {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        try {

        CartRepository cartRepository = new CartRepository(entityManager);
        CartItemRepository cartItemRepository = new CartItemRepository(entityManager);
        ProductRepository productRepository = new ProductRepository(entityManager);
        
        CartService service = new CartService(cartRepository, productRepository, cartItemRepository);

        ProductDTO p1 = new ProductDTO();
        p1.setId(1L);

        ProductDTO p2 = new ProductDTO();
        p2.setId(2L);


        CartItemDTO ci1 = new CartItemDTO(p1, 2);
        CartItemDTO ci2 = new CartItemDTO(p2, 2);
        List<CartItemDTO> items = new ArrayList<>(List.of(ci1, ci2));

        CartDTO cartDTO = new CartDTO();
        cartDTO.setItems(items);

        cartDTO = service.create(cartDTO);

        System.out.println("CART ID: " + cartDTO.getId());
        System.out.println("CART TOTAL: " + cartDTO.getTotal());
        
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JPAUtils.closeEntityManager(entityManager);
            JPAUtils.closeEntityManagerFactory();
        }
        




    }
}