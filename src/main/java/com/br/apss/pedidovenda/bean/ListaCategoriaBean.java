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
import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.filter.CategoriaFilter;
import com.br.apss.pedidovenda.service.CategoriaService;

@Named
@ViewScoped
public class ListaCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaService categoriaService;

	private CategoriaFilter filtro = new CategoriaFilter();

	private List<Categoria> categorias = new ArrayList<Categoria>();

	private Categoria categoriaSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new CategoriaFilter();
	}

	public void pesquisar() {
		categorias = categoriaService.filtrados(filtro);
	}

	public void excluir() {
		categoriaService.excluir(categoriaSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public CategoriaFilter getFiltro() {
		return filtro;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public void setFiltro(CategoriaFilter filtro) {
		this.filtro = filtro;
	}

}
