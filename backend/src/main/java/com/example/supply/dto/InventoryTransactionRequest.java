package com.example.supply.dto;

public class InventoryTransactionRequest {
    private Long supplyId;
    private String type; // "IN" or "OUT"
    private Integer quantity;
    private String note;

    // Constructors
    public InventoryTransactionRequest() {
    }

    public InventoryTransactionRequest(Long supplyId, String type, Integer quantity, String note) {
        this.supplyId = supplyId;
        this.type = type;
        this.quantity = quantity;
        this.note = note;
    }

    // Getters and Setters
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
