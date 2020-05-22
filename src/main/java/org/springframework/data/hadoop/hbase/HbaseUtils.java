/*
 * Copyright 2011-2013 the original author or authors.
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

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;

/**
 * Helper class featuring methods for Hbase table handling and exception translation. 
 * 
 * @author Costin Leau
 */
public class HbaseUtils {

	/**
	 * Converts the given (Hbase) exception to an appropriate exception from <tt>org.springframework.dao</tt> hierarchy.
	 * 
	 * @param ex Hbase exception that occurred
	 * @return the corresponding DataAccessException instance
	 */
	public static DataAccessException convertHbaseException(Exception ex) {
		return new HbaseSystemException(ex);
	}

	/**
	 * Retrieves an Hbase table instance identified by its name.
	 * 
	 * @param configuration Hbase configuration object
	 * @param tableName table name
	 * @return table instance
	 */
	public static Table getTable(String tableName, Configuration configuration) {
		if (HbaseSynchronizationManager.hasResource(tableName)) {
			return (HTable) HbaseSynchronizationManager.getResource(tableName);
		}

		Table table = null;
		try {
			table = ConnectionFactory.createConnection(configuration).getTable(TableName.valueOf(tableName));
			HbaseSynchronizationManager.bindResource(tableName, table);
			return table;
		} catch (Exception ex) {
			throw convertHbaseException(ex);
		}
	}

	/**
	 * Releases (or closes) the given table, created via the given configuration if it is not managed externally (or bound to the thread).
	 * 
	 * @param tableName table name
	 * @param table table
	 */
	public static void releaseTable(String tableName, Table table) {
		releaseTable(tableName, table, null);
	}

	/**
	 * Releases (or closes) the given table, created via the given configuration if it is not managed externally (or bound to the thread).
	 * 
	 * @param tableName table name
	 * @param table table
	 * @param connection hbase connection
	 */
	public static void releaseTable(String tableName, Table table, Connection connection) {
		try {
			doReleaseTable(tableName, table, connection);
		} catch (IOException ex) {
			throw HbaseUtils.convertHbaseException(ex);
		}
	}

	private static void doReleaseTable(String tableName, Table table, Connection connection)
			throws IOException {
		if (table == null || connection == null) {
			return;
		}

		// close only if its unbound 
		if (!isBoundToThread(tableName)) {
			table.close();
			connection.close();
		}
	}

	private static boolean isBoundToThread(String tableName) {
		return HbaseSynchronizationManager.hasResource(tableName);
	}
}