package weg.seguranca.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class MySQLRepository {

    @Autowired
    private DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void testConnection() {
        try (Connection connection = getConnection()) {
            System.out.println("✅ Conexão MySQL estabelecida com sucesso!");
            System.out.println("URL: " + connection.getMetaData().getURL());
        } catch (SQLException e) {
            System.err.println("❌ Erro de conexão MySQL: " + e.getMessage());
        }
    }
}
