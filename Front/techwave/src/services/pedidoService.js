import api from './api';

/**
 * Busca os pedidos do cliente logado
 * @returns {Promise} Promise que retorna os pedidos do cliente
 */
export function getPedidosDoCliente(clienteId) {
  return api.get(`/api/pedidos/clientes/${clienteId}`);
}

/**
 * Busca os detalhes de um pedido específico incluindo seus itens
 * @param {number} pedidoId ID do pedido
 * @returns {Promise} Promise que retorna os detalhes do pedido com itens
 */
export function getDetalhesPedido(pedidoId) {
  return api.get(`/api/pedidos/${pedidoId}/itens`);
}
