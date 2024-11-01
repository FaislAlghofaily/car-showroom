
package com.example.car_showroom.dto;

import java.io.Serializable;

/**
 * This DTO is used for Pagination
 * 
 */
public class PageDTO implements Serializable {

	private static final long serialVersionUID = -6321393751936889027L;

	private Integer limit;
	private Integer page;
	private Long total;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
