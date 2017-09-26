package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.Status;
import com.br.apss.pedidovenda.model.CategoriaProduto;
import com.br.apss.pedidovenda.model.filter.CategoriaProdutoFilter;
import com.br.apss.pedidovenda.service.CategoriaProdutoService;

@Named
@ViewScoped
public class ListaCategoriaProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaProdutoService categoriaProdutoService;

	private CategoriaProdutoFilter filtro = new CategoriaProdutoFilter();

	private List<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();

	private CategoriaProduto categoriaProdutoSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new CategoriaProdutoFilter();
	}

	public void pesquisar() {
		categorias = categoriaProdutoService.filtrados(filtro);
	}

	public void excluir() {
		categoriaProdutoService.excluir(categoriaProdutoSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public CategoriaProdutoFilter getFiltro() {
		return filtro;
	}

	public List<CategoriaProduto> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaProduto> categorias) {
		this.categorias = categorias;
	}

	public CategoriaProduto getCategoriaProdutoSelecionada() {
		return categoriaProdutoSelecionada;
	}

	public void setCategoriaProdutoSelecionada(CategoriaProduto categoriaProdutoSelecionada) {
		this.categoriaProdutoSelecionada = categoriaProdutoSelecionada;
	}

	public void setFiltro(CategoriaProdutoFilter filtro) {
		this.filtro = filtro;
	}

}
