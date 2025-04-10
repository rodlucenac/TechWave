package br.com.projeto.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.api.model.Administrador;
import br.com.projeto.api.repository.AdministradorRepository;

@Service
public class AdministradorService {

  @Autowired
  private AdministradorRepository administradorRepository;

  public boolean isAdmin(int idUsuario) {
    return administradorRepository.existsById(idUsuario);
  }

  public Administrador buscarPorId(int idUsuario) {
    return administradorRepository.findById(idUsuario);
  }
}
