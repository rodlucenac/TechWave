package br.com.projeto.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {
  private int idPedido;
  private int idCliente;
  private LocalDateTime dataPedido;
  private String statusPedido;
  private BigDecimal valorTotal;
  
  public Pedido(){}

  public Pedido(int idPedido, int idCliente, LocalDateTime dataPedido, String statusPedido, BigDecimal valorTotal){
    this.idPedido = idPedido;
    this.idCliente = idCliente;
    this.dataPedido = dataPedido;
    this.statusPedido = statusPedido;
    this.valorTotal = valorTotal;
  }

  public int getIdPedido(){
    return idPedido;
  }
  public void setIdPedido(int idPedido){
    this.idPedido = idPedido;
  }
  public int getIdCliente(){
    return idCliente;
  }
  public void setIdCliente(int idCliente){
    this.idCliente = idCliente;
  }
  public LocalDateTime getDataPedido(){
    return dataPedido;
  }
  public void setDataPedido(LocalDateTime dataPedido){
    this.dataPedido = dataPedido;
  }
  public String getStatusPedido(){
    return statusPedido;
  }
  public void setStatusPedido(String statusPedido){
    this.statusPedido = statusPedido;
  }
  public BigDecimal getValorTotal(){
    return valorTotal;
  }
  public void setValorTotal(BigDecimal valorTotal){
    this.valorTotal = valorTotal;
  }
}
