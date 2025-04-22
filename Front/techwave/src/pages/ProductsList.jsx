import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import Header from '../components/Header';
import styles from './ProductsList.module.css';

export default function ProductsList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading]   = useState(true);
  const [error, setError]       = useState(null);

  useEffect(() => {
    api.get('/produtos')
       .then(res => {
         setProducts(res.data);
         setLoading(false);
       })
       .catch(err => {
         setError(err);
         setLoading(false);
       });
  }, []);

  const handleDelete = (id) => {
    if (window.confirm('Confirma exclusão do produto?')) {
      api.delete(`/produtos/${id}`)
         .then(() => setProducts(products.filter(p => p.id_produto !== id)))
         .catch(err => alert('Erro ao excluir: ' + err.message));
    }
  };

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
            <div key={prod.id_produto} className={styles.card}>
              <h2 className={styles.cardTitle}>{prod.nome}</h2>
              <p className={styles.cardDesc}>{prod.descricao}</p>
              <p className={styles.cardInfo}>Estoque: {prod.estoque}</p>
              <p className={styles.cardInfo}>Preço: R$ {prod.preco.toFixed(2)}</p>
              <div className={styles.actions}>
                <Link
                  to={`/produtos/${prod.id_produto}/editar`}
                  className={styles.btnEdit}
                >
                  Editar
                </Link>
                <button
                  onClick={() => handleDelete(prod.id_produto)}
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
