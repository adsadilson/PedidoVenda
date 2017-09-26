package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import com.br.apss.pedidovenda.model.UnidadeMedida;
import com.br.apss.pedidovenda.service.UnidadeMedidaService;
import com.br.apss.pedidovenda.util.FacesUtil;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroUnidadeMedidaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UnidadeMedida unidade = new UnidadeMedida();

	private Long idUnidadeMedida;

	@Inject
	private UnidadeMedidaService unidadeMedidaService;

	public void inicializar() {
		if (idUnidadeMedida != null) {
			unidade = unidadeMedidaService.porId(idUnidadeMedida);
		}
	}

	public void salvar() {
		
		UnidadeMedida unidadeMedidaExistente = unidadeMedidaService.porNome(unidade.getNome());
		if (unidadeMedidaExistente != null && !unidadeMedidaExistente.equals(unidade)) {
			throw new NegocioException("Já existe uma Unidade de Medida com esse nome informado.");
		}
		
		unidadeMedidaService.salvar(unidade);
		FacesUtil.addInfoMessage("Registro salvor com sucesso.");
		//Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		unidade = new UnidadeMedida();
	}

	public void excluir() {
		unidadeMedidaService.excluir(unidade);
		// return "lista-UnidadeMedida.xhtml?faces-redirect=true";
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidade;
	}

	public void setUnidadeMedida(UnidadeMedida unidade) {
		this.unidade = unidade;
	}

	public Long getIdUnidadeMedida() {
		return idUnidadeMedida;
	}

	public void setIdUnidadeMedida(Long idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}

}
