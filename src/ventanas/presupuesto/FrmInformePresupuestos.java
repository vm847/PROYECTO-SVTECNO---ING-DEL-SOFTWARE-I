package ventanas.presupuesto;

import ventas.FrmVentas;
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
public class FrmInformePresupuestos extends javax.swing.JFrame {
    Grilla miGrilla1 = new Grilla();
    Fechas fe = new Fechas();
    BaseDatos1 bd = new BaseDatos1();
    Connection conn = null;
    private FrmVentas frmVentas = null;
    String usuarioLogeado;
    String rol;
    String date1,date2;
    String estado = "ACTIVO";
    String opcion = "";
    
    
    //MUESTRA TODOS LOS PRESUPUESTOS
    public FrmInformePresupuestos(String usuLogeado, String rol, String obs, String Titulo) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.setTitle(Titulo);
        this.opcion = obs;
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.panelInforme.setVisible(true);
        this.fecha_Desde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Desde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.fecha_Hasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Hasta, fe.obtenerFechaActualFormatoNormal());
        this.actualizarGrillaTodas();
    }
        
    //MUESTRA TODOS LOS PRESUPUESTOS ABIERTO DESDE VENTAS
    public FrmInformePresupuestos(FrmVentas frmVentas, String usuLogeado, String rol, String obs, String Titulo) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.setTitle(Titulo);
        this.frmVentas = frmVentas;
        this.opcion = obs;
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.panelInforme.setVisible(true);
        this.fecha_Desde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Desde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.fecha_Hasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Hasta, fe.obtenerFechaActualFormatoNormal());
        this.actualizarGrillaTodas();
    }
    
    public void actualizarGrillaTodas(){
        String [] cabeceras = {"COD","CLIENTE", "FECHA", "ESTADO", "FACTURACION","MONTO", "INSERCION"};
        int [] tamaños = {100,500,150,150,150,150,150};
        miGrilla1.configurarModelo(grdPresupuestos, cabeceras, tamaños);
        String tabla = " presupuestos pr ";
        String [] campos = {"pr.cod_presupuesto",
        "CONCAT(pr.cod_cliente,' - ', cl.nombre_cliente) AS cliente",
        "pr.fecha_presupuesto",
        "pr.estado_presupuesto",
        "COALESCE(pr.num_facturacion, '') AS facturacion",
        "SUM(pd.precio_producto*pd.cantidad_producto) AS total",
        "pr.usuario_insercion"};
        String condicion =  "   JOIN clientes cl ON pr.cod_cliente=cl.cod_cliente\n" +
                            "   JOIN presupuesto_detalle pd ON pr.cod_presupuesto=pd.cod_presupuesto_cabecera\n" +
                            "   WHERE pr.fecha_presupuesto >= '" + fe.formatoFecha(fecha_Desde) + "' " +
                            "   AND pr.fecha_presupuesto <= '" + fe.formatoFecha(fecha_Hasta) + "'\n" + 
                            "   GROUP BY pr.cod_presupuesto";
        miGrilla1.cargarGrilla(grdPresupuestos, tabla, campos, condicion);
        miGrilla1.setSeparadorMiles(grdPresupuestos, 5);
    }
    
    
    
    public void actualizarGrilla2(){
        String tabla = "cuotas_credito_cliente cdc ";        
        String [] campos = {
            "cc.fecha_inicio_credito", 
            "cl.cod_cliente", 
            "CONCAT(cl.nombre_cliente,' ',cl.apellido_cliente)", 
            "sum(cdc.monto_cuota)", "vc.cod_venta_cabecera",
        "vc.usuario_insercion"};
        String condicion = " JOIN credito_cliente cc ON cdc.cod_credito_cliente = cc.cod_credito_cliente "
                + " JOIN ventas_cabecera vc ON cc.cod_venta = vc.cod_venta_cabecera "
                + " JOIN clientes cl ON vc.cod_cliente = cl.cod_cliente "
                + " WHERE estado_cuota='ACTIVO' "
                + "AND vc.fecha_venta >= '" + fe.formatoFecha(fecha_Desde) + "'  "
                + " AND vc.fecha_venta <= '" + fe.formatoFecha(fecha_Hasta) + "'"
                + " GROUP BY cc.cod_credito_cliente";
        miGrilla1.cargarGrilla(grdPresupuestos, tabla, campos, condicion);
        miGrilla1.formatoFechaGrilla(grdPresupuestos, 0);
    }
    
    
    //ESTE SE USA PARA EL INFORME POR CAJA
    public void actualizarGrilla3(String codCaja){
        String tabla = " ventas_cabecera vc ";        
        String [] campos = {
            "vc.cod_venta_cabecera", 
            "CONCAT(vc.cod_cliente,' - ',vc.razon_social_cliente)", 
            "vc.tipo_movimiento", 
            "vc.concepto_movimiento", 
            "vc.fecha_venta",
            "COALESCE(vc.num_comprobante_venta, '')",
            "COALESCE(" +
                "(SELECT GROUP_CONCAT(CONCAT(metodo_pago_venta, ': ', monto_venta) SEPARATOR ', ') \n" +
                "FROM metodo_pago_venta WHERE cod_venta = vc.cod_venta_cabecera), " +
                "CONCAT('EFECTIVO',': ',(SELECT SUM(monto_egreso) FROM detalle_egreso_venta WHERE cod_venta_cabecera = vc.cod_venta_cabecera)), ''" +
            ") AS Metodo",
            "COALESCE(\n" +
            "	(SELECT SUM(vd.cantidad_producto * vd.precio_producto) \n" +
            "	FROM ventas_detalle vd \n" +
            "	WHERE vd.cod_venta_cabecera = vc.cod_venta_cabecera),\n" +
            "	(SELECT SUM(ccc.monto_cuota) \n" +
            "	FROM cuotas_credito_cliente ccc \n" +
            "	WHERE ccc.num_movimiento = vc.cod_venta_cabecera),\n" +
            "	(SELECT\n" +
            "	CONCAT('-',sum(monto_egreso)) AS monto\n" +
            "	FROM detalle_egreso_venta\n" +
            "	WHERE cod_venta_cabecera = vc.cod_venta_cabecera)\n" +
            ") AS monto_total", 
            "vc.usuario_insercion"
        };
        String condicion =  " LEFT JOIN ventas_detalle vd ON vc.cod_venta_cabecera=vd.cod_venta_cabecera" +
                            " WHERE vc.cod_caja = " + codCaja + "  and vc.estado_venta = 'ACTIVO'" +
                            " GROUP BY vc.cod_venta_cabecera";
        miGrilla1.cargarGrilla(grdPresupuestos, tabla, campos, condicion);
    }
    
    
    
    
    
    
    
    
    private boolean validarFecha(){
        if (fecha_Desde.getDate() == null || fecha_Hasta.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingrese el rango de fecha para mostrar el informe", "Atención", JOptionPane.WARNING_MESSAGE);
                return false;
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat dfh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date1 = df.format(fecha_Desde.getDate());
            date2 = df.format(fecha_Hasta.getDate());            
        }
        
        LocalDate primeraFecha = LocalDate.parse(date1);
        LocalDate segundaFecha = LocalDate.parse(date2);
        if (primeraFecha.compareTo(segundaFecha) > 0) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio debe ser menor o igual a la fecha final ", "Atención", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void llamarReportePorFecha(){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null;
            java.sql.Date date_desde = new java.sql.Date(fecha_Desde.getDate().getTime()); 
            java.sql.Date date_hasta = new java.sql.Date(fecha_Hasta.getDate().getTime());  

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reportePresupuestosPorFecha.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();

            System.out.println("desde " + date_desde);
            System.out.println("hasta " + date_hasta);
            parametros.put("fecha_desde", date_desde);
            parametros.put("fecha_hasta", date_hasta);
            parametros.put("estado_venta", this.estado);
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    
    private float calcularTotal(){
        JTable tabla = this.grdPresupuestos;
        float total = 0;
        int filas = tabla.getRowCount();
        for (int x = 0; x < filas; x++) {
            total += Float.parseFloat(tabla.getValueAt(x, 5).toString());
        }
        return total;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanainformeCompras = new javax.swing.JDesktopPane();
        scpContenedor = new javax.swing.JScrollPane();
        grdPresupuestos = new javax.swing.JTable();
        panelInforme = new javax.swing.JPanel();
        lblDesde = new javax.swing.JLabel();
        fecha_Desde = new com.toedter.calendar.JDateChooser();
        lblHasta = new javax.swing.JLabel();
        fecha_Hasta = new com.toedter.calendar.JDateChooser();
        btnActualizar = new javax.swing.JButton();
        btnImprimiResumen = new javax.swing.JToggleButton();
        btnImprimirDetalle = new javax.swing.JToggleButton();
        btnFacturarPresupuesto = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ventanainformeCompras.setBackground(new java.awt.Color(51, 102, 255));
        ventanainformeCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdPresupuestos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdPresupuestos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdPresupuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CLIENTE", "TIPO", "FECHA", "NUMERO DE COMPROBANTE", "MONTO", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdPresupuestos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdPresupuestos.setFillsViewportHeight(true);
        grdPresupuestos.setRowMargin(3);
        grdPresupuestos.setShowGrid(true);
        grdPresupuestos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                grdPresupuestosMousePressed(evt);
            }
        });
        grdPresupuestos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grdPresupuestosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                grdPresupuestosKeyReleased(evt);
            }
        });
        scpContenedor.setViewportView(grdPresupuestos);
        if (grdPresupuestos.getColumnModel().getColumnCount() > 0) {
            grdPresupuestos.getColumnModel().getColumn(0).setPreferredWidth(100);
            grdPresupuestos.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdPresupuestos.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdPresupuestos.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdPresupuestos.getColumnModel().getColumn(4).setPreferredWidth(200);
            grdPresupuestos.getColumnModel().getColumn(5).setPreferredWidth(200);
            grdPresupuestos.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        ventanainformeCompras.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1300, 550));

        panelInforme.setBackground(new java.awt.Color(51, 102, 255));
        panelInforme.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelInforme.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDesde.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDesde.setForeground(new java.awt.Color(255, 255, 255));
        lblDesde.setText("Desde");
        panelInforme.add(lblDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 25));

        fecha_Desde.setDateFormatString("dd-MM-yyyy");
        fecha_Desde.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fecha_Desde.setOpaque(false);
        panelInforme.add(fecha_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 25));

        lblHasta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHasta.setForeground(new java.awt.Color(255, 255, 255));
        lblHasta.setText("Hasta:");
        panelInforme.add(lblHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 25));

        fecha_Hasta.setDateFormatString("dd-MM-yyyy");
        fecha_Hasta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fecha_Hasta.setOpaque(false);
        panelInforme.add(fecha_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 150, 25));

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
        panelInforme.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        btnImprimiResumen.setText("Imprimir Resumen");
        btnImprimiResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimiResumenActionPerformed(evt);
            }
        });
        panelInforme.add(btnImprimiResumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 140, 30));

        btnImprimirDetalle.setText("Imprimir Detalle");
        btnImprimirDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirDetalleActionPerformed(evt);
            }
        });
        panelInforme.add(btnImprimirDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 140, 30));

        btnFacturarPresupuesto.setText("Facturar");
        btnFacturarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarPresupuestoActionPerformed(evt);
            }
        });
        panelInforme.add(btnFacturarPresupuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, 140, 30));

        ventanainformeCompras.add(panelInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 1300, 50));

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

    private void grdPresupuestosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdPresupuestosMousePressed
        
    }//GEN-LAST:event_grdPresupuestosMousePressed

    private void grdPresupuestosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdPresupuestosKeyPressed

    }//GEN-LAST:event_grdPresupuestosKeyPressed

    private void grdPresupuestosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdPresupuestosKeyReleased
        //DefaultTableModel tm=(DefaultTableModel)grdbcli.getModel();
        /*if(evt.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            int fila = this.grdBuscar.getSelectedRow();
            FrmCompras2.txtCodArticulo.setText(String.valueOf(grdBuscar.getValueAt(fila,0)));
            FrmBuscarArticulo.this.dispose();
            FrmCompras2.txtCodArticulo.requestFocus();
        }*/
    }//GEN-LAST:event_grdPresupuestosKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarFecha()) {
            if(this.opcion.equals("TODOS")){
                this.actualizarGrillaTodas();                
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnActualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnActualizarKeyPressed

    }//GEN-LAST:event_btnActualizarKeyPressed

    private void btnImprimiResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimiResumenActionPerformed
        this.llamarReportePorFecha();
    }//GEN-LAST:event_btnImprimiResumenActionPerformed

    private void btnImprimirDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirDetalleActionPerformed
        JTable tabla = this.grdPresupuestos;
        int fila = tabla.getSelectedRow();
        String url = "/reportes/reportePresupuestoDetalle.jasper";
        if(fila > -1){
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            String cod = tabla.getValueAt(fila, 0).toString();                        
            
            String [] datos = bd.obtenerRegistro(   "select " + 
                                                    "fecha_presupuesto, \n" +
                                                    "cl.ruc_cliente, \n" +
                                                    "CONCAT(cl.apellido_cliente, ', ', cl.nombre_cliente) AS cliente, \n" +
                                                    "SUM(pd.cantidad_producto*pd.precio_producto) AS monto\n" +
                                                    "from presupuestos pr \n" +
                                                    "JOIN clientes cl ON pr.cod_cliente=cl.cod_cliente\n" +
                                                    "JOIN presupuesto_detalle pd ON pr.cod_presupuesto=pd.cod_presupuesto_cabecera\n" +
                                                    "where pr.cod_presupuesto=" + cod);
            String fecha = datos[0];
            String ruc = datos[1];
            String cliente = datos[2];
            String total = datos[3];
            if(!total.isEmpty()){
                String numeroConSeparador = formato.format(Float.parseFloat(total));
                total = String.valueOf(numeroConSeparador);                
            }
            String [] nomParamet = {"cod",
                "fecha",
                "ruc",
                "nombre_cliente",
                "total"};
            String [] paramet = {cod, fecha, ruc, cliente, total};
            bd.llamarReporte(url, nomParamet, paramet);
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una compra para ver su detalle");
        }
    }//GEN-LAST:event_btnImprimirDetalleActionPerformed

    private void btnFacturarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarPresupuestoActionPerformed
        int fila = this.grdPresupuestos.getSelectedRow();
        if(fila > -1){
            if(this.grdPresupuestos.getValueAt(fila, 3).toString().equals("ACTIVO")){
                String cod = this.grdPresupuestos.getValueAt(fila, 0).toString();
                if(this.frmVentas != null){
                    frmVentas.codPresupuesto = cod;
                    frmVentas.facturarPresupuesto(cod);
                    frmVentas.setVisible(true);
                    this.dispose();
                    System.out.println("frmVentas no es nullo");
                }else{
                    FrmVentas fr = new FrmVentas(usuarioLogeado, this.rol, cod);
                    fr.setDefaultCloseOperation(HIDE_ON_CLOSE);
                    fr.setVisible(true);
                    System.out.println("frmVentas es nullo");                
                }
            }else{
                JOptionPane.showMessageDialog(null, "ESTE PRESUPUESTO YA FUE FACTURADO");
            }
        }
    }//GEN-LAST:event_btnFacturarPresupuestoActionPerformed

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
            java.util.logging.Logger.getLogger(FrmInformePresupuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInformePresupuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInformePresupuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInformePresupuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmInformePresupuestos("VLOPEZ","ADMIN","TODOS", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JToggleButton btnFacturarPresupuesto;
    private javax.swing.JToggleButton btnImprimiResumen;
    private javax.swing.JToggleButton btnImprimirDetalle;
    private com.toedter.calendar.JDateChooser fecha_Desde;
    private com.toedter.calendar.JDateChooser fecha_Hasta;
    public static javax.swing.JTable grdPresupuestos;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JPanel panelInforme;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JDesktopPane ventanainformeCompras;
    // End of variables declaration//GEN-END:variables
}
