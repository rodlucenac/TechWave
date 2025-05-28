package br.com.projeto.api.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.math.BigDecimal;


import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.model.CarrinhoItem;

@Repository
public class CartDao {

    private final JdbcTemplate jdbc;

    public CartDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /*----------------  Carrinho  ----------------*/

    /** Cria um carrinho vazio (status = 'aberto') */
    public int criarCarrinho() {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO Carrinho_compra(status_carrinho) VALUES('aberto')",
                 Statement.RETURN_GENERATED_KEYS);
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    /** Lê somente id e status do carrinho */
    public Carrinho buscarCarrinho(int id) {
        return jdbc.queryForObject(
            "SELECT id_carrinho, status_carrinho FROM Carrinho_compra WHERE id_carrinho = ?",
            (rs, rn) -> {
                Carrinho c = new Carrinho();
                c.setIdCarrinho(rs.getInt("id_carrinho"));
                c.setStatusCarrinho(rs.getString("status_carrinho"));
                return c;
            },
            id
        );
    }

    /**
     * Fecha o carrinho e atualiza Pedido e Pagamento.
     * - Carrinho_compra.status_carrinho → 'fechado'
     * - Pedido.status_pedido           → 'aguardando_pagamento'
     * - Pedido.valor_total             → soma dos itens
     * - Se não existir, insere um Pagamento pendente.
     */
    // src/main/java/br/com/projeto/api/dao/CartDao.java
// *** Substitua APENAS o método checkout ***

public void checkout(int cartId, Integer enderecoId) {

    // fecha carrinho
    jdbc.update(
        "UPDATE Carrinho_compra SET status_carrinho = 'fechado' WHERE id_carrinho = ?",
        cartId
    );

    int pedidoId = obterOuCriarPedido(cartId);

    // calcula total
    BigDecimal total = jdbc.queryForObject(
        "SELECT SUM(a.quantidade * p.preco) " +
        "FROM Adiciona a JOIN Produto p ON p.id_produto = a.produto_id " +
        "WHERE a.pedido_id = ?",
        BigDecimal.class, pedidoId
    );

    // grava total + endereço escolhido
    jdbc.update(
        "UPDATE Pedido SET status_pedido = 'aguardando_pagamento', " +
        "valor_total = ?, endereco_id = ? " +
        "WHERE id_pedido = ?",
        total, enderecoId, pedidoId
    );

    // cria pagamento pendente se necessário
    Integer pagId = jdbc.query(
        "SELECT id_pagamento FROM Pagamento WHERE pedido_id = ?",
        rs -> rs.next() ? rs.getInt(1) : null,
        pedidoId
    );
    if (pagId == null) {
        jdbc.update(
            "INSERT INTO Pagamento(tipo_pagamento, status_pagamento, valor_pagamento, data_pagamento, pedido_id) " +
            "VALUES('pix', 'pendente', ?, NOW(), ?)",
            total, pedidoId
        );
    }
}

    /*----------------  Pedido / Itens  ----------------*/

    /** Garante que existe um pedido 1-para-1 para o carrinho e devolve seu id */
    private int obterOuCriarPedido(int cartId) {
        Integer id = jdbc.query(
            "SELECT id_pedido FROM Pedido WHERE carrinho_id = ?", rs -> rs.next() ? rs.getInt(1) : null, cartId);
        if (id != null) return id;

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Pedido(carrinho_id, data_pedido, status_pedido) VALUES(?, NOW(), 'aberto')",
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cartId);
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    /** Adiciona ou incrementa um produto no carrinho usando Pedido + Adiciona */
    public void addItem(int cartId, int prodId, int qtde) {
        int pedidoId = obterOuCriarPedido(cartId);

        int updated = jdbc.update(
            "UPDATE Adiciona SET quantidade = quantidade + ? WHERE pedido_id = ? AND produto_id = ?",
            qtde, pedidoId, prodId
        );

        if (updated == 0) {
            jdbc.update(
              "INSERT INTO Adiciona(pedido_id, produto_id, quantidade) VALUES(?,?,?)",
              pedidoId, prodId, qtde
            );
        }
    }

    /** Atualiza quantidade ou remove se novaQtde <= 0 */
    public void atualizarQuantidade(int cartId, int prodId, int novaQtde) {
        int pedidoId = obterOuCriarPedido(cartId);

        if (novaQtde > 0) {
            jdbc.update(
              "UPDATE Adiciona SET quantidade = ? WHERE pedido_id = ? AND produto_id = ?",
              novaQtde, pedidoId, prodId
            );
        } else {
            jdbc.update(
              "DELETE FROM Adiciona WHERE pedido_id = ? AND produto_id = ?",
              pedidoId, prodId
            );
        }
    }

    /** Remove item */
    public void removerItem(int cartId, int prodId) {
        int pedidoId = obterOuCriarPedido(cartId);
        jdbc.update("DELETE FROM Adiciona WHERE pedido_id = ? AND produto_id = ?", pedidoId, prodId);
    }

    /**
     * Lista todos os itens do carrinho já trazendo nome, descrição e imagem do Produto.
     */
    public List<CarrinhoItem> listarItens(int cartId) {

        // Localiza o pedido 1‑para‑1 ligado ao carrinho
        Integer pedidoId = jdbc.query(
            "SELECT id_pedido FROM Pedido WHERE carrinho_id = ?",
            rs -> rs.next() ? rs.getInt(1) : null,
            cartId
        );
        if (pedidoId == null) return List.of();

        String sql = """
            SELECT a.produto_id,
                   a.quantidade,
                   p.preco,
                   p.nome,
                   p.descricao,
                   p.imagem_nome
              FROM Adiciona a
              JOIN Produto p ON p.id_produto = a.produto_id
             WHERE a.pedido_id = ?
        """;

        return jdbc.query(sql, (rs, rn) -> {
            CarrinhoItem it = new CarrinhoItem();
            it.setCarrinhoId(cartId);
            it.setProdutoId(rs.getInt("produto_id"));
            it.setQuantidade(rs.getInt("quantidade"));
            it.setPrecoUnitario(rs.getBigDecimal("preco"));
            it.setNome(rs.getString("nome"));
            it.setDescricao(rs.getString("descricao"));
            it.setImagemNome(rs.getString("imagem_nome"));
            return it;
        }, pedidoId);
    }


}