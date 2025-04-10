package br.com.projeto.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Administrador;

@Repository
public class AdministradorRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean existsById(int idUsuario) {
    String sql = "SELECT COUNT(*) FROM Administrador WHERE id_usuario = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
    return count != null && count > 0;
  }

  public Administrador findById(int idUsuario) {
    String sql = "SELECT * FROM Administrador WHERE id_usuario = ?";
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Administrador.class), idUsuario);
  }
}
