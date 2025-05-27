package br.com.projeto.api.service;

import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  public boolean isCliente(int idUsuario) {
    return clienteRepository.existsById(idUsuario);
  }

  public Cliente buscarPorId(int idUsuario) {
    try {
      return clienteRepository.findById(idUsuario);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public void inserirCliente(Cliente cliente) {
    clienteRepository.save(cliente);
  }
}
