import { useState, useEffect } from 'react';
import { Supply } from '../types/supply';
import { supplyService } from '../services/supplyService';

export const useSupplies = () => {
  const [supplies, setSupplies] = useState<Supply[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchSupplies = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await supplyService.getAll();
      setSupplies(data);
    } catch (err) {
      setError('補給品の取得に失敗しました');
      console.error('Error fetching supplies:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSupplies();
  }, []);

  return { supplies, loading, error, refetch: fetchSupplies };
};
