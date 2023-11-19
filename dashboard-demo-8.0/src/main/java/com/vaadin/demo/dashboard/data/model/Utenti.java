package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the utenti database table.
 * 
 */
@Entity
@NamedQuery(name="Utenti.findAll", query="SELECT u FROM Utenti u")
public class Utenti implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean attivo;

	private Boolean cambioPassword;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCreazioneUtente;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAggiornamento;
	
	private Boolean eliminato;

	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idAzienda")
    private Aziende azienda;

	@Id
	private int idUtente;

	private String nomeCognome;

	private String password;

	private String tipoUtente;

	private String username;

	public Utenti() {
	}

	public Boolean getAttivo() {
		return this.attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Boolean getCambioPassword() {
		return this.cambioPassword;
	}

	public void setCambioPassword(Boolean cambioPassword) {
		this.cambioPassword = cambioPassword;
	}

	public Date getDataCreazioneUtente() {
		return this.dataCreazioneUtente;
	}

	public void setDataCreazioneUtente(Date dataCreazioneUtente) {
		this.dataCreazioneUtente = dataCreazioneUtente;
	}

	public Date getDataUltimoAggiornamento() {
		return this.dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeCognome() {
		return this.nomeCognome;
	}

	public void setNomeCognome(String nomeCognome) {
		this.nomeCognome = nomeCognome;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipoUtente() {
		return this.tipoUtente;
	}

	public void setTipoUtente(String tipoUtente) {
		this.tipoUtente = tipoUtente;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Aziende getAzienda() {
		return azienda;
	}

	public void setAzienda(Aziende azienda) {
		this.azienda = azienda;
	}

}