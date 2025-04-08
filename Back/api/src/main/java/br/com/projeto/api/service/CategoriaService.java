package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.api.model.Categoria;
import br.com.projeto.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  public List<Categoria> listarCategorias() {
    return categoriaRepository.findAll();
  }

  @Transactional
  public void criarCategoria(Categoria categoria) {
    if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("O nome da categoria não pode ser vazio.");
    }
    categoriaRepository.inserirCategoria(categoria);
  }

  @Transactional
  public void atualizarCategoria(int id, Categoria categoria) {
    categoriaRepository.atualizarCategoria(id, categoria);
  }

  @Transactional
  public void removerCategoria(int id) {
    categoriaRepository.deletarCategoria(id);
  }
}
