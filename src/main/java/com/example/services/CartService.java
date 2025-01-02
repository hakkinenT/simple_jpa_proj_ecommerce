package com.example.services;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import com.example.models.Cart;
import com.example.models.CartItem;
import com.example.models.Product;
import com.example.models.dto.CartDTO;
import com.example.models.dto.CartItemDTO;
import com.example.models.pk.CartItemPK;
import com.example.repositories.CartItemRepository;
import com.example.repositories.CartRepository;
import com.example.repositories.ProductRepository;

public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
            CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public CartDTO create(CartDTO dto){
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        for (CartItemDTO ci : dto.getItems()) {
            Optional<Product> p = productRepository.findById(ci.getProduct().getId());
            CartItemPK pk = null;
            if (p.isPresent()) {
                pk = new CartItemPK(cart, p.get());
            }else{
                throw new EntityNotFoundException("Produto não encontrado");
            }
            
            CartItem cartItem = new CartItem(ci.getQuantity(), pk.getCart(), pk.getProduct());
            cartItem.calculateSubtotal();
            cartItem = cartItemRepository.save(cartItem);
            cart.getItems().add(cartItem);

        }
        cart.setTotal(calculateTotal(cart));
        cart = cartRepository.update(cart);
        
        return new CartDTO(cart, cart.getItems());
    }

    private double calculateTotal(Cart cart){
        return cart.getItems()
            .stream()
            .map(CartItem::getSubtotal)
            .reduce(0.0, Double::sum);
    }
}
