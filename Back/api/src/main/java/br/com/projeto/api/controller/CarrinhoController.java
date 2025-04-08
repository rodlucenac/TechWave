package br.com.projeto.api.controller;

import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
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
