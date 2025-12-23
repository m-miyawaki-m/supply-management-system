/**
 * REST APIコントローラー層
 *
 * <p>このパッケージにはREST APIのエンドポイントを定義するコントローラークラスが含まれます。
 * すべてのコントローラーはSpring MVCの{@code @RestController}アノテーションが付与され、
 * JSONベースのHTTP APIを提供します。</p>
 *
 * <h2>含まれるコントローラー</h2>
 * <ul>
 *   <li>{@link com.example.supply.controller.SupplyController} - 補給品管理API</li>
 *   <li>{@link com.example.supply.controller.InventoryController} - 在庫管理API</li>
 * </ul>
 *
 * <h2>API設計方針</h2>
 * <ul>
 *   <li>RESTfulな設計原則に従う</li>
 *   <li>適切なHTTPメソッド（GET, POST, PUT, DELETE）の使用</li>
 *   <li>適切なHTTPステータスコードの返却</li>
 *   <li>CORS設定によるクロスオリジンアクセスの許可</li>
 *   <li>Swagger/OpenAPIによるAPI仕様の文書化</li>
 * </ul>
 *
 * <h2>エンドポイントプレフィックス</h2>
 * <ul>
 *   <li>{@code /api/supplies} - 補給品関連のエンドポイント</li>
 *   <li>{@code /api/inventory} - 在庫管理関連のエンドポイント</li>
 * </ul>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply.controller;
