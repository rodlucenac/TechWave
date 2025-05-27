// src/pages/ProductsList.jsx
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import api from '../services/api';
import Header from '../components/Header';
import styles from './ProductsList.module.css';

export default function ProductsList() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // redireciona quem não for admin
  useEffect(() => {
    if (!user || user.tipo !== 'admin') {
      navigate('/login', { replace: true });
    }
  }, [user, navigate]);

  // carrega lista de produtos
  useEffect(() => {
    api.get('/produtos')
      .then(res => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError(err);
        setLoading(false);
      });
  }, []);

  // exclui produto via stored procedure no back
  const handleDelete = idProduto => {
    if (!window.confirm('Confirma exclusão do produto?')) return;
    api.delete(`/produtos/${idProduto}`)
      .then(res => {
        if (res.status === 204) {
          setProducts(curr => curr.filter(p => p.idProduto !== idProduto));
        } else {
          throw new Error('Retorno inesperado: ' + res.status);
        }
      })
      .catch(err => {
        console.error(err);
        alert('Erro ao excluir: ' + err.message);
      });
  };

  if (loading) return <p className={styles.loading}>Carregando produtos...</p>;
  if (error) return <p className={styles.error}>Erro: {error.message}</p>;

  return (
    <>
      <Header />

      <div className={styles.container}>
        <div className={styles.header}>
          <h1 className={styles.title}>Gestão de Produtos</h1>
          <Link to="/produtos/novo" className={styles.btnNew}>
            Novo Produto
          </Link>
        </div>

        <div className={styles.list}>
          {products.map(prod => (
            <div key={prod.idProduto} className={styles.card}>
              <h2 className={styles.cardTitle}>{prod.nome}</h2>
              <p className={styles.cardDesc}>{prod.descricao}</p>
              <p className={styles.cardInfo}>Estoque: {prod.estoque}</p>
              <p className={styles.cardInfo}>
                Preço: R$ {Number(prod.preco).toFixed(2)}
              </p>
              <div className={styles.actions}>
                <Link
                  to={`/produtos/${prod.idProduto}/editar`}
                  className={styles.btnEdit}
                >
                  Editar
                </Link>
                <button
                  onClick={() => handleDelete(prod.idProduto)}
                  className={styles.btnDelete}
                >
                  Excluir
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
