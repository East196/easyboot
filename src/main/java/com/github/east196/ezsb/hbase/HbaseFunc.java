package com.github.east196.ezsb.hbase;

import ch.hsr.geohash.GeoHash;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class HbaseFunc {
	
	public static final int HASH_LENGTH=32; 
	
	public static GeoHash geoHash(String lat, String lon) {
		return geoHash(lat, lon, 12);
	}

	public static GeoHash geoHash(String lat, String lon, int precision) {
		double latIn = Double.parseDouble(lat);
		double lonIn = Double.parseDouble(lon);
		return geoHash(latIn, lonIn, precision);
	}

	public static String geoHashBase32(Double lat, Double lon) {
		return geoHash(lat, lon).toBase32();
	}

	public static GeoHash geoHash(Double lat, Double lon) {
		return geoHash(lat, lon, 12);
	}
	
	public static GeoHash geoHash(Double lat, Double lon, int precision) {
		return GeoHash.withCharacterPrecision(lat, lon, precision);
	}

	public static String hash(String item) {
		return Hashing.md5().hashString(item, Charsets.UTF_8).toString();
	}

	public static void main(String[] args) {
		System.out.println(geoHash("23.111", "122.8889"));
		System.out.println(geoHash("23.111", "122.8889").toBase32());
		System.out.println(hash("23.111"));
		System.out.println(hash("23.111").length());
	}
}
