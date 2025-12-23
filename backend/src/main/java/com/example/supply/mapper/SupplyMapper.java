package com.example.supply.mapper;

import com.example.supply.entity.Supply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 補給品データアクセスマッパーインターフェース
 *
 * <p>このインターフェースはMyBatisを使用して補給品テーブルへのCRUD操作を定義します。
 * 各メソッドは対応するXMLマッパーファイル（SupplyMapper.xml）でSQLクエリと紐付けられます。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>補給品の全件取得</li>
 *   <li>IDによる補給品の検索</li>
 *   <li>補給品の登録・更新・削除</li>
 *   <li>カテゴリによる補給品の検索</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see Supply
 */
@Mapper
public interface SupplyMapper {

    /**
     * 全ての補給品を取得します。
     *
     * @return 補給品のリスト（データがない場合は空のリスト）
     */
    List<Supply> findAll();

    /**
     * 指定されたIDの補給品を取得します。
     *
     * @param id 補給品ID
     * @return 補給品情報、該当するデータが存在しない場合はnull
     */
    Supply findById(@Param("id") Long id);

    /**
     * 新しい補給品を登録します。
     *
     * <p>登録後、引数のsupplyオブジェクトにはデータベースで自動生成されたIDが設定されます。</p>
     *
     * @param supply 登録する補給品情報
     */
    void insert(Supply supply);

    /**
     * 既存の補給品情報を更新します。
     *
     * @param supply 更新する補給品情報（IDを含む）
     */
    void update(Supply supply);

    /**
     * 指定されたIDの補給品を削除します。
     *
     * @param id 削除対象の補給品ID
     */
    void delete(@Param("id") Long id);

    /**
     * 指定されたカテゴリの補給品を取得します。
     *
     * @param category カテゴリ名
     * @return カテゴリに一致する補給品のリスト（該当データがない場合は空のリスト）
     */
    List<Supply> findByCategory(@Param("category") String category);
}
