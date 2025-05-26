import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import api from '../services/api';
import styles from './Cadastro.module.css';

export default function Cadastro() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [senha2, setSenha2] = useState('');
  const [cpf, setCpf] = useState('');
  const [rua, setRua] = useState('');
  const [numero, setNumero] = useState('');
  const [bairro, setBairro] = useState('');
  const [cidade, setCidade] = useState('');
  const [estado, setEstado] = useState('');
  const [cep, setCep] = useState('');
  const [erro, setErro] = useState('');
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  const handleSubmit = async e => {
    e.preventDefault();
    if (senha !== senha2) {
      setErro('As senhas não coincidem');
      return;
    }
    if (!rua || !numero || !bairro || !cidade || !estado || !cep) {
      setErro('Preencha todos os campos de endereço.');
      return;
    }
    if (cpf.length !== 11) {
      setErro('CPF deve conter 11 caracteres.');
      return;
    }
    if (cep.length !== 8) {
      setErro('CEP deve conter 8 caracteres.');
      return;
    }
    try {
      const payload = {
        usuario: {
          nome,
          email,
          senha,
          cpf
        },
        endereco: {
          rua,
          numero,
          bairro,
          cidade,
          estado,
          cep
        }
      };

      await api.post('/usuarios/registro', payload);
      navigate('/login');
    } catch {
      setErro('Falha ao cadastrar. Tente novamente.');
    }
  };

  return (
    <>
      <Header />
      <main className={styles.main}>
        <div className={styles.loginCard}>
          <button
            type="button"
            className={styles.backButton}
            onClick={handleBack}
          >
            Voltar
          </button>

          <h1 className={styles.heading}>CADASTRE-SE</h1>

          <section className={styles.section}>
            <h2 className={styles.subheading}>Dados da Conta</h2>
            {erro && <div className={styles.error}>{erro}</div>}
            <div className={styles.grid}>
              <div className={styles.gridItem}>
                <label>Nome completo</label>
                <input
                  type="text"
                  value={nome}
                  onChange={e => setNome(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>CPF</label>
                <input
                  type="text"
                  value={cpf}
                  onChange={e => setCpf(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>E-mail</label>
                <input
                  type="email"
                  value={email}
                  onChange={e => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Senha</label>
                <input
                  type="password"
                  value={senha}
                  onChange={e => setSenha(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Confirmar senha</label>
                <input
                  type="password"
                  value={senha2}
                  onChange={e => setSenha2(e.target.value)}
                  required
                />
              </div>
            </div>
          </section>

          <div className={styles.divider}></div>

          {/* Seção de endereço */}
          <section className={styles.section}>
            <h2 className={styles.subheading}>Endereço</h2>
            <div className={styles.grid}>
              <div className={styles.gridItemFull}>
                <label>Rua</label>
                <input
                  type="text"
                  value={rua}
                  onChange={e => setRua(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Número</label>
                <input
                  type="text"
                  value={numero}
                  onChange={e => setNumero(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Bairro</label>
                <input
                  type="text"
                  value={bairro}
                  onChange={e => setBairro(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Cidade</label>
                <input
                  type="text"
                  value={cidade}
                  onChange={e => setCidade(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>Estado</label>
                <input
                  type="text"
                  maxLength={2}
                  value={estado}
                  onChange={e => setEstado(e.target.value)}
                  required
                />
              </div>
              <div className={styles.gridItem}>
                <label>CEP</label>
                <input
                  type="text"
                  value={cep}
                  onChange={e => setCep(e.target.value)}
                  required
                />
              </div>
            </div>
          </section>

          <button className={styles.button} onClick={handleSubmit}>
            CONTINUAR
          </button>
        </div>
      </main>
    </>
  );
}
