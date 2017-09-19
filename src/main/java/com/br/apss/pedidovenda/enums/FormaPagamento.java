package com.br.apss.pedidovenda.enums;

public enum FormaPagamento {

	REVENDA("REVENDA"),
	CONSUMO("CONSUMO"),
	SERVICO("SERVIÇO");
	
	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}