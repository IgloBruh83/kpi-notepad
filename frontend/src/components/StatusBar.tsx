import React, { useState, useEffect } from 'react';

const StatusBar: React.FC = () => {
  const [isOffline, setIsOffline] = useState(!navigator.onLine);
  const [informed, setInformed] = useState(false);
  const [lastUpdate, setLastUpdate] = useState<string | null>(null);

  useEffect(() => {
    setIsOffline(!navigator.onLine);

    const handleOnline = () => setIsOffline(false);
    const handleOffline = () => setIsOffline(true);

    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);

    const getLatestTimestamp = () => {
      const keys = Object.keys(localStorage).filter(k => k.endsWith('_time'));
      const dates = keys.map(k => localStorage.getItem(k)).filter(Boolean) as string[];
      
      if (dates.length > 0) {
        const latest = dates.sort().reverse()[0];
        setLastUpdate(new Date(latest).toLocaleString('uk-UA'));
      }
    };

    getLatestTimestamp();

    return () => {
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, [isOffline]);

  if (!isOffline || informed) return null;

  return (
    <div className="offline-status-bar">
      <div className="status-content">
        <i className="fa-solid fa-plug-circle-xmark"></i>
        <span>
          Ви офлайн. Показано дані станом на: <strong>{lastUpdate || 'невідомо'}</strong>
        </span>
      </div>
      <button className="status-close" onClick={() => setInformed(true)}>
        ✕
      </button>
    </div>
  );
};

export default StatusBar;