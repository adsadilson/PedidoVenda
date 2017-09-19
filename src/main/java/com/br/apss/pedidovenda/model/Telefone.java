package com.br.apss.pedidovenda.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "telefone")
@SequenceGenerator(name = "TELEFONE_ID", sequenceName = "TELEFONE_SEQ", allocationSize = 1, initialValue = 1)
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TELEFONE_ID")
	private Long id;

	@Column(length = 150)
	private String contato;

	@Column(length = 150)
	private String numero;

	@Column(length = 250)
	private String email;

	@Column(length = 40)
	private String tipo;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Pessoa cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato == null ? null : contato.toUpperCase();;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.toLowerCase();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
