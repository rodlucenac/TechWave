import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../services/api';
import styles from './EnderecoForm.module.css';

export default function EnderecoForm() {
  const { id } = useParams(); // 'novo' ou id existente
  const isEdit = id !== 'novo';
  const [form, setForm] = useState({
    rua: '', numero: '', bairro: '',
    cidade: '', estado: '', cep: ''
  });
  const [erro, setErro] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (isEdit) {
      async function loadEndereco() {
        try {
          const resp = await api.get(`/api/enderecos/${id}`);
          setForm({ ...resp.data });
        } catch (err) {
          console.error('Erro ao carregar:', err);
        }
      }
      loadEndereco();
    }
  }, [id, isEdit]);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setErro('');
    // validações básicas
    if (!form.rua || !form.numero || !form.bairro || !form.cidade || !form.estado || !form.cep) {
      setErro('Preencha todos os campos.');
      return;
    }
    try {
      if (isEdit) {
        await api.put(`/enderecos/${id}`, form);
      } else {
        await api.post('/enderecos', form);
      }
      navigate('/enderecos');
    } catch (err) {
      console.error('Erro ao salvar:', err);
      setErro('Falha ao salvar. Tente novamente.');
    }
  };

  return (
    <div className={styles.container}>
      <h1>{isEdit ? 'Editar Endereço' : 'Novo Endereço'}</h1>
      {erro && <div className={styles.error}>{erro}</div>}
      <form onSubmit={handleSubmit} className={styles.form}>
        {['rua','numero','bairro','cidade','estado','cep'].map(field => (
          <div key={field} className={styles.field}>
            <label>{field.charAt(0).toUpperCase() + field.slice(1)}</label>
            <input
              name={field}
              value={form[field]}
              onChange={handleChange}
              maxLength={field === 'estado' ? 2 : undefined}
            />
          </div>
        ))}
        <div className={styles.buttons}>
          <button type="button" onClick={() => navigate(-1)} className={styles.backBtn}>
            Voltar
          </button>
          <button type="submit" className={styles.saveBtn}>
            Salvar
          </button>
        </div>
      </form>
    </div>
  );
}
