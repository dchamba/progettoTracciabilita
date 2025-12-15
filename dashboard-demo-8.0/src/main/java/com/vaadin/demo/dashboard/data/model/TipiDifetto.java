package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "TipiDifetto")
public class TipiDifetto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoDifetto;    

	@ManyToOne
	@JoinColumn(name ="idFaseProcesso")
	private FasiProcesso faseProcesso;
    
    private String codiceDifetto;
    private String descrizione;
    private Boolean attivo;
    private Boolean eliminato;

    public TipiDifetto() {
        this.attivo = true;
        this.eliminato = false;
    }

    public Integer getIdTipoDifetto() {
        return idTipoDifetto;
    }

    public void setIdTipoDifetto(Integer idTipoDifetto) {
        this.idTipoDifetto = idTipoDifetto;
    }

    public FasiProcesso getFaseProcesso() {
        return faseProcesso;
    }

    public void setFaseProcesso(FasiProcesso faseProcesso) {
        this.faseProcesso = faseProcesso;
    }

    public String getCodiceDifetto() {
        return codiceDifetto;
    }

    public void setCodiceDifetto(String codiceDifetto) {
        this.codiceDifetto = codiceDifetto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }

    public Boolean getEliminato() {
        return eliminato;
    }

    public void setEliminato(Boolean eliminato) {
        this.eliminato = eliminato;
    }

    @Override
    public String toString() {
        return codiceDifetto + " - " + descrizione;
    }
}