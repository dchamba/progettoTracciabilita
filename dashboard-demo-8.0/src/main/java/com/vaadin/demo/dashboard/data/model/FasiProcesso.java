package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FasiProcesso database table.
 * 
 */
@Entity
@NamedQuery(name="FasiProcesso.findAll", query="SELECT d FROM FasiProcesso d")
public class FasiProcesso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idFaseProcesso;
	
	private String codiceFase;
	
	private String descrizione;
	
	private String titoloPaginaFase;
	
	private Integer ordine;
	
	private Boolean attivo;
	
	private Boolean inserisciDatamatrix;
	
	private Boolean segnalaErroreSeDMEsiste;
	
	private Boolean inserisciFaseProcesso;
	
	private Boolean verificaSaltoFaseProcesso;
	
	private Boolean creaDatamatrixSeNonEsiste;
	
	private Boolean inserisciSoloSeEsisteDatamatrix;
	
	private Boolean eliminato;
	
	public FasiProcesso() {
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Integer getIdFaseProcesso() {
		return idFaseProcesso;
	}

	public void setIdFaseProcesso(Integer idFaseProcesso) {
		this.idFaseProcesso = idFaseProcesso;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Boolean getCreaDatamatrixSeNonEsiste() {
		return creaDatamatrixSeNonEsiste;
	}

	public void setCreaDatamatrixSeNonEsiste(Boolean creaDatamatrixSeNonEsiste) {
		this.creaDatamatrixSeNonEsiste = creaDatamatrixSeNonEsiste;
	}

	public Boolean getInserisciSoloSeEsisteDatamatrix() {
		return inserisciSoloSeEsisteDatamatrix;
	}

	public void setInserisciSoloSeEsisteDatamatrix(Boolean inserisciSoloSeEsisteDatamatrix) {
		this.inserisciSoloSeEsisteDatamatrix = inserisciSoloSeEsisteDatamatrix;
	}

	public Boolean getInserisciDatamatrix() {
		return inserisciDatamatrix;
	}

	public void setInserisciDatamatrix(Boolean inserisciDatamatrix) {
		this.inserisciDatamatrix = inserisciDatamatrix;
	}

	public Boolean getInserisciFaseProcesso() {
		return inserisciFaseProcesso;
	}

	public void setInserisciFaseProcesso(Boolean inserisciFaseProcesso) {
		this.inserisciFaseProcesso = inserisciFaseProcesso;
	}

	public Boolean getSegnalaErroreSeDMEsiste() {
		return segnalaErroreSeDMEsiste;
	}

	public void setSegnalaErroreSeDMEsiste(Boolean segnalaErroreSeDMEsiste) {
		this.segnalaErroreSeDMEsiste = segnalaErroreSeDMEsiste;
	}

	public String getTitoloPaginaFase() {
		return titoloPaginaFase;
	}

	public void setTitoloPaginaFase(String titoloPaginaFase) {
		this.titoloPaginaFase = titoloPaginaFase;
	}

	public String getCodiceFase() {
		return codiceFase;
	}

	public void setCodiceFase(String codiceFase) {
		this.codiceFase = codiceFase;
	}

	public Boolean getVerificaSaltoFaseProcesso() {
		return verificaSaltoFaseProcesso;
	}

	public void setVerificaSaltoFaseProcesso(Boolean verificaSaltoFaseProcesso) {
		this.verificaSaltoFaseProcesso = verificaSaltoFaseProcesso;
	}
	
}