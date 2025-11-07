package productos;

import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import servicios.BaseDatos1;
import servicios.Fechas;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author VictorM
 */
public class FrmProductos extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    Fechas fe = new Fechas();
    FrmAjustes aj = new FrmAjustes();
    Grilla miGrilla1 = new Grilla();
    String usuarioLogeado;
    String rol;
    
    public FrmProductos() {
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Mantenimiento de Productos - ");
        this.actualizarGrilla();
    }
    
    
    public FrmProductos(String usuLogeado, String rol) {
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.setTitle("MANTENIMIENTO DE PRODUCTOS - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.actualizarGrilla();
    }
    
    public void actualizarGrilla(){
        String tabla = "productos pr";
        String [] campos = {"pr.cod_barras", 
            "pr.nombre_producto", 
            "cl.nombre_clasificacion", 
            "ma.nombre_marca", 
            "pr.precio_producto", 
            "pr.existencia_producto", 
            "pr.iva_producto", 
            "pr.estado_producto", 
            "pr.usuario_insercion", 
            "pr.fecha_insercion", 
            "pr.usuario_modificacion", 
            "pr.fecha_modificacion"};
        String condicion = " JOIN marcas ma ON pr.cod_marca = ma.cod_marca "
                + "JOIN clasificacion cl ON pr.cod_clasificacion = cl.cod_clasificacion order by cod_producto";
        miGrilla1.cargarGrilla(grdProductos, tabla, campos, condicion);
    }
    
    private void restricciones(){
        aj.soloMayusculas(txtBuscar, 50);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        scpContenedor = new javax.swing.JScrollPane();
        grdProductos = new javax.swing.JTable();
        btnInforme = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 700, 40));

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 70, 40));

        btnModificar.setBackground(new java.awt.Color(204, 204, 204));
        btnModificar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 70, 40));

        btnEliminar.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 70, 40));

        btnCerrar.setBackground(new java.awt.Color(204, 204, 204));
        btnCerrar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 70, 40));

        btnEstado.setBackground(new java.awt.Color(204, 204, 204));
        btnEstado.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/desactivar.png"))); // NOI18N
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 70, 40));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 70, 40));

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdProductos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdProductos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "NOMBRE DEL PRODUCTO", "CLASIFICACION", "MARCA", "PRECIO", "EXISTENCIA", "IVA", "ESTADO", "INSERSION", "FECHA INSERCION", "MODIFICACION", "FECHA MODIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdProductos.setFillsViewportHeight(true);
        grdProductos.setRowMargin(3);
        grdProductos.setShowGrid(true);
        grdProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdProductosMouseClicked(evt);
            }
        });
        scpContenedor.setViewportView(grdProductos);
        if (grdProductos.getColumnModel().getColumnCount() > 0) {
            grdProductos.getColumnModel().getColumn(0).setPreferredWidth(100);
            grdProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
            grdProductos.getColumnModel().getColumn(3).setPreferredWidth(200);
            grdProductos.getColumnModel().getColumn(4).setPreferredWidth(100);
            grdProductos.getColumnModel().getColumn(5).setPreferredWidth(100);
            grdProductos.getColumnModel().getColumn(6).setPreferredWidth(100);
            grdProductos.getColumnModel().getColumn(7).setPreferredWidth(100);
            grdProductos.getColumnModel().getColumn(8).setPreferredWidth(150);
            grdProductos.getColumnModel().getColumn(9).setPreferredWidth(150);
            grdProductos.getColumnModel().getColumn(10).setPreferredWidth(150);
            grdProductos.getColumnModel().getColumn(11).setPreferredWidth(150);
        }

        jPanel1.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1270, 460));

        btnInforme.setBackground(new java.awt.Color(204, 204, 204));
        btnInforme.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel1.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 20, 70, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        JDNuevoProducto jdN = new JDNuevoProducto(this, true, this, usuarioLogeado);
        jdN.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = this.grdProductos.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdProductos.getValueAt(fila, 0).toString();
            JDEliminarProducto jE = new JDEliminarProducto(this, true, this, usuarioLogeado, codigo);
            jE.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = this.grdProductos.getSelectedRow();
        if (fila > -1) {
            String codigo = this.grdProductos.getValueAt(fila, 0).toString();
            JDModificarProducto jM = new JDModificarProducto(this, true, this, usuarioLogeado, codigo);
            jM.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        int fila = this.grdProductos.getSelectedRow();
        if(fila > -1){
            if(this.grdProductos.getValueAt(fila, 7).toString().equals("ACTIVO")){
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que deseas desactivar este registro?",
                    "Confirmación desactivacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del producto
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("productos", "estado_producto='INACTIVO'", "cod_producto=" + this.grdProductos.getValueAt(fila, 0).toString());
                    this.actualizarGrilla();
                }
            }else{
                bd.modificarRegistro("productos", "estado_producto='ACTIVO'", "cod_producto=" + this.grdProductos.getValueAt(fila, 0).toString());
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.txtBuscar.setText(null);
        this.actualizarGrilla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        String valor = this.txtBuscar.getText();
        miGrilla1.filtrarGrilla(grdProductos, valor);
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void grdProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdProductosMouseClicked
        int fila = this.grdProductos.getSelectedRow();
        if(fila > -1){
            System.out.println(fila);
            if(this.grdProductos.getValueAt(fila, 7).toString().equals("ACTIVO")){
                Icon desactivar = new ImageIcon(getClass().getResource("/iconos/desactivar.png"));
                this.btnEstado.setIcon(desactivar);
            }else{
                Icon activar = new ImageIcon(getClass().getResource("/iconos/activar.png"));
                this.btnEstado.setIcon(activar);
            }
        }
    }//GEN-LAST:event_grdProductosMouseClicked

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        JComboBox cboMarca = new JComboBox();
        String [][] marcas = bd.obtenerRegistros(
                "select p.cod_marca, m.nombre_marca from productos p\n" +
                "JOIN marcas m ON p.cod_marca=m.cod_marca\n" +
                "GROUP BY m.nombre_marca"
        );
        if(marcas != null){
            bd.agregarItemCombo(cboMarca, 0, "TODOS");
            for (int x = 0; x < marcas.length; x++) {
                bd.agregarItemCombo(cboMarca, Integer.parseInt(marcas[x][0]), marcas[x][1]);
            }
        }
        cboMarca.setSelectedIndex(0);
        
        JComboBox cboClasificaciones = new JComboBox();
        String [][] clasi = bd.obtenerRegistros(
                "select p.cod_clasificacion, c.nombre_clasificacion from productos p\n" +
                "JOIN clasificacion c ON p.cod_clasificacion=c.cod_clasificacion\n" +
                "GROUP BY c.nombre_clasificacion"
        );
        if(clasi != null){
            bd.agregarItemCombo(cboClasificaciones, 0, "TODOS");
            for (int x = 0; x < clasi.length; x++) {
                bd.agregarItemCombo(cboClasificaciones, Integer.parseInt(clasi[x][0]), clasi[x][1]);
            }
        }
        cboClasificaciones.setSelectedIndex(0);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.add(new JLabel("ESTADO: "));
        panel.add(cboEstado);
        panel.add(new JLabel("MARCA:"));
        panel.add(cboMarca);
        panel.add(new JLabel("CLASIFICACION:"));
        panel.add(cboClasificaciones);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL EL ESTADO, LA MARCA Y LA CLASIFICACION", JOptionPane.DEFAULT_OPTION);
        
        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();
            String marca = cboMarca.getSelectedItem().toString();
            String clasifi = cboClasificaciones.getSelectedItem().toString();
            System.out.println(estado + " - " + marca+ " - " + clasifi);
            
            
            if(estado.equals("TODOS") && marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductos.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);        
            }else if(!estado.equals("TODOS") && marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosEstado.jasper";
                String [] nomParamet = {"estado_producto"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);                        
            }else if(estado.equals("TODOS") && !marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosMarca.jasper";
                String [] nomParamet = {"marca_producto"};
                String [] paramet = {marca};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(estado.equals("TODOS") && marca.equals("TODOS") && !clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosClasificacion.jasper";
                String [] nomParamet = {"clasificacion_producto"};
                String [] paramet = {clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && !marca.equals("TODOS") && clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosEstadoMarca.jasper";
                String [] nomParamet = {"estado_producto", "marca_producto"};
                String [] paramet = {estado, marca};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && marca.equals("TODOS") && !clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosEstadoClasificacion.jasper";
                String [] nomParamet = {"estado_producto", "clasificacion_producto"};
                String [] paramet = {estado, clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(estado.equals("TODOS") && !marca.equals("TODOS") && !clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosMarcaClasificacion.jasper";
                String [] nomParamet = {"marca_producto", "clasificacion_producto"};
                String [] paramet = {marca, clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && !marca.equals("TODOS") && !clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosEstadoMarcaClasificacion.jasper";
                String [] nomParamet = {"estado_producto","marca_producto", "clasificacion_producto"};
                String [] paramet = {estado, marca, clasifi};
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
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmProductos("VLOPEZ", "ADMIN").setVisible(true);
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
    public static javax.swing.JTable grdProductos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
