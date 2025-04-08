package br.com.projeto.api.dto;

import java.math.BigDecimal;

public class PagamentoStatusDTO {
  private String statusPagamento;
  private int totalPagamentos;
  private BigDecimal totalReceita;

  // Getters e Setters
  public String getStatusPagamento() {
    return statusPagamento;
  }
  public void setStatusPagamento(String statusPagamento) {
    this.statusPagamento = statusPagamento;
  }
  public int getTotalPagamentos() {
    return totalPagamentos;
  }
  public void setTotalPagamentos(int totalPagamentos) {
    this.totalPagamentos = totalPagamentos;
  }
  public BigDecimal getTotalReceita() {
    return totalReceita;
  }
  public void setTotalReceita(BigDecimal totalReceita) {
    this.totalReceita = totalReceita;
  }
}
