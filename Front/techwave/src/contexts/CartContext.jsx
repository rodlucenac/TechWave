// src/contexts/CartContext.jsx
import { createContext, useContext, useState } from 'react';
import { criarCarrinho } from '../services/cartService';

const CartCtx = createContext({
  cartId: null,
  ensureCart: async () => null
});

export function CartProvider({ children }) {
  const [cartId, setCartId] = useState(null);
  const [pendingPromise, setPendingPromise] = useState(null);

  /** Garante que exista um carrinho e devolve o id (promise única) */
  const ensureCart = async () => {
    if (cartId) return cartId;               // já existe
    if (pendingPromise) return pendingPromise;

    // cria uma promessa única
    const promise = criarCarrinho()
      .then(c => {
        setCartId(c.idCarrinho);
        setPendingPromise(null);
        return c.idCarrinho;
      })
      .catch(err => {
        setPendingPromise(null);
        throw err;
      });

    setPendingPromise(promise);
    return promise;
  };

  return (
    <CartCtx.Provider value={{ cartId, ensureCart }}>
      {children}
    </CartCtx.Provider>
  );
}

export function useCart() {
  return useContext(CartCtx);
}