package com.br.apss.pedidovenda.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.br.apss.pedidovenda.model.Usuario;

@Named
@RequestScoped
public class Seguranca {

	public Usuario getUsuarioLogado() {
		Usuario user = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado2();

		if (usuarioLogado != null) {
			user = usuarioLogado.getUsuario();
		}

		return user;
	}

	private UsuarioSistema getUsuarioLogado2() {
		UsuarioSistema usuario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario;
	}

}
