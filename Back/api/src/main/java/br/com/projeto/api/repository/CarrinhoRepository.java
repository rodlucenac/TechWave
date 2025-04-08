package br.com.projeto.api.repository;

import br.com.projeto.api.model.Carrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CarrinhoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Carrinho> findAll() {
    String sql = "SELECT * FROM Carrinho_compra";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Carrinho.class));
  }

  public int criarCarrinho(String status) {
    String sql = "INSERT INTO Carrinho_compra (status_carrinho) VALUES (?)";
    jdbcTemplate.update(sql, status);
    return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
  }
}
