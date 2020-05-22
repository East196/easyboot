package com.github.east196.ezsb.hbase.gis;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.east196.ezsb.hbase.HbaseFunc;

public class Place {//场景
	
	private String uuid;//uuid
	
	private Double lat;//维度
	
	private Double lon;//经度
	
	private Long distance;//有效距离
	
	private String tag1;//一级分类
	
	private String tag2;//二级分类
	
	public Place(){}
	
	public Place(String uuid,Double lat,Double lon,Long distance,String tag1,String tag2){
		this.uuid=uuid;
		this.lat=lat;
		this.lon=lon;
		this.distance=distance;
		this.tag1=tag1;
		this.tag2=tag2;
	}

	public String toRowKey() {
		StringBuilder sb=new StringBuilder();
		sb.append(HbaseFunc.geoHashBase32(lat,lon));
		sb.append("_");
		sb.append(uuid);
		return sb.toString();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}
	
	public String getTag1() {
		return tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	
	public String getTag2() {
		return tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	
	@Override 
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override 
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
	
}
