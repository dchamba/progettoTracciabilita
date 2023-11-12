package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the accessi database table.
 * 
 */
@Entity
@NamedQuery(name="Accessi.findAll", query="SELECT a FROM Accessi a")
public class Accessi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOra;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idAccesso;

	private int idUtente;

	private String ip;

	private String userAgent;

	public Accessi() {
	}

	public Date getDataOra() {
		return this.dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public int getIdAccesso() {
		return this.idAccesso;
	}

	public void setIdAccesso(int idAccesso) {
		this.idAccesso = idAccesso;
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}