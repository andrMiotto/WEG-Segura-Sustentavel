package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexaoCleverCloud {
    public static void main(String[] args) {
        String url = "jdbc:mysql://root:ZtzdINLCVuLMfpSTlHudposOErVCfBhq@yamabiko.proxy.rlwy.net:23402/railway";
        String usuario = "root";
        String senha = "ZtzdINLCVuLMfpSTlHudposOErVCfBhq";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Tentando conectar...");
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("✅ Conexão estabelecida com sucesso!");

            conexao.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC do MySQL não encontrado. Adicione o conector ao classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Erro de conexão com o banco:");
            e.printStackTrace();
        }
    }
}