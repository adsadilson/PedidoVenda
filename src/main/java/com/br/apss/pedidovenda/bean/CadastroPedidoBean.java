package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;

import com.br.apss.pedidovenda.enums.Estado;
import com.br.apss.pedidovenda.enums.FormaPagamento;
import com.br.apss.pedidovenda.model.Cidade;
import com.br.apss.pedidovenda.model.ItemPedido;
import com.br.apss.pedidovenda.model.Pedido;
import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.Produto;
import com.br.apss.pedidovenda.model.Usuario;
import com.br.apss.pedidovenda.model.filter.PessoaFilter;
import com.br.apss.pedidovenda.model.filter.ProdutoFilter;
import com.br.apss.pedidovenda.service.CidadeService;
import com.br.apss.pedidovenda.service.PedidoService;
import com.br.apss.pedidovenda.service.PessoaService;
import com.br.apss.pedidovenda.service.ProdutoService;
import com.br.apss.pedidovenda.service.UsuarioService;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;

	@Inject
	private UsuarioService vendedorService;

	@Inject
	private PessoaService clienteService;

	@Inject
	private CidadeService cidadeService;

	@Inject
	private ProdutoService produtoService;

	private Pedido pedido;

	private String codigoBarra;

	private BigDecimal vlrUnit;

	private BigDecimal qtdeEstoque;

	private String descricao;

	private Produto produtoLinhaEditavel;

	private boolean skip;

	private boolean achou = false;

	private List<Cidade> cidades;

	public void inicializar() {
		if (this.pedido == null) {
			limpar();
		}
		this.carregarCidadesPorEstados();
		this.recalcularPedido();
		this.pedido.adicionarItemVazio();
	}

	public void salvar() {
		pedidoService.salvar(pedido);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();
	}

	private void limpar() {
		pedido = new Pedido();
	}

	public FormaPagamento[] getFormaPagto() {
		return FormaPagamento.values();
	}

	public List<Pessoa> completarCliente(String nome) {
		PessoaFilter filtroCliente = new PessoaFilter();
		filtroCliente.setCliente(true);
		filtroCliente.setOrigem("principal");
		filtroCliente.setNome(nome);
		return clienteService.filtrados(filtroCliente);
	}

	public List<Usuario> getVendedores() {
		return vendedorService.listarTodos();
	}

	public List<Estado> getEstados() {
		return Arrays.asList(Estado.values());
	}

	public void carregarCidadesPorEstados() {
		if (null != this.pedido.getEnderecoEntrega().getUf()) {
			cidades = cidadeService.buscarPorEstado(this.pedido.getEnderecoEntrega().getUf());
		} else {
			cidades = new ArrayList<Cidade>();
		}
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}

	public void carregarProdutoPesquisado() {
		if (null != this.codigoBarra || this.produtoLinhaEditavel != null) {
			if (null != this.codigoBarra) {
				this.produtoLinhaEditavel = this.produtoService.porCodigoBarra(this.codigoBarra);
			}
			if (null != this.produtoLinhaEditavel) {
				this.achou = true;
				this.getProdutoLinhaEditavel().getNome();
				this.setVlrUnit(this.getProdutoLinhaEditavel().getVlrVenda());
				this.setQtdeEstoque(this.produtoLinhaEditavel.getQuantidade());
				this.setCodigoBarra(this.getProdutoLinhaEditavel().getCodigoBarra());
			} else {
				this.achou = false;
				Messages.addGlobalInfo("Produto não localizado.");
			}
			// this.carregarProdutoLinhaEditavel();
		}
	}

	public void carregarProdutoLinhaEditavel(Integer qtde,int linha) {

		if (qtde != null) {
			if (qtde == 0) {
				//Excluir o item da tabela
				this.getPedido().getItens().remove(linha);
			} else {
				ItemPedido item = this.pedido.getItens().get(0);

				if (this.produtoLinhaEditavel != null) {
					if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
						Messages.addGlobalWarn("Já existe um item no pedido com o produto informado.");
					} else {
						item.setProduto(this.produtoLinhaEditavel);
						item.setValorUnitario(this.produtoLinhaEditavel.getVlrVenda());

						this.pedido.adicionarItemVazio();
						this.produtoLinhaEditavel = null;
						this.codigoBarra = null;
						this.vlrUnit = null;
						this.qtdeEstoque = null;
					}
				}
			}
			this.pedido.recalcularValorTotal();
		}

	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		ProdutoFilter filtroProduto = new ProdutoFilter();
		filtroProduto.setNome(nome);
		return this.produtoService.filtrados(filtroProduto);
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	/************** Getters e Seterrs *******************/

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAchou() {
		return achou;
	}

	public void setAchou(boolean achou) {
		this.achou = achou;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(BigDecimal qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

}
