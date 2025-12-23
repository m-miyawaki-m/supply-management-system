package com.example.supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 補給管理システムのメインアプリケーションクラス
 *
 * <p>このクラスはSpring Bootアプリケーションのエントリーポイントです。
 * {@code @SpringBootApplication}アノテーションにより、
 * 自動設定、コンポーネントスキャン、および設定プロパティの読み込みが有効になります。</p>
 *
 * <p>システム概要:
 * <ul>
 *   <li>補給品のCRUD操作</li>
 *   <li>在庫の入出庫管理</li>
 *   <li>トランザクション履歴の記録</li>
 *   <li>データのインポート/エクスポート機能</li>
 * </ul>
 * </p>
 *
 * <p>使用技術:
 * <ul>
 *   <li>Spring Boot 3.x</li>
 *   <li>MyBatis</li>
 *   <li>H2 Database（開発環境）</li>
 *   <li>Swagger/OpenAPI（API仕様）</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 */
@SpringBootApplication
public class SupplyApplication {

    /**
     * アプリケーションのメインエントリーポイント
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(SupplyApplication.class, args);
    }

}
