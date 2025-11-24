package papeleria.ui;

import papeleria.dao.ClienteDAOImpl;
import papeleria.database.DBConnection;
import papeleria.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FrmClientes extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDAOImpl clienteDAO;

    public FrmClientes() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {

        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Documento", "Teléfono", "Correo", "Dirección"},
            0
        );

        tablaClientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBounds(20, 20, 540, 300);
        add(scroll);

        try {
            // ✔ AQUÍ VA LO QUE ME PREGUNTASTE
            Connection conn = DBConnection.getConnection();
            clienteDAO = new ClienteDAOImpl(conn);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        try {
            List<Cliente> lista = clienteDAO.listar();

            modeloTabla.setRowCount(0); // limpiar

            for (Cliente c : lista) {
                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getNombre(),
                        c.getCedulaRuc(),
                        c.getTelefono(),
                        c.getCorreo(),
                        c.getDireccion()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage());
        }
    }
}
