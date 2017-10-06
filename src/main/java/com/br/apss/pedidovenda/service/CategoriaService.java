package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.filter.CategoriaFilter;
import com.br.apss.pedidovenda.repository.CategoriaRepository;
import com.br.apss.pedidovenda.util.Transacional;

public class CategoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaRepository dao;

	@Transacional
	public void salvar(Categoria obj) {
		dao.salvar(obj);
	}

	@Transacional
	public void excluir(Categoria obj) {
		dao.excluir(obj);
	}

	public List<Categoria> filtrados(CategoriaFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<Categoria> listarTodos() {
		return dao.listarTodos();
	}

	public Categoria porId(Long id) {
		return dao.porId(id);
	}

	public Categoria porNome(String nome) {
		return dao.porNome(nome);
	}

	public int quantidadeFiltrados(CategoriaFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
