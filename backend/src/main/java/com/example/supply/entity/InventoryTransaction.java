package com.example.supply.entity;

import java.time.LocalDateTime;

/**
 * 在庫トランザクションエンティティクラス
 *
 * <p>このクラスは在庫の入出庫履歴を表現するドメインモデルです。
 * データベースのinventory_transactionsテーブルとマッピングされ、
 * 在庫移動の記録を保持します。</p>
 *
 * <p>主な属性:
 * <ul>
 *   <li>id: トランザクションの一意識別子（自動生成）</li>
 *   <li>supplyId: 対象補給品のID</li>
 *   <li>type: トランザクションタイプ（"IN":入庫、"OUT":出庫）</li>
 *   <li>quantity: 移動数量</li>
 *   <li>transactionDate: トランザクション日時（自動設定）</li>
 *   <li>note: 備考</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
public class InventoryTransaction {
    /** トランザクションID（主キー） */
    private Long id;

    /** 補給品ID（外部キー） */
    private Long supplyId;

    /** トランザクションタイプ（"IN":入庫、"OUT":出庫） */
    private String type;

    /** 移動数量 */
    private Integer quantity;

    /** トランザクション日時 */
    private LocalDateTime transactionDate;

    /** 備考 */
    private String note;

    /**
     * デフォルトコンストラクタ
     */
    public InventoryTransaction() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param id トランザクションID
     * @param supplyId 補給品ID
     * @param type トランザクションタイプ（"IN"または"OUT"）
     * @param quantity 移動数量
     * @param transactionDate トランザクション日時
     * @param note 備考
     */
    public InventoryTransaction(Long id, Long supplyId, String type, Integer quantity,
                                LocalDateTime transactionDate, String note) {
        this.id = id;
        this.supplyId = supplyId;
        this.type = type;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.note = note;
    }

    /**
     * トランザクションIDを取得します。
     *
     * @return トランザクションID
     */
    public Long getId() {
        return id;
    }

    /**
     * トランザクションIDを設定します。
     *
     * @param id トランザクションID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 補給品IDを取得します。
     *
     * @return 補給品ID
     */
    public Long getSupplyId() {
        return supplyId;
    }

    /**
     * 補給品IDを設定します。
     *
     * @param supplyId 補給品ID
     */
    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    /**
     * トランザクションタイプを取得します。
     *
     * @return トランザクションタイプ（"IN"または"OUT"）
     */
    public String getType() {
        return type;
    }

    /**
     * トランザクションタイプを設定します。
     *
     * @param type トランザクションタイプ（"IN"または"OUT"）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 移動数量を取得します。
     *
     * @return 移動数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 移動数量を設定します。
     *
     * @param quantity 移動数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * トランザクション日時を取得します。
     *
     * @return トランザクション日時
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * トランザクション日時を設定します。
     *
     * @param transactionDate トランザクション日時
     */
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * 備考を取得します。
     *
     * @return 備考
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考を設定します。
     *
     * @param note 備考
     */
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
