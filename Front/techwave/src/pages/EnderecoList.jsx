// src/pages/EnderecoList.jsx
import React, { useState, useEffect, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import styles from './EnderecoList.module.css';
import { AuthContext } from '../contexts/AuthContext';

export default function EnderecoList() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [enderecos, setEnderecos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // Redireciona para login se não autenticado
  useEffect(() => {
    if (!user) {
      navigate('/login');
    }
  }, [user, navigate]);

  // Busca endereços do usuário autenticado
  useEffect(() => {
    async function fetchEnderecos() {
      if (!user) return;
      setLoading(true);
      try {
        const userId = user.detalhes?.idUsuario ?? user.detalhes?.id;
        if (!userId) {
          setError('ID do usuário indisponível.');
          return;
        }
        // Inclui /api no path para bater no endpoint correto
        const resp = await api.get(`/api/enderecos/usuario/${userId}`);
        setEnderecos(resp.data);
      } catch (err) {
        console.error('Erro ao buscar endereços do usuário:', err);
        setError('Erro ao carregar endereços.');
      } finally {
        setLoading(false);
      }
    }
    fetchEnderecos();
  }, [user]);

  const handleDelete = async id => {
    if (!window.confirm('Confirma exclusão deste endereço?')) return;
    try {
      // Inclui /api para DELETE
      await api.delete(`/api/enderecos/${id}`);
      setEnderecos(prev => prev.filter(e => e.idEndereco !== id));
    } catch (err) {
      console.error('Erro ao deletar:', err);
      setError('Erro ao excluir endereço.');
    }
  };

  if (loading) {
    return <p className={styles.loading}>Carregando endereços...</p>;
  }

  if (error) {
    return <p className={styles.error}>{error}</p>;
  }

  if (enderecos.length === 0) {
    return <p className={styles.loading}>Nenhum endereço cadastrado.</p>;
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <span className={styles.title}>Meus Endereços</span>
        <button
          onClick={() => navigate('/enderecos/novo')}
          className={styles.btnNew}
        >
          Novo Endereço
        </button>
      </div>
      <ul className={styles.list}>
        {enderecos.map(end => (
          <li key={end.idEndereco} className={styles.item}>
            <div className={styles.info}>
              <strong>{end.rua}, {end.numero}</strong>
              <p>{end.bairro} - {end.cidade}/{end.estado} - CEP: {end.cep}</p>
            </div>
            <div className={styles.actions}>
              <Link
                to={`/enderecos/${end.idEndereco}/editar`}
                className={styles.btnEdit}
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(end.idEndereco)}
                className={styles.btnDelete}
              >
                Excluir
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
