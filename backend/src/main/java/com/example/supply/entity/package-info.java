/**
 * ドメインモデル（エンティティ）層
 *
 * <p>このパッケージにはアプリケーションのドメインモデルを表現するエンティティクラスが含まれます。
 * エンティティクラスはデータベーステーブルと1対1で対応し、
 * MyBatisによってマッピングされます。</p>
 *
 * <h2>含まれるエンティティ</h2>
 * <ul>
 *   <li>{@link com.example.supply.entity.Supply} - 補給品エンティティ</li>
 *   <li>{@link com.example.supply.entity.InventoryTransaction} - 在庫トランザクションエンティティ</li>
 * </ul>
 *
 * <h2>設計方針</h2>
 * <ul>
 *   <li>POJO（Plain Old Java Object）として実装</li>
 *   <li>データベーステーブルの構造を反映</li>
 *   <li>ゲッター/セッターによるカプセル化</li>
 *   <li>不変性は保証せず、状態変更可能な設計</li>
 * </ul>
 *
 * <h2>データベースマッピング</h2>
 * <p>MyBatisのXMLマッパーファイルにより、以下のテーブルとマッピングされます:</p>
 * <ul>
 *   <li>{@code supplies} テーブル ⇔ {@link com.example.supply.entity.Supply}</li>
 *   <li>{@code inventory_transactions} テーブル ⇔ {@link com.example.supply.entity.InventoryTransaction}</li>
 * </ul>
 *
 * <h2>主キーの自動生成</h2>
 * <p>すべてのエンティティのIDフィールドは、データベース側で自動生成されます。
 * 新規登録時は{@code null}を設定し、登録後にマッパーによって自動生成されたIDが設定されます。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply.entity;
