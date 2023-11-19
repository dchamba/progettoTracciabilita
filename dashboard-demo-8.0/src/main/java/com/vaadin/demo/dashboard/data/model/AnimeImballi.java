package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import com.vaadin.demo.dashboard.component.utils.CommonUtils;

import java.util.Date;

/**
 * The persistent class for the AnimeImballi database table.
 * 
 */
@Entity
@NamedQuery(name="AnimeImballi.findAll", query="SELECT a FROM AnimeImballi a")
public class AnimeImballi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idImballoAnime;
	
	@Column(length =50)
	private String qrcode;
	
	private Integer qtaAnime;
	
	private Integer garanziaGiorniAnima;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataProduzione;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOraCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataScadenza;
//	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name ="idProdotto")
//    private Prodotti prodotto;

	private int idProdotto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtenteCreatore")
	private Utenti utenteCreatore;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUtilizzo;
	
	private Boolean eliminato;
	
	public AnimeImballi() {
	}

	public Date getDataOraCreazione() {
		return this.dataOraCreazione;
	}

	public void setDataOraCreazione(Date dataOraCreazione) {
		this.dataOraCreazione = dataOraCreazione;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Utenti getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utenti utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public String getNomeUtenteCreatore() {
		if(utenteCreatore != null) {
			return getUtenteCreatore().getNomeCognome();
		} else {
			return "";
		}
	}
//
//	public Prodotti getProddoto() {
//		return prodotto;
//	}
//
//	public void setProddoto(Prodotti proddoto) {
//		this.prodotto = proddoto;
//	}
//	
//	public String getCodiceProdotto() {
//		return this.prodotto == null ? "" : this.prodotto.getCodiceProdotto();
//	}

	public int getIdImballoAnime() {
		return idImballoAnime;
	}

	public void setIdImballoAnime(int idImballoAnime) {
		this.idImballoAnime = idImballoAnime;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Integer getQtaAnime() {
		return qtaAnime;
	}

	public void setQtaAnime(Integer qtaAnime) {
		this.qtaAnime = qtaAnime;
	}

	public Integer getGaranziaGiorniAnima() {
		return garanziaGiorniAnima;
	}

	public void setGaranziaGiorniAnima(Integer garanziaGiorniAnima) {
		this.garanziaGiorniAnima = garanziaGiorniAnima;
	}

	public Date getDataProduzione() {
		return dataProduzione;
	}

	public String getDataProduzioneFormatoString() {
		return CommonUtils.formatDate(dataProduzione, CommonUtils.DATEFORMAT);
	}

	public void setDataProduzione(Date dataProduzione) {
		this.dataProduzione = dataProduzione;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public String getDataScadenzaFormatoString() {
		return CommonUtils.formatDate(dataScadenza, CommonUtils.DATEFORMAT);
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataUtilizzo() {
		return dataUtilizzo;
	}

	public void setDataUtilizzo(Date dataUtilizzo) {
		this.dataUtilizzo = dataUtilizzo;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
}