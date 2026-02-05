interface ProtectedFetchOptions extends RequestInit {
  headers?: Record<string, string>;
}

export const protectedFetch = async (url: string, options: ProtectedFetchOptions = {}) => {
  const token = localStorage.getItem('token');

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...options.headers,
    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
  };

  try {
    const response = await fetch(url, {
      ...options,
      headers,
    });
    if (response.status === 401 || response.status === 403) {
      console.error('Сесія завершена або доступ заборонено');
      
      localStorage.removeItem('token');
      localStorage.removeItem('isAuthenticated');
      localStorage.removeItem('username');
      localStorage.removeItem('role');

      window.location.href = '/login'; 
      return Promise.reject('Unauthorized');
    }

    if (response.ok) {
      const clonedResponse = response.clone();
      clonedResponse.json().then(data => {
        localStorage.setItem(`cache_${url}`, JSON.stringify(data));
        localStorage.setItem(`cache_${url}_time`, new Date().toISOString());
      }).catch(err => console.error("Помилка запису в кеш:", err));
    }

    return response;

  } catch (error) {
    const cachedData = localStorage.getItem(`cache_${url}`);
    
    if (cachedData) {
      console.warn(`Мережа недоступна. Використовую офлайн-кеш для: ${url}`);
      return new Response(cachedData, {
        status: 200,
        headers: { 'Content-Type': 'application/json' }
      });
    }
    throw error;
  }
};