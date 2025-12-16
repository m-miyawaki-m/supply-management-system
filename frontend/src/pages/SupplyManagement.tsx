import React, { useState } from 'react';
import { useSupplies } from '../hooks/useSupplies';
import SupplyList from '../components/supply/SupplyList';
import SupplyForm from '../components/supply/SupplyForm';
import { Supply, SupplyFormData } from '../types/supply';
import { supplyService } from '../services/supplyService';

const SupplyManagement: React.FC = () => {
  const { supplies, loading, error, refetch } = useSupplies();
  const [showForm, setShowForm] = useState(false);
  const [editingSupply, setEditingSupply] = useState<Supply | undefined>(undefined);

  const handleCreate = () => {
    setEditingSupply(undefined);
    setShowForm(true);
  };

  const handleEdit = (supply: Supply) => {
    setEditingSupply(supply);
    setShowForm(true);
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('本当に削除しますか?')) {
      try {
        await supplyService.delete(id);
        refetch();
      } catch (err) {
        alert('削除に失敗しました');
        console.error('Error deleting supply:', err);
      }
    }
  };

  const handleSubmit = async (data: SupplyFormData) => {
    try {
      if (editingSupply) {
        await supplyService.update(editingSupply.id, data);
      } else {
        await supplyService.create(data);
      }
      setShowForm(false);
      setEditingSupply(undefined);
      refetch();
    } catch (err) {
      alert('保存に失敗しました');
      console.error('Error saving supply:', err);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingSupply(undefined);
  };

  const handleExport = async () => {
    try {
      const blob = await supplyService.exportExcel();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'supplies.xlsx';
      a.click();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      alert('エクスポートに失敗しました');
      console.error('Error exporting:', err);
    }
  };

  if (loading) {
    return <div style={styles.container}>読み込み中...</div>;
  }

  if (error) {
    return <div style={styles.container}>{error}</div>;
  }

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>補給品管理</h1>
        <div style={styles.buttonGroup}>
          <button onClick={handleCreate} style={{ ...styles.button, ...styles.createButton }}>
            新規登録
          </button>
          <button onClick={handleExport} style={{ ...styles.button, ...styles.exportButton }}>
            Excelエクスポート
          </button>
        </div>
      </div>

      {showForm ? (
        <SupplyForm supply={editingSupply} onSubmit={handleSubmit} onCancel={handleCancel} />
      ) : (
        <SupplyList supplies={supplies} onEdit={handleEdit} onDelete={handleDelete} />
      )}
    </div>
  );
};

const styles = {
  container: {
    padding: '2rem',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '2rem',
  },
  title: {
    color: '#2c3e50',
    margin: 0,
  },
  buttonGroup: {
    display: 'flex',
    gap: '1rem',
  },
  button: {
    padding: '0.75rem 1.5rem',
    border: 'none',
    borderRadius: '4px',
    fontSize: '1rem',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  createButton: {
    backgroundColor: '#27ae60',
    color: 'white',
  },
  exportButton: {
    backgroundColor: '#3498db',
    color: 'white',
  },
};

export default SupplyManagement;
