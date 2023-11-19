package com.vaadin.demo.dashboard.component.utils;

public class ProveTenutaUtils {

	public static String NON_PROVATO_PT = "NON_PROVATO_PT";
	public static String PROVATO_KO = "PROVATO_KO";
	public static String PROVATO_OK = "PROVATO_OK";
	
	public static void verificaEsitoTenutaELanciaErrore(String esitoProvaTenuta) throws Exception {
		if(esitoProvaTenuta.equals(ProveTenutaUtils.NON_PROVATO_PT)) {
            throw new Exception("ATTENZIONE! Questo pezzo NON è stato provato a tenuta");
		} else if(esitoProvaTenuta.equals(ProveTenutaUtils.PROVATO_KO)) {
            throw new Exception("ATTENZIONE! Questo pezzo risulta KO a provato tenuta");
		} else {
			
		}
	}
}
