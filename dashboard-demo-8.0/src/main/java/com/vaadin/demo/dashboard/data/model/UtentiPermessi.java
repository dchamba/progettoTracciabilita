package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the utentipermessi database table.
 * 
 */
@Entity
@NamedQuery(name="UtentiPermessi.findAll", query="SELECT u FROM UtentiPermessi u")
public class UtentiPermessi implements Serializable {
	private static final long serialVersionUID = 1L;

	private String idPermesso;

	private int idUtente;

	@Id
	private int idUtentePermesso;

	public UtentiPermessi() {
	}

	public String getIdPermesso() {
		return this.idPermesso;
	}

	public void setIdPermesso(String idPermesso) {
		this.idPermesso = idPermesso;
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdUtentePermesso() {
		return this.idUtentePermesso;
	}

	public void setIdUtentePermesso(int idUtentePermesso) {
		this.idUtentePermesso = idUtentePermesso;
	}

}