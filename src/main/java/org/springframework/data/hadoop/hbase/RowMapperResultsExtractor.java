package org.springframework.data.hadoop.hbase;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * Adapter encapsulating the RowMapper callback. 
 * 
 * @author Costin Leau
 */
class RowMapperResultsExtractor<T> implements ResultsExtractor<List<T>> {

	private final RowMapper<T> rowMapper;

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * @param rowMapper the RowMapper which creates an object for each row
	 */
	public RowMapperResultsExtractor(RowMapper<T> rowMapper) {
		Validate.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
	}

	public List<T> extractData(ResultScanner results) throws Exception {
		List<T> rs = new ArrayList<T>();
		int rowNum = 0;
		for (Result result : results) {
			rs.add(this.rowMapper.mapRow(result, rowNum++));
		}
		return rs;
	}
}