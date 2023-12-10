package com.vaadin.demo.dashboard.component.model;

import java.util.HashMap;

public class StatoBancale {
	
	private boolean isBoxesQtyPerPalletComplete;
	
	private int qtyOfBoxesInThePallet;

	private int qtyOfPcsInThePallet;
	
	private HashMap<String, Integer> boxesWithMissingPcs = new HashMap<String, Integer>();

	private int standardPcsQtyPerBoxes;
	
	private int standardPcsQtyPerPallet;
	
	private int standardBoxesQtyPerPallet;
	
	public StatoBancale() {	}

	public boolean isBoxesQtyPerPalletComplete() {
		return isBoxesQtyPerPalletComplete;
	}

	public void setBoxesQtyPerPalletComplete(boolean isBoxesQtyPerPalletComplete) {
		this.isBoxesQtyPerPalletComplete = isBoxesQtyPerPalletComplete;
	}

	public int getQtyOfBoxesInThePallet() {
		return qtyOfBoxesInThePallet;
	}

	public void setQtyOfBoxesInThePallet(int qtyOfBoxesInThePallet) {
		this.qtyOfBoxesInThePallet = qtyOfBoxesInThePallet;
	}

	public boolean isPcsQtyForPalletComplete() {
		return this.standardPcsQtyPerPallet == this.qtyOfPcsInThePallet;
	}

	public int getQtyOfPcsInThePallet() {
		return qtyOfPcsInThePallet;
	}

	public void setQtyOfPcsInThePallet(int qtyOfPcsInThePallet) {
		this.qtyOfPcsInThePallet = qtyOfPcsInThePallet;
	}

	public HashMap<String, Integer> getBoxesWithMissingPcs() {
		return boxesWithMissingPcs;
	}

	public void setBoxesWithMissingPcs(HashMap<String, Integer> boxesWithMissingPcs) {
		this.boxesWithMissingPcs = boxesWithMissingPcs;
	}

	public int getStandardPcsQtyPerBoxes() {
		return standardPcsQtyPerBoxes;
	}

	public void setStandardPcsQtyPerBoxes(int standardQtyPerBoxes) {
		this.standardPcsQtyPerBoxes = standardQtyPerBoxes;
	}

	public int getQtyOfMissingPcsInThePallet() {
		return getStandardPcsQtyPerPallet() - getQtyOfPcsInThePallet();
	}

	public int getStandardPcsQtyPerPallet() {
		return this.getStandardPcsQtyPerBoxes() * this.getStandardBoxesQtyPerPallet();
	}

	public void setStandardBoxesQtyPerPallet(int standardBoxesQtyPerPallet) {
		this.standardBoxesQtyPerPallet = standardBoxesQtyPerPallet;
	}

	public int getStandardBoxesQtyPerPallet() {
		return standardBoxesQtyPerPallet;
	}

}