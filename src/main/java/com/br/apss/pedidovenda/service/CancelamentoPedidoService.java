package com.br.apss.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.br.apss.pedidovenda.enums.StatusPedido;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.util.NegocioException;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private EstoqueService estoqueService;
	
	public Pedido cancelar(Pedido pedido) {
		
		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado no status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidoService.salvar(pedido);
		
		return pedido;
	}


}
