package br.com.projeto.api.model;

import java.time.LocalDate;

public class Cliente {
  private int idCliente;
  private LocalDate dataNascimento;
  private String telefone;
  private int idUsuario;
  
  public Cliente(){}

  public Cliente(int idCliente, LocalDate dataNascimento, String telefone, int idUsuario){
    this.idCliente = idCliente;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.idUsuario = idUsuario;
  }

  public int getIdCliente(){
    return idCliente;
  }
  public void setIdCliente(int idCliente){
    this.idCliente = idCliente;
  }
  public LocalDate getDataNascimento(){
    return dataNascimento;
  }
  public void setDataNascimento(LocalDate dataNascimento){
    this.dataNascimento = dataNascimento;
  }
  public String getTelefone(){
    return telefone;
  }
  public void setTelefone(String telefone){
    this.telefone = telefone;
  }
  public int getIdUsuario(){
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario){
    this.idUsuario = idUsuario;
  }
}
