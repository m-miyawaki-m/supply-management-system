export interface Supply {
  id: number;
  name: string;
  quantity: number;
  unitPrice: number;
  category: string;
  createdAt: string;
  updatedAt: string;
}

export interface SupplyFormData {
  name: string;
  quantity: number;
  unitPrice: number;
  category: string;
}

export interface InventoryTransaction {
  id: number;
  supplyId: number;
  type: 'IN' | 'OUT';
  quantity: number;
  transactionDate: string;
  note?: string;
}

export interface InventoryTransactionRequest {
  supplyId: number;
  type: 'IN' | 'OUT';
  quantity: number;
  note?: string;
}
