package br.com.projeto.api.service;

import org.springframework.stereotype.Service;
import br.com.projeto.api.dao.CartDao;
import br.com.projeto.api.model.Carrinho;
import br.com.projeto.api.model.CarrinhoItem;

import java.util.List;

@Service
public class CartService {
    private final CartDao dao;

    public CartService(CartDao dao) {
        this.dao = dao;
    }

    /**
     * Cria um novo carrinho (status = 'aberto') e retorna o objeto completo.
     */
    public Carrinho criar() {
        int id = dao.criarCarrinho();
        return dao.buscarCarrinho(id);
    }

    /**
     * Adiciona quantidade de um produto ao carrinho,
     * ou insere se ainda não existir, e retorna o carrinho atualizado.
     */
    public Carrinho addItem(int cartId, int prodId, int qtde) {
        dao.addItem(cartId, prodId, qtde);
        return dao.buscarCarrinho(cartId);
    }

    /**
     * Atualiza a quantidade de um item; se novaQtde ≤ 0,
     * remove o item. Retorna o carrinho atualizado.
     */
    public Carrinho atualizarQuantidade(int cartId, int prodId, int novaQtde) {
        dao.atualizarQuantidade(cartId, prodId, novaQtde);
        return dao.buscarCarrinho(cartId);
    }

    /**
     * Remove completamente um item do carrinho e retorna o carrinho atualizado.
     */
    public Carrinho removerItem(int cartId, int prodId) {
        dao.removerItem(cartId, prodId);
        return dao.buscarCarrinho(cartId);
    }

    /**
     * Finaliza o carrinho (status = 'fechado') e retorna o carrinho.
     */
    public Carrinho checkout(int cartId) {
        dao.checkout(cartId);
        return dao.buscarCarrinho(cartId);
    }

    /**
     * Busca apenas os dados do carrinho (id, status, total, criadoEm).
     */
    public Carrinho buscar(int cartId) {
        return dao.buscarCarrinho(cartId);
    }

    /**
     * Lista todos os itens atuais do carrinho.
     */
    public List<CarrinhoItem> listarItens(int cartId) {
        return dao.listarItens(cartId);
    }
}