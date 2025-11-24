package papeleria.dao;

import papeleria.model.Categoria;
import java.util.List;

public interface CategoriaDAO {

    List<Categoria> listar();

    Categoria buscar(int id);

    public Categoria buscarPorId(int idCategoria);
}
