package br.com.projeto.api.dao;

import br.com.projeto.api.model.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PedidoDao {
    private final JdbcTemplate jdbc;
    
    public PedidoDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Pedido> listarTodos() {
        return jdbc.query("SELECT * FROM pedido", (rs, rn) -> {
            Pedido p = new Pedido();
            p.setIdPedido(rs.getInt("id_pedido"));
            p.setDataPedido(rs.getDate("data_pedido"));
            p.setStatusPedido(rs.getString("status_pedido"));
            p.setValorTotal(rs.getBigDecimal("valor_total"));
            p.setCarrinhoId(rs.getInt("carrinho_id"));
            p.setStatusPagamento(rs.getString("status_pagamento"));
            return p;
        });
    }

    public List<Pedido> listarPorCliente(int idCliente) {
        return jdbc.query("""
            SELECT p.* FROM pedido p
            JOIN carrinho_compra c ON c.id_carrinho = p.carrinho_id
            WHERE c.id_carrinho IN (
              SELECT id_carrinho FROM carrinho_compra
              WHERE id_carrinho IN (
                SELECT carrinho_id FROM pedido WHERE carrinho_id = c.id_carrinho
              )
            )
            AND c.id_carrinho IN (
              SELECT carrinho_id FROM pedido
              JOIN carrinho_compra cc ON cc.id_carrinho = pedido.carrinho_id
              JOIN cliente cl ON cl.id_usuario = ?
            )
            """,
            (rs, rn) -> {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setDataPedido(rs.getDate("data_pedido"));
                p.setStatusPedido(rs.getString("status_pedido"));
                p.setValorTotal(rs.getBigDecimal("valor_total"));
                p.setCarrinhoId(rs.getInt("carrinho_id"));
                p.setStatusPagamento(rs.getString("status_pagamento"));
                return p;
            }, idCliente);
    }

    public void inserirPedido(Pedido pedido) {
        String sql = "INSERT INTO Pedido (data_pedido, status_pedido, valor_total, carrinho_id) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
            pedido.getDataPedido(),
            pedido.getStatusPedido(),
            pedido.getValorTotal(),
            pedido.getCarrinhoId()
            
        );
    }
    
    public int obterUltimoId() {
        return jdbc.queryForObject("SELECT MAX(id_pedido) FROM Pedido", Integer.class);
    }

    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        String sql = """
            SELECT p.*
            FROM pedido p
            INNER JOIN carrinho_compra c ON p.carrinho_id = c.id_carrinho
            WHERE c.cliente_id = ?
            ORDER BY p.data_pedido DESC
        """;
    
        return jdbc.query(sql, (rs, rowNum) -> {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(rs.getInt("id_pedido"));
            pedido.setDataPedido(rs.getTimestamp("data_pedido"));
            pedido.setStatusPedido(rs.getString("status_pedido"));
            pedido.setStatusPagamento(rs.getString("status_pagamento"));
            pedido.setValorTotal(rs.getBigDecimal("valor_total"));
            pedido.setCarrinhoId(rs.getInt("carrinho_id"));
            return pedido;
        }, idCliente);
    }
}