package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the datamatrixtrattamenti database table.
 * 
 */
@Entity
@NamedQuery(name="DatamatrixFasiProcesso.findAll", query="SELECT d FROM DatamatrixFasiProcesso d")
public class DatamatrixFasiProcesso implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codiceTrattamento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOra;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idAzienda")
    private Aziende azienda;

	@Id
	private int idDatamatrixFaseProcesso;

	//private int idUtente;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtente")
	private Utenti utenteOperatore;

	@Lob
	private String note;

	private Double valore;
	
	@Column(length = 10)
	private String numero;
	
	@ManyToOne
	@JoinColumn(name ="idDataMatrix")
	private Datamatrix dataMatrix;

	
	@ManyToOne
	@JoinColumn(name ="idFaseProcesso")
	private FasiProcesso faseProcesso;
	
	private Boolean eliminato;

	public DatamatrixFasiProcesso() {
	}

	public String getCodiceTrattamento() {
		return this.codiceTrattamento;
	}

	public void setCodiceTrattamento(String codiceTrattamento) {
		this.codiceTrattamento = codiceTrattamento;
	}

	public Date getDataOra() {
		return this.dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getValore() {
		return this.valore;
	}

	public void setValore(Double valore) {
		this.valore = valore;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Datamatrix getDataMatrix() {
		return dataMatrix;
	}

	public void setDataMatrix(Datamatrix dataMatrix) {
		this.dataMatrix = dataMatrix;
	}

	public Utenti getUtenteOperatore() {
		return utenteOperatore;
	}

	public void setUtenteOperatore(Utenti utenteOperatore) {
		this.utenteOperatore = utenteOperatore;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Aziende getAzienda() {
		return azienda;
	}

	public void setAzienda(Aziende azienda) {
		this.azienda = azienda;
	}

	public int getIdDatamatrixFaseProcesso() {
		return idDatamatrixFaseProcesso;
	}

	public void setIdDatamatrixFaseProcesso(int idDatamatrixFaseProcesso) {
		this.idDatamatrixFaseProcesso = idDatamatrixFaseProcesso;
	}

	public FasiProcesso getFaseProcesso() {
		return faseProcesso;
	}

	public void setFaseProcesso(FasiProcesso faseProcesso) {
		this.faseProcesso = faseProcesso;
	}

}