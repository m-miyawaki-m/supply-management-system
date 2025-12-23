package com.example.supply.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 補給品エンティティクラス
 *
 * <p>このクラスは補給品の情報を表現するドメインモデルです。
 * データベースのsuppliesテーブルとマッピングされ、補給品の属性を保持します。</p>
 *
 * <p>主な属性:
 * <ul>
 *   <li>id: 補給品の一意識別子（自動生成）</li>
 *   <li>name: 補給品名</li>
 *   <li>quantity: 在庫数量</li>
 *   <li>unitPrice: 単価</li>
 *   <li>category: カテゴリ</li>
 *   <li>createdAt: 登録日時（自動設定）</li>
 *   <li>updatedAt: 更新日時（自動更新）</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
public class Supply {
    /** 補給品ID（主キー） */
    private Long id;

    /** 補給品名 */
    private String name;

    /** 在庫数量 */
    private Integer quantity;

    /** 単価 */
    private BigDecimal unitPrice;

    /** カテゴリ */
    private String category;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /**
     * デフォルトコンストラクタ
     */
    public Supply() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param id 補給品ID
     * @param name 補給品名
     * @param quantity 在庫数量
     * @param unitPrice 単価
     * @param category カテゴリ
     * @param createdAt 登録日時
     * @param updatedAt 更新日時
     */
    public Supply(Long id, String name, Integer quantity, BigDecimal unitPrice, String category,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 補給品IDを取得します。
     *
     * @return 補給品ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 補給品IDを設定します。
     *
     * @param id 補給品ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 補給品名を取得します。
     *
     * @return 補給品名
     */
    public String getName() {
        return name;
    }

    /**
     * 補給品名を設定します。
     *
     * @param name 補給品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 在庫数量を取得します。
     *
     * @return 在庫数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 在庫数量を設定します。
     *
     * @param quantity 在庫数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 単価を取得します。
     *
     * @return 単価
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 単価を設定します。
     *
     * @param unitPrice 単価
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * カテゴリを取得します。
     *
     * @return カテゴリ
     */
    public String getCategory() {
        return category;
    }

    /**
     * カテゴリを設定します。
     *
     * @param category カテゴリ
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 登録日時を取得します。
     *
     * @return 登録日時
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 登録日時を設定します。
     *
     * @param createdAt 登録日時
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 更新日時を取得します。
     *
     * @return 更新日時
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新日時を設定します。
     *
     * @param updatedAt 更新日時
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Supply{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
