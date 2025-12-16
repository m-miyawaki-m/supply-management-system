import api from './api';
import { InventoryTransaction, InventoryTransactionRequest } from '../types/supply';

export const inventoryService = {
  // 在庫トランザクション一覧取得
  getAll: async (): Promise<InventoryTransaction[]> => {
    const response = await api.get<InventoryTransaction[]>('/inventory');
    return response.data;
  },

  // 入庫登録
  stockIn: async (data: InventoryTransactionRequest): Promise<InventoryTransaction> => {
    const response = await api.post<InventoryTransaction>('/inventory/in', data);
    return response.data;
  },

  // 出庫登録
  stockOut: async (data: InventoryTransactionRequest): Promise<InventoryTransaction> => {
    const response = await api.post<InventoryTransaction>('/inventory/out', data);
    return response.data;
  },

  // 補給品別トランザクション取得
  getBySupplyId: async (supplyId: number): Promise<InventoryTransaction[]> => {
    const response = await api.get<InventoryTransaction[]>(`/inventory/supply/${supplyId}`);
    return response.data;
  },
};
