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
public class CadastroUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UnidadeMedida unidade = new UnidadeMedida();

	private Long idUnidade;

	@Inject
	private UnidadeMedidaService unidadeService;

	public void inicializar() {
		if (idUnidade != null) {
			unidade = unidadeService.porId(idUnidade);
		}
	}

	public void salvar() {
		
		UnidadeMedida unidadeExistente = unidadeService.porNome(unidade.getNome());
		if (unidadeExistente != null && !unidadeExistente.equals(unidade)) {
			throw new NegocioException("Já existe uma Unidade de Medida com esse nome informado.");
		}
		
		unidadeService.salvar(unidade);
		FacesUtil.addInfoMessage("Registro salvor com sucesso.");
		//Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		unidade = new UnidadeMedida();
	}

	public void excluir() {
		unidadeService.excluir(unidade);
		// return "lista-Unidade.xhtml?faces-redirect=true";
	}

	public UnidadeMedida getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeMedida unidade) {
		this.unidade = unidade;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

}
