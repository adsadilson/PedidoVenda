package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.br.apss.pedidovenda.enums.Status;
import com.br.apss.pedidovenda.model.Produto;
import com.br.apss.pedidovenda.model.filter.ProdutoFilter;
import com.br.apss.pedidovenda.service.ProdutoService;

@Named
@ViewScoped
public class ListaProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoService produtoService;

	private ProdutoFilter filtro = new ProdutoFilter();

	private LazyDataModel<Produto> dataModel;

	private Produto produtoSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new ProdutoFilter();
	}

	public void pesquisar() {
		dataModel = new LazyDataModel<Produto>() {

			private static final long serialVersionUID = 1L;

			public List<Produto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setCampoOrdenacao(sortField);

				setRowCount(produtoService.quantidadeFiltrados(filtro));

				return produtoService.filtrados(filtro);
			}

			@Override
			public Produto getRowData(String rowKey) {
				Produto objeto = (Produto) produtoService.porId(Long.parseLong(rowKey));
				return objeto;
			}

			@Override
			public String getRowKey(Produto objeto) {
				return objeto.getId().toString();
			}

		};
	}

	public void excluir() {
		produtoService.excluir(produtoSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<Produto> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Produto> dataModel) {
		this.dataModel = dataModel;
	}

	public Produto getProdutoSelecionada() {
		return produtoSelecionada;
	}

	public void setProdutoSelecionada(Produto produtoSelecionada) {
		this.produtoSelecionada = produtoSelecionada;
	}

	public void setFiltro(ProdutoFilter filtro) {
		this.filtro = filtro;
	}


}
