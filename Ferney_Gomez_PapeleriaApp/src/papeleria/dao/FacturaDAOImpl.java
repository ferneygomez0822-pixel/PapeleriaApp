package papeleria.dao;

import papeleria.database.DBConnection;
import papeleria.model.Factura;
import papeleria.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    @Override
    public void insertar(Factura factura) {
        String sql = "INSERT INTO papeleria.facturas (cliente_id, fecha, total) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, factura.getCliente().getId());
            stmt.setDate(2, Date.valueOf(factura.getFecha()));
            stmt.setDouble(3, factura.getTotal());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Factura> listar() {
        List<Factura> lista = new ArrayList<>();

      String sql = 
    "SELECT f.id, f.fecha, f.total, " +
    "c.id AS cliente_id, c.nombre AS cliente_nombre " +
    "FROM papeleria.facturas f " +
    "LEFT JOIN papeleria.clientes c ON f.cliente_id = c.id " +
    "ORDER BY f.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Factura factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setFecha(rs.getDate("fecha").toLocalDate());
                factura.setTotal(rs.getDouble("total"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNombre(rs.getString("cliente_nombre"));

                factura.setCliente(cliente);

                lista.add(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM papeleria.facturas WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
