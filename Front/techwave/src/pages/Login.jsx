// src/pages/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import api from '../services/api';
import styles from './Login.module.css';

export default function Login() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const res = await api.get('/usuarios');
      const usuario = res.data.find(
        u => u.email === email && u.senha === senha
      );
      if (!usuario) {
        setErro('E‑mail ou senha incorretos');
        return;
      }
      localStorage.setItem('usuarioLogado', JSON.stringify(usuario));
      navigate('/');
    } catch {
      setErro('Erro ao conectar com o servidor');
    }
  };

  return (
    <>
      <Header />
      <main className={styles.main}>
        {/* Área para banner/promocional (pode deixar vazia ou inserir imagem) */}
        <div className={styles.hero}></div>

        {/* Card de login */}
        <div className={styles.loginWrapper}>
          <div className={styles.loginCard}>
            <h1 className={styles.heading}>ACESSE SUA CONTA</h1>

            {erro && <div className={styles.error}>{erro}</div>}

            <form onSubmit={handleSubmit} className={styles.form}>
              <div className={styles.inputGroup}>
                <label htmlFor="email">E‑mail</label>
                <input
                  id="email"
                  type="email"
                  value={email}
                  onChange={e => setEmail(e.target.value)}
                  placeholder="seu@exemplo.com"
                  required
                />
              </div>

              <div className={styles.inputGroup}>
                <label htmlFor="senha">Senha</label>
                <input
                  id="senha"
                  type="password"
                  value={senha}
                  onChange={e => setSenha(e.target.value)}
                  placeholder="••••••••"
                  required
                />
              </div>

              <button type="submit" className={styles.button}>
                Entrar
              </button>
            </form>
          </div>
        </div>
      </main>
    </>
  );
}
