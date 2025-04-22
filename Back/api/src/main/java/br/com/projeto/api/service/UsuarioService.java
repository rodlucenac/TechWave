package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Para encriptar a senha
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  //private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public Usuario findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  public List<Usuario> listarUsuarios() {
    return usuarioRepository.findAll();
  }

  @Transactional
  public void criarUsuario(Usuario usuario) {
    if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
    }
    if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
      throw new IllegalArgumentException("O email do usuário não pode ser vazio.");
    }
    //usuario.setSenha(encoder.encode(usuario.getSenha()));
    usuarioRepository.inserirUsuario(usuario);
  }
}
