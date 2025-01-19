
package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the FasiProcessoProdotto database table.
 * 
 */
@Entity
@NamedQuery(name="VistaLottiFusioneAssegnazioneStampi.findAll", query="SELECT d FROM VistaLottiFusioneAssegnazioneStampi d")
public class VistaLottiFusioneAssegnazioneStampi implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idLottoFusioneAssegnazioneStampo;

    private String codiceProdotto;
    private String descrizioneProdotto;
    private String descrizioneStampo;
    private String descrizioneStampoPackingList;
    private String codificaStampo;
    private Integer idProdotto;
    private Integer idStampoProdotto;
	
    @ManyToOne
    @MapsId("idProdotto")
    @JoinColumn(name = "idStampoProdotto")
	private Prodotti prodotto;
    
    private Date daData;
    private Date aData;
    private Integer daProgressivo;
    private Integer aProgressivo;
    private String note;
    private boolean eliminato;
    private Date dataUltimoAggiornamento;
    private String utenteAggiornamento;

    public VistaLottiFusioneAssegnazioneStampi() { }

	public int getIdLottoFusioneAssegnazioneStampo() {
		return idLottoFusioneAssegnazioneStampo;
	}

	public void setIdLottoFusioneAssegnazioneStampo(int idLottoFusioneAssegnazioneStampo) {
		this.idLottoFusioneAssegnazioneStampo = idLottoFusioneAssegnazioneStampo;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isEliminato() {
		return eliminato;
	}

	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public String getUtenteAggiornamento() {
		return utenteAggiornamento;
	}

	public void setUtenteAggiornamento(String utenteAggiornamento) {
		this.utenteAggiornamento = utenteAggiornamento;
	}
}