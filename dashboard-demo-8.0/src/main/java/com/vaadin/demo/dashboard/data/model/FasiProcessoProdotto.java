
package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "FasiProcessoProdotto")
public class FasiProcessoProdotto implements Serializable {

    private static final long serialVersionUID = 1L;

    // ===== CHIAVE COMPOSTA =====
    @Embeddable
    public static class FasiProcessoProdottoKey implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "idProdotto")
        private Integer idProdotto;

        @Column(name = "idFaseProcesso")
        private Integer idFaseProcesso;

        // Costruttore di default richiesto da Hibernate
        public FasiProcessoProdottoKey() {
        }

        public FasiProcessoProdottoKey(Integer idProdotto, Integer idFaseProcesso) {
            this.idProdotto = idProdotto;
            this.idFaseProcesso = idFaseProcesso;
        }

        public Integer getIdProdotto() {
            return idProdotto;
        }

        public void setIdProdotto(Integer idProdotto) {
            this.idProdotto = idProdotto;
        }

        public Integer getIdFaseProcesso() {
            return idFaseProcesso;
        }

        public void setIdFaseProcesso(Integer idFaseProcesso) {
            this.idFaseProcesso = idFaseProcesso;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FasiProcessoProdottoKey)) return false;
            FasiProcessoProdottoKey that = (FasiProcessoProdottoKey) o;
            if (idProdotto != null ? !idProdotto.equals(that.idProdotto) : that.idProdotto != null) return false;
            return idFaseProcesso != null ? idFaseProcesso.equals(that.idFaseProcesso) : that.idFaseProcesso == null;
        }

        @Override
        public int hashCode() {
            int result = idProdotto != null ? idProdotto.hashCode() : 0;
            result = 31 * result + (idFaseProcesso != null ? idFaseProcesso.hashCode() : 0);
            return result;
        }
    }

    @EmbeddedId
    private FasiProcessoProdottoKey id;

    @ManyToOne
    @MapsId("idProdotto")
    @JoinColumn(name = "idProdotto")
    private Prodotti prodotto;

    @ManyToOne
    @MapsId("idFaseProcesso")
    @JoinColumn(name = "idFaseProcesso")
    private FasiProcesso faseProcesso;

    @Column(name = "ordine", nullable = false)
    private Integer ordine;

    @Column(name = "verificaSaltoProcessoFasiPrecedenti")
    private Boolean verificaSaltoProcessoFasiPrecedenti;

    @Column(name = "letturaQrCodeObbligatoriaInSwTracciabilita")
    private Boolean letturaQrCodeObbligatoriaInSwTracciabilita;

    // getter/setter

    public FasiProcessoProdottoKey getId() {
        return id;
    }

    public void setId(FasiProcessoProdottoKey id) {
        this.id = id;
    }

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


/**
 * The persistent class for the FasiProcessoProdotto database table.
 *
@Entity
public class FasiProcessoProdotto implements Serializable {
	private static final long serialVersionUID = 1L;


	@Embeddable
	class FasiProcessoProdottoKey implements Serializable { 
	    @Column(name = "idProdotto")
	    Integer idProdotto;
	 
	    @Column(name = "idFaseProcesso")
	    Integer idFaseProcesso;
	}
	
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
 */
