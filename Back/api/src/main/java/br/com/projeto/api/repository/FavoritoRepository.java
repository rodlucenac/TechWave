package br.com.projeto.api.repository;

import br.com.projeto.api.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FavoritoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Favorito> findByClienteId(int clienteId) {
    String sql = "SELECT * FROM Favorito WHERE cliente_id = ?";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorito.class), clienteId);
  }

  public void inserirFavorito(Favorito favorito) {
    String sql = "INSERT INTO Favorito (produto_id, cliente_id, data_adicao) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, favorito.getProdutoId(), favorito.getClienteId(), favorito.getDataAdicao());
  }

  public void deletarFavorito(int clienteId, int produtoId) {
    String sql = "DELETE FROM Favorito WHERE cliente_id = ? AND produto_id = ?";
    jdbcTemplate.update(sql, clienteId, produtoId);
  }
}
