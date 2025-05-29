// src/services/paymentService.js
import api from './api';

/**
 * Envia o pedido de pagamento para o backend.
 * @param {number} pedidoId  ID do pedido gerado no checkout
 * @param {string} tipoPagamento  Ex.: 'pix', 'boleto', 'cartao', etc.
 * @returns {Promise} Promise que resolve quando o POST retornar 200
 */
export function payOrder(pedidoId, tipoPagamento) {
  return api.post('/api/pagamentos', {
    pedidoId,
    tipoPagamento
  });
}