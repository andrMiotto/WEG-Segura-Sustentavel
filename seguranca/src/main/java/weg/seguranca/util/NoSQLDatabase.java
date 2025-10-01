package weg.seguranca.util;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NoSQLDatabase {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==".toCharArray();
    private static final String org = "WegSegura";
    private static final String bucket = "WegSegura";

    private static InfluxDBClient client;
    private static WriteApiBlocking writeApi;
    private static QueryApi queryApi;

    public NoSQLDatabase(){
        client = InfluxDBClientFactory.create(url, token, org, bucket);;
        writeApi = client.getWriteApiBlocking();
        queryApi = client.getQueryApi();
    }

    public InfluxDBClient getInfluxDBCliente(){
        return client;
    }

    public WriteApiBlocking getWriteApi(){
        return writeApi;
    }

    public QueryApi getQueryApi() {
        return queryApi;
    }

    public String getOrg() {
        return org;
    }

    public String getBucket(){
        return bucket;
    }



}

