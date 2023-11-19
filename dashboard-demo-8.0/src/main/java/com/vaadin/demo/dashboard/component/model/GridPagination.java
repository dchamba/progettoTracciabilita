package com.vaadin.demo.dashboard.component.model;

import java.util.List;

import com.vaadin.data.provider.QuerySortOrder;

public class GridPagination {

	private Integer pageNumber = 0;
	private Integer rowPerPage = 20;
	//private Integer rowPerPage = 20;
	
	private List<QuerySortOrder> sortOrders;
	
	private Integer limit = 0;
	private Integer offset = 0;

	public GridPagination() { }
	
	public GridPagination(Integer pageNumber, Integer rowPerPage) {
		this.pageNumber = pageNumber;
		this.rowPerPage = rowPerPage;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(Integer rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<QuerySortOrder> getSortOrders() {
		return sortOrders;
	}

	public void setSortOrders(List<QuerySortOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}
}
