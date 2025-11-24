package papeleria.ui;

import papeleria.dao.ProductoDAO;
import papeleria.dao.ProductoDAOImpl;
import papeleria.dao.CategoriaDAO;
import papeleria.dao.CategoriaDAOImpl;
import papeleria.model.Producto;
import papeleria.model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductoForm extends JFrame {

    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    private JTable tblProductos;
    private DefaultTableModel modeloTabla;

    private JTextField textNombre, textPrecio, textStock, textCategoria;
    private JComboBox<String> jComboBoxCategorias;

    private JButton btnAgregar, btnActualizar, btnEliminar, btnRefrescar;

    public ProductoForm() {
        setTitle("Gestión de Productos");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        cargarCategorias();
        cargarTabla();
    }

    private void initComponents() {

        // PANEL SUPERIOR - FORMULARIO
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nombre:"));
        textNombre = new JTextField();
        panelForm.add(textNombre);

        panelForm.add(new JLabel("Categoría:"));
        jComboBoxCategorias = new JComboBox<>();
        panelForm.add(jComboBoxCategorias);

        panelForm.add(new JLabel("ID Categoría Seleccionada:"));
        textCategoria = new JTextField();
        textCategoria.setEditable(false);
        panelForm.add(textCategoria);

        panelForm.add(new JLabel("Precio:"));
        textPrecio = new JTextField();
        panelForm.add(textPrecio);

        panelForm.add(new JLabel("Stock:"));
        textStock = new JTextField();
        panelForm.add(textStock);

        add(panelForm, BorderLayout.NORTH);

        // PANEL BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);

        // TABLA
        modeloTabla = new DefaultTableModel(new String[]{
                "ID", "Nombre", "Categoria", "Precio", "Stock"
        }, 0);

        tblProductos = new JTable(modeloTabla);
        add(new JScrollPane(tblProductos), BorderLayout.CENTER);

        // EVENTOS
        btnAgregar.addActionListener(e -> btnAgregarActionPerformed(e));
        btnActualizar.addActionListener(e -> btnActualizarActionPerformed(e));
        btnEliminar.addActionListener(e -> btnEliminarActionPerformed(e));
        btnRefrescar.addActionListener(e -> btnRefrescarActionPerformed(e));

        jComboBoxCategorias.addActionListener(e -> jComboBoxCategoriasActionPerformed(e));

        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCamposDesdeTabla();
            }
        });
    }

    private void cargarCategorias() {
        try {
            jComboBoxCategorias.removeAllItems();
            List<Categoria> cats = categoriaDAO.listar();

            for (Categoria c : cats) {
                jComboBoxCategorias.addItem(c.getId() + " - " + c.getNombre());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Producto> lista = productoDAO.listar();

            for (Producto p : lista) {
                Categoria cat = categoriaDAO.buscarPorId(p.getIdCategoria());

                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        (cat != null ? cat.getNombre() : "Sin categoría"),
                        p.getPrecio(),
                        p.getStock()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos");
        }
    }

    private void cargarCamposDesdeTabla() {
        int fila = tblProductos.getSelectedRow();
        if (fila == -1) return;

        textNombre.setText(String.valueOf(tblProductos.getValueAt(fila, 1)));
        textCategoria.setText(String.valueOf(tblProductos.getValueAt(fila, 2))); // nombre cat
        textPrecio.setText(String.valueOf(tblProductos.getValueAt(fila, 3)));
        textStock.setText(String.valueOf(tblProductos.getValueAt(fila, 4)));
    }

    private void limpiarCampos() {
        textNombre.setText("");
        textCategoria.setText("");
        textPrecio.setText("");
        textStock.setText("");
        jComboBoxCategorias.setSelectedIndex(-1);
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nombre = textNombre.getText();
            int idCategoria = obtenerIdCategoriaSeleccionada();
            double precio = Double.parseDouble(textPrecio.getText());
            int stock = Integer.parseInt(textStock.getText());

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setIdCategoria(idCategoria);
            p.setPrecio(precio);
            p.setStock(stock);

            productoDAO.insertar(p);
            cargarTabla();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Producto agregado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int fila = tblProductos.getSelectedRow();
            if (fila == -1) throw new Exception("Seleccione un producto");

            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            String nombre = textNombre.getText();
            int idCategoria = obtenerIdCategoriaSeleccionada();
            double precio = Double.parseDouble(textPrecio.getText());
            int stock = Integer.parseInt(textStock.getText());

            Producto p = new Producto(id, nombre, idCategoria, precio, stock);

            productoDAO.actualizar(p);
            cargarTabla();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Producto actualizado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int fila = tblProductos.getSelectedRow();
            if (fila == -1) throw new Exception("Seleccione un producto");

            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

            productoDAO.eliminar(id);
            cargarTabla();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Producto eliminado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private int obtenerIdCategoriaSeleccionada() throws Exception {
        if (jComboBoxCategorias.getSelectedIndex() == -1)
            throw new Exception("Seleccione una categoría");

        String item = jComboBoxCategorias.getSelectedItem().toString();
        return Integer.parseInt(item.split(" - ")[0]);
    }

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
        cargarTabla();
    }

    private void jComboBoxCategoriasActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxCategorias.getSelectedIndex() != -1) {
            String item = jComboBoxCategorias.getSelectedItem().toString();
            textCategoria.setText(item.split(" - ")[0]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductoForm().setVisible(true));
    }
}
