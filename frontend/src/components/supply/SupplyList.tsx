import React from 'react';
import { Supply } from '../../types/supply';

interface SupplyListProps {
  supplies: Supply[];
  onEdit: (supply: Supply) => void;
  onDelete: (id: number) => void;
}

const SupplyList: React.FC<SupplyListProps> = ({ supplies, onEdit, onDelete }) => {
  return (
    <div style={styles.container}>
      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.th}>ID</th>
            <th style={styles.th}>品名</th>
            <th style={styles.th}>数量</th>
            <th style={styles.th}>単価</th>
            <th style={styles.th}>カテゴリ</th>
            <th style={styles.th}>操作</th>
          </tr>
        </thead>
        <tbody>
          {supplies.map((supply) => (
            <tr key={supply.id} style={styles.tr}>
              <td style={styles.td}>{supply.id}</td>
              <td style={styles.td}>{supply.name}</td>
              <td style={styles.td}>{supply.quantity}</td>
              <td style={styles.td}>¥{supply.unitPrice.toLocaleString()}</td>
              <td style={styles.td}>{supply.category}</td>
              <td style={styles.td}>
                <button
                  onClick={() => onEdit(supply)}
                  style={{ ...styles.button, ...styles.editButton }}
                >
                  編集
                </button>
                <button
                  onClick={() => onDelete(supply.id)}
                  style={{ ...styles.button, ...styles.deleteButton }}
                >
                  削除
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const styles = {
  container: {
    marginTop: '1rem',
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse' as const,
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  },
  th: {
    backgroundColor: '#34495e',
    color: 'white',
    padding: '0.75rem',
    textAlign: 'left' as const,
    fontWeight: 'bold',
  },
  tr: {
    borderBottom: '1px solid #ddd',
  },
  td: {
    padding: '0.75rem',
  },
  button: {
    padding: '0.4rem 0.8rem',
    marginRight: '0.5rem',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '0.9rem',
  },
  editButton: {
    backgroundColor: '#3498db',
    color: 'white',
  },
  deleteButton: {
    backgroundColor: '#e74c3c',
    color: 'white',
  },
};

export default SupplyList;
