package br.com.projeto.api.service;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Para encriptar a senha
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public Usuario findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  public List<Usuario> listarUsuarios() {
    return usuarioRepository.findAll();
  }

  @Transactional
  public void criarUsuario(Usuario usuario) {
    // Validação simples
    if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
    }
    if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
      throw new IllegalArgumentException("O email do usuário não pode ser vazio.");
    }
    // Encripta a senha antes de salvar
    usuario.setSenha(encoder.encode(usuario.getSenha()));
    usuario.setAtivo(true);
    usuarioRepository.inserirUsuario(usuario);
  }
}
