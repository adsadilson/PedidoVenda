package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.util.FacesUtil;
import com.br.apss.pedidovenda.util.Mailer;
import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido() {
		MailMessage message = mailer.novaMensagem();
		
		message.to(this.pedido.getCliente().getEmail())
			.subject("Pedido " + this.pedido.getId())
			.bodyHtml("<strong>Valor total:</strong> " + this.pedido.getValorTotal())
			.send();
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}

}
