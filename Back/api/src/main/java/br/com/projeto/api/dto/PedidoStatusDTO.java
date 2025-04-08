package br.com.projeto.api.dto;

import java.math.BigDecimal;

public class PedidoStatusDTO {
  private String statusPedido;
  private int totalPedidos;
  private BigDecimal totalValor;

  // Getters e Setters
  public String getStatusPedido() {
    return statusPedido;
  }
  public void setStatusPedido(String statusPedido) {
    this.statusPedido = statusPedido;
  }
  public int getTotalPedidos() {
    return totalPedidos;
  }
  public void setTotalPedidos(int totalPedidos) {
    this.totalPedidos = totalPedidos;
  }
  public BigDecimal getTotalValor() {
    return totalValor;
  }
  public void setTotalValor(BigDecimal totalValor) {
    this.totalValor = totalValor;
  }
}
