
package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the FasiProcessoProdotto database table.
 * 
 */
@Entity
public class FasiProcessoProdotto implements Serializable {
	private static final long serialVersionUID = 1L;

    @EmbeddedId
    FasiProcessoProdottoKey id;

    @ManyToOne
    @MapsId("idProdotto")
    @JoinColumn(name = "idProdotto")
	private
    Prodotti prodotto;
 
    @ManyToOne
    @MapsId("idFaseProcesso")
    @JoinColumn(name = "idFaseProcesso")
	private
    FasiProcesso faseProcesso;
 
    private Integer ordine;

    private Boolean verificaSaltoProcessoFasiPrecedenti;
    
    private Boolean letturaQrCodeObbligatoriaInSwTracciabilita;
    
	public FasiProcesso getFaseProcesso() {
		return faseProcesso;
	}

	public void setFaseProcesso(FasiProcesso faseProcesso) {
		this.faseProcesso = faseProcesso;
	}

	public Prodotti getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotti prodotto) {
		this.prodotto = prodotto;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public Boolean getVerificaSaltoProcessoFasiPrecedenti() {
		return verificaSaltoProcessoFasiPrecedenti;
	}

	public void setVerificaSaltoProcessoFasiPrecedenti(Boolean verificaSaltoProcessoFasiPrecedenti) {
		this.verificaSaltoProcessoFasiPrecedenti = verificaSaltoProcessoFasiPrecedenti;
	}

	public Boolean getLetturaQrCodeObbligatoriaInSwTracciabilita() {
		return letturaQrCodeObbligatoriaInSwTracciabilita;
	}

	public void setLetturaQrCodeObbligatoriaInSwTracciabilita(Boolean letturaQrCodeObbligatoriaInSwTracciabilita) {
		this.letturaQrCodeObbligatoriaInSwTracciabilita = letturaQrCodeObbligatoriaInSwTracciabilita;
	}
}

@Embeddable
class FasiProcessoProdottoKey implements Serializable {
	private static final long serialVersionUID = 1L;
 
    @Column(name = "idProdotto")
    Integer idProdotto;
 
    @Column(name = "idFaseProcesso")
    Integer idFaseProcesso;
}