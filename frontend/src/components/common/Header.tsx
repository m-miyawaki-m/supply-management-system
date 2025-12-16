import React from 'react';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
  return (
    <header style={styles.header}>
      <h1 style={styles.title}>補給管理システム</h1>
      <nav style={styles.nav}>
        <Link to="/" style={styles.link}>ダッシュボード</Link>
        <Link to="/supplies" style={styles.link}>補給品管理</Link>
        <Link to="/inventory" style={styles.link}>在庫管理</Link>
      </nav>
    </header>
  );
};

const styles = {
  header: {
    backgroundColor: '#2c3e50',
    color: 'white',
    padding: '1rem 2rem',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  title: {
    margin: 0,
    fontSize: '1.5rem',
  },
  nav: {
    display: 'flex',
    gap: '1.5rem',
  },
  link: {
    color: 'white',
    textDecoration: 'none',
    fontSize: '1rem',
    padding: '0.5rem 1rem',
    borderRadius: '4px',
    transition: 'background-color 0.3s',
  },
};

export default Header;
