package weg.seguranca.dao;

import weg.seguranca.model.Sala;
import weg.seguranca.util.MySQLDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDao {

    private static final Connection conn;

    static {
        try {
            conn = MySQLDatabase.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvar(Sala sala) throws SQLException {
        String sql = "INSERT INTO salas (numero, bloco, portaria, unidade, situacao_risco, id_emergencia_atual) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sala.getNumero());
            stmt.setString(2, sala.getBloco());
            stmt.setInt(3, sala.getPortaria());
            stmt.setString(4, sala.getUnidade());
            stmt.setBoolean(5, sala.isSituacao_risco());
            stmt.setInt(6, sala.getId_emergencia_atual());
            stmt.executeUpdate();
        }
    }

    public Sala buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM salas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Sala(
                            rs.getInt("id"),
                            rs.getInt("numero"),
                            rs.getString("bloco"),
                            rs.getInt("portaria"),
                            rs.getString("unidade"),
                            rs.getBoolean("situacao_risco"),
                            rs.getInt("id_emergencia_atual")
                    );
                }
            }
        }
        return null;
    }

    public static List<Sala> listarTodos() throws SQLException {
        List<Sala> lista = new ArrayList<>();
        String sql = "SELECT * FROM salas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Sala(
                        rs.getInt("id"),
                        rs.getInt("numero"),
                        rs.getString("bloco"),
                        rs.getInt("portaria"),
                        rs.getString("unidade"),
                        rs.getBoolean("situacao_risco"),
                        rs.getInt("id_emergencia_atual")
                ));
            }
        }
        return lista;
    }

    public void atualizar(Sala sala) throws SQLException {
        String sql = "UPDATE salas SET numero=?, bloco=?, portaria=?, unidade=?, situacao_risco=?, id_emergencia_atual=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sala.getNumero());
            stmt.setString(2, sala.getBloco());
            stmt.setInt(3, sala.getPortaria());
            stmt.setString(4, sala.getUnidade());
            stmt.setBoolean(5, sala.isSituacao_risco());
            stmt.setInt(6, sala.getId_emergencia_atual());
            stmt.setInt(7, sala.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM salas WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


}
