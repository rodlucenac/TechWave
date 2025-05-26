package br.com.projeto.api.repository;

import br.com.projeto.api.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnderecoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int inserirEndereco(Endereco endereco) {
        String sql = "INSERT INTO Endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, endereco.getRua(), endereco.getNumero(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado(), endereco.getCep());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
}