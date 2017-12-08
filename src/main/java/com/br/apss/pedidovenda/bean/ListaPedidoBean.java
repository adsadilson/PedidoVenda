package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.model.filter.PedidoFilter;
import com.br.apss.pedidovenda.service.PedidoService;
import com.br.apss.pedidovenda.util.FacesUtil;

@Named
@ViewScoped
public class ListaPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;

	private PedidoFilter filtro = new PedidoFilter();

	private LazyDataModel<Pedido> pedidos;

	private Pedido pedidoSelecionada;

	public void excluir() {
		pedidoService.excluir(this.pedidoSelecionada);
		pesquisar();
		FacesUtil.addInfoMessage("Pedido excluído com sucesso!");
	}

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void pesquisar() {

		pedidos = new LazyDataModel<Pedido>() {

			private static final long serialVersionUID = 1L;

			public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPriRegistro(first);
				filtro.setQtdeRegistros(pageSize);
				filtro.setAsc(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setCampoOrder(sortField);

				setRowCount(pedidoService.qtdeFiltrados(filtro));

				return pedidoService.filtrados(filtro);
			}

			@Override
			public Pedido getRowData(String rowKey) {
				Pedido objeto = (Pedido) pedidoService.porId(Long.parseLong(rowKey));
				return objeto;
			}

			@Override
			public String getRowKey(Pedido objeto) {
				return objeto.getId().toString();
			}

		};

	}
	
	public void enviarPedidoPorEmail(){
		new EnvioPedidoEmailBean().enviarPedidoPorEmail(this.pedidoSelecionada);	
	}

	public void novoFiltro() {
		filtro = new PedidoFilter();
	}

	/********* Getters e Setters *********/

	public LazyDataModel<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(LazyDataModel<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(PedidoFilter filtro) {
		this.filtro = filtro;
	}

	public Pedido getPedidoSelecionada() {
		return pedidoSelecionada;
	}

	public void setPedidoSelecionada(Pedido pedidoSelecionada) {
		this.pedidoSelecionada = pedidoSelecionada;
	}

}
