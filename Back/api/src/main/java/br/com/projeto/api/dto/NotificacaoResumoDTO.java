package br.com.projeto.api.dto;

public class NotificacaoResumoDTO {
  private int idNotificacao;
  private String mensagem;
  private int totalEnvios;
  private int enviosAtivos;

  // Getters e Setters
  public int getIdNotificacao() {
    return idNotificacao;
  }
  public void setIdNotificacao(int idNotificacao) {
    this.idNotificacao = idNotificacao;
  }
  public String getMensagem() {
    return mensagem;
  }
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
  public int getTotalEnvios() {
    return totalEnvios;
  }
  public void setTotalEnvios(int totalEnvios) {
    this.totalEnvios = totalEnvios;
  }
  public int getEnviosAtivos() {
    return enviosAtivos;
  }
  public void setEnviosAtivos(int enviosAtivos) {
    this.enviosAtivos = enviosAtivos;
  }
}
