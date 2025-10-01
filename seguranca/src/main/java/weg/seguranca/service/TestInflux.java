package weg.seguranca.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import weg.seguranca.util.NoSQLDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestInflux {

    static NoSQLDatabase influx = new NoSQLDatabase();

    public static void insertTeste(){
        List<Point> pontos = new ArrayList<>();

        Point ponto1 = Point.measurement("logs_sensores")
                .addField("ha_movimento_na_sala", true)
                .addTag("pessoa", "81787")
                .addTag("sala", "1")
                .addTag("temperatura", "50")
                .addTag("umidade", "20")

                .time(System.currentTimeMillis(), WritePrecision.MS);

        Point ponto2 = Point.measurement("logs_sensores")
                .addField("ha_movimento_na_sala", false)
                .addTag("pessoa", "81785")
                .addTag("sala", "2")
                .addTag("temperatura", "30")
                .addTag("umidade", "40")

                .time(System.currentTimeMillis(), WritePrecision.MS);

        influx.getWriteApi().writePoint(ponto1); //teste
        influx.getWriteApi().writePoint(ponto2);

        System.out.println("Dados inseridos com sucesso!");
    }

    public static List<FluxTable> recebimento() {
        QueryApi queryApi = influx.getQueryApi();
        String flux = "from(bucket: \"" + influx.getBucket() + "\") "
                + "|> range(start: -1h) "
                + "|> filter(fn: (r) => r._measurement == \"logs_sensores\")";

        List<FluxTable> tables = queryApi.query(flux, influx.getOrg());

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(
                        "Tempo: " + record.getTime() +
                        " | Pessoa: " + record.getValueByKey("pessoa") +
                        " | Sala: " + record.getValueByKey("sala") +
                        " | Movimento: " + record.getValue() +
                        " | Temperatura: " + record.getValueByKey("temperatura") +
                        " | Umidade: " + record.getValueByKey("umidade")

                );

                SenderSQL.salvarMySQL(record);
            }
        }
        return tables;
    }

}
