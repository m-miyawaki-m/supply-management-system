/**
 * 補給管理システムのルートパッケージ
 *
 * <p>このパッケージは補給品と在庫の管理を行うSpring Bootアプリケーションの
 * ルートパッケージです。以下のサブパッケージで構成されています。</p>
 *
 * <h2>パッケージ構成</h2>
 * <ul>
 *   <li>{@link com.example.supply.controller} - REST APIコントローラー層</li>
 *   <li>{@link com.example.supply.service} - ビジネスロジック層</li>
 *   <li>{@link com.example.supply.entity} - ドメインモデル（エンティティ）層</li>
 *   <li>{@link com.example.supply.dto} - データ転送オブジェクト層</li>
 *   <li>{@link com.example.supply.mapper} - データアクセス層（MyBatis）</li>
 * </ul>
 *
 * <h2>システム概要</h2>
 * <p>補給管理システムは以下の機能を提供します:</p>
 * <ul>
 *   <li>補給品のCRUD操作（登録、参照、更新、削除）</li>
 *   <li>在庫の入出庫管理</li>
 *   <li>トランザクション履歴の記録と追跡</li>
 *   <li>データのインポート/エクスポート（CSV/Excel）</li>
 * </ul>
 *
 * <h2>アーキテクチャ</h2>
 * <p>本システムは以下の技術スタックで構築されています:</p>
 * <ul>
 *   <li>Spring Boot 3.x - アプリケーションフレームワーク</li>
 *   <li>MyBatis - データベースアクセス</li>
 *   <li>H2 Database - 開発環境用データベース</li>
 *   <li>Swagger/OpenAPI - API仕様管理</li>
 * </ul>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply;
