import React, { useState, useEffect } from 'react';
import { InventoryTransaction, InventoryTransactionRequest } from '../types/supply';
import { inventoryService } from '../services/inventoryService';
import { useSupplies } from '../hooks/useSupplies';

const InventoryManagement: React.FC = () => {
  const { supplies } = useSupplies();
  const [transactions, setTransactions] = useState<InventoryTransaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState<InventoryTransactionRequest>({
    supplyId: 0,
    type: 'IN',
    quantity: 0,
    note: '',
  });

  const fetchTransactions = async () => {
    try {
      setLoading(true);
      const data = await inventoryService.getAll();
      setTransactions(data);
    } catch (err) {
      console.error('Error fetching transactions:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransactions();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (formData.type === 'IN') {
        await inventoryService.stockIn(formData);
      } else {
        await inventoryService.stockOut(formData);
      }
      setFormData({ supplyId: 0, type: 'IN', quantity: 0, note: '' });
      fetchTransactions();
    } catch (err) {
      alert('登録に失敗しました');
      console.error('Error submitting transaction:', err);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'quantity' || name === 'supplyId' ? Number(value) : value,
    }));
  };

  if (loading && supplies.length === 0) {
    return <div style={styles.container}>読み込み中...</div>;
  }

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>在庫管理</h1>

      <div style={styles.formContainer}>
        <h2 style={styles.formTitle}>入出庫登録</h2>
        <form onSubmit={handleSubmit} style={styles.form}>
          <div style={styles.formGroup}>
            <label style={styles.label}>補給品</label>
            <select
              name="supplyId"
              value={formData.supplyId}
              onChange={handleChange}
              required
              style={styles.select}
            >
              <option value="">選択してください</option>
              {supplies.map((supply) => (
                <option key={supply.id} value={supply.id}>
                  {supply.name} (在庫: {supply.quantity})
                </option>
              ))}
            </select>
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>種別</label>
            <select
              name="type"
              value={formData.type}
              onChange={handleChange}
              required
              style={styles.select}
            >
              <option value="IN">入庫</option>
              <option value="OUT">出庫</option>
            </select>
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>数量</label>
            <input
              type="number"
              name="quantity"
              value={formData.quantity}
              onChange={handleChange}
              required
              min="1"
              style={styles.input}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>備考</label>
            <textarea
              name="note"
              value={formData.note}
              onChange={handleChange}
              style={styles.textarea}
            />
          </div>

          <button type="submit" style={styles.submitButton}>
            登録
          </button>
        </form>
      </div>

      <div style={styles.tableContainer}>
        <h2 style={styles.tableTitle}>トランザクション履歴</h2>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>日時</th>
              <th style={styles.th}>補給品ID</th>
              <th style={styles.th}>種別</th>
              <th style={styles.th}>数量</th>
              <th style={styles.th}>備考</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.id} style={styles.tr}>
                <td style={styles.td}>
                  {new Date(transaction.transactionDate).toLocaleString()}
                </td>
                <td style={styles.td}>{transaction.supplyId}</td>
                <td style={styles.td}>
                  <span
                    style={{
                      ...styles.badge,
                      backgroundColor: transaction.type === 'IN' ? '#27ae60' : '#e74c3c',
                    }}
                  >
                    {transaction.type === 'IN' ? '入庫' : '出庫'}
                  </span>
                </td>
                <td style={styles.td}>{transaction.quantity}</td>
                <td style={styles.td}>{transaction.note || '-'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

const styles = {
  container: {
    padding: '2rem',
  },
  title: {
    color: '#2c3e50',
    marginBottom: '2rem',
  },
  formContainer: {
    backgroundColor: 'white',
    padding: '2rem',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
    marginBottom: '2rem',
  },
  formTitle: {
    marginTop: 0,
    color: '#34495e',
  },
  form: {
    maxWidth: '500px',
  },
  formGroup: {
    marginBottom: '1rem',
  },
  label: {
    display: 'block',
    marginBottom: '0.5rem',
    fontWeight: 'bold',
    color: '#34495e',
  },
  select: {
    width: '100%',
    padding: '0.5rem',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '1rem',
  },
  input: {
    width: '100%',
    padding: '0.5rem',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '1rem',
    boxSizing: 'border-box' as const,
  },
  textarea: {
    width: '100%',
    padding: '0.5rem',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '1rem',
    minHeight: '80px',
    boxSizing: 'border-box' as const,
  },
  submitButton: {
    padding: '0.75rem 1.5rem',
    backgroundColor: '#27ae60',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    fontSize: '1rem',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  tableContainer: {
    backgroundColor: 'white',
    padding: '2rem',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  },
  tableTitle: {
    marginTop: 0,
    color: '#34495e',
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse' as const,
  },
  th: {
    backgroundColor: '#34495e',
    color: 'white',
    padding: '0.75rem',
    textAlign: 'left' as const,
  },
  tr: {
    borderBottom: '1px solid #ddd',
  },
  td: {
    padding: '0.75rem',
  },
  badge: {
    padding: '0.25rem 0.5rem',
    borderRadius: '4px',
    color: 'white',
    fontSize: '0.875rem',
    fontWeight: 'bold',
  },
};

export default InventoryManagement;
