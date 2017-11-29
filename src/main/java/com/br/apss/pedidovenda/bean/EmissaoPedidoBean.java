package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.service.EmissaoPedidoService;

@Named
@ViewScoped
public class EmissaoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void emitirPedido() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			// this.pedidoAlteradoEvent.fire(new
			// PedidoAlteradoEvent(this.pedido));

			Messages.addGlobalInfo("Pedido emitido com sucesso!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

}
