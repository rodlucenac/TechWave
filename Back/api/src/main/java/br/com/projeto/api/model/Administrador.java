package br.com.projeto.api.model;

public class Administrador {
  private int idAdministrador;
  private String cargo;
  private int idUsuario;
  
  public Administrador(){}

  public Administrador(int idAdministrador, String cargo, int idUsuario){
    this.idAdministrador = idAdministrador;
    this.cargo = cargo;
    this.idUsuario = idUsuario;
  }

  public int getIdAdministrador(){
    return idAdministrador;
  }
  public void setIdAdministrador(int idAdministrador){
    this.idAdministrador = idAdministrador;
  }
  public String getCargo(){
    return cargo;
  }
  public void setCargo(String cargo){
    this.cargo = cargo;
  }
  public int getIdUsuario(){
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario){
    this.idUsuario = idUsuario;
  }
}
