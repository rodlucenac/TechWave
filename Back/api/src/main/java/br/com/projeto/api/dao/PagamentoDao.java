package br.com.projeto.api.dao;

import br.com.projeto.api.model.Pagamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PagamentoDao {
  private final JdbcTemplate jdbc;

  public PagamentoDao(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public void inserirPagamento(Pagamento pagamento) {
    String sql = "INSERT INTO Pagamento (tipo_pagamento, status_pagamento, valor_pagamento, data_pagamento, pedido_id) VALUES (?, ?, ?, ?, ?)";
    jdbc.update(sql,
      pagamento.getTipoPagamento(),
      pagamento.getStatusPagamento(),
      pagamento.getValorPagamento(),
      pagamento.getDataPagamento(),
      pagamento.getPedidoId()
    );
  }
}