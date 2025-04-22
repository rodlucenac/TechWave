package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Favorito;
import br.com.projeto.api.service.FavoritoService;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

  @Autowired
  private FavoritoService favoritoService;

  @GetMapping("/{clienteId}")
  public List<Favorito> listarFavoritos(@PathVariable int clienteId) {
    return favoritoService.listarFavoritosPorCliente(clienteId);
  }

  @PostMapping
  public String adicionarFavorito(@RequestBody Favorito favorito) {
    favoritoService.adicionarFavorito(favorito);
    return "Favorito adicionado com sucesso!";
  }

  @DeleteMapping("/{clienteId}/{produtoId}")
  public String removerFavorito(@PathVariable int clienteId, @PathVariable int produtoId) {
    favoritoService.removerFavorito(clienteId, produtoId);
    return "Favorito removido com sucesso!";
  }
}
