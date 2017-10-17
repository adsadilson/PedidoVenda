package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.apss.pedidovenda.enums.TipoEndereco;
import com.br.apss.pedidovenda.enums.TipoTelefone;
import com.br.apss.pedidovenda.model.Endereco;
import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.Telefone;
import com.br.apss.pedidovenda.service.PessoaService;
import com.br.apss.pedidovenda.util.FacesUtil;
import com.br.apss.pedidovenda.util.NegocioException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadastroFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa fornecedor = new Pessoa();

	private Endereco novoEndereco = new Endereco();

	private Telefone novoTelefone = new Telefone();

	private Telefone telefoneSelecionado = new Telefone();

	private Endereco enderecoSelecionado = new Endereco();

	private Long idFornecedor;

	private boolean editandoEnd = false;

	private boolean editandoFone = false;

	@Inject
	private PessoaService fornecedorService;

	public void inicializar() {
		if (idFornecedor != null) {
			fornecedor = fornecedorService.porId(idFornecedor);
			for (int i = 0; i < fornecedor.getEnderecos().size(); i++) {
				novoEndereco.setLogradouro(fornecedor.getEnderecos().get(i).getLogradouro());
				novoEndereco.setNumero((fornecedor.getEnderecos().get(i).getNumero()));
				novoEndereco.setComplemento(fornecedor.getEnderecos().get(i).getComplemento());
				novoEndereco.setBairro(fornecedor.getEnderecos().get(i).getBairro());
				novoEndereco.setCidade(fornecedor.getEnderecos().get(i).getCidade());
				novoEndereco.setCep(fornecedor.getEnderecos().get(i).getCep());
				novoEndereco.setUf(fornecedor.getEnderecos().get(i).getUf());
			}
				
		}
	}

	public void salvar() {

		Pessoa fornecedorExistente = fornecedorService.porCpf(fornecedor.getCpfCnpj());
		if (fornecedorExistente != null && !fornecedorExistente.equals(fornecedor)) {
			throw new NegocioException("Já existe uma Fornecedor com esse CPF/CNPJ informado.");
		}
		
		novoEndereco.setPessoa(fornecedor);
		List<Endereco> end = new ArrayList<>();
		end.add(novoEndereco);
		fornecedor.setEnderecos(end);
		fornecedor.setFornecedor(true);
		fornecedorService.salvar(fornecedor);
		FacesUtil.addInfoMessage("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		fornecedor = new Pessoa();
		novoEndereco = new Endereco();
	}

	public void excluir() {
		fornecedorService.excluir(fornecedor);
	}

	public void incluirEndereco() {
		if (fornecedor.getEnderecos().size() > 0) {
			if (!existeEndereco(novoEndereco)) {
				if (!editandoEnd) {
					novoEndereco.setPessoa(fornecedor);
					fornecedor.getEnderecos().add(novoEndereco);
				}
				novoEndereco = new Endereco();
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} else {
				throw new NegocioException("J� existe um endere�o com esse 'CEP' informado..");
			}
		} else {
			novoEndereco.setPessoa(fornecedor);
			fornecedor.getEnderecos().add(novoEndereco);
			novoEndereco = new Endereco();
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
	}

	public boolean existeEndereco(Endereco novoEndereco) {
		boolean existeEndereco = false;
		for (Endereco end : this.fornecedor.getEnderecos()) {
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
		for (int i = 0; i < this.getFornecedor().getEnderecos().size(); i++) {
			if (this.getFornecedor().getEnderecos().get(i).getCep() == enderecoSelecionado.getCep()) {
				this.fornecedor.getEnderecos().remove(i);
			}
		}
		prepararNovoEndereco();
	}

	public void prepararNovoEndereco() {
		this.novoEndereco = new Endereco();
		this.editandoEnd = false;
	}

	public void incluirTelefone() {
		if (fornecedor.getTelefones().size() > 0) {
			if (!existeTelefone(novoTelefone)) {
				if (!editandoFone) {
					novoTelefone.setPessoa(fornecedor);
					fornecedor.getTelefones().add(novoTelefone);
				}
				novoTelefone = new Telefone();
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} else {
				throw new NegocioException("J� existe um telefone com esse 'N�MERO' informado..");
			}
		} else {
			novoTelefone.setPessoa(fornecedor);
			fornecedor.getTelefones().add(novoTelefone);
			novoTelefone = new Telefone();
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
	}

	public boolean existeTelefone(Telefone fone) {
		boolean existeTelefone = false;
		for (Telefone telefone : this.fornecedor.getTelefones()) {
			if (fone.getNumero().contains(telefone.getNumero())) {
				if (!editandoFone) {
					existeTelefone = true;
				}
				break;
			}
		}
		return existeTelefone;
	}

	public void removerTelefone() {
		for (int i = 0; i < this.getFornecedor().getTelefones().size(); i++) {
			if (this.getFornecedor().getTelefones().get(i).getNumero() == telefoneSelecionado.getNumero()) {
				this.fornecedor.getTelefones().remove(i);
			}
		}
		prepararNovoTelefone();
	}

	private void prepararNovoTelefone() {
		this.novoTelefone = new Telefone();
		this.editandoFone = false;
	}

	public void prepararEdicaoEndereco() {
		this.editandoEnd = true;
	}

	public void prepararEdicaoTelefone() {
		this.editandoFone = true;
	}

	public Pessoa getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Pessoa fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public Endereco getNovoEndereco() {
		return novoEndereco;
	}

	public List<TipoEndereco> getTipoEnderecos() {
		return Arrays.asList(TipoEndereco.values());
	}

	public List<TipoTelefone> getTipoTelefones() {
		return Arrays.asList(TipoTelefone.values());
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

	public Telefone getNovoTelefone() {
		return novoTelefone;
	}

	public void setNovoTelefone(Telefone novoTelefone) {
		this.novoTelefone = novoTelefone;
	}

	public boolean isEditandoFone() {
		return editandoFone;
	}

	public void setEditandoFone(boolean editandoFone) {
		this.editandoFone = editandoFone;
	}

	public Telefone getTelefoneSelecionado() {
		return telefoneSelecionado;
	}

	public void setTelefoneSelecionado(Telefone telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}

}
