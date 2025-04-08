package br.com.projeto.api.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PedidoPorDiaDTO {
  private Date dia;
  private int totalPedidos;
  private BigDecimal totalVendas;
  private BigDecimal ticketMedio;

  // Getters e Setters
  public Date getDia() {
    return dia;}
  public void setDia(Date dia) {
    this.dia = dia;}
  public int getTotalPedidos() {
    return totalPedidos;}
  public void setTotalPedidos(int totalPedidos) {
    this.totalPedidos = totalPedidos;}
  public BigDecimal getTotalVendas() {
    return totalVendas;}
  public void setTotalVendas(BigDecimal totalVendas) {
    this.totalVendas = totalVendas;}
  public BigDecimal getTicketMedio() {
    return ticketMedio;}
  public void setTicketMedio(BigDecimal ticketMedio) {
    this.ticketMedio = ticketMedio;}
}
