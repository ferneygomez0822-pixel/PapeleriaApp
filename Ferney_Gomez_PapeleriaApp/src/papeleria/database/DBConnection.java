package papeleria.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/papeleria";
    private static final String USER = "postgres";
    private static final String PASS = "0822";

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✓ Conexión exitosa a PostgreSQL");

            // 🔥 CAMBIO IMPORTANTE
            conn.createStatement().execute("SET search_path TO name");
        }
        return conn;
    }
}
