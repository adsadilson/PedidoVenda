package com.br.apss.pedidovenda.enums;

public enum StatusPedido {

	REVENDA("REVENDA"),
	CONSUMO("CONSUMO"),
	SERVICO("SERVI�O");
	
	private String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}