package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.GrupoUsuario;
import com.br.apss.pedidovenda.service.GrupoUsuarioService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroGrupoUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private GrupoUsuario grupoUsuario = new GrupoUsuario();

	@Inject
	private GrupoUsuarioService grupoUsuarioService;

	public void inicializar() {
		if (this.grupoUsuario == null) {
			limpar();
		}
	}

	public void salvar() {

		GrupoUsuario grupoUsuarioExistente = grupoUsuarioService.porNome(grupoUsuario.getNome());
		if (grupoUsuarioExistente != null && !grupoUsuarioExistente.equals(grupoUsuario)) {
			throw new NegocioException("Já existe um Grupo de Usuario com esse nome informado.");
		}

		grupoUsuarioService.salvar(grupoUsuario);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		grupoUsuario = new GrupoUsuario();
	}

	public void excluir() {
		grupoUsuarioService.excluir(grupoUsuario);
		// return "lista-GrupoUsuarioProduto.xhtml?faces-redirect=true";
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

}
