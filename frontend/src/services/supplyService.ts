import api from './api';
import { Supply, SupplyFormData } from '../types/supply';

export const supplyService = {
  // 補給品一覧取得
  getAll: async (): Promise<Supply[]> => {
    const response = await api.get<Supply[]>('/supplies');
    return response.data;
  },

  // 補給品詳細取得
  getById: async (id: number): Promise<Supply> => {
    const response = await api.get<Supply>(`/supplies/${id}`);
    return response.data;
  },

  // 補給品登録
  create: async (data: SupplyFormData): Promise<Supply> => {
    const response = await api.post<Supply>('/supplies', data);
    return response.data;
  },

  // 補給品更新
  update: async (id: number, data: SupplyFormData): Promise<Supply> => {
    const response = await api.put<Supply>(`/supplies/${id}`, data);
    return response.data;
  },

  // 補給品削除
  delete: async (id: number): Promise<void> => {
    await api.delete(`/supplies/${id}`);
  },

  // CSVインポート
  importCsv: async (file: File): Promise<string> => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await api.post<string>('/supplies/import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },

  // Excelエクスポート
  exportExcel: async (): Promise<Blob> => {
    const response = await api.get('/supplies/export', {
      responseType: 'blob',
    });
    return response.data;
  },
};
