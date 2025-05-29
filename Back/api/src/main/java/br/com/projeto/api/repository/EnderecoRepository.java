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

    public int inserirEndereco(Endereco endereco) {
    String sql = """
      INSERT INTO Endereco 
        (id_usuario, rua, numero, bairro, cidade, estado, cep)
      VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
    jdbcTemplate.update(
      sql,
      endereco.getIdUsuario(),
      endereco.getRua(),
      endereco.getNumero(),
      endereco.getBairro(),
      endereco.getCidade(),
      endereco.getEstado(),
      endereco.getCep()
    );
    return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
}


    public void atualizarEndereco(Endereco endereco) {
        String sql = "UPDATE Endereco SET rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";
        jdbcTemplate.update(
            sql,
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep(),
            endereco.getIdEndereco()
        );
    }

    public void deletarEndereco(int idEndereco) {
        String sql = "DELETE FROM Endereco WHERE id_endereco = ?";
        jdbcTemplate.update(sql, idEndereco);
    }

    public List<Endereco> listarPorUsuario(int idUsuario) {
        String sql = "SELECT id_endereco AS idEndereco, rua, numero, bairro, cidade, estado, cep, id_usuario AS idUsuario FROM Endereco WHERE id_usuario = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Endereco.class), idUsuario);
    }


    public Endereco buscarPorId(int idEndereco) {
        String sql = "SELECT id_endereco AS idEndereco, id_usuario AS idUsuario, rua, numero, bairro, cidade, estado, cep " +
                     "FROM Endereco WHERE id_endereco = ?";
        return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Endereco.class),
            idEndereco
        );
    }
}
