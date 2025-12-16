package com.example.supply.service;

import com.example.supply.dto.InventoryTransactionRequest;
import com.example.supply.entity.InventoryTransaction;
import com.example.supply.entity.Supply;
import com.example.supply.mapper.InventoryTransactionMapper;
import com.example.supply.mapper.SupplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {

    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final SupplyMapper supplyMapper;

    public InventoryService(InventoryTransactionMapper inventoryTransactionMapper,
                            SupplyMapper supplyMapper) {
        this.inventoryTransactionMapper = inventoryTransactionMapper;
        this.supplyMapper = supplyMapper;
    }

    public List<InventoryTransaction> getAllTransactions() {
        return inventoryTransactionMapper.findAll();
    }

    public InventoryTransaction stockIn(InventoryTransactionRequest request) {
        // 在庫を増やす
        Supply supply = supplyMapper.findById(request.getSupplyId());
        if (supply == null) {
            throw new RuntimeException("Supply not found with id: " + request.getSupplyId());
        }

        supply.setQuantity(supply.getQuantity() + request.getQuantity());
        supplyMapper.update(supply);

        // トランザクション記録
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setSupplyId(request.getSupplyId());
        transaction.setType("IN");
        transaction.setQuantity(request.getQuantity());
        transaction.setNote(request.getNote());

        inventoryTransactionMapper.insert(transaction);
        return transaction;
    }

    public InventoryTransaction stockOut(InventoryTransactionRequest request) {
        // 在庫を減らす
        Supply supply = supplyMapper.findById(request.getSupplyId());
        if (supply == null) {
            throw new RuntimeException("Supply not found with id: " + request.getSupplyId());
        }

        if (supply.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + supply.getQuantity());
        }

        supply.setQuantity(supply.getQuantity() - request.getQuantity());
        supplyMapper.update(supply);

        // トランザクション記録
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setSupplyId(request.getSupplyId());
        transaction.setType("OUT");
        transaction.setQuantity(request.getQuantity());
        transaction.setNote(request.getNote());

        inventoryTransactionMapper.insert(transaction);
        return transaction;
    }

    public List<InventoryTransaction> getTransactionsBySupplyId(Long supplyId) {
        return inventoryTransactionMapper.findBySupplyId(supplyId);
    }
}
