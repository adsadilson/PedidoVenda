package com.br.apss.pedidovenda.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.SubCategoria;
import com.br.apss.pedidovenda.model.filter.CategoriaFilter;
import com.br.apss.pedidovenda.service.CategoriaService;
import com.br.apss.pedidovenda.service.SubCategoriaService;
import com.br.apss.pedidovenda.util.NegocioException;

@Named
@ViewScoped
public class CadastroSubCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private SubCategoria subCategoria = new SubCategoria();

	private Long idSubCategoria;

	@Inject
	private SubCategoriaService subCategoriaService;
	
	@Inject
	private CategoriaService categoriaService;

	public void inicializar() {
		if (idSubCategoria != null) {
			subCategoria = subCategoriaService.porId(idSubCategoria);
			getCategorias();
		}
	}

	public void salvar() {

		SubCategoria subCategoriaExistente = subCategoriaService.porNome(subCategoria.getNome());
		if (subCategoriaExistente != null && !subCategoriaExistente.equals(subCategoria)) {
			throw new NegocioException("Já existe uma SubCategoria com esse nome informado.");
		}

		subCategoriaService.salvar(subCategoria);
		Messages.addGlobalInfo("Registro salvor com sucesso.");
		limpar();

	}

	private void limpar() {
		subCategoria = new SubCategoria();
	}

	public void excluir() {
		subCategoriaService.excluir(subCategoria);
		// return "lista-SubCategoriaProduto.xhtml?faces-redirect=true";
	}
	
	public List<Categoria> getCategorias(){
		CategoriaFilter filtro = new CategoriaFilter();
		return categoriaService.filtrados(filtro);
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Long getIdSubCategoria() {
		return idSubCategoria;
	}

	public void setIdSubCategoria(Long idSubCategoria) {
		this.idSubCategoria = idSubCategoria;
	}

}
