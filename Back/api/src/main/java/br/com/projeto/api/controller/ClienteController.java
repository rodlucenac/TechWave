package br.com.projeto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/{id}")
  public Cliente buscarClientePorId(@PathVariable int id) {
    return clienteService.buscarPorId(id);
  }

  @GetMapping("/is-cliente/{id}")
  public boolean verificarSeEhCliente(@PathVariable int id) {
    return clienteService.isCliente(id);
  }
}
