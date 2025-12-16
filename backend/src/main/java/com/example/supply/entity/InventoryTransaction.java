package com.example.supply.entity;

import java.time.LocalDateTime;

public class InventoryTransaction {
    private Long id;
    private Long supplyId;
    private String type; // "IN" or "OUT"
    private Integer quantity;
    private LocalDateTime transactionDate;
    private String note;

    // Constructors
    public InventoryTransaction() {
    }

    public InventoryTransaction(Long id, Long supplyId, String type, Integer quantity,
                                LocalDateTime transactionDate, String note) {
        this.id = id;
        this.supplyId = supplyId;
        this.type = type;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.note = note;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "id=" + id +
                ", supplyId=" + supplyId +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", transactionDate=" + transactionDate +
                ", note='" + note + '\'' +
                '}';
    }
}
