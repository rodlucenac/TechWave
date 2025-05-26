package br.com.projeto.api.service;

import br.com.projeto.api.model.Endereco;
import br.com.projeto.api.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public int criarEndereco(Endereco endereco) {
        return enderecoRepository.inserirEndereco(endereco);

    }


    public int salvarEndereco(Endereco endereco) {
        return enderecoRepository.inserirEndereco(endereco);
    }
}