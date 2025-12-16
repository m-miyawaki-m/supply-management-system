package com.example.supply.dto;

import java.math.BigDecimal;

public class SupplyRequest {
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String category;

    // Constructors
    public SupplyRequest() {
    }

    public SupplyRequest(String name, Integer quantity, BigDecimal unitPrice, String category) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
