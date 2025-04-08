package br.com.projeto.api.model;

import java.util.Date;

public class Favorito {
  private int produtoId;
  private int clienteId;
  private Date dataAdicao;

  // Getters e Setters
  public int getProdutoId() {
    return produtoId;
  }
  public void setProdutoId(int produtoId) {
    this.produtoId = produtoId;
  }
  public int getClienteId() {
    return clienteId;
  }
  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }
  public Date getDataAdicao() {
    return dataAdicao;
  }
  public void setDataAdicao(Date dataAdicao) {
    this.dataAdicao = dataAdicao;
  }
}
