package papeleria.ui;

import papeleria.dao.UsuarioDAO;
import papeleria.model.Usuario;
import papeleria.database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FrmUsuarios extends javax.swing.JFrame {

    private JTable tablaUsuarios;
    private DefaultTableModel modelo;

    public FrmUsuarios() {
        initComponents();
        cargarUsuarios();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Usuarios");
        setSize(500, 400);

        modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Usuario"} // columnas correctas
        );

        tablaUsuarios = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);

        add(scroll);
    }

    private void cargarUsuarios() {
        try {
            Connection conn = DBConnection.getConnection();
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> lista = dao.listar();

            modelo.setRowCount(0); // limpiar tabla

            for (Usuario u : lista) {
                modelo.addRow(new Object[]{
                        u.getId(),
                        u.getNombre(),
                        u.getUsuario()
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando usuarios: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
