package br.com.projeto.api.controller;

import br.com.projeto.api.model.Cliente;
import br.com.projeto.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
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
