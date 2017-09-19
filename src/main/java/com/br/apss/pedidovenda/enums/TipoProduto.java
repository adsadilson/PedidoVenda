package com.br.apss.pedidovenda.enums;

public enum TipoProduto {

	REVENDA("REVENDA"),
	CONSUMO("CONSUMO"),
	SERVICO("SERVIÇO");
	
	private String descricao;

	TipoProduto(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}