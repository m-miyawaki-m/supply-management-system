package com.example.supply.controller;

import com.example.supply.dto.InventoryTransactionRequest;
import com.example.supply.entity.InventoryTransaction;
import com.example.supply.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Inventory Management", description = "在庫管理API")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(summary = "在庫トランザクション一覧取得", description = "全ての在庫トランザクションを取得します")
    public ResponseEntity<List<InventoryTransaction>> getAllTransactions() {
        List<InventoryTransaction> transactions = inventoryService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/in")
    @Operation(summary = "入庫登録", description = "在庫を増やす入庫処理を行います")
    public ResponseEntity<InventoryTransaction> stockIn(@RequestBody InventoryTransactionRequest request) {
        try {
            InventoryTransaction transaction = inventoryService.stockIn(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/out")
    @Operation(summary = "出庫登録", description = "在庫を減らす出庫処理を行います")
    public ResponseEntity<InventoryTransaction> stockOut(@RequestBody InventoryTransactionRequest request) {
        try {
            InventoryTransaction transaction = inventoryService.stockOut(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/supply/{supplyId}")
    @Operation(summary = "補給品別トランザクション取得", description = "指定された補給品のトランザクション履歴を取得します")
    public ResponseEntity<List<InventoryTransaction>> getTransactionsBySupplyId(@PathVariable Long supplyId) {
        List<InventoryTransaction> transactions = inventoryService.getTransactionsBySupplyId(supplyId);
        return ResponseEntity.ok(transactions);
    }
}
