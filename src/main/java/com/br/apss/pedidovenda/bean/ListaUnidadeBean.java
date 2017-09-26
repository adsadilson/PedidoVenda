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
import com.br.apss.pedidovenda.model.UnidadeMedida;
import com.br.apss.pedidovenda.model.filter.UnidadeMedidaFilter;
import com.br.apss.pedidovenda.service.UnidadeMedidaService;

@Named
@ViewScoped
public class ListaUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeMedidaService unidadeService;

	private UnidadeMedidaFilter filtro = new UnidadeMedidaFilter();

	private LazyDataModel<UnidadeMedida> dataModel;

	private UnidadeMedida unidadeSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new UnidadeMedidaFilter();
	}

	public void pesquisar() {
		dataModel = new LazyDataModel<UnidadeMedida>() {

			private static final long serialVersionUID = 1L;

			public List<UnidadeMedida> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setCampoOrdenacao(sortField);

				setRowCount(unidadeService.quantidadeFiltrados(filtro));

				return unidadeService.filtrados(filtro);
			}

			@Override
			public UnidadeMedida getRowData(String rowKey) {
				UnidadeMedida objeto = (UnidadeMedida) unidadeService.porId(Long.parseLong(rowKey));
				return objeto;
			}

			@Override
			public String getRowKey(UnidadeMedida objeto) {
				return objeto.getId().toString();
			}

		};
	}

	public void excluir() {
		unidadeService.excluir(unidadeSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public UnidadeMedidaFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<UnidadeMedida> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UnidadeMedida> dataModel) {
		this.dataModel = dataModel;
	}

	public UnidadeMedida getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(UnidadeMedida unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public void setFiltro(UnidadeMedidaFilter filtro) {
		this.filtro = filtro;
	}


}
