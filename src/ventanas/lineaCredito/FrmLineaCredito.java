

package ventanas.lineaCredito;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;
import ventanas.principal.FrmAjustes;
/**
 *
 * @author Asus
 */
public class FrmLineaCredito extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    Fechas fe = new Fechas();
    FrmAjustes aj = new FrmAjustes();
    String usuarioLogeado;
    String rol;
    
    
    public FrmLineaCredito() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.limpiarCampos();
    }
    public FrmLineaCredito(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("LINEAS DE CREDITO - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.limpiarCampos();
        this.restricciones();
    }

    private void restricciones(){
        aj.soloNumeros(txtCodigo, 5);
        aj.enterActionPerformed(btnBuscar);
    }
    public void limpiarCampos(){
        this.txtCodigo.setText(null);
        this.txtNomApellido.setText(null);
        this.txtCredito.setText(null);
        this.txtCDisponible.setText(null);
        this.txtFechaAsignacion.setText(null);
        this.txtVencimiento.setText(null);
        this.txtEstado.setText(null);
        this.btnModificar.setEnabled(false);
        this.btnEliminar.setEnabled(false);
    }
    
    public void mostrarDatos(String codigo){
        String sql =    "SELECT lc.cod_linea_credito, lc.cod_cliente,CONCAT(cl.nombre_cliente,\" \",cl.apellido_cliente) AS nombre,lc.monto_total,\n" +
                        "lc.saldo_disponible, lc.fecha_asignacion, lc.fecha_vencimiento, lc.estado_linea_credito FROM lineas_credito lc\n" +
                        "JOIN clientes cl ON lc.cod_cliente = cl.cod_cliente\n" +
                        "WHERE lc.cod_linea_credito ='" + codigo + "'";
        String [] datos = bd.obtenerRegistro(sql);
        if(datos != null){
            this.limpiarCampos();
            this.txtCodigo.setText(datos[0]);
            this.txtNomApellido.setText(datos[2]);
            this.txtCredito.setText(datos[3]);
            this.txtCDisponible.setText(datos[4]);
            this.txtFechaAsignacion.setText(fe.formatoFechaBDaJava(datos[5]));
            this.txtVencimiento.setText(fe.formatoFechaBDaJava(datos[6]));
            this.txtEstado.setText(datos[7]);
            this.btnEliminar.setEnabled(true);
            this.btnModificar.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null, "Sin resultados");
        }
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        txtCredito = new javax.swing.JTextField();
        txtNomApellido = new javax.swing.JTextField();
        txtCDisponible = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtFechaAsignacion = new javax.swing.JTextField();
        txtVencimiento = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setBackground(new java.awt.Color(0, 153, 255));
        panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCredito.setEditable(false);
        txtCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCredito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 140, 30));

        txtNomApellido.setEditable(false);
        txtNomApellido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtNomApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 380, 30));

        txtCDisponible.setEditable(false);
        txtCDisponible.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCDisponible.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtCDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 140, 30));

        jLabel1.setFont(new java.awt.Font("Engravers MT", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LINEA DE CREDITO");
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, 38));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 130, 16));

        jLabel2.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Insersion");
        panel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, 130, 16));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        panel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 130, 16));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Saldo Disponible");
        panel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 90, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CI/RUC");
        panel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Vencimiento");
        panel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 80, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Credito");
        panel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 50, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Estado Linea de Credito");
        panel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 140, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Asignacion");
        panel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 70, 30));

        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 340, 30));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 30, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Cliente");
        panel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        panel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 90, 40));

        btnModificar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        panel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 90, 40));

        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 90, 40));

        btnCerrar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        panel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 90, 40));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.setEnabled(false);
        txtCodigo.setFocusable(false);
        panel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 0, -1));

        txtFechaAsignacion.setEditable(false);
        txtFechaAsignacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaAsignacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtFechaAsignacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 140, 30));

        txtVencimiento.setEditable(false);
        txtVencimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVencimiento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 140, 30));

        txtEstado.setEditable(false);
        txtEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.limpiarCampos();
        JDNuevaLineaCredito jN = new JDNuevaLineaCredito(this, true, this, usuarioLogeado);
        jN.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String codigo = this.txtCodigo.getText();
        JDEliminarLineaCredito je = new JDEliminarLineaCredito(this, true, this, usuarioLogeado, codigo);
        je.setVisible(true);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String codigo = this.txtCodigo.getText();
        JDModificarLineaCredito jM = new JDModificarLineaCredito(this, true, this, usuarioLogeado, codigo);
        jM.setVisible(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String valor = this.txtBuscar.getText();
        String sql =    "SELECT lc.cod_linea_credito FROM lineas_credito lc " +
                        "JOIN clientes cl ON lc.cod_cliente = cl.cod_cliente\n" +
                        "WHERE cl.ruc_cliente ='" + valor + "'";
        String [] datos = bd.obtenerRegistro(sql);
        if(datos != null){
            this.mostrarDatos(datos[0]);
        }else{
            JOptionPane.showMessageDialog(null, "Sin resultados");
            this.limpiarCampos();
        }
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
            java.util.logging.Logger.getLogger(FrmLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLineaCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLineaCredito().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JPanel panel1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCDisponible;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCredito;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFechaAsignacion;
    private javax.swing.JTextField txtNomApellido;
    private javax.swing.JTextField txtVencimiento;
    // End of variables declaration//GEN-END:variables
}
