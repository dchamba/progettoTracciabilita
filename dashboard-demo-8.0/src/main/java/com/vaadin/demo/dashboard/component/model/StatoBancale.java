package com.vaadin.demo.dashboard.component.model;

import java.util.HashMap;

public class StatoBancale {
	
	private boolean isBoxesQtyPerPalletComplete;
	
	private boolean isPcsQtyForPalletComplete;

	private int qtyOfBoxesInThePallet;

	private int qtyOfPcsInThePallet;
	
	private HashMap<String, Integer> boxesWithMissingPcs = new HashMap<String, Integer>();

	private int standardQtyPerBoxes;
	
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
		return isPcsQtyForPalletComplete;
	}

	public void setPcsQtyForPalletComplete(boolean isPcsQtyForPalletComplete) {
		this.isPcsQtyForPalletComplete = isPcsQtyForPalletComplete;
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

	public int getStandardQtyPerBoxes() {
		return standardQtyPerBoxes;
	}

	public void setStandardQtyPerBoxes(int standardQtyPerBoxes) {
		this.standardQtyPerBoxes = standardQtyPerBoxes;
	}

}