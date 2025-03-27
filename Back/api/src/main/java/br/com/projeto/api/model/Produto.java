package br.com.projeto.api.model;

import java.math.BigDecimal;

public class Produto {
  private int idProduto;
  private String nome;
  private String descricao;
  private int estoque;
  private String imagem;
  private BigDecimal preco;
  private int idCategoria;
  
  public Produto(){}

  public Produto(int idProduto, String nome, String descricao, int estoque, String imagem, BigDecimal preco, int idCategoria){
    this.idProduto = idProduto;
    this.nome = nome;
    this.descricao = descricao;
    this.estoque = estoque;
    this.imagem = imagem;
    this.preco = preco;
    this.idCategoria = idCategoria;
  }

  public int getIdProduto(){
    return idProduto;
  }
  public void setIdProduto(int idProduto){
    this.idProduto = idProduto;
  }
  public String getNome(){
    return nome;
  }
  public void setNome(String nome){
    this.nome = nome;
  }
  public String getDescricao(){
    return descricao;
  }
  public void setDescricao(String descricao){
    this.descricao = descricao;
  }
  public int getEstoque(){
    return estoque;
  }
  public void setEstoque(int estoque){
    this.estoque = estoque;
  }
  public String getImagem(){
    return imagem;
  }
  public void setImagem(String imagem){
    this.imagem = imagem;
  }
  public BigDecimal getPreco(){
    return preco;
  }
  public void setPreco(BigDecimal preco){
    this.preco = preco;
  }
  public int getIdCategoria(){
    return idCategoria;
  }
  public void setIdCategoria(int idCategoria){
    this.idCategoria = idCategoria;
  }
}
