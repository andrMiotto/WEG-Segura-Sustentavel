package weg.seguranca.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import weg.seguranca.util.MySQLDatabase;
import weg.seguranca.util.NoSQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TestInflux {

    static NoSQLDatabase influx = new NoSQLDatabase();

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

        influx.getWriteApi().writePoint(ponto1); //teste
        influx.getWriteApi().writePoint(ponto2);

        System.out.println("Dados inseridos com sucesso!");
    }

    public static List<FluxTable> recebimento() {
        // Executa a query
        QueryApi queryApi = influx.getQueryApi();
        String flux = "from(bucket: \"" + influx.getBucket() + "\") "
                + "|> range(start: -1h) "
                + "|> filter(fn: (r) => r._measurement == \"logs_sensores\")";

        List<FluxTable> tables = queryApi.query(flux, influx.getOrg());

        // Itera sobre os resultados
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(
                        "Tempo: " + record.getTime() +
                                " | Pessoa: " + record.getValueByKey("pessoa") +
                                " | Sala: " + record.getValueByKey("sala") +
                                " | Movimento: " + record.getValue()
                );

                SenderSQL.salvarMySQL(record);
            }
        }
        return tables;
    }

}
