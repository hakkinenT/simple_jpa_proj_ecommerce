package com.example.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import com.example.models.Product;
import com.example.models.dto.ProductDTO;
import com.example.models.views.ProductReport;
import com.example.repositories.ProductReportRepository;
import com.example.repositories.ProductRepository;
import com.example.specification.Specification;
import com.example.specification.impl.ProductSpecifications;
import com.example.utils.PDFGenerator;

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductReportRepository productReportRepository;

    

    public ProductService(ProductRepository productRepository, ProductReportRepository productReportRepository) {
        this.productRepository = productRepository;
        this.productReportRepository = productReportRepository;
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

    public List<ProductDTO> findAll(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::new).toList();
    }

    public List<ProductDTO> findAllPaginated(int pageNumber, int pageSize){
        List<Product> products = productRepository.findAllPaginated(pageNumber, pageSize);
        return products.stream().map(ProductDTO::new).toList();
    }

    public List<ProductDTO> filterProducts(String name, String category, double[] priceRanger){
        Specification<Product> specification = createSpecification(name, category, priceRanger);

        List<Product> products = productRepository.filterProducts(specification);
        return products.stream().map(ProductDTO::new).toList();
    }

    private Specification<Product> createSpecification(String name, String category, double[] priceRanger){
        return Specification.allOf(
            ProductSpecifications.hasName(name),
            ProductSpecifications.hasCategory(category),
            ProductSpecifications.hasPriceBetween(priceRanger[0], priceRanger[1])
        );
    }

    public void generateProductReport() throws FileNotFoundException{
        String filePath = "product_report.pdf";
        List<ProductReport> products = productReportRepository.findAll();
        PDFGenerator.generateProductReport(filePath, products);
    }
}
