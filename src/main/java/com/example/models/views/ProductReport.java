package com.example.models.views;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "vw_product_report")
public class ProductReport {
    @Id
    private Long id;
    private String name;
    
    @Column(name = "total_sales")
    private Integer totalSales;

    public ProductReport() {
    }

    public ProductReport(Long id, String name, Integer totalSales) {
        this.id = id;
        this.name = name;
        this.totalSales = totalSales;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

}
