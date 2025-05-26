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

  const getCliente = async(user) => {
    const res = await api.get(`/clientes/${user.idUsuario}`);
    return res.data;
  }
  const getAdmin = async(user) => {
    const res = await api.get(`/admin/${user.idUsuario}`);
    return res.data;
  }

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
      localStorage.setItem('usuario Logado', JSON.stringify(usuario));
      console.log('usario',  JSON.stringify(usuario));
      const cliente = await getCliente(usuario);
      const admin = await getAdmin(usuario);
      if (!!cliente){
        localStorage.setItem(`Cliente`, JSON.stringify(cliente));
      }
      else if (!!admin){
      localStorage.setItem(`Administrador`, JSON.stringify(admin));
    
      }
      
      navigate('/');
    } catch(error) {
      console.log("ERROR", error);
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
