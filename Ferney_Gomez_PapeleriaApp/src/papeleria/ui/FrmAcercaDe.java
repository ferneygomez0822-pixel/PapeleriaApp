package papeleria.ui;

public class FrmAcercaDe extends javax.swing.JFrame {

    public FrmAcercaDe() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ayuda");

        // ---- TÍTULO ----
        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Ayuda del Sistema");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // ---- CUADRO DE TEXTO ----
        javax.swing.JTextArea txtInfo = new javax.swing.JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtInfo.setText(
                "Sistema de Gestión de Papelería\n\n"
                + "Este módulo contiene la ayuda general del sistema.\n\n"
                + "Autor:\n"
                + "Deninson Ferney Gómez Peña\n\n"
                + "Proyecto desarrollado en Java con PostgreSQL\n\n"
                + "Unidades Tecnológicas de Santander\n"
                + "2025"
        );

        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(txtInfo);

        // ---- PANEL GENERAL ----
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.BorderLayout(10, 10));

        panel.add(lblTitulo, java.awt.BorderLayout.NORTH);
        panel.add(scroll, java.awt.BorderLayout.CENTER);

        add(panel);
        pack();
        setSize(500, 350); // tamaño más adecuado
    }
}

