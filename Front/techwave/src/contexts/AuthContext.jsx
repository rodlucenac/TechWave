// src/contexts/AuthContext.jsx
import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // ao carregar o app, tenta ler do localStorage
    const stored = localStorage.getItem('usuarioLogado');
    if (stored) setUser(JSON.parse(stored));
  }, []);

  const login = usuario => {
    localStorage.setItem('usuarioLogado', JSON.stringify(usuario));
    setUser(usuario);
  };

  const logout = () => {
    localStorage.removeItem('usuarioLogado');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}