package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Pedido;
import br.com.projeto.api.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
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
