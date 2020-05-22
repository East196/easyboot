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

public class PlaceDao {
	
	public static final byte[] PLACE_TABLE=Bytes.toBytes("place");
	
	public static final byte[] UUID_FAM=Bytes.toBytes("uuid");
	
	public static final byte[] GPS_FAM=Bytes.toBytes("gps");
	
	public static final byte[] TAGS_FAM=Bytes.toBytes("tags");
	
	public static final byte[] UUID_COL_UUID_FAM=Bytes.toBytes("uuid");
	
	public static final byte[] LAT_COL_GPS_FAM=Bytes.toBytes("lat");
	
	public static final byte[] LON_COL_GPS_FAM=Bytes.toBytes("lon");
	
	public static final byte[] DISTANCE_COL_GPS_FAM=Bytes.toBytes("distance");
	
	public static final byte[] TAG1_COL_TAGS_FAM=Bytes.toBytes("tag1");
	
	public static final byte[] TAG2_COL_TAGS_FAM=Bytes.toBytes("tag2");
	
	public static RowMapper<Place> placeRowMapper;
	
	static {
		placeRowMapper = (result, rowNum) -> {
			Place place = new Place();
			place.setUuid(Bytes.toString(result.getValue(UUID_FAM, UUID_COL_UUID_FAM)));
			place.setLat(Bytes.toDouble(result.getValue(GPS_FAM, LAT_COL_GPS_FAM)));
			place.setLon(Bytes.toDouble(result.getValue(GPS_FAM, LON_COL_GPS_FAM)));
			place.setDistance(Bytes.toLong(result.getValue(GPS_FAM, DISTANCE_COL_GPS_FAM)));
			place.setTag1(Bytes.toString(result.getValue(TAGS_FAM, TAG1_COL_TAGS_FAM)));
			place.setTag2(Bytes.toString(result.getValue(TAGS_FAM, TAG2_COL_TAGS_FAM)));
			return place;
		};
	}
	
	private HbaseTemplate hbaseTemplate;

	public PlaceDao(HbaseTemplate hbaseTemplate) {
		this.hbaseTemplate = hbaseTemplate;
	}
	
	public void put(Place place) {
		put(place.toRowKey(), place);
	}
	
	public void put(String rowKey, Place place) {
		Table<String, String, byte[]> rowFamilyTable = HashBasedTable.create();
		rowFamilyTable.put("uuid","uuid", Bytes.toBytes(place.getUuid()));
		rowFamilyTable.put("gps","lat", Bytes.toBytes(place.getLat()));
		rowFamilyTable.put("gps","lon", Bytes.toBytes(place.getLon()));
		rowFamilyTable.put("gps","distance", Bytes.toBytes(place.getDistance()));
		rowFamilyTable.put("tags","tag1", Bytes.toBytes(place.getTag1()));
		rowFamilyTable.put("tags","tag2", Bytes.toBytes(place.getTag2()));
		hbaseTemplate.put("place", rowKey, rowFamilyTable);
	}
	
	public Place get(Place place) {
		return get(place.toRowKey());
	}
	
	public Place get(String rowKey) {
		return hbaseTemplate.get("place", rowKey, placeRowMapper);
	}
	
	public List<Place> find(Scan scan) {
		return hbaseTemplate.find("place", scan, placeRowMapper);
	}
	
	public void delete(Place place) {
		delete(place.toRowKey());
	}
	
	public void delete(String rowKey) {
		hbaseTemplate.delete("place", rowKey);
	}
	
	public Long count(){
		Scan scan = new Scan();
		scan.setFilter(new KeyOnlyFilter());
		scan.setMaxVersions();
		return count(scan);
	}
	
	public Long count(Scan scan){
		return hbaseTemplate.execute("place", table->{
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
