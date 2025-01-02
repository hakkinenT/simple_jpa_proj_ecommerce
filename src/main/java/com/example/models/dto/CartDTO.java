package com.example.models.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.models.Cart;
import com.example.models.CartItem;

public class CartDTO {
    private Long id;
    private Double total;
    private List<CartItemDTO> items = new ArrayList<>();
    
    public CartDTO() {
    }

    public CartDTO(Long id, Double total) {
        this.id = id;
        this.total = total;
    }

    public CartDTO(Cart entity, List<CartItem> cartItems) {
        this.id = entity.getId();
        this.total = entity.getTotal();
        this.items = cartItems
                        .stream()
                        .map(item -> new CartItemDTO(item))
                        .toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }


    
}
