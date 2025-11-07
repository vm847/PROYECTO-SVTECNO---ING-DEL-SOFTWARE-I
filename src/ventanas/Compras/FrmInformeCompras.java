package ventanas.Compras;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import servicios.BaseDatos1;
import servicios.Fechas;
import servicios.Grilla;

/**
 *
 * @author Asus
 */
public class FrmInformeCompras extends javax.swing.JFrame {
    Grilla gr = new Grilla();
    Fechas fe = new Fechas();
    BaseDatos1 bd = new BaseDatos1();
    Connection conn = null;
    String date1,date2;
    String estado = "ACTIVO";
    String obs = "";
    
    public FrmInformeCompras(String estado) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.obs = estado;
        if(estado.equals("ACTIVO")){
            this.setTitle("COMPRAS ACTIVAS");
        }else{
            this.setTitle("COMPRAS INACTIVAS");
        }
        this.dateDesde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(dateDesde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.dateHasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(dateHasta, fe.obtenerFechaActualFormatoNormal());
        this.estado = estado;
        this.actualizarGrilla();
    }
    
    //DEUDAS PROVEEDORES
    public FrmInformeCompras(String estado, String titulo) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.setTitle(titulo);
        this.obs = titulo;
        this.btnImprimiResumen.setVisible(false);
        this.btnImprimirDetalle.setVisible(false);
        this.dateDesde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(dateDesde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.dateHasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(dateHasta, fe.obtenerFechaActualFormatoNormal());
        this.estado = estado;
        this.actualizarGrillaDeudas();
    }
    
    
    public FrmInformeCompras(String [] cabeceras, int [] tamaños, String tabla, String [] campos, String condicion, String obs) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        gr.configurarModelo(grdCompras, cabeceras, tamaños);
        gr.cargarGrilla(grdCompras, tabla, campos, condicion);
        if(obs.equals("creditos proveedor")){
            this.setTitle("INFORME DE CREDITOS PROVEEDOR");
            this.lblDesde.setVisible(false);
            this.lblHasta.setVisible(false);
            this.dateDesde.setVisible(false);
            this.dateHasta.setVisible(false);
            this.btnActualizar.setVisible(false);
            this.btnImprimiResumen.setVisible(false);
            this.btnImprimirDetalle.setVisible(false);
        }
    }
    
    
    public void actualizarGrilla(){
        String tabla = "compras_cabecera cc ";
        String [] campos = {"cc.cod_compra_cabecera", 
            "cc.nom_proveedor", 
            "cc.tipo_movimiento", 
            "cc.fecha_comprobante", 
            "COALESCE(cc.num_comprobante_compra,'')",
            "COALESCE(\n" +
                "	(\n" +
                "	SELECT SUM(cd.cant_producto*cd.prec_producto) \n" +
                "	FROM compras_detalle cd \n" +
                "	WHERE cd.cod_compra_cabecera=cc.cod_compra_cabecera\n" +
                "	)\n" +
                ",\n" +
                "	(\n" +
                "		SELECT monto_cuota FROM cuotas_credito_proveedor WHERE num_movimiento= cc.cod_compra_cabecera\n" +
                "	)\n" +
                ") AS monto", 
            "cc.usuario_insercion"};
        String condicion = " where cc.fecha_comprobante >= '" + fe.formatoFecha(dateDesde) + "' AND cc.fecha_comprobante <= '" 
                + fe.formatoFecha(dateHasta) + "' AND estado_compra='" + this.estado + "' ";
        gr.cargarGrilla(grdCompras, tabla, campos, condicion);
    }
    
    public void actualizarGrillaDeudas(){
        String [] columnas = {"FECHA", "PROVEEDOR", "SALDO", "NUM COMPRA", "INSERCION"};
        int [] tamaños = {150,400,150,150,150};
        gr.configurarModelo(grdCompras, columnas, tamaños);
        String tabla = " cuotas_credito_proveedor ccp ";
        String [] campos = {
            "cc.fecha_comprobante",
            "CONCAT(pr.cod_proveedor,' - ',pr.nombre_proveedor) AS proveedor",
            "SUM(ccp.monto_cuota) AS monto",
            "cc.cod_compra_cabecera",
            "cc.usuario_insercion"
        };
        String condicion = " JOIN credito_proveedor cp ON ccp.cod_credito_proveedor=cp.cod_credito_proveedor\n" +
                            "JOIN compras_cabecera cc ON cp.cod_compra=cc.cod_compra_cabecera\n" +
                            "JOIN proveedores pr ON cc.cod_proveedor=pr.cod_proveedor\n" +
                            "WHERE ccp.estado_cuota = 'ACTIVO'\n" +
                            "AND cc.fecha_comprobante >= ' " + fe.formatoFecha(dateDesde) +"'\n" +
                            "AND cc.fecha_comprobante <= ' " + fe.formatoFecha(dateHasta) +"'\n" +
                            "GROUP BY cp.cod_credito_proveedor ";
        gr.cargarGrilla(grdCompras, tabla, campos, condicion);
        gr.formatoFechaGrilla(grdCompras, 0);
    }
    
    
    
    
    
    private boolean validarFecha(){
        if (dateDesde.getDate() == null || dateHasta.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingrese el rango de fecha para mostrar el informe", "Atención", JOptionPane.WARNING_MESSAGE);
                return false;
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat dfh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date1 = df.format(dateDesde.getDate());
            date2 = df.format(dateHasta.getDate());            
        }
        
        LocalDate primeraFecha = LocalDate.parse(date1);
        LocalDate segundaFecha = LocalDate.parse(date2);
        if (primeraFecha.compareTo(segundaFecha) > 0) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio debe ser menor o igual a la fecha final ", "Atención", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void llamarReporteCompras(){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null;
            java.sql.Date date_desde = new java.sql.Date(dateDesde.getDate().getTime()); 
            java.sql.Date date_hasta = new java.sql.Date(dateHasta.getDate().getTime()); 
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            String numeroConSeparador = formato.format(calcularTotal());
            String total = String.valueOf(numeroConSeparador);

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteComprasPorFecha.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();

            parametros.put("fecha_desde", date_desde);
            parametros.put("fecha_hasta", date_hasta);
            parametros.put("total_compra", total);
            parametros.put("estado_compra", this.estado);
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    private void llamarReporte(String [] nomParamet, String [] paramet){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null; 
            
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteCompraDetalle.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();
            
            for (int x = 0; x < nomParamet.length; x++) {
                parametros.put(nomParamet[x], paramet[x]);
                System.out.println("nom " + nomParamet[x] + " valor " + paramet[x]);
            }
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    
    private float calcularTotal(){
        JTable tabla = this.grdCompras;
        float total = 0;
        int filas = tabla.getRowCount();
        for (int x = 0; x < filas; x++) {
            if(tabla.getValueAt(x, 5) != null){
                total += Float.parseFloat(tabla.getValueAt(x, 5).toString());    
                System.out.println(tabla.getValueAt(x, 5).toString());
            }
        }
        return total;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanainformeCompras = new javax.swing.JDesktopPane();
        lblDesde = new javax.swing.JLabel();
        dateDesde = new com.toedter.calendar.JDateChooser();
        lblHasta = new javax.swing.JLabel();
        dateHasta = new com.toedter.calendar.JDateChooser();
        scpContenedor = new javax.swing.JScrollPane();
        grdCompras = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnImprimiResumen = new javax.swing.JToggleButton();
        btnImprimirDetalle = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ventanainformeCompras.setBackground(new java.awt.Color(51, 102, 255));
        ventanainformeCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDesde.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDesde.setForeground(new java.awt.Color(255, 255, 255));
        lblDesde.setText("Desde:");
        ventanainformeCompras.add(lblDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 25));

        dateDesde.setDateFormatString("dd-MM-yyyy");
        dateDesde.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dateDesde.setOpaque(false);
        ventanainformeCompras.add(dateDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 150, 25));

        lblHasta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHasta.setForeground(new java.awt.Color(255, 255, 255));
        lblHasta.setText("Hasta:");
        ventanainformeCompras.add(lblHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 25));

        dateHasta.setDateFormatString("dd-MM-yyyy");
        dateHasta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dateHasta.setOpaque(false);
        ventanainformeCompras.add(dateHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 150, 25));

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdCompras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdCompras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "PROVEEDOR", "TIPO", "FECHA", "NUMERO DE COMPROBANTE", "MONTO", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdCompras.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdCompras.setFillsViewportHeight(true);
        grdCompras.setRowMargin(3);
        grdCompras.setShowGrid(true);
        grdCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                grdComprasMousePressed(evt);
            }
        });
        grdCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grdComprasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                grdComprasKeyReleased(evt);
            }
        });
        scpContenedor.setViewportView(grdCompras);
        if (grdCompras.getColumnModel().getColumnCount() > 0) {
            grdCompras.getColumnModel().getColumn(0).setPreferredWidth(100);
            grdCompras.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdCompras.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdCompras.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdCompras.getColumnModel().getColumn(4).setPreferredWidth(200);
            grdCompras.getColumnModel().getColumn(5).setPreferredWidth(200);
            grdCompras.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        ventanainformeCompras.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1300, 580));

        btnActualizar.setBackground(new java.awt.Color(51, 204, 0));
        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setMnemonic('a');
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        btnActualizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnActualizarKeyPressed(evt);
            }
        });
        ventanainformeCompras.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        btnImprimiResumen.setText("Imprimir Resumen");
        btnImprimiResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimiResumenActionPerformed(evt);
            }
        });
        ventanainformeCompras.add(btnImprimiResumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, 140, 30));

        btnImprimirDetalle.setText("Imprimir Detalle");
        btnImprimirDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirDetalleActionPerformed(evt);
            }
        });
        ventanainformeCompras.add(btnImprimirDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 10, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ventanainformeCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 1318, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ventanainformeCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grdComprasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdComprasMousePressed
        /*int filaSeleccionada = this.grdBuscar.getSelectedRow();
        try {
            FrmCompras2.txtCodArticulo.setText(String.valueOf(grdBuscar.getValueAt(filaSeleccionada,0)));
            FrmBuscarArticulo.this.dispose();
            FrmCompras2.txtCodArticulo.requestFocus();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
        }*/
    }//GEN-LAST:event_grdComprasMousePressed

    private void grdComprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdComprasKeyPressed

    }//GEN-LAST:event_grdComprasKeyPressed

    private void grdComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdComprasKeyReleased
        //DefaultTableModel tm=(DefaultTableModel)grdbcli.getModel();
        /*if(evt.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            int fila = this.grdBuscar.getSelectedRow();
            FrmCompras2.txtCodArticulo.setText(String.valueOf(grdBuscar.getValueAt(fila,0)));
            FrmBuscarArticulo.this.dispose();
            FrmCompras2.txtCodArticulo.requestFocus();
        }*/
    }//GEN-LAST:event_grdComprasKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarFecha()) {
            if(this.obs.equals("DEUDAS PROVEEDORES")){
                this.actualizarGrillaDeudas();                
            }else{
                this.actualizarGrilla();                
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnActualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnActualizarKeyPressed

    }//GEN-LAST:event_btnActualizarKeyPressed

    private void btnImprimiResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimiResumenActionPerformed
        this.llamarReporteCompras();
    }//GEN-LAST:event_btnImprimiResumenActionPerformed

    private void btnImprimirDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirDetalleActionPerformed
        JTable tabla = this.grdCompras;
        int fila = tabla.getSelectedRow();
        if(fila > -1){
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            
            String codCompra = tabla.getValueAt(fila, 0).toString();
            String proveedor = tabla.getValueAt(fila, 1).toString();
            String condicion = tabla.getValueAt(fila, 2).toString();
            String vencimiento = "";
            String fecha = tabla.getValueAt(fila, 3).toString();
            String num_coprobante = "";
            String total = "";
            num_coprobante = tabla.getValueAt(fila, 4).toString();                
            String numeroConSeparador = formato.format(Float.parseFloat(tabla.getValueAt(fila, 5).toString()));
            total = String.valueOf(numeroConSeparador);
            
            
            String [] datos = bd.obtenerRegistro("select ruc_proveedor, timbrado_compra from compras_cabecera where cod_compra_cabecera=" + codCompra);
            String ruc = datos[0];
            String timbrado = datos[1];
            
            if(condicion.equals("COMPRA CREDITO")){
                vencimiento = bd.obtenerRegistro("select fecha_vencimiento_credito from credito_proveedor where cod_compra=" + codCompra)[0];
            }
            
            String [] nomParamet = {"cod_compra","fecha_compra","ruc_proveedor","nombre_proveedor","condicion_compra","vencimiento_credito", "timbrado_compra","num_comprobante", "total_compra"};
            String [] paramet = {codCompra, fecha, ruc, proveedor, condicion, vencimiento, timbrado, num_coprobante, total};
            this.llamarReporte(nomParamet, paramet);
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una compra para ver su detalle");
        }
    }//GEN-LAST:event_btnImprimirDetalleActionPerformed

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
            java.util.logging.Logger.getLogger(FrmInformeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInformeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInformeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInformeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmInformeCompras("ACTIVO").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JToggleButton btnImprimiResumen;
    private javax.swing.JToggleButton btnImprimirDetalle;
    private com.toedter.calendar.JDateChooser dateDesde;
    private com.toedter.calendar.JDateChooser dateHasta;
    public static javax.swing.JTable grdCompras;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JDesktopPane ventanainformeCompras;
    // End of variables declaration//GEN-END:variables
}
