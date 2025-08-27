package weg.seguranca.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Bean
    public CommandLineRunner databaseConnectionTest(@Autowired DataSource dataSource) {
        return args -> {
            logger.info("=== TESTANDO CONEXÃO COM BANCO DE DADOS ===");
            try (Connection connection = dataSource.getConnection()) {
                logger.info("✅ Conexão com banco de dados estabelecida com sucesso!");
                logger.info("URL do banco: {}", connection.getMetaData().getURL());
                logger.info("Nome do banco: {}", connection.getCatalog());
                logger.info("Versão do banco: {}", connection.getMetaData().getDatabaseProductVersion());
                logger.info("Driver: {}", connection.getMetaData().getDriverName());
            } catch (SQLException e) {
                logger.error("❌ Erro ao conectar com banco de dados: {}", e.getMessage());
                logger.error("Detalhes do erro:", e);
                // Força a aplicação a falhar se não conseguir conectar ao banco
                throw new RuntimeException("Falha na conexão com banco de dados", e);
            }
            logger.info("=== CONEXÃO TESTADA COM SUCESSO ===");
        };
    }
}
