package br.com.projeto.api.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Carrinho {
  private int idCarrinho;
  private String statusCarrinho;
  private BigDecimal valorTotal;
  private Instant criadoEm;

  // Getters e Setters
  public int getIdCarrinho() {
    return idCarrinho;
  }
  public void setIdCarrinho(int idCarrinho) {
    this.idCarrinho = idCarrinho;
  }
  public String getStatusCarrinho() {
    return statusCarrinho;
  }
  public void setStatusCarrinho(String statusCarrinho) {
    this.statusCarrinho = statusCarrinho;
  }

  public BigDecimal getValorTotal() {
      return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
      this.valorTotal = valorTotal;
  }

  public Instant getCriadoEm() {
      return criadoEm;
  }

  public void setCriadoEm(Instant criadoEm) {
      this.criadoEm = criadoEm;
  }
}
