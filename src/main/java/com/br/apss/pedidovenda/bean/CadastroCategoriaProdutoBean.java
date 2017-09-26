package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.CategoriaProduto;
import com.br.apss.pedidovenda.service.CategoriaProdutoService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroCategoriaProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CategoriaProduto categoriaProduto = new CategoriaProduto();

	private Long idCategoriaProduto;

	@Inject
	private CategoriaProdutoService categoriaProdutoService;

	public void inicializar() {
		if (idCategoriaProduto != null) {
			categoriaProduto = categoriaProdutoService.porId(idCategoriaProduto);
		}
	}

	public void salvar() {
		
		CategoriaProduto categoriaProdutoExistente = categoriaProdutoService.porNome(categoriaProduto.getNome());
		if (categoriaProdutoExistente != null && !categoriaProdutoExistente.equals(categoriaProduto)) {
			throw new NegocioException("Já existe uma Categoria com esse nome informado.");
		}
		
		categoriaProdutoService.salvar(categoriaProduto);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		categoriaProduto = new CategoriaProduto();
	}

	public void excluir() {
		categoriaProdutoService.excluir(categoriaProduto);
		// return "lista-CategoriaProduto.xhtml?faces-redirect=true";
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public Long getIdCategoriaProduto() {
		return idCategoriaProduto;
	}

	public void setIdCategoriaProduto(Long idCategoriaProduto) {
		this.idCategoriaProduto = idCategoriaProduto;
	}

}
