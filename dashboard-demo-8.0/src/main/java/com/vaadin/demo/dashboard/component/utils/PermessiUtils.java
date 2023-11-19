package com.vaadin.demo.dashboard.component.utils;

import javax.persistence.Transient;

public class PermessiUtils {
	
	@Transient
	public static String tipoUtenteSuperUser = "S";
	@Transient
	public static String tipoUtenteUtenteNormale = "N";
	@Transient
	public static String tipoUtenteUtenteAmministratore = "A";
	
	public static String ricerca_permessoFaseProcessoSingolo = "FASE_PROCESSO_";
	
	public enum PermessiUtentiLista {
		DASHBOARD, 
		GESTIONE_DATAMATRIX,
		IMBALLI_ANIME,
		IMBALLI_ANIME_UTILIZZO,
		CONSULTAZIONE_DATAMATRIX,
		GESTIONE_TRATTAMENTO,
		FASE_PROCESSO_HB,
		FASE_PROCESSO_TD,
		FASE_PROCESSO_FUS,
		FASE_PROCESSO_STE,
		FASE_PROCESSO_SAB,
		FASE_PROCESSO_SBA,
		FASE_PROCESSO_LAV,
		FASE_PROCESSO_TEN,
		FASE_PROCESSO_FIN,
		FASE_PROCESSO_LIQ,
		FASE_PROCESSO_RX,
		FASE_PROCESSO_ANU,
		FASE_PROCESSO_PACKINGLISTCCU,
		FASE_PROCESSO_PACKINGLISTATTCO,
		FASE_PROCESSO_PACKINGLISTGESTAMP,
		FASE_PROCESSO_PACKINGLISTFIA0505,
		FASE_PROCESSO_PACKINGLISTFIA0504,
		FASE_PROCESSO_PACKINGLISTFIA10,
		FASE_PROCESSO_PACKINGLISTACFSHIELD;
	}
}
