package br.com.projeto.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.dto.RegistroRequest;
import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public List<Usuario> listarUsuarios() {
      return usuarioService.listarUsuarios();
  }

  @PostMapping("/registro")
  public ResponseEntity<String> registrarUsuario(@RequestBody RegistroRequest request) {
    usuarioService.criarUsuarioComEndereco(request);
    return ResponseEntity.ok("Usuário registrado com sucesso!");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Usuario request) {
    Usuario usuario = usuarioService.findByEmail(request.getEmail());

    if (usuario == null || !usuario.getSenha().equals(request.getSenha())) {
      return ResponseEntity.status(401).body("Credenciais inválidas.");
    }

    String tipo = usuarioService.identificarTipoUsuario(usuario.getIdUsuario());
    if ("desconhecido".equals(tipo)) {
      return ResponseEntity.status(403).body("Usuário sem papel definido.");
    }

    return ResponseEntity.ok(Map.of(
      "id",    usuario.getIdUsuario(),
      "nome",  usuario.getNome(),
      "email", usuario.getEmail(),
      "tipo",  tipo
    ));
  }
}
