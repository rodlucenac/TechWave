package br.com.projeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Administrador;
import br.com.projeto.api.service.AdministradorService;

@RestController
@RequestMapping("/admin")
public class AdministradorController {

  @Autowired
  private AdministradorService administradorService;

  @GetMapping("/{id}")
  public Administrador buscarAdministradorPorId(@PathVariable int id) {
    return administradorService.buscarPorId(id);
  }

  @GetMapping("/is-admin/{id}")
  public boolean verificarSeEhAdmin(@PathVariable int id) {
    return administradorService.isAdmin(id);
  }
}
