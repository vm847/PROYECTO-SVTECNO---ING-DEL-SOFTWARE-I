
package ventanas.UsuariosListo;
import java.awt.GridLayout;
import java.sql.Connection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import servicios.Grilla;
import servicios.BaseDatos1;


/**
 *
 * @author VictorM
 */
public class FrmUsuarios extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    Grilla miGrilla1 = new Grilla();
    String usuarioLogeado;
    String rol;
    Connection conn = null;
    
    public FrmUsuarios() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("Mantenimiento de Usuario - ");
        this.usuarioLogeado = "indefinido";
        this.restricciones();
        this.actualizarGrilla();
    }
    
    public FrmUsuarios(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("MANTENIMIENTO DE USUARIOS - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.restricciones();
        this.actualizarGrilla();
    }
    private void restricciones(){
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
            }
        });
    }
    public void actualizarGrilla(){
        String tabla = "usuarios";
        String [] campos = {"cod_usuario", "nom_usuario", "rol_usuario", "estado_usuario", "uso_registro", "usuario_insercion", "fecha_insercion", "usuario_modificacion", "fecha_modificacion"};
        miGrilla1.cargarGrilla(grdUsuario, tabla, campos, "");
    }
    
    
    private void llamarReporte(String url, String [] nomParamet, String [] paramet){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null; 
            
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(url));
            Map<String,Object> parametros = new java.util.HashMap<>();
            
            for (int x = 0; x < nomParamet.length; x++) {
                parametros.put(nomParamet[x], paramet[x]);
            }
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        scpContenedor = new javax.swing.JScrollPane();
        grdUsuario = new javax.swing.JTable();
        btnInforme = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 500));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 420, 40));

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 40));

        btnModificar.setBackground(new java.awt.Color(204, 204, 204));
        btnModificar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel3.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 70, 40));

        btnEliminar.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 70, 40));

        btnCerrar.setBackground(new java.awt.Color(204, 204, 204));
        btnCerrar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 70, 40));

        btnEstado.setBackground(new java.awt.Color(204, 204, 204));
        btnEstado.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/desactivar.png"))); // NOI18N
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });
        jPanel3.add(btnEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 70, 40));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, 40));

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdUsuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "NOMBRE DE USUARIO", "ROL", "ESTADO", "USO", "INSERSION", "FECHA INSERCION", "MODIFICACION", "FECHA MODIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdUsuario.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdUsuario.setFillsViewportHeight(true);
        grdUsuario.setRowMargin(3);
        grdUsuario.setShowGrid(true);
        grdUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdUsuarioMouseClicked(evt);
            }
        });
        scpContenedor.setViewportView(grdUsuario);
        if (grdUsuario.getColumnModel().getColumnCount() > 0) {
            grdUsuario.getColumnModel().getColumn(0).setPreferredWidth(100);
            grdUsuario.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdUsuario.getColumnModel().getColumn(2).setPreferredWidth(100);
            grdUsuario.getColumnModel().getColumn(3).setPreferredWidth(100);
            grdUsuario.getColumnModel().getColumn(4).setPreferredWidth(100);
            grdUsuario.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdUsuario.getColumnModel().getColumn(6).setPreferredWidth(150);
            grdUsuario.getColumnModel().getColumn(7).setPreferredWidth(150);
            grdUsuario.getColumnModel().getColumn(8).setPreferredWidth(150);
        }

        jPanel3.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 980, 490));

        btnInforme.setBackground(new java.awt.Color(204, 204, 204));
        btnInforme.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel3.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 70, 40));

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        JDNuevoUsuario nUsu = new JDNuevoUsuario(this, true, this,usuarioLogeado);
        nUsu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        nUsu.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = this.grdUsuario.getSelectedRow();
        if(fila > -1){
            if(this.grdUsuario.getValueAt(fila, 4).toString().equals("INACTIVO")){
                String codigo = this.grdUsuario.getValueAt(fila, 0).toString();
//                FrmEliminarUsuario eUsu = new FrmEliminarUsuario(this, codigo, usuarioLogeado);
                JDEliminarUsuario eUsu = new JDEliminarUsuario(this, true, this, codigo, usuarioLogeado);
                eUsu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                eUsu.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "No se puede eliminar el usuario por que ya se encuentra en uso");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Seleccione un registro para Eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = this.grdUsuario.getSelectedRow();
        if(fila > -1){
            String codigo = this.grdUsuario.getValueAt(fila, 0).toString();
            JDModificarUsuario mUsu = new JDModificarUsuario(this, true, this, codigo, usuarioLogeado);
//            FrmModificarUsuario mUsu = new FrmModificarUsuario(this, codigo, usuarioLogeado);
            mUsu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            mUsu.setVisible(true);            
        }else{
            JOptionPane.showMessageDialog(null,"Seleccione un registro para modificar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        if(!this.txtBuscar.getText().isEmpty()){
            String tabla = "usuarios us";
            String [] campos = {"cod_usuario", "nom_usuario","rol_usuario", "estado_usuario", "uso_registro", "usuario_insercion", "fecha_insercion"};
            miGrilla1.cargarGrilla(grdUsuario, tabla, campos, " WHERE cod_usuario LIKE '%"+ this.txtBuscar.getText() + "%'" +
                " OR nom_usuario LIKE '%"+ this.txtBuscar.getText() + "%'\n" +
                " OR tel_usuario LIKE '%"+ this.txtBuscar.getText() + "%'\n" +
                " OR correo_usuario LIKE '%"+ this.txtBuscar.getText() + "%'\n" +
                " OR rol_usuario LIKE '%"+ this.txtBuscar.getText() + "%'\n" +
                " OR estado_usuario LIKE '"+ this.txtBuscar.getText() + "%';"
            );
        }else{
            this.actualizarGrilla();
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        int fila = this.grdUsuario.getSelectedRow();
        if(fila > -1){
            if(this.grdUsuario.getValueAt(fila, 3).toString().equals("ACTIVO")){
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas desactivar este registro?", 
                "Confirmación desactivacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del usuario
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("usuarios", "estado_usuario='INACTIVO'", "cod_usuario=" + this.grdUsuario.getValueAt(fila, 0).toString());
                    this.actualizarGrilla();
                }
            }else{
                bd.modificarRegistro("usuarios", "estado_usuario='ACTIVO'", "cod_usuario=" + this.grdUsuario.getValueAt(fila, 0).toString());
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.txtBuscar.setText(null);
        this.actualizarGrilla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void grdUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdUsuarioMouseClicked
        int fila = this.grdUsuario.getSelectedRow();
        if(fila > -1){
            System.out.println(fila);
            if(this.grdUsuario.getValueAt(fila, 3).toString().equals("ACTIVO")){
                Icon desactivar = new ImageIcon(getClass().getResource("/iconos/desactivar.png"));
                this.btnEstado.setIcon(desactivar);
            }else{
                Icon activar = new ImageIcon(getClass().getResource("/iconos/activar.png"));
                this.btnEstado.setIcon(activar);
            }
        }
    }//GEN-LAST:event_grdUsuarioMouseClicked

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        JComboBox cboRol = new JComboBox();
        String [][] datos = bd.obtenerRegistros("select rol_usuario from usuarios group by rol_usuario");
        if(datos != null){
            bd.agregarItemCombo(cboRol, 0, "TODOS");
            for (int x = 0; x < datos.length; x++) {
                bd.agregarItemCombo(cboRol, x+1, datos[x][0]);
            }
        }
        cboRol.setSelectedIndex(0);
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("ROL: "));
        panel.add(cboRol);
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ROL", JOptionPane.DEFAULT_OPTION);
        
        if(imput == JOptionPane.OK_OPTION){
            String rol = cboRol.getSelectedItem().toString();
            String estado = cboEstado.getSelectedItem().toString();
            System.out.println(estado + " - " + rol);
            
            
            if(estado.equals("TODOS") && rol.equals("TODOS")){
                String [] nomParamet = {};
                String [] paramet = {};
                System.out.println("/reportes/reporteUsuarios.jasper");
                this.llamarReporte("/reportes/reporteUsuarios.jasper", nomParamet, paramet);
            }else if(estado.equals("TODOS") && !rol.equals("TODOS")){
                String [] nomParamet = {"rol_usuario"};
                String [] paramet = {rol};
                System.out.println("/reportes/reporteUsuarioPorRol.jasper");
                this.llamarReporte("/reportes/reporteUsuarioPorRol.jasper", nomParamet, paramet);                    
            }else if(!estado.equals("TODOS") && rol.equals("TODOS")){
                String [] nomParamet = {"estado_usuario"};
                String [] paramet = {estado};
                System.out.println("/reportes/reporteUsuariosEstado.jasper");
                this.llamarReporte("/reportes/reporteUsuariosEstado.jasper", nomParamet, paramet); 
            }else if(!estado.equals("TODOS") && !rol.equals("TODOS")){
                String [] nomParamet = {"estado_usuario", "rol_usuario"};
                String [] paramet = {estado,rol};
                System.out.println("/reportes/reporteUsuariosRolEstado.jasper");
                this.llamarReporte("/reportes/reporteUsuariosRolEstado.jasper", nomParamet, paramet); 
            }
        }
    }//GEN-LAST:event_btnInformeActionPerformed

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
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEstado;
    private javax.swing.JButton btnInforme;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    public static javax.swing.JTable grdUsuario;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
