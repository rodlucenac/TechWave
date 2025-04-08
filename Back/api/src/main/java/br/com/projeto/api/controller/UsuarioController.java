package br.com.projeto.api.controller;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
