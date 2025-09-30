package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteInsercao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://root:ZtzdINLCVuLMfpSTlHudposOErVCfBhq@yamabiko.proxy.rlwy.net:23402/railway";
        String usuario = "root";
        String senha = "ZtzdINLCVuLMfpSTlHudposOErVCfBhq";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("✅ Conexão estabelecida com sucesso!");

            String sqlSala = "INSERT INTO salas (numero, bloco, portaria, unidade, situacao_de_risco) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtSala = conexao.prepareStatement(sqlSala, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtSala.setInt(1, 211);
            stmtSala.setString(2, "CentroWeg");
            stmtSala.setInt(3, 16);
            stmtSala.setString(4, "Parque Fabril 1");
            stmtSala.setBoolean(5, false);

            stmtSala.executeUpdate();

            ResultSet rs = stmtSala.getGeneratedKeys();
            int idSala = 0;
            if (rs.next()) {
                idSala = rs.getInt(1);
                System.out.println("✅ Sala inserida com id: " + idSala);
            }
            stmtSala.close();

            // 2️⃣ Inserir uma pessoa na sala criada
            String sqlPessoa = "INSERT INTO pessoas (nome, cadastro, tipo, situacao_de_risco, id_sala_atual) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtPessoa = conexao.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, "Cadu Braga");
            stmtPessoa.setInt(2, 81336);
            stmtPessoa.setString(3, "Colaborador");
            stmtPessoa.setBoolean(4, false);
            stmtPessoa.setInt(5, 1);

            int linhasInseridas = stmtPessoa.executeUpdate();
            System.out.println("✅ Linhas inseridas na tabela pessoas: " + linhasInseridas);

            stmtPessoa.close();
            conexao.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC do MySQL não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Erro de conexão ou inserção no banco:");
            e.printStackTrace();
        }
    }
}
