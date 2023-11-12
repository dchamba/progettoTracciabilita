package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Archive_22 database table.
 * 
 */
@Entity
@NamedQuery(name="Archive_22.findAll", query="SELECT a FROM Archive_22 a")
public class Archive_22 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int Id;

	private String Data;

	private String Ora;

	private String DMC;

	private String EsitoTOT;

	public Archive_22() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getOra() {
		return Ora;
	}

	public void setOra(String ora) {
		Ora = ora;
	}

	public String getDMC() {
		return DMC;
	}

	public void setDMC(String dMC) {
		DMC = dMC;
	}

	public String getEsitoTOT() {
		return EsitoTOT;
	}

	public void setEsitoTOT(String esitoTOT) {
		EsitoTOT = esitoTOT;
	}

}