import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import { useCart } from '../contexts/CartContext';
import api from '../services/api';
import styles from './Pagamento.module.css';

export default function Payment() {
  const navigate = useNavigate();
  const { cartId, ensureCart } = useCart();
  const [cart, setCart] = useState(null);
  const [method, setMethod] = useState('pix');
  const [loading, setLoading] = useState(false);
  const [cardInfo, setCardInfo] = useState({
    numero: '',
    nome: '',
    validade: '',
    cvv: ''
  });

  // Load cart items and prices
  useEffect(() => {
    async function load() {
      const id = await ensureCart();
      const res = await api.get(`/api/cart/${id}`);
      setCart(res.data);
    }
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (!cart) {
    return (
      <>
        <Header />
        <p className={styles.loading}>Carregando…</p>
      </>
    );
  }

  // calculate totals
  const subtotal = cart.itens.reduce((s, i) => s + i.precoUnitario * i.quantidade, 0);
  const discount = method === 'pix' ? subtotal * 0.1 : 0; 
  const frete = 0;
  const total = subtotal - discount + frete;

  const handleContinue = async () => {
    setLoading(true);
    try {
      await ensureCart();
      await api.post(`/api/cart/${cartId}/checkout?paymentMethod=${method}`);
      navigate('/confirmation');
    } catch (err) {
      console.error(err);
      alert('Erro ao processar pagamento');
    }
    setLoading(false);
  };

  return (
    <>
      <Header />
      <div className={styles.page}>
        <div className={styles.methods}>
          <h2>Forma de Pagamento</h2>
          <div className={styles.option}>
            <label>
              <input
                type="radio"
                name="payment"
                value="pix"
                checked={method === 'pix'}
                onChange={() => setMethod('pix')}
              />
              PIX
            </label>
            {method === 'pix' && (
              <p className={styles.desc}>
                Até 10% de desconto com aprovação imediata.
              </p>
            )}
          </div>
          <div className={styles.option}>
            <label>
              <input
                type="radio"
                name="payment"
                value="boleto"
                checked={method === 'boleto'}
                onChange={() => setMethod('boleto')}
              />
              Boleto Bancário
            </label>
          </div>
          <div className={styles.option}>
            <label>
              <input
                type="radio"
                name="payment"
                value="cartao"
                checked={method === 'cartao'}
                onChange={() => setMethod('cartao')}
              />
              Cartão de Crédito
            </label>
          </div>
          {method === 'cartao' && (
            <div className={styles.cardForm}>
              <div className={styles.cardPreview}>
                <div className="cardName">{cardInfo.nome || 'Nome Impresso'}</div>
                <div className="cardValid">Valido até {cardInfo.validade || 'MM/AA'}</div>
              </div>
              <div className={styles.fieldsGrid}>
                <div className={styles.field}>
                  <label>Número do Cartão*</label>
                  <input
                    type="text"
                    maxLength="19"
                    placeholder="0000 0000 0000 0000"
                    value={cardInfo.numero}
                    onChange={e => setCardInfo({...cardInfo, numero: e.target.value})}
                  />
                </div>
                <div className={styles.field}>
                  <label>Nome Impresso*</label>
                  <input
                    type="text"
                    placeholder="Como no cartão"
                    value={cardInfo.nome}
                    onChange={e => setCardInfo({...cardInfo, nome: e.target.value})}
                  />
                </div>
                <div className={styles.field}>
                  <label>Validade*</label>
                  <input
                    type="text"
                    maxLength="5"
                    placeholder="MM/AA"
                    value={cardInfo.validade}
                    onChange={e => setCardInfo({...cardInfo, validade: e.target.value})}
                  />
                </div>
                <div className={styles.field}>
                  <label>CVV*</label>
                  <input
                    type="password"
                    maxLength="4"
                    placeholder="123"
                    value={cardInfo.cvv}
                    onChange={e => setCardInfo({...cardInfo, cvv: e.target.value})}
                  />
                </div>
                <div className={styles.field}>
                  <label>Apelido*</label>
                  <input
                    type="text"
                    placeholder="Ex: Meu VISA"
                  />
                </div>
                <div className={styles.field}>
                  <label>CPF do Titular*</label>
                  <input
                    type="text"
                    placeholder="000.000.000-00"
                  />
                </div>
              </div>
            </div>
          )}
          <div className={styles.option}>
            <label>
              <input
                type="radio"
                name="payment"
                value="nupay"
                checked={method === 'nupay'}
                onChange={() => setMethod('nupay')}
              />
              Nupay
            </label>
          </div>
        </div>

        <aside className={styles.summary}>
          <h2>Resumo</h2>
          <div className={styles.row}>
            <span>Valor dos Produtos:</span>
            <span>R$ {subtotal.toFixed(2)}</span>
          </div>
          <div className={styles.row}>
            <span>Descontos:</span>
            <span className={styles.discount}>- R$ {discount.toFixed(2)}</span>
          </div>
          <div className={styles.row}>
            <span>Frete:</span>
            <span>R$ {frete.toFixed(2)}</span>
          </div>
          <div className={styles.totalBox}>
            <span>Total:</span>
            <span>R$ {total.toFixed(2)}</span>
          </div>
          <button
            className={styles.btnContinue}
            onClick={handleContinue}
            disabled={loading}
          >
            {loading ? 'Processando…' : 'Continuar'}
          </button>
          <button
            className={styles.btnBack}
            onClick={() => navigate(-1)}
          >
            Voltar
          </button>
        </aside>
      </div>
    </>
  );
}