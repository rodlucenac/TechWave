package br.com.projeto.api.service;

import br.com.projeto.api.dto.PedidoPorDiaDTO;
import br.com.projeto.api.dto.PedidoStatusDTO;
import br.com.projeto.api.dto.PagamentoStatusDTO;
import br.com.projeto.api.dto.ProdutoFavoritoDTO;
import br.com.projeto.api.dto.NotificacaoResumoDTO;
import br.com.projeto.api.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {

  @Autowired
  private DashboardRepository dashboardRepository;

  public List<ProdutoFavoritoDTO> getProdutosFavoritos() {
    return dashboardRepository.getProdutosFavoritos();
  }

  public List<PedidoPorDiaDTO> getPedidosPorDia() {
    return dashboardRepository.getPedidosPorDia();
  }

  public List<PedidoStatusDTO> getPedidosStatus() {
    return dashboardRepository.getPedidosStatus();
  }

  public List<PagamentoStatusDTO> getPagamentosStatus() {
    return dashboardRepository.getPagamentosStatus();
  }

  public List<NotificacaoResumoDTO> getResumoNotificacoes() {
    return dashboardRepository.getResumoNotificacoes();
  }
}
