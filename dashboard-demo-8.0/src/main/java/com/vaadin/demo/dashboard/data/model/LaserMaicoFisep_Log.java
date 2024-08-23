package com.vaadin.demo.dashboard.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DatiProvaTenutaElio database table.
 * 
 */
@Entity(name="laserMaicoFisep_Log")
@NamedQuery(name="laserMaicoFisep_Log.findAll", query="SELECT a FROM laserMaicoFisep_Log a")
public class LaserMaicoFisep_Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int Id;

	private String CodProduct;

	private String MarkingContent;

	private Date DateHours;

	private String CodGrade;

	private Double CycleTime;
	
	private String MarkingResult;

	public LaserMaicoFisep_Log() { }

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getCodProduct() {
		return CodProduct;
	}

	public void setCodProduct(String codProduct) {
		CodProduct = codProduct;
	}

	public String getMarkingContent() {
		return MarkingContent;
	}

	public void setMarkingContent(String markingContent) {
		MarkingContent = markingContent;
	}

	public Date getDateHours() {
		return DateHours;
	}

	public void setDateHours(Date dateHours) {
		DateHours = dateHours;
	}

	public String getCodGrade() {
		return CodGrade;
	}

	public void setCodGrade(String codGrade) {
		CodGrade = codGrade;
	}

	public Double getCycleTime() {
		return CycleTime;
	}

	public void setCycleTime(Double cycleTime) {
		CycleTime = cycleTime;
	}

	public String getMarkingResult() {
		return MarkingResult;
	}

	public void setMarkingResult(String markingResult) {
		MarkingResult = markingResult;
	}
}