/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ventanas.UsuariosListo;

import java.util.Arrays;
import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Admision-2
 */
public class JDModificarUsuario extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    String usuarioLogeado;
    private FrmUsuarios frmUsuarios;
    
    public JDModificarUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JDModificarUsuario(java.awt.Frame parent, boolean modal, FrmUsuarios frmUsuarios, String codigo, String usuLogeado) {
        super(parent, modal);
        initComponents();
        this.resticciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmUsuarios = frmUsuarios;
        setTitle("Modificar Usuario - " + usuLogeado );
        this.usuarioLogeado = usuLogeado;
        this.mostrarRegistro(codigo);
        this.txtNombre.requestFocus();
    }
    
    private void resticciones(){
        aj.soloMayusculas(txtNombre, 20);
        aj.soloMayusculas(txtCorreo, 50);
        aj.soloNumeros(txtTelefono, 15);
        aj.longitudMaxima(psContraseña, 50);
        aj.longitudMaxima(psConfirmarContraseña, 50);
    }
    private void mostrarRegistro(String codigo){
        String sql = "select cod_usuario,nom_usuario,tel_usuario,correo_usuario,CAST(AES_DECRYPT(cont_usuario, 'myclave') AS CHAR) AS contrasena_desencriptada,"
                + "rol_usuario,estado_usuario, uso_registro, usuario_insercion, fecha_insercion,usuario_modificacion, fecha_modificacion  from usuarios where cod_usuario =" + codigo + ";";
        String [] datos = Arrays.copyOf(bd.obtenerRegistro(sql), bd.obtenerRegistro(sql).length);
        this.txtCodigo.setText(datos[0]);
        this.txtNombre.setText(datos[1]);
        this.txtTelefono.setText(datos[2]);
        this.txtCorreo.setText(datos[3]);
        this.psContraseña.setText(datos[4]);
        this.psConfirmarContraseña.setText(datos[4]);
        bd.seleccionarItemCombo(cboRol, datos[5]);
        bd.seleccionarItemCombo(cboEstado, datos[6]);
        bd.seleccionarItemCombo(cboUso, datos[7]);
        this.lblUsuarioCreacion.setText(datos[8]);
        this.lblFechaCreacion.setText(datos[9]);
        this.lblUsuarioModificacion.setText(datos[10]);
        this.lblFechaModificacion.setText(datos[11]);
    }
    
    public static boolean verificarContraseña(String contraseña) {
        if (contraseña.length() < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña no puede ser menor a 8 digitos");
            return false;
        }
        
        boolean tieneMinuscula = false;
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        
        // Verificar cada caracter de la contraseña
        for (char c : contraseña.toCharArray()) {
            if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            }
            else if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }
        if(!tieneMinuscula || !tieneMayuscula || !tieneNumero){
            JOptionPane.showMessageDialog(null, "La contraseña debe tener minusculas, mayusculas y numeros");
        }
        
        // Verificar que tenga al menos una minúscula, una mayúscula y un número
        return tieneMinuscula && tieneMayuscula && tieneNumero;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        psContraseña = new javax.swing.JPasswordField();
        psConfirmarContraseña = new javax.swing.JPasswordField();
        lblUsuarioModificacion = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboUso = new javax.swing.JComboBox<>();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 100, 30));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 130, 16));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 130, 16));

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 90, 40));

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 90, 40));

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 210, 30));

        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 160, 30));

        txtCorreo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCorreo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 370, 30));

        psContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        psContraseña.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(psContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 170, 30));

        psConfirmarContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        psConfirmarContraseña.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        psConfirmarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psConfirmarContraseñaActionPerformed(evt);
            }
        });
        jPanel1.add(psConfirmarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 170, 30));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 130, 16));

        cboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "ENCARGADO STOCK", "CAJERO", "SECRETARIO" }));
        cboRol.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(cboRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 170, 30));

        jLabel2.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Rol");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 30, 30));

        jLabel4.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Codigo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        jLabel5.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nombre ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 30));

        jLabel6.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Confirmar");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 70, 30));

        jLabel7.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Telefono");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 50, 30));

        jLabel8.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Correo");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 50, 30));

        jLabel9.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Uso");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 40, 30));

        jLabel10.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Contraseña");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 70, 30));

        cboUso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboUso.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        cboUso.setEnabled(false);
        jPanel1.add(cboUso, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 120, 30));

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboEstado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 120, 30));

        jLabel11.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Estado");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 40, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String codigo = this.txtCodigo.getText();
        String nombre = this.txtNombre.getText();
        String telefono = this.txtTelefono.getText();
        String correo = this.txtCorreo.getText();
        String rol = this.cboRol.getSelectedItem().toString();
        String estado = this.cboEstado.getSelectedItem().toString();
        
        char [] cont = this.psContraseña.getPassword();
        String contraseña = new String(cont);
        char [] contCinfirmar = this.psConfirmarContraseña.getPassword();
        String contraseñaConfirmar = new String(contCinfirmar);

        if(!nombre.isEmpty()){
            if(cont.length != 0){
                if(contCinfirmar.length != 0){
                    if(contraseña.equals(contraseñaConfirmar)){
                        if(verificarContraseña(contraseña)){
                            if(!bd.verificarDuplicidadString("usuarios", "nom_usuario", nombre, "cod_usuario", codigo)){
                                String valores = "nom_usuario='" + nombre + "',"
                                        + "cont_usuario=AES_ENCRYPT('" + contraseña +"','myclave'),"
                                        + "tel_usuario='" + telefono +"',"
                                        + "correo_usuario='" + correo + "',"
                                        + "rol_usuario='" + rol + "',"
                                        + "estado_usuario='" + estado + "',"
                                        + "usuario_modificacion='" + usuarioLogeado + "', "
                                        + "fecha_modificacion=NOW()";
                                bd.modificarRegistro("usuarios", valores, "cod_usuario = " + this.txtCodigo.getText());
                                JOptionPane.showMessageDialog(null, "Usuario Actualizado", "Registro Actualizado", JOptionPane.INFORMATION_MESSAGE);
                                frmUsuarios.actualizarGrilla();
                                this.dispose();
                            }else{
                                JOptionPane.showMessageDialog(null, "El Nombre de usuario ya existe");
                                this.txtNombre.requestFocus();
                            }
                        }
                        System.out.println(contraseña);
                    }else{
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Debes confirmar la contraseña");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese la contraseña");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Ingresa el nombre de usuario");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void psConfirmarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psConfirmarContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_psConfirmarContraseñaActionPerformed

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
            java.util.logging.Logger.getLogger(JDModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDModificarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDModificarUsuario dialog = new JDModificarUsuario(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JComboBox<String> cboUso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JPasswordField psConfirmarContraseña;
    private javax.swing.JPasswordField psContraseña;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
