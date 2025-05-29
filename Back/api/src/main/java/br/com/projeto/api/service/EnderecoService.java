package br.com.projeto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.api.model.Endereco;
import br.com.projeto.api.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    public int criarEndereco(Endereco endereco) {
        return enderecoRepository.inserirEndereco(endereco);
    }

    public void atualizarEndereco(Endereco endereco) {
        enderecoRepository.atualizarEndereco(endereco);
    }

    public void deletarEndereco(int idEndereco) {
        enderecoRepository.deletarEndereco(idEndereco);
    }

    public int salvarEndereco(Endereco endereco) {
        if (endereco.getIdEndereco() > 0) {
            atualizarEndereco(endereco);
            return endereco.getIdEndereco();
        } else {
            return criarEndereco(endereco);
        }
    }

    public List<Endereco> listarPorUsuario(int idUsuario) {
        return enderecoRepository.listarPorUsuario(idUsuario);
    }

    public Endereco obterEndereco(int idEndereco) {
        return enderecoRepository.buscarPorId(idEndereco);
    }

}