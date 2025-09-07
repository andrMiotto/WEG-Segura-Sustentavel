package weg.seguranca.exception;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.xml.sax.ErrorHandler;
import weg.seguranca.rfid.LeituraRFIDRepetida;
import weg.seguranca.rfid.RFIDmonitor;
import weg.model.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class CustomExeption {

    Boolean alertaLigado;

    public void updatePresenca(String sql, String insert, String tagId, int salaId, boolean presente) throws SQLException {
        try(Connection conn = getConnection()){ //metodo de conexão com o db
            conn.setAutoCommit(false);

            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setBoolean(1, presente);
                stmt.setString(2, tagId);
                stmt.setInt(3, salaId);
                int rows = stmt.executeUpdate();

                if(rows == 0){ //tenta inserir uma segunda vez
                    try(PreparedStatement stmt2 = conn.prepareStatement(insert)){
                        stmt2.setString(1, tagId);
                        stmt2.setInt(2, salaId);
                        stmt2.setBoolean(3, presente);
                        stmt2.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e){
                conn.rollback();
                throw e;
            }
        }
    }

    public void processarLeitura(String id, Pessoa pessoa){

        String ultimoRFIDlido;
        long ultimoTempo;
        long agora = System.currentTimeMillis();

        if(alertaLigado == true){ //Alerta ligado recebe o sinal do Iot.

            try{
                if(id.equals(ultimoRFIDlido) && (agora - ultimoTempo) > 15000){
                    System.out.println("Emergência detectada.");
                }
            }catch(Exception erro){
                System.out.println("Emergência detectada!!!!");
                System.out.println("Sala: " + pessoa.getIdSalaAtual);
            }
        }else{
            try{
                RFIDmonitor.verificarLeitura(tagId);
            } catch (LeituraRFIDRepetida e){
                e.getMessage();
            }      
        }

        ultimoRFIDlido = id;
        ultimoTempo = agora;
    }
}
