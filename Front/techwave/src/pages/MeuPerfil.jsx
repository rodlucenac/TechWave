import React, { useContext, useState, useEffect } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import api from '../services/api';
import styles from './MeuPerfil.module.css';

export default function MeuPerfil() {
  const { user, logout } = useContext(AuthContext);
  const cliente = user?.detalhes;

  useEffect(() => {
    if (!cliente?.idUsuario) return;
    (async () => {
      const res = await api.get(`/clientes/${cliente.idUsuario}`);
      setNome(res.data.nome);
      setEmail(res.data.email);
      setTelefone(res.data.telefone);
    })();
  }, [cliente?.idUsuario]);

  // States para formulário
  const [nome, setNome] = useState(cliente?.nome || '');
  const [email, setEmail] = useState(user?.email || '');
  const [telefone, setTelefone] = useState(cliente?.telefone || '');
  const [senha, setSenha] = useState('');
  const [confirmaSenha, setConfirmaSenha] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState('');

  const handleSave = async e => {
    e.preventDefault();
    setMensagem('');
    setErro('');

    if (senha && senha !== confirmaSenha) {
      setErro('As senhas não coincidem.');
      return;
    }

    try {
      // Atualiza dados pessoais sem senha
      await api.put(`/clientes/${cliente.idUsuario}`, {
        nome,
        email,
        telefone
      });

      // Se nova senha for fornecida, atualiza via procedure
      if (senha) {
        await api.patch(`/clientes/${cliente.idUsuario}/credenciais`, {
          novaSenha: senha
        });
      }

      setMensagem('Perfil atualizado com sucesso!');
      setSenha('');
      setConfirmaSenha('');
    } catch (err) {
      console.error(err);
      setErro('Erro ao atualizar perfil. Tente novamente.');
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.heading}>Minha Conta</h1>

      {/* Mensagens de sucesso e erro */}
      {mensagem && <p className={styles.messageSuccess}>{mensagem}</p>}
      {erro && <p className={styles.messageError}>{erro}</p>}

      {/* Formulário de dados pessoais */}
      <form onSubmit={handleSave} className={styles.form}>
        <fieldset className={styles.fieldset}>
          <legend>Dados Pessoais</legend>

          <div className={styles.inputGroup}>
            <label htmlFor="nome">Nome completo</label>
            <input
              id="nome"
              type="text"
              value={nome}
              onChange={e => setNome(e.target.value)}
              required
            />
          </div>

          <div className={styles.inputGroup}>
            <label htmlFor="email">E-mail</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
          </div>

          <div className={styles.inputGroup}>
            <label htmlFor="telefone">Telefone</label>
            <input
              id="telefone"
              type="tel"
              value={telefone}
              onChange={e => setTelefone(e.target.value)}
            />
          </div>

          <div className={styles.inputGroup}>
            <label htmlFor="senha">Nova senha</label>
            <input
              id="senha"
              type="password"
              value={senha}
              onChange={e => setSenha(e.target.value)}
              placeholder="••••••••"
            />
          </div>

          <div className={styles.inputGroup}>
            <label htmlFor="confirmaSenha">Confirmar senha</label>
            <input
              id="confirmaSenha"
              type="password"
              value={confirmaSenha}
              onChange={e => setConfirmaSenha(e.target.value)}
              placeholder="••••••••"
            />
          </div>
        </fieldset>

        <button type="submit" className={styles.button}>
          Salvar alterações
        </button>
      </form>

      {/* Links rápidos */}
      <nav className={styles.quickLinks}>
        <a href="/meus-pedidos">Meus Pedidos</a>
        <a href="/meus-enderecos">Meus Endereços</a>
        <a href="/meus-metodos-pgto">Meus Métodos de Pagamento</a>
      </nav>

      <button onClick={logout} className={styles.logoutButton}>
        Sair da Conta
      </button>
    </div>
  );
}