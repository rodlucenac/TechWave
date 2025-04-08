package br.com.projeto.api.service;

import br.com.projeto.api.model.Favorito;
import br.com.projeto.api.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoritoService {

  @Autowired
  private FavoritoRepository favoritoRepository;

  public List<Favorito> listarFavoritosPorCliente(int clienteId) {
    return favoritoRepository.findByClienteId(clienteId);
  }

  @Transactional
  public void adicionarFavorito(Favorito favorito) {
    favoritoRepository.inserirFavorito(favorito);
  }

  @Transactional
  public void removerFavorito(int clienteId, int produtoId) {
    favoritoRepository.deletarFavorito(clienteId, produtoId);
  }
}
