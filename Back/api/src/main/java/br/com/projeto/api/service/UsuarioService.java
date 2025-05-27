package br.com.projeto.api.service;

import br.com.projeto.api.model.Cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Para encriptar a senha
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.repository.UsuarioRepository;
import br.com.projeto.api.dto.RegistroRequest;
import br.com.projeto.api.model.Endereco;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EnderecoService enderecoService;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private AdministradorService administradorService;

  //private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public Usuario findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  public List<Usuario> listarUsuarios() {
    return usuarioRepository.findAll();
  }

  @Transactional
  public void criarUsuario(Usuario usuario, Endereco endereco) {
    if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
    }
    if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
      throw new IllegalArgumentException("O email do usuário não pode ser vazio.");
    }

    int enderecoId = enderecoService.salvarEndereco(endereco);
    usuario.setEnderecoId(enderecoId);
    usuario.setAtivo(true);
    System.out.println("DEBUG - usuario.ativo = " + usuario.getAtivo());
    //usuario.setSenha(encoder.encode(usuario.getSenha()));
    usuarioRepository.inserirUsuario(usuario);
  }

public void criarUsuarioComEndereco(RegistroRequest request) {
    Usuario usuario = request.getUsuario();
    Endereco endereco = request.getEndereco();

    if (usuario == null || usuario.getNome() == null) {
        throw new IllegalArgumentException("Dados do usuário são obrigatórios.");
    }

    int enderecoId = enderecoService.salvarEndereco(endereco);
    usuario.setEnderecoId(enderecoId);
    usuario.setAtivo(true);

    usuarioRepository.inserirUsuario(usuario);

    Usuario usuarioSalvo = usuarioRepository.findByEmail(usuario.getEmail());
    Cliente cliente = new Cliente();
    cliente.setIdUsuario(usuarioSalvo.getIdUsuario());
    clienteService.inserirCliente(cliente);
}

public String identificarTipoUsuario(int idUsuario) {
    if (clienteService.isCliente(idUsuario)) {
        return "cliente";
    } else if (administradorService.isAdministrador(idUsuario)) {
        return "admin";
    } else {
        return "desconhecido";
    }
}
  
}
