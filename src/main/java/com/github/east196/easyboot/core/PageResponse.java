package com.github.east196.easyboot.core;

import org.springframework.hateoas.PagedResources.PageMetadata;

public class PageResponse<T> extends DataResponse<T> {

	public PageResponse(String code, String message, T data, PageMetadata page) {
		super(code, message, data);
		this.page = page;
	}

	private PageMetadata page;

	public PageMetadata getPage() {
		return page;
	}

	public void setPage(PageMetadata page) {
		this.page = page;
	}

}