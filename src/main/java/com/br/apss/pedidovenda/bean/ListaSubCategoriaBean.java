package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.SubCategoria;
import com.br.apss.pedidovenda.model.filter.SubCategoriaFilter;
import com.br.apss.pedidovenda.service.SubCategoriaService;

@Named
@ViewScoped
public class ListaSubCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SubCategoriaService subCategoriaService;

	private SubCategoriaFilter filtro = new SubCategoriaFilter();

	private List<SubCategoria> subCategorias = new ArrayList<SubCategoria>();

	private SubCategoria subCategoriaSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new SubCategoriaFilter();
	}

	public void pesquisar() {
		subCategorias = subCategoriaService.filtrados(filtro);
	}

	public void excluir() {
		subCategoriaService.excluir(subCategoriaSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	/********* Getters e Setters *********/

	public SubCategoriaFilter getFiltro() {
		return filtro;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public SubCategoria getSubCategoriaSelecionada() {
		return subCategoriaSelecionada;
	}

	public void setSubCategoriaSelecionada(SubCategoria subCategoriaSelecionada) {
		this.subCategoriaSelecionada = subCategoriaSelecionada;
	}

	public void setFiltro(SubCategoriaFilter filtro) {
		this.filtro = filtro;
	}

}
