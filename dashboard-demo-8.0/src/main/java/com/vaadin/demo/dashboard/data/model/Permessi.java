package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permessi database table.
 * 
 */
@Entity
@NamedQuery(name="Permessi.findAll", query="SELECT p FROM Permessi p")
public class Permessi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idPermesso;

	public Permessi() {
	}

	public String getIdPermesso() {
		return this.idPermesso;
	}

	public void setIdPermesso(String idPermesso) {
		this.idPermesso = idPermesso;
	}

}