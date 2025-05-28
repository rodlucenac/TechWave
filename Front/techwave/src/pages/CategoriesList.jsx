import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import Header from '../components/Header';
import styles from './CategoriesList.module.css';

export default function CategoriesList() {
  const [cats, setCats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    api.get('/categorias')
      .then(res => setCats(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  const handleDelete = idCategoria => {
    if (!window.confirm('Confirma exclusão da categoria?')) return;
    api.delete(`/categorias/${idCategoria}`)
      .then(res => {
        if (res.status === 204) {
          setCats(curr => curr.filter(c => c.idCategoria !== idCategoria));
        } else {
          throw new Error('Retorno inesperado: ' + res.status);
        }
      })
      .catch(err => alert('Erro ao excluir: ' + err.message));
  };

  if (loading) return <p className={styles.loading}>Carregando categorias...</p>;
  if (error) return <p className={styles.error}>Erro: {error.message}</p>;

  return (
    <>
      <Header />
      <div className={styles.container}>
        <div className={styles.header}>
          <h1 className={styles.title}>Gestão de Categorias</h1>
          <Link to="/categorias/novo" className={styles.btnNew}>
            Nova Categoria
          </Link>
        </div>

        <ul className={styles.list}>
          {cats.map(cat => {
            const idCategoria = cat.idCategoria;
            return (
              <li key={idCategoria} className={styles.item}>
                <div className={styles.info}>
                  <strong>{cat.nome}</strong>
                  <p>{cat.descricao}</p>
                </div>
                <div className={styles.actions}>
                  <Link
                    to={`/categorias/${idCategoria}/editar`}
                    className={styles.btnEdit}
                  >
                    Editar
                  </Link>
                  <button
                    onClick={() => handleDelete(idCategoria)}
                    className={styles.btnDelete}
                  >
                    Excluir
                  </button>
                </div>
              </li>
            );
          })}
        </ul>
      </div>
    </>
  );
}
