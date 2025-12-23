/**
 * ビジネスロジック層
 *
 * <p>このパッケージにはアプリケーションのビジネスロジックを実装するサービスクラスが含まれます。
 * サービス層はコントローラー層とデータアクセス層の間に位置し、
 * 業務処理の実装とトランザクション管理を担当します。</p>
 *
 * <h2>含まれるサービス</h2>
 * <ul>
 *   <li>{@link com.example.supply.service.SupplyService} - 補給品管理ビジネスロジック</li>
 *   <li>{@link com.example.supply.service.InventoryService} - 在庫管理ビジネスロジック</li>
 * </ul>
 *
 * <h2>責務</h2>
 * <ul>
 *   <li>ビジネスルールの実装と検証</li>
 *   <li>トランザクション境界の定義（{@code @Transactional}）</li>
 *   <li>DTOとエンティティ間の変換</li>
 *   <li>複数のマッパーを組み合わせた複雑な処理の実装</li>
 *   <li>例外処理とエラーハンドリング</li>
 * </ul>
 *
 * <h2>トランザクション管理</h2>
 * <p>すべてのサービスクラスは{@code @Transactional}アノテーションにより、
 * パブリックメソッドがトランザクション境界として管理されます。
 * データの一貫性を保つため、関連する複数のデータベース操作は
 * 単一のトランザクション内で実行されます。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply.service;
