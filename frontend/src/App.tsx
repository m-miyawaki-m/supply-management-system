import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/common/Header';
import Dashboard from './pages/Dashboard';
import SupplyManagement from './pages/SupplyManagement';
import InventoryManagement from './pages/InventoryManagement';

const App: React.FC = () => {
  return (
    <Router>
      <div style={styles.app}>
        <Header />
        <main style={styles.main}>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/supplies" element={<SupplyManagement />} />
            <Route path="/inventory" element={<InventoryManagement />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

const styles = {
  app: {
    minHeight: '100vh',
    backgroundColor: '#ecf0f1',
  },
  main: {
    minHeight: 'calc(100vh - 80px)',
  },
};

export default App;
