import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api    from '../services/api';
import Header from '../components/Header';
import styles from './CategoryForm.module.css';

export default function CategoryForm() {
  const { id }     = useParams();
  const isEdit     = Boolean(id);
  const navigate   = useNavigate();

  const [form, setForm]     = useState({ nome: '', descricao: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError]     = useState(null);

  useEffect(() => {
    if (isEdit) {
      setLoading(true);
      api.get(`/categorias/${id}`)
         .then(res => setForm({
           nome: res.data.nome,
           descricao: res.data.descricao
         }))
         .catch(err => setError(err))
         .finally(() => setLoading(false));
    }
  }, [id, isEdit]);

  const handleChange = e => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = e => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const request = isEdit
      ? api.put(`/categorias/${id}`, form)
      : api.post('/categorias', form);

    request
      .then(() => navigate('/categorias'))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  };

  return (
    <>
      <Header />
      <div className={styles.container}>
        <h1 className={styles.title}>
          {isEdit ? 'Editar Categoria' : 'Nova Categoria'}
        </h1>

        {loading && <p className={styles.loading}>Carregando...</p>}
        {error   && <p className={styles.error}>Erro: {error.message}</p>}

        {!loading && (
          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.field}>
              <label htmlFor="nome" className={styles.label}>
                Nome
              </label>
              <input
                id="nome"
                name="nome"
                value={form.nome}
                onChange={handleChange}
                required
                className={styles.input}
              />
            </div>

            <div className={styles.field}>
              <label htmlFor="descricao" className={styles.label}>
                Descrição
              </label>
              <textarea
                id="descricao"
                name="descricao"
                rows={3}
                value={form.descricao}
                onChange={handleChange}
                className={styles.textarea}
              />
            </div>

            <div className={styles.buttons}>
              <button
                type="button"
                onClick={() => navigate('/categorias')}
                className={styles.cancel}
              >
                Cancelar
              </button>
              <button type="submit" className={styles.submit}>
                {isEdit ? 'Atualizar' : 'Criar'}
              </button>
            </div>
          </form>
        )}
      </div>
    </>
  );
}
