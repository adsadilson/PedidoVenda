package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.apss.pedidovenda.model.ConfigEmail;
import com.br.apss.pedidovenda.service.ConfigEmailService;
import com.br.apss.pedidovenda.util.FacesUtil;

@Named
@ViewScoped
public class CadastroConfigEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConfigEmail configEmail = new ConfigEmail();

	@Inject
	private ConfigEmailService configEmailService;

	public void inicializar() {
		if (this.configEmail == null) {
			limpar();
		}
	}

	public void salvar() {

		/*
		 * ConfigEmail configEmailExistente =
		 * configEmailService.porNome(configEmail.getNome()); if
		 * (configEmailExistente != null &&
		 * !configEmailExistente.equals(configEmail)) { throw new
		 * NegocioException("Já existe uma Unidade de Medida com esse nome informado."
		 * ); }
		 */

		configEmailService.salvar(configEmail);
		FacesUtil.addInfoMessage("Registro salvor com sucesso.");
		// Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		configEmail = new ConfigEmail();
	}

	public void excluir() {
		configEmailService.excluir(configEmail);
		// return "lista-Unidade.xhtml?faces-redirect=true";
	}

	/*************** Getters e Setters *******************/

	public ConfigEmail getConfigEmail() {
		return configEmail;
	}

	public void setConfigEmail(ConfigEmail configEmail) {
		this.configEmail = configEmail;
	}

}
