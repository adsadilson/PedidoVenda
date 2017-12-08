package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.filter.PessoaFilter;
import com.br.apss.pedidovenda.repository.PessoaRepository;
import com.br.apss.pedidovenda.util.jpa.Transacional;

public class PessoaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaRepository dao;

	@Transacional
	public void salvar(Pessoa obj) {
		dao.salvar(obj);
	}

	@Transacional
	public void excluir(Pessoa obj) {
		dao.excluir(obj);
	}

	public List<Pessoa> filtrados(PessoaFilter filtro) {
		return dao.filtrados(filtro);
	}

	public List<Pessoa> listarTodos() {
		return dao.listarTodos();
	}

	public Pessoa porId(Long id) {
		return dao.porId(id);
	}

	public Pessoa porNome(String nome) {
		return dao.porNome(nome);
	}
	
	public Pessoa porCpf(String cpf) {
		return dao.porCpf(cpf);
	}

	public int quantidadeFiltrados(PessoaFilter filtro) {
		return dao.quantidadeFiltrados(filtro);
	}
}
