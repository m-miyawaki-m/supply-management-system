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

/**
 * 在庫管理のREST APIコントローラー
 *
 * <p>このコントローラーは在庫トランザクションの管理機能を提供します。
 * 入庫・出庫処理、トランザクション履歴の取得など、在庫の移動に関する操作を行います。
 * すべてのエンドポイントは{@code /api/inventory}配下に配置されています。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>在庫トランザクション履歴の取得</li>
 *   <li>入庫処理（在庫増加）</li>
 *   <li>出庫処理（在庫減少）</li>
 *   <li>補給品別のトランザクション履歴取得</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see InventoryTransaction
 * @see InventoryService
 * @see InventoryTransactionRequest
 */
@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Inventory Management", description = "在庫管理API")
public class InventoryController {

    /** 在庫管理サービス */
    private final InventoryService inventoryService;

    /**
     * コンストラクタ
     *
     * @param inventoryService 在庫管理サービス（自動インジェクション）
     */
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * 在庫トランザクション一覧を取得します。
     *
     * <p>システムに記録されている全ての在庫トランザクション（入庫・出庫の履歴）を取得します。
     * トランザクションが存在しない場合は空のリストを返します。</p>
     *
     * @return 在庫トランザクションのリストを含むレスポンスエンティティ（HTTP 200 OK）
     */
    @GetMapping
    @Operation(summary = "在庫トランザクション一覧取得", description = "全ての在庫トランザクションを取得します")
    public ResponseEntity<List<InventoryTransaction>> getAllTransactions() {
        List<InventoryTransaction> transactions = inventoryService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * 入庫処理を行います。
     *
     * <p>指定された補給品の在庫を増やす入庫処理を実行します。
     * リクエストには補給品IDと入庫数量などの情報を含める必要があります。
     * 処理が成功した場合、入庫トランザクションの記録を返します。
     * 補給品が存在しない場合やデータが不正な場合は、HTTP 400 Bad Requestを返します。</p>
     *
     * @param request 入庫トランザクションリクエスト（補給品ID、数量、備考などを含む）
     * @return 作成された入庫トランザクション情報（HTTP 201 Created）、
     *         または処理失敗時（HTTP 400 Bad Request）
     */
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

    /**
     * 出庫処理を行います。
     *
     * <p>指定された補給品の在庫を減らす出庫処理を実行します。
     * リクエストには補給品IDと出庫数量などの情報を含める必要があります。
     * 処理が成功した場合、出庫トランザクションの記録を返します。
     * 補給品が存在しない場合、在庫が不足している場合、またはデータが不正な場合は、
     * HTTP 400 Bad Requestを返します。</p>
     *
     * @param request 出庫トランザクションリクエスト（補給品ID、数量、備考などを含む）
     * @return 作成された出庫トランザクション情報（HTTP 201 Created）、
     *         または処理失敗時（HTTP 400 Bad Request）
     */
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

    /**
     * 指定された補給品のトランザクション履歴を取得します。
     *
     * <p>補給品IDに紐づく全ての在庫トランザクション（入庫・出庫の履歴）を取得します。
     * 特定の補給品の在庫変動履歴を追跡する際に使用します。
     * トランザクションが存在しない場合は空のリストを返します。</p>
     *
     * @param supplyId 補給品ID
     * @return 指定された補給品の在庫トランザクションリストを含むレスポンスエンティティ（HTTP 200 OK）
     */
    @GetMapping("/supply/{supplyId}")
    @Operation(summary = "補給品別トランザクション取得", description = "指定された補給品のトランザクション履歴を取得します")
    public ResponseEntity<List<InventoryTransaction>> getTransactionsBySupplyId(@PathVariable Long supplyId) {
        List<InventoryTransaction> transactions = inventoryService.getTransactionsBySupplyId(supplyId);
        return ResponseEntity.ok(transactions);
    }
}
