package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

  // Lista todos os produtos
  public List<Produto> listarProdutos() {
    return produtoRepository.findAll();
  }

  // Cria um novo produto com validações básicas
  @Transactional
  public void criarProduto(Produto produto) {
    if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
    }
    // Outras validações podem ser adicionadas aqui.
    produtoRepository.inserirProduto(produto);
  }

  // Atualiza um produto existente
  @Transactional
  public void atualizarProduto(int id, Produto produto) {
    // Aqui você pode adicionar validações ou verificações extras
    produtoRepository.atualizarProduto(id, produto);
  }

  // Remove um produto
  @Transactional
  public void removerProduto(int id) {
    // Verifique, por exemplo, se o produto não está associado a pedidos ativos antes de remover.
    produtoRepository.deletarProduto(id);
    }
}
