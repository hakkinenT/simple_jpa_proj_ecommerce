package com.example.services;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import com.example.models.Product;
import com.example.models.dto.ProductDTO;
import com.example.repositories.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO create(ProductDTO dto){
        Product product = new Product();
        copyDTOToEntity(dto, product);

        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    private void copyDTOToEntity(ProductDTO dto, Product entity){
        if(dto.getId() != null){
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
    }

    public ProductDTO update(ProductDTO dto){
        Product product = new Product();
        copyDTOToEntity(dto, product);

        product = productRepository.update(product);
        return new ProductDTO(product);
    }

    public ProductDTO findById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return new ProductDTO(product.get());
        }else{
            throw new EntityNotFoundException("Produto não encontrado");
        }
        
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
