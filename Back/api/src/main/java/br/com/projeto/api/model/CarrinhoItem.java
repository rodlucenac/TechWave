package br.com.projeto.api.model;

import java.math.BigDecimal;

public class CarrinhoItem {
    private Integer idItem;
    private Integer carrinhoId;
    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    


        public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(Integer carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}