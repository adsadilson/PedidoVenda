package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.Estado;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.service.PedidoService;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();

	@Inject
	private PedidoService pedidoService;

	public void inicializar() {
		if (this.pedido == null) {
			limpar();
		}
	}

	public void salvar() {
		pedidoService.salvar(pedido);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		pedido = new Pedido();
	}

	public List<Estado> getEstados() {
		return Arrays.asList(Estado.values());
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
