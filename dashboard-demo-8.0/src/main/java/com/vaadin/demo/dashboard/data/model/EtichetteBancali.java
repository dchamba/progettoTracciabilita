package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name="EtichetteBancali.findAll", query="SELECT a FROM EtichetteBancali a")
public class EtichetteBancali implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String codiceEtichetta;
	@Id
	private String codiceBancale;

	private Double quantita;
	private String numeroDDT;
	private Date dataDDT;
	
	public EtichetteBancali() {
	}

	public String getCodiceEtichetta() {
		return codiceEtichetta;
	}

	public void setCodiceEtichetta(String codiceEtichetta) {
		this.codiceEtichetta = codiceEtichetta;
	}

	public String getCodiceBancale() {
		return codiceBancale;
	}

	public void setCodiceBancale(String codiceBancale) {
		this.codiceBancale = codiceBancale;
	}

	public Double getQuantita() {
		return quantita;
	}

	public void setQuantita(Double quantita) {
		this.quantita = quantita;
	}

	public String getNumeroDDT() {
		return numeroDDT;
	}

	public void setNumeroDDT(String numeroDDT) {
		this.numeroDDT = numeroDDT;
	}

	public Date getDataDDT() {
		return dataDDT;
	}

	public void setDataDDT(Date dataDDT) {
		this.dataDDT = dataDDT;
	}

}