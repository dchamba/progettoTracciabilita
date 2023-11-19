package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name="VistaPackingList.findAll", query="SELECT a FROM VistaPackingList a")
public class VistaPackingList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idDataMatrix;
	private String dataMatrix;
	private String codiceProdotto;
	private String codiceEtichetta;
	private String numeroDDT;
	private String codiceEtichettaRiepilogativa;
	private Integer idEtichettaPezzo;
	private Integer idEtichettaImballo;
	private String nomeCognome;
	private Date dataDDT;
    private boolean eliminato;
	
	public VistaPackingList() {
	}

	public Integer getIdDataMatrix() {
		return idDataMatrix;
	}

	public void setIdDataMatrix(Integer idDataMatrix) {
		this.idDataMatrix = idDataMatrix;
	}

	public String getDataMatrix() {
		return dataMatrix;
	}

	public void setDataMatrix(String dataMatrix) {
		this.dataMatrix = dataMatrix;
	}

	public String getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public String getCodiceEtichetta() {
		return codiceEtichetta;
	}

	public void setCodiceEtichetta(String codiceEtichetta) {
		this.codiceEtichetta = codiceEtichetta;
	}

	public String getNumeroDDT() {
		return numeroDDT;
	}

	public void setNumeroDDT(String numeroDDT) {
		this.numeroDDT = numeroDDT;
	}

	public String getCodiceEtichettaRiepilogativa() {
		return codiceEtichettaRiepilogativa;
	}

	public void setCodiceEtichettaRiepilogativa(String codiceEtichettaRiepilogativa) {
		this.codiceEtichettaRiepilogativa = codiceEtichettaRiepilogativa;
	}

	public Integer getIdEtichettaPezzo() {
		return idEtichettaPezzo;
	}

	public void setIdEtichettaPezzo(Integer idEtichettaPezzo) {
		this.idEtichettaPezzo = idEtichettaPezzo;
	}

	public String getNomeCognome() {
		return nomeCognome;
	}

	public void setNomeCognome(String nomeCognome) {
		this.nomeCognome = nomeCognome;
	}

	public Date getDataDDT() {
		return dataDDT;
	}

	public void setDataDDT(Date dataDDT) {
		this.dataDDT = dataDDT;
	}

	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Integer getIdEtichettaImballo() {
		return idEtichettaImballo;
	}

	public void setIdEtichettaImballo(Integer idEtichettaImballo) {
		this.idEtichettaImballo = idEtichettaImballo;
	}

}