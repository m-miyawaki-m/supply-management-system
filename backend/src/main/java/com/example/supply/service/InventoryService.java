package com.example.supply.service;

import com.example.supply.dto.InventoryTransactionRequest;
import com.example.supply.entity.InventoryTransaction;
import com.example.supply.entity.Supply;
import com.example.supply.mapper.InventoryTransactionMapper;
import com.example.supply.mapper.SupplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 在庫管理のビジネスロジックを提供するサービスクラス
 *
 * <p>このサービスクラスは在庫の入出庫処理、トランザクション履歴の管理など、
 * 在庫に関連する業務ロジックを実装します。
 * すべてのパブリックメソッドはトランザクション管理されており、
 * 在庫数量の更新とトランザクション記録の登録が同一トランザクション内で行われます。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>入庫処理（在庫増加 + トランザクション記録）</li>
 *   <li>出庫処理（在庫減少 + トランザクション記録）</li>
 *   <li>トランザクション履歴の取得</li>
 *   <li>補給品別のトランザクション履歴取得</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see InventoryTransaction
 * @see InventoryTransactionMapper
 * @see Supply
 * @see SupplyMapper
 */
@Service
@Transactional
@Slf4j
public class InventoryService {

    /** 在庫トランザクションデータアクセスマッパー */
    private final InventoryTransactionMapper inventoryTransactionMapper;

    /** 補給品データアクセスマッパー */
    private final SupplyMapper supplyMapper;

    /**
     * コンストラクタ
     *
     * @param inventoryTransactionMapper 在庫トランザクションマッパー（自動インジェクション）
     * @param supplyMapper 補給品マッパー（自動インジェクション）
     */
    public InventoryService(InventoryTransactionMapper inventoryTransactionMapper,
                            SupplyMapper supplyMapper) {
        this.inventoryTransactionMapper = inventoryTransactionMapper;
        this.supplyMapper = supplyMapper;
    }

    /**
     * 全ての在庫トランザクション履歴を取得します。
     *
     * @return 在庫トランザクションのリスト（データがない場合は空のリスト）
     */
    public List<InventoryTransaction> getAllTransactions() {
        log.debug("Fetching all inventory transactions");
        List<InventoryTransaction> transactions = inventoryTransactionMapper.findAll();
        log.debug("Found {} transactions", transactions.size());
        return transactions;
    }

    /**
     * 入庫処理を実行します。
     *
     * <p>指定された補給品の在庫数量を増加させ、入庫トランザクションを記録します。
     * 在庫更新とトランザクション記録は同一トランザクション内で実行されるため、
     * 片方だけが反映されることはありません。</p>
     *
     * @param request 入庫リクエスト（補給品ID、数量、備考を含む）
     * @return 作成された入庫トランザクション情報
     * @throws RuntimeException 指定された補給品IDが存在しない場合
     */
    public InventoryTransaction stockIn(InventoryTransactionRequest request) {
        log.info("Processing stock in: supplyId={}, quantity={}",
                request.getSupplyId(), request.getQuantity());

        // 在庫を増やす
        Supply supply = supplyMapper.findById(request.getSupplyId());
        if (supply == null) {
            log.error("Stock in failed - Supply not found: id={}", request.getSupplyId());
            throw new RuntimeException("Supply not found with id: " + request.getSupplyId());
        }

        int oldQuantity = supply.getQuantity();
        int newQuantity = oldQuantity + request.getQuantity();
        supply.setQuantity(newQuantity);
        supplyMapper.update(supply);

        log.info("Stock quantity updated: supplyId={}, name={}, oldQuantity={}, newQuantity={}",
                supply.getId(), supply.getName(), oldQuantity, newQuantity);

        // トランザクション記録
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setSupplyId(request.getSupplyId());
        transaction.setType("IN");
        transaction.setQuantity(request.getQuantity());
        transaction.setNote(request.getNote());

        inventoryTransactionMapper.insert(transaction);
        log.info("Stock in completed: transactionId={}, supplyId={}, quantity={}",
                transaction.getId(), request.getSupplyId(), request.getQuantity());
        return transaction;
    }

    /**
     * 出庫処理を実行します。
     *
     * <p>指定された補給品の在庫数量を減少させ、出庫トランザクションを記録します。
     * 在庫更新とトランザクション記録は同一トランザクション内で実行されるため、
     * 片方だけが反映されることはありません。
     * 出庫数量が現在の在庫数量を超える場合はエラーとなります。</p>
     *
     * @param request 出庫リクエスト（補給品ID、数量、備考を含む）
     * @return 作成された出庫トランザクション情報
     * @throws RuntimeException 指定された補給品IDが存在しない場合、
     *                         または在庫数量が不足している場合
     */
    public InventoryTransaction stockOut(InventoryTransactionRequest request) {
        log.info("Processing stock out: supplyId={}, quantity={}",
                request.getSupplyId(), request.getQuantity());

        // 在庫を減らす
        Supply supply = supplyMapper.findById(request.getSupplyId());
        if (supply == null) {
            log.error("Stock out failed - Supply not found: id={}", request.getSupplyId());
            throw new RuntimeException("Supply not found with id: " + request.getSupplyId());
        }

        int oldQuantity = supply.getQuantity();
        int requestedQuantity = request.getQuantity();

        if (oldQuantity < requestedQuantity) {
            log.warn("Stock out failed - Insufficient stock: supplyId={}, name={}, available={}, requested={}",
                    supply.getId(), supply.getName(), oldQuantity, requestedQuantity);
            throw new RuntimeException("Insufficient stock. Available: " + oldQuantity);
        }

        int newQuantity = oldQuantity - requestedQuantity;
        supply.setQuantity(newQuantity);
        supplyMapper.update(supply);

        log.info("Stock quantity updated: supplyId={}, name={}, oldQuantity={}, newQuantity={}",
                supply.getId(), supply.getName(), oldQuantity, newQuantity);

        // トランザクション記録
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setSupplyId(request.getSupplyId());
        transaction.setType("OUT");
        transaction.setQuantity(request.getQuantity());
        transaction.setNote(request.getNote());

        inventoryTransactionMapper.insert(transaction);
        log.info("Stock out completed: transactionId={}, supplyId={}, quantity={}",
                transaction.getId(), request.getSupplyId(), request.getQuantity());
        return transaction;
    }

    /**
     * 指定された補給品のトランザクション履歴を取得します。
     *
     * @param supplyId 補給品ID
     * @return 指定された補給品に関連する在庫トランザクションのリスト（データがない場合は空のリスト）
     */
    public List<InventoryTransaction> getTransactionsBySupplyId(Long supplyId) {
        log.debug("Fetching transactions for supplyId: {}", supplyId);
        List<InventoryTransaction> transactions = inventoryTransactionMapper.findBySupplyId(supplyId);
        log.debug("Found {} transactions for supplyId: {}", transactions.size(), supplyId);
        return transactions;
    }
}
