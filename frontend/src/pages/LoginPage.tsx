import React, { useState } from 'react';

interface Props {
  onLogin: () => void;
}

const LoginPage: React.FC<Props> = ({ onLogin }) => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // 1. Використовуємо стандартний fetch для логіна (тут токен ще не потрібен)
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ login, password }),
      });

      // 2. Отримуємо дані згідно з твоєю новою структурою AuthResponse.java
      const data = await response.json();

      if (response.ok && data.token) { // Перевіряємо наявність токена
        // 3. Зберігаємо "паспорт" уlocalStorage для protectedFetch
        localStorage.setItem('token', data.token); 
        
        // 4. Зберігаємо допоміжні дані для UI та прав доступу
        localStorage.setItem('isAuthenticated', 'true');
        localStorage.setItem('username', data.username);
        localStorage.setItem('role', data.role);

        // Повідомляємо App.tsx про успішний вхід
        onLogin();
      } else {
        // Виводимо повідомлення про помилку з сервера
        alert(data.message || 'Помилка авторизації: Перевір логін або пароль');
      }
    } catch (error) {
      console.error('Помилка запиту:', error);
      alert('Не вдалося з’єднатися з сервером. Перевір, чи запущено Spring Boot та базу даних.');
    }
  };

  return (
    <div className="auth-container centering">
      <div className="block login-card">
        <h1>Авторизація</h1>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <input 
              type="text" 
              id="login" 
              placeholder="Логін" 
              value={login}
              onChange={(e) => setLogin(e.target.value)}
              required 
            />
          </div>
          
          <div className="input-group">
            <input 
              type="password" 
              id="password" 
              placeholder="Пароль" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required 
            />
          </div>
          
          <button type="submit" className="login-btn">Увійти</button>
          
          <div className="form-footer">
            Забули пароль? — <a href="https://t.me/IgloBruh83" target="_blank" rel="noreferrer">tg: @IgloBruh83</a>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;