package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.Produto;
import com.br.apss.pedidovenda.model.filter.ProdutoFilter;
import com.br.apss.pedidovenda.repository.ProdutoRepository;
import com.br.apss.pedidovenda.util.Transacional;

public class ProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository dao;

	@Transacional
	public void salvar(Produto tarefa) {
		dao.salvar(tarefa);
	}

	@Transacional
	public void excluir(Produto tarefa) {
		dao.excluir(tarefa);
	}

	public List<Produto> filtrados(ProdutoFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<Produto> listarTodos() {
		return dao.listarTodos();
	}

	public Produto porId(Long id) {
		return dao.porId(id);
	}

	public Produto porNome(String nome) {
		return dao.porNome(nome);
	}

	public int quantidadeFiltrados(ProdutoFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
