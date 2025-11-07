package ventanas.clientesListo;

import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;

/**
 *
 * @author Admision-2
 */
public class JDEliminarCliente extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    Fechas fe = new Fechas();
    private FrmClientes frmClientes;
    String usuarioLogeado;
    
    public JDEliminarCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JDEliminarCliente(java.awt.Frame parent, boolean modal, FrmClientes frmClientes,String codigo, String usuLogeado) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("Eliminar Cliente - " + usuLogeado);
        this.usuarioLogeado = usuLogeado;
        this.frmClientes = frmClientes;
        this.mostrarRegistro(codigo);
    }
    
    private void mostrarRegistro(String codigo){
        String sql = "select * from clientes where cod_cliente =" + codigo + ";";
        String [] datos = bd.obtenerRegistro(sql);
        
        this.txtCodigoCliente.setText(datos[0]);
        this.txtNombre.setText(datos[1]);
        this.txtApellido.setText(datos[2]);
        this.txtRucCliente.setText(datos[3]);
        bd.seleccionarItemCombo(cboSexoCliente, datos[4]);
        fe.mostrarFechaJDateChooser(dateFechaN, datos[5]);
        this.txtDireccionCliente.setText(datos[6]);
        this.txtTelefonoCliente.setText(datos[7]);
        this.lblUsuarioCreacion.setText(datos[8]);
        this.lblUsuarioModificacion.setText(datos[9]);
        this.lblFechaCreacion.setText(datos[10]);
        this.lblFechaModificacion.setText(datos[11]);
        this.txtNombre.requestFocus();
    }
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtRucCliente = new javax.swing.JTextField();
        cboSexoCliente = new javax.swing.JComboBox<>();
        dateFechaN = new com.toedter.calendar.JDateChooser();
        txtDireccionCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtCodigoCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        cboUso = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        p1.setBackground(new java.awt.Color(0, 153, 255));
        p1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        p1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 130, 16));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        p1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 130, 16));

        btnCancel.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        p1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 90, 40));

        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        p1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 90, 40));

        txtNombre.setEditable(false);
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 350, 30));

        txtApellido.setEditable(false);
        txtApellido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 350, 30));

        txtRucCliente.setEditable(false);
        txtRucCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRucCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtRucCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 160, 30));

        cboSexoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INDEFINIDO", "MASCULINO", "FEMENINO" }));
        cboSexoCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboSexoCliente.setEnabled(false);
        p1.add(cboSexoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 120, 30));

        dateFechaN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateFechaN.setDateFormatString("dd-MM-yyyy");
        dateFechaN.setEnabled(false);
        p1.add(dateFechaN, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 130, 30));

        txtDireccionCliente.setEditable(false);
        txtDireccionCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccionCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtDireccionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 350, 30));

        txtTelefonoCliente.setEditable(false);
        txtTelefonoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefonoCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 350, 30));

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p1.add(txtCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 80, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha de Nacimiento");
        p1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 130, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sexo");
        p1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 30, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Codigo");
        p1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 50, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nombre");
        p1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 50, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Apellido");
        p1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 50, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Telefono");
        p1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 60, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("CI/RUC");
        p1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 50, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Direccion");
        p1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 60, 30));

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboEstado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        cboEstado.setEnabled(false);
        p1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 120, 30));

        cboUso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboUso.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        cboUso.setEnabled(false);
        p1.add(cboUso, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 120, 30));

        jLabel12.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Uso");
        p1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 40, 30));

        jLabel13.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Estado");
        p1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 40, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(null,
            "¿Estás seguro de que deseas eliminar este registro?",
            "Confirmación de Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        // Comprueba la elección del usuario
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Registro eliminado.");
            bd.borrarRegistro("clientes", "cod_cliente=" + this.txtCodigoCliente.getText());
            frmClientes.actualizarGrilla();
            this.dispose();
            JOptionPane.showMessageDialog(null, "Cliente Eliminado", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Operación cancelada.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(JDEliminarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDEliminarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDEliminarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDEliminarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDEliminarCliente dialog = new JDEliminarCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboSexoCliente;
    private javax.swing.JComboBox<String> cboUso;
    private com.toedter.calendar.JDateChooser dateFechaN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel p1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRucCliente;
    private javax.swing.JTextField txtTelefonoCliente;
    // End of variables declaration//GEN-END:variables
}
