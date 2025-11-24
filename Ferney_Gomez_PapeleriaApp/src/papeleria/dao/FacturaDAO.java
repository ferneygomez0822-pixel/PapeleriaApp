package papeleria.dao;

import papeleria.model.Factura;
import java.util.List;

public interface FacturaDAO {
    void insertar(Factura factura);
    List<Factura> listar();
    void eliminar(int id);
}
