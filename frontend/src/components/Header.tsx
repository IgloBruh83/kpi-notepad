import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useWindowWidth } from '../hooks/useWindowWidth';

const Header: React.FC = () => {
  const width = useWindowWidth();
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const isMobile: boolean = width <= 768;

  const toggleMenu = (): void => setIsOpen(!isOpen);
  const closeMenu = (): void => setIsOpen(false);

  return (
    <header className="header-container">
      <h1>KPI Notepad</h1>
      
      {isMobile && (
        <button className="burger-btn" onClick={toggleMenu}>
          {isOpen ? '✕' : '☰'}
        </button>
      )}

      <nav className={`${isMobile ? 'nav-mobile' : 'nav-desktop'} ${isOpen ? 'is-open' : ''}`}>
        <Link to="/calendar" onClick={closeMenu}>Календар</Link>
        <Link to="/tasks" onClick={closeMenu}>Завдання</Link>
        <Link to="/subjects" onClick={closeMenu}>Предмети</Link>
        <Link to="/queue" onClick={closeMenu}>Черга</Link>
      </nav>

      {!isMobile && <div style={{ width: '40px' }}></div>}
    </header>
  );
}

export default Header;