#!/bin/bash
# VSCode tasks用のファイルオープナースクリプト
# WSL環境でWindowsパスに変換して開く

FILE_PATH="$1"

# WSL環境かチェック
if command -v wslpath >/dev/null 2>&1; then
    # WSLパスをWindowsパスに変換
    WINDOWS_PATH=$(wslpath -w "$FILE_PATH")
    if command -v explorer.exe >/dev/null 2>&1; then
        explorer.exe "$WINDOWS_PATH"
        exit 0
    fi
fi

# explorer.exeがある場合（WSL）
if command -v explorer.exe >/dev/null 2>&1; then
    explorer.exe "$FILE_PATH"
    exit 0
fi

# xdg-openがある場合（Linux）
if command -v xdg-open >/dev/null 2>&1; then
    xdg-open "$FILE_PATH"
    exit 0
fi

# どれも見つからない場合
echo "Error: No suitable file opener found (tried: wslpath+explorer.exe, explorer.exe, xdg-open)"
exit 1