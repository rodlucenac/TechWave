package br.com.projeto.api.model;

import java.time.LocalDateTime;

public class Favorito {
  private int idUsuario;
  private int idProduto;
  private LocalDateTime dataAdicao;
  
  public Favorito(){}

  public Favorito(int idUsuario, int idProduto, LocalDateTime dataAdicao){
    this.idUsuario = idUsuario;
    this.idProduto = idProduto;
    this.dataAdicao = dataAdicao;
  }

  public int getIdUsuario(){
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario){
    this.idUsuario = idUsuario;
  }
  public int getIdProduto(){
    return idProduto;
  }
  public void setIdProduto(int idProduto){
    this.idProduto = idProduto;
  }
  public LocalDateTime getDataAdicao(){
    return dataAdicao;
  }
  public void setDataAdicao(LocalDateTime dataAdicao){
    this.dataAdicao = dataAdicao;
  }
}
