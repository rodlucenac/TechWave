package br.com.projeto.api.controller;

import br.com.projeto.api.model.Pedido;
import br.com.projeto.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  // Endpoint para listar todos os pedidos
  @GetMapping
  public List<Pedido> listarPedidos() {
    return pedidoService.listarPedidos();
  }

  // Endpoint para criar um novo pedido
  @PostMapping
  public String criarPedido(@RequestBody Pedido pedido) {
    pedidoService.criarPedido(pedido);
    return "Pedido criado com sucesso!";
  }

  // Endpoint para atualizar um pedido
  @PutMapping("/{id}")
  public String atualizarPedido(@PathVariable int id, @RequestBody Pedido pedido) {
    pedidoService.atualizarPedido(id, pedido);
    return "Pedido atualizado com sucesso!";
  }

  // Endpoint para remover um pedido
  @DeleteMapping("/{id}")
  public String removerPedido(@PathVariable int id) {
    pedidoService.removerPedido(id);
    return "Pedido removido com sucesso!";
  }
}
