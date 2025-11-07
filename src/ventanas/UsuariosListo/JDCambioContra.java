/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.UsuariosListo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import ventanas.principal.FrmVentanaPrincipal;

/**
 *
 * @author user
 */
public class JDCambioContra extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    private FrmVentanaPrincipal frmVentanaPrincipal;
    String codUsuario;
    String usuarioLogeado;
    String rol;
    
    
    public JDCambioContra(java.awt.Frame parent, boolean modal, String usuLogeado, String rol) {
        super(parent, modal);
        initComponents();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = this.getSize();
        this.setLocation(pantalla.width/2-ventana.width/2,pantalla.height/2-ventana.height/2);
        this.codUsuario = bd.obtenerRegistro("select cod_usuario from usuarios where nom_usuario='" + usuLogeado + "'")[0];
        this.frmVentanaPrincipal = frmVentanaPrincipal;
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.lblUsuario.setText(usuLogeado);
        this.habilitarGuardar();
    }
    
    
    public JDCambioContra(java.awt.Frame parent, boolean modal, FrmVentanaPrincipal frmVentanaPrincipal, String usuLogeado, String rol) {
        super(parent, modal);
        initComponents();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = this.getSize();
        this.setLocation(pantalla.width/2-ventana.width/2,pantalla.height/2-ventana.height/2);
        this.codUsuario = bd.obtenerRegistro("select cod_usuario from usuarios where nom_usuario='" + usuLogeado + "'")[0];
        this.frmVentanaPrincipal = frmVentanaPrincipal;
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.lblUsuario.setText(usuLogeado);
        this.habilitarGuardar();
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
    
    private boolean verificarContraseñaAnterior(){
        String sql = "Select nom_usuario, CAST(AES_DECRYPT(cont_usuario, 'myclave') AS CHAR) AS contrasena_desencriptada, rol_usuario from usuarios "
                        + "where nom_usuario = '"+ this.usuarioLogeado + "'";
        if(bd.obtenerRegistro(sql) != null){
            char [] cont = this.txtContraAnterior.getPassword();
            String contraseña = new String(cont);
            String [] datos = bd.obtenerRegistro(sql);
            System.out.println("contr anteriro = " + datos[1]);
            System.out.println("contr  = " + contraseña);
            
            if(contraseña.equals(datos[1])){
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtContra = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtRepetir = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtContraAnterior = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        cmdCerrar = new javax.swing.JButton();
        cmdGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 152, 153));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Seguridad del sistema");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Complete los datos para cambiar sus datos de usuario");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("Usuario Actual");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContraKeyReleased(evt);
            }
        });
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 250, 30));

        jLabel4.setText("Clave");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 30));

        jLabel5.setText("Repetir Clave");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 80, 30));

        txtRepetir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRepetirKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRepetirKeyReleased(evt);
            }
        });
        jPanel1.add(txtRepetir, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 250, 30));

        jLabel6.setText("Clave Anterior");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        txtContraAnterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraAnteriorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContraAnteriorKeyReleased(evt);
            }
        });
        jPanel1.add(txtContraAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 250, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 96, 354, 130));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        cmdCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        cmdCerrar.setText("Cerrar");
        cmdCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCerrarActionPerformed(evt);
            }
        });

        cmdGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        cmdGuardar.setText("Guardar cambios");
        cmdGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmdCerrar, cmdGuardar});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCerrar)
                    .addComponent(cmdGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 230, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGuardarActionPerformed

        char [] contAnt = this.txtContraAnterior.getPassword();
        String contraseñaAnterior = new String(contAnt);
        char [] cont = this.txtContra.getPassword();
        String contraseña = new String(cont);
        char [] contCinfirmar = this.txtRepetir.getPassword();
        String contraseñaConfirmar = new String(contCinfirmar);
        
        if(this.verificarContraseñaAnterior()){
            if(verificarContraseña(contraseña)){
                String valores = "cont_usuario=AES_ENCRYPT('" + contraseña +"','myclave')";

                if(bd.modificarRegistro("usuarios", valores, "cod_usuario=" + codUsuario)){
                    if(frmVentanaPrincipal != null){
                        this.dispose();
                        frmVentanaPrincipal.setVisible(true);
                    }
                    JOptionPane.showMessageDialog(null, "Registro actualizado","Seguridad del Sistema",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al intentar guardar cambios","Seguridad del Sistema",JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "LA CONTRASEÑA ANTERIOR ES INCORRECTA","CAMBIO DE CONTRASEÑA",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_cmdGuardarActionPerformed

    private void txtContraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraKeyReleased
        this.habilitarGuardar();
    }//GEN-LAST:event_txtContraKeyReleased

    private void txtRepetirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepetirKeyReleased
        this.habilitarGuardar();
    }//GEN-LAST:event_txtRepetirKeyReleased

    private void cmdCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_cmdCerrarActionPerformed

    private void txtContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            this.txtRepetir.requestFocus();
        }
    }//GEN-LAST:event_txtContraKeyPressed

    private void txtRepetirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepetirKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            this.cmdGuardar.requestFocus();
        }
    }//GEN-LAST:event_txtRepetirKeyPressed

    private void txtContraAnteriorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraAnteriorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraAnteriorKeyPressed

    private void txtContraAnteriorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraAnteriorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraAnteriorKeyReleased


    private void habilitarGuardar(){
        String contra=String.valueOf(this.txtContra.getPassword());
        String repetir=String.valueOf(this.txtRepetir.getPassword());
        if(contra.equals(repetir) && !contra.isEmpty()){
            this.cmdGuardar.setEnabled(true);
        }else{
            this.cmdGuardar.setEnabled(false);
        }
    }
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDCambioContra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDCambioContra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDCambioContra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDCambioContra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDCambioContra dialog = new JDCambioContra(new javax.swing.JFrame(), true, "VLOPEZ","ADMIN");
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
    private javax.swing.JButton cmdCerrar;
    private javax.swing.JButton cmdGuardar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JPasswordField txtContraAnterior;
    private javax.swing.JPasswordField txtRepetir;
    // End of variables declaration//GEN-END:variables
}
