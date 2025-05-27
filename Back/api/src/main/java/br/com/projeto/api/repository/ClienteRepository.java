package br.com.projeto.api.repository;

import br.com.projeto.api.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean existsById(int idUsuario) {
    String sql = "SELECT COUNT(*) FROM Cliente WHERE id_usuario = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
    return count != null && count > 0;
  }

  public Cliente findById(int idUsuario) {
    String sql = "SELECT * FROM Cliente WHERE id_usuario = ?";
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), idUsuario);
  }


  public void save(Cliente cliente) {
    String sql = "INSERT INTO Cliente (id_usuario, telefone, data_nascimento) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql,
        cliente.getIdUsuario(),
        cliente.getTelefone(),
        cliente.getDataNascimento()
    );
  }
}
