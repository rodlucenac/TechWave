package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Usuario;

@Repository
public class UsuarioRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Usuario findByEmail(String email) {
    String sql = "SELECT * FROM Usuario WHERE email = ?";
    List<Usuario> usuarios = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class), email);
    return usuarios.isEmpty() ? null : usuarios.get(0);
  }

  public List<Usuario> findAll() {
    String sql = "SELECT * FROM Usuario";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class));
  }

  public void inserirUsuario(Usuario usuario) {
    String sql = "INSERT INTO Usuario (nome, email, senha, cpf, endereco_id, ativo) VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
      usuario.getNome(),
      usuario.getEmail(),
      usuario.getSenha(),
      usuario.getCpf(),
      usuario.getEnderecoId(),
      Boolean.TRUE.equals(usuario.getAtivo()) ? 1 : 0
    );
  }

}
