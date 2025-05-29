package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.api.model.Produto;
import br.com.projeto.api.repository.FavoritoRepository;

@Service
public class FavoritoService {

  @Autowired
  private FavoritoRepository favoritoRepository;

  public void favoritar(int produtoId, int clienteId) {
    if (!favoritoRepository.existeFavorito(produtoId, clienteId)) {
      favoritoRepository.inserirFavorito(produtoId, clienteId);
    }
  }

  public void desfavoritar(int produtoId, int clienteId) {
    if (favoritoRepository.existeFavorito(produtoId, clienteId)) {
      favoritoRepository.removerFavorito(produtoId, clienteId);
    }
  }

  public boolean isFavorito(int produtoId, int clienteId) {
    return favoritoRepository.existeFavorito(produtoId, clienteId);
  }

  public List<Produto> listarProdutosFavoritos(int clienteId) {
    return favoritoRepository.listarProdutosFavoritosPorCliente(clienteId);
}
}
