package com.github.east196.ezsb.influxdb;

import lombok.Data;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Data
public class InfluxDBConnect {
    private String username;// 用户名
    private String password;// 密码
    private String openurl;// 连接地址
    private String database;// 数据库

    private InfluxDB influxDB;

    public InfluxDBConnect(String username, String password, String openurl, String database) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
    }

    /** 连接时序数据库；获得InfluxDB **/
    public InfluxDB influxDbBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
            influxDB.createDatabase(database);
        }
        return influxDB;
    }

    /**
     * 设置数据保存策略 defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1 副本个数为1/ 结尾DEFAULT
     * 表示 设为默认的策略
     */
    public void createRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "defalut", database, "7200d", 1);
        this.query(command);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, database));
    }
    public QueryResult query(String command, TimeUnit unit) {
        return influxDB.query(new Query(command, database),unit);
    }

    /**
     * 插入
     * @param measurement 表
     * @param tags 标签
     * @param fields 字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time) {
        Point.Builder builder = Point.measurement(measurement);
        builder.time(time, TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);
        influxDB.write(database, "", builder.build());
    }

    /**
     * 插入
      * @param measurement 表
       * @param tags 标签
       * @param fields 字段
        */
     public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields){
         Point.Builder builder = Point.measurement(measurement);
         builder.tag(tags);
         builder.fields(fields);

         influxDB.write(database, "", builder.build());
     }


    /**
     * 插入
     * @param batchPoints 批量插入
     */
    public void batchInsert(BatchPoints batchPoints){
        influxDB.write(batchPoints);
    }

    /**
     * 删除
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    /**
     * 创建数据库
     * @param dbName
     */
    public void createDB(String dbName) {
        influxDB.createDatabase(dbName);
    }

    /**
     * 删除数据库
     *
     * @param dbName
     */
    public void deleteDB(String dbName) {
        influxDB.deleteDatabase(dbName);
    }
}