package com.vaadin.demo.dashboard.data.model;

import java.util.ArrayList;
import java.util.Date;

public class DatamatrixFasiEseguite {
	
	private int idDataMatrix;

	private String dataMatrix;
	
	private Date dataOra;
	
	private String faseProcesso;
	
	private String utente;

	private String esito;
	
	private String esitoValore;
	
	private String impianto;
	
	private ArrayList<DatamatrixFasiEseguite> controlliMaster;
	
	public DatamatrixFasiEseguite () {
		
	}

	public int getIdDataMatrix() {
		return idDataMatrix;
	}

	public void setIdDataMatrix(int idDataMatrix) {
		this.idDataMatrix = idDataMatrix;
	}

	public String getDataMatrix() {
		return dataMatrix;
	}

	public void setDataMatrix(String dataMatrix) {
		this.dataMatrix = dataMatrix;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getFaseProcesso() {
		return faseProcesso;
	}

	public void setFaseProcesso(String faseProcesso) {
		this.faseProcesso = faseProcesso;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}


	public String getEsitoValore() {
		return esitoValore;
	}

	public void setEsitoValore(String esitoValore) {
		this.esitoValore = esitoValore;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getImpianto() {
		return impianto;
	}

	public void setImpianto(String impianto) {
		this.impianto = impianto;
	}

	public ArrayList<DatamatrixFasiEseguite> getControlliMaster() {
		return controlliMaster;
	}

	public void setControlliMaster(ArrayList<DatamatrixFasiEseguite> controlliMaster) {
		this.controlliMaster = controlliMaster;
	}
	
	
}