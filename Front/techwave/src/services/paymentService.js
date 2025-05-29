// src/services/paymentService.js
import api from './api';

/**
 * Envia o pedido de pagamento para o backend.
 * @param {number} carrinhoId  ID do carrinho de compras
 * @param {number} valorTotal  Valor total a ser pago
 * @param {string} tipoPagamento  Tipo do pagamento: 'pix', 'boleto', 'cartao', etc.
 * @returns {Promise} Promise que resolve quando o POST retornar 200
 */
export function payOrder(carrinhoId, valorTotal, tipoPagamento) {
  return api.post('/api/pagamentos', {
    carrinhoId,
    valorTotal,
    tipoPagamento
  });
}