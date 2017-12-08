package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.UnidadeMedida;
import com.br.apss.pedidovenda.model.filter.UnidadeMedidaFilter;
import com.br.apss.pedidovenda.repository.UnidadeMedidaRepository;
import com.br.apss.pedidovenda.util.jpa.Transacional;

public class UnidadeMedidaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeMedidaRepository dao;

	@Transacional
	public void salvar(UnidadeMedida tarefa) {
		dao.salvar(tarefa);
	}

	@Transacional
	public void excluir(UnidadeMedida tarefa) {
		dao.excluir(tarefa);
	}

	public List<UnidadeMedida> filtrados(UnidadeMedidaFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<UnidadeMedida> listarTodos() {
		return dao.listarTodos();
	}

	public UnidadeMedida porId(Long id) {
		return dao.porId(id);
	}

	public UnidadeMedida porNome(String nome) {
		return dao.porNome(nome);
	}

	public int quantidadeFiltrados(UnidadeMedidaFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
