# 補給管理システム

Java/Spring Boot、JavaScript/TypeScript/Reactの学習を目的とした補給管理システム

## 概要

このプロジェクトは、補給品の管理と在庫管理を行うWebアプリケーションです。バックエンドにSpring Boot、フロントエンドにReactを使用しています。

## 主要機能

- 補給品マスタ管理（登録・編集・削除・一覧表示）
- 在庫管理（入庫・出庫登録、在庫照会）
- ファイル入出力（CSVインポート、Excelエクスポート）

## 技術スタック

### バックエンド
- Java 17
- Spring Boot 3.2.0
- MyBatis 3.0.3
- H2 Database
- Gradle

### フロントエンド
- React 18.2.0
- TypeScript 5.3.3
- Vite 5.0.8
- React Router 6.20.0
- Axios 1.6.2

## プロジェクト構造

```
supply-management-system/
├── backend/          # Spring Boot API
└── frontend/         # React Application
```

## セットアップと実行

### バックエンド

1. バックエンドディレクトリに移動
```bash
cd backend
```

2. Gradleラッパーに実行権限を付与（初回のみ）
```bash
chmod +x gradlew
```

3. アプリケーションを起動
```bash
./gradlew bootRun
```

バックエンドは `http://localhost:8080` で起動します。

#### APIドキュメント
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

### フロントエンド

1. フロントエンドディレクトリに移動
```bash
cd frontend
```

2. 依存関係をインストール
```bash
npm install
```

3. 開発サーバーを起動
```bash
npm run dev
```

フロントエンドは `http://localhost:5173` で起動します。

## API エンドポイント

### 補給品管理
- `GET /api/supplies` - 補給品一覧取得
- `GET /api/supplies/{id}` - 補給品詳細取得
- `POST /api/supplies` - 補給品登録
- `PUT /api/supplies/{id}` - 補給品更新
- `DELETE /api/supplies/{id}` - 補給品削除
- `POST /api/supplies/import` - CSVインポート
- `GET /api/supplies/export` - Excelエクスポート

### 在庫管理
- `GET /api/inventory` - 在庫トランザクション一覧取得
- `POST /api/inventory/in` - 入庫登録
- `POST /api/inventory/out` - 出庫登録
- `GET /api/inventory/supply/{supplyId}` - 補給品別トランザクション取得

## 開発

### バックエンド開発

ディレクトリ構成:
- `controller/` - REST API Controller
- `service/` - ビジネスロジック
- `mapper/` - MyBatis Mapper Interface
- `entity/` - Domain Model
- `dto/` - Data Transfer Object
- `config/` - 設定クラス

### フロントエンド開発

ディレクトリ構成:
- `components/` - Reactコンポーネント
- `pages/` - ページコンポーネント
- `services/` - API通信
- `types/` - TypeScript型定義
- `hooks/` - カスタムフック

## ライセンス

学習用プロジェクトのため、ライセンスは設定していません。
