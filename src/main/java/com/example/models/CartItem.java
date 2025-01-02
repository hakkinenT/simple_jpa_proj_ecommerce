package com.example.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.example.models.pk.CartItemPK;

@Entity
@Table(name = "tb_cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemPK id = new CartItemPK();
    private Integer quantity;
    private Double subtotal;
    
    
    public CartItem() {
    }

    public CartItem(Integer quantity, Cart cart, Product product) {
        this.quantity = quantity;
        id.setCart(cart);
        id.setProduct(product);
    }

    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return id.getCart();
    }

    public void setCart(Cart cart) {
        this.id.setCart(cart);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        this.id.setProduct(product);;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void calculateSubtotal() {
        this.subtotal = this.id.getProduct().getPrice() * this.quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    

    
    
}
