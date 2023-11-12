package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DatiProvaTenutaElio database table.
 * 
 */
@Entity
@NamedQuery(name="DatiProvaTenutaElio.findAll", query="SELECT a FROM DatiProvaTenutaElio a")
public class DatiProvaTenutaElio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int Id;

	private String Data;

	private String Ora;

	private String Datamatrix;

	private String Esito;

	private String Operatore;
	
	private String Perdita;

	public DatiProvaTenutaElio() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getOra() {
		return Ora;
	}

	public void setOra(String ora) {
		Ora = ora;
	}

	public String getDatamatrix() {
		return Datamatrix;
	}

	public void setDatamatrix(String datamatrix) {
		Datamatrix = datamatrix;
	}

	public String getEsito() {
		return Esito;
	}

	public void setEsito(String esito) {
		Esito = esito;
	}

	public String getOperatore() {
		return Operatore;
	}

	public void setOperatore(String operatore) {
		Operatore = operatore;
	}

	public String getPerdita() {
		return Perdita;
	}

	public void setPerdita(String perdita) {
		Perdita = perdita;
	}

}