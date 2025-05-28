package br.com.projeto.api.model;

import java.math.BigDecimal;
import java.util.Date;

public class Pedido {

  private int idPedido;
  private Date dataPedido;
  private String statusPedido;
  private String statusPagamento;
  private BigDecimal valorTotal;
  private int carrinhoId;

  // Getters e Setters
  public int getIdPedido() {
    return idPedido;
  }
  public void setIdPedido(int idPedido) {
    this.idPedido = idPedido;
  }
  public Date getDataPedido() {
    return dataPedido;
  }
  public void setDataPedido(Date dataPedido) {
    this.dataPedido = dataPedido;
  }
  public String getStatusPedido() {
    return statusPedido;
  }
  public void setStatusPedido(String statusPedido) {
    this.statusPedido = statusPedido;
  }
  public BigDecimal getValorTotal() {
    return valorTotal;
  }
  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }
  public int getCarrinhoId() {
    return carrinhoId;
  }
  public void setCarrinhoId(int carrinhoId) {
    this.carrinhoId = carrinhoId;
  }
  public String getStatusPagamento() {
    return statusPagamento;
  }
  public void setStatusPagamento(String statusPagamento) {
    this.statusPagamento = statusPagamento;
  }
}
