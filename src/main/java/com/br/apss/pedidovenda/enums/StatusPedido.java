package com.br.apss.pedidovenda.enums;

public enum StatusPedido {

	ORCAMENTO("ORÇAMENTO"), 
	EMITIDO("EMITIDO"), 
	CANCELADO("CANCELADO");

	private String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}