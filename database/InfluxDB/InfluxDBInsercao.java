package InfluxDB;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

public class InfluxDBInsercao {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==".toCharArray();
    private static final String org = "WegSegura";
    private static final String bucket = "WegSegura";

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
                    .addTag("pessoa", "15")
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