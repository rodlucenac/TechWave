package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Categoria;

@Repository
public class CategoriaRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // Lista todas as categorias
  public List<Categoria> findAll() {
    String sql = "SELECT * FROM Categoria";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categoria.class));
  }

  // Insere uma nova categoria usando a procedure sp_inserir_categoria
  public void inserirCategoria(Categoria categoria) {
    String sql = "CALL sp_inserir_categoria(?, ?)";
    jdbcTemplate.update(sql, categoria.getNome(), categoria.getDescricao());
  }

  // Atualiza uma categoria usando a procedure sp_atualizar_categoria
  public void atualizarCategoria(int id, Categoria categoria) {
    String sql = "CALL sp_atualizar_categoria(?, ?, ?)";
    jdbcTemplate.update(sql, id, categoria.getNome(), categoria.getDescricao());
  }

  // Deleta uma categoria usando a procedure sp_deletar_categoria
  public void deletarCategoria(int id) {
    String sql = "CALL sp_deletar_categoria(?)";
    jdbcTemplate.update(sql, id);
  }
}
