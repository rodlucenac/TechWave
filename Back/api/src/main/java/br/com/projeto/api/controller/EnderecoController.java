package br.com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Endereco;
import br.com.projeto.api.service.EnderecoService;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "http://localhost:3000")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Integer> salvarEndereco(@RequestBody Endereco endereco) {
        int id = enderecoService.salvarEndereco(endereco);
        return ResponseEntity.status(endereco.getIdEndereco() > 0 ? 200 : 201).body(id);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<Void> atualizarEndereco(
        @PathVariable int idEndereco,
        @RequestBody Endereco endereco
    ) {
        endereco.setIdEndereco(idEndereco);
        enderecoService.atualizarEndereco(endereco);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Endereco>> listarPorUsuario(@PathVariable int idUsuario) {
        List<Endereco> lista = enderecoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable int idEndereco) {
        Endereco endereco = enderecoService.obterEndereco(idEndereco);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable int idEndereco) {
        enderecoService.deletarEndereco(idEndereco);
        return ResponseEntity.noContent().build();
    }
}