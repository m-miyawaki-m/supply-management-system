package com.example.supply.dto;

import java.math.BigDecimal;

/**
 * 補給品登録・更新リクエストDTO
 *
 * <p>このクラスはクライアントから送信される補給品の登録・更新リクエストデータを表現します。
 * REST APIのリクエストボディとしてJSONからデシリアライズされ、
 * サービス層で{@link com.example.supply.entity.Supply}エンティティに変換されます。</p>
 *
 * <p>含まれる情報:
 * <ul>
 *   <li>name: 補給品名</li>
 *   <li>quantity: 在庫数量</li>
 *   <li>unitPrice: 単価</li>
 *   <li>category: カテゴリ</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
public class SupplyRequest {
    /** 補給品名 */
    private String name;

    /** 在庫数量 */
    private Integer quantity;

    /** 単価 */
    private BigDecimal unitPrice;

    /** カテゴリ */
    private String category;

    /**
     * デフォルトコンストラクタ
     */
    public SupplyRequest() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param name 補給品名
     * @param quantity 在庫数量
     * @param unitPrice 単価
     * @param category カテゴリ
     */
    public SupplyRequest(String name, Integer quantity, BigDecimal unitPrice, String category) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
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
}
