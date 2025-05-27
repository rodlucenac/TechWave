// src/pages/ProductsList.jsx
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';
import { addItem } from '../services/cartService';
import api from '../services/api';
import Header from '../components/Header';
import styles from './ProductsList.module.css';

export default function ProductsList() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const { ensureCart } = useCart();

  const [products, setProducts] = useState([]);
  const [loading, setLoading]   = useState(true);
  const [error,   setError]     = useState(null);

  /* 1. Redireciona quem não for admin (mantido do seu código) */
  useEffect(() => {
    if (!user || user.tipo !== 'admin') {
      navigate('/login', { replace: true });
    }
  }, [user, navigate]);

  /* 2. Carrega lista de produtos */
  useEffect(() => {
    api.get('/produtos')
      .then(res  => setProducts(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  /* 3. Excluir produto (admin) */
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

  /* 4. Adiciona ao carrinho (cliente ou admin testando) */
const handleAddToCart = idProduto => {
  ensureCart()
    .then(cartId => {
      if (!cartId) {
        alert('Você precisa estar logado para adicionar ao carrinho.');
        return;
      }
      return addItem(cartId, idProduto, 1);
    })
    .then(() => alert('Produto adicionado ao carrinho!'))
    .catch(err => {
      if (err) alert('Erro ao adicionar: ' + err.message);
    });
};

  /* 5. Estados de carregamento/erro */
  if (loading) return <p className={styles.loading}>Carregando produtos...</p>;
  if (error)   return <p className={styles.error}>Erro: {error.message}</p>;

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
                <button
                  onClick={() => handleAddToCart(prod.idProduto)}
                  className={styles.btnAddCart}
                >
                  Adicionar ao Carrinho
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}