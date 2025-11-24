package papeleria.dao;

import papeleria.database.DBConnection;
import papeleria.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conn;

    // ✔ Constructor correcto (usa DBConnection)
    public UsuarioDAO() throws SQLException {
        this.conn = DBConnection.getConnection();
    }

    public void insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO papeleria.usuarios (nombre, usuario, contraseña) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getContraseña());

            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM papeleria.usuarios WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contraseña")
                );
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM papeleria.usuarios ORDER BY id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contraseña")
                ));
            }
        }
        return lista;
    }
}
