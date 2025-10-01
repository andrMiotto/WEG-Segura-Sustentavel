package weg.seguranca.dao;

import weg.seguranca.model.SalaEmergencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaEmergenciaDao {

    private Connection conn;

    public SalaEmergenciaDao(Connection conn) {
        this.conn = conn;
    }

    public void salvar(SalaEmergencia salaEmergencia) throws SQLException {
        String sql = "INSERT INTO salas_emergencia (sala_id, emergencia_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaEmergencia.getSala_id());
            stmt.setInt(2, salaEmergencia.getEmergencia_id());
            stmt.executeUpdate();
        }
    }

    public SalaEmergencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM salas_emergencia WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SalaEmergencia(
                            rs.getInt("id"),
                            rs.getInt("sala_id"),
                            rs.getInt("emergencia_id")
                    );
                }
            }
        }
        return null;
    }

    public List<SalaEmergencia> listarTodos() throws SQLException {
        List<SalaEmergencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM salas_emergencia";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new SalaEmergencia(
                        rs.getInt("id"),
                        rs.getInt("sala_id"),
                        rs.getInt("emergencia_id")
                ));
            }
        }
        return lista;
    }

    public void atualizar(SalaEmergencia salaEmergencia) throws SQLException {
        String sql = "UPDATE salas_emergencia SET sala_id=?, emergencia_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salaEmergencia.getSala_id());
            stmt.setInt(2, salaEmergencia.getEmergencia_id());
            stmt.setInt(3, salaEmergencia.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM salas_emergencia WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
