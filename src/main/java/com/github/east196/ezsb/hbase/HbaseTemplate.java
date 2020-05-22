/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.east196.ezsb.hbase;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HbaseTemplate implements HbaseOperations {

	private Configuration configuration;

	public HbaseTemplate() {
		setConfiguration(HBaseConfiguration.create());
	}

	public HbaseTemplate(Configuration configuration) {
		setConfiguration(configuration);
	}

	@Override
	public <T> T execute(String tableName, TableCallback<T> action) {
		Validate.notNull(action, "Callback object must not be null");
		Validate.notNull(tableName, "No table specified");

		Table table = getTable(tableName);

		try {
			T result = action.doInTable(table);
			return result;
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}

	private Table getTable(String tableName) {
		return HbaseUtils.getTable(tableName, getConfiguration());
	}

	private void releaseTable(String tableName, Table table) {
		HbaseUtils.releaseTable(tableName, table);
	}

	public DataAccessException convertHbaseAccessException(Exception ex) {
		return HbaseUtils.convertHbaseException(ex);
	}

	@Override
	public <T> T find(String tableName, String family, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(Charsets.UTF_8));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(Charsets.UTF_8), qualifier.getBytes(Charsets.UTF_8));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action) {
		return execute(tableName, htable -> {
			try (ResultScanner scanner = htable.getScanner(scan)) {
				return action.extractData(scanner);
			}
		});
	}

	@Override
	public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(Charsets.UTF_8));
		return find(tableName, scan, action);
	}

	@Override
	public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(Charsets.UTF_8), qualifier.getBytes(Charsets.UTF_8));
		return find(tableName, scan, action);
	}

	@Override
	public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
		return find(tableName, scan, new RowMapperResultsExtractor<T>(action));
	}

	@Override
	public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
		return get(tableName, rowName, null, null, mapper);
	}

	@Override
	public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
		return get(tableName, rowName, familyName, null, mapper);
	}

	@Override
	public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier,
			final RowMapper<T> mapper) {
		return execute(tableName, table -> {
			Get get = new Get(rowName.getBytes(Charsets.UTF_8));
			if (familyName != null) {
				byte[] family = familyName.getBytes(Charsets.UTF_8);

				if (qualifier != null) {
					get.addColumn(family, qualifier.getBytes(Charsets.UTF_8));
				} else {
					get.addFamily(family);
				}
			}
			Result result = table.get(get);
			return mapper.mapRow(result, 0);
		});
	}

	@Override
	public void put(String tableName, final String rowName, final String familyName, final String qualifier, final byte[] value) {
		Validate.notBlank(rowName);
		Validate.notBlank(familyName);
		Validate.notBlank(qualifier);
		Validate.notNull(value);
		execute(tableName, table -> {
			Put put = new Put(rowName.getBytes(Charsets.UTF_8)).addColumn(familyName.getBytes(Charsets.UTF_8), qualifier.getBytes(Charsets.UTF_8), value);
			table.put(put);
			return null;
		});
	}
	
	@Override
	public void put(String tableName, final String rowName, final String familyName, final Map<String,byte[]> kvMap) {
		Validate.notBlank(rowName);
		Validate.notBlank(familyName);
		Validate.notNull(kvMap);
		execute(tableName, table -> {
			Put put = new Put(rowName.getBytes(Charsets.UTF_8));
			for (Entry<String, byte[]> kv : kvMap.entrySet()) {
				put.addColumn(familyName.getBytes(Charsets.UTF_8), kv.getKey().getBytes(Charsets.UTF_8), kv.getValue());
			}
			table.put(put);
			return null;
		});
	}

	@Override
	public void delete(String tableName, final String rowName, final String familyName) {
		delete(tableName, rowName, familyName, null);
	}

	@Override
	public void delete(String tableName, final String rowName, final String familyName, final String qualifier) {
		Validate.notBlank(rowName);
		Validate.notBlank(familyName);
		execute(tableName, table -> {
			Delete delete = new Delete(rowName.getBytes(Charsets.UTF_8));
			byte[] family = familyName.getBytes(Charsets.UTF_8);

			if (qualifier != null) {
				delete.addColumn(family, qualifier.getBytes(Charsets.UTF_8));
			} else {
				delete.addFamily(family);
			}

			table.delete(delete);
			return null;
		});
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}