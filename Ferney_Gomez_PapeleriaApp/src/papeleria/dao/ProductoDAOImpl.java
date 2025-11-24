package papeleria.dao;

import papeleria.database.DBConnection;
import papeleria.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private Connection conn;

    public ProductoDAOImpl() {
    try {
        this.conn = DBConnection.getConnection();
    } catch (SQLException e) {
        throw new RuntimeException("Error conectando a la base de datos", e);
    }
        if (conn == null) {
            System.err.println("❌ Error: no se pudo conectar a la base de datos.");
        } else {
            System.out.println("✔ Conexión DAO OK");
        }
    }

    public ProductoDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // ============================================================
    @Override
    public void insertar(Producto p) throws SQLException {
        if (conn == null) throw new SQLException("No hay conexión a la base de datos.");

        String sql = "INSERT INTO papeleria.productos " +
                     "(nombre, descripcion, categoria_id, precio_venta) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, p.getNombre());
        stmt.setString(2, p.getDescripcion());
        stmt.setInt(3, p.getIdCategoria());
        stmt.setDouble(4, p.getPrecioVenta());

        int filas = stmt.executeUpdate();

        if (filas == 0) {
            throw new SQLException("No se insertó el producto.");
        }

        // Obtener ID generado
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            p.setId(rs.getInt(1));
        }

        rs.close();
        stmt.close();
    }

    // ============================================================
    @Override
    public Producto buscarPorId(int id) throws SQLException {
        if (conn == null) throw new SQLException("No hay conexión a la base de datos.");

        String sql = "SELECT p.id, p.nombre, p.descripcion, p.categoria_id, p.precio_venta, " +
                     "COALESCE(i.cantidad, 0) AS stock " +
                     "FROM papeleria.productos p " +
                     "LEFT JOIN papeleria.inventario i ON p.id = i.producto_id " +
                     "WHERE p.id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setIdCategoria(rs.getInt("categoria_id"));
            p.setPrecioVenta(rs.getDouble("precio_venta"));
            p.setStock(rs.getInt("stock"));

            rs.close();
            stmt.close();
            return p;
        }

        rs.close();
        stmt.close();
        return null;
    }

    // ============================================================
    @Override
    public void actualizar(Producto p) throws SQLException {
        if (conn == null) throw new SQLException("No hay conexión a la base de datos.");

        String sql = "UPDATE papeleria.productos " +
                     "SET nombre=?, descripcion=?, categoria_id=?, precio_venta=? " +
                     "WHERE id=?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, p.getNombre());
        stmt.setString(2, p.getDescripcion());
        stmt.setInt(3, p.getIdCategoria());
        stmt.setDouble(4, p.getPrecioVenta());
        stmt.setInt(5, p.getId());

        stmt.executeUpdate();
        stmt.close();
    }

    // ============================================================
    @Override
    public void eliminar(int id) throws SQLException {
        if (conn == null) throw new SQLException("No hay conexión a la base de datos.");

        String sql = "DELETE FROM papeleria.productos WHERE id=?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        stmt.close();
    }

    // ============================================================
    @Override
    public List<Producto> listar() throws SQLException {
        if (conn == null) throw new SQLException("No hay conexión a la base de datos.");

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT p.id, p.nombre, p.descripcion, p.categoria_id, p.precio_venta, " +
                     "COALESCE(i.cantidad, 0) AS stock " +
                     "FROM papeleria.productos p " +
                     "LEFT JOIN papeleria.inventario i ON p.id = i.producto_id " +
                     "ORDER BY p.id";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setIdCategoria(rs.getInt("categoria_id"));
            p.setPrecioVenta(rs.getDouble("precio_venta"));
            p.setStock(rs.getInt("stock"));

            lista.add(p);
        }

        rs.close();
        stmt.close();

        return lista;
    }
}
