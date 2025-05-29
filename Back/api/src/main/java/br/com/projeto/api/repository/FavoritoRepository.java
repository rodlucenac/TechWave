package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Produto;

@Repository
public class FavoritoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void inserirFavorito(int produtoId, int clienteId) {
    String sql = "INSERT INTO Favorito (produto_id, cliente_id, data_adicao) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, produtoId, clienteId, new java.sql.Date(System.currentTimeMillis()));
  }

  public void removerFavorito(int produtoId, int clienteId) {
    String sql = "DELETE FROM Favorito WHERE produto_id = ? AND cliente_id = ?";
    jdbcTemplate.update(sql, produtoId, clienteId);
  }

  public boolean existeFavorito(int produtoId, int clienteId) {
    String sql = "SELECT COUNT(*) FROM Favorito WHERE produto_id = ? AND cliente_id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, produtoId, clienteId);
    return count != null && count > 0;
  }

  public List<Produto> listarProdutosFavoritosPorCliente(int clienteId) {
    String sql = """
      SELECT p.*
      FROM Produto p
      JOIN Favorito f ON p.id_produto = f.produto_id
      WHERE f.cliente_id = ?
    """;
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produto.class), clienteId);
  }
}
