package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@NamedQuery(name="CriteriBloccoDatamatrix.findAll", query="SELECT t FROM CriteriBloccoDatamatrix t")
public class CriteriBloccoDatamatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idBloccoDatamatrix;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idProdotto")
    private Prodotti prodotto;
	
	private Date daData;
	
	private Date aData;
	
	private Integer daProgressivo;
	
	private Integer aProgressivo;
	
	private Boolean applicaSoloAPackingList;

	private String messaggioUtente;

	private Date dataUltimoAggiornamento;
	
	private Boolean attivo; 
	
	public CriteriBloccoDatamatrix() { }

	public int getIdBloccoDatamatrix() {
		return idBloccoDatamatrix;
	}

	public void setIdBloccoDatamatrix(int idBloccoDatamatrix) {
		this.idBloccoDatamatrix = idBloccoDatamatrix;
	}

	public Prodotti getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotti prodotto) {
		this.prodotto = prodotto;
	}

	public Date getDaData() {
		return daData;
	}

	public void setDaData(Date daData) {
		this.daData = daData;
	}

	public Date getaData() {
		return aData;
	}

	public void setaData(Date aData) {
		this.aData = aData;
	}

	public Integer getDaProgressivo() {
		return daProgressivo;
	}

	public void setDaProgressivo(Integer daProgressivo) {
		this.daProgressivo = daProgressivo;
	}

	public Integer getaProgressivo() {
		return aProgressivo;
	}

	public void setaProgressivo(Integer aProgressivo) {
		this.aProgressivo = aProgressivo;
	}

	public Boolean getApplicaSoloAPackingList() {
		return applicaSoloAPackingList;
	}

	public void setApplicaSoloAPackingList(Boolean applicaSoloAPackingList) {
		this.applicaSoloAPackingList = applicaSoloAPackingList;
	}

	public String getMessaggioUtente() {
		return messaggioUtente;
	}

	public void setMessaggioUtente(String messaggioUtente) {
		this.messaggioUtente = messaggioUtente;
	}

	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

}