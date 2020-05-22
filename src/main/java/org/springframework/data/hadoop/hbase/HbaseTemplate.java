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
package org.springframework.data.hadoop.hbase;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import com.google.common.base.Charsets;
import com.google.common.collect.Table.Cell;

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
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(Table htable) throws Throwable {
				ResultScanner scanner = htable.getScanner(scan);
				try {
					return action.extractData(scanner);
				} finally {
					scanner.close();
				}
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
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(Table htable) throws Throwable {
				Get get = new Get(rowName.getBytes(Charsets.UTF_8));
				if (familyName != null) {
					byte[] family = familyName.getBytes(Charsets.UTF_8);

					if (qualifier != null) {
						get.addColumn(family, qualifier.getBytes(Charsets.UTF_8));
					} else {
						get.addFamily(family);
					}
				}
				Result result = htable.get(get);
				return mapper.mapRow(result, 0);
			}
		});
	}

	@Override
	public void put(String tableName, final String rowName, final String familyName, final String qualifier,
			final byte[] value) {
		Validate.notBlank(rowName);
		Validate.notBlank(familyName);
		Validate.notBlank(qualifier);
		Validate.notNull(value);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table table) throws Throwable {
				Put put = new Put(rowName.getBytes(Charsets.UTF_8)).addColumn(familyName.getBytes(Charsets.UTF_8),
						qualifier.getBytes(Charsets.UTF_8), value);
				table.put(put);
				return null;
			}
		});
	}

	@Override
	public void put(String tableName, final String rowName, final String familyName, final Map<String, byte[]> kvMap) {
		Validate.notBlank(rowName);
		Validate.notBlank(familyName);
		Validate.notNull(kvMap);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table table) throws Throwable {
				Put put = new Put(rowName.getBytes(Charsets.UTF_8));
				for (Entry<String, byte[]> kv : kvMap.entrySet()) {
					put.addColumn(familyName.getBytes(Charsets.UTF_8), kv.getKey().getBytes(Charsets.UTF_8),
							kv.getValue());
				}
				table.put(put);
				return null;
			}
		});
	}

	@Override
	public void put(String tableName, final String rowName,
			final com.google.common.collect.Table<String, String, byte[]> rowFamilyTable) {
		Validate.notBlank(rowName);
		Validate.notNull(rowFamilyTable);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table table) throws Throwable {
				Put put = new Put(rowName.getBytes(Charsets.UTF_8));
				for (Cell<String, String, byte[]> cell : rowFamilyTable.cellSet()) {
					put.addColumn(cell.getRowKey().getBytes(Charsets.UTF_8),
							cell.getColumnKey().getBytes(Charsets.UTF_8), cell.getValue());
				}
				table.put(put);
				return null;
			}
		});
	}

	@Override
	public void delete(String tableName, final String rowName) {
		delete(tableName, rowName, null, null);
	}

	@Override
	public void delete(String tableName, final String rowName, final String familyName) {
		delete(tableName, rowName, familyName, null);
	}

	@Override
	public void delete(String tableName, final String rowName, final String familyName, final String qualifier) {
		Validate.notBlank(rowName);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table htable) throws Throwable {
				Delete delete = new Delete(rowName.getBytes(Charsets.UTF_8));
				if (familyName != null) {
					byte[] family = familyName.getBytes(Charsets.UTF_8);

					if (qualifier != null) {
						delete.addColumn(family, qualifier.getBytes(Charsets.UTF_8));
					} else {
						delete.addFamily(family);
					}
				}
				htable.delete(delete);
				return null;
			}
		});
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}