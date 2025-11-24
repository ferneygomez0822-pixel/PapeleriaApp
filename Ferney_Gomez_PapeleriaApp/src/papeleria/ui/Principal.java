package papeleria.ui;

public class Principal extends javax.swing.JFrame {

    public Principal() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PapeleríaApp - Menú Principal");

        javax.swing.JButton btnProductos = new javax.swing.JButton("Productos");
        javax.swing.JButton btnClientes = new javax.swing.JButton("Clientes");
        javax.swing.JButton btnFacturas = new javax.swing.JButton("Facturas");
        javax.swing.JButton btnUsuarios = new javax.swing.JButton("Usuarios");
        javax.swing.JButton btnAcercaDe = new javax.swing.JButton("Acerca de");

        btnProductos.addActionListener(e -> new FrmProductos().setVisible(true));
        btnClientes.addActionListener(e -> new FrmClientes().setVisible(true));
        btnFacturas.addActionListener(e -> new FrmFacturas().setVisible(true));
        btnUsuarios.addActionListener(e -> new FrmUsuarios().setVisible(true));
        btnAcercaDe.addActionListener(e -> new FrmAcercaDe().setVisible(true));

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
        panel.add(btnProductos);
        panel.add(btnClientes);
        panel.add(btnFacturas);
        panel.add(btnUsuarios);
        panel.add(btnAcercaDe);

        add(panel);
        pack();
    }

    public static void main(String[] args) {
        new Principal().setVisible(true);
    }
}

