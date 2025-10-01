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

                salvarNoMySQL(record);
            }
        }
        return tables;
    }

    private static void salvarNoMySQL(FluxRecord record) {
        try (Connection conn = MySQLDatabase.connect()) {

            String pessoa = (String) record.getValueByKey("pessoa");
            String sala = (String) record.getValueByKey("sala");
            Boolean movimento = (Boolean) record.getValue();
            Instant tempo = record.getTime();
            Timestamp timestamp = tempo != null ? Timestamp.from(tempo) : new Timestamp(System.currentTimeMillis());


            if (pessoa == null || sala == null || movimento == null) {
                System.out.println("Registro ignorado (valores nulos).");
                return;
            }

            String checkSql = "SELECT COUNT(*) FROM emergencia WHERE pessoa = ? AND sala = ? AND tempo = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, pessoa);
                checkStmt.setString(2, sala);
                checkStmt.setTimestamp(3, timestamp);
                var rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Registro duplicado ignorado!");
                    return;
                }
            }

            String sqlEmergencia = "INSERT INTO emergencia (pessoa, sala, movimento, tempo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEmergencia)) {
                stmt.setString(1, pessoa);
                stmt.setString(2, sala);
                stmt.setBoolean(3, movimento != null ? movimento : false);
                stmt.setTimestamp(4, timestamp);
                stmt.executeUpdate();
            }

            String sqlSala = "INSERT INTO sala_emergencia (sala, ultima_atividade) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlSala)) {
                stmt.setString(1, sala);
                stmt.setTimestamp(2, timestamp);
                stmt.executeUpdate();
            }

            System.out.println("Registro inserido no MySQL com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
