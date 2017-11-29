package com.br.apss.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.ItemPedido;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.util.Transacional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;
	
	@Transacional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoService.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	@Transacional
	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidoService.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
}
