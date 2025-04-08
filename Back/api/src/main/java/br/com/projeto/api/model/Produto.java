package br.com.projeto.api.model;

import java.math.BigDecimal;

public class Produto {

  private int idProduto;
  private String nome;
  private String descricao;
  private int estoque;
  private BigDecimal preco;
  private String imagem;

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
  public String getDescricao() {
    return descricao;
  }
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  public int getEstoque() {
    return estoque;
  }
  public void setEstoque(int estoque) {
    this.estoque = estoque;
  }
  public BigDecimal getPreco() {
    return preco;
  }
  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }
  public String getImagem() {
    return imagem;
  }
  public void setImagem(String imagem) {
    this.imagem = imagem;
  }
}
