package weg.seguranca.service;

import com.influxdb.query.FluxRecord;
import weg.seguranca.util.MySQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class SenderSQL {

    public static void salvarMySQL(FluxRecord record) {
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

            String checkSql = "SELECT COUNT(*) FROM emergencias WHERE pessoa = ? AND sala = ? AND tempo = ?";
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

            String sqlSala = "INSERT INTO salas_emergencia (sala, emergencia_id) VALUES (?, ?)";
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
