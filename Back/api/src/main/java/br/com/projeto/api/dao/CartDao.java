// src/main/java/br/com/projeto/api/dao/CartDao.java
package br.com.projeto.api.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


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

    /** Fecha o carrinho (checkout) */
    public void checkout(int cartId) {
        jdbc.update("UPDATE Carrinho_compra SET status_carrinho = 'fechado' WHERE id_carrinho = ?", cartId);
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
 * Lista todos os itens do carrinho consultando a tabela Adiciona.
 */
public List<CarrinhoItem> listarItens(int cartId) {
    // Localiza o pedido 1-para-1 ligado ao carrinho
    Integer pedidoId = jdbc.query(
        "SELECT id_pedido FROM Pedido WHERE carrinho_id = ?",
        rs -> rs.next() ? rs.getInt(1) : null,
        cartId
    );
    if (pedidoId == null) {
        return List.of();   // carrinho ainda sem itens
    }

    return jdbc.query(
        "SELECT a.pedido_id, a.produto_id, a.quantidade, p.preco " +
        "FROM Adiciona a JOIN Produto p ON p.id_produto = a.produto_id " +
        "WHERE a.pedido_id = ?",
        (rs, rn) -> {
            CarrinhoItem item = new CarrinhoItem();
            item.setCarrinhoId(cartId);
            item.setProdutoId(rs.getInt("produto_id"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getBigDecimal("preco"));
            return item;
        },
        pedidoId
    );
}


}