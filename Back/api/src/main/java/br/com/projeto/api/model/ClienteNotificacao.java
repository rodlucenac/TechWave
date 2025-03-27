package br.com.projeto.api.model;

import java.time.LocalDateTime;

public class ClienteNotificacao {
  private int idCliente;
  private int idNotificacao;
  private LocalDateTime dataEnvio;
  private boolean statusLida;
  
  public ClienteNotificacao(){}

  public ClienteNotificacao(int idCliente, int idNotificacao, LocalDateTime dataEnvio, boolean statusLida){
    this.idCliente = idCliente;
    this.idNotificacao = idNotificacao;
    this.dataEnvio = dataEnvio;
    this.statusLida = statusLida;
  }

  public int getIdCliente(){
    return idCliente;
  }
  public void setIdCliente(int idCliente){
    this.idCliente = idCliente;
  }
  public int getIdNotificacao(){
    return idNotificacao;
  }
  public void setIdNotificacao(int idNotificacao){
    this.idNotificacao = idNotificacao;
  }
  public LocalDateTime getDataEnvio(){
    return dataEnvio;
  }
  public void setDataEnvio(LocalDateTime dataEnvio){
    this.dataEnvio = dataEnvio;
  }
  public boolean isStatusLida(){
    return statusLida;
  }
  public void setStatusLida(boolean statusLida){
    this.statusLida = statusLida;
  }
}

