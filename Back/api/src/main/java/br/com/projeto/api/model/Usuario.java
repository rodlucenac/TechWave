package br.com.projeto.api.model;

public class Usuario {
  private int idUsuario;
  private String nome;
  private String email;
  private String senha;
  private String cpf;
  private int enderecoId;
  private boolean ativo;

  // Getters e Setters
  public int getIdUsuario() {
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getSenha() {
    return senha;
  }
  public void setSenha(String senha) {
    this.senha = senha;
  }
  public String getCpf() {
    return cpf;
  }
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
  public int getEnderecoId() {
      return enderecoId;
  }
  public void setEnderecoId(int enderecoId) {
    this.enderecoId = enderecoId;
  }
  public boolean isAtivo() {
    return ativo;
  }
  public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }
}
