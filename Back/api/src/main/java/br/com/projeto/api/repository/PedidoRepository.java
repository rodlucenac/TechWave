package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Pedido;

@Repository
public class PedidoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // Lista todos os pedidos
  public List<Pedido> findAll() {
    String sql = "SELECT * FROM Pedido";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));
  }

  /**
   * Retorna todos os pedidos associados a um cliente específico.
   * Observação: este SELECT assume que existe relação entre
   * Cliente (id_usuario) → Carrinho_compra → Pedido.
   * Ajuste a cláusula JOIN conforme o seu modelo real.
   */
  public List<Pedido> listarPedidosPorCliente(int idCliente) {
    String sql = """
        SELECT p.*
          FROM Pedido p
          JOIN Carrinho_compra c ON c.id_carrinho = p.carrinho_id
          JOIN Cliente cli       ON cli.id_usuario = ?
        """;
    return jdbcTemplate.query(
        sql,
        new BeanPropertyRowMapper<>(Pedido.class),
        idCliente
    );
  }

  // Insere um novo pedido (se houver uma procedure para criação, chame-a aqui; senão, insere diretamente)
  public void inserirPedido(Pedido pedido) {
    // Exemplo: inserindo diretamente. Caso você tenha uma procedure, substitua por "CALL sp_inserir_pedido(?, ?, ?, ?)".
    String sql = "INSERT INTO Pedido (data_pedido, status_pedido, valor_total, carrinho_id) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql,
          pedido.getDataPedido(),
          pedido.getStatusPedido(),
          pedido.getValorTotal(),
          pedido.getCarrinhoId());
  }

  // Atualiza um pedido (pode ser utilizado para atualizar status, por exemplo)
  public void atualizarPedido(int id, Pedido pedido) {
    // Exemplo de update direto. Se houver uma procedure, use-a.
    String sql = "UPDATE Pedido SET data_pedido = ?, status_pedido = ?, valor_total = ?, carrinho_id = ? WHERE id_pedido = ?";
    jdbcTemplate.update(sql,
          pedido.getDataPedido(),
          pedido.getStatusPedido(),
          pedido.getValorTotal(),
          pedido.getCarrinhoId(),
          id);
  }

  // Deleta um pedido
  public void deletarPedido(int id) {
    String sql = "DELETE FROM Pedido WHERE id_pedido = ?";
    jdbcTemplate.update(sql, id);
  }
}
