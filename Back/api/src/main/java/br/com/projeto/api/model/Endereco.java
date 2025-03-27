package br.com.projeto.api.model;

public class Endereco {
  private int idEndereco;
  private int idUsuario;
  private String rua;
  private String numero;
  private String bairro;
  private String cidade;
  private String estado;
  private String cep;
  
  public Endereco(){}

  public Endereco(int idEndereco, int idUsuario, String rua, String numero, String bairro, String cidade, String estado, String cep){
    this.idEndereco = idEndereco;
    this.idUsuario = idUsuario;
    this.rua = rua;
    this.numero = numero;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
    this.cep = cep;
  }

  public int getIdEndereco(){
    return idEndereco;
  }
  public void setIdEndereco(int idEndereco){
    this.idEndereco = idEndereco;
  }
  public int getIdUsuario(){
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario){
    this.idUsuario = idUsuario;
  }
  public String getRua(){
    return rua;
  }
  public void setRua(String rua){
    this.rua = rua;
  }
  public String getNumero(){
    return numero;
  }
  public void setNumero(String numero){
    this.numero = numero;
  }
  public String getBairro(){
    return bairro;
  }
  public void setBairro(String bairro){
    this.bairro = bairro;
  }
  public String getCidade(){
    return cidade;
  }
  public void setCidade(String cidade){
    this.cidade = cidade;
  }
  public String getEstado(){
    return estado;
  }
  public void setEstado(String estado){
    this.estado = estado;
  }
  public String getCep(){
    return cep;
  }
  public void setCep(String cep){
    this.cep = cep;
  }
}
