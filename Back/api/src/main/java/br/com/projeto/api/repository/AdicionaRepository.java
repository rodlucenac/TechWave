package br.com.projeto.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdicionaRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void adicionarItem(int pedidoId, int produtoId, int quantidade) {
    String sql = "INSERT INTO Adiciona (pedido_id, produto_id, quantidade) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, pedidoId, produtoId, quantidade);
  }

  public void removerItem(int pedidoId, int produtoId) {
    String sql = "DELETE FROM Adiciona WHERE pedido_id = ? AND produto_id = ?";
    jdbcTemplate.update(sql, pedidoId, produtoId);
  }
}
