package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.enums.TipoProduto;
import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.Produto;
import com.br.apss.pedidovenda.model.SubCategoria;
import com.br.apss.pedidovenda.model.UnidadeMedida;
import com.br.apss.pedidovenda.model.filter.CategoriaFilter;
import com.br.apss.pedidovenda.service.CategoriaService;
import com.br.apss.pedidovenda.service.ProdutoService;
import com.br.apss.pedidovenda.service.SubCategoriaService;
import com.br.apss.pedidovenda.service.UnidadeMedidaService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();

	private Long idProduto;

	@Inject
	private ProdutoService produtoService;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private SubCategoriaService subCategoriaService;
	
	@Inject
	private UnidadeMedidaService uMedidaService;

	private List<SubCategoria> subCategorias = new ArrayList<SubCategoria>();

	public void inicializar() {
		if (idProduto != null) {
			produto = produtoService.porId(idProduto);
			getCategorias();
			listarSubCategoriasFiltrada();
		}
	}

	public void salvar() {

		Produto produtoExistente = produtoService.porNome(produto.getNome());
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe uma Produto com esse nome informado.");
		}

		produtoService.salvar(produto);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		produto = new Produto();
	}

	public void excluir() {
		produtoService.excluir(produto);
		// return "lista-ProdutoProduto.xhtml?faces-redirect=true";
	}

	public List<Categoria> getCategorias() {
		CategoriaFilter filtro = new CategoriaFilter();
		return categoriaService.filtrados(filtro);
	}
	
	public List<UnidadeMedida> getUnidades(){
		return uMedidaService.listarTodos();
	}
	
	public List<TipoProduto> getTipos(){
		return Arrays.asList(TipoProduto.values());
	}

	public void listarSubCategoriasFiltrada() {
		if (null != produto.getCategoria()) {
			subCategorias = subCategoriaService.porCategoria(produto.getCategoria());
		} else {
			subCategorias = new ArrayList<SubCategoria>();
		}
	}
	
	public void calcMargem() {
		BigDecimal n = produto.getVlrCusto();
		BigDecimal n3 = BigDecimal.ZERO;
		BigDecimal n2 = produto.getMargLucro();
		if (produto.getVlrCusto().signum() > 0 && produto.getMargLucro().signum() > 0) {
			n3 = (n.multiply(n2)).divide(new BigDecimal(100)).add(n);
			produto.setVlrVenda(n3);
			calcLucro();
		} else {
			produto.setVlrVenda(BigDecimal.ZERO);
		}
	}

	public void calcVenda() {
		BigDecimal n = produto.getVlrCusto();
		BigDecimal n3 = BigDecimal.ZERO;
		BigDecimal n2 = produto.getVlrVenda();
		if (n.signum() > 0 && n2.signum() > 0) {
			n3 = (n2.subtract(n)).divide(n, MathContext.DECIMAL128).multiply(new BigDecimal(100));
			produto.setMargLucro(n3);
			calcLucro();
		} else {
			produto.setMargLucro(BigDecimal.ZERO);
		}
	}

	public void calcLucro() {
		BigDecimal n = produto.getVlrCusto();
		BigDecimal n3 = BigDecimal.ZERO;
		BigDecimal n2 = produto.getVlrVenda();
		if (n.signum() > 0 && n2.signum() > 0) {
			n3 = n2.subtract(n);
			produto.setLucro(n3);
		} else {
			produto.setLucro(BigDecimal.ZERO);
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

}
