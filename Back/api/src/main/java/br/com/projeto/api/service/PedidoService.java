package br.com.projeto.api.service;

import br.com.projeto.api.model.Pedido;
import br.com.projeto.api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  // Lista todos os pedidos
  public List<Pedido> listarPedidos() {
    return pedidoRepository.findAll();
  }

  // Cria um novo pedido com validações básicas
  @Transactional
  public void criarPedido(Pedido pedido) {
    if (pedido.getDataPedido() == null) {
      throw new IllegalArgumentException("A data do pedido não pode ser nula.");
    }
    if (pedido.getStatusPedido() == null || pedido.getStatusPedido().trim().isEmpty()) {
      throw new IllegalArgumentException("O status do pedido não pode ser vazio.");
    }
    pedidoRepository.inserirPedido(pedido);
  }

  // Atualiza um pedido existente
  @Transactional
  public void atualizarPedido(int id, Pedido pedido) {
    // Possíveis validações podem ser adicionadas aqui
    pedidoRepository.atualizarPedido(id, pedido);
  }

  // Remove um pedido
  @Transactional
  public void removerPedido(int id) {
    // Pode incluir verificações para não remover pedidos que já foram processados, etc.
    pedidoRepository.deletarPedido(id);
  }
}
