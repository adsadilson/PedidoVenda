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
import com.br.apss.pedidovenda.model.GrupoUsuario;
import com.br.apss.pedidovenda.model.filter.GrupoUsuarioFilter;
import com.br.apss.pedidovenda.service.GrupoUsuarioService;

@Named
@ViewScoped
public class ListaGrupoUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoUsuarioService grupoUsuarioService;

	private GrupoUsuarioFilter filtro = new GrupoUsuarioFilter();

	private List<GrupoUsuario> grupoUsuarios = new ArrayList<GrupoUsuario>();

	private GrupoUsuario grupoUsuarioSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new GrupoUsuarioFilter();
	}

	public void pesquisar() {
		grupoUsuarios = grupoUsuarioService.filtrados(filtro);
	}

	public void excluir() {
		grupoUsuarioService.excluir(grupoUsuarioSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public GrupoUsuarioFilter getFiltro() {
		return filtro;
	}

	public List<GrupoUsuario> getGrupoUsuarios() {
		return grupoUsuarios;
	}

	public void setGrupoUsuarios(List<GrupoUsuario> grupoUsuarios) {
		this.grupoUsuarios = grupoUsuarios;
	}

	public GrupoUsuario getGrupoUsuarioSelecionada() {
		return grupoUsuarioSelecionada;
	}

	public void setGrupoUsuarioSelecionada(GrupoUsuario grupoUsuarioSelecionada) {
		this.grupoUsuarioSelecionada = grupoUsuarioSelecionada;
	}

	public void setFiltro(GrupoUsuarioFilter filtro) {
		this.filtro = filtro;
	}

}
