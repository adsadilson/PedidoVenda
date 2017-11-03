package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.TipoConta;
import com.br.apss.pedidovenda.model.PlanoConta;
import com.br.apss.pedidovenda.service.PlanoContaService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroPlanoContaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PlanoConta planoConta = new PlanoConta();

	@Inject
	private PlanoContaService planoContaService;

	public void inicializar() {
		if (this.planoConta == null) {
			limpar();
		}
	}

	public void salvar() {

		PlanoConta planoContaExistente = planoContaService.porNomeTipo(planoConta.getNome(), planoConta.getTipo());
		if (planoContaExistente != null && !planoContaExistente.equals(planoConta)) {
			throw new NegocioException("Plano Conta já cadastrada para esse Tipo informado.");
		}

		planoContaService.salvar(planoConta);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		planoConta = new PlanoConta();
	}

	public List<TipoConta> getTipos() {
		return Arrays.asList(TipoConta.values());
	}

	public PlanoConta getPlanoConta() {
		return planoConta;
	}

	public void setPlanoConta(PlanoConta planoConta) {
		this.planoConta = planoConta;
	}

}
