package com.br.apss.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.apss.pedidovenda.enums.Estado;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.model.filter.PedidoFilter;
import com.br.apss.pedidovenda.repository.PedidoRepository;
import com.br.apss.pedidovenda.util.Transacional;

public class PedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository dao;

	@Transacional
	public void salvar(Pedido obj) {
		dao.salvar(obj);
	}

	@Transacional
	public void excluir(Pedido obj) {
		dao.excluir(obj);
	}

	public List<Pedido> listAll() {
		return dao.listAll();
	}

	public Pedido porId(Long id) {
		return dao.porId(id);
	}

	public Pedido porNome(String nome) {
		return dao.porNome(nome);
	}

	public Pedido porNomeEstado(String nome, Estado uf) {
		return dao.porNomeEstado(nome, uf);
	}

	public List<Pedido> buscarPorEstado(Estado estado) {
		return dao.buscarPorEstado(estado);
	}

	public List<Pedido> filtrados(PedidoFilter filtro) {
		return dao.filtrados(filtro);
	}
	
	public int qtdeFiltrados(PedidoFilter filtro) {
		return dao.qtdeFiltrados(filtro);
	}

}
