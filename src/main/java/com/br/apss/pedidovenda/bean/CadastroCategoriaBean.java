package com.br.apss.pedidovenda.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.service.CategoriaService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Categoria categoria = new Categoria();

	private Long idCategoria;

	@Inject
	private CategoriaService categoriaService;

	public void inicializar() {
		if (idCategoria != null) {
			categoria = categoriaService.porId(idCategoria);
		}
	}

	public void salvar() {

		Categoria categoriaExistente = categoriaService.porNome(categoria.getNome());
		if (categoriaExistente != null && !categoriaExistente.equals(categoria)) {
			throw new NegocioException("Já existe uma Categoria com esse nome informado.");
		}

		categoriaService.salvar(categoria);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		categoria = new Categoria();
	}

	public void excluir() {
		categoriaService.excluir(categoria);
		// return "lista-CategoriaProduto.xhtml?faces-redirect=true";
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

}
