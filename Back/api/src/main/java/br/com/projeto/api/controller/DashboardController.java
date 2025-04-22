package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.dto.NotificacaoResumoDTO;
import br.com.projeto.api.dto.PagamentoStatusDTO;
import br.com.projeto.api.dto.PedidoPorDiaDTO;
import br.com.projeto.api.dto.PedidoStatusDTO;
import br.com.projeto.api.dto.ProdutoFavoritoDTO;
import br.com.projeto.api.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  @GetMapping("/produtos-favoritos")
  public List<ProdutoFavoritoDTO> getProdutosFavoritos() {
    return dashboardService.getProdutosFavoritos();
  }

  @GetMapping("/pedidos-por-dia")
  public List<PedidoPorDiaDTO> getPedidosPorDia() {
    return dashboardService.getPedidosPorDia();
  }

  @GetMapping("/pedidos-status")
  public List<PedidoStatusDTO> getPedidosStatus() {
    return dashboardService.getPedidosStatus();
  }

  @GetMapping("/pagamentos-status")
  public List<PagamentoStatusDTO> getPagamentosStatus() {
    return dashboardService.getPagamentosStatus();
  }

  @GetMapping("/notificacoes-resumo")
  public List<NotificacaoResumoDTO> getResumoNotificacoes() {
    return dashboardService.getResumoNotificacoes();
  }
}
