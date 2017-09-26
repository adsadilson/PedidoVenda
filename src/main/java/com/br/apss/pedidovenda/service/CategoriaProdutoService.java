package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.CategoriaProduto;
import com.br.apss.pedidovenda.model.filter.CategoriaProdutoFilter;
import com.br.apss.pedidovenda.repository.CategoriaProdutoRepository;
import com.br.apss.pedidovenda.util.Transacional;

public class CategoriaProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaProdutoRepository dao;

	@Transacional
	public void salvar(CategoriaProduto tarefa) {
		dao.salvar(tarefa);
	}

	@Transacional
	public void excluir(CategoriaProduto tarefa) {
		dao.excluir(tarefa);
	}

	public List<CategoriaProduto> filtrados(CategoriaProdutoFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<CategoriaProduto> listarTodos() {
		return dao.listarTodos();
	}

	public CategoriaProduto porId(Long id) {
		return dao.porId(id);
	}

	public CategoriaProduto porNome(String nome) {
		return dao.porNome(nome);
	}

	public int quantidadeFiltrados(CategoriaProdutoFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
