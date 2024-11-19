package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/caldinho_do_chico";
    private static final String USER = "root";         // Coloque seu usuário do MySQL aqui
    private static final String PASSWORD = "1234"; // Coloque sua senha do MySQL aqui

    private static Connection connection = null;

    // Método para conectar ao banco de dados
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para fechar a conexão (opcional)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}
