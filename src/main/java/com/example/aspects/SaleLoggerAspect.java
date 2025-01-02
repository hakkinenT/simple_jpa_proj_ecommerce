package com.example.aspects;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.example.models.dto.CartDTO;

@Aspect
public class SaleLoggerAspect {
    private static final String LOG_FILE_PATH = "sales_log.txt";

    @Before("execution(* com.example.services.CartService.create(..))")
    public void test(){
        System.out.println("Teste");
    }

    @AfterReturning(pointcut = "execution(* com.example.services.CartService.create(..))", returning = "cartDTO")
    public void logSale(CartDTO cartDTO){
        if (cartDTO != null){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                
                writer.write("_____Nova compra_____");
                
                writer.newLine();

                writer.write("Data e Hora: " + timestamp);
                writer.newLine();
                writer.write("Total da compra: " + cartDTO.getTotal());
                writer.newLine();
                writer.write("Itens comprados: ");
                writer.newLine();
                cartDTO.getItems().forEach(item -> {
                    try {
                        writer.write(String.format("- Produto: %s | Preço: R$ %.2f | Quantidade: %d | Subtotal: R$ %.2f",item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity(), item.getSubtotal()));
                        writer.newLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }});
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
}
