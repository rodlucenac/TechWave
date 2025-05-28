package br.com.projeto.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.List;
import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.model.CarrinhoItem;
import br.com.projeto.api.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService svc;
    public CartController(CartService svc) { this.svc = svc; }

    @PostMapping
    public Carrinho criar() {
        return svc.criar();
    }

    @GetMapping("/{id}")
    public Map<String,Object> getCart(@PathVariable int id) {
        Carrinho c = svc.buscar(id);
        List<CarrinhoItem> itens = svc.listarItens(id);
        return Map.of("carrinho", c, "itens", itens);
    }

    @PostMapping("/{id}/items")
    public Carrinho addItem(@PathVariable int id,
                            @RequestParam int productId,
                            @RequestParam int quantity) {
        return svc.addItem(id, productId, quantity);
    }

    @PutMapping("/{id}/items/{prodId}")
    public Carrinho updItem(@PathVariable int id,
                            @PathVariable int prodId,
                            @RequestParam int quantity) {
        return svc.atualizarQuantidade(id, prodId, quantity);
    }

    @PostMapping("/{id}/checkout")
public Carrinho checkout(@PathVariable int id,
                         @RequestParam Integer enderecoId) {
    return svc.checkout(id, enderecoId);
}

    @DeleteMapping("/{id}/items/{prodId}")
    public Carrinho removeItem(@PathVariable int id,
                           @PathVariable int prodId) {
    svc.removerItem(id, prodId);
    return svc.buscar(id);
}
}