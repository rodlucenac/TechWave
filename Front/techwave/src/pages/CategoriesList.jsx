import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api    from '../services/api';
import Header from '../components/Header';
import styles from './CategoriesList.module.css';

export default function CategoriesList() {
  const [cats, setCats]     = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState(null);

  useEffect(() => {
    api.get('/categorias')
       .then(res => {
         setCats(res.data);
         setLoading(false);
       })
       .catch(err => {
         setError(err);
         setLoading(false);
       });
  }, []);

  const handleDelete = id => {
    if (window.confirm('Confirma exclusão da categoria?')) {
      api.delete(`/categorias/${id}`)
         .then(() => setCats(cats.filter(c => c.id_categoria !== id)))
         .catch(err => alert('Erro ao excluir: ' + err.message));
    }
  };

  if (loading) return <p className={styles.loading}>Carregando categorias...</p>;
  if (error)   return <p className={styles.error}>Erro: {error.message}</p>;

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
          {cats.map(cat => (
            <li key={cat.id_categoria} className={styles.item}>
              <div className={styles.info}>
                <strong>{cat.nome}</strong>
                <p>{cat.descricao}</p>
              </div>
              <div className={styles.actions}>
                <Link
                  to={`/categorias/${cat.id_categoria}/editar`}
                  className={styles.btnEdit}
                >
                  Editar
                </Link>
                <button
                  onClick={() => handleDelete(cat.id_categoria)}
                  className={styles.btnDelete}
                >
                  Excluir
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}
