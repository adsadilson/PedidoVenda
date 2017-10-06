package com.br.apss.pedidovenda.enums;

public enum TipoEndereco {

	RESIDENCIAL("RESIDENCIAL"),
	COMERCIAL("COMERCIAL"),
	COBRANCA("COBRAN�A");
	
	private String descricao;

	TipoEndereco(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}