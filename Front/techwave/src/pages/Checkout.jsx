// src/pages/Checkout.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../contexts/CartContext';
import { useAuth } from '../contexts/AuthContext';
import api from '../services/api';
import Header from '../components/Header';
import styles from './Checkout.module.css';

export default function Checkout() {
  const { ensureCart } = useCart();
  const { user }       = useAuth();
  const navigate       = useNavigate();

  const [cart, setCart]       = useState(null);
  const [enderecos, setEnd]   = useState([]);
  const [selEnd, setSelEnd]   = useState('');
  const [loading, setLoading] = useState(true);

  // carrega carrinho + endereços do cliente
  useEffect(() => {
    const fetchAll = async () => {
      const cartId = await ensureCart();
      const idCli  = user.detalhes.idUsuario;
      const [c, e] = await Promise.all([
        api.get(`/api/cart/${cartId}`).then(r => r.data),
        api.get(`/api/clientes/${idCli}/endereco`).then(r => [r.data])
      ]);
      setCart(c);
      setEnd(e);
      if (e.length) setSelEnd(e[0].idEndereco ?? e[0].id_endereco);
      setLoading(false);
    };
    fetchAll().catch(console.error);
  }, [ensureCart, user]);

  const total = cart?.itens.reduce((s,i) => s + i.precoUnitario * i.quantidade, 0) || 0;

  const finalizar = async () => {
    if (!selEnd) {
      return alert('Selecione um endereço');
    }
    // redireciona para a tela de pagamento
    navigate('/pagamento');
  };

  if (loading) return <p className={styles.loading}>Carregando…</p>;


  return (
    <>
      <Header />
      <main className={styles.container}>
        <div className={styles.left}>
          <h1>Produtos e Serviço</h1>
          <p className={styles.vendor}>
            Vendido e entregue por: <strong>Magalu</strong>
          </p>
          <ul className={styles.list}>
            {cart.itens.map(i => (
              <li key={i.produtoId} className={styles.item}>
                <img src={i.imagemUrl} alt={i.nome} className={styles.itemImage} />
                <div className={styles.itemInfo}>
                  <strong>{i.nome}</strong>
                  <p>{i.descricao}</p>
                  <p className={styles.itemQuantity}>Quantidade: {i.quantidade}</p>
                </div>
                <div className={styles.itemPrice}>
                  R$ {i.precoUnitario.toFixed(2)}
                </div>
              </li>
            ))}
          </ul>

          <div className={styles.coupon}>
            <input type="text" placeholder="Cupom de desconto" />
            <button>APLICAR CUPOM</button>
          </div>
        </div>

        <aside className={styles.sidebar}>
          <div className={styles.summary}>
            <h2>Resumo</h2>
            <div className={styles.row}>
              <span>Valor dos Produtos:</span>
              <span>R$ {total.toFixed(2)}</span>
            </div>
            <div className={styles.row}>
              <span>Frete:</span>
              <span>R$ 0,00</span>
            </div>
            <div className={styles.row}>
              <strong>Total a prazo:</strong>
              <strong>R$ {total.toFixed(2)}</strong>
            </div>
            <div className={styles.pix}>
              <span>Valor à vista no PIX:</span>
              <strong>R$ { (total * 0.9).toFixed(2) }</strong>
            </div>
          </div>

          <div className={styles.delivery}>
            <h2>Entrega</h2>
            <label>Selecione seu endereço:</label>
            <select
              value={selEnd}
              onChange={e => setSelEnd(e.target.value)}
            >
              {enderecos.map(end => (
                <option
                  key={end.id_endereco ?? end.idEndereco}
                  value={end.id_endereco ?? end.idEndereco}
                >
                  {end.rua}, {end.numero} – CEP: {end.cep}
                </option>
              ))}
            </select>
          
            <p className={styles.vendorFooter}>
              Vendido e entregue por: <strong>TechWave</strong>
            </p>
            <div className={styles.shippingMethods}>
              <h3>Forma de Envio</h3>
              <label>
                <input type="radio" name="ship" defaultChecked /> Correios Convencional – Grátis
              </label>
            </div>
          </div>

          <button className={styles.btnCheckout} onClick={finalizar}>
            ir para pagamento
          </button>
        </aside>
      </main>
    </>
  );
}