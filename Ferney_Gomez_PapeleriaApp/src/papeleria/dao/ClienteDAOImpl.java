package papeleria.dao;

import papeleria.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl {

    private Connection conn;

    public ClienteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Cliente> listar() throws SQLException {

        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM papeleria.clientes ORDER BY id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cedula_ruc"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                ));
            }
        }

        return lista;
    }

    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO papeleria.clientes (nombre, cedula_ruc, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getCedulaRuc());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getDireccion());
            stmt.executeUpdate();
        }
    }
}
