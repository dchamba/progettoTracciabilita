package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the Pan5 database table.
 * 
 */
@Entity
@NamedQuery(name="Pan5.findAll", query="SELECT a FROM Pan5 a")
public class Pan5 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ID;

	@Column(name="QR-Code")
	private String QRCode;

	@Column(name="Date_Time")
	private String dataOra;

	@Column(name="Pres-pin-1")
	private Double pressionePin1;

	@Column(name="Pres-pin-2")
	private Double pressionePin2;

	@Column(name="Pos-pin-1")
	private Double posizionePin1;

	@Column(name="Pos-pin-2")
	private Double posizionePin2;

	@Column(name="Pz-OK")
	private boolean esito;
	
	public Pan5() { }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getQRCode() {
		return QRCode;
	}

	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}

	public String getDataOra() {
		return dataOra;
	}

	public void setDataOra(String dataOra) {
		this.dataOra = dataOra;
	}

	public Double getPressionePin1() {
		return pressionePin1;
	}

	public void setPressionePin1(Double pressionePin1) {
		this.pressionePin1 = pressionePin1;
	}

	public Double getPressionePin2() {
		return pressionePin2;
	}

	public void setPressionePin2(Double pressionePin2) {
		this.pressionePin2 = pressionePin2;
	}

//	public Double getPosizionePin1() {
//		return posizionePin1;
//	}
//
//	public void setPosizionePin1(Double posizionePin1) {
//		this.posizionePin1 = posizionePin1;
//	}
//
//	public Double getPosizionePin2() {
//		return posizionePin2;
//	}
//
//	public void setPosizionePin2(Double posizionePin2) {
//		this.posizionePin2 = posizionePin2;
//	}
//
//	public boolean isEsito() {
//		return esito;
//	}
//
//	public void setEsito(boolean esito) {
//		this.esito = esito;
//	}
	
}