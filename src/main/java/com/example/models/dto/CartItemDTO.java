package com.example.models.dto;

import com.example.models.CartItem;

public class CartItemDTO {
    private ProductDTO product;
    private Integer quantity;
    private Double subtotal;

    public CartItemDTO() {
    }

    public CartItemDTO(ProductDTO product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        
    }


    public CartItemDTO(CartItem entity) {
        product = new ProductDTO(entity.getProduct());
        quantity = entity.getQuantity();
        subtotal = entity.getSubtotal();
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
