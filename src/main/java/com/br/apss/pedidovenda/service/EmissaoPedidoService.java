package com.br.apss.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.br.apss.pedidovenda.enums.StatusPedido;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.util.NegocioException;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private PedidoService pedidoService;

	public Pedido emitir(Pedido pedido) {
		pedido = this.pedidoService.salvar(pedido);
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException(
					"Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao() + ".");
		}
		this.estoqueService.baixarItensEstoque(pedido);
		pedido.setStatus(StatusPedido.EMITIDO);
		pedido = this.pedidoService.salvar(pedido);
		return pedido;
	}

}
