
package com.example.car_showroom.dto;

/**
 * This DTO is used for Pageable Data
 * 
 */
public class PageableDTO {

	Object content;
	PageDTO page = new PageDTO();

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public PageDTO getPage() {
		return page;
	}

	public void setPage(PageDTO page) {
		this.page = page;
	}

}
