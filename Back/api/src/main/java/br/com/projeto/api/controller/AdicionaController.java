package br.com.projeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.service.AdicionaService;

@RestController
@RequestMapping("/carrinho/item")
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
