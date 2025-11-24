package papeleria.ui;

import java.awt.HeadlessException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import papeleria.dao.ProductoDAO;
import papeleria.dao.ProductoDAOImpl;
import papeleria.model.Producto;

public class FrmProductos extends javax.swing.JFrame {

    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    private JTable tabla;
    private DefaultTableModel tableModel;

    public FrmProductos() {
        initComponents();
        setLocationRelativeTo(null);

        btnAgregar.addActionListener(e -> abrirFormularioAgregar());
        btnEditar.addActionListener(this::accionEditar);
        btnEliminar.addActionListener(this::accionEliminar);
        btnRefrescar.addActionListener(e -> refrescarTabla());

        refrescarTabla();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Productos");

        JLabel lblTitulo = new JLabel("Gestión de Productos");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(tableModel);
        tabla.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBtns = new JPanel();
        panelBtns.add(btnAgregar);
        panelBtns.add(btnEditar);
        panelBtns.add(btnEliminar);
        panelBtns.add(btnRefrescar);

        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(lblTitulo, java.awt.BorderLayout.NORTH);
        panel.add(scroll, java.awt.BorderLayout.CENTER);
        panel.add(panelBtns, java.awt.BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    private void abrirFormularioAgregar() {
        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
            if (nombre == null || nombre.trim().isEmpty()) return;

            String categoriaStr = JOptionPane.showInputDialog(this, "ID Categoría:");
            int idCategoria = Integer.parseInt(categoriaStr);

            String precioStr = JOptionPane.showInputDialog(this, "Precio:");
            double precio = Double.parseDouble(precioStr);

            String stockStr = JOptionPane.showInputDialog(this, "Stock:");
            int stock = Integer.parseInt(stockStr);

            Producto p = new Producto();
            p.setNombre(nombre.trim());
            p.setIdCategoria(idCategoria);
            p.setPrecioVenta(precio);
            p.setStock(stock);

            ProductoDAO dao = new ProductoDAOImpl();
            dao.insertar(p);

            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            refrescarTabla();

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error al agregar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEditar(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.");
            return;
        }

        int id = (int) tableModel.getValueAt(fila, 0);

        try {
            ProductoDAO dao = new ProductoDAOImpl();
            Producto p = dao.buscarPorId(id);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", p.getNombre());
            if (nombre == null) return;

            String categoria = JOptionPane.showInputDialog(this, "Nueva categoría:", p.getIdCategoria());
            String precio = JOptionPane.showInputDialog(this, "Nuevo precio:", p.getPrecioVenta());
            String stock = JOptionPane.showInputDialog(this, "Nuevo stock:", p.getStock());

            p.setNombre(nombre);
            p.setIdCategoria(Integer.parseInt(categoria));
            p.setPrecioVenta(Double.parseDouble(precio));
            p.setStock(Integer.parseInt(stock));

            dao.actualizar(p);

            JOptionPane.showMessageDialog(this, "Producto actualizado.");
            refrescarTabla();

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage());
        }
    }

    private void accionEliminar(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.");
            return;
        }

        int id = (int) tableModel.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar producto ID " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            ProductoDAO dao = new ProductoDAOImpl();
            dao.eliminar(id);

            JOptionPane.showMessageDialog(this, "Producto eliminado.");
            refrescarTabla();

        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
    try {
        ProductoDAO dao = new ProductoDAOImpl();
        List<Producto> lista = dao.listar();

        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Agregar filas
        for (Producto p : lista) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getIdCategoria(),
                p.getPrecioVenta(),
                p.getStock()
            });
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar productos: " + ex.getMessage());
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmProductos().setVisible(true));
    }
}
