package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@NamedQuery(name="EtichetteImballi.findAll", query="SELECT a FROM EtichetteImballi a")
public class EtichetteImballi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idEtichettaImballo;
	
	private String codiceEtichetta;
	
	private String codiceEtichettaRiepilogativa;
	
	private String numeroDDT;
	
	private Date dataDDT;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idTipoImballo")
	private TipoImballi tipoImballo;

	private Boolean eliminato;
	
	public EtichetteImballi() {
	}

	public TipoImballi getTipoImballo() {
		return tipoImballo;
	}

	public void setTipoImballo(TipoImballi tipoImballo) {
		this.tipoImballo = tipoImballo;
	}
	
	public Date getDataDDT() {
		return dataDDT;
	}

	public void setDataDDT(Date dataDDT) {
		this.dataDDT = dataDDT;
	}

	public String getNumeroDDT() {
		return numeroDDT;
	}

	public void setNumeroDDT(String numeroDDT) {
		this.numeroDDT = numeroDDT;
	}

	public String getCodiceEtichetta() {
		return codiceEtichetta;
	}

	public void setCodiceEtichetta(String codiceEtichetta) {
		this.codiceEtichetta = codiceEtichetta;
	}

	public String getCodiceEtichettaRiepilogativa() {
		return codiceEtichettaRiepilogativa;
	}

	public void setCodiceEtichettaRiepilogativa(String codiceEtichettaRiepilogativa) {
		this.codiceEtichettaRiepilogativa = codiceEtichettaRiepilogativa;
	}

	public int getIdEtichettaImballo() {
		return idEtichettaImballo;
	}

	public void setIdEtichettaImballo(int idEtichettaImballo) {
		this.idEtichettaImballo = idEtichettaImballo;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

}