package weg.seguranca.util;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoSQLDatabase {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==".toCharArray();
    private static final String org = "WegSegura";
    private static final String bucket = "WegSegura";

    public static final InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket);
    public static final WriteApiBlocking writeApi = client.getWriteApiBlocking();


    public static void main(String[] args) {

        try {

            insertTeste();
            QueryApi queryApi = client.getQueryApi();

            recebimento();


        } finally {
            client.close();
        }


    }

    public static void insertTeste(){
        List<Point> pontos = new ArrayList<>();

        Point ponto1 = Point.measurement("logs_sensores")
                .addTag("sala","211")
                .addTag("pessoa","112")
                .addField("ha_movimento_na_sala", true)
                .time(System.currentTimeMillis(), WritePrecision.MS);

        Point ponto2 = Point.measurement("logs_sensores")
                .addTag("sala", "105")
                .addTag("pessoa", "12345")
                .addField("ha_movimento_na_sala", false)
                .time(System.currentTimeMillis(), WritePrecision.MS);

        writeApi.writePoint(ponto1); //teste
        writeApi.writePoint(ponto2);

        System.out.println("Dados inseridos com sucesso!");
    }

    public static List<FluxTable> recebimento(){
        // Executa a query
        QueryApi queryApi = client.getQueryApi();
        String flux = "from(bucket: \"" + bucket + "\") "
                + "|> range(start: -1h) "
                + "|> filter(fn: (r) => r._measurement == \"logs_sensores\")";

        List<FluxTable> tables = queryApi.query(flux, org);

        // Itera sobre os resultados
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(
                        "Tempo: " + record.getTime() +
                                " | Pessoa: " + record.getValueByKey("pessoa") +
                                " | Sala: " + record.getValueByKey("sala") +
                                " | Movimento: " + record.getValue()
                );
            }
        }

        return tables;
    }
}
