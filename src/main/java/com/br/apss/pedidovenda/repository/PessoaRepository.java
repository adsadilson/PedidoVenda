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

import com.br.apss.pedidovenda.model.Pessoa;
import com.br.apss.pedidovenda.model.filter.PessoaFilter;
import com.br.apss.pedidovenda.util.NegocioException;

public class PessoaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Pessoa salvar(Pessoa obj) {
		return manager.merge(obj);
	}

	public void excluir(Pessoa obj) {
		try {
			obj = porId(obj.getId());
			manager.remove(obj);
			manager.flush();

		} catch (Exception e) {
			throw new NegocioException("Pessoa não pode ser excluída");
		}
	}

	public Pessoa porId(Long id) {
		return manager.find(Pessoa.class, id);
	}

	public List<Pessoa> listarTodos() {
		return manager.createQuery("from Pessoa order by nome", Pessoa.class).getResultList();
	}

	public Pessoa porNome(String nome) {
		try {
			return manager.createQuery("from Pessoa where upper(nome) = :nome", Pessoa.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Pessoa porCpf(String cpf) {
		try {
			return manager.createQuery("from Pessoa where cpf_cnpj = :cpf", Pessoa.class)
					.setParameter("cpf", cpf).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "deprecation" })
	private Criteria criarCriteriaParaFiltro(PessoaFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pessoa.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatus() != null) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> filtrados(PessoaFilter filtro) {
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

	public int quantidadeFiltrados(PessoaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

}
