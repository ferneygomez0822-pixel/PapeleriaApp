package papeleria.dao;

import papeleria.database.DBConnection;
import papeleria.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categoria";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNombre(rs.getString("nombre"));
                lista.add(cat);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar categorías: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Categoria buscar(int id) {
        Categoria cat = null;
        String sql = "SELECT id, nombre FROM categoria WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNombre(rs.getString("nombre"));
            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar categoría: " + e.getMessage());
        }

        return cat;
    }

    @Override
    public Categoria buscarPorId(int idCategoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
