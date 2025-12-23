# ドキュメント生成ガイド

このガイドでは、補給管理システムのコードドキュメント（JavaDoc、TypeDoc）の生成方法を説明します。

---

## 📚 目次

1. [JavaDoc（バックエンド）](#javadocバックエンド)
2. [TypeDoc（フロントエンド）](#typedocフロントエンド)
3. [VSCodeでの生成](#vscodeでの生成)
4. [CIで自動生成](#ciで自動生成)
5. [ドキュメントの書き方](#ドキュメントの書き方)

---

## 📖 JavaDoc（バックエンド）

### 生成方法

#### 方法1: Gradleコマンド（推奨）

```bash
# バックエンドディレクトリに移動
cd backend

# JavaDocを生成
./gradlew javadoc

# 生成されたドキュメントを確認
open build/docs/javadoc/index.html  # macOS
xdg-open build/docs/javadoc/index.html  # Linux
start build/docs/javadoc/index.html  # Windows
```

#### 方法2: VSCodeタスクで生成

1. `Ctrl+Shift+P` → "Tasks: Run Task"
2. "Backend: Generate JavaDoc" を選択

#### 方法3: Spring Boot Dashboardから

1. 左サイドバーの Spring Boot アイコンをクリック
2. プロジェクトを右クリック → "Run Task" → "javadoc"

### 出力先

```
backend/
└── build/
    └── docs/
        └── javadoc/
            ├── index.html           # メインページ
            ├── allclasses-index.html
            ├── overview-tree.html
            └── com/
                └── example/
                    └── supply/
                        ├── controller/
                        ├── service/
                        ├── entity/
                        └── ...
```

### JavaDoc JAR の作成

配布用にJARファイルとしてパッケージ化:

```bash
cd backend
./gradlew javadocJar

# 生成されたJAR
ls -lh build/libs/*javadoc.jar
```

### 設定ファイル

**build.gradle** の設定内容:

```gradle
javadoc {
    options {
        encoding = 'UTF-8'
        charSet = 'UTF-8'
        author = true
        version = true
        locale = 'ja_JP'  // 日本語対応

        // 外部APIリンク
        links(
            'https://docs.oracle.com/en/java/javase/17/docs/api/',
            'https://docs.spring.io/spring-framework/docs/current/javadoc-api/'
        )
    }

    destinationDir = file("${buildDir}/docs/javadoc")
    title = "補給管理システム API ドキュメント"
}
```

### JavaDocの書き方

**Controller の例:**

```java
/**
 * 補給品管理のREST APIコントローラー
 *
 * <p>このコントローラーは補給品のCRUD操作とファイルインポート/エクスポート機能を提供します。</p>
 *
 * @author 開発チーム
 * @version 1.0.0
 * @since 2024-12-17
 * @see Supply
 * @see SupplyService
 */
@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    /**
     * すべての補給品を取得します。
     *
     * @return 補給品のリスト。データがない場合は空のリストを返します。
     * @apiNote このエンドポイントはページネーション未対応です。
     *          大量データの場合はパフォーマンスに注意してください。
     */
    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies() {
        // ...
    }

    /**
     * 指定されたIDの補給品を取得します。
     *
     * @param id 補給品ID（必須、正の整数）
     * @return 補給品情報。見つからない場合は404を返します。
     * @throws IllegalArgumentException IDが不正な場合
     */
    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        // ...
    }
}
```

**Service の例:**

```java
/**
 * 補給品のビジネスロジックを提供するサービスクラス
 *
 * <p>このサービスはトランザクション管理とバリデーションを含みます。</p>
 *
 * @implNote MyBatisを使用してデータベースアクセスを行います。
 */
@Service
public class SupplyService {

    /**
     * 補給品を新規作成します。
     *
     * @param request 補給品登録リクエスト
     * @return 作成された補給品
     * @throws IllegalArgumentException リクエストが不正な場合
     * @throws DataAccessException データベースエラーが発生した場合
     */
    public Supply createSupply(SupplyRequest request) {
        // ...
    }
}
```

### JavaDocタグ一覧

| タグ | 説明 | 使用場所 |
|------|------|---------|
| `@param` | パラメータの説明 | メソッド |
| `@return` | 戻り値の説明 | メソッド |
| `@throws` | 例外の説明 | メソッド |
| `@see` | 関連項目へのリンク | すべて |
| `@since` | 追加されたバージョン | すべて |
| `@deprecated` | 非推奨マーク | すべて |
| `@author` | 作成者 | クラス |
| `@version` | バージョン | クラス |
| `@apiNote` | API使用上の注意 | メソッド |
| `@implNote` | 実装の詳細 | メソッド |
| `@implSpec` | 実装要件 | メソッド |

---

## 🔷 TypeDoc（フロントエンド）

### 生成方法

#### 方法1: npmスクリプト（推奨）

```bash
# フロントエンドディレクトリに移動
cd frontend

# TypeDocを生成
npm run docs

# ドキュメントをブラウザで開く
npm run docs:serve  # http://localhost:8081で自動的に開く
```

#### 方法2: VSCodeタスクで生成

1. `Ctrl+Shift+P` → "Tasks: Run Task"
2. "Frontend: Generate TypeDoc" を選択

### セットアップ（初回のみ）

```bash
cd frontend

# 依存関係をインストール
npm install --save-dev typedoc typedoc-plugin-markdown

# ドキュメント生成
npm run docs
```

### 出力先

```
frontend/
└── docs/
    ├── index.html                  # メインページ
    ├── modules.html
    ├── classes/
    ├── functions/
    ├── interfaces/
    └── types/
```

### 設定ファイル

**typedoc.json:**

```json
{
  "entryPoints": ["src"],
  "entryPointStrategy": "expand",
  "out": "docs",
  "name": "補給管理システム フロントエンド API ドキュメント",
  "categorizeByGroup": true,
  "categoryOrder": [
    "Pages",
    "Components",
    "Hooks",
    "Services",
    "Types"
  ]
}
```

### TypeDoc（JSDoc）の書き方

**Component の例:**

```typescript
/**
 * 補給品一覧を表示するコンポーネント
 *
 * @remarks
 * このコンポーネントはテーブル形式で補給品を表示し、
 * 編集・削除アクションを提供します。
 *
 * @example
 * ```tsx
 * <SupplyList
 *   supplies={supplies}
 *   onEdit={handleEdit}
 *   onDelete={handleDelete}
 * />
 * ```
 *
 * @param props - コンポーネントのプロパティ
 * @returns 補給品一覧のReact要素
 */
export const SupplyList: React.FC<SupplyListProps> = ({ supplies, onEdit, onDelete }) => {
  // ...
};

/**
 * SupplyListコンポーネントのプロパティ
 *
 * @interface
 */
export interface SupplyListProps {
  /** 表示する補給品の配列 */
  supplies: Supply[];

  /** 編集ボタンクリック時のコールバック */
  onEdit: (supply: Supply) => void;

  /** 削除ボタンクリック時のコールバック */
  onDelete: (id: number) => void;
}
```

**Custom Hook の例:**

```typescript
/**
 * 補給品データを管理するカスタムフック
 *
 * @remarks
 * このフックは補給品の取得、ローディング状態、エラー状態を管理します。
 * コンポーネントマウント時に自動的にデータを取得します。
 *
 * @example
 * ```tsx
 * function MyComponent() {
 *   const { supplies, loading, error, refetch } = useSupplies();
 *
 *   if (loading) return <div>読み込み中...</div>;
 *   if (error) return <div>{error}</div>;
 *
 *   return <SupplyList supplies={supplies} />;
 * }
 * ```
 *
 * @returns 補給品データと操作関数を含むオブジェクト
 */
export const useSupplies = () => {
  const [supplies, setSupplies] = useState<Supply[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // ...

  return { supplies, loading, error, refetch };
};
```

**Service の例:**

```typescript
/**
 * 補給品APIとの通信を行うサービス
 *
 * @remarks
 * このサービスはaxiosを使用してバックエンドAPIと通信します。
 * すべてのメソッドは非同期で、Promiseを返します。
 */
export const supplyService = {
  /**
   * すべての補給品を取得します
   *
   * @returns 補給品の配列のPromise
   * @throws {Error} API通信エラー時
   *
   * @example
   * ```typescript
   * const supplies = await supplyService.getAll();
   * console.log(supplies);
   * ```
   */
  getAll: async (): Promise<Supply[]> => {
    const response = await api.get<Supply[]>('/api/supplies');
    return response.data;
  },

  /**
   * 補給品を新規作成します
   *
   * @param data - 作成する補給品のデータ
   * @returns 作成された補給品のPromise
   * @throws {Error} バリデーションエラーまたはAPI通信エラー時
   */
  create: async (data: SupplyFormData): Promise<Supply> => {
    const response = await api.post<Supply>('/api/supplies', data);
    return response.data;
  },
};
```

**Type の例:**

```typescript
/**
 * 補給品エンティティ
 *
 * @remarks
 * データベースから取得される補給品の完全な情報を表します。
 *
 * @interface
 */
export interface Supply {
  /** 補給品ID（プライマリキー） */
  id: number;

  /** 品名（1-100文字） */
  name: string;

  /** 現在の在庫数量（0以上） */
  quantity: number;

  /** 単価（円、0以上） */
  unitPrice: number;

  /** カテゴリ（例: 電子機器、文房具） */
  category: string;

  /** 作成日時（ISO 8601形式） */
  createdAt: string;

  /** 更新日時（ISO 8601形式） */
  updatedAt: string;
}
```

### TypeDoc（JSDoc）タグ一覧

| タグ | 説明 | 使用例 |
|------|------|--------|
| `@param` | パラメータの説明 | `@param id - 補給品ID` |
| `@returns` | 戻り値の説明 | `@returns 補給品の配列` |
| `@throws` | 例外の説明 | `@throws {Error} APIエラー時` |
| `@example` | 使用例 | コードブロックで記述 |
| `@remarks` | 詳細説明 | 補足情報 |
| `@deprecated` | 非推奨マーク | `@deprecated v2.0で削除予定` |
| `@see` | 関連項目リンク | `@see Supply` |
| `@since` | 追加バージョン | `@since 1.0.0` |
| `@interface` | インターフェース宣言 | 型定義で使用 |
| `@type` | 型の明示 | `@type {string}` |

---

## 🖥️ VSCodeでの生成

### VSCodeタスクを追加

既に `.vscode/tasks.json` に以下のタスクが定義されています:

```json
{
  "label": "Backend: Generate JavaDoc",
  "type": "shell",
  "command": "./gradlew javadoc",
  "options": {
    "cwd": "${workspaceFolder}/backend"
  }
},
{
  "label": "Frontend: Generate TypeDoc",
  "type": "shell",
  "command": "npm run docs",
  "options": {
    "cwd": "${workspaceFolder}/frontend"
  }
},
{
  "label": "Generate All Documentation",
  "dependsOn": [
    "Backend: Generate JavaDoc",
    "Frontend: Generate TypeDoc"
  ]
}
```

### 実行方法

1. `Ctrl+Shift+P` → "Tasks: Run Task"
2. 以下から選択:
   - **Backend: Generate JavaDoc** - JavaDocのみ生成
   - **Frontend: Generate TypeDoc** - TypeDocのみ生成
   - **Generate All Documentation** - 両方生成

---

## 🔄 CI で自動生成

### GitHub Actions の例

`.github/workflows/docs.yml`:

```yaml
name: Generate Documentation

on:
  push:
    branches: [main, master]
  pull_request:
    branches: [main, master]

jobs:
  generate-docs:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      # Java & Gradle セットアップ
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      # JavaDoc生成
      - name: Generate JavaDoc
        run: |
          cd backend
          ./gradlew javadoc

      # Node.js セットアップ
      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'

      # TypeDoc生成
      - name: Generate TypeDoc
        run: |
          cd frontend
          npm install
          npm run docs

      # GitHub Pagesにデプロイ
      - name: Deploy to GitHub Pages
        uses: peaceiris/actions-gh-pages@v3
        if: github.ref == 'refs/heads/main'
        with:
          github_token: ${{ secrets.GITHUB_TOKEN }}
          publish_dir: ./docs
```

---

## 📝 ドキュメントの書き方ベストプラクティス

### 共通ルール

1. **日本語で記述**: チームが日本人中心の場合
2. **簡潔に**: 1文は短く、明確に
3. **例を含める**: 複雑なAPIには `@example` を追加
4. **型を明示**: TypeScriptでは型推論に頼らず明示的に

### 記述すべき内容

#### ✅ 必ず書く
- メソッド/関数の目的
- パラメータの説明
- 戻り値の説明
- 例外/エラーの説明

#### ⭐ 推奨
- 使用例（`@example`）
- 詳細説明（`@remarks`）
- 関連項目（`@see`）
- 注意事項（`@apiNote`）

#### ❌ 不要
- 自明な内容（例: `getId()` → "IDを取得します"だけ）
- コードと同じ内容の繰り返し
- 実装の詳細（外部APIでは隠蔽）

### 悪い例と良い例

#### ❌ 悪い例

```typescript
/**
 * データを取得する
 */
function getData() {
  // ...
}
```

#### ✅ 良い例

```typescript
/**
 * サーバーから補給品データを取得します
 *
 * @remarks
 * このメソッドはキャッシュを使用し、2回目以降は高速に動作します。
 * キャッシュの有効期限は5分です。
 *
 * @returns 補給品の配列のPromise
 * @throws {NetworkError} ネットワークエラー時
 * @throws {AuthenticationError} 認証エラー時
 *
 * @example
 * ```typescript
 * try {
 *   const supplies = await getData();
 *   console.log(`取得件数: ${supplies.length}`);
 * } catch (error) {
 *   console.error('データ取得エラー:', error);
 * }
 * ```
 */
async function getData(): Promise<Supply[]> {
  // ...
}
```

---

## 📊 生成されるドキュメントの例

### JavaDoc

```
補給管理システム API ドキュメント
├── パッケージ一覧
│   ├── com.example.supply.controller
│   ├── com.example.supply.service
│   ├── com.example.supply.entity
│   └── com.example.supply.dto
├── クラス一覧
├── 索引
└── 検索
```

### TypeDoc

```
補給管理システム フロントエンド API ドキュメント
├── モジュール
├── Pages
│   ├── Dashboard
│   ├── SupplyManagement
│   └── InventoryManagement
├── Components
│   ├── Header
│   ├── SupplyList
│   └── SupplyForm
├── Hooks
│   └── useSupplies
├── Services
│   ├── supplyService
│   └── inventoryService
└── Types
    ├── Supply
    ├── SupplyFormData
    └── InventoryTransaction
```

---

## 🔗 参考リンク

- [JavaDoc公式ガイド](https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html)
- [TypeDoc公式サイト](https://typedoc.org/)
- [JSDoc公式サイト](https://jsdoc.app/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html#s7-javadoc)