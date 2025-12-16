# 補給管理システム - フロントエンド

Reactを使用したシングルページアプリケーション

## 技術スタック

- React 18.2.0
- TypeScript 5.3.3
- Vite 5.0.8
- React Router 6.20.0
- Axios 1.6.2

## セットアップ

### 前提条件

- Node.js 18以降がインストールされていること
- npm または yarn がインストールされていること

### インストールと実行

```bash
# 依存関係をインストール
npm install

# 開発サーバーを起動
npm run dev

# 本番ビルド
npm run build

# ビルドしたアプリをプレビュー
npm run preview
```

## アクセスURL

開発サーバー: http://localhost:5173

## プロジェクト構成

```
src/
├── components/        # Reactコンポーネント
│   ├── common/       # 共通コンポーネント
│   └── supply/       # 補給品関連コンポーネント
├── pages/            # ページコンポーネント
├── services/         # API通信サービス
├── types/            # TypeScript型定義
├── hooks/            # カスタムフック
├── App.tsx           # ルートコンポーネント
└── main.tsx          # エントリーポイント
```

## 機能

### ページ

- **ダッシュボード** (`/`) - システムのホーム画面
- **補給品管理** (`/supplies`) - 補給品のCRUD操作
- **在庫管理** (`/inventory`) - 入庫・出庫登録とトランザクション履歴

### コンポーネント

- `Header` - ヘッダーナビゲーション
- `SupplyList` - 補給品一覧表示
- `SupplyForm` - 補給品登録・編集フォーム

### カスタムフック

- `useSupplies` - 補給品データの取得と状態管理

## API連携

バックエンドAPIとの通信は `services/` 配下のサービスクラスを使用します。

- `supplyService` - 補給品管理API
- `inventoryService` - 在庫管理API

## 開発

### 新しいページの追加

1. `pages/` にページコンポーネントを作成
2. `App.tsx` にルートを追加
3. `Header.tsx` にナビゲーションリンクを追加

### 新しいAPIサービスの追加

1. `types/` に型定義を追加
2. `services/` にサービスクラスを作成
3. 必要に応じて `hooks/` にカスタムフックを作成

## ビルド設定

- Vite設定: `vite.config.ts`
- TypeScript設定: `tsconfig.json`
- 開発サーバーポート: 5173
- APIプロキシ: `/api` → `http://localhost:8080`
