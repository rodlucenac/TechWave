package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public List<Usuario> listarUsuarios() {
    return usuarioService.listarUsuarios();
  }

  @PostMapping("/registro")
  public String registrarUsuario(@RequestBody Usuario usuario) {
    usuarioService.criarUsuario(usuario);
    return "Usuário registrado com sucesso!";
  }
}
