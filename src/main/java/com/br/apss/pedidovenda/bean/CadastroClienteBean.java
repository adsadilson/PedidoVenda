package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.apss.pedidovenda.enums.TipoEndereco;
import com.br.apss.pedidovenda.model.Endereco;
import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.service.PessoaService;
import com.br.apss.pedidovenda.util.FacesUtil;
import com.br.apss.pedidovenda.util.NegocioException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa cliente = new Pessoa();

	private Endereco novoEndereco = new Endereco();

	private Endereco enderecoSelecionado = new Endereco();

	private Long idCliente;

	private boolean editandoEnd = false;

	@Inject
	private PessoaService clienteService;

	public void inicializar() {
		if (idCliente != null) {
			cliente = clienteService.porId(idCliente);
		}
	}

	public void salvar() {

		Pessoa clienteExistente = clienteService.porCpf(cliente.getCpfCnpj());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe uma Cliente com esse CPF/CNPJ informado.");
		}

		clienteService.salvar(cliente);
		FacesUtil.addInfoMessage("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		cliente = new Pessoa();
	}

	public void excluir() {
		clienteService.excluir(cliente);
	}

	public void incluirEndereco() {
		if (cliente.getEnderecos().size() > 0) {
			if (!existeEndereco(novoEndereco)) {
				if (!editandoEnd) {
					novoEndereco.setCliente(cliente);
					cliente.getEnderecos().add(novoEndereco);
				}
				novoEndereco = new Endereco();
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} else {
				throw new NegocioException("Já existe um endereço com esse 'CEP' informado..");
			}
		} else {
			novoEndereco.setCliente(cliente);
			cliente.getEnderecos().add(novoEndereco);
			novoEndereco = new Endereco();
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
	}

	public boolean existeEndereco(Endereco novoEndereco) {
		boolean existeEndereco = false;
		for (Endereco end : this.cliente.getEnderecos()) {
			if (novoEndereco.getCep().contains(end.getCep())) {
				if (!editandoEnd) {
					existeEndereco = true;
				}
				break;
			}
		}
		return existeEndereco;
	}

	public void removerEndereco() {
		this.cliente.getEnderecos().remove(enderecoSelecionado);
		FacesUtil.addInfoMessage("Endereco excluído com sucesso!");
		prepararNovoEndereco();
	}

	public void prepararNovoEndereco() {
		this.novoEndereco = new Endereco();
		this.editandoEnd = false;
	}

	public void prepararEdicaoEndereco() {
		this.editandoEnd = true;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Endereco getNovoEndereco() {
		return novoEndereco;
	}

	public List<TipoEndereco> getTipoEnderecos() {
		return Arrays.asList(TipoEndereco.values());
	}

	public void setNovoEndereco(Endereco novoEndereco) {
		this.novoEndereco = novoEndereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}

	public boolean isEditandoEnd() {
		return editandoEnd;
	}

	public void setEditandoEnd(boolean editandoEnd) {
		this.editandoEnd = editandoEnd;
	}

}
