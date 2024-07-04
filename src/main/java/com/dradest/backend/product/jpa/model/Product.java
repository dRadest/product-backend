package com.dradest.backend.product.jpa.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "product")
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price", nullable = false, scale = 2, precision = 14)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
