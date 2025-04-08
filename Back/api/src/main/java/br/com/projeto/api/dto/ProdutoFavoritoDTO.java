package br.com.projeto.api.dto;

public class ProdutoFavoritoDTO {
  private int idProduto;
  private String nome;
  private int totalFavoritos;

  // Getters e Setters
  public int getIdProduto() {
    return idProduto;
  }
  public void setIdProduto(int idProduto) {
    this.idProduto = idProduto;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public int getTotalFavoritos() {
    return totalFavoritos;
  }
  public void setTotalFavoritos(int totalFavoritos) {
    this.totalFavoritos = totalFavoritos;
  }
}
