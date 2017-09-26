package com.br.apss.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.br.apss.pedidovenda.model.CategoriaProduto;
import com.br.apss.pedidovenda.model.filter.CategoriaProdutoFilter;
import com.br.apss.pedidovenda.util.NegocioException;

public class CategoriaProdutoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public CategoriaProduto salvar(CategoriaProduto obj) {
		return manager.merge(obj);
	}

	public void excluir(CategoriaProduto obj) {
		try {
			obj = porId(obj.getId());
			manager.remove(obj);
			manager.flush();

		} catch (Exception e) {
			throw new NegocioException("Categoria de Produto não pode ser excluída");
		}
	}

	public CategoriaProduto porId(Long id) {
		return manager.find(CategoriaProduto.class, id);
	}

	public List<CategoriaProduto> listarTodos() {
		return manager.createQuery("from CategoriaProduto order by nome", CategoriaProduto.class).getResultList();
	}

	public CategoriaProduto porNome(String nome) {
		try {
			return manager.createQuery("from CategoriaProduto where upper(nome) = :nome", CategoriaProduto.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "deprecation" })
	private Criteria criarCriteriaParaFiltro(CategoriaProdutoFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(CategoriaProduto.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatus()!=null) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<CategoriaProduto> filtrados(CategoriaProdutoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public int quantidadeFiltrados(CategoriaProdutoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

}
