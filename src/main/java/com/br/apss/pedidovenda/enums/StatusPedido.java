package com.br.apss.pedidovenda.enums;

public enum StatusPedido {

	REVENDA("REVENDA"),
	CONSUMO("CONSUMO"),
	SERVICO("SERVIÇO");
	
	private String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}