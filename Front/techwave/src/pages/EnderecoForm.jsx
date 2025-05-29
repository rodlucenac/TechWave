// src/pages/EnderecoForm.jsx
import React, { useState, useEffect, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../services/api';
import styles from './EnderecoForm.module.css';
import { AuthContext } from '../contexts/AuthContext';

export default function EnderecoForm() {
  const { id } = useParams();
  const isEdit = Boolean(id) && id !== 'novo';
  const { user } = useContext(AuthContext);
  const [form, setForm] = useState({ rua: '', numero: '', bairro: '', cidade: '', estado: '', cep: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (isEdit) {
      api.get(`/api/enderecos/${id}`)
         .then(res => setForm(res.data))
         .catch(() => setError('Erro ao carregar endereço.'));
    }
  }, [id, isEdit]);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setError('');
    const { rua, numero, bairro, cidade, estado, cep } = form;
    if (!rua || !numero || !bairro || !cidade || !estado || !cep) {
      setError('Preencha todos os campos.');
      return;
    }
    console.log(user);
    // Monta payload incluindo idUsuario para vincular corretamente
    const payload = { ...form, idUsuario: user.detalhes.id };

    try {
      if (isEdit) {
        await api.put(`/api/enderecos/${id}`, payload);
      } else {
        await api.post('/api/enderecos', payload);
      }
      navigate('/enderecos');
    } catch (err) {
      console.error(err);
      setError('Falha ao salvar.');
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>{isEdit ? 'Editar Endereço' : 'Novo Endereço'}</h1>
      {error && <p className={styles.error}>{error}</p>}
      <form onSubmit={handleSubmit} className={styles.form}>
        {['rua','numero','bairro','cidade','estado','cep'].map(field => (
          <div key={field} className={styles.field}>
            <label className={styles.label} htmlFor={field}>{field.charAt(0).toUpperCase()+field.slice(1)}</label>
            <input
              id={field}
              name={field}
              className={styles.input}
              value={form[field]}
              onChange={handleChange}
              maxLength={field==='estado'?2:undefined}
              required
            />
          </div>
        ))}
        <div className={styles.buttons}>
          <button type="button" onClick={() => navigate(-1)} className={styles.cancel}>Cancelar</button>
          <button type="submit" className={styles.submit}>Salvar</button>
        </div>
      </form>
    </div>
  );
}
