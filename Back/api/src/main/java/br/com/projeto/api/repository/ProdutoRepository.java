package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Produto;

@Repository
public class ProdutoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // Lista todos os produtos
  public List<Produto> findAll() {
    String sql = "SELECT * FROM Produto";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produto.class));
  }

  // Insere um novo produto usando a procedure sp_inserir_produto
  public void inserirProduto(Produto produto) {
    String sql = "CALL sp_inserir_produto(?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
          produto.getNome(),
          produto.getDescricao(),
          produto.getEstoque(),
          produto.getPreco(),
          produto.getImagem());
  }

  // Atualiza um produto usando a procedure sp_atualizar_produto
  public void atualizarProduto(int id, Produto produto) {
    String sql = "CALL sp_atualizar_produto(?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
          id,
          produto.getNome(),
          produto.getDescricao(),
          produto.getEstoque(),
          produto.getPreco(),
          produto.getImagem());
  }

  // Deleta um produto usando a procedure sp_deletar_produto
  public void deletarProduto(int id) {
    String sql = "CALL sp_deletar_produto(?)";
    jdbcTemplate.update(sql, id);
  }
}
