# VSCode タスク記述ガイド

このガイドでは、VSCodeの `tasks.json` でタスクを定義する方法を詳しく解説します。

---

## 📋 目次

1. [基本構造](#基本構造)
2. [タスクの種類](#タスクの種類)
3. [よく使うパラメータ](#よく使うパラメータ)
4. [実践例](#実践例)
5. [高度な機能](#高度な機能)
6. [トラブルシューティング](#トラブルシューティング)

---

## 基本構造

### tasks.json の全体構造

```json
{
  "version": "2.0.0",
  "tasks": [
    {
      "label": "タスク名",
      "type": "shell",
      "command": "実行するコマンド",
      "args": ["引数1", "引数2"],
      "options": {
        "cwd": "${workspaceFolder}/サブディレクトリ"
      },
      "group": "build",
      "problemMatcher": []
    }
  ]
}
```

### 必須パラメータ

| パラメータ | 説明 | 例 |
|-----------|------|-----|
| `label` | タスクの表示名（ユニークである必要がある） | `"Backend: Build"` |
| `type` | タスクの種類 | `"shell"` または `"process"` |
| `command` | 実行するコマンド | `"npm"`, `"./gradlew"` |

---

## タスクの種類

### 1. Shell タスク（推奨）

シェルを通してコマンドを実行します。パイプやリダイレクトが使えます。

```json
{
  "label": "Example: Shell Task",
  "type": "shell",
  "command": "echo 'Hello World' && ls -la",
  "problemMatcher": []
}
```

**特徴:**
- ✅ パイプ (`|`)、リダイレクト (`>`, `>>`) が使える
- ✅ 環境変数が展開される
- ✅ シェルスクリプトが実行できる
- ❌ 若干オーバーヘッドがある

### 2. Process タスク

プロセスを直接実行します（シェル非経由）。

```json
{
  "label": "Example: Process Task",
  "type": "process",
  "command": "node",
  "args": ["script.js"],
  "problemMatcher": []
}
```

**特徴:**
- ✅ 高速（シェルのオーバーヘッドなし）
- ✅ セキュア（シェルインジェクション回避）
- ❌ パイプやリダイレクトは使えない
- ❌ 環境変数の展開が限定的

---

## よく使うパラメータ

### args - コマンド引数

```json
{
  "label": "Gradle Build with Args",
  "type": "shell",
  "command": "./gradlew",
  "args": [
    "build",
    "--info",
    "--stacktrace"
  ]
}
```

**注意点:**
- 配列形式で指定
- 各引数は個別の要素として記述
- スペースを含む引数は1つの要素にまとめる

### options - 実行オプション

#### cwd - 作業ディレクトリ

```json
{
  "options": {
    "cwd": "${workspaceFolder}/backend"
  }
}
```

**変数:**
- `${workspaceFolder}` - ワークスペースルート
- `${workspaceFolder}/backend` - サブディレクトリ
- `${fileDirname}` - 現在開いているファイルのディレクトリ

#### env - 環境変数

```json
{
  "options": {
    "env": {
      "NODE_ENV": "development",
      "API_URL": "http://localhost:8080"
    }
  }
}
```

#### shell - 使用するシェル

```json
{
  "options": {
    "shell": {
      "executable": "/bin/bash",
      "args": ["-c"]
    }
  }
}
```

### group - タスクグループ

タスクをグループ化して、ショートカットで実行できるようにします。

```json
{
  "group": {
    "kind": "build",
    "isDefault": true
  }
}
```

**グループ種類:**
- `"build"` - ビルドタスク（`Ctrl+Shift+B`で実行）
- `"test"` - テストタスク
- `"none"` - グループなし

**isDefault:**
- `true` - そのグループのデフォルトタスク
- `false` - 通常のタスク

### presentation - 出力の表示方法

```json
{
  "presentation": {
    "reveal": "always",
    "panel": "new",
    "focus": false,
    "echo": true,
    "showReuseMessage": true,
    "clear": false
  }
}
```

| パラメータ | 説明 | 値 |
|-----------|------|-----|
| `reveal` | ターミナルを表示するタイミング | `"always"`, `"silent"`, `"never"` |
| `panel` | パネルの使い方 | `"shared"`, `"dedicated"`, `"new"` |
| `focus` | 実行時にターミナルにフォーカス | `true`, `false` |
| `echo` | コマンドを表示 | `true`, `false` |
| `clear` | 実行前にクリア | `true`, `false` |

### problemMatcher - 問題マッチャー

エラーや警告をVSCodeの問題パネルに表示します。

```json
{
  "problemMatcher": "$tsc"
}
```

**組み込みマッチャー:**
- `$tsc` - TypeScript
- `$eslint-stylish` - ESLint
- `$gcc` - GCC
- `$msCompile` - Visual Studio
- `[]` - マッチャーなし（エラー検出しない）

### dependsOn - 依存タスク

他のタスクを先に実行します。

```json
{
  "label": "Deploy",
  "dependsOn": [
    "Build",
    "Test"
  ]
}
```

**実行順序:**
- 配列の順番通りに実行
- すべて成功した場合のみ本タスクを実行

### isBackground - バックグラウンド実行

サーバー起動などの継続的なタスクに使用します。

```json
{
  "isBackground": true,
  "problemMatcher": {
    "pattern": {
      "regexp": "."
    },
    "background": {
      "activeOnStart": true,
      "beginsPattern": "^.*Starting.*$",
      "endsPattern": "^.*Started.*$"
    }
  }
}
```

**使用例:**
- Webサーバー起動
- ファイルウォッチャー
- 開発サーバー

---

## 実践例

### 例1: Gradle ビルド

```json
{
  "label": "Backend: Gradle Build",
  "type": "shell",
  "command": "./gradlew",
  "args": ["build", "--info"],
  "options": {
    "cwd": "${workspaceFolder}/backend"
  },
  "group": {
    "kind": "build",
    "isDefault": true
  },
  "presentation": {
    "reveal": "always",
    "panel": "shared"
  },
  "problemMatcher": []
}
```

**実行方法:**
- `Ctrl+Shift+B` で実行（デフォルトビルド）
- または `Ctrl+Shift+P` → "Tasks: Run Build Task"

### 例2: npm スクリプト実行

```json
{
  "label": "Frontend: Vite Dev Server",
  "type": "shell",
  "command": "npm",
  "args": ["run", "dev"],
  "options": {
    "cwd": "${workspaceFolder}/frontend"
  },
  "isBackground": true,
  "problemMatcher": {
    "pattern": {
      "regexp": "."
    },
    "background": {
      "activeOnStart": true,
      "beginsPattern": "VITE.*ready in",
      "endsPattern": "Local:.*http://localhost:5173"
    }
  },
  "presentation": {
    "reveal": "always",
    "panel": "dedicated"
  }
}
```

**ポイント:**
- `isBackground: true` でバックグラウンド実行
- `problemMatcher.background` で起動完了を検知
- `panel: "dedicated"` で専用パネルを使用

### 例3: 複数タスクの連鎖実行

```json
{
  "label": "Build and Deploy",
  "dependsOn": [
    "Backend: Gradle Build",
    "Frontend: npm build",
    "Copy Files",
    "Deploy to Server"
  ],
  "problemMatcher": []
}
```

**実行順序:**
1. Backend: Gradle Build
2. Frontend: npm build
3. Copy Files
4. Deploy to Server

### 例4: 環境変数を使用

```json
{
  "label": "Run with Environment",
  "type": "shell",
  "command": "npm",
  "args": ["run", "dev"],
  "options": {
    "cwd": "${workspaceFolder}/frontend",
    "env": {
      "VITE_API_BASE_URL": "http://localhost:4010",
      "NODE_ENV": "development"
    }
  },
  "problemMatcher": []
}
```

### 例5: カスタムシェルスクリプト

```json
{
  "label": "Custom: Setup Database",
  "type": "shell",
  "command": "bash",
  "args": [
    "-c",
    "cd backend && ./gradlew flywayMigrate && echo 'Database setup complete!'"
  ],
  "problemMatcher": []
}
```

### 例6: ファイルを開く

```json
{
  "label": "Backend: Open JavaDoc",
  "type": "shell",
  "command": "xdg-open",
  "args": ["${workspaceFolder}/backend/build/docs/javadoc/index.html"],
  "dependsOn": ["Backend: Generate JavaDoc"],
  "problemMatcher": []
}
```

**ポイント:**
- `dependsOn` でJavaDoc生成を先に実行
- `xdg-open` (Linux), `open` (macOS), `start` (Windows)

### 例7: 条件付き実行（OS別）

```json
{
  "label": "Open Documentation",
  "type": "shell",
  "windows": {
    "command": "start",
    "args": ["${workspaceFolder}/docs/index.html"]
  },
  "linux": {
    "command": "xdg-open",
    "args": ["${workspaceFolder}/docs/index.html"]
  },
  "osx": {
    "command": "open",
    "args": ["${workspaceFolder}/docs/index.html"]
  },
  "problemMatcher": []
}
```

---

## 高度な機能

### 入力変数（Input Variables）

ユーザーに入力を求めることができます。

**tasks.json:**
```json
{
  "label": "Deploy to Environment",
  "type": "shell",
  "command": "npm",
  "args": ["run", "deploy", "--", "--env=${input:environment}"],
  "problemMatcher": []
}
```

**プロジェクトルートまたはワークスペースファイルで定義:**
```json
{
  "inputs": [
    {
      "id": "environment",
      "type": "pickString",
      "description": "デプロイ先環境を選択",
      "options": ["development", "staging", "production"],
      "default": "development"
    }
  ]
}
```

### カスタム Problem Matcher

```json
{
  "problemMatcher": {
    "owner": "custom",
    "fileLocation": ["relative", "${workspaceFolder}"],
    "pattern": {
      "regexp": "^(.*):(\\d+):(\\d+):\\s+(warning|error):\\s+(.*)$",
      "file": 1,
      "line": 2,
      "column": 3,
      "severity": 4,
      "message": 5
    }
  }
}
```

### タスクのグループ化（複合タスク）

```json
{
  "label": "Start All Servers",
  "dependsOn": [
    "Backend: Spring Boot Run",
    "Frontend: Vite Dev Server",
    "Mock Server: Start Prism"
  ],
  "group": "test",
  "problemMatcher": []
}
```

### runOptions - 実行オプション

```json
{
  "runOptions": {
    "runOn": "folderOpen",
    "instanceLimit": 1
  }
}
```

**runOn:**
- `"default"` - 手動実行のみ
- `"folderOpen"` - フォルダを開いたときに自動実行

**instanceLimit:**
- 同時実行可能なインスタンス数

---

## 変数リファレンス

### よく使う変数

| 変数 | 説明 | 例 |
|------|------|-----|
| `${workspaceFolder}` | ワークスペースルート | `/home/user/project` |
| `${workspaceFolderBasename}` | フォルダ名 | `project` |
| `${file}` | 現在開いているファイルのパス | `/path/to/file.ts` |
| `${fileBasename}` | ファイル名 | `file.ts` |
| `${fileDirname}` | ファイルのディレクトリ | `/path/to` |
| `${fileExtname}` | ファイルの拡張子 | `.ts` |
| `${cwd}` | 現在の作業ディレクトリ | `/current/dir` |
| `${lineNumber}` | カーソルの行番号 | `42` |
| `${selectedText}` | 選択中のテキスト | `selected text` |
| `${env:PATH}` | 環境変数 | システムのPATH |

### 複数ワークスペースフォルダ

```json
{
  "options": {
    "cwd": "${workspaceFolder:backend}"
  }
}
```

---

## プロジェクト実例

### 補給管理システムのタスク構成

```
タスク階層:
├── ビルド系
│   ├── Backend: Gradle Build (デフォルト)
│   ├── Backend: Gradle Clean Build
│   ├── Frontend: npm install
│   └── Mock Server: npm install
│
├── 実行系
│   ├── Backend: Spring Boot Run (バックグラウンド)
│   ├── Frontend: Vite Dev Server (バックグラウンド)
│   ├── Mock Server: Start Prism (バックグラウンド)
│   ├── Start All Servers (Mock)
│   └── Start All Servers (Real Backend)
│
└── ドキュメント系
    ├── Backend: Generate JavaDoc
    ├── Backend: Open JavaDoc
    ├── Frontend: Generate TypeDoc
    ├── Frontend: Serve TypeDoc
    └── Generate All Documentation
```

### 実際のタスク例（抜粋）

```json
{
  "version": "2.0.0",
  "tasks": [
    // デフォルトビルド
    {
      "label": "Backend: Gradle Build",
      "type": "shell",
      "command": "./gradlew",
      "args": ["build", "--info"],
      "options": {
        "cwd": "${workspaceFolder}/backend"
      },
      "group": {
        "kind": "build",
        "isDefault": true
      },
      "problemMatcher": []
    },

    // バックグラウンド実行（サーバー）
    {
      "label": "Frontend: Vite Dev Server",
      "type": "shell",
      "command": "npm",
      "args": ["run", "dev"],
      "options": {
        "cwd": "${workspaceFolder}/frontend"
      },
      "isBackground": true,
      "problemMatcher": {
        "pattern": {
          "regexp": "."
        },
        "background": {
          "activeOnStart": true,
          "beginsPattern": "VITE.*ready in",
          "endsPattern": "Local:.*http://localhost:5173"
        }
      }
    },

    // 複合タスク
    {
      "label": "Generate All Documentation",
      "dependsOn": [
        "Backend: Generate JavaDoc",
        "Frontend: Generate TypeDoc"
      ],
      "presentation": {
        "reveal": "always",
        "panel": "new"
      },
      "problemMatcher": []
    }
  ]
}
```

---

## トラブルシューティング

### Q1: タスクが見つからない

**症状:** `Ctrl+Shift+P` → "Tasks: Run Task" でタスクが表示されない

**解決策:**
1. `tasks.json` の構文エラーをチェック
2. VSCodeをリロード（`Ctrl+Shift+P` → "Developer: Reload Window"）
3. `label` がユニークか確認

### Q2: コマンドが見つからない

**症状:** `command not found` エラー

**解決策:**
1. コマンドのパスを確認
   ```json
   "command": "${workspaceFolder}/backend/gradlew"
   ```
2. 実行権限を確認
   ```bash
   chmod +x backend/gradlew
   ```
3. 環境変数PATHを設定
   ```json
   "options": {
     "env": {
       "PATH": "${env:PATH}:/custom/path"
     }
   }
   ```

### Q3: 作業ディレクトリが違う

**症状:** ファイルが見つからないエラー

**解決策:**
```json
{
  "options": {
    "cwd": "${workspaceFolder}/backend"
  }
}
```

### Q4: バックグラウンドタスクが終了しない

**症状:** サーバータスクが「実行中」のまま

**解決策:**
```json
{
  "isBackground": true,
  "problemMatcher": {
    "background": {
      "beginsPattern": "Starting",
      "endsPattern": "Started"  // ← このパターンに一致する出力が必要
    }
  }
}
```

### Q5: 複数インスタンスが起動してしまう

**解決策:**
```json
{
  "presentation": {
    "panel": "dedicated"  // 専用パネルを使用
  },
  "runOptions": {
    "instanceLimit": 1  // 同時実行は1つまで
  }
}
```

---

## ベストプラクティス

### 1. わかりやすい命名

```json
// ❌ 悪い例
{"label": "task1"}

// ✅ 良い例
{"label": "Backend: Gradle Build"}
```

### 2. グループ化

関連するタスクは同じプレフィックスを使用:
- `Backend: ...`
- `Frontend: ...`
- `Mock Server: ...`

### 3. デフォルトタスクの設定

最もよく使うタスクをデフォルトに:
```json
{
  "group": {
    "kind": "build",
    "isDefault": true
  }
}
```

### 4. エラーハンドリング

```json
{
  "problemMatcher": "$tsc",  // TypeScriptエラーを検出
  "presentation": {
    "reveal": "always"  // エラー時も出力を表示
  }
}
```

### 5. ドキュメント化

複雑なタスクにはコメントを追加:
```json
{
  // この複合タスクは、ビルド → テスト → デプロイの順で実行します
  "label": "Full Deploy",
  "dependsOn": ["build", "test", "deploy"]
}
```

---

## 参考リンク

- [VSCode Tasks Documentation](https://code.visualstudio.com/docs/editor/tasks)
- [Task Schema](https://code.visualstudio.com/docs/editor/tasks-appendix)
- [Variables Reference](https://code.visualstudio.com/docs/editor/variables-reference)

---

## まとめ

VSCodeのタスク機能を使うと:
- ✅ ビルド・実行・テストを統一的に管理
- ✅ キーボードショートカットで高速実行
- ✅ チーム全体で同じ環境を共有
- ✅ 複雑なコマンドをシンプルに実行

このガイドを参考に、プロジェクトに合ったタスクを定義してください。