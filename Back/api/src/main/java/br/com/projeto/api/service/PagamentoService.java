package br.com.projeto.api.service;

import br.com.projeto.api.dao.PedidoDao;
import br.com.projeto.api.dao.PagamentoDao;
import br.com.projeto.api.dto.PagamentoRequestDTO;
import br.com.projeto.api.model.Pedido;
import br.com.projeto.api.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PagamentoService {

  @Autowired
  private PedidoDao pedidoDao;

  @Autowired
  private PagamentoDao pagamentoDao;

  public void registrarPagamento(PagamentoRequestDTO dto) {
    Pedido pedido = new Pedido();
    pedido.setDataPedido(new Date());
    pedido.setStatusPedido("CONFIRMADO");
    pedido.setStatusPagamento("PAGO");
    pedido.setValorTotal(dto.getValorTotal());
    pedido.setCarrinhoId(dto.getCarrinhoId());

    pedidoDao.inserirPedido(pedido);

    int idPedido = pedidoDao.obterUltimoId();

    Pagamento pagamento = new Pagamento();
    pagamento.setTipoPagamento(dto.getTipoPagamento());
    pagamento.setStatusPagamento("CONFIRMADO");
    pagamento.setValorPagamento(dto.getValorTotal());
    pagamento.setDataPagamento(new Date());
    pagamento.setPedidoId(idPedido);

    pagamentoDao.inserirPagamento(pagamento);
  }
}