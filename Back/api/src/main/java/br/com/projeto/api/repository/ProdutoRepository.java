package br.com.projeto.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import br.com.projeto.api.model.Produto;

@Repository
public class ProdutoRepository {
  private final JdbcTemplate jdbc;

  public ProdutoRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Produto> findAll() {
    return jdbc.query(
      "SELECT * FROM Produto",
      new BeanPropertyRowMapper<>(Produto.class)
    );
  }

  public void inserirProduto(Produto p, MultipartFile img) {
    byte[] blob = null;
    String nomeArq = null;
    if (img != null && !img.isEmpty()) {
      try {
        blob = img.getBytes();
        nomeArq = img.getOriginalFilename();
      } catch (IOException e) {
        throw new RuntimeException("Falha ao ler arquivo de imagem", e);
      }
    }
    jdbc.update(
      "CALL sp_inserir_produto(?, ?, ?, ?, ?, ?)",
      p.getNome(),
      p.getDescricao(),
      p.getEstoque(),
      p.getPreco(),
      blob,
      nomeArq
    );
  }

  public void atualizarProduto(int id, Produto p, MultipartFile img) {
    byte[] blob = null;
    String nomeArq = null;
    if (img != null && !img.isEmpty()) {
      try {
        blob = img.getBytes();
        nomeArq = img.getOriginalFilename();
      } catch (IOException e) {
        throw new RuntimeException("Falha ao ler arquivo de imagem", e);
      }
    }
    jdbc.update(
      "CALL sp_atualizar_produto(?, ?, ?, ?, ?, ?, ?)",
      id,
      p.getNome(),
      p.getDescricao(),
      p.getEstoque(),
      p.getPreco(),
      blob,
      nomeArq
    );
  }

  public void deletarProduto(int id) {
    jdbc.update("CALL sp_deletar_produto(?)", id);
  }
}
