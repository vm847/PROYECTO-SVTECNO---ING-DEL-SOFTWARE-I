package ventanas.lineaCredito;

import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDModificarLineaCredito extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Fechas fe = new Fechas();
    private FrmLineaCredito frmLineaCredito;
    String usuarioLogeado;
    
    public JDModificarLineaCredito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.mostrarDatos("1");
        bd.llenarComboHasta20(cboCliente, " cod_cliente, CONCAT(nombre_cliente,' ',apellido_cliente) ", "clientes where estado_cliente='ACTIVO'");
        this.cboCliente.requestFocus();
    }
    
    public JDModificarLineaCredito(java.awt.Frame parent, boolean modal, FrmLineaCredito frmLineaCredito, String usuLogeado, String codigo) {
        super(parent, modal);
        initComponents();
        this.restricciones();
        this.frmLineaCredito = frmLineaCredito;
        this.usuarioLogeado= usuLogeado;
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Modificar Linea de Credito - " + usuLogeado);
        this.mostrarDatos(codigo);
        this.cboCliente.requestFocus();
    }
    
    
    private void restricciones(){
        aj.soloNumeros(txtMonto, 12);
    }
    
    private boolean verificarFormatoFecha(String fecha){
        if(fecha != null){
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return true;
            }
        }
        return false;
    }
    
    private void mostrarDatos(String codigo){
        String sql = "select "
                + "lc.cod_linea_credito, "
                + "lc.cod_cliente, "
                + "CONCAT(cl.nombre_cliente,' ',cl.apellido_cliente),"
                + "lc.monto_total, "
                + "lc.fecha_vencimiento, "
                + "lc.estado_linea_credito "
                + "from lineas_credito lc "
                + " Join clientes cl ON lc.cod_cliente = cl.cod_cliente"
                + " where cod_linea_credito = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos != null){
            this.txtCodigo.setText(datos[0]);
            bd.agregarItemCombo(cboCliente, Integer.parseInt(datos[1]), datos[2]);
            this.txtMonto.setText(datos[3]);
            fe.mostrarFechaJDateChooser(dateVencimiento, datos[4]);
            bd.seleccionarItemCombo(cboEstado, datos[5]);
        }
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
        dateVencimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 130, 16));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 130, 16));

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 90, 40));

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 90, 40));

        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        jPanel1.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 350, 30));

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 350, 30));

        dateVencimiento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateVencimiento.setDateFormatString("dd MM yyyy");
        jPanel1.add(dateVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 140, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Vence");
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
        jPanel1.add(cboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 350, 30));

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboEstado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 120, 30));

        jLabel13.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Estado");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 50, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        String [] lineaCredito = bd.obtenerRegistro("select monto_total, saldo_disponible from lineas_credito where cod_linea_credito=" + codigo);
        int codCliente = bd.obtenerCodCombo(cboCliente);
        String monto = this.txtMonto.getText();
        float saldoDisponible = (Float.parseFloat(lineaCredito[0]) - Float.parseFloat(lineaCredito[1])) + Float.parseFloat(monto);
        String vencimiento = fe.formatoFecha(dateVencimiento);
        String estado = this.cboEstado.getSelectedItem().toString();
        if(!monto.isEmpty()){
            if(vencimiento!= "-1" && verificarFormatoFecha(vencimiento)){
                if(!bd.verificarDuplicidadInt("lineas_credito", "cod_cliente", codCliente, "cod_linea_credito", codigo)){
                    float saldoDisponibre = Float.parseFloat(monto) - (Float.parseFloat(lineaCredito[0]) - Float.parseFloat(lineaCredito[1])); 
                    String valores = "cod_cliente=" + codCliente
                    + ",monto_total=" + monto
                    + ",saldo_disponible=" + saldoDisponibre
                    + ",fecha_vencimiento='" + vencimiento + "'"
                    + ",estado_linea_credito='" + estado + "'";
                    bd.modificarRegistro("lineas_credito", valores, "cod_linea_credito=" + codigo);
                    this.dispose();
                    frmLineaCredito.mostrarDatos(codigo);
                    JOptionPane.showMessageDialog(null, "Linea de Credito Actualizada", "Registro Actualizado", JOptionPane.INFORMATION_MESSAGE);
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
            java.util.logging.Logger.getLogger(JDModificarLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDModificarLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDModificarLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDModificarLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDModificarLineaCredito dialog = new JDModificarLineaCredito(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboEstado;
    private com.toedter.calendar.JDateChooser dateVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
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
