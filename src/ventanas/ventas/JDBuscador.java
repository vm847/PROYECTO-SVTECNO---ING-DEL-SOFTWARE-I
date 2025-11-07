package ventanas.ventas;

import ventanas.Compras.*;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDBuscador extends javax.swing.JDialog {
    String tablaA;
    private FrmVentas frmVentas;
    Grilla gr = new Grilla();
    FrmAjustes aj = new FrmAjustes();
    
    
    public JDBuscador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    } 
    
    
    public JDBuscador(java.awt.Frame parent, boolean modal, FrmVentas frmVentas, String tabla, String [] campos, String condicion, String [] cabeceras, int [] ancho) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Buscador");
        this.tablaA = tabla;
        this.frmVentas = frmVentas;
        gr.configurarModelo(grdBuscar, cabeceras, ancho);
        gr.cargarGrilla(grdBuscar, tabla, campos, condicion); 
        aj.soloMayusculas(txtBuscar, 50);
        txtBuscar.requestFocus();
        if(tabla.equals(" cuotas_credito_cliente ccc ")){
            gr.alinearDerecha(grdBuscar, 5);
            gr.formatoFechaGrilla(grdBuscar, 3);
            gr.formatoFechaGrilla(grdBuscar, 6);
        }
    }
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdBuscar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 980, 40));

        grdBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "RUC", "Telefono", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdBuscarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdBuscar);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 65, 1060, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BUSCAR");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1075, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        gr.filtrarGrilla(grdBuscar, txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void grdBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdBuscarMouseClicked
        if(evt.getClickCount() == 2){
            int fila = this.grdBuscar.getSelectedRow();
            if(fila != -1){
                if(this.tablaA.equals("clientes")){
                    frmVentas.setCliente(this.grdBuscar.getValueAt(fila, 0).toString(), this.grdBuscar.getValueAt(fila, 1).toString(),
                    this.grdBuscar.getValueAt(fila, 2).toString());
                    this.dispose();
                }
                if(this.tablaA.equals("productos p")){
                    String codigo = this.grdBuscar.getValueAt(fila, 0).toString();
                    String nombre = this.grdBuscar.getValueAt(fila, 1).toString();
                    String precio = this.grdBuscar.getValueAt(fila, 3).toString();
                    String iva = this.grdBuscar.getValueAt(fila, 5).toString();
                    frmVentas.setProducto(codigo, nombre, precio, iva);
                    this.dispose();
                }
                if(this.tablaA.equals(" cuotas_credito_cliente ccc ")){
                    String codigo = this.grdBuscar.getValueAt(fila, 0).toString();
                    String nombre = this.grdBuscar.getValueAt(fila, 1).toString();
                    String precio = this.grdBuscar.getValueAt(fila, 5).toString();
                    String iva = "10%";
                    frmVentas.setCuota(codigo, nombre, precio, iva);
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_grdBuscarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDBuscador dialog = new JDBuscador(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grdBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
