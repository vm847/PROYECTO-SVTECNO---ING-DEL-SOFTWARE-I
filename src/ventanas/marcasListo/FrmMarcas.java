package ventanas.marcasListo;

import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import servicios.BaseDatos1;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Admision-2
 */
public class FrmMarcas extends javax.swing.JFrame {
    Grilla miGrilla1 = new Grilla();
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    String usuarioLogeado;
    String rol;
    
    public FrmMarcas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.restricciones();
        this.actualizarGrilla();
    }
    public FrmMarcas(String usuLogeado, String rol) {
        initComponents();
        this.setTitle("MANTENIMIENTO DE MARCAS - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.actualizarGrilla();
    }
    
    private void restricciones(){
        aj.soloMayusculas(txtBuscar, 50);
    }

    public void actualizarGrilla(){
        String tabla = "marcas";
        String [] campos = {"cod_marca", "nombre_marca", "estado_marca", "uso_marca", "usuario_insercion", "fecha_insercion", "usuario_modificacion", "fecha_modificacion"};
        miGrilla1.cargarGrilla(grdMarcas, tabla, campos, "");
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdMarcas = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnCerrar1 = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        btnModificar1 = new javax.swing.JButton();
        btnNuevo1 = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnInforme = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setMinimumSize(new java.awt.Dimension(1000, 510));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 510));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grdMarcas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Marca", "Estado", "Uso", "Insersion", "Fecha Insersion", "Modificacion", "Fecha Modificacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdMarcasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdMarcas);
        if (grdMarcas.getColumnModel().getColumnCount() > 0) {
            grdMarcas.getColumnModel().getColumn(0).setPreferredWidth(50);
            grdMarcas.getColumnModel().getColumn(1).setPreferredWidth(200);
            grdMarcas.getColumnModel().getColumn(2).setPreferredWidth(50);
            grdMarcas.getColumnModel().getColumn(3).setPreferredWidth(50);
            grdMarcas.getColumnModel().getColumn(4).setPreferredWidth(50);
            grdMarcas.getColumnModel().getColumn(5).setPreferredWidth(120);
            grdMarcas.getColumnModel().getColumn(6).setPreferredWidth(50);
            grdMarcas.getColumnModel().getColumn(7).setPreferredWidth(120);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 980, 480));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 420, 40));

        btnCerrar1.setBackground(new java.awt.Color(204, 204, 204));
        btnCerrar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnCerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 70, 40));

        btnEliminar1.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 70, 40));

        btnModificar1.setBackground(new java.awt.Color(204, 204, 204));
        btnModificar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 70, 40));

        btnNuevo1.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnNuevo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 40));

        btnEstado.setBackground(new java.awt.Color(204, 204, 204));
        btnEstado.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/desactivar.png"))); // NOI18N
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });
        jPanel2.add(btnEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 70, 40));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, 40));

        btnInforme.setBackground(new java.awt.Color(204, 204, 204));
        btnInforme.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel2.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 70, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grdMarcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdMarcasMouseClicked
        int fila = this.grdMarcas.getSelectedRow();
        if(fila > -1){
            System.out.println(fila);
            if(this.grdMarcas.getValueAt(fila, 2).toString().equals("ACTIVO")){
                Icon desactivar = new ImageIcon(getClass().getResource("/iconos/desactivar.png"));
                this.btnEstado.setIcon(desactivar);
            }else{
                Icon activar = new ImageIcon(getClass().getResource("/iconos/activar.png"));
                this.btnEstado.setIcon(activar);
            }
        }
    }//GEN-LAST:event_grdMarcasMouseClicked

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        int fila = this.grdMarcas.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdMarcas.getValueAt(fila, 0).toString();
            JDEliminarMarca em = new JDEliminarMarca(this, true, this, codigo, this.usuarioLogeado);
            em.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            em.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        int fila = this.grdMarcas.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdMarcas.getValueAt(fila, 0).toString();
//            FrmModificarMarca mMarca = new FrmModificarMarca(this, codigo, this.usuarioLogeado);
            JDModificarMarca mMarca = new JDModificarMarca(this, true, this, codigo, this.usuarioLogeado);
            mMarca.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            mMarca.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para modificar");
        }
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
//        FrmNuevaMarca nmarca = new FrmNuevaMarca(this, usuarioLogeado);
        JDNuevaMarca nmarca = new JDNuevaMarca(this, true, this, usuarioLogeado);
        nmarca.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        nmarca.setVisible(true);
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        int fila = this.grdMarcas.getSelectedRow();
        if(fila > -1){
            if(this.grdMarcas.getValueAt(fila, 2).toString().equals("ACTIVO")){
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que deseas desactivar este registro?",
                    "Confirmación desactivacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del usuario
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("marcas", "estado_marca='INACTIVO'", "cod_marca=" + this.grdMarcas.getValueAt(fila, 0).toString());
                    this.actualizarGrilla();
                }
            }else{
                bd.modificarRegistro("marcas", "estado_marca='ACTIVO'", "cod_marca=" + this.grdMarcas.getValueAt(fila, 0).toString());
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        if(!this.txtBuscar.getText().isEmpty()){
            String tabla = "marcas";
            String [] campos = {"cod_marca", "nombre_marca", "estado_marca", "uso_marca", "usuario_insercion", "fecha_insercion", "usuario_modificacion", "fecha_modificacion"};
            miGrilla1.cargarGrilla(grdMarcas, tabla, campos, " WHERE nombre_marca LIKE '%"+ this.txtBuscar.getText() + "%'" +
                " OR estado_marca LIKE '"+ this.txtBuscar.getText() + "%';");
        }else{
            this.actualizarGrilla();
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.txtBuscar.setText(null);
        this.actualizarGrilla();
    }//GEN-LAST:event_btnActualizarActionPerformed

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
                String url = "/reportes/reporteMarcas.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteMarcasEstado.jasper";
                String [] nomParamet = {"estado_marca"};
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
            java.util.logging.Logger.getLogger(FrmMarcas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMarcas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMarcas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMarcas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMarcas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEstado;
    private javax.swing.JButton btnInforme;
    private javax.swing.JButton btnModificar1;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JTable grdMarcas;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
