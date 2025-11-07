package ventanas.clientesListo;

import java.awt.GridLayout;
import java.sql.Connection;
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
import servicios.BaseDatos1;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author VictorM
 */
public class FrmClientes extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    Grilla miGrilla1 = new Grilla();
    FrmAjustes aj = new FrmAjustes();
    Connection conn = null;
    String usuarioLogeado = "INDEFINIDO";
    String rolUsuario;
    
    public FrmClientes() {
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.actualizarGrilla();
    }
    public FrmClientes(String usuLogeado, String rol) {
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("MANTENIMIENTO DE CLIENTES - " + usuLogeado);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.usuarioLogeado = usuLogeado;
        this.rolUsuario = rol;
        this.actualizarGrilla();
    }
    
    private void restricciones(){
        aj.soloMayusculas(txtBuscar, 50);
    }
    
    
    
    public void actualizarGrilla(){
        String tabla = "clientes cl";
        String [] campos = {"cod_cliente", "concat(nombre_cliente, ' ', apellido_cliente)", "ruc_cliente", 
            "telefono_cliente", "estado_cliente", "uso_cliente", "usuario_insercion", "fecha_insercion", "usuario_modificacion","fecha_modificacion"};
        miGrilla1.cargarGrilla(grdClientes, tabla, campos, "");
    }
    
    private void mostrarReporteClientes(){
        try {
            conn = bd.conexion;
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteClientes.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe "+ e.getMessage());   
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        scpContenedor = new javax.swing.JScrollPane();
        grdClientes = new javax.swing.JTable();
        btnInforme = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 420, 40));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, 40));

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

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 40));

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CLIENTE", "RUC/CI", "TELEFONO", "ESTADO", "USO", "INSERSION", "FECHA INSERCION", "MODIFICACION", "FECHA MODIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdClientes.setFillsViewportHeight(true);
        grdClientes.setRowMargin(3);
        grdClientes.setShowGrid(true);
        grdClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdClientesMouseClicked(evt);
            }
        });
        scpContenedor.setViewportView(grdClientes);
        if (grdClientes.getColumnModel().getColumnCount() > 0) {
            grdClientes.getColumnModel().getColumn(0).setPreferredWidth(75);
            grdClientes.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdClientes.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdClientes.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdClientes.getColumnModel().getColumn(4).setPreferredWidth(100);
            grdClientes.getColumnModel().getColumn(5).setPreferredWidth(100);
            grdClientes.getColumnModel().getColumn(6).setPreferredWidth(150);
            grdClientes.getColumnModel().getColumn(7).setPreferredWidth(150);
            grdClientes.getColumnModel().getColumn(8).setPreferredWidth(150);
            grdClientes.getColumnModel().getColumn(9).setPreferredWidth(150);
        }

        jPanel3.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 980, 470));

        btnInforme.setBackground(new java.awt.Color(204, 204, 204));
        btnInforme.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel3.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 70, 40));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.txtBuscar.setText(null);
        this.actualizarGrilla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = this.grdClientes.getSelectedRow();
        if (fila > -1) {  
            if(this.grdClientes.getValueAt(fila, 5).toString().equals("INACTIVO")){                
                String dato = this.grdClientes.getValueAt(fila, 0).toString();
                JDEliminarCliente ecli = new JDEliminarCliente(this, true, this, dato, this.usuarioLogeado);
                ecli.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                ecli.setVisible(true);
//                FrmEliminarCliente ecli = new FrmEliminarCliente(this, dato, this.usuarioLogeado);
//                ecli.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//                ecli.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "No se puede eliminar por que esta en uso en otra tabla");
            }
        } else {
            // Mostrar mensaje de advertencia si no se seleccionó ninguna fila
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = this.grdClientes.getSelectedRow();
        if (fila > -1) {
            String dato = this.grdClientes.getValueAt(fila, 0).toString();

            JDModificarCliente mcli = new JDModificarCliente(this, true, this, dato, this.usuarioLogeado);
            mcli.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            mcli.setVisible(true);
//            FrmModificarCliente mcli = new FrmModificarCliente(this, dato, this.usuarioLogeado);
//            mcli.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//            mcli.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        int fila = this.grdClientes.getSelectedRow();
        if(fila > -1){
            if(this.grdClientes.getValueAt(fila, 4).toString().equals("ACTIVO")){
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que deseas desactivar este registro?",
                    "Confirmación desactivacion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del usuario
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("clientes", "estado_cliente='INACTIVO'", "cod_cliente=" + this.grdClientes.getValueAt(fila, 0).toString());
                    this.actualizarGrilla();
                }
            }else{
                bd.modificarRegistro("clientes", "estado_cliente='ACTIVO'", "cod_cliente=" + this.grdClientes.getValueAt(fila, 0).toString());
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        String valor = this.txtBuscar.getText();
        String tabla = "clientes";
        String [] campos = {"cod_cliente", "concat(nombre_cliente, ' ', apellido_cliente)", 
            "ruc_cliente", "telefono_cliente", "estado_cliente", "uso_cliente", "usuario_insercion", "fecha_insercion", "usuario_modificacion","fecha_modificacion"};
        String condicion = "where nombre_cliente LIKE '%" + valor + "%'"
                + " OR apellido_cliente LIKE '%" + valor + "%'"
                + " OR ruc_cliente LIKE '%" + valor + "%'"
                + " OR telefono_cliente LIKE '%" + valor + "%'"
                + " OR estado_cliente LIKE '" + valor + "%'";
        miGrilla1.cargarGrilla(grdClientes, tabla, campos, condicion);
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
//        FrmNuevoCliente nc = new FrmNuevoCliente(this, usuarioLogeado);
//        nc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        nc.setVisible(true);
        JDNuevoCliente nc = new JDNuevoCliente(this, true, this, usuarioLogeado);
        nc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        nc.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void grdClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdClientesMouseClicked
        int fila = this.grdClientes.getSelectedRow();
        if(fila > -1){
            System.out.println(fila);
            if(this.grdClientes.getValueAt(fila, 3).toString().equals("ACTIVO")){
                Icon desactivar = new ImageIcon(getClass().getResource("/iconos/desactivar.png"));
                this.btnEstado.setIcon(desactivar);
            }else{
                Icon activar = new ImageIcon(getClass().getResource("/iconos/activar.png"));
                this.btnEstado.setIcon(activar);
            }
        }
    }//GEN-LAST:event_grdClientesMouseClicked

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
                String url = "/reportes/reporteClientes.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteClientesEstado.jasper";
                String [] nomParamet = {"estado_cliente"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

            }
        }
    }//GEN-LAST:event_btnInformeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new FrmClientes().setVisible(true);
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
    public static javax.swing.JTable grdClientes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
