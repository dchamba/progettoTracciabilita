package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the datamatrix database table.
 * 
 */
@Entity
@NamedQuery(name="Datamatrix.findAll", query="SELECT d FROM Datamatrix d")
public class Datamatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codiceInternoProdotto;

	@Column(length =20)
	private String dataMatrix;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOraCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOraUltimaModifica;

	private String descrizione;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int idDataMatrix;

	//private Integer idProdotto;

	private String partNumber;

	private String stato;

	private Boolean eliminato;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtenteCreatore")
	private Utenti utenteCreatore;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idUtenteUltimaModifica")
	private Utenti utenteUltimaModifica;
	
    @OneToMany(mappedBy="dataMatrix", fetch = FetchType.EAGER)
    private Set<DatamatrixFasiProcesso> trattamenti = new HashSet<DatamatrixFasiProcesso>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="idProdotto")
    private Prodotti prodotto;
    
	public Datamatrix() {
	}

	public String getCodiceInternoProdotto() {
		return this.codiceInternoProdotto;
	}

	public void setCodiceInternoProdotto(String codiceInternoProdotto) {
		this.codiceInternoProdotto = codiceInternoProdotto;
	}

	public String getDataMatrix() {
		return this.dataMatrix;
	}

	public void setDataMatrix(String dataMatrix) {
		this.dataMatrix = dataMatrix;
	}

	public Date getDataOraCreazione() {
		return this.dataOraCreazione;
	}

	public void setDataOraCreazione(Date dataOraCreazione) {
		this.dataOraCreazione = dataOraCreazione;
	}

	public Date getDataOraUltimaModifica() {
		return this.dataOraUltimaModifica;
	}

	public void setDataOraUltimaModifica(Date dataOraUltimaModifica) {
		this.dataOraUltimaModifica = dataOraUltimaModifica;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getIdDataMatrix() {
		return this.idDataMatrix;
	}

	public void setIdDataMatrix(int idDataMatrix) {
		this.idDataMatrix = idDataMatrix;
	}

//	public Integer getIdProdotto() {
//		return this.idProdotto;
//	}
//
//	public void setIdProdotto(Integer idProdotto) {
//		this.idProdotto = idProdotto;
//	}

	public String getPartNumber() {
		return this.partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Boolean getEliminato() {
		return eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Set<DatamatrixFasiProcesso> getTrattamenti() {
		return trattamenti;
	}

	public void setTrattamenti(Set<DatamatrixFasiProcesso> trattamenti) {
		this.trattamenti = trattamenti;
	}

	public Utenti getUtenteUltimaModifica() {
		return utenteUltimaModifica;
	}

	public void setUtenteUltimaModifica(Utenti utenteUltimaModifica) {
		this.utenteUltimaModifica = utenteUltimaModifica;
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
	
	public DatamatrixFasiProcesso trattamentoHtt() {
		DatamatrixFasiProcesso trattamentoHtt = null;
		if(this.trattamenti != null && !this.trattamenti.isEmpty()) {
			trattamentoHtt = this.trattamenti.iterator().next();
		}
		return trattamentoHtt;
	}

	public String trattamentoHttNomeUtente() {
		return (trattamentoHtt() != null && trattamentoHtt().getUtenteOperatore() != null) ? trattamentoHtt().getUtenteOperatore().getNomeCognome() : "";  
	}

	public String trattamentoHttNumeroFornata() {
		return (trattamentoHtt() != null && trattamentoHtt().getNumero() != null) ? trattamentoHtt().getNumero() : "";  
	}

	public Double trattamentoHttValoreDurezza() {
		return (trattamentoHtt() != null && trattamentoHtt().getValore() != null) ? trattamentoHtt().getValore() : null;  
	}
	
	public String trattamentoHttNumeroFornataCodificato() {
		if (trattamentoHttNumeroFornata() == null) {
			return "";
		}
		String valore = trattamentoHttNumeroFornata();
		if(trattamentoHtt() != null && trattamentoHtt().getAzienda() != null && trattamentoHtt().getAzienda().getCodiceValoreDurezza() != null) {
			valore = trattamentoHtt().getAzienda().getCodiceValoreDurezza() + " " + valore;
		}
		return valore;
	}
	
	public String trattamentoHttValoreDurezzaCodificato() {
		if (trattamentoHttValoreDurezza() == null) {
			return "";
		}
		String valore = CommonUtils.formatDouble(trattamentoHttValoreDurezza(), CommonUtils.TWODECIMALFORMAT);
//		if(trattamentoHtt().getAzienda() != null && trattamentoHtt().getAzienda().getCodiceValoreDurezza() != null) {
//			valore = trattamentoHtt().getAzienda().getCodiceValoreDurezza() + " " + valore;
//		}
		return valore;
	}
	
	public Date trattamentoHttDataOra() {
		return (trattamentoHtt() != null && trattamentoHtt().getDataOra() != null) ? trattamentoHtt().getDataOra() : null;  
	}

	public Prodotti getProddoto() {
		return prodotto;
	}

	public void setProddoto(Prodotti proddoto) {
		this.prodotto = proddoto;
	}
	
	public String getCodiceProdotto() {
		return this.prodotto == null ? "" : this.prodotto.getCodiceProdotto();
	}
}