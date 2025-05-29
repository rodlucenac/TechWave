package br.com.projeto.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.Endereco;

@Repository
public class EnderecoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Insere um novo endereço usando stored procedure.
     * Retorna o novo id_endereco.
     */
    public int inserirEndereco(Endereco endereco) {
        jdbcTemplate.update(
            "CALL sp_inserir_endereco(?, ?, ?, ?, ?, ?)",
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    /**
     * Atualiza um endereço existente via stored procedure.
     */
    public void atualizarEndereco(Endereco endereco) {
        jdbcTemplate.update(
            "CALL sp_atualizar_endereco(?, ?, ?, ?, ?, ?, ?)",
            endereco.getIdEndereco(),
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }

    /**
     * Deleta um endereço pelo id via stored procedure.
     */
    public void deletarEndereco(int idEndereco) {
        jdbcTemplate.update(
            "CALL sp_deletar_endereco(?)",
            idEndereco
        );
    }

    /**
     * Lista todos os endereços.
     */
    public List<Endereco> listarPorUsuario(int idUsuario) {
        String sql = """
            SELECT e.id_endereco AS idEndereco,
                e.rua, e.numero, e.bairro, e.cidade, e.estado, e.cep
            FROM Usuario u 
            JOIN Endereco e ON u.endereco_id = e.id_endereco
            WHERE u.id_usuario = ?
        """;
        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(Endereco.class),
            idUsuario
        );
    }

    /**
     * Pesquisa um endereço por ID.
     */
    public Endereco buscarPorId(int idEndereco) {
        String sql = "SELECT id_endereco AS idEndereco, rua, numero, bairro, cidade, estado, cep FROM Endereco WHERE id_endereco = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Endereco.class), idEndereco);
    }

}