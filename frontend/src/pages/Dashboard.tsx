import React from 'react';
import { Link } from 'react-router-dom';

const Dashboard: React.FC = () => {
  return (
    <div style={styles.container}>
      <h1 style={styles.title}>ダッシュボード</h1>
      <div style={styles.cardContainer}>
        <Link to="/supplies" style={styles.card}>
          <h2 style={styles.cardTitle}>補給品管理</h2>
          <p style={styles.cardDescription}>
            補給品の登録・編集・削除・一覧表示
          </p>
        </Link>

        <Link to="/inventory" style={styles.card}>
          <h2 style={styles.cardTitle}>在庫管理</h2>
          <p style={styles.cardDescription}>
            入庫・出庫登録、在庫照会
          </p>
        </Link>

        <div style={styles.card}>
          <h2 style={styles.cardTitle}>ファイル入出力</h2>
          <p style={styles.cardDescription}>
            CSVインポート、Excelエクスポート
          </p>
        </div>
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
  cardContainer: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '2rem',
  },
  card: {
    backgroundColor: 'white',
    padding: '2rem',
    borderRadius: '8px',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
    textDecoration: 'none',
    color: 'inherit',
    transition: 'transform 0.2s, box-shadow 0.2s',
    cursor: 'pointer',
  },
  cardTitle: {
    color: '#34495e',
    marginTop: 0,
    marginBottom: '1rem',
  },
  cardDescription: {
    color: '#7f8c8d',
    margin: 0,
    lineHeight: 1.6,
  },
};

export default Dashboard;
