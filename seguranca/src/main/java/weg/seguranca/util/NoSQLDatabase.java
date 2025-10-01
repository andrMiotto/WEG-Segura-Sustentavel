package weg.seguranca.util;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NoSQLDatabase {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "e8d2ryJeLHE5cyJ5joTVvuPkQsLc1uDMiL8EFAkbLhDGEDVxJ-RfzVpvO5iHRQlzX5I5bTb2qbAYTgwYQtYRgA".toCharArray();
    private static final String org = "Weg";
    private static final String bucket = "WegSegura";

    InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket);
    WriteApiBlocking writeApi = client.getWriteApiBlocking();
    String query = "from(bucket: \"WegSegura\")\n" +
            "  |> range(start: -24h)\n" +
            "  |> filter(fn: (r) => r._measurement == \"logs_sensores\" and r.sala == \"1\")\n" +
            "  |> aggregateWindow(every: 5m, fn: mean)\n";
    QueryApi queryApi = client.getQueryApi();


    public static void main(String[] args) {

        InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket);
        WriteApiBlocking writeApi = client.getWriteApiBlocking();

        try {
            Point ponto1 = Point.measurement("logs_sensores")
                    .addTag("sala", "1")
                    .addTag("pessoa", "10")
                    .addField("ha_movimento_na_sala", true)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            Point ponto2 = Point.measurement("logs_sensores")
                    .addTag("sala", "2")
                    .addTag("pessoa", "12")
                    .addField("ha_movimento_na_sala", false)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            Point ponto3 = Point.measurement("logs_sensores")
                    .addTag("sala", "3")
                    .addTag("pessoa", "13")
                    .addField("ha_movimento_na_sala", true)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            writeApi.writePoint(ponto1);
            writeApi.writePoint(ponto2);
            writeApi.writePoint(ponto3);

            System.out.println("Dados inseridos com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
