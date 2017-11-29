package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import com.br.apss.pedidovenda.model.Pedido;

public class PedidoAlteradoEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido;

	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
