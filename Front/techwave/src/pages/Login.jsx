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
        setErro('E-mail ou senha incorretos');
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
        
        <div className={styles.loginWrapper}>
          <div className={styles.loginCard}>
            <h1 className={styles.heading}>ACESSE SUA CONTA</h1>

            {erro && <div className={styles.error}>{erro}</div>}

            <form onSubmit={handleSubmit} className={styles.form}>
              <div className={styles.inputGroup}>
                <label htmlFor="email">E-mail</label>
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

              <a href="#!" className={styles.forgot}>
                Esqueceu a senha?
              </a>
            </form>

            <div className={styles.divider}>
              <span>OU</span>
            </div>

            <div className={styles.socials}>
              <button className={styles.socialButton}>
                <span className={styles.socialIcon}>G</span> Google
              </button>
              <button className={styles.socialButton}>
                <span className={styles.socialIcon}></span> Apple
              </button>
            </div>

            <p className={styles.signupText}>
              Novo no TechWave?{' '}
              <span
                className={styles.signupLink}
                onClick={() => navigate('/cadastro')}
              >
                Cadastre-se
              </span>
            </p>
          </div>
        </div>
      </main>
    </>
  );
}
