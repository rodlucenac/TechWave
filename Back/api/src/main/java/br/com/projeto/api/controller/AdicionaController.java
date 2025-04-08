package br.com.projeto.api.controller;

import br.com.projeto.api.service.AdicionaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinho/item")
public class AdicionaController {

  @Autowired
  private AdicionaService adicionaService;

  @PostMapping
  public String adicionarItem(@RequestParam int pedidoId, @RequestParam int produtoId, @RequestParam int quantidade) {
    adicionaService.adicionarItem(pedidoId, produtoId, quantidade);
    return "Item adicionado com sucesso!";
  }

  @DeleteMapping
  public String removerItem(@RequestParam int pedidoId, @RequestParam int produtoId) {
    adicionaService.removerItem(pedidoId, produtoId);
    return "Item removido com sucesso!";
  }
}
