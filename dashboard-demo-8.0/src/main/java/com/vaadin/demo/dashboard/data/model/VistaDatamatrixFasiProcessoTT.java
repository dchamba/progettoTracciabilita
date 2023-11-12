package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
//@IdClass(VistaDatamatrixFasiProcessoTTId.class)
@NamedQuery(name="VistaDatamatrixFasiProcessoTT.findAll", query="SELECT a FROM VistaDatamatrixFasiProcessoTT a")
public class VistaDatamatrixFasiProcessoTT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id private Integer idDatamatrixFaseProcessoTT;

	private Integer idDatamatrixFaseProcesso;

	private Integer idDataMatrix;
	
	private String dataMatrix;
	private Date dataOra;

	//private Integer idProdotto;
	private Integer idFaseProcesso;
	private Integer idUtente;
	private Integer idAzienda;
	
	private String numeroFornata;
	private Integer valoreDurezza;
	private String operatore;
    private boolean eliminato;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idProdotto")
    private Prodotti prodotto;
	
	public VistaDatamatrixFasiProcessoTT() {
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

	public String getNumeroFornata() {
		return numeroFornata;
	}

	public void setNumeroFornata(String numeroFornata) {
		this.numeroFornata = numeroFornata;
	}

	public Integer getValoreDurezza() {
		return valoreDurezza;
	}

	public void setValoreDurezza(Integer valoreDurezza) {
		this.valoreDurezza = valoreDurezza;
	}

	public Prodotti getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotti prodotto) {
		this.prodotto = prodotto;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public Integer getIdDatamatrixFaseProcessoTT() {
		return idDatamatrixFaseProcessoTT;
	}

	public void setIdDatamatrixFaseProcessoTT(Integer idDatamatrixFaseProcessoTT) {
		this.idDatamatrixFaseProcessoTT = idDatamatrixFaseProcessoTT;
	}
}