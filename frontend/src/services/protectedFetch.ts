interface ProtectedFetchOptions extends RequestInit {
  headers?: Record<string, string>;
}

export const protectedFetch = async (url: string, options: ProtectedFetchOptions = {}) => {
  const token = localStorage.getItem('token');

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(url, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    console.error('Сесія завершена або токен невалідний');
    localStorage.removeItem('token');
    
  }

  return response;
};