package br.com.projeto.api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

  private final ProdutoRepository repo;
  public ProdutoService(ProdutoRepository repo) {
    this.repo = repo;
  }

  public List<Produto> listarProdutos() {
    return repo.findAll();
  }

  @Transactional
  public void criarProduto(
      String nome,
      String descricao,
      Integer estoque,
      BigDecimal preco,
      MultipartFile img
  ) {
    Produto p = new Produto();
    p.setNome(nome);
    p.setDescricao(descricao);
    p.setEstoque(estoque);
    p.setPreco(preco);
    repo.inserirProduto(p, img);
  }

  @Transactional
  public void atualizarProduto(
      int id,
      String nome,
      String descricao,
      Integer estoque,
      BigDecimal preco,
      MultipartFile img
  ) {
    Produto p = new Produto();
    p.setNome(nome);
    p.setDescricao(descricao);
    p.setEstoque(estoque);
    p.setPreco(preco);
    repo.atualizarProduto(id, p, img);
  }

  @Transactional
  public void removerProduto(int id) {
    repo.deletarProduto(id);
  }
}
