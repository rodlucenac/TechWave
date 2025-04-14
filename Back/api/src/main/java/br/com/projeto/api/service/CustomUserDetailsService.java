package br.com.projeto.api.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.repository.AdministradorRepository;
import br.com.projeto.api.repository.ClienteRepository;
import br.com.projeto.api.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AdministradorRepository administradorRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByEmail(email);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }

    String role;

    if (administradorRepository.existsById(usuario.getIdUsuario())) {
      role = "ROLE_ADMIN";
    } else if (clienteRepository.existsById(usuario.getIdUsuario())) {
      role = "ROLE_CLIENT";
    } else {
      throw new UsernameNotFoundException("Usuário sem tipo definido (não é admin nem cliente).");
    }

    return new User(
        usuario.getEmail(),
        usuario.getSenha(),
        Collections.singletonList(new SimpleGrantedAuthority(role))
    );
  }
}
