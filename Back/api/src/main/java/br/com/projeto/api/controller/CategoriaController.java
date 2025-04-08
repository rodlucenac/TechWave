package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Categoria;
import br.com.projeto.api.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

  @Autowired
  private CategoriaService categoriaService;

  @GetMapping
  public List<Categoria> listarCategorias() {
    return categoriaService.listarCategorias();
  }

  @PostMapping
  public String criarCategoria(@RequestBody Categoria categoria) {
    categoriaService.criarCategoria(categoria);
    return "Categoria inserida com sucesso!";
  }

  @PutMapping("/{id}")
  public String atualizarCategoria(@PathVariable int id, @RequestBody Categoria categoria) {
    categoriaService.atualizarCategoria(id, categoria);
    return "Categoria atualizada com sucesso!";
  }

  @DeleteMapping("/{id}")
  public String removerCategoria(@PathVariable int id) {
    categoriaService.removerCategoria(id);
    return "Categoria removida com sucesso!";
  }
}
