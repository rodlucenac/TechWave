package br.com.projeto.api.model;

public class Notificacao {
  private int idNotificacao;
  private String mensagem;
  
  public Notificacao(){}

  public Notificacao(int idNotificacao, String mensagem){
    this.idNotificacao = idNotificacao;
    this.mensagem = mensagem;
  }

  public int getIdNotificacao(){
    return idNotificacao;
  }
  public void setIdNotificacao(int idNotificacao){
    this.idNotificacao = idNotificacao;
  }
  public String getMensagem(){
    return mensagem;
  }
  public void setMensagem(String mensagem){
    this.mensagem = mensagem;
  }
}
