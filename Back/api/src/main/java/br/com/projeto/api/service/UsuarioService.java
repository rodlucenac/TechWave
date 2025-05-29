package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.api.dto.RegistroRequest;
import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.model.Endereco;
import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.repository.UsuarioRepository;

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

  public Usuario findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  public List<Usuario> listarUsuarios() {
      return usuarioRepository.findAll();
  }

  @Transactional
  public void criarUsuarioComEndereco(RegistroRequest request) {
    Usuario usuario = request.getUsuario();
    Endereco endereco = request.getEndereco();

    if (usuario == null || usuario.getNome() == null || usuario.getEmail() == null) {
      throw new IllegalArgumentException("Dados do usuário são obrigatórios.");
    }

    int idUsuario = usuarioRepository.inserirUsuario(usuario);

    endereco.setIdUsuario(idUsuario);
    enderecoService.salvarEndereco(endereco);

    Cliente cliente = new Cliente();
    cliente.setIdUsuario(idUsuario);
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
