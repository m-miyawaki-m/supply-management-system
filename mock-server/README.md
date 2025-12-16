# Mock Server (Stoplight Prism)

このディレクトリには、OpenAPI仕様からモックサーバーを起動するための設定が含まれています。

## Stoplight Prism とは

OpenAPI/Swagger仕様ファイルから自動的にモックサーバーを生成するツールです。
フロントエンド開発時にバックエンドAPIが完成していなくても、仕様ベースでテストが可能になります。

## セットアップ

### 1. 依存関係のインストール

```bash
cd mock-server
npm install
```

または、グローバルインストール:

```bash
npm install -g @stoplight/prism-cli
```

## 使い方

### モックサーバーの起動

#### ローカルインストール版
```bash
npm run mock
```

#### グローバルインストール版
```bash
# プロジェクトルートから実行
prism mock openapi.yaml --host 0.0.0.0 --port 4010
```

サーバーが起動すると、以下のURLでアクセス可能になります:
- **ベースURL**: `http://localhost:4010/api`

### 動的レスポンスモード

レスポンスの値を動的に生成したい場合:

```bash
npm run mock:dynamic
```

このモードでは、スキーマに基づいてランダムな値が生成されます。

### OpenAPI仕様のバリデーション

```bash
npm run validate
```

## 利用可能なエンドポイント

モックサーバー起動後、以下のエンドポイントが利用できます:

### 補給品管理
- `GET /api/supplies` - 補給品一覧取得
- `POST /api/supplies` - 補給品登録
- `GET /api/supplies/{id}` - 補給品詳細取得
- `PUT /api/supplies/{id}` - 補給品更新
- `DELETE /api/supplies/{id}` - 補給品削除
- `POST /api/supplies/import` - CSVインポート
- `GET /api/supplies/export` - Excelエクスポート

### 在庫管理
- `GET /api/inventory` - 在庫一覧取得
- `POST /api/inventory/in` - 入庫登録
- `POST /api/inventory/out` - 出庫登録
- `GET /api/inventory/history` - 入出庫履歴取得

## テスト例

### 補給品一覧取得
```bash
curl http://localhost:4010/api/supplies
```

### 補給品登録
```bash
curl -X POST http://localhost:4010/api/supplies \
  -H "Content-Type: application/json" \
  -d '{
    "name": "ノートPC",
    "quantity": 10,
    "unitPrice": 150000,
    "category": "電子機器"
  }'
```

### 補給品詳細取得
```bash
curl http://localhost:4010/api/supplies/1
```

## フロントエンドとの連携

### 開発環境での切り替え

`frontend/src/services/api.ts` でベースURLを切り替えることで、実サーバーとモックサーバーを使い分けられます:

```typescript
// モックサーバーを使う場合
const api = axios.create({
  baseURL: 'http://localhost:4010/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// 実サーバーを使う場合
const api = axios.create({
  baseURL: '/api', // Viteのproxyが有効
  headers: {
    'Content-Type': 'application/json',
  },
});
```

### 環境変数での制御（推奨）

`frontend/.env.development`:
```
VITE_API_BASE_URL=http://localhost:4010/api
```

`frontend/src/services/api.ts`:
```typescript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});
```

## Prismの特徴

### 1. 静的レスポンス（デフォルト）
- OpenAPI仕様の `examples` セクションに定義された値を返す
- 一貫性のあるテストが可能

### 2. 動的レスポンス（`--dynamic`オプション）
- スキーマに基づいてランダムな値を生成
- 様々なデータパターンでのテストが可能

### 3. バリデーション
- リクエストが仕様に準拠しているかチェック
- 不正なリクエストには適切なエラーを返す

### 4. プリファードレスポンス選択
- `Prefer` ヘッダーで特定のサンプルを選択可能
```bash
curl http://localhost:4010/api/supplies \
  -H "Prefer: example=success"
```

## トラブルシューティング

### ポートが使用中の場合
```bash
# 別のポートを指定
prism mock ../openapi.yaml --port 4011
```

### CORS エラーが発生する場合
Prismは自動的にCORSヘッダーを付与しますが、問題がある場合は以下を確認:
- フロントエンドのURLが正しいか
- ブラウザのコンソールでエラー詳細を確認

### OpenAPI仕様の変更が反映されない
- Prismを再起動してください
- ブラウザのキャッシュをクリアしてください

## 参考リンク

- [Stoplight Prism 公式ドキュメント](https://docs.stoplight.io/docs/prism/)
- [OpenAPI Specification](https://swagger.io/specification/)
