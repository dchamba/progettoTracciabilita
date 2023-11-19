package com.vaadin.demo.dashboard.component.utils;

import java.util.regex.Pattern;

import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;

public class ImballiAnimeUtils {
	
	public static void verificaFormatoQrCodeImballiAnime(String codiceDataMatrixInserito) throws Exception {
		String formatoQrCodeImballoAnime = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.animeFormatoQrCodeImballoAnime);
		if(formatoQrCodeImballoAnime != null && !formatoQrCodeImballoAnime.isEmpty()) {
        	boolean isFormatoCorretto = Pattern.matches(formatoQrCodeImballoAnime, codiceDataMatrixInserito);
        	if(!isFormatoCorretto) {
	            throw new Exception("Formato QrCode imballo non corretto");
        	}
		} else {
            throw new Exception("Non è stato configurato alcun formato QrCode per imballi anime");
		}
	}
}
