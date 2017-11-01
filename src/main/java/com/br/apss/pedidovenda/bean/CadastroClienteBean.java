package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.apss.pedidovenda.enums.Estado;
import com.br.apss.pedidovenda.enums.TipoEndereco;
import com.br.apss.pedidovenda.enums.TipoTelefone;
import com.br.apss.pedidovenda.model.Cidade;
import com.br.apss.pedidovenda.model.Endereco;
import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.Telefone;
import com.br.apss.pedidovenda.service.CidadeService;
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

	private Telefone novoTelefone = new Telefone();

	private Telefone telefoneSelecionado = new Telefone();

	private Endereco enderecoSelecionado = new Endereco();
	
	private List<Cidade> cidades;

	private Long idCliente;

	private boolean editandoEnd = false;

	private boolean editandoFone = false;

	@Inject
	private PessoaService clienteService;
	
	@Inject
	private CidadeService cidadeService;

	public void inicializar() {
		if (idCliente != null) {
			cliente = clienteService.porId(idCliente);
		}
	}

	public void carregarCidadesPorEstados() {
		if (null != this.novoEndereco.getUf()) {
			cidades = cidadeService.buscarPorEstado(this.novoEndereco.getUf());
		} else {
			cidades = new ArrayList<Cidade>();
		}
		
	}

	public void salvar() {

		Pessoa clienteExistente = clienteService.porCpf(cliente.getCpfCnpj());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe uma Cliente com esse CPF/CNPJ informado.");
		}

		cliente.setCliente(true);
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
					novoEndereco.setPessoa(cliente);
					cliente.getEnderecos().add(novoEndereco);
				}
				novoEndereco = new Endereco();
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} else {
				throw new NegocioException("Já existe um endere�o com esse 'CEP' informado..");
			}
		} else {
			novoEndereco.setPessoa(cliente);
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
		for (int i = 0; i < this.getCliente().getEnderecos().size(); i++) {
			if (this.getCliente().getEnderecos().get(i).getCep() == enderecoSelecionado.getCep()) {
				this.cliente.getEnderecos().remove(i);
			}
		}
		prepararNovoEndereco();
	}

	public void prepararNovoEndereco() {
		this.novoEndereco = new Endereco();
		this.editandoEnd = false;
	}

	public void incluirTelefone() {
		if (cliente.getTelefones().size() > 0) {
			if (!existeTelefone(novoTelefone)) {
				if (!editandoFone) {
					novoTelefone.setPessoa(cliente);
					cliente.getTelefones().add(novoTelefone);
				}
				novoTelefone = new Telefone();
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} else {
				throw new NegocioException("J� existe um telefone com esse 'N�MERO' informado..");
			}
		} else {
			novoTelefone.setPessoa(cliente);
			cliente.getTelefones().add(novoTelefone);
			novoTelefone = new Telefone();
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
	}

	public boolean existeTelefone(Telefone fone) {
		boolean existeTelefone = false;
		for (Telefone telefone : this.cliente.getTelefones()) {
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
		for (int i = 0; i < this.getCliente().getTelefones().size(); i++) {
			if (this.getCliente().getTelefones().get(i).getNumero() == telefoneSelecionado.getNumero()) {
				this.cliente.getTelefones().remove(i);
			}
		}
		prepararNovoTelefone();
	}

	private void prepararNovoTelefone() {
		this.novoTelefone = new Telefone();
		this.editandoFone = false;
	}

	public void prepararEdicaoEndereco() {
		carregarCidadesPorEstados();
		this.editandoEnd = true;
	}

	public void prepararEdicaoTelefone() {
		this.editandoFone = true;
	}

	public List<Estado> getEstados() {
		return Arrays.asList(Estado.values());
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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	

}
