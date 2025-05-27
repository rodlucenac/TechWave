// src/pages/Cart.jsx
import React, { useEffect, useState } from 'react';
import {
  getCarrinho,
  updateItem,
  removeItem,
  checkout
} from '../services/cartService';
import Header from '../components/Header';
import styles from './Cart.module.css';
import { useCart } from '../contexts/CartContext';
import { Link } from 'react-router-dom';

export default function Cart() {
  const [cart, setCart] = useState(null);
  const { ensureCart } = useCart();

  const refresh = () =>
    ensureCart().then(id => getCarrinho(id).then(setCart));

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

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

  const pay = async () => {
    const id = await ensureCart();
    checkout(id).then(refresh);
  };

  return (
    <>
      <Header />
      <div className={styles.backWrapper}>
        <Link to="/" className={styles.backBtn}>← Voltar</Link>
      </div>
      <main className={styles.container}>
        <h2>Carrinho #{cart.idCarrinho}</h2>

        {cart.itens.length === 0 && <p>Seu carrinho está vazio.</p>}

        <ul className={styles.list}>
          {cart.itens.map(i => (
            <li key={i.produtoId} className={styles.item}>
              <span>Prod&nbsp;{i.produtoId}</span>
              <span>R$ {i.precoUnitario.toFixed(2)}</span>
              <div className={styles.qty}>
                <button onClick={() => change(i.produtoId, -1)}>-</button>
                {i.quantidade}
                <button onClick={() => change(i.produtoId, +1)}>+</button>
              </div>
              <span>
                Subtotal:&nbsp;
                R$ {(i.precoUnitario * i.quantidade).toFixed(2)}
              </span>
              <button onClick={() => rm(i.produtoId)}>x</button>
            </li>
          ))}
        </ul>

        <p className={styles.total}>
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