package com.example.supply.controller;

import com.example.supply.dto.SupplyRequest;
import com.example.supply.entity.Supply;
import com.example.supply.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Supply Management", description = "補給品管理API")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    @Operation(summary = "補給品一覧取得", description = "全ての補給品を取得します")
    public ResponseEntity<List<Supply>> getAllSupplies() {
        List<Supply> supplies = supplyService.getAllSupplies();
        return ResponseEntity.ok(supplies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "補給品詳細取得", description = "指定されたIDの補給品を取得します")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        Supply supply = supplyService.getSupplyById(id);
        if (supply == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supply);
    }

    @PostMapping
    @Operation(summary = "補給品登録", description = "新しい補給品を登録します")
    public ResponseEntity<Supply> createSupply(@RequestBody SupplyRequest request) {
        Supply supply = supplyService.createSupply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(supply);
    }

    @PutMapping("/{id}")
    @Operation(summary = "補給品更新", description = "指定されたIDの補給品を更新します")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @RequestBody SupplyRequest request) {
        try {
            Supply supply = supplyService.updateSupply(id, request);
            return ResponseEntity.ok(supply);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "補給品削除", description = "指定されたIDの補給品を削除します")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        try {
            supplyService.deleteSupply(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/import")
    @Operation(summary = "CSVインポート", description = "CSVファイルから補給品をインポートします")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            supplyService.importFromCsv(file);
            return ResponseEntity.ok("Import successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Import failed: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    @Operation(summary = "Excelエクスポート", description = "補給品一覧をExcelファイルとしてエクスポートします")
    public ResponseEntity<byte[]> exportExcel() {
        try {
            byte[] excelData = supplyService.exportToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=supplies.xlsx")
                    .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
