package com.example.supply.dto;

/**
 * 在庫トランザクションリクエストDTO
 *
 * <p>このクラスはクライアントから送信される在庫の入出庫リクエストデータを表現します。
 * REST APIのリクエストボディとしてJSONからデシリアライズされ、
 * サービス層で{@link com.example.supply.entity.InventoryTransaction}エンティティに変換されます。</p>
 *
 * <p>含まれる情報:
 * <ul>
 *   <li>supplyId: 対象補給品のID</li>
 *   <li>type: トランザクションタイプ（通常はサーバー側で設定されるため、このフィールドは使用されない場合があります）</li>
 *   <li>quantity: 移動数量</li>
 *   <li>note: 備考</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
public class InventoryTransactionRequest {
    /** 補給品ID */
    private Long supplyId;

    /** トランザクションタイプ（"IN":入庫、"OUT":出庫） */
    private String type;

    /** 移動数量 */
    private Integer quantity;

    /** 備考 */
    private String note;

    /**
     * デフォルトコンストラクタ
     */
    public InventoryTransactionRequest() {
    }

    /**
     * 全フィールドを指定するコンストラクタ
     *
     * @param supplyId 補給品ID
     * @param type トランザクションタイプ
     * @param quantity 移動数量
     * @param note 備考
     */
    public InventoryTransactionRequest(Long supplyId, String type, Integer quantity, String note) {
        this.supplyId = supplyId;
        this.type = type;
        this.quantity = quantity;
        this.note = note;
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
}
