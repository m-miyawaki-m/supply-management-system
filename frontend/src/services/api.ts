import axios from 'axios';

// 環境変数からベースURLを取得
// モックサーバー: http://localhost:4010 (各サービスで /api/supplies のように指定)
// 実サーバー: 空文字列 (各サービスで /api/supplies のように指定し、Viteプロキシが処理)
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  headers: {
    'Content-Type': 'application/json',
  },
});

// リクエストインターセプター（将来の拡張用）
api.interceptors.request.use(
  (config) => {
    // 認証トークンの追加などをここで実施可能
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// レスポンスインターセプター（将来の拡張用）
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // 共通エラーハンドリング
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export default api;
