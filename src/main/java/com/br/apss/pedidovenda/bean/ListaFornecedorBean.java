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
import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.filter.PessoaFilter;
import com.br.apss.pedidovenda.service.PessoaService;

@Named
@ViewScoped
public class ListaFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaService fornecedorService;

	private PessoaFilter filtro = new PessoaFilter();

	private LazyDataModel<Pessoa> dataModel;

	private Pessoa fornecedorSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new PessoaFilter();
	}

	public void pesquisar() {
		dataModel = new LazyDataModel<Pessoa>() {

			private static final long serialVersionUID = 1L;

			public List<Pessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setCampoOrdenacao(sortField);
				filtro.setFonecedor(true);

				setRowCount(fornecedorService.quantidadeFiltrados(filtro));

				return fornecedorService.filtrados(filtro);
			}

			@Override
			public Pessoa getRowData(String rowKey) {
				Pessoa objeto = (Pessoa) fornecedorService.porId(Long.parseLong(rowKey));
				return objeto;
			}

			@Override
			public String getRowKey(Pessoa objeto) {
				return objeto.getId().toString();
			}

		};
	}

	public void excluir() {
		fornecedorService.excluir(fornecedorSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro exclu�do com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public PessoaFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<Pessoa> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Pessoa> dataModel) {
		this.dataModel = dataModel;
	}

	public Pessoa getFornecedorSelecionada() {
		return fornecedorSelecionada;
	}

	public void setFornecedorSelecionada(Pessoa fornecedorSelecionada) {
		this.fornecedorSelecionada = fornecedorSelecionada;
	}

	public void setFiltro(PessoaFilter filtro) {
		this.filtro = filtro;
	}

}
