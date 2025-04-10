package br.com.projeto.api.model;

public class Administrador {
  private int idUsuario;
  private String cargo;

  // Getters e Setters
  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }
}
