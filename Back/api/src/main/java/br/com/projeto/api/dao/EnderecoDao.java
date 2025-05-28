package br.com.projeto.api.dao;

import br.com.projeto.api.model.Endereco;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnderecoDao {

    private final JdbcTemplate jdbc;
    public EnderecoDao(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    /**
     * Retorna o endereço “principal” de um cliente,
     * usando a FK Usuario.endereco_id.
     */
    public Endereco buscarPorCliente(int idCliente) {
        String sql = """
            SELECT e.* 
            FROM Usuario u
            JOIN Endereco e ON u.endereco_id = e.id_endereco
            WHERE u.id_usuario = ?
        """;
        return jdbc.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Endereco.class),
            idCliente
        );
    }
}