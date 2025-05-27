// src/services/cartService.js
import api from './api';   // garante que src/services/api.js exista

/* ---------- Carrinho ---------- */

export const criarCarrinho = () =>
  api.post('/api/cart').then(res => res.data);

export const getCarrinho = (cartId) =>
  api.get(`/api/cart/${cartId}`).then(res => res.data);

export const addItem = (cartId, productId, quantity = 1) =>
  api.post(`/api/cart/${cartId}/items`, null, {
    params: { productId, quantity }
  }).then(res => res.data);

export const updateItem = (cartId, productId, quantity) =>
  api.put(`/api/cart/${cartId}/items/${productId}`, null, {
    params: { quantity }
  }).then(res => res.data);

export const removeItem = (cartId, productId) =>
  api.delete(`/api/cart/${cartId}/items/${productId}`)
     .then(res => res.data);

export const checkout = (cartId) =>
  api.post(`/api/cart/${cartId}/checkout`).then(res => res.data);