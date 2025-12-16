import React, { useState, useEffect } from 'react';
import { Supply, SupplyFormData } from '../../types/supply';

interface SupplyFormProps {
  supply?: Supply;
  onSubmit: (data: SupplyFormData) => void;
  onCancel: () => void;
}

const SupplyForm: React.FC<SupplyFormProps> = ({ supply, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState<SupplyFormData>({
    name: '',
    quantity: 0,
    unitPrice: 0,
    category: '',
  });

  useEffect(() => {
    if (supply) {
      setFormData({
        name: supply.name,
        quantity: supply.quantity,
        unitPrice: supply.unitPrice,
        category: supply.category,
      });
    }
  }, [supply]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'quantity' || name === 'unitPrice' ? Number(value) : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
      <h2 style={styles.title}>{supply ? '補給品編集' : '補給品登録'}</h2>

      <div style={styles.formGroup}>
        <label style={styles.label}>品名</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          style={styles.input}
        />
      </div>

      <div style={styles.formGroup}>
        <label style={styles.label}>数量</label>
        <input
          type="number"
          name="quantity"
          value={formData.quantity}
          onChange={handleChange}
          required
          min="0"
          style={styles.input}
        />
      </div>

      <div style={styles.formGroup}>
        <label style={styles.label}>単価</label>
        <input
          type="number"
          name="unitPrice"
          value={formData.unitPrice}
          onChange={handleChange}
          required
          min="0"
          step="0.01"
          style={styles.input}
        />
      </div>

      <div style={styles.formGroup}>
        <label style={styles.label}>カテゴリ</label>
        <input
          type="text"
          name="category"
          value={formData.category}
          onChange={handleChange}
          required
          style={styles.input}
        />
      </div>

      <div style={styles.buttonGroup}>
        <button type="submit" style={{ ...styles.button, ...styles.submitButton }}>
          {supply ? '更新' : '登録'}
        </button>
        <button type="button" onClick={onCancel} style={{ ...styles.button, ...styles.cancelButton }}>
          キャンセル
        </button>
      </div>
    </form>
  );
};

const styles = {
  form: {
    backgroundColor: 'white',
    padding: '2rem',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
    maxWidth: '500px',
    margin: '2rem auto',
  },
  title: {
    marginTop: 0,
    marginBottom: '1.5rem',
    color: '#2c3e50',
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
  input: {
    width: '100%',
    padding: '0.5rem',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '1rem',
    boxSizing: 'border-box' as const,
  },
  buttonGroup: {
    display: 'flex',
    gap: '1rem',
    marginTop: '1.5rem',
  },
  button: {
    flex: 1,
    padding: '0.75rem',
    border: 'none',
    borderRadius: '4px',
    fontSize: '1rem',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  submitButton: {
    backgroundColor: '#27ae60',
    color: 'white',
  },
  cancelButton: {
    backgroundColor: '#95a5a6',
    color: 'white',
  },
};

export default SupplyForm;
