package weg.seguranca.dao;

import weg.seguranca.model.Emergencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergenciaDao {

    private Connection conn;

    public EmergenciaDao(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Emergencia emergencia) throws SQLException {
        String sql = "INSERT INTO emergencias (titulo, descricao, inicio, fim) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emergencia.getTitulo());
            stmt.setString(2, emergencia.getDescricao());
            stmt.setDate(3, Date.valueOf(emergencia.getInicio()));
            stmt.setDate(4, Date.valueOf(emergencia.getFim()));
            stmt.executeUpdate();
        }
    }

    public Emergencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emergencias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Emergencia(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descricao"),
                            rs.getDate("inicio").toLocalDate(),
                            rs.getDate("fim").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    public List<Emergencia> listarTodos() throws SQLException {
        List<Emergencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM emergencias";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Emergencia(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                ));
            }
        }
        return lista;
    }

    public void atualizar(Emergencia emergencia) throws SQLException {
        String sql = "UPDATE emergencias SET titulo=?, descricao=?, inicio=?, fim=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emergencia.getTitulo());
            stmt.setString(2, emergencia.getDescricao());
            stmt.setDate(3, Date.valueOf(emergencia.getInicio()));
            stmt.setDate(4, Date.valueOf(emergencia.getFim()));
            stmt.setInt(5, emergencia.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM emergencias WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
