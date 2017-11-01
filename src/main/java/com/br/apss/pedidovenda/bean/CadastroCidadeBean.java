package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.Estado;
import com.br.apss.pedidovenda.model.Cidade;
import com.br.apss.pedidovenda.service.CidadeService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroCidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cidade cidade = new Cidade();

	@Inject
	private CidadeService cidadeService;

	public void inicializar() {
		if (this.cidade == null) {
			limpar();
		}
	}

	public void salvar() {

		Cidade cidadeExistente = cidadeService.porNomeEstado(cidade.getNome(),cidade.getUf());
		if (cidadeExistente != null && !cidadeExistente.equals(cidade)) {
			throw new NegocioException("Cidade já cadastrada para esse Estado informado.");
		}

		cidadeService.salvar(cidade);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		cidade = new Cidade();
	}

	public List<Estado> getEstados() {
		return Arrays.asList(Estado.values());
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
