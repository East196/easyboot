package com.github.east196.ezsb.hbase.gis;

import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class UsersDao {
	
	public static final byte[] USERS_TABLE=Bytes.toBytes("users");
	
	public static final byte[] INFO_FAM=Bytes.toBytes("info");
	
	public static final byte[] USER_COL_INFO_FAM=Bytes.toBytes("user");
	
	public static final byte[] NAME_COL_INFO_FAM=Bytes.toBytes("name");
	
	public static final byte[] EMAIL_COL_INFO_FAM=Bytes.toBytes("email");
	
	public static final byte[] PASSWORD_COL_INFO_FAM=Bytes.toBytes("password");
	
	public static final byte[] TWEET_COUNT_COL_INFO_FAM=Bytes.toBytes("tweetCount");
	
	public static RowMapper<Users> usersRowMapper;
	
	static {
		usersRowMapper = (result, rowNum) -> {
			Users users = new Users();
			users.setUser(Bytes.toString(result.getValue(INFO_FAM, USER_COL_INFO_FAM)));
			users.setName(Bytes.toString(result.getValue(INFO_FAM, NAME_COL_INFO_FAM)));
			users.setEmail(Bytes.toString(result.getValue(INFO_FAM, EMAIL_COL_INFO_FAM)));
			users.setPassword(Bytes.toString(result.getValue(INFO_FAM, PASSWORD_COL_INFO_FAM)));
			users.setTweetCount(Bytes.toLong(result.getValue(INFO_FAM, TWEET_COUNT_COL_INFO_FAM)));
			return users;
		};
	}
	
	private HbaseTemplate hbaseTemplate;

	public UsersDao(HbaseTemplate hbaseTemplate) {
		this.hbaseTemplate = hbaseTemplate;
	}
	
	public void put(Users users) {
		put(users.toRowKey(), users);
	}
	
	public void put(String rowKey, Users users) {
		Table<String, String, byte[]> rowFamilyTable = HashBasedTable.create();
		rowFamilyTable.put("info","user", Bytes.toBytes(users.getUser()));
		rowFamilyTable.put("info","name", Bytes.toBytes(users.getName()));
		rowFamilyTable.put("info","email", Bytes.toBytes(users.getEmail()));
		rowFamilyTable.put("info","password", Bytes.toBytes(users.getPassword()));
		rowFamilyTable.put("info","tweetCount", Bytes.toBytes(users.getTweetCount()));
		hbaseTemplate.put("users", rowKey, rowFamilyTable);
	}
	
	public Users get(Users users) {
		return get(users.toRowKey());
	}
	
	public Users get(String rowKey) {
		return hbaseTemplate.get("users", rowKey, usersRowMapper);
	}
	
	public List<Users> find(Scan scan) {
		return hbaseTemplate.find("users", scan, usersRowMapper);
	}
	
	public void delete(Users users) {
		delete(users.toRowKey());
	}
	
	public void delete(String rowKey) {
		hbaseTemplate.delete("users", rowKey);
	}
	
	public Long count(){
		Scan scan = new Scan();
		scan.setFilter(new KeyOnlyFilter());
		scan.setMaxVersions();
		return count(scan);
	}
	
	public Long count(Scan scan){
		return hbaseTemplate.execute("users", table->{
			long count = 0;
			try (AggregationClient ac = new AggregationClient(table.getConfiguration());){
				count= ac.rowCount(table, new LongColumnInterpreter(), scan);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return count;
		});
	}

}
