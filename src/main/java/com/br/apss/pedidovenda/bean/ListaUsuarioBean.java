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
import com.br.apss.pedidovenda.model.Usuario;
import com.br.apss.pedidovenda.model.filter.UsuarioFilter;
import com.br.apss.pedidovenda.service.UsuarioService;

@Named
@ViewScoped
public class ListaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private UsuarioFilter filtro = new UsuarioFilter();

	private List<Usuario> usuarios = new ArrayList<Usuario>();

	private Usuario usuarioSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new UsuarioFilter();
	}

	public void pesquisar() {
		usuarios = usuarioService.filtrados(filtro);
	}

	public void excluir() {
		usuarioService.excluir(usuarioSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionada() {
		return usuarioSelecionada;
	}

	public void setUsuarioSelecionada(Usuario usuarioSelecionada) {
		this.usuarioSelecionada = usuarioSelecionada;
	}

	public void setFiltro(UsuarioFilter filtro) {
		this.filtro = filtro;
	}

}
