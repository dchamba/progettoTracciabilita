package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the prodotti database table.
 * 
 */
@Entity
@NamedQuery(name="Prodotti.findAll", query="SELECT p FROM Prodotti p")
public class Prodotti implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codiceProdotto;

	private String descrizione;

	private String numeroDisegno;
	
	private String codiceDatamatrix;

	private String formatoDatamatrix;

	//private String formatoDatamatrix2;

	private int idAziendaRichiedente;

	@Id
	private int idProdotto;

    @OneToMany(mappedBy = "prodotto", fetch = FetchType.EAGER)
    @OrderBy("ordine ASC")
	private Set<FasiProcessoProdotto> fasiProcesso;
    
    private boolean attivo;
    
    private String packingListPermesso;
    
    private boolean eliminato;

    private boolean provatenutaaria;

    private boolean provatenutaelio;
    
//    private String fasiProcesso;

	private int minValoreDurezza;
    
	public Prodotti() {
	}

	public String getCodiceProdotto() {
		return this.codiceProdotto;
	}

	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getIdAziendaRichiedente() {
		return this.idAziendaRichiedente;
	}

	public void setIdAziendaRichiedente(int idAziendaRichiedente) {
		this.idAziendaRichiedente = idAziendaRichiedente;
	}

	public int getIdProdotto() {
		return this.idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getCodiceDatamatrix() {
		return codiceDatamatrix;
	}

	public void setCodiceDatamatrix(String codiceDatamatrix) {
		this.codiceDatamatrix = codiceDatamatrix;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public String getFormatoDatamatrix() {
		return formatoDatamatrix;
	}

	public void setFormatoDatamatrix(String formatoDatamatrix) {
		this.formatoDatamatrix = formatoDatamatrix;
	}

	public Set<FasiProcessoProdotto> getFasiProcesso() {
		return fasiProcesso;
	}

	public void setFasiProcesso(Set<FasiProcessoProdotto> fasiProcesso) {
		this.fasiProcesso = fasiProcesso;
	}

	public String getPackingListPermesso() {
		return packingListPermesso;
	}

	public void setPackingListPermesso(String packingListPermesso) {
		this.packingListPermesso = packingListPermesso;
	}

	public String getNumeroDisegno() {
		return numeroDisegno;
	}

	public void setNumeroDisegno(String numeroDisegno) {
		this.numeroDisegno = numeroDisegno;
	}

	public boolean isProvatenutaelio() {
		return provatenutaelio;
	}

	public void setProvatenutaelio(boolean provatenutaelio) {
		this.provatenutaelio = provatenutaelio;
	}

	public boolean isProvatenutaaria() {
		return provatenutaaria;
	}

	public void setProvatenutaaria(boolean provatenutaaria) {
		this.provatenutaaria = provatenutaaria;
	}

	public boolean isProvatenutNecessaria() {
		return provatenutaaria  || provatenutaelio;
	}

	public int getMinValoreDurezza() {
		return minValoreDurezza;
	}

	public void setMinValoreDurezza(int minValoreDurezza) {
		this.minValoreDurezza = minValoreDurezza;
	}

//	public String getFormatoDatamatrix2() {
//		return formatoDatamatrix2;
//	}

//	public void setFormatoDatamatrix2(String formatoDatamatrix2) {
//		this.formatoDatamatrix2 = formatoDatamatrix2;
//	}

//	public String getFasiProcesso() {
//		return fasiProcesso;
//	}
//
//	public void setFasiProcesso(String fasiProcesso1) {
//		this.fasiProcesso = fasiProcesso1;
//	}

}