import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexaoCleverCloud {
    public static void main(String[] args) {
        String url = "jdbc:mysql://bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com:3306/bmjbvsmlzkvrphhok83p";
        String usuario = "u0np3s8gbvzfctph";
        String senha = "zXUowZICMsDyvmzTVVqV";

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