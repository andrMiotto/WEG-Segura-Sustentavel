package weg.seguranca.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase {
        private static final String URL = "jdbc:mysql://root:ZtzdINLCVuLMfpSTlHudposOErVCfBhq@yamabiko.proxy.rlwy.net:23402/railway";
        private static final String USER = "root";
        private static final String PASSWORD = "ZtzdINLCVuLMfpSTlHudposOErVCfBhq";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main (String [] args ){
        try{
            Connection conn = connect();
            if(conn != null){
                System.out.println("Conexão bem sucedida!");
                conn.close();
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
