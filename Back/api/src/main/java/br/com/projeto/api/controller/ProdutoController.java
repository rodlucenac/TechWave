package br.com.projeto.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

  private final ProdutoService produtoService;

  public ProdutoController(ProdutoService produtoService) {
    this.produtoService = produtoService;
  }

  @GetMapping
  public List<Produto> listarProdutos() {
    return produtoService.listarProdutos();
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> criarProduto(
    @RequestParam("nome") String nome,
    @RequestParam("descricao") String descricao,
    @RequestParam("estoque") Integer estoque,
    @RequestParam("preco") BigDecimal preco,
    @RequestPart(name = "imagemArquivo", required = false) MultipartFile imagemArquivo
  ) {
    produtoService.criarProduto(nome, descricao, estoque, preco, imagemArquivo);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> atualizarProduto(
    @PathVariable int id,
    @RequestParam("nome") String nome,
    @RequestParam("descricao") String descricao,
    @RequestParam("estoque") Integer estoque,
    @RequestParam("preco") BigDecimal preco,
    @RequestPart(name = "imagemArquivo", required = false) MultipartFile imagemArquivo
  ) {
    // chama o service com os parâmetros corretos
    produtoService.atualizarProduto(id, nome, descricao, estoque, preco, imagemArquivo);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removerProduto(@PathVariable int id) {
    produtoService.removerProduto(id);
    return ResponseEntity.noContent().build();
  }
}
