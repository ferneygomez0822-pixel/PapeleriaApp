package papeleria.dao;

import papeleria.model.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {

    void insertar(Producto producto) throws SQLException;

    Producto buscarPorId(int id) throws SQLException;

    void actualizar(Producto producto) throws SQLException;

    void eliminar(int id) throws SQLException;

    List<Producto> listar() throws SQLException;
}