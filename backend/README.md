# 補給管理システム - バックエンド

Spring Bootを使用したREST APIサーバー

## 技術スタック

- Java 17
- Spring Boot 3.2.0
- MyBatis 3.0.3
- H2 Database (インメモリ)
- Gradle
- Swagger (OpenAPI 3.0)

## セットアップ

### 前提条件

- Java 17以降がインストールされていること
- 環境変数 `JAVA_HOME` が設定されていること

### ビルドと実行

```bash
# Gradleラッパーに実行権限を付与（初回のみ）
chmod +x gradlew

# アプリケーションを起動
./gradlew bootRun

# ビルド（JARファイル作成）
./gradlew build
```

## アクセスURL

- API サーバー: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

### H2 Database設定

- JDBC URL: `jdbc:h2:mem:supplydb`
- User Name: `sa`
- Password: (空白)

## プロジェクト構成

```
src/main/java/com/example/supply/
├── controller/          # REST APIエンドポイント
├── service/            # ビジネスロジック
├── mapper/             # MyBatis Mapper Interface
├── entity/             # エンティティクラス
├── dto/                # Data Transfer Object
├── config/             # 設定クラス
└── SupplyApplication.java  # メインクラス

src/main/resources/
├── mapper/             # MyBatis XMLマッパー
├── application.yml     # アプリケーション設定
├── schema.sql         # DDL
└── data.sql           # 初期データ
```

## 開発

### 新しいエンティティの追加

1. `entity/` にエンティティクラスを作成
2. `dto/` にリクエスト/レスポンスDTOを作成
3. `mapper/` にMapperインターフェースを作成
4. `resources/mapper/` にXMLマッパーを作成
5. `service/` にサービスクラスを作成
6. `controller/` にコントローラーを作成

### テスト

```bash
./gradlew test
```

## API仕様

詳細なAPI仕様は Swagger UI (http://localhost:8080/swagger-ui.html) を参照してください。
