package br.com.projeto.api.service;

import br.com.projeto.api.repository.AdicionaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdicionaService {

  @Autowired
  private AdicionaRepository adicionaRepository;

  @Transactional
  public void adicionarItem(int pedidoId, int produtoId, int quantidade) {
    adicionaRepository.adicionarItem(pedidoId, produtoId, quantidade);
  }

  @Transactional
  public void removerItem(int pedidoId, int produtoId) {
    adicionaRepository.removerItem(pedidoId, produtoId);
  }
}
