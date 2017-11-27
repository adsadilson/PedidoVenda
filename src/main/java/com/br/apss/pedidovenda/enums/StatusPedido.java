package com.br.apss.pedidovenda.enums;

public enum StatusPedido {

	ORCAMENTO("OR�AMENTO"), 
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