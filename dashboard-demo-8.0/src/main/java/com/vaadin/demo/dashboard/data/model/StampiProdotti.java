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
	    private boolean isDefault;
	    private boolean isAttivo;
	    private boolean isEliminato;

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

	    public boolean isDefault() {
	        return isDefault;
	    }

	    public void setDefault(boolean isDefault) {
	        this.isDefault = isDefault;
	    }

	    public boolean isAttivo() {
	        return isAttivo;
	    }

	    public void setAttivo(boolean isAttivo) {
	        this.isAttivo = isAttivo;
	    }

	    public boolean isEliminato() {
	        return isEliminato;
	    }

	    public void setEliminato(boolean isEliminato) {
	        this.isEliminato = isEliminato;
	    }

}