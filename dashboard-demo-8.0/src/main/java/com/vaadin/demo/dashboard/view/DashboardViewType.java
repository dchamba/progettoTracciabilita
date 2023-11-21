package com.vaadin.demo.dashboard.view;


import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.view.anime.VerificaUtilizzoImballiAnime;
import com.vaadin.demo.dashboard.view.anime.VistaImballiAnime;
import com.vaadin.demo.dashboard.view.datamatrix.DatamatrixView;
import com.vaadin.demo.dashboard.view.datamatrix.NewDatamatrixView;
import com.vaadin.demo.dashboard.view.datamatrix.VerificaTracciablitaQrCode;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseAnimeUtilizzoView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseControlloFinaleView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseControlloRXView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseFusioneView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseLavorazioneView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseLiquidiPenetrantiView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseProvaTenutaView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseSabbiaturaView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseSbavaturaView;
import com.vaadin.demo.dashboard.view.fasiprocesso.FaseSterraturaView;
import com.vaadin.demo.dashboard.view.packinglist.PackingListATTCOView;
import com.vaadin.demo.dashboard.view.packinglist.PackingListCCUView;
import com.vaadin.demo.dashboard.view.packinglist.PackingListFia0504;
import com.vaadin.demo.dashboard.view.packinglist.PackingListFia0505;
import com.vaadin.demo.dashboard.view.packinglist.PackingListFia10;
import com.vaadin.demo.dashboard.view.packinglist.PackingListGestampView;
import com.vaadin.demo.dashboard.view.packinglist.PackingListPAN11View;
import com.vaadin.demo.dashboard.view.trattamenti.TrattamentoView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
		DASHBOARD("home", "Home", DashboardView.class, FontAwesome.HOME, true, null), 
		ENTRYDATAMATRIX("GESTIONE_DATAMATRIX", ViewUtils.titoloEntryDataMatrix, NewDatamatrixView.class, FontAwesome.PLUS_CIRCLE, false, PermessiUtentiLista.GESTIONE_DATAMATRIX),
    	DATAMATRIX("CONSULTAZIONE_DATAMATRIX", ViewUtils.titoloDataMatrix, VerificaTracciablitaQrCode.class, FontAwesome.BARCODE, false, PermessiUtentiLista.CONSULTAZIONE_DATAMATRIX),
    	TRATTAMENTOHTT("GESTIONE_TRATTAMENTO", ViewUtils.titoloTrattamentoHtt, TrattamentoView.class, FontAwesome.FIRE, false, PermessiUtentiLista.GESTIONE_TRATTAMENTO),
    	IMBALLI_ANIME("IMBALLI_ANIME", ViewUtils.titoloVistaImballiAnime, VistaImballiAnime.class, FontAwesome.FIRE, false, PermessiUtentiLista.IMBALLI_ANIME),
    	IMBALLI_ANIME_UTILIZZO("IMBALLI_ANIME_UTILIZZO", ViewUtils.titoloVistaImballiAnimeUtilizzo, VerificaUtilizzoImballiAnime.class, FontAwesome.FIRE, false, PermessiUtentiLista.IMBALLI_ANIME_UTILIZZO),
    	FASE_PROCESSO_FIN("FASE_PROCESSO_FIN", ViewUtils.titoloFaseProcessoFin, FaseControlloFinaleView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_FIN),
		FASE_PROCESSO_TEN("FASE_PROCESSO_TEN", ViewUtils.titoloFaseProcessoTen, FaseProvaTenutaView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_TEN),
		FASE_PROCESSO_LAV("FASE_PROCESSO_LAV", ViewUtils.titoloFaseProcessoLav, FaseLavorazioneView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_LAV),
		FASE_PROCESSO_SBA("FASE_PROCESSO_SBA", ViewUtils.titoloFaseProcessoSba, FaseSbavaturaView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_SBA),
		FASE_PROCESSO_SAB("FASE_PROCESSO_SAB", ViewUtils.titoloFaseProcessoSab, FaseSabbiaturaView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_SAB),
		FASE_PROCESSO_FUS("FASE_PROCESSO_FUS", ViewUtils.titoloFaseProcessoFus, FaseFusioneView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_FUS),
		FASE_PROCESSO_STE("FASE_PROCESSO_STE", ViewUtils.titoloFaseProcessoSte, FaseSterraturaView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_STE),
		FASE_PROCESSO_LIQ("FASE_PROCESSO_LIQ", ViewUtils.titoloFaseProcessoLiq, FaseLiquidiPenetrantiView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_LIQ),
		FASE_PROCESSO_RX("FASE_PROCESSO_RX", ViewUtils.titoloFaseProcessoRX, FaseControlloRXView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_RX),
		FASE_PROCESSO_ANU("FASE_PROCESSO_ANU", ViewUtils.titoloFaseProcessoAnu, FaseAnimeUtilizzoView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_ANU),
		FASE_PROCESSO_PACKINGLISTCCU("FASE_PROCESSO_PACKINGLISTCCU", ViewUtils.titoloFaseProcessoPackingListCCU, PackingListCCUView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTCCU),
		FASE_PROCESSO_PACKINGLISTACFSHIELD("FASE_PROCESSO_PACKINGLISTACFSHIELD", ViewUtils.titoloFaseProcessoPackingListPan11, PackingListPAN11View.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTACFSHIELD),
		FASE_PROCESSO_PACKINGLISTGESTAMP("FASE_PROCESSO_PACKINGLISTGESTAMP", ViewUtils.titoloFaseProcessoPackingListGestamp, PackingListGestampView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTGESTAMP),
		FASE_PROCESSO_PACKINGLISTFIA10("FASE_PROCESSO_PACKINGLISTFIA10", ViewUtils.titoloFaseProcessoPackingListFia10, PackingListFia10.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA10),
		FASE_PROCESSO_PACKINGLISTFIA0505("FASE_PROCESSO_PACKINGLISTFIA0505", ViewUtils.titoloFaseProcessoPackingListFia0505, PackingListFia0505.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA0505),
		FASE_PROCESSO_PACKINGLISTFIA0504("FASE_PROCESSO_PACKINGLISTFIA0504", ViewUtils.titoloFaseProcessoPackingListFia0504, PackingListFia0504.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA0504),
		FASE_PROCESSO_PACKINGLISTATTCO("FASE_PROCESSO_PACKINGLISTATTCO", ViewUtils.titoloFaseProcessoPackingListATTCO, PackingListATTCOView.class, FontAwesome.FIRE, false, PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTATTCO);
	
    	
    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private final String menuCaption;
    private final PermessiUtentiLista permesso;

    private DashboardViewType(final String viewName, final String menuCaption, final Class<? extends View> viewClass, final Resource icon, final boolean stateful, final PermessiUtentiLista permesso) {
        this.viewName = viewName;
        this.menuCaption = menuCaption;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.permesso = permesso;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : DashboardViewType.values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

	public String getMenuCaption() {
		return menuCaption;
	}

	public PermessiUtentiLista getPermesso() {
		return permesso;
	}
}
