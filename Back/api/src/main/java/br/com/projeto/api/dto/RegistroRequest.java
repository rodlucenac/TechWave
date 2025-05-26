package br.com.projeto.api.dto;

import br.com.projeto.api.model.Usuario;
import br.com.projeto.api.model.Endereco;

public class RegistroRequest {
    private Usuario usuario;
    private Endereco endereco;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}