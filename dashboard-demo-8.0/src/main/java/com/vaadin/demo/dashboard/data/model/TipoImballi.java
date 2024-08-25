package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@NamedQuery(name="TipoImballi.findAll", query="SELECT t FROM TipoImballi t")
public class TipoImballi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idTipoImballo;
	
	private int qtaScatolePerBancale;
	
	private int qtaPezziPerScatola;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idProdotto")
	private Prodotti prodotto;
	
	//private int idProdotto;

	private Boolean imballoStandard;

	private String formatoDatamatrixImballo;
	
	private Boolean eliminato;

	public TipoImballi() {
	}

	public int getIdTipoImballo() {
		return idTipoImballo;
	}

	public void setIdTipoImballo(int idTipoImballo) {
		this.idTipoImballo = idTipoImballo;
	}

	public int getQtaScatolePerBancale() {
		return qtaScatolePerBancale;
	}

	public void setQtaScatolePerBancale(int qtaScatolePerBancale) {
		this.qtaScatolePerBancale = qtaScatolePerBancale;
	}

	public int getQtaPezziPerScatola() {
		return qtaPezziPerScatola;
	}

	public void setQtaPezziPerScatola(int qtaPezziPerScatola) {
		this.qtaPezziPerScatola = qtaPezziPerScatola;
	}

	public Prodotti getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotti prodotto) {
		this.prodotto = prodotto;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Boolean getImballoStandard() {
		return imballoStandard;
	}

	public void setImballoStandard(Boolean imballoStandard) {
		this.imballoStandard = imballoStandard;
	}

	public String getFormatoDatamatrixImballo() {
		return formatoDatamatrixImballo;
	}

	public void setFormatoDatamatrixImballo(String formatoDatamatrixImballo) {
		this.formatoDatamatrixImballo = formatoDatamatrixImballo;
	}

//	public Integer getIdProdotto() {
//		return idProdotto;
//	}
//
//	public void setIdProdotto(Integer idProdotto) {
//		this.idProdotto = idProdotto;
//	}


}