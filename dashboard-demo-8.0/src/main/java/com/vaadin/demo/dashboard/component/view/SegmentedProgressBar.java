package com.vaadin.demo.dashboard.component.view;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class SegmentedProgressBar extends CustomComponent {

    // ✅ Colori statici predefiniti
    public static final String COLOR_VERDE = "#4CAF50";
    public static final String COLOR_GIALLO = "#FFC107";
    public static final String COLOR_GRIGIO = "#CCCCCC";
    public static final String COLOR_BLU = "#2196F3";

    private final HorizontalLayout barContainer = new HorizontalLayout();
    private final List<Label> segments = new ArrayList<>();
    private final List<String> segmentColors = new ArrayList<>();
    private int totalSteps;
    private int currentStep = 0;

    public SegmentedProgressBar(int totalSteps) {
        this.totalSteps = totalSteps;

        // Layout interno per segmenti
        barContainer.setSpacing(false);
        barContainer.setMargin(false);
        barContainer.setWidth("100%");

        buildSegments();

        
        /*
        barContainer.setStyleName("v-layout");
        barContainer.setStyleName("progress-bar-inner-wrapper");
        barContainer.setStyle("background-color: #eeeeee; border: 1px solid #cccccc; border-radius: 8px; padding: 2px;");
*/
        
/*
        // ✅ Contenitore con bordo + sfondo
        CssLayout outerWrapper = new CssLayout();
        outerWrapper.setWidth("100%");
        outerWrapper.setStyleName("v-csslayout");
        outerWrapper.setStyleName("progress-bar-wrapper");
        outerWrapper.setStyleName("padding:10px; border:1px solid #ccc; border-radius:6px; background-color:#f9f9f9;");

        outerWrapper.setCaptionAsHtml(true);
        outerWrapper.setCaption(""); // opzionale: puoi aggiungere un titolo se vuoi
        outerWrapper.addComponent(barContainer);
*/

        CssLayout outerWrapper = new CssLayout();
        outerWrapper.setWidth("100%");
        outerWrapper.setStyleName("v-csslayout"); // stile base Vaadin
        //outerWrapper.setStyleName("background-color: #f9f9f9; border: 1px solid #ccc; border-radius: 8px; padding: 6px;");
        outerWrapper.setStyleName("background-color: #f9f9f9; border: 1px solid #ccc; border-radius: 8px; padding: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);");
        outerWrapper.setCaptionAsHtml(true);
        outerWrapper.setCaption(""); // opzionale
        outerWrapper.addComponent(barContainer);
        
/*
        Panel outerWrapper = new Panel();
        outerWrapper.setWidth("100%");
        outerWrapper.setContent(barContainer);
        outerWrapper.addStyleName("light"); // stile Vaadin nativo
*/

        
        setCompositionRoot(outerWrapper);
   
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }
    
    private void buildSegments() {
        barContainer.removeAllComponents();
        segments.clear();
        segmentColors.clear();

        for (int i = 0; i < totalSteps; i++) {
            Label segment = new Label();
            segment.setContentMode(ContentMode.HTML);

            if (totalSteps == 1) segment.setWidth("100%");
			else segment.setWidth("90%");
            
            segment.setHeightUndefined();

            segments.add(segment);
            segmentColors.add(COLOR_VERDE); // colore di default

            barContainer.addComponent(segment);
            barContainer.setExpandRatio(segment, 1.0f);
        }

        updateSegmentColors();
    }

    public void setCurrentStep(int step) {
        if (step < 0 || step > totalSteps) {
            throw new IllegalArgumentException("Il passo deve essere compreso tra 0 e " + totalSteps);
        }
        this.currentStep = step;
        updateSegmentColors();
    }

    public void setSegmentColor(int index, String hexColor) {
        if (index < 0 || index >= totalSteps) {
            throw new IllegalArgumentException("Indice fuori intervallo");
        }
        segmentColors.set(index, hexColor);
        updateSegmentColors();
    }

    public void setAllSegmentsColor(String hexColor) {
        for (int i = 0; i < segmentColors.size(); i++) {
            segmentColors.set(i, hexColor);
        }
        updateSegmentColors();
    }

    private void updateSegmentColors() {
        for (int i = 0; i < totalSteps; i++) {
            Label segment = segments.get(i);
            String color;

            if (i <= currentStep - 1) {
                color = segmentColors.get(i); // completati
            } else {
                color = COLOR_GRIGIO; // non ancora completati
            }
            
            // Bordi arrotondati solo su primo e ultimo segmento
            String borderRadius = "";
            if (totalSteps == 1) {
                // Solo un segmento: tutti i bordi arrotondati
                borderRadius = "border-radius:4px;";
            } else if (i == 0) {
                borderRadius = "border-top-left-radius:4px; border-bottom-left-radius:4px;";
            } else if (i == totalSteps - 1) {
                borderRadius = "border-top-right-radius:4px; border-bottom-right-radius:4px;";
            }            

            String html = "<div style='width:100%;height:20px;background-color:" + color + ";" + borderRadius + "'></div>";
            
            //String html = "<div style='width:100%;height:15px;background-color:" + color +  ";border-radius:4px;margin-right:6px;'></div>";

            segment.setContentMode(ContentMode.HTML);
            segment.setValue(html);
        }
    }
    
    public void setLastNStepsColor(int lastNSteps, String color) {
        // Protezione: se currentStep è 0, colora tutto di grigio
        if (currentStep <= 0) {
            for (int i = 0; i < totalSteps; i++) {
                setSegmentColor(i, COLOR_GRIGIO);
            }
            return;
        }

        // Calcola da quale indice iniziare a colorare (non meno di 0)
        int startColorIndex = Math.max(0, currentStep - lastNSteps);

        for (int i = 0; i < totalSteps; i++) {
            if (i < startColorIndex) {
                // Segmenti completati prima degli ultimi N → colore default
                setSegmentColor(i, COLOR_VERDE);
            } else if (i < currentStep) {
                // Ultimi N step completati → colore personalizzato
                setSegmentColor(i, color);
            } else {
                // Step non ancora raggiunti → colore grigio
                setSegmentColor(i, COLOR_GRIGIO);
            }
        }
    }
}
