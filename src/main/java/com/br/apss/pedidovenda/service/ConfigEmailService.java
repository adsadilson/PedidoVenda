package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.model.ConfigEmail;
import com.br.apss.pedidovenda.repository.ConfigEmailRepository;
import com.br.apss.pedidovenda.util.jpa.Transacional;

public class ConfigEmailService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ConfigEmailRepository dao;

	@Transacional
	public void salvar(ConfigEmail tarefa) {
		dao.salvar(tarefa);
	}

	@Transacional
	public void excluir(ConfigEmail tarefa) {
		dao.excluir(tarefa);
	}


	public List<ConfigEmail> listarTodos() {
		return dao.listarTodos();
	}

	public ConfigEmail porId(Long id) {
		return dao.porId(id);
	}
	
	public ConfigEmail emailEmUso() {
		return dao.emailEmUso();
	}

}
