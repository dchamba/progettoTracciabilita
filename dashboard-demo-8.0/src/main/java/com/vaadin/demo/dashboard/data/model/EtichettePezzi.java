package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name="EtichettePezzi.findAll", query="SELECT a FROM EtichettePezzi a")
public class EtichettePezzi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idEtichettaPezzo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idEtichettaImballo")
	private EtichetteImballi etichettaImballo;

	@Column(insertable = false, updatable = false)
	private int idEtichettaImballo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idDatamatrix")
	private Datamatrix datamatrix;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtenteCreatore")
	private Utenti utenteCreatore;
	
	private Date dataOraCreazione;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtenteUltimaModifica")
	private Utenti utenteUltimaModifica;
	
	private Date dataOraUltimaModifca;

    private boolean eliminato;
	
	public EtichettePezzi() {
	}

	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Date getDataOraUltimaModifca() {
		return dataOraUltimaModifca;
	}

	public void setDataOraUltimaModifca(Date dataOraUltimaModifca) {
		this.dataOraUltimaModifca = dataOraUltimaModifca;
	}

	public Utenti getUtenteUltimaModifica() {
		return utenteUltimaModifica;
	}

	public void setUtenteUltimaModifica(Utenti utenteUltimaModifica) {
		this.utenteUltimaModifica = utenteUltimaModifica;
	}

	public Date getDataOraCreazione() {
		return dataOraCreazione;
	}

	public void setDataOraCreazione(Date dataOraCreazione) {
		this.dataOraCreazione = dataOraCreazione;
	}

	public Utenti getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utenti utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Datamatrix getDatamatrix() {
		return datamatrix;
	}

	public void setDatamatrix(Datamatrix datamatrix) {
		this.datamatrix = datamatrix;
	}

	public EtichetteImballi getEtichettaImballo() {
		return etichettaImballo;
	}

	public void setEtichettaImballo(EtichetteImballi etichettaImballo) {
		this.etichettaImballo = etichettaImballo;
	}

	public int getIdEtichettaPezzo() {
		return idEtichettaPezzo;
	}

	public void setIdEtichettaPezzo(int idEtichettaPezzo) {
		this.idEtichettaPezzo = idEtichettaPezzo;
	}

	public int getIdEtichettaImballo() {
		return idEtichettaImballo;
	}

	public void setIdEtichettaImballo(int idEtichettaImballo) {
		this.idEtichettaImballo = idEtichettaImballo;
	}


}