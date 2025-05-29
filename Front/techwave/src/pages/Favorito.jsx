import React, { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import styles from './Favorito.module.css';
import { AuthContext } from '../contexts/AuthContext';

export default function Favorito() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState('');

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }
    async function fetchFavoritos() {
      try {
        const clienteId = user.detalhes.id ?? user.detalhes.id;
        const resp = await api.get('/api/favoritos/listar', {
          params: { clienteId }
        });
        setProdutos(resp.data);
      } catch (err) {
        setErro('Erro ao carregar favoritos.');
      } finally {
        setLoading(false);
      }
    }
    fetchFavoritos();
  }, [user, navigate]);

  const handleDelete = async (produtoId) => {
    if (!window.confirm('Remover dos favoritos?')) return;
    try {
      const clienteId = user.detalhes.idUsuario ?? user.detalhes.id;
      await api.delete('/api/favoritos/remover', {
        params: { produtoId, clienteId }
      });
      setProdutos(prods => prods.filter(p => p.idProduto !== produtoId));
    } catch {
      setErro('Erro ao remover dos favoritos.');
    }
  };

  if (loading) return <div className={styles.loading}>Carregando favoritos...</div>;
  if (erro) return <div className={styles.error}>{erro}</div>;
  if (!produtos.length) return <div className={styles.loading}>Nenhum produto favorito.</div>;

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <button
          className={styles.btnBack}
          onClick={() => navigate('/')}
        >
          ← Voltar
        </button>
        <span className={styles.title}>Meus Favoritos</span>
      </div>
      <ul className={styles.list}>
        {produtos.map(produto => (
          <li key={produto.idProduto} className={styles.item}>
            <div className={styles.info}>
              <strong>{produto.nome}</strong>
              <p>
                R$ {Number(produto.preco).toFixed(2)}
                {produto.descricao && <><br /><span>{produto.descricao}</span></>}
              </p>
            </div>
            <div className={styles.actions}>
              <button
                className={styles.btnDelete}
                onClick={() => handleDelete(produto.idProduto)}
              >
                Remover
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
