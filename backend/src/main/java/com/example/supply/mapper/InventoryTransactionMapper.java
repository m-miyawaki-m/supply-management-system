package com.example.supply.mapper;

import com.example.supply.entity.InventoryTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 在庫トランザクションデータアクセスマッパーインターフェース
 *
 * <p>このインターフェースはMyBatisを使用して在庫トランザクションテーブルへの操作を定義します。
 * 各メソッドは対応するXMLマッパーファイル（InventoryTransactionMapper.xml）で
 * SQLクエリと紐付けられます。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>在庫トランザクションの全件取得</li>
 *   <li>IDによるトランザクションの検索</li>
 *   <li>トランザクションの登録</li>
 *   <li>補給品IDによるトランザクション履歴の取得</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see InventoryTransaction
 */
@Mapper
public interface InventoryTransactionMapper {

    /**
     * 全ての在庫トランザクションを取得します。
     *
     * @return 在庫トランザクションのリスト（データがない場合は空のリスト）
     */
    List<InventoryTransaction> findAll();

    /**
     * 指定されたIDの在庫トランザクションを取得します。
     *
     * @param id トランザクションID
     * @return トランザクション情報、該当するデータが存在しない場合はnull
     */
    InventoryTransaction findById(@Param("id") Long id);

    /**
     * 新しい在庫トランザクションを登録します。
     *
     * <p>登録後、引数のtransactionオブジェクトには
     * データベースで自動生成されたIDが設定されます。</p>
     *
     * @param transaction 登録するトランザクション情報
     */
    void insert(InventoryTransaction transaction);

    /**
     * 指定された補給品IDに関連する全てのトランザクション履歴を取得します。
     *
     * @param supplyId 補給品ID
     * @return 指定された補給品の在庫トランザクションリスト（データがない場合は空のリスト）
     */
    List<InventoryTransaction> findBySupplyId(@Param("supplyId") Long supplyId);
}
