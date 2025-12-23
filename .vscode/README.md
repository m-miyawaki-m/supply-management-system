# VSCode設定ガイド

このディレクトリには、補給管理システムの開発に最適化されたVSCode設定が含まれています。

## セットアップ方法

### 1. ワークスペースファイルを使用する（推奨）

プロジェクトルートの `supply-management-system.code-workspace` を開きます。

```bash
code supply-management-system.code-workspace
```

これにより、Backend、Frontend、Mock Serverが個別のフォルダとして認識され、Spring Boot拡張機能が正しく動作します。

### 2. 推奨拡張機能のインストール

VSCodeを開くと、推奨拡張機能のインストールを促すポップアップが表示されます。
「すべてインストール」をクリックしてください。

手動でインストールする場合:
1. `Ctrl+Shift+X` で拡張機能タブを開く
2. `@recommended` で検索
3. 表示される拡張機能をインストール

### 必須の拡張機能

#### Java & Spring Boot
- **Extension Pack for Java** (vscjava.vscode-java-pack)
- **Spring Boot Extension Pack** (vmware.vscode-spring-boot)
- **Spring Boot Dashboard** (vscjava.vscode-spring-boot-dashboard)

#### TypeScript & React
- **ESLint** (dbaeumer.vscode-eslint)
- **Prettier** (esbenp.prettier-vscode)
- **ES7+ React/Redux/React-Native snippets** (dsznajder.es7-react-js-snippets)

## 使い方

### Spring Boot Dashboard

1. 左サイドバーに Spring Boot アイコンが表示されます
2. クリックすると、検出されたSpring Bootアプリケーションが表示されます
3. `SupplyApplication` を右クリック → "Start" で起動

### デバッグ実行

1. `F5` を押すか、デバッグパネルを開く
2. 「Launch Spring Boot - SupplyApplication」を選択
3. デバッグ実行が開始されます

### タスク実行

`Ctrl+Shift+P` → "Tasks: Run Task" で以下のタスクを実行できます:

#### バックエンド
- `Backend: Gradle Build` - プロジェクトをビルド
- `Backend: Spring Boot Run` - アプリケーションを起動

#### フロントエンド
- `Frontend: Vite Dev Server` - 開発サーバー起動
- `Frontend: npm install` - 依存関係インストール

#### モックサーバー
- `Mock Server: Start Prism` - モックサーバー起動
- `Mock Server: npm install` - 依存関係インストール

#### 複合タスク
- `Start All Servers (Mock)` - フロントエンド + モックサーバー
- `Start All Servers (Real Backend)` - フロントエンド + バックエンド

## トラブルシューティング

### Spring Bootアプリケーションが検出されない

1. **Java Extension Packがインストールされているか確認**
   ```bash
   code --list-extensions | grep vscjava
   ```

2. **Javaのパスが設定されているか確認**
   - `Ctrl+Shift+P` → "Java: Configure Java Runtime"
   - JDK 17以上が設定されていることを確認

3. **ワークスペースをリロード**
   - `Ctrl+Shift+P` → "Developer: Reload Window"

4. **Gradleプロジェクトを再インポート**
   - `Ctrl+Shift+P` → "Java: Clean Java Language Server Workspace"
   - VSCodeを再起動

### Gradleビルドエラー

1. **Gradle Wrapperの実行権限を確認**
   ```bash
   chmod +x backend/gradlew
   ```

2. **依存関係をクリーンビルド**
   ```bash
   cd backend
   ./gradlew clean build
   ```

### TypeScriptエラーが表示される

1. **TypeScript SDKの設定**
   - フロントエンドのファイルを開く
   - 右下に "Select TypeScript Version" が表示される
   - "Use Workspace Version" を選択

2. **node_modulesを再インストール**
   ```bash
   cd frontend
   rm -rf node_modules package-lock.json
   npm install
   ```

## ファイル説明

### settings.json
プロジェクト全体の設定。Java、TypeScript、フォーマッター等の設定が含まれます。

### launch.json
デバッグ設定。Spring Bootアプリケーションのデバッグ起動設定が含まれます。

### tasks.json
タスクランナー設定。ビルド、実行、サーバー起動等のタスクが定義されています。

### extensions.json
推奨拡張機能のリスト。チーム全体で同じ拡張機能を使用できます。

### supply-management-system.code-workspace
マルチルートワークスペース設定。Backend、Frontend、Mock Serverを個別のプロジェクトとして管理します。

## キーボードショートカット

| ショートカット | 機能 |
|--------------|------|
| `F5` | デバッグ実行開始 |
| `Ctrl+Shift+B` | ビルドタスク実行 |
| `Ctrl+Shift+P` | コマンドパレット |
| `Ctrl+P` | ファイル検索 |
| `Ctrl+Shift+F` | プロジェクト全体検索 |
| `Ctrl+\`` | ターミナル表示/非表示 |
| `F12` | 定義へジャンプ |
| `Shift+F12` | 参照を検索 |

## Spring Boot Dashboard使い方

Spring Boot Dashboardを使うと、GUIでアプリケーションの起動・停止ができます。

1. 左サイドバーの Spring Boot アイコンをクリック
2. `SupplyApplication` が表示される
3. 右クリックメニュー:
   - **Start**: アプリケーション起動
   - **Debug**: デバッグモードで起動
   - **Stop**: 停止
   - **Open in Browser**: ブラウザで開く

## 参考リンク

- [VSCode Java Documentation](https://code.visualstudio.com/docs/java/java-tutorial)
- [Spring Boot in VSCode](https://code.visualstudio.com/docs/java/java-spring-boot)
- [TypeScript in VSCode](https://code.visualstudio.com/docs/languages/typescript)