/**
 * データ転送オブジェクト（DTO）層
 *
 * <p>このパッケージにはクライアントとサーバー間でデータをやり取りするための
 * データ転送オブジェクト（DTO）クラスが含まれます。
 * DTOはREST APIのリクエスト/レスポンスボディとして使用され、
 * JSONとの相互変換が行われます。</p>
 *
 * <h2>含まれるDTO</h2>
 * <ul>
 *   <li>{@link com.example.supply.dto.SupplyRequest} - 補給品の登録/更新リクエスト</li>
 *   <li>{@link com.example.supply.dto.InventoryTransactionRequest} - 在庫トランザクションリクエスト</li>
 * </ul>
 *
 * <h2>DTOの役割</h2>
 * <ul>
 *   <li>APIのインターフェース定義</li>
 *   <li>エンティティの内部構造をクライアントから隠蔽</li>
 *   <li>必要なデータのみを公開</li>
 *   <li>バージョン管理とAPIの進化を容易にする</li>
 * </ul>
 *
 * <h2>エンティティとの違い</h2>
 * <table border="1">
 *   <tr>
 *     <th>項目</th>
 *     <th>DTO</th>
 *     <th>エンティティ</th>
 *   </tr>
 *   <tr>
 *     <td>用途</td>
 *     <td>APIのデータ転送</td>
 *     <td>データベースマッピング</td>
 *   </tr>
 *   <tr>
 *     <td>スコープ</td>
 *     <td>プレゼンテーション層</td>
 *     <td>永続化層</td>
 *   </tr>
 *   <tr>
 *     <td>フィールド</td>
 *     <td>APIで必要なフィールドのみ</td>
 *     <td>テーブルの全カラム</td>
 *   </tr>
 * </table>
 *
 * <h2>変換処理</h2>
 * <p>DTOとエンティティ間の変換はサービス層で行われます。
 * リクエストDTOはエンティティに変換されてデータベースに保存され、
 * エンティティはレスポンスとしてそのまま返却されるか、
 * 必要に応じてDTOに変換されます。</p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
package com.example.supply.dto;
