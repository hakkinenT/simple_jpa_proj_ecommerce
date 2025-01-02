package com.example;

import jakarta.persistence.EntityManager;

import java.util.List;

import com.example.models.Category;
import com.example.models.Product;
import com.example.models.dto.ProductDTO;
import com.example.models.views.ProductReport;
import com.example.repositories.ProductReportRepository;
import com.example.repositories.ProductRepository;
import com.example.services.ProductService;
import com.example.specification.Specification;
import com.example.specification.impl.ProductSpecifications;
import com.example.utils.JPAUtils;
import com.example.utils.PDFGenerator;


public class ProductGUI {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtils.getEntityManager();
        try {

            ProductRepository productRepository = new ProductRepository(entityManager);
            ProductReportRepository productReportRepository = new ProductReportRepository(entityManager);
            ProductService service = new ProductService(productRepository);

            /*ProductDTO product = new ProductDTO(null, "Notebook", 3000.0);
            product = service.create(product);

            System.out.println("INFORMAÇÕES DO PRODUTO CADASTRADO: ");
            System.out.println("ID: " + product.getId());
            System.out.println("NOME: " + product.getName());
            System.out.println("PREÇO: " + product.getPrice());

            System.out.println();

            product = service.findById(product.getId());

            product.setPrice(3500.0);

            product = service.update(product);

            System.out.println("INFORMAÇÕES DO PRODUTO ATUALIZADO: ");
            System.out.println("ID: " + product.getId());
            System.out.println("NOME: " + product.getName());
            System.out.println("PREÇO: " + product.getPrice());

            System.out.println();

            System.out.println("EXCLUINDO PRODUTO COM ID " + product.getId() + "...");
            service.delete(product.getId());
            System.out.println("Produto excluido com sucesso!");*/
            
            /*List<Product> products = productRepository.findAllPaginated(10, 10);
            for (Product product : products) {
                System.out.println("ID: " + product.getId() + ", NAME: " + product.getName());
                for (Category category : product.getCategories()) {
                    System.out.println("CATEGORY: " + category.getName());
                }
            }*/

            // Filtros
        String nome = "Notebook";
        String categoria = "Eletrônicos";
        Double minPreco = 1000.0;
        Double maxPreco = 5000.0;

        // Combinar as especificações
        Specification<Product> specification = Specification.allOf(
            ProductSpecifications.hasName(nome),
            ProductSpecifications.hasCategory(categoria),
            ProductSpecifications.hasPriceBetween(minPreco, maxPreco)
        );
        
        //List<Product> produtos = productRepository.filterProducts(specification);
        List<ProductReport> produtos = productReportRepository.findAll();
        
        // Gera o relatório
            String filePath = "relatorio_produtos_view.pdf";
            PDFGenerator.generateProductReport(filePath, produtos);

            System.out.println("Relatório gerado em: " + filePath);
        //produtos.forEach(produto -> System.out.println(produto.getName() + ", " + produto.getPrice()));

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JPAUtils.closeEntityManager(entityManager);
            JPAUtils.closeEntityManagerFactory();
        }
        
    }
}