package com.vaadin.demo.dashboard.component.model;

import java.util.Date;

public class FilterDatamatrix {
	private String datamatrix;
	private Date dataInizio, dataFine;
	private Integer idAziendaUtente;
	private String codiceProdotto;
	
	public FilterDatamatrix() { }
	
	public FilterDatamatrix(String dataMatrix) {
		this.datamatrix = dataMatrix;
	}
	
	private GridPagination pagination;

	public String getDatamatrix() {
		return datamatrix;
	}

	public void setDatamatrix(String datamatrix) {
		this.datamatrix = datamatrix;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getIdAziendaUtente() {
		return idAziendaUtente;
	}

	public void setIdAziendaUtente(Integer idAziendaUtente) {
		this.idAziendaUtente = idAziendaUtente;
	}

	public String getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public GridPagination getPagination() {
		return pagination;
	}

	public void setPagination(GridPagination pagination) {
		this.pagination = pagination;
	}
}
