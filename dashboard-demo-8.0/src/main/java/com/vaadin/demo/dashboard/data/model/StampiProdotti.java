package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the prodotti database table.
 * 
 */
@Entity
@NamedQuery(name="StampiProdotti.findAll", query="SELECT p FROM StampiProdotti p")
public class StampiProdotti implements Serializable {

	    private static final long serialVersionUID = 1L;

		@Id
	    private int idStampoProdotto;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name ="idProdotto")
		private Prodotti prodotto;
		
	    private String descrizioneStampo;
	    private String descrizioneStampoPackingList;
	    private String codificaStampo;
//	    private boolean default;
	    private boolean attivo;
	    private boolean eliminato;

	    public StampiProdotti() { }
	    
	    public int getIdStampoProdotto() {
	        return idStampoProdotto;
	    }

	    public void setIdStampoProdotto(int idStampoProdotto) {
	        this.idStampoProdotto = idStampoProdotto;
	    }

	    public Prodotti getProdotto() {
	        return prodotto;
	    }

	    public void setProdotto(Prodotti prodotto) {
	        this.prodotto = prodotto;
	    }

	    public String getDescrizioneStampo() {
	        return descrizioneStampo;
	    }

	    public void setDescrizioneStampo(String descrizioneStampo) {
	        this.descrizioneStampo = descrizioneStampo;
	    }

	    public String getDescrizioneStampoPackingList() {
	        return descrizioneStampoPackingList;
	    }

	    public void setDescrizioneStampoPackingList(String descrizioneStampoPackingList) {
	        this.descrizioneStampoPackingList = descrizioneStampoPackingList;
	    }

	    public String getCodificaStampo() {
	        return codificaStampo;
	    }

	    public void setCodificaStampo(String codificaStampo) {
	        this.codificaStampo = codificaStampo;
	    }

//	    public boolean isDefault() {
//	        return default;
//	    }
//
//	    public void setDefault(boolean isDefault) {
//	        this.default = isDefault;
//	    }

	    public boolean isAttivo() {
	        return attivo;
	    }

	    public void setAttivo(boolean isAttivo) {
	        this.attivo = isAttivo;
	    }

	    public boolean isEliminato() {
	        return eliminato;
	    }

	    public void setEliminato(boolean isEliminato) {
	        this.eliminato = isEliminato;
	    }

}