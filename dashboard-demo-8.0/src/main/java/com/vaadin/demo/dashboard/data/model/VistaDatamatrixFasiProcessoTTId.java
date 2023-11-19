package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;

import javax.persistence.Id;

public class VistaDatamatrixFasiProcessoTTId implements Serializable {
	
	@Id private Integer idDatamatrixFaseProcessoTT;
	@Id private Integer idDatamatrixFaseProcesso;

	public VistaDatamatrixFasiProcessoTTId() { }
	
    public VistaDatamatrixFasiProcessoTTId(Integer idDatamatrixFaseProcessoTT, Integer idDatamatrixFaseProcesso) {
        this.idDatamatrixFaseProcessoTT = idDatamatrixFaseProcessoTT;
        this.idDatamatrixFaseProcesso = idDatamatrixFaseProcesso;
    }
}