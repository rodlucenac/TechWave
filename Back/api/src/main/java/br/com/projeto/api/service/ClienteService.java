package br.com.projeto.api.service;

import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  public boolean isCliente(int idUsuario) {
    return clienteRepository.existsById(idUsuario);
  }

  public Cliente buscarPorId(int idUsuario) {
    return clienteRepository.findById(idUsuario);
  }
}
