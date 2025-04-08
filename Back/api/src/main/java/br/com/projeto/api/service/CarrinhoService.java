package br.com.projeto.api.service;

import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CarrinhoService {

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  public List<Carrinho> listarCarrinhos() {
    return carrinhoRepository.findAll();
  }

  @Transactional
  public int criarCarrinho(String status) {
    return carrinhoRepository.criarCarrinho(status);
  }
}
