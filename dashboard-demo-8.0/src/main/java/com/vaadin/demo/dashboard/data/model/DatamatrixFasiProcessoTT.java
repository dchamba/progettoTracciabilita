package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the DatamatrixFasiProcessoTT database table.
 * 
 */
@Entity
@NamedQuery(name="DatamatrixFasiProcessoTT.findAll", query="SELECT d FROM DatamatrixFasiProcessoTT d")
public class DatamatrixFasiProcessoTT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idDatamatrixFaseProcessoTT;

	private Boolean eliminato;
	
	private Integer valoreDurezza;
	
	private String numeroFornata;
	
	private int idDatamatrixFaseProcesso;
	
	private String operatore;
	
	public DatamatrixFasiProcessoTT() {
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}
	
	public int getIdDatamatrixFaseProcesso() {
		return idDatamatrixFaseProcesso;
	}

	public void setIdDatamatrixFaseProcesso(int idDatamatrixFaseProcesso) {
		this.idDatamatrixFaseProcesso = idDatamatrixFaseProcesso;
	}

	public Integer getValoreDurezza() {
		return valoreDurezza;
	}

	public void setValoreDurezza(Integer valoreDurezza) {
		this.valoreDurezza = valoreDurezza;
	}

	public String getNumeroFornata() {
		return numeroFornata;
	}

	public void setNumeroFornata(String numeroFornata) {
		this.numeroFornata = numeroFornata;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

}