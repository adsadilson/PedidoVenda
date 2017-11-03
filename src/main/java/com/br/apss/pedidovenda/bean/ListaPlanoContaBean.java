package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.Status;
import com.br.apss.pedidovenda.model.PlanoConta;
import com.br.apss.pedidovenda.model.filter.PlanoContaFilter;
import com.br.apss.pedidovenda.service.PlanoContaService;

@Named
@ViewScoped
public class ListaPlanoContaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PlanoContaService planoContaService;

	private PlanoContaFilter filtro = new PlanoContaFilter();

	private List<PlanoConta> planoContas = new ArrayList<PlanoConta>();

	private PlanoConta planoContaSelecionada;

	@PostConstruct
	public void inicializar() {
		pesquisar();
	}

	public void novoFiltro() {
		filtro = new PlanoContaFilter();
	}

	public void pesquisar() {
		planoContas = planoContaService.filtrados(filtro);
	}

	public void excluir() {
		planoContaService.excluir(planoContaSelecionada);
		pesquisar();
		Messages.addGlobalInfo("Registro excluído com sucesso!");
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	/********* Getters e Setters *********/

	public PlanoContaFilter getFiltro() {
		return filtro;
	}

	public List<PlanoConta> getPlanoContas() {
		return planoContas;
	}

	public void setPlanoContas(List<PlanoConta> planoContas) {
		this.planoContas = planoContas;
	}

	public PlanoConta getPlanoContaSelecionada() {
		return planoContaSelecionada;
	}

	public void setPlanoContaSelecionada(PlanoConta planoContaSelecionada) {
		this.planoContaSelecionada = planoContaSelecionada;
	}

	public void setFiltro(PlanoContaFilter filtro) {
		this.filtro = filtro;
	}

}
