package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.GrupoUsuario;
import com.br.apss.pedidovenda.model.filter.GrupoUsuarioFilter;
import com.br.apss.pedidovenda.repository.GrupoUsuarioRepository;
import com.br.apss.pedidovenda.util.jpa.Transacional;

public class GrupoUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoUsuarioRepository dao;

	@Transacional
	public void salvar(GrupoUsuario obj) {
		dao.salvar(obj);
	}

	@Transacional
	public void excluir(GrupoUsuario obj) {
		dao.excluir(obj);
	}

	public List<GrupoUsuario> filtrados(GrupoUsuarioFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<GrupoUsuario> listarTodos() {
		return dao.listarTodos();
	}

	public GrupoUsuario porId(Long id) {
		return dao.porId(id);
	}

	public GrupoUsuario porNome(String nome) {
		return dao.porNome(nome);
	}

	public int quantidadeFiltrados(GrupoUsuarioFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
