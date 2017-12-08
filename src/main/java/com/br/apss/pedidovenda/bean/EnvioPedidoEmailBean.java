package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.velocity.tools.generic.NumberTool;

import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.service.PedidoService;
import com.br.apss.pedidovenda.util.email.ConfigEnvioEmail;
import com.br.apss.pedidovenda.util.email.Mailer;
import com.br.apss.pedidovenda.util.jsf.FacesUtil;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ConfigEnvioEmail email;

	@Inject
	private Mailer mailer;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void enviarPedidoSimpleEmail() {
		SimpleEmail message;
		try {
			message = email.emailSimples();
			message.addTo(this.pedido.getCliente().getEmail());
			message.setSubject("Pedido " + this.pedido.getId());
			message.setMsg("<strong>Valor total:</strong> " + this.pedido.getValorTotal());
			message.send();

			FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		} catch (EmailException e) {
			e.printStackTrace();
		}

	}

	public void enviarPedido() {
		MailMessage message = mailer.novaMensagem();

		message.to(this.pedido.getCliente().getEmail()).subject("Pedido " + this.pedido.getId())
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
				.put("pedido", this.pedido).put("numberTool", new NumberTool()).put("locale", new Locale("pt", "BR"))
				.send();

		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}

	public void enviarPedidoPorEmail(Pedido p) {

		Pedido pedido = pedidoService.buscarPeidoComItens(p.getId());
		
		MailMessage message = mailer.novaMensagem();

		message.to(pedido.getCliente().getEmail()).subject("Pedido " + pedido.getId())
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
				.put("pedido", pedido).put("numberTool", new NumberTool()).put("locale", new Locale("pt", "BR")).send();

		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}

}
