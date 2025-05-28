package br.com.projeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.service.ClienteService;

import br.com.projeto.api.dao.EnderecoDao;
import br.com.projeto.api.model.Endereco;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private EnderecoDao enderecoDao;

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> buscarClientePorId(@PathVariable int id) {
    Cliente cliente = clienteService.buscarPorId(id);
    if (cliente == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(cliente);
  }

  @GetMapping("/is-cliente/{id}")
  public boolean verificarSeEhCliente(@PathVariable int id) {
    return clienteService.isCliente(id);
  }

  /**
   * Retorna o endereço principal de um cliente.
   * GET /api/clientes/{id}/endereco
   */
  @GetMapping(path = "/{id}/endereco", produces = MediaType.APPLICATION_JSON_VALUE)
  public Endereco getEndereco(@PathVariable("id") int idCliente) {
    return enderecoDao.buscarPorCliente(idCliente);
  }
}
