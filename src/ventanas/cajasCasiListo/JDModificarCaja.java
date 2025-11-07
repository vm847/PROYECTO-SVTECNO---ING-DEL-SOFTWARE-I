package ventanas.cajasCasiListo;

import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;

/**
 *
 * @author Asus
 */
public class JDModificarCaja extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    Fechas fe = new Fechas();
    private FrmCajas frmCajas;
    String usuarioLogeado = "INDEFINIDO";
    private boolean cajaCerrada = false;
    
    
    public JDModificarCaja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.dateApertura.setMaxSelectableDate(new java.util.Date());
        this.dateCierre.setMaxSelectableDate(new java.util.Date());
        bd.llenarCombo(cboUsuario, "cod_usuario,nom_usuario", "usuarios");
        this.mostrarDatos("2");
    }
    public JDModificarCaja(java.awt.Frame parent, boolean modal, FrmCajas frmCajas, String codigo, String usuLogeado) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.dateApertura.setMaxSelectableDate(new java.util.Date());
        this.dateCierre.setMaxSelectableDate(new java.util.Date());
        this.setTitle("Modificar Caja - " + usuLogeado);
        this.frmCajas = frmCajas;
        this.usuarioLogeado = usuLogeado;
        bd.llenarCombo(cboUsuario, "cod_usuario,nom_usuario", "usuarios where rol_usuario = 'CAJERO'");
        this.mostrarDatos(codigo);
    }
    
    private void mostrarDatos(String codigo){
        String [] datos = bd.obtenerRegistro("select * from cajas where cod_caja = " + codigo);
        if(datos != null){
            this.txtCodigo.setText(datos[0]);
            bd.seleccionarItemComboBD(cboUsuario, Integer.parseInt(datos[1]));
            fe.mostrarFechaHoraJDateChooser(dateApertura, datos[2]);
            if(datos[3] != null){                
                fe.mostrarFechaHoraJDateChooser(dateCierre, datos[3]);
                this.cajaCerrada = true;
            }
            this.lblUsuarioCreacion.setText(datos[5]);
            this.lblFechaCreacion.setText(fe.formatearFechaHora(datos[6]));
            this.lblUsuarioModificacion.setText(datos[7]);
            if(datos[8] != null){                
                this.lblFechaModificacion.setText(fe.formatearFechaHora(datos[8]));
            }
        }
    }
    
    public void setUsuario(int codigo){
        bd.seleccionarItemComboBD(cboUsuario, codigo);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        cboUsuario = new javax.swing.JComboBox<>();
        dateApertura = new com.toedter.calendar.JDateChooser();
        dateCierre = new com.toedter.calendar.JDateChooser();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 340, 30));

        cboUsuario.setEditable(true);
        jPanel1.add(cboUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 340, 30));

        dateApertura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateApertura.setDateFormatString("dd-MM-yyyy HH:mm");
        jPanel1.add(dateApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 340, 30));

        dateCierre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateCierre.setDateFormatString("dd-MM-yyyy HH:mm:ss");
        jPanel1.add(dateCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 340, 30));

        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 90, 40));

        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 90, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cierre");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 40, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cajero");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Codigo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Apertura");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 50, 30));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 130, 16));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 130, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        boolean guardado = false;
        String codigo = this.txtCodigo.getText();
        int codUsuario = bd.obtenerCodCombo(cboUsuario);
        String fechaApertura = fe.formatearFechaHoraJDateChooser(dateApertura);
        String fechaCierre = fe.formatearFechaHoraJDateChooser(dateCierre);
        System.out.println("fecha apertura = " + fechaApertura);
        System.out.println("fecha cierre = " + fechaCierre);
        if(!fechaApertura.equals("-1")){
            if(cajaCerrada){
                if(!fechaCierre.equals("-1")){
                    if(fe.compararFechas(fechaApertura, fechaCierre)){
                        String valores = "cod_usuario=" + codUsuario + ",fecha_apertura_caja='" + fechaApertura + "',fecha_cierre_caja='" + fechaCierre + 
                        "',usuario_modificacion='" + usuarioLogeado + "',fecha_modificacion=NOW()";
                        bd.modificarRegistro("cajas", valores, "cod_caja=" + codigo);
                        guardado = true;
                    }
                }else{
                    String valores1 = "cod_usuario=" + codUsuario + ",fecha_apertura_caja='" + fechaApertura + "',fecha_cierre_caja=NULL, usuario_reapertura='" + usuarioLogeado + 
                    "',usuario_modificacion='" + usuarioLogeado + "',fecha_modificacion=NOW()";
                    bd.modificarRegistro("cajas", valores1, "cod_caja=" + codigo);
                    guardado = true;
                }
            }else{
                if(!fechaCierre.equals("-1")){
                    if(fe.compararFechas(fechaApertura, fechaCierre)){
                        String valores = "cod_usuario=" + codUsuario + ",fecha_apertura_caja='" + fechaApertura + "',fecha_cierre_caja='" + fechaCierre + 
                        "',usuario_modificacion='" + usuarioLogeado + "',fecha_modificacion=NOW()";
                        bd.modificarRegistro("cajas", valores, "cod_caja=" + codigo);
                        guardado = true;
                    }
                }else{
                    String valores = "cod_usuario=" + codUsuario + ",fecha_apertura_caja='" + fechaApertura + "',fecha_cierre_caja=NULL" + 
                    ",usuario_modificacion='" + usuarioLogeado + "',fecha_modificacion=NOW()";
                    bd.modificarRegistro("cajas", valores, "cod_caja=" + codigo);
                    guardado = true;
                }
            }
            if(guardado){                
                frmCajas.actualizarGrilla();
                this.dispose();
                JOptionPane.showMessageDialog(null, "Caja Modificada", "Registro Actualizado", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debes ingresar la fecha de apertura");
            this.dateApertura.requestFocus();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JDModificarCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDModificarCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDModificarCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDModificarCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDModificarCaja dialog = new JDModificarCaja(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboUsuario;
    private com.toedter.calendar.JDateChooser dateApertura;
    private com.toedter.calendar.JDateChooser dateCierre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
