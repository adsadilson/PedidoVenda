package com.br.apss.pedidovenda.model.filter;

import com.br.apss.pedidovenda.enums.TipoConta;

public class PlanoContaFilter {

	private String nome;
	private TipoConta tipo;
	private Boolean status;
	private String origem;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setOrigem(String origem) {
		if (origem.equals("principal")) {
			this.status = null;
			this.tipo = null;
		}
		this.origem = origem;
	}

	public String getOrigem() {
		return origem;
	}

	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}

}
