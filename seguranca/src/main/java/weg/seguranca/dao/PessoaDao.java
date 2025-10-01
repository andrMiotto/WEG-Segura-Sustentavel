package weg.seguranca.dao;

import weg.seguranca.util.MySQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import weg.seguranca.model.Pessoa;

public class PessoaDao {

    public void insert(Pessoa pessoa) throws  SQLException {
        String sql = """
                INSERT INTO pessoa (nome, cadastro, tipo, situacao_de_risco, sala_atual, emergencia_atual)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

       try(Connection conn = MySQLDatabase.connect();
           PreparedStatement stmt = conn.prepareStatement(sql)){

           stmt.setString(1, pessoa.getNome());
           stmt.setInt(2, pessoa.getCadastro());
           stmt.setString(3, pessoa.getTipo());
           stmt.setInt(4, pessoa.getSituacao_de_risco());
           stmt.setInt(5, pessoa.getSala_atual());
           stmt.setInt(6, pessoa.getEmergencia_atual());

           stmt.executeUpdate();

        }
    }

    public List<Pessoa> select() throws SQLException{
        String sql = """
                SELECT id, nome, cadastro, tipo, situacao_de_risco, sala_atual, emergencia_atual
                FROM pessoa;
                """;

        List<Pessoa> pessoas = new ArrayList<>();

         try(Connection conn = MySQLDatabase.connect();
              PreparedStatement stmt = conn.prepareStatement(sql)){
             ResultSet rs = stmt.executeQuery();

             while(rs.next()){
                 int id = rs.getInt("id");
                 String nome = rs.getString("nome");
                 int cadastro = rs.getInt("cadastro");
                 String tipo = rs.getString("tipo");
                 int situacao_de_risco = rs.getInt("situacao_de_risco");
                 int sala_atual = rs.getInt("sala_atual");
                 int emergencia_atual = rs.getInt("emergencia_atual");

                 Pessoa pessoa = new Pessoa(id, nome, cadastro, tipo, situacao_de_risco, sala_atual, emergencia_atual);

                 pessoas.add(pessoa);
             }
         }

         return pessoas;
    }

    public void updateSituacaoRisco(Pessoa pessoa) throws SQLException {
        String sql = """
                UPDATE pessoa SET situacao_de_risco = ? WHERE id = ?;
                """;

        try (Connection conn = MySQLDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoa.getSituacao_de_risco());
            stmt.setInt(2, pessoa.getId());

            stmt.executeUpdate();
        }
    }

    public void updateSalaAtual(Pessoa pessoa) throws SQLException {
        String sql = """
                UPDATE pessoa SET id_sala_atual = ? WHERE id = ?;
                """;

        try (Connection conn = MySQLDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoa.getSala_atual());
            stmt.setInt(2, pessoa.getId());

            stmt.executeUpdate();
        }
    }

    public void updateEmergenciaAtual(Pessoa pessoa) throws SQLException {
        String sql = """
                UPDATE pessoa SET id_emergencia_atual = ? WHERE id = ?;
                """;

        try (Connection conn = MySQLDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoa.getEmergencia_atual());
            stmt.setInt(2, pessoa.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conn = MySQLDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

    }
}
