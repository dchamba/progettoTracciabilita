package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name="VistaDatamatrixFasiProcesso.findAll", query="SELECT a FROM VistaDatamatrixFasiProcesso a")
public class VistaDatamatrixFasiProcesso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idDataMatrix;
	private String dataMatrix;
	private Date dataOra;

	private Integer idProdotto;
	private Integer idDatamatrixFaseProcesso;
	private Integer idFaseProcesso;
	private Integer idUtente;
	private Integer idAzienda;
	
	private String codiceTrattamento;
	private Double valore;
	private String numero;
	private String note;
    private boolean eliminato;
    
	public VistaDatamatrixFasiProcesso() {
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


	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}

	public Integer getIdDatamatrixFaseProcesso() {
		return idDatamatrixFaseProcesso;
	}

	public void setIdDatamatrixFaseProcesso(Integer idDatamatrixFaseProcesso) {
		this.idDatamatrixFaseProcesso = idDatamatrixFaseProcesso;
	}

	public Integer getIdFaseProcesso() {
		return idFaseProcesso;
	}

	public void setIdFaseProcesso(Integer idFaseProcesso) {
		this.idFaseProcesso = idFaseProcesso;
	}

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}

	public Integer getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Integer idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getCodiceTrattamento() {
		return codiceTrattamento;
	}

	public void setCodiceTrattamento(String codiceTrattamento) {
		this.codiceTrattamento = codiceTrattamento;
	}

	public Double getValore() {
		return valore;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}