package br.com.projeto.api.dao;

import br.com.projeto.api.model.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PedidoDao {
    private final JdbcTemplate jdbc;
    public PedidoDao(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Pedido> listarTodos() {
        return jdbc.query("SELECT * FROM Pedido", (rs, rn) -> {
            Pedido p = new Pedido();
            p.setIdPedido(rs.getInt("id_pedido"));
            p.setDataPedido(rs.getDate("data_pedido"));
            p.setStatusPedido(rs.getString("status_pedido"));
            p.setValorTotal(rs.getBigDecimal("valor_total"));
            p.setCarrinhoId(rs.getInt("carrinho_id"));
            return p;
        });
    }

    public List<Pedido> listarPorCliente(int idCliente) {
        return jdbc.query("""
            SELECT p.* FROM Pedido p
            JOIN Carrinho_compra c ON c.id_carrinho = p.carrinho_id
            WHERE c.id_carrinho IN (
              SELECT id_carrinho FROM Carrinho_compra
              WHERE id_carrinho IN (
                SELECT carrinho_id FROM Pedido WHERE carrinho_id = c.id_carrinho
              )
            )
            AND c.id_carrinho IN (
              SELECT carrinho_id FROM Pedido
              JOIN Carrinho_compra cc ON cc.id_carrinho = Pedido.carrinho_id
              JOIN Cliente cl ON cl.id_usuario = ?
            )
            """,
            (rs, rn) -> {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setDataPedido(rs.getDate("data_pedido"));
                p.setStatusPedido(rs.getString("status_pedido"));
                p.setValorTotal(rs.getBigDecimal("valor_total"));
                p.setCarrinhoId(rs.getInt("carrinho_id"));
                return p;
            }, idCliente);
    }
}