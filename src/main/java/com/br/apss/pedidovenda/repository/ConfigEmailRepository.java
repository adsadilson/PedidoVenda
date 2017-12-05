package com.br.apss.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.br.apss.pedidovenda.model.Categoria;
import com.br.apss.pedidovenda.model.ConfigEmail;
import com.br.apss.pedidovenda.util.NegocioException;

public class ConfigEmailRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public ConfigEmail salvar(ConfigEmail obj) {
		return manager.merge(obj);
	}

	public void excluir(ConfigEmail obj) {
		try {
			obj = porId(obj.getId());
			manager.remove(obj);
			manager.flush();

		} catch (Exception e) {
			throw new NegocioException("Configuração não pode ser excluída");
		}
	}

	public ConfigEmail porId(Long id) {
		return manager.find(ConfigEmail.class, id);
	}

	public List<ConfigEmail> listarTodos() {
		return manager.createQuery("from ConfigEmail order by login", ConfigEmail.class).getResultList();
	}

	public Categoria emailEmUso() {
		try {
			return manager.createQuery("from ConfigEmail where status = 'true'", Categoria.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
