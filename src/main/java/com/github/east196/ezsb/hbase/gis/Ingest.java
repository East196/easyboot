package com.github.east196.ezsb.hbase.gis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Splitter;

/**
 * TODO: HBase 2.x API 有较大变更，需要重构
 */
public class Ingest {

  private static final String usage =
    "ingest table source.tsv\n" +
    "  help - print this message and exit.\n" +
    "  table - the target table to load.\n" +
    "  source.tsv - path to the tsv file to load.\n" +
    "\n" +
    "load data from source.tsv. assumes new-line delimited, tab-separated\n" +
    "records. drops the first line. generates a geohash for the rowkey.\n" +
    "records are stored in columns in the 'a' familt, columns are:\n" +
    "  lon,lat,id,name,address,city,url,phone,type,zip\n";

  private static final String[] COLUMNS = new String[] {
    "lon", "lat", "id", "name", "address",
    "city", "url", "phone", "type", "zip"
  };
  private static final Iterator<String> COLS = Arrays.asList(COLUMNS).iterator();
  private static final Splitter SPLITTER = Splitter.on('\t')
    .trimResults()
    .limit(COLUMNS.length);

  public static void main(String[] args) throws IOException {

    if (args.length != 2) {
      System.out.println(usage);
      System.exit(0);
    }

    // TODO: HBase 2.x 需要重构
    System.out.println("HBase 2.x 需要重构 Ingest 类");
  }
}
