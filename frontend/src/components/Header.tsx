import React from 'react';
import { Link } from 'react-router-dom';

export default function Header() {
  return (
    <header>
      <h1>KPI Notepad</h1>
      <nav>
        <Link to="/calendar">Календар</Link>
        <Link to="/tasks">Завдання</Link>
        <Link to="/subjects">Предмети</Link>
        <Link to="/group">Група</Link>
      </nav>
      <div>
        {/*<button className="account-btn">Аккаунт</button>*/}
      </div>
    </header>
  );
}