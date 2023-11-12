package com.vaadin.demo.dashboard.data.repository;

public class RepositoryProvider {

	private static RepositoryDatamatrix repositoryDatamatrix = null;
	private static RepositoryUtenti repositoryUtenti = null;
	private static RepositoryUtentiPermessi repositoryUtentiPermessi = null;
	private static RepositoryProdotti repositoryProdotti = null;
	private static RepositoryDatamatrixFasiProcesso repositoryDatamatrixTrattamenti = null;
	private static RepositoryDatamatrixFasiProcessoTT repositoryDatamatrixTrattamentiTT = null;
	private static RepositoryFasiProcesso repositoryFasiProcesso = null;
	private static RepositoryFasiProcessoProdotti repositoryFasiProcessoProdotti = null;
	private static RepositoryImballi repositoryImballi = null;
	private static RepositoryProveTenuta repositoryProveTenuta = null;
	private static RepositoryConfig repositoryConfig = null;
	private static RepositoryAnimeImballi repositoryAnimeImballi = null;
	
	
	public static RepositoryDatamatrix getRepositoryDatamatrix() {
		if (RepositoryProvider.repositoryDatamatrix == null) {
			RepositoryProvider.repositoryDatamatrix = new RepositoryDatamatrix();
		}
		return RepositoryProvider.repositoryDatamatrix;
	}
	
	public static RepositoryAnimeImballi repositoryAnimeImballi() {
		if (RepositoryProvider.repositoryAnimeImballi == null) {
			RepositoryProvider.repositoryAnimeImballi = new RepositoryAnimeImballi();
		}
		return RepositoryProvider.repositoryAnimeImballi;
	}
	
	public static RepositoryImballi repositoryImballi() {
		if (RepositoryProvider.repositoryImballi == null) {
			RepositoryProvider.repositoryImballi = new RepositoryImballi();
		}
		return RepositoryProvider.repositoryImballi;
	}

	public static RepositoryUtenti getRepositoryUtenti() {
		if (RepositoryProvider.repositoryUtenti == null) {
			RepositoryProvider.repositoryUtenti = new RepositoryUtenti();
		}
		return RepositoryProvider.repositoryUtenti;
	}

	public static RepositoryProdotti getRepositoryProdotti() {
		if (RepositoryProvider.repositoryProdotti == null) {
			RepositoryProvider.repositoryProdotti = new RepositoryProdotti();
		}
		return RepositoryProvider.repositoryProdotti;
	}

	public static RepositoryDatamatrixFasiProcesso getRepositoryDatamatrixTrattamenti() {
		if (RepositoryProvider.repositoryDatamatrixTrattamenti == null) {
			RepositoryProvider.repositoryDatamatrixTrattamenti = new RepositoryDatamatrixFasiProcesso();
		}
		return RepositoryProvider.repositoryDatamatrixTrattamenti;
	}

	public static RepositoryDatamatrixFasiProcessoTT getRepositoryDatamatrixTrattamentiTT() {
		if (RepositoryProvider.repositoryDatamatrixTrattamentiTT == null) {
			RepositoryProvider.repositoryDatamatrixTrattamentiTT = new RepositoryDatamatrixFasiProcessoTT();
		}
		return RepositoryProvider.repositoryDatamatrixTrattamentiTT;
	}


	public static RepositoryUtentiPermessi repositoryUtentiPermessi() {
		if (RepositoryProvider.repositoryUtentiPermessi == null) {
			RepositoryProvider.repositoryUtentiPermessi = new RepositoryUtentiPermessi();
		}
		return RepositoryProvider.repositoryUtentiPermessi;
	}

	public static RepositoryFasiProcesso repositoryFasiProcesso() {
		if (RepositoryProvider.repositoryFasiProcesso == null) {
			RepositoryProvider.repositoryFasiProcesso = new RepositoryFasiProcesso();
		}
		return RepositoryProvider.repositoryFasiProcesso;
	}

	public static RepositoryFasiProcessoProdotti repositoryFasiProcessoProdotti() {
		if (RepositoryProvider.repositoryFasiProcessoProdotti == null) {
			RepositoryProvider.repositoryFasiProcessoProdotti = new RepositoryFasiProcessoProdotti();
		}
		return RepositoryProvider.repositoryFasiProcessoProdotti;
	}
	
	public static RepositoryProveTenuta getRepositoryProveTenuta() {
		if (RepositoryProvider.repositoryProveTenuta == null) {
			RepositoryProvider.repositoryProveTenuta = new RepositoryProveTenuta();
		}
		return RepositoryProvider.repositoryProveTenuta;
	}
	
	public static RepositoryConfig getRepositoryConfig() {
		if (RepositoryProvider.repositoryConfig == null) {
			RepositoryProvider.repositoryConfig = new RepositoryConfig();
		}
		return RepositoryProvider.repositoryConfig;
	}
}
