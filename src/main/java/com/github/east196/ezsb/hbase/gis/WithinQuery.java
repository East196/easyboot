package com.github.east196.ezsb.hbase.gis;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;

import com.github.east196.ezsb.hbase.gis.filter.WithinFilter;
import com.github.east196.ezsb.hbase.gis.model.DistanceComparator;
import com.github.east196.ezsb.hbase.gis.model.QueryMatch;
import ch.hsr.geohash.GeoHash;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * TODO: HBase 2.x 需要重构
 */
public class WithinQuery {

    static final byte[] TABLE = "wifi".getBytes();
    static final byte[] FAMILY = "a".getBytes();
    static final byte[] ID = "id".getBytes();
    static final byte[] X_COL = "lon".getBytes();
    static final byte[] Y_COL = "lat".getBytes();

    private static final String usage =
        "WithinQuery local|remote wkt\n" +
        "  help - print this message and exit.\n" +
        "  local | remote - run the exclusion filter client-side or in the filter.\n" +
        "  wkt - the query geometry in Well-Known Text format.";

    final GeometryFactory factory = new GeometryFactory();
    
    public WithinQuery(Object pool) {
        // TODO: HBase 2.x 需要重构
    }
}
