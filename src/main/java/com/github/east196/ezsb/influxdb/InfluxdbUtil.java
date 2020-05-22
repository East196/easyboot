package com.github.east196.ezsb.influxdb;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class InfluxdbUtil {

    public static List<Object> queryInfluxdb(String query_sql, String table_type, InfluxDBConnect influxDB) {
        long starttime = System.currentTimeMillis();
        QueryResult result = influxDB.query(query_sql.toString(), TimeUnit.MILLISECONDS);
        long endtime = System.currentTimeMillis();

        List<Object> influx_data_list = getInfluxData(result.getResults().get(0), table_type);

        return influx_data_list;

    }
    public static List<Object> getInfluxData(QueryResult.Result result, String table_type) {
        List<Object> influx_data_list = null;
        if (null != result) {
            influx_data_list = new ArrayList<>();

            List<QueryResult.Series> series = result.getSeries();
            if (null != series && series.size() > 0) {
                for (QueryResult.Series serie : series) {
                    List<List<Object>> values = serie.getValues();

                    List<Object> result_list = getInfluxDataAndBuild(values, serie, table_type);

                    influx_data_list.addAll(result_list);
                }
            }
        }

        return influx_data_list;
    }

    public static  List<Object> getInfluxDataAndBuild(List<List<Object>> values, QueryResult.Series
            serie, String table_type) {
        List<Object> influx_data_list = new ArrayList<>();
        List<String> influx_cloumns = serie.getColumns();
        Map<String, String> influx_tags = serie.getTags();

        Map<String, Object> build_maps;

        if (values.size() > 0) {

            for (int i = 0; i < values.size(); i++) {
                build_maps = new LinkedHashMap<>();
                if (null != influx_tags) {

                    for (Map.Entry<String, String> entry : influx_tags.entrySet()) {
                        String entry_key = entry.getKey();
                        if (entry_key.contains("tag_")) {
                            entry_key = entry_key.substring(4);
                        }
                        build_maps.put(entry_key, entry.getValue());
                    }
                }
                Object[] influx_Obj = values.get(i).toArray();

                for (int j = 0; j < influx_Obj.length; j++) {
                    String build_maps_key = influx_cloumns.get(j);
                    if ("time".equals(build_maps_key)) {
                        if ("normal".equals(table_type)) {
                            BigDecimal bd = new BigDecimal(String.valueOf(influx_Obj[j]));
                            build_maps.put("timestamp",  bd.toPlainString());
                        } else if ("polymerize".equals(table_type)) {
                            //  build_maps.put("timestamp", DateUtil.dateToStampAndAddMinute(String.valueOf(influx_Obj[j])));
                        }
                    } else {
                        if (build_maps_key.contains("tag_")) {
                            build_maps_key = build_maps_key.substring(4);
                        }

                        build_maps.put(build_maps_key, influx_Obj[j]);
                    }
                }

                influx_data_list.add(build_maps);
            }
        }
        return influx_data_list;
    }
}