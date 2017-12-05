package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.ConfigEmail;
import com.br.apss.pedidovenda.service.ConfigEmailService;

@Named
@ViewScoped
public class ListaConfigEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ConfigEmailService configEmailService;

	private List<ConfigEmail> emais = new ArrayList<ConfigEmail>();

	private ConfigEmail configEmailSelecionada;

	private ConfigEmail configEmail;

	@PostConstruct
	public void inicializar() {
		listar();
	}

	public void novo() {
		configEmail = new ConfigEmail();
	}

	public void listar() {
		this.emais = configEmailService.listarTodos();
	}

	public void excluir() {
		configEmailService.excluir(configEmailSelecionada);
		listar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	/********* Getters e Setters *********/

	public List<ConfigEmail> getEmais() {
		return emais;
	}

	public void setEmais(List<ConfigEmail> emais) {
		this.emais = emais;
	}

	public ConfigEmail getConfigEmailSelecionada() {
		return configEmailSelecionada;
	}

	public void setConfigEmailSelecionada(ConfigEmail configEmailSelecionada) {
		this.configEmailSelecionada = configEmailSelecionada;
	}

	public ConfigEmail getConfigEmail() {
		return configEmail;
	}

	public void setConfigEmail(ConfigEmail configEmail) {
		this.configEmail = configEmail;
	}

}
