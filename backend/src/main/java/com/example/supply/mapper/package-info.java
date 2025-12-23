/**
 * データアクセス層（MyBatisマッパー）
 *
 * <p>このパッケージにはMyBatisを使用したデータアクセス層のインターフェースが含まれます。
 * マッパーインターフェースはデータベース操作を抽象化し、
 * SQLクエリの実行とオブジェクトマッピングを担当します。</p>
 *
 * <h2>含まれるマッパー</h2>
 * <ul>
 *   <li>{@link com.example.supply.mapper.SupplyMapper} - 補給品テーブルのデータアクセス</li>
 *   <li>{@link com.example.supply.mapper.InventoryTransactionMapper} - 在庫トランザクションテーブルのデータアクセス</li>
 * </ul>
 *
 * <h2>MyBatisマッパーの仕組み</h2>
 * <p>各マッパーインターフェースは対応するXMLマッパーファイルと紐付けられます:</p>
 * <ul>
 *   <li>{@code SupplyMapper.java} ⇔ {@code SupplyMapper.xml}</li>
 *   <li>{@code InventoryTransactionMapper.java} ⇔ {@code InventoryTransactionMapper.xml}</li>
 * </ul>
 *
 * <h2>マッパーの責務</h2>
 * <ul>
 *   <li>CRUD操作の定義（Create, Read, Update, Delete）</li>
 *   <li>検索条件に基づくクエリの実行</li>
 *   <li>結果セットとJavaオブジェクト間のマッピング</li>
 *   <li>自動生成キーの取得</li>
 * </ul>
 *
 * <h2>使用方法</h2>
 * <p>マッパーインターフェースは{@code @Mapper}アノテーションにより、
 * Spring Bootの起動時に自動的にプロキシが生成され、DIコンテナに登録されます。
 * サービス層でインジェクションして使用します。</p>
 *
 * <pre>
 * {@code
 * @Service
 * public class SupplyService {
 *     private final SupplyMapper supplyMapper;
 *
 *     public SupplyService(SupplyMapper supplyMapper) {
 *         this.supplyMapper = supplyMapper;
 *     }
 * }
 * }
 * </pre>
 *
 * <h2>トランザクション</h2>
 * <p>マッパー自体はトランザクション管理を行いません。
 * トランザクション境界はサービス層の{@code @Transactional}アノテーションで定義されます。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply.mapper;
