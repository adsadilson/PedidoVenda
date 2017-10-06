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

import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.SubCategoria;
import com.br.apss.pedidovenda.model.filter.SubCategoriaFilter;
import com.br.apss.pedidovenda.util.NegocioException;

public class SubCategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public SubCategoria salvar(SubCategoria obj) {
		return manager.merge(obj);
	}

	public void excluir(SubCategoria obj) {
		try {
			obj = porId(obj.getId());
			manager.remove(obj);
			manager.flush();

		} catch (Exception e) {
			throw new NegocioException("Sub-Categoria de Produto n�o pode ser exclu�da");
		}
	}

	public SubCategoria porId(Long id) {
		return manager.find(SubCategoria.class, id);
	}

	public List<SubCategoria> listarTodos() {
		return manager.createQuery("from SubCategoria order by nome", SubCategoria.class).getResultList();
	}

	public SubCategoria porNome(String nome) {
		try {
			return manager.createQuery("from SubCategoria where upper(nome) = :nome", SubCategoria.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<SubCategoria> porCategoria(Categoria categoria) {
		try {
			return manager.createQuery("from SubCategoria where categoria = :categoria", SubCategoria.class)
					.setParameter("categoria", categoria).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "deprecation" })
	private Criteria criarCriteriaParaFiltro(SubCategoriaFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(SubCategoria.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatus() != null) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<SubCategoria> filtrados(SubCategoriaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatus() != null) {
			if (filtro.getStatus()) {
				criteria.add(Restrictions.eq("status", true));
			} else {
				criteria.add(Restrictions.eq("status", false));
			}

		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public int quantidadeFiltrados(SubCategoriaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

}
