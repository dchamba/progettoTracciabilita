package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the config database table.
 * 
 */
@Entity
@NamedQuery(name="Config.findAll", query="SELECT c FROM Config c")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String chiave;

	@Lob
	private String valore;

	public Config() {
	}

	public String getChiave() {
		return this.chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}