package ventanas.clasificacionesListo;

import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import servicios.BaseDatos1;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author VictorM
 */
public class FrmClasificaciones extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Grilla miGrilla1 = new Grilla();
    String usuarioLogeado;
    String rol;
    
    public FrmClasificaciones() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Mantenimiento de Clasificaciones - ");
        this.actualizarGrilla();
    }
    
    public FrmClasificaciones(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.setTitle("MANTENIMIENTO DE CLASIFICACIONES - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.actualizarGrilla();
    }
    private void restricciones(){
        aj.soloMayusculas(txtBuscar, 50);
    }
    public void actualizarGrilla(){
        String tabla = "clasificacion";
        String [] campos = {"cod_clasificacion", "nombre_clasificacion", "estado_clasificacion", "usuario_insercion", "fecha_insercion", "usuario_modificacion", "fecha_modificacion"};
        miGrilla1.cargarGrilla(grdClasificaciones, tabla, campos, "");
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnEstado = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        scpContenedor2 = new javax.swing.JScrollPane();
        grdClasificaciones = new javax.swing.JTable();
        btnInforme = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 40));

        btnModificar.setBackground(new java.awt.Color(204, 204, 204));
        btnModificar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 70, 40));

        btnCerrar.setBackground(new java.awt.Color(204, 204, 204));
        btnCerrar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 70, 40));

        btnEliminar.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 70, 40));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 420, 39));

        btnEstado.setBackground(new java.awt.Color(204, 204, 204));
        btnEstado.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/desactivar.png"))); // NOI18N
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 70, 40));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, 40));

        scpContenedor2.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor2.setAutoscrolls(true);

        grdClasificaciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdClasificaciones.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdClasificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CLASIFICACIONES", "ESTADO", "INSERSION", "FECHA INSERCION", "MODIFICACION", "FECHA MODIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdClasificaciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdClasificaciones.setFillsViewportHeight(true);
        grdClasificaciones.setRowMargin(3);
        grdClasificaciones.setShowGrid(true);
        grdClasificaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdClasificacionesMouseClicked(evt);
            }
        });
        scpContenedor2.setViewportView(grdClasificaciones);
        if (grdClasificaciones.getColumnModel().getColumnCount() > 0) {
            grdClasificaciones.getColumnModel().getColumn(0).setPreferredWidth(75);
            grdClasificaciones.getColumnModel().getColumn(1).setPreferredWidth(200);
            grdClasificaciones.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdClasificaciones.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdClasificaciones.getColumnModel().getColumn(4).setPreferredWidth(150);
            grdClasificaciones.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdClasificaciones.getColumnModel().getColumn(6).setPreferredWidth(150);
        }

        jPanel1.add(scpContenedor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 980, 460));

        btnInforme.setBackground(new java.awt.Color(204, 204, 204));
        btnInforme.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel1.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 70, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        JDNuevaClasificacion jN = new JDNuevaClasificacion(this, true, this, this.usuarioLogeado);
        jN.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = this.grdClasificaciones.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdClasificaciones.getValueAt(fila, 0).toString();
            JDEliminarClasificacion jE = new JDEliminarClasificacion(this, true, this, usuarioLogeado, codigo);
            jE.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = this.grdClasificaciones.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdClasificaciones.getValueAt(fila, 0).toString();
            JDModificarClasificacion jM = new JDModificarClasificacion(this, true, this, this.usuarioLogeado, codigo);
            jM.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        int fila = this.grdClasificaciones.getSelectedRow();
        if(fila > -1){
            String codigo = this.grdClasificaciones.getValueAt(fila, 0).toString();
            if(this.grdClasificaciones.getValueAt(fila, 2).toString().equals("ACTIVO")){
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que deseas desactivar este registro?",
                    "Confirmación desactivacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del producto
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("clasificacion", "estado_clasificacion='INACTIVO'", "cod_clasificacion=" + codigo);
                    this.actualizarGrilla();
                }
            }else{
                bd.modificarRegistro("clasificacion", "estado_clasificacion='ACTIVO'", "cod_clasificacion=" + codigo);
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.txtBuscar.setText(null);
        this.actualizarGrilla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        miGrilla1.filtrarGrilla(grdClasificaciones, this.txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void grdClasificacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdClasificacionesMouseClicked
        int fila = this.grdClasificaciones.getSelectedRow();
        if(fila > -1){
            System.out.println(fila);
            if(this.grdClasificaciones.getValueAt(fila, 2).toString().equals("ACTIVO")){
                Icon desactivar = new ImageIcon(getClass().getResource("/iconos/desactivar.png"));
                this.btnEstado.setIcon(desactivar);
            }else{
                Icon activar = new ImageIcon(getClass().getResource("/iconos/activar.png"));
                this.btnEstado.setIcon(activar);
            }
        }
    }//GEN-LAST:event_grdClasificacionesMouseClicked

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));;
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();

            if(estado.equals("TODOS")){
                String url = "/reportes/reporteClasificaciones.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteClasificacionesEstado.jasper";
                String [] nomParamet = {"estado_clasificacion"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

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
            java.util.logging.Logger.getLogger(FrmClasificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClasificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClasificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClasificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmClasificaciones().setVisible(true);
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
    public static javax.swing.JTable grdClasificaciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scpContenedor2;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
