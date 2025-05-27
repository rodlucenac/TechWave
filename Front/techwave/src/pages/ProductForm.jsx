// src/pages/ProductForm.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import api from '../services/api';
import Header from '../components/Header';
import styles from './ProductForm.module.css';

export default function ProductForm() {
  const { user } = useAuth();
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();

  // redirect non-admins
  useEffect(() => {
    if (!user || user.tipo !== 'admin') {
      navigate('/login', { replace: true });
    }
  }, [user, navigate]);

  const [form, setForm] = useState({
    nome: '',
    descricao: '',
    estoque: 0,
    preco: ''
  });
  const [file, setFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // load existing product for edit
  useEffect(() => {
    if (!isEdit) return;
    setLoading(true);
    api.get(`/produtos/${id}`)
       .then(res => {
         const data = res.data;
         setForm({
           nome: data.nome,
           descricao: data.descricao,
           estoque: data.estoque,
           preco: data.preco
         });
         if (data.imagem) {
           setPreviewUrl(data.imagem);
         }
       })
       .catch(err => setError(err))
       .finally(() => setLoading(false));
  }, [id, isEdit]);

  // handle text inputs
  const handleChange = e => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  // handle file input + preview
  const handleFileChange = e => {
    const chosen = e.target.files[0];
    if (chosen) {
      setFile(chosen);
      setPreviewUrl(URL.createObjectURL(chosen));
    }
  };

  // submit via multipart/form-data
  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const formData = new FormData();
      formData.append('nome', form.nome);
      formData.append('descricao', form.descricao);
      formData.append('estoque', form.estoque);
      formData.append('preco', form.preco);
      if (file) {
        formData.append('imagemArquivo', file);
      }

      if (isEdit) {
        await api.put(`/produtos/${id}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
      } else {
        await api.post('/produtos', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
      }

      navigate('/produtos');
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Header />
      <div className={styles.container}>
        <h1 className={styles.title}>
          {isEdit ? 'Editar Produto' : 'Novo Produto'}
        </h1>

        {loading && <p className={styles.loading}>Carregando...</p>}
        {error && <p className={styles.error}>Erro: {error.message}</p>}

        {!loading && (
          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.field}>
              <label htmlFor="nome" className={styles.label}>
                Nome do Produto
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
                value={form.descricao}
                onChange={handleChange}
                rows={4}
                required
                className={styles.textarea}
              />
            </div>

            <div className={styles.row}>
              <div className={`${styles.field} ${styles.flex1}`}>
                <label htmlFor="estoque" className={styles.label}>
                  Estoque
                </label>
                <input
                  id="estoque"
                  type="number"
                  name="estoque"
                  value={form.estoque}
                  onChange={handleChange}
                  required
                  className={styles.input}
                />
              </div>
              <div className={`${styles.field} ${styles.flex1}`}>
                <label htmlFor="preco" className={styles.label}>
                  Preço (R$)
                </label>
                <input
                  id="preco"
                  type="number"
                  step="0.01"
                  name="preco"
                  value={form.preco}
                  onChange={handleChange}
                  required
                  className={styles.input}
                />
              </div>
            </div>

            <div className={styles.field}>
              <label htmlFor="imagemArquivo" className={styles.label}>
                Imagem do Produto
              </label>
              <input
                id="imagemArquivo"
                type="file"
                accept="image/*"
                onChange={handleFileChange}
                className={styles.input}
              />
            </div>

            {previewUrl && (
              <div className={styles.preview}>
                <p>Preview:</p>
                <img
                  src={previewUrl}
                  alt="Preview"
                  className={styles.previewImage}
                />
              </div>
            )}

            <div className={styles.buttons}>
              <button
                type="button"
                onClick={() => navigate('/produtos')}
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
