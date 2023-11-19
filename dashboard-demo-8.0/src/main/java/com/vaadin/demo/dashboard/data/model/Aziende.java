package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the aziende database table.
 * 
 */
@Entity
@NamedQuery(name="Aziende.findAll", query="SELECT a FROM Aziende a")
public class Aziende implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codiceAzienda;

	private String codiceValoreDurezza;
	
	private String codiceFiscale;

	private Boolean eliminato;

	@Id
	private int idAzienda;

	private String indirizzo;

	private String partitaIva;

	private String ragioneSociale;

	private String tipoAzienda;

	public Aziende() {
	}

	public String getCodiceAzienda() {
		return this.codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public int getIdAzienda() {
		return this.idAzienda;
	}

	public void setIdAzienda(int idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getTipoAzienda() {
		return this.tipoAzienda;
	}

	public void setTipoAzienda(String tipoAzienda) {
		this.tipoAzienda = tipoAzienda;
	}

	public String getCodiceValoreDurezza() {
		return codiceValoreDurezza;
	}

	public void setCodiceValoreDurezza(String codiceValoreDurezza) {
		this.codiceValoreDurezza = codiceValoreDurezza;
	}

}