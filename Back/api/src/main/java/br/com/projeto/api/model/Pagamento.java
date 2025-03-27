package br.com.projeto.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {
  private int idPagamento;
  private int idPedido;
  private String statusPagamento;
  private BigDecimal valorPagamento;
  private LocalDateTime dataPagamento;
  
  public Pagamento(){}

  public Pagamento(int idPagamento, int idPedido, String statusPagamento, BigDecimal valorPagamento, LocalDateTime dataPagamento){
    this.idPagamento = idPagamento;
    this.idPedido = idPedido;
    this.statusPagamento = statusPagamento;
    this.valorPagamento = valorPagamento;
    this.dataPagamento = dataPagamento;
  }

  public int getIdPagamento(){
    return idPagamento;
  }
  public void setIdPagamento(int idPagamento){
    this.idPagamento = idPagamento;
  }
  public int getIdPedido(){
    return idPedido;
  }
  public void setIdPedido(int idPedido){
    this.idPedido = idPedido;
  }
  public String getStatusPagamento(){
    return statusPagamento;
  }
  public void setStatusPagamento(String statusPagamento){
    this.statusPagamento = statusPagamento;
  }
  public BigDecimal getValorPagamento(){
    return valorPagamento;
  }
  public void setValorPagamento(BigDecimal valorPagamento){
    this.valorPagamento = valorPagamento;
  }
  public LocalDateTime getDataPagamento(){
    return dataPagamento;
  }
  public void setDataPagamento(LocalDateTime dataPagamento){
    this.dataPagamento = dataPagamento;
  }
}
