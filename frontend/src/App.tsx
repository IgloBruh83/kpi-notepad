import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/Header';
import CalendarPage from './pages/CalendarPage';
import LoginPage from './pages/LoginPage';
import UnderConstructionPage from './pages/UnderConstruct';
import './assets/css/fonts.css';
import './assets/css/style.css';
import './assets/css/auth.css';

const App: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    localStorage.getItem('isAuthenticated') === 'true'
  );

  const handleLogin = () => {
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    localStorage.clear();
    setIsAuthenticated(false);
  };

  if (!isAuthenticated) {
    return <LoginPage onLogin={handleLogin} />;
  }

  return (
    <Router>
      <div className="app-wrapper">
        <Header />
        <main>
          <Routes>
            <Route path="/calendar" element={<CalendarPage />} />
            <Route path="/tasks" element={<UnderConstructionPage />} />
            <Route path="/subjects" element={<UnderConstructionPage />} />
            <Route path="/group" element={<UnderConstructionPage />} />
            <Route path="/" element={<Navigate to="/calendar" replace />} />
            <Route path="*" element={<Navigate to="/calendar" replace />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;