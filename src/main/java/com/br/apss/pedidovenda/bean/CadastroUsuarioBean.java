package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.GrupoUsuario;
import com.br.apss.pedidovenda.model.Usuario;
import com.br.apss.pedidovenda.service.GrupoUsuarioService;
import com.br.apss.pedidovenda.service.UsuarioService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private GrupoUsuario grupo = new GrupoUsuario();

	private GrupoUsuario grupoSelecionado = new GrupoUsuario();

	private List<GrupoUsuario> grupos = new ArrayList<GrupoUsuario>();

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private GrupoUsuarioService grupoUsuarioService;

	public void inicializar() {
		if (this.usuario == null) {
			limpar();
		}
		this.grupos = grupoUsuarioService.listarTodos();
	}

	public void salvar() {
		Usuario usuarioExistente = usuarioService.porEmail(usuario.getEmail());
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("J� existe uma Usu�rio com esse 'E-MAIL' informado.");
		}
		usuarioService.salvar(usuario);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();
	}

	private void limpar() {
		this.usuario = new Usuario();
	}

	public void excluir() {
		usuarioService.excluir(usuario);
		// return "lista-Unidade.xhtml?faces-redirect=true";
	}

	public void adicionarGrupo() {
		if (this.grupo != null) {
			if (this.usuario.getGrupos().contains(this.grupo)) {
				throw new NegocioException("Grupo já cadastrado.");
			}
			this.usuario.getGrupos().add(this.grupo);
			this.grupo = null;
		} else {
			Messages.addGlobalWarn("Selecione um grupo primeiro.");
		}
	}
	
	public void removeGrupo(){
		this.usuario.getGrupos().remove(this.grupoSelecionado);
	}

	/********* Getters e Setters *************/

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public GrupoUsuario getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoUsuario grupo) {
		this.grupo = grupo;
	}

	public List<GrupoUsuario> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoUsuario> grupos) {
		this.grupos = grupos;
	}

	public GrupoUsuario getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(GrupoUsuario grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

}
