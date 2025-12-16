# モックサーバー クイックスタートガイド

このガイドでは、Stoplight Prismを使ってOpenAPI仕様からモックサーバーを起動し、フロントエンド開発を効率化する方法を説明します。

## 目次
1. [モックサーバーとは](#モックサーバーとは)
2. [セットアップ](#セットアップ)
3. [起動方法](#起動方法)
4. [フロントエンドとの連携](#フロントエンドとの連携)
5. [使用例](#使用例)
6. [トラブルシューティング](#トラブルシューティング)

---

## モックサーバーとは

**Stoplight Prism** は、OpenAPI/Swagger仕様ファイルから自動的にモックAPIサーバーを生成するツールです。

### メリット
- バックエンドの実装を待たずにフロントエンド開発が可能
- API仕様に基づいた正確なレスポンスでテスト
- バリデーションエラーの早期発見
- 複数の開発者が並行作業可能

---

## セットアップ

### 方法1: ローカルインストール（推奨）

```bash
# mock-serverディレクトリに移動
cd mock-server

# 依存関係をインストール
npm install
```

### 方法2: グローバルインストール

```bash
# グローバルにPrismをインストール
npm install -g @stoplight/prism-cli
```

---

## 起動方法

### ローカルインストール版

```bash
cd mock-server
npm run mock
```

### グローバルインストール版

```bash
# プロジェクトルートから実行
prism mock openapi.yaml --host 0.0.0.0 --port 4010
```

### 起動確認

以下のメッセージが表示されれば成功です:

```
[CLI] …  awaiting  Starting Prism…
[CLI] ℹ  info      GET        http://0.0.0.0:4010/api/supplies
[CLI] ℹ  info      POST       http://0.0.0.0:4010/api/supplies
[CLI] ▶  start     Prism is listening on http://0.0.0.0:4010
```

---

## フロントエンドとの連携

### ステップ1: 環境変数を設定

`frontend/.env.development` を編集:

```bash
# モックサーバーを使う場合（コメントを外す）
VITE_API_BASE_URL=http://localhost:4010/api

# 実サーバーを使う場合（コメントアウト）
# VITE_API_BASE_URL=/api
```

### ステップ2: フロントエンドを起動

```bash
cd frontend
npm run dev
```

### ステップ3: 動作確認

ブラウザで `http://localhost:5173` にアクセスし、以下を確認:
- 補給品一覧が表示される
- 新規登録フォームが動作する
- 編集・削除ボタンが機能する

---

## 使用例

### API仕様に基づいたテスト

#### 1. 補給品一覧取得

**リクエスト:**
```bash
curl http://localhost:4010/api/supplies
```

**レスポンス:**
```json
[
  {
    "id": 1,
    "name": "ノートPC",
    "quantity": 10,
    "unitPrice": 150000,
    "category": "電子機器",
    "createdAt": "2024-01-01T00:00:00Z",
    "updatedAt": "2024-01-01T00:00:00Z"
  },
  {
    "id": 2,
    "name": "マウス",
    "quantity": 50,
    "unitPrice": 2000,
    "category": "電子機器",
    "createdAt": "2024-01-02T00:00:00Z",
    "updatedAt": "2024-01-02T00:00:00Z"
  }
]
```

#### 2. 補給品登録（成功パターン）

**リクエスト:**
```bash
curl -X POST http://localhost:4010/api/supplies \
  -H "Content-Type: application/json" \
  -d '{
    "name": "キーボード",
    "quantity": 20,
    "unitPrice": 5000,
    "category": "電子機器"
  }'
```

**レスポンス:**
```json
{
  "id": 3,
  "name": "ノートPC",
  "quantity": 10,
  "unitPrice": 150000,
  "category": "電子機器",
  "createdAt": "2024-01-03T00:00:00Z",
  "updatedAt": "2024-01-03T00:00:00Z"
}
```

#### 3. バリデーションエラーのテスト

**リクエスト（必須項目なし）:**
```bash
curl -X POST http://localhost:4010/api/supplies \
  -H "Content-Type: application/json" \
  -d '{}'
```

**レスポンス（400 Bad Request）:**
```json
{
  "code": "E001",
  "message": "バリデーションエラー",
  "details": [
    {
      "field": "name",
      "message": "品名は必須です"
    },
    {
      "field": "quantity",
      "message": "数量は0以上である必要があります"
    }
  ]
}
```

#### 4. 存在しないリソース（404エラー）

**リクエスト:**
```bash
curl http://localhost:4010/api/supplies/9999
```

**レスポンス（404 Not Found）:**
```json
{
  "code": "E404",
  "message": "指定されたIDの補給品が見つかりません"
}
```

---

## 動的レスポンスモード

デフォルトでは `openapi.yaml` の `examples` に定義された固定値が返されます。
ランダムな値を生成したい場合は動的モードを使用:

```bash
cd mock-server
npm run mock:dynamic
```

このモードでは、スキーマ定義に基づいてランダムなデータが生成されます。

---

## 実サーバーとモックサーバーの切り替え

### 環境変数で切り替え（推奨）

**モックサーバーを使う:**
```bash
# frontend/.env.development
VITE_API_BASE_URL=http://localhost:4010/api
```

**実サーバーを使う:**
```bash
# frontend/.env.development
VITE_API_BASE_URL=/api
```

### 起動手順

#### パターンA: モックサーバーのみ
```bash
# ターミナル1: モックサーバー起動
cd mock-server
npm run mock

# ターミナル2: フロントエンド起動（モックサーバー設定）
cd frontend
# .env.developmentでVITE_API_BASE_URL=http://localhost:4010/api
npm run dev
```

#### パターンB: 実サーバー使用
```bash
# ターミナル1: バックエンド起動
cd backend
./mvnw spring-boot:run

# ターミナル2: フロントエンド起動（実サーバー設定）
cd frontend
# .env.developmentでVITE_API_BASE_URL=/api
npm run dev
```

---

## OpenAPI仕様の更新

API仕様を変更した場合:

1. `openapi.yaml` を編集
2. モックサーバーを再起動

```bash
# Ctrl+C でモックサーバーを停止
# 再度起動
npm run mock
```

3. 仕様のバリデーション（任意）

```bash
npm run validate
```

---

## トラブルシューティング

### Q1: モックサーバーが起動しない

**エラー:** `Error: listen EADDRINUSE: address already in use :::4010`

**解決策:** ポートが使用中です。別のポートを使用してください。

```bash
# ポート4011で起動
prism mock ../openapi.yaml --port 4011

# フロントエンドの設定も変更
VITE_API_BASE_URL=http://localhost:4011/api
```

### Q2: CORSエラーが発生する

**エラー:** `Access to XMLHttpRequest at 'http://localhost:4010/api/supplies' from origin 'http://localhost:5173' has been blocked by CORS policy`

**解決策:** Prismは自動的にCORSヘッダーを付与しますが、問題がある場合:

1. ブラウザのキャッシュをクリア
2. モックサーバーを再起動
3. フロントエンドを再起動

### Q3: レスポンスが期待と異なる

**確認事項:**
1. `openapi.yaml` の `examples` セクションを確認
2. 動的モードで起動していないか確認（`--dynamic` オプション）
3. モックサーバーのログを確認

### Q4: 環境変数が反映されない

**解決策:**
1. Viteの開発サーバーを再起動
2. `.env.development` のファイル名が正しいか確認
3. 環境変数名が `VITE_` で始まっているか確認

---

## 便利なコマンド

### OpenAPI仕様のバリデーション
```bash
cd mock-server
npm run validate
```

### 特定のサンプルレスポンスを取得
```bash
curl http://localhost:4010/api/supplies \
  -H "Prefer: example=success"
```

### ログレベルの変更
```bash
# 詳細ログ
prism mock ../openapi.yaml --verbosity debug

# エラーのみ
prism mock ../openapi.yaml --verbosity error
```

---

## 参考リンク

- [Stoplight Prism 公式ドキュメント](https://docs.stoplight.io/docs/prism/)
- [OpenAPI Specification 3.0](https://swagger.io/specification/)
- [プロジェクトのOpenAPI仕様](../openapi.yaml)
- [モックサーバー詳細README](./mock-server/README.md)

---

## まとめ

このモックサーバーを活用することで:

1. バックエンドの実装を待たずにフロントエンド開発が可能
2. API仕様に基づいた正確なテスト
3. エラーハンドリングの実装と検証が容易
4. 開発速度の向上

ぜひ活用して、効率的な開発を進めてください！
