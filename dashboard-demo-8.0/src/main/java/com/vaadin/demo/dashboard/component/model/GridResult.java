package com.vaadin.demo.dashboard.component.model;

import java.util.List;

public class GridResult<T> {
	private List<T> result;
	private Long resultCount;
	
	public GridResult(List<T> listResult, Long rowCount) {
		this.result = listResult;
		this.resultCount = rowCount;
	}
	
	public GridResult(List<T> listResult) {
		this.result = listResult;
	}
	
	public Long getResultCount() {
		return resultCount == null ? 0 : resultCount;
	}
	
	public void setResultCount(Long resultCount) {
		this.resultCount = resultCount;
	}
	
	public List<T> getResult() {
		return result;
	}
	
	public void setResult(List<T> result) {
		this.result = result;
	}
}
