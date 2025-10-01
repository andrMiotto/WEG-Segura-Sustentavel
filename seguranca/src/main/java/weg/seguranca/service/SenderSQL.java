package weg.seguranca.service;

import com.influxdb.query.FluxRecord;
import jakarta.persistence.criteria.CriteriaBuilder;
import weg.seguranca.dao.EmergenciaDao;
import weg.seguranca.dao.PessoaDao;
import weg.seguranca.dao.SalaDao;
import weg.seguranca.dao.SalaEmergenciaDao;
import weg.seguranca.model.Emergencia;
import weg.seguranca.model.Pessoa;
import weg.seguranca.model.Sala;
import weg.seguranca.model.SalaEmergencia;
import weg.seguranca.util.MySQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class SenderSQL {

    private Emergencia emergencia;
    private Pessoa pessoa;
    private Sala sala;
    private SalaEmergencia salaEmergencia;

    private EmergenciaDao emergenciaData;
    private PessoaDao pessoaData;
    private SalaDao salaData;
    private SalaEmergenciaDao salaEmergenciaData;

    public static void salvarMySQL(FluxRecord record) {
        try (Connection conn = MySQLDatabase.connect()) {

            String pessoa = (String) record.getValueByKey("pessoa");
            String sala = (String) record.getValueByKey("sala");
            Boolean movimento = (Boolean) record.getValue();
            Instant tempo = record.getTime();
            String temperatura = (String) record.getValueByKey("temperatura");
            String umidade = (String) record.getValueByKey("umidade");
            Timestamp timestamp = tempo != null ? Timestamp.from(tempo) : new Timestamp(System.currentTimeMillis());

            if (pessoa == null || sala == null || movimento == null || temperatura == null || umidade == null) {
                System.out.println("Registro ignorado (valores nulos).");
                return;
            }

            int pessoaInt = Integer.parseInt(pessoa);
            int salaInt = Integer.parseInt(sala);
            Double temperaturaDouble = Double.parseDouble(temperatura);
            Double umidadeDouble = Double.parseDouble(umidade);

            updateSalaAtual(pessoaInt, salaInt);

            /*
            String checkSql = """
                    Select count(cadastro) from pessoas
                    where id_sala_atual = ?;
                    """;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, sala);
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
            */

            System.out.println("Registro inserido no MySQL com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSalaAtual(int cadastro, int id_sala_atual){
        PessoaDao pessoaData = new PessoaDao();

        Pessoa pessoa = new Pessoa(cadastro, id_sala_atual);

        try{
            pessoaData.updateSalaAtual(pessoa);
            System.out.println("Enviado para o MySQL com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}
