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

import com.br.apss.pedidovenda.model.Cidade;
import com.br.apss.pedidovenda.model.filter.CidadeFilter;
import com.br.apss.pedidovenda.service.CidadeService;
import com.br.apss.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ListaCidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeService cidadeService;

	private CidadeFilter filtro = new CidadeFilter();

	private LazyDataModel<Cidade> cidades;

	private Cidade cidadeSelecionada;

	public void excluir() {
		cidadeService.excluir(this.cidadeSelecionada);
		pesquisar();
		FacesUtil.addInfoMessage("Cidade excluído com sucesso!");
	}

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void pesquisar() {

		cidades = new LazyDataModel<Cidade>() {

			private static final long serialVersionUID = 1L;

			public List<Cidade> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPriRegistro(first);
				filtro.setQtdeRegistros(pageSize);
				filtro.setAsc(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setCampoOrder(sortField);

				setRowCount(cidadeService.qtdeFiltrados(filtro));

				return cidadeService.filtrados(filtro);
			}

			@Override
			public Cidade getRowData(String rowKey) {
				Cidade objeto = (Cidade) cidadeService.porId(Long.parseLong(rowKey));
				return objeto;
			}

			@Override
			public String getRowKey(Cidade objeto) {
				return objeto.getId().toString();
			}

		};

	}

	public void novoFiltro() {
		filtro = new CidadeFilter();
	}

	/********* Getters e Setters *********/

	public LazyDataModel<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(LazyDataModel<Cidade> cidades) {
		this.cidades = cidades;
	}

	public CidadeFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(CidadeFilter filtro) {
		this.filtro = filtro;
	}

	public Cidade getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(Cidade cidadeSelecionada) {
		this.cidadeSelecionada = cidadeSelecionada;
	}

}
