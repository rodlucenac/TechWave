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
    public ResponseEntity<Integer> criarEndereco(@RequestBody Endereco endereco) {
        int id = enderecoService.criarEndereco(endereco);
        return ResponseEntity.status(201).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarEndereco(
            @PathVariable int id,
            @RequestBody Endereco endereco) {
        endereco.setIdEndereco(id);
        enderecoService.atualizarEndereco(endereco);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable int id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Endereco>> listarDoUsuario(@PathVariable("id") int idUsuario) {
        List<Endereco> lista = enderecoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable int id) {
        Endereco e = enderecoService.obterEndereco(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e);
    }
}