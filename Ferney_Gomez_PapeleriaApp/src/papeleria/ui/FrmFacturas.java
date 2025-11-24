package papeleria.ui;

import papeleria.dao.FacturaDAO;
import papeleria.dao.FacturaDAOImpl;
import papeleria.model.Factura;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmFacturas extends javax.swing.JFrame {

    public FrmFacturas() {
        initComponents();
        setLocationRelativeTo(null);
        cargarFacturas();   
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFacturas = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Facturas");

        tablaFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Cliente", "Fecha", "Total"
            }
        ));

        jScrollPane1.setViewportView(tablaFacturas);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnActualizar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizar)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }

   
    private void cargarFacturas() {
        try {
            FacturaDAO dao = new FacturaDAOImpl();
            List<Factura> lista = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) tablaFacturas.getModel();
            modelo.setRowCount(0);  

            for (Factura f : lista) {
                modelo.addRow(new Object[]{
                        f.getId(),
                        f.getCliente().getNombre(), 
                        f.getFecha(),
                        f.getTotal()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando facturas: " + e.getMessage(),
                    "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarFacturas();
    }

 
    private javax.swing.JButton btnActualizar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaFacturas;

}
