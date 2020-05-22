package com.github.east196.ezsb.hbase.gis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QueryMatch {
    public String id;
    public String hash;
    public double lon, lat;
    public double distance = Double.NaN;

    public QueryMatch(String id, String hash, double lon, double lat) {
        this.id = id;
        this.hash = hash;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("hash", hash)
                .append("distance", distance)
                .append("lat", lat)
                .append("lon", lon)
                .toString();
    }
}
