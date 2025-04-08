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

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

  @Autowired
  private ProdutoService produtoService;

  // Endpoint para listar produtos
  @GetMapping
  public List<Produto> listarProdutos() {
    return produtoService.listarProdutos();
  }

  // Endpoint para criar um novo produto
  @PostMapping
  public String criarProduto(@RequestBody Produto produto) {
    produtoService.criarProduto(produto);
    return "Produto inserido com sucesso!";
  }

  // Endpoint para atualizar um produto
  @PutMapping("/{id}")
  public String atualizarProduto(@PathVariable int id, @RequestBody Produto produto) {
    produtoService.atualizarProduto(id, produto);
    return "Produto atualizado com sucesso!";
  }

  // Endpoint para deletar um produto
  @DeleteMapping("/{id}")
  public String removerProduto(@PathVariable int id) {
    produtoService.removerProduto(id);
    return "Produto removido com sucesso!";
  }
}
