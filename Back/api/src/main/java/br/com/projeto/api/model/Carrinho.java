package br.com.projeto.api.model;

public class Carrinho {
  private int idCarrinho;
  private int idCliente;
  private String statusCarrinho;
  
  public Carrinho(){}

  public Carrinho(int idCarrinho, int idCliente, String statusCarrinho){
    this.idCarrinho = idCarrinho;
    this.idCliente = idCliente;
    this.statusCarrinho = statusCarrinho;
  }

  public int getIdCarrinho(){
    return idCarrinho;
  }
  public void setIdCarrinho(int idCarrinho){
    this.idCarrinho = idCarrinho;
  }
  public int getIdCliente(){
    return idCliente;
  }
  public void setIdCliente(int idCliente){
    this.idCliente = idCliente;
  }
  public String getStatusCarrinho(){
    return statusCarrinho;
  }
  public void setStatusCarrinho(String statusCarrinho){
    this.statusCarrinho = statusCarrinho;
  }
}
