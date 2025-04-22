package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

  @Autowired
  private CarrinhoService carrinhoService;

  @GetMapping
  public List<Carrinho> listarCarrinhos() {
    return carrinhoService.listarCarrinhos();
  }

  @PostMapping
  public String criarCarrinho(@RequestParam(defaultValue = "aberto") String status) {
    int carrinhoId = carrinhoService.criarCarrinho(status);
    return "Carrinho criado com sucesso, ID: " + carrinhoId;
  }
}
