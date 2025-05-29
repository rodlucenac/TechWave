// src/pages/Cart.jsx
import React, { useEffect, useState } from 'react';
import {
  getCarrinho,
  updateItem,
  removeItem
} from '../services/cartService';
import Header from '../components/Header';
import styles from './Cart.module.css';
import { useCart } from '../contexts/CartContext';
import { Link, useNavigate } from 'react-router-dom';

export default function Cart() {
  const [cart, setCart] = useState(null);
  const { ensureCart } = useCart();
  const navigate = useNavigate();

  const refresh = () =>
    ensureCart().then(async id => {
      const data = await getCarrinho(id);
      setCart(data);
    });

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []); // keep once

  if (!cart) return <p className={styles.loading}>Carregando carrinho…</p>;

  const change = async (pid, delta) => {
    const id = await ensureCart();
    const nova = cart.itens.find(i => i.produtoId === pid).quantidade + delta;
    updateItem(id, pid, nova).then(refresh);
  };

  const rm = async pid => {
    const id = await ensureCart();
    removeItem(id, pid).then(refresh);
  };

  const pay = () => {
    navigate('/checkout');   // apenas vai para a tela de confirmação
  };

  return (
    <>
      <Header />
      <div className={styles.backWrapper}>
        <Link to="/" className={styles.backBtn}>
          <span aria-hidden="true">←</span>
          <span>Voltar</span>
        </Link>
      </div>
      <main className={styles.container}>
        <h2 className={styles.title}>Carrinho #{cart.idCarrinho}</h2>

        {cart.itens.length === 0 && <p>Seu carrinho está vazio.</p>}

        <ul className={styles.list}>
          {cart.itens.map(i => (
            <li key={i.produtoId} className={styles.item}>
              <div className={styles.itemLeft}>
                <h3 className={styles.itemName}>{i.nome}</h3>
                {i.descricao && <p className={styles.itemDesc}>{i.descricao}</p>}
              </div>
              <div className={styles.itemCenter}>
                <div className={styles.qty}>
                  <button onClick={() => change(i.produtoId, -1)}>-</button>
                  <span>{i.quantidade}</span>
                  <button onClick={() => change(i.produtoId, +1)}>+</button>
                </div>
              </div>
              <div className={styles.itemRight}>
                <span className={styles.itemPrice}>R$ {i.precoUnitario.toFixed(2)}</span>
                <span className={styles.itemSubtotal}>
                  Subtotal: R$ {(i.precoUnitario * i.quantidade).toFixed(2)}
                </span>
                <button className={styles.removeBtn} onClick={() => rm(i.produtoId)}>
                  Remover
                </button>
              </div>
            </li>
          ))}
        </ul>

        <p className={styles.totalText}>
          <strong>Total: R${" "}
            {cart.itens
              .reduce(
                (s, i) => s + i.precoUnitario * i.quantidade,
                0
              )
              .toFixed(2)}
          </strong>
        </p>

        <button
          className={styles.btnCheckout}
          onClick={pay}
          disabled={cart.statusCarrinho === 'fechado'}
        >
          Finalizar Compra
        </button>
      </main>
    </>
  );
}