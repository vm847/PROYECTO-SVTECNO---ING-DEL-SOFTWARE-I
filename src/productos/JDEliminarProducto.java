package productos;

import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDEliminarProducto extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    private FrmProductos frmProductos;
    String usuarioLogeado;
    
    public JDEliminarProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.mostrarRegistro("1");
    }
    public JDEliminarProducto(java.awt.Frame parent, boolean modal, FrmProductos frmProductos, String usuLogeado, String codigo) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmProductos = frmProductos;
        this.setTitle("Eliminar Producto - " + usuLogeado);
        this.mostrarRegistro(codigo);
    }
    
    public void setClasificacion(int codigo, String clasificacion){
        if(!bd.seleccionarItemComboBD(cboClasificacion, codigo)){
            bd.agregarItemCombo(cboClasificacion, codigo, clasificacion);
        }
    }
    public void setMarca(int codigo, String marca){
        if(!bd.seleccionarItemComboBD(cboMarca, codigo)){
            bd.agregarItemCombo(cboMarca, codigo, marca);
        }
    }
   
    private void mostrarRegistro(String codigo){
        String sql = "select "
                + "pr.cod_producto,"
                + "pr.cod_barras,"
                + "pr.nombre_producto,"
                + "pr.img1_producto,"
                + "pr.img2_producto,"
                + "pr.cod_clasificacion,"
                + "cl.nombre_clasificacion,"
                + "pr.cod_marca,"
                + "ma.nombre_marca,"
                + "pr.precio_producto,"
                + "pr.iva_producto,"
                + "pr.estado_producto,"
                + "pr.coment_producto,"
                + "pr.usuario_insercion,"
                + "pr.usuario_modificacion,"
                + "pr.fecha_insercion,"
                + "pr.fecha_modificacion"
                + " from productos pr "
                + "JOIN clasificacion cl ON pr.cod_clasificacion=cl.cod_clasificacion "
                + "JOIN marcas ma ON pr.cod_marca=ma.cod_marca "
                + " where cod_producto =" + codigo + ";";
        String [] datos = bd.obtenerRegistro(sql);
        
        this.txtCodigo.setText(datos[0]);
        this.txtCodBarras.setText(datos[1]);
        this.txtNombre.setText(datos[2]);
        if(datos[3]!=null){
            bd.mostrarImg("productos", "img1_producto", "cod_producto = " + codigo, img1);         
        }
        if(datos[4]!=null){
            bd.mostrarImg("productos", "img2_producto", "cod_producto = " + codigo, img2);
        }
        bd.agregarItemCombo(cboClasificacion, 1, datos[6]);
        bd.agregarItemCombo(cboMarca, 1, datos[8]);
        this.txtPrecio.setText(datos[9]);
        bd.agregarItemCombo(cboIva, 1, datos[10]);
        bd.agregarItemCombo(cboEstado, 1, datos[11]);
        this.txtComentario.setText(datos[12]);
        this.lblUsuarioCreacion.setText(datos[13]);
        this.lblUsuarioModificacion.setText(datos[14]);
        this.lblFechaCreacion.setText(datos[15]);
        this.lblFechaModificacion.setText(datos[16]);
        this.txtNombre.requestFocus();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        cboClasificacion = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        cboMarca = new javax.swing.JComboBox<>();
        cboIva = new javax.swing.JComboBox<>();
        txtPrecio = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnGuardar1 = new javax.swing.JButton();
        img2 = new javax.swing.JLabel();
        img1 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtCodBarras = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 10, 0));

        cboClasificacion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 170, 30));

        txtNombre.setEditable(false);
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 400, 30));

        cboMarca.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 170, 30));

        cboIva.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 170, 30));

        txtPrecio.setEditable(false);
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 170, 30));

        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 90, 40));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 130, 16));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 330, 130, 16));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Clasificacion");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 50, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cod Barras");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Precio");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 50, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Marca");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("IVA");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 40, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Producto");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, 30));

        btnGuardar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 90, 40));

        img2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(img2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 150, 150));

        img1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(img1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 150, 150));

        cboEstado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 170, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Estado");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 40, 30));

        txtCodBarras.setEditable(false);
        txtCodBarras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodBarras.setToolTipText("CODIGO DE BARRAS");
        txtCodBarras.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtCodBarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 170, 30));

        txtComentario.setEditable(false);
        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        txtComentario.setToolTipText("COMENTARIOS");
        jScrollPane2.setViewportView(txtComentario);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 400, 100));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Comentario");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 70, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        String codigo = this.txtCodigo.getText();
        int confirmacion = JOptionPane.showConfirmDialog(null,
            "¿Estás seguro de que deseas eliminar este registro?",
            "Confirmación de Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        // Comprueba la elección del usuario
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Registro eliminado.");
            bd.borrarRegistro("productos", "cod_producto=" + codigo);
            frmProductos.actualizarGrilla();
            this.dispose();
            JOptionPane.showMessageDialog(null, "Producto Eliminado", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Operación cancelada.");
        }
        
    }//GEN-LAST:event_btnGuardar1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDEliminarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDEliminarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDEliminarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDEliminarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDEliminarProducto dialog = new JDEliminarProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JComboBox<String> cboClasificacion;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboIva;
    private javax.swing.JComboBox<String> cboMarca;
    private javax.swing.JLabel img1;
    private javax.swing.JLabel img2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JTextField txtCodBarras;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
