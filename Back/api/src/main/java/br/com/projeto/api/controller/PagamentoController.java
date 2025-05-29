package br.com.projeto.api.controller;


import br.com.projeto.api.service.PagamentoService;
import br.com.projeto.api.dto.PagamentoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<String> registrarPagamento(@RequestBody PagamentoRequestDTO dto) {
        try {
            pagamentoService.registrarPagamento(dto);
            return ResponseEntity.ok("Pedido confirmado e pagamento registrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro ao registrar pagamento: " + e.getMessage());
        }
    }
}