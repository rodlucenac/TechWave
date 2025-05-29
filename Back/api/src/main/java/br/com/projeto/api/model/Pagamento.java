package br.com.projeto.api.model;

import java.math.BigDecimal;
import java.util.Date;

public class Pagamento {
  private int idPagamento;
  private String tipoPagamento;
  private String statusPagamento;
  private BigDecimal valorPagamento;
  private Date dataPagamento;
  private int pedidoId;

  // Getters e Setters
  public int getIdPagamento() { return idPagamento; }
  public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

  public String getTipoPagamento() { return tipoPagamento; }
  public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

  public String getStatusPagamento() { return statusPagamento; }
  public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }

  public BigDecimal getValorPagamento() { return valorPagamento; }
  public void setValorPagamento(BigDecimal valorPagamento) { this.valorPagamento = valorPagamento; }

  public Date getDataPagamento() { return dataPagamento; }
  public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }

  public int getPedidoId() { return pedidoId; }
  public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }
}