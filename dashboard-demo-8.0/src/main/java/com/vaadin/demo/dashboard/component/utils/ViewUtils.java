package com.vaadin.demo.dashboard.component.utils;

import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;;

public class ViewUtils {
	
	public static String titoloEntryDataMatrix = "RX DataMatrix scanning";
	public static String titoloTrattamentoHtt = "HT DataMatrix scanning";
	public static String titoloVerificDataMatrix = "Verifica QrCode";
	public static String titoloVistaImballiAnime = "Imballi anime";
	public static String titoloVistaImballiAnimeUtilizzo = "Imballi anime utilizzo";
	public static String titoloVistaTracciabilitàDatamatrix = "Tracciabilità datamatrix";
	public static String titoloVistaVerificaQrCode = "Verifica QrCode";
	
	
	public static String titoloFaseProcessoFin = "Controllo finale";
	public static String titoloFaseProcessoTen = "Prova tenuta";
	public static String titoloFaseProcessoLav = "Lavorazione";
	public static String titoloFaseProcessoSba = "Sbavatura";
	public static String titoloFaseProcessoSab = "Sabbiatura";
	public static String titoloFaseProcessoFus = "Fusione";
	public static String titoloFaseProcessoSte = "Sterratura";
	public static String titoloFaseProcessoRX = "Controllo RX";
	public static String titoloFaseProcessoLiq = "Liquidi penetranti";
	public static String titoloFaseProcessoAnu = "Verifica anime utilizzo";
	
	public static String titoloFaseProcessoPackingListGestamp = "Packing list - Gestamp";
	public static String titoloFaseProcessoPackingListPan11 = "Packing list - PAN0011";
	public static String titoloFaseProcessoPackingListCCU = "Packing list - Panasonic CCU";
	public static String titoloFaseProcessoPackingListFia10 = "Packing list - FIA10";
	public static String titoloFaseProcessoPackingListFia1201 = "Packing list - FIA1201";
	public static String titoloFaseProcessoPackingListFia0505 = "Packing list - FIA0505";
	public static String titoloFaseProcessoPackingListFia0504 = "Packing list - FIA0504";
	public static String titoloFaseProcessoPackingListATTCO = "Packing list - ATTCO";
	public static String titoloFaseProcessoPackingListOMR0041 = "Packing list - OMR0041";
	public static String titoloFaseProcessoPackingListOMR0050 = "Packing list - OMR0050";
	public static String titoloFaseProcessoPackingListOmr01e02 = "Packing list - OMR01 e OMR02";
	public static String titoloFaseProcessoPackingListOmr03e04 = "Packing list - OMR03 e OMR04";
	public static String titoloFaseProcessoPackingListOmr05e06 = "Packing list - OMR05 e OMR06";
	public static String titoloFaseProcessoPackingListOmr07e08 = "Packing list - OMR07 e OMR08";
	public static String titoloFaseProcessoPackingListOmr41 = "Packing list - OMR41";
	public static String titoloFaseProcessoPackingListOmr50 = "Packing list - OMR50";
	
	public static String titoloAgiornamentoDatiLogistica = "Aggiornamento dati logistica";

	public static String titoloStampaEtichettaImballo = "Stampa etichetta imballo";
	public static String nomeStampaEtichettaImballoView = "stampaEtichettaImballo";
	
	public static int defaultPaginationLimit = 10;
	
	public static void showSuccessfullNotification(String caption) {
        Notification success = new Notification(caption, Notification.Type.WARNING_MESSAGE);
        //success.setDelayMsec(1500);
        success.setStyleName("bar success small");
        success.setPosition(Position.MIDDLE_RIGHT);
        success.show(Page.getCurrent());
	}

	public static void showErrorNotification(String caption) {
		showErrorNotification(caption, 1000);
	}

	public static void showErrorNotificationWithDelay(String caption, Integer delayMSec) {
		showErrorNotification(caption, delayMSec);
	}
	
	private static void showErrorNotification(String caption, Integer delayMSec) {
		//PopupTextFieldContent popup = new PopupTextFieldContent();
		
        Notification error = new Notification(caption, Type.ERROR_MESSAGE);
        error.setDelayMsec(delayMSec);
        //success.setStyleName("bar success small");
        error.setPosition(Position.TOP_RIGHT);
        error.show(Page.getCurrent());
	}
	
	public static void showNormalNotification(String titolo, String message) {
		Notification notification = new Notification( "Welcome to Dashboard Demo");
		notification.setDescription("<span>This application is not real, it only demonstrates an application built with the <a href=\"https://vaadin.com\">Vaadin framework</a>.</span> <span>No username or password is required, just click the <b>Sign In</b> button to continue.</span>");
		notification.setHtmlContentAllowed(true);
		notification.setStyleName("tray dark small closable login-help");
		notification.setPosition(Position.BOTTOM_CENTER);
		notification.setDelayMsec(1500);
		notification.show(Page.getCurrent());
	}
	
}