package br.com.projeto.api.repository;

import br.com.projeto.api.dto.PedidoPorDiaDTO;
import br.com.projeto.api.dto.PedidoStatusDTO;
import br.com.projeto.api.dto.PagamentoStatusDTO;
import br.com.projeto.api.dto.ProdutoFavoritoDTO;
import br.com.projeto.api.dto.NotificacaoResumoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DashboardRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<ProdutoFavoritoDTO> getProdutosFavoritos() {
    String sql = "SELECT * FROM vw_produtos_favoritos";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProdutoFavoritoDTO.class));
  }

  public List<PedidoPorDiaDTO> getPedidosPorDia() {
    String sql = "SELECT * FROM vw_pedidos_por_dia";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PedidoPorDiaDTO.class));
  }

  public List<PedidoStatusDTO> getPedidosStatus() {
    String sql = "SELECT * FROM vw_pedidos_status";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PedidoStatusDTO.class));
  }

  public List<PagamentoStatusDTO> getPagamentosStatus() {
    String sql = "SELECT * FROM vw_pagamentos_status";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PagamentoStatusDTO.class));
  }

  public List<NotificacaoResumoDTO> getResumoNotificacoes() {
    String sql = "SELECT * FROM vw_resumo_notificacoes";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NotificacaoResumoDTO.class));
  }
}
