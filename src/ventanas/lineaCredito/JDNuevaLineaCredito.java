package ventanas.lineaCredito;

import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDNuevaLineaCredito extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Fechas fe = new Fechas();
    private FrmLineaCredito frmLineaCredito;
    String usuarioLogeado;
    
    public JDNuevaLineaCredito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Nueva Linea de Credito - ");
        bd.llenarComboHasta20(cboCliente, " cod_cliente, CONCAT(nombre_cliente,' ',apellido_cliente) ", "clientes where estado_cliente='ACTIVO'");
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("lineas_credito", "cod_linea_credito"));
    }
    public JDNuevaLineaCredito(java.awt.Frame parent, boolean modal, FrmLineaCredito frmLineaCredito, String usuLogeado) {
        super(parent, modal);
        initComponents();
        this.frmLineaCredito = frmLineaCredito;
        this.usuarioLogeado= usuLogeado;
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Nueva Linea de Credito - " + usuLogeado);
        bd.llenarComboHasta20(cboCliente, " cod_cliente, CONCAT(nombre_cliente,' ',apellido_cliente) ", "clientes where estado_cliente='ACTIVO'");
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("lineas_credito", "cod_linea_credito"));
        this.cboCliente.requestFocus();
    }
    
    private void restricciones(){
        aj.soloNumeros(txtMonto, 12);
    }
    
    public void setCliente(int codigo, String nombreApellido){
        if(!bd.seleccionarItemComboBD(cboCliente, codigo)){
            bd.agregarItemCombo(cboCliente, codigo, nombreApellido);
        }
    }
    
    private boolean verificarFormatoFecha(String fecha){
        if(fecha != null){
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtMonto = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        dateVencimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 130, 16));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 130, 16));

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 90, 40));

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 90, 40));

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        jPanel1.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 290, 30));

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 290, 30));

        btnBuscar.setBackground(new java.awt.Color(204, 204, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 30, 30));

        dateVencimiento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateVencimiento.setDateFormatString("dd MM yyyy");
        jPanel1.add(dateVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 290, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Vencimiento");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 70, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Codigo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cliente");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 50, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Monto");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 50, 30));

        cboCliente.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 250, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String codigo = this.txtCodigo.getText();
        int codCliente = bd.obtenerCodCombo(cboCliente);
        String monto = this.txtMonto.getText();
        String saldoDisponible = this.txtMonto.getText();
        String vencimiento = fe.formatoFecha(dateVencimiento);
        System.out.println(vencimiento);
        if(!monto.isEmpty()){
            if(vencimiento!= "-1" && verificarFormatoFecha(vencimiento)){
                if(!bd.verificarDuplicidadInt("lineas_credito", "cod_cliente", codCliente, "cod_linea_credito", codigo)){
                    String campos = "cod_linea_credito,"
                            + "cod_cliente,"
                            + "monto_total,"
                            + "saldo_disponible,"
                            + "fecha_asignacion,"
                            + "fecha_vencimiento";
                    String valores = codigo
                            + "," + codCliente
                            + "," + monto
                            + "," + saldoDisponible
                            + ",NOW()"
                            + ",'" + vencimiento + "'";
                    bd.insertarRegistro("lineas_credito", campos, valores);
                    this.dispose();
                    frmLineaCredito.mostrarDatos(codigo);
                    JOptionPane.showMessageDialog(null, "Nueva Linea de Credito Guardada", "Registro Guardado", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Este cliente ya cuenta con una linea de credito");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese la fecha de vencimiento");
                this.dateVencimiento.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debes definir el monto de la linea de credito");
            this.txtMonto.requestFocus();
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String [] campos ={"cod_cliente", "CONCAT(nombre_cliente,' ',apellido_cliente)", "ruc_cliente"};
        String [] cabeceras = {"Codigo", "Nombre y Apellido", "RUC/CI"};
        int [] ancho = {100,400,300};
        JDBuscador jB = new JDBuscador(this, true, this, null, campos, "where estado_cliente = 'ACTIVO'", cabeceras, ancho);
        jB.setVisible(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(JDNuevaLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDNuevaLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDNuevaLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDNuevaLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDNuevaLineaCredito dialog = new JDNuevaLineaCredito(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboCliente;
    private com.toedter.calendar.JDateChooser dateVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
