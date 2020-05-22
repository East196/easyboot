package com.github.east196.core.api;

import lombok.Data;

@Data
public class TableResult<T> {
	private long pageNo;
	private long pageSize;
	private long totalCount;
	private long totalPage;
	private T data;
}