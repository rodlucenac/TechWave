package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.service.FavoritoService;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

  @Autowired
  private FavoritoService favoritoService;

  @PostMapping("/adicionar")
  public ResponseEntity<?> favoritar(@RequestParam int produtoId, @RequestParam int clienteId) {
    favoritoService.favoritar(produtoId, clienteId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remover")
  public ResponseEntity<?> desfavoritar(@RequestParam int produtoId, @RequestParam int clienteId) {
    favoritoService.desfavoritar(produtoId, clienteId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/verifica")
  public boolean isFavorito(@RequestParam int produtoId, @RequestParam int clienteId) {
    return favoritoService.isFavorito(produtoId, clienteId);
  }

  @GetMapping("/listar")
  public ResponseEntity<List<Produto>> listarFavoritos(@RequestParam int clienteId) {
    List<Produto> favoritos = favoritoService.listarProdutosFavoritos(clienteId);
    return ResponseEntity.ok(favoritos);
  }

  public static class FavoritoDTO {
    public int produtoId;
    public int clienteId;
    public java.util.Date dataAdicao;

    public FavoritoDTO(int produtoId, int clienteId, java.util.Date dataAdicao) {
      this.produtoId = produtoId;
      this.clienteId = clienteId;
      this.dataAdicao = dataAdicao;
    }
  }
}
