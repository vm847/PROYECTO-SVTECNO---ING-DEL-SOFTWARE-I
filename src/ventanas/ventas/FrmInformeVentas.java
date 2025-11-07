package ventanas.ventas;

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
import javax.swing.JTable;
import servicios.BaseDatos1;
import servicios.Fechas;
import servicios.Grilla;

/**
 *
 * @author Asus
 */
public class FrmInformeVentas extends javax.swing.JFrame {
    Grilla miGrilla1 = new Grilla();
    Fechas fe = new Fechas();
    BaseDatos1 bd = new BaseDatos1();
    Connection conn = null;
    String date1,date2;
    String estado = "ACTIVO";
    String opcion = "";
    
    
    //MUESTRA TODAS LAS VENTAS POR RANGO DE FECHA
    public FrmInformeVentas(String estado) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.opcion = "ESTADO";
        this.panelInformeCaja.setVisible(false);
        this.panelInformeVentas.setVisible(true);
        this.fecha_Desde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Desde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.fecha_Hasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Hasta, fe.obtenerFechaActualFormatoNormal());
        this.estado = estado;
        this.actualizarGrilla();
        if(estado.equals("INACTIVO")){
            this.btnImprimirDetalle.setVisible(false);
        }else{
            this.btnImprimirDetalle.setVisible(true);            
        }
        
        this.lblEfectivo.setVisible(false);
        this.txtEfectivo.setVisible(false);
        this.lblOtros.setVisible(false);
        this.txtOtros.setVisible(false);
        this.lblTotal.setVisible(false);
        this.txtTotalcaja.setVisible(false);
    }
    
    
    public FrmInformeVentas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.opcion = "TODAS";
        this.panelInformeCaja.setVisible(false);
        this.panelInformeVentas.setVisible(true);
        this.fecha_Desde.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Desde, fe.obtenerFechaMenosDiasFormatoNormal(-10));
        this.fecha_Hasta.setMaxSelectableDate(new java.util.Date());
        fe.mostrarFechaJDateChooserFormatoNormal(fecha_Hasta, fe.obtenerFechaActualFormatoNormal());
        String [] columnas = {"FECHA", "COD CLIENTE", "NOMBRE CLIENTE", "SALDO", "NUM VENTA", "INSERCION"};
        int [] tamaños = {150,150,500,150,150,150};
        miGrilla1.configurarModelo(grdVentas, columnas, tamaños);
        this.actualizarGrilla2();
    }
    
    //INFORME VENTAS POR CAJA
    public FrmInformeVentas(String caja, String c){
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.estado = "CAJA";
        String [] columnas = {"COD", "NOMBRE CLIENTE", "TIPO", "CONDICION", "FECHA", "COMPROBANTE", "METODO PAGO", "MONTO", "INSERCION"};
        int [] tamaños = {50,300,100,150,150,150,200, 100, 100};
        miGrilla1.configurarModelo(grdVentas, columnas, tamaños);
        this.actualizarGrilla3(caja);
        miGrilla1.alinearDerecha(grdVentas, 6);
        miGrilla1.setSeparadorMiles(grdVentas, 6);
        this.obtenerCaja(caja);
        this.panelInformeCaja.setVisible(true);
        this.panelInformeVentas.setVisible(false);
        this.lblNumCaja.setText(caja);
        this.calcularTotales(caja);
    }
    
    public FrmInformeVentas(String [] cabecera, int [] tamaños, String tabla, String [] campos, String condicion, String obs){
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        miGrilla1.configurarModelo(grdVentas, cabecera, tamaños);
        miGrilla1.cargarGrilla(grdVentas, tabla, campos, condicion);
        miGrilla1.formatoFechaGrilla(grdVentas, 5);
        miGrilla1.formatoFechaGrilla(grdVentas, 6);
        miGrilla1.alinearCentrar(grdVentas, 1);
        miGrilla1.alinearCentrar(grdVentas, 2);
        miGrilla1.alinearCentrar(grdVentas, 3);
        miGrilla1.alinearCentrar(grdVentas, 4);
        miGrilla1.alinearCentrar(grdVentas, 5);
        miGrilla1.alinearCentrar(grdVentas, 6);
        miGrilla1.alinearCentrar(grdVentas, 9);
        miGrilla1.alinearDerecha(grdVentas, 7);
        miGrilla1.alinearDerecha(grdVentas, 8);
        miGrilla1.setSeparadorMiles(grdVentas, 7);
        miGrilla1.setSeparadorMiles(grdVentas, 8);
        this.panelInformeCaja.setVisible(false);
        this.panelInformeVentas.setVisible(false);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void obtenerCaja(String numCaja){
        String [] caja = bd.obtenerRegistro("select cod_caja, cod_usuario, fecha_apertura_caja, IFNULL(fecha_cierre_caja, 'NULL') from cajas where cod_caja = " + numCaja);
        if(caja != null){
            String nomUsuario = bd.obtenerRegistro("select nom_usuario from usuarios where cod_usuario = " + caja[1])[0];
            this.lblNombreCajero.setText(nomUsuario);
            this.lblNumCaja.setText(caja[0]);
            this.txtApertura.setText(fe.formatearFechaHora(caja[2]));
            if(!caja[3].equals("NULL")){                
                this.txtCierre.setText(fe.formatearFechaHora(caja[3]));
            }else{
                this.txtCierre.setText(null);
            }
        }
    }
    private void calcularTotales(String numCaja){
        int filas = this.grdVentas.getRowCount();
        float efectivo = 0;
        float egreso = 0;
        float otros = 0;
        float total = 0;
        String [][] montos = bd.obtenerRegistros(   "SELECT mp.metodo_pago_venta, mp.monto_venta FROM metodo_pago_venta mp\n" +
                                                    "JOIN ventas_cabecera vc ON mp.cod_venta = vc.cod_venta_cabecera\n" +
                                                    "WHERE cod_caja = " + numCaja + " AND vc.estado_venta = 'ACTIVO'");
        if(montos != null){
            for (int x = 0; x < montos.length; x++) {
                if(montos[x][0].equals("EFECTIVO")){
                    efectivo+= Float.parseFloat(montos[x][1]);
                    total+= Float.parseFloat(montos[x][1]);
                }else{
                    otros+= Float.parseFloat(montos[x][1]);
                    total+= Float.parseFloat(montos[x][1]);
                }
            }
            for (int x = 0; x < filas; x++) {
                if(this.grdVentas.getValueAt(x, 2).toString().equals("EGRESO")){
                    egreso+= Float.parseFloat(this.grdVentas.getValueAt(x, 7).toString());                    
                }
            }
            this.txtEfectivo.setText(bd.agregarSeparadorMiles(String.valueOf(efectivo + egreso)));
            this.txtOtros.setText(bd.agregarSeparadorMiles(String.valueOf(otros)));
            this.txtEgresos.setText(bd.agregarSeparadorMiles(String.valueOf(egreso)));
            this.txtTotalcaja.setText(bd.agregarSeparadorMiles(String.valueOf(total + egreso)));
        }
    }
    
    public void actualizarGrilla(){
        String tabla = "ventas_cabecera vc ";
        String [] campos = {"vc.cod_venta_cabecera", 
            "concat(cl.nombre_cliente, ' ', cl.apellido_cliente) ", 
            "vc.concepto_movimiento", 
            "vc.fecha_venta", 
            "vc.num_comprobante_venta",
            "(" +
            "SELECT \n" +
            "SUM(mp.monto_venta)\n" +
            "FROM metodo_pago_venta mp\n" +
            "WHERE mp.cod_venta = vc.cod_venta_cabecera" +
            ") AS monto_total", 
            "vc.usuario_insercion"};
        String condicion = " JOIN clientes cl ON vc.cod_cliente = cl.cod_cliente  "
                + "where vc.fecha_venta >= '" + fe.formatoFecha(fecha_Desde) + "' AND vc.fecha_venta <= '" 
                + fe.formatoFecha(fecha_Hasta) + "' AND vc.estado_venta='" + this.estado + "' ";
        miGrilla1.cargarGrilla(grdVentas, tabla, campos, condicion);
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
        miGrilla1.cargarGrilla(grdVentas, tabla, campos, condicion);
        miGrilla1.formatoFechaGrilla(grdVentas, 0);
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
        miGrilla1.cargarGrilla(grdVentas, tabla, campos, condicion);
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
    
    private void llamarReporteVentas(){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null;
            java.sql.Date date_desde = new java.sql.Date(fecha_Desde.getDate().getTime()); 
            java.sql.Date date_hasta = new java.sql.Date(fecha_Hasta.getDate().getTime());  

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteVentasPorFecha.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();

            System.out.println("desde " + fe.formatoFechaNormal(fecha_Desde));
            System.out.println("hasta " + fe.formatoFechaNormal(fecha_Hasta));
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
    private void llamarReporte(String [] nomParamet, String [] paramet){
        try {
            conn = bd.obtenerConexion();
            JasperReport reporte = null; 
            
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteVentaDetalle.jasper"));
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
        JTable tabla = this.grdVentas;
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
        grdVentas = new javax.swing.JTable();
        txtTotalcaja = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtEfectivo = new javax.swing.JTextField();
        lblEfectivo = new javax.swing.JLabel();
        txtOtros = new javax.swing.JTextField();
        lblOtros = new javax.swing.JLabel();
        panelInformeCaja = new javax.swing.JPanel();
        lblNombreCajero = new javax.swing.JLabel();
        lblCajero = new javax.swing.JLabel();
        lblCaja = new javax.swing.JLabel();
        lblNumCaja = new javax.swing.JLabel();
        btnImprimiResumen1 = new javax.swing.JToggleButton();
        btnImprimirDetalle1 = new javax.swing.JToggleButton();
        lblCierre = new javax.swing.JLabel();
        lblApertura = new javax.swing.JLabel();
        txtApertura = new javax.swing.JTextField();
        txtCierre = new javax.swing.JTextField();
        panelInformeVentas = new javax.swing.JPanel();
        lblDesde = new javax.swing.JLabel();
        fecha_Desde = new com.toedter.calendar.JDateChooser();
        lblHasta = new javax.swing.JLabel();
        fecha_Hasta = new com.toedter.calendar.JDateChooser();
        btnActualizar = new javax.swing.JButton();
        btnImprimiResumen = new javax.swing.JToggleButton();
        btnImprimirDetalle = new javax.swing.JToggleButton();
        txtEgresos = new javax.swing.JTextField();
        lblEgresos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ventanainformeCompras.setBackground(new java.awt.Color(51, 102, 255));
        ventanainformeCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdVentas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdVentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        grdVentas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdVentas.setFillsViewportHeight(true);
        grdVentas.setRowMargin(3);
        grdVentas.setShowGrid(true);
        grdVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                grdVentasMousePressed(evt);
            }
        });
        grdVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grdVentasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                grdVentasKeyReleased(evt);
            }
        });
        scpContenedor.setViewportView(grdVentas);
        if (grdVentas.getColumnModel().getColumnCount() > 0) {
            grdVentas.getColumnModel().getColumn(0).setPreferredWidth(100);
            grdVentas.getColumnModel().getColumn(1).setPreferredWidth(300);
            grdVentas.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdVentas.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdVentas.getColumnModel().getColumn(4).setPreferredWidth(200);
            grdVentas.getColumnModel().getColumn(5).setPreferredWidth(200);
            grdVentas.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        ventanainformeCompras.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1300, 480));

        txtTotalcaja.setEditable(false);
        txtTotalcaja.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalcaja.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalcaja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalcaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalcaja.setFocusable(false);
        ventanainformeCompras.add(txtTotalcaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 580, 250, 40));

        lblTotal.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("TOTAL CAJA");
        ventanainformeCompras.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 590, 120, 20));

        txtEfectivo.setEditable(false);
        txtEfectivo.setBackground(new java.awt.Color(255, 255, 255));
        txtEfectivo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEfectivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEfectivo.setFocusable(false);
        ventanainformeCompras.add(txtEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 150, 30));

        lblEfectivo.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblEfectivo.setForeground(new java.awt.Color(255, 255, 255));
        lblEfectivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEfectivo.setText("EFECTIVO");
        ventanainformeCompras.add(lblEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 570, 70, -1));

        txtOtros.setEditable(false);
        txtOtros.setBackground(new java.awt.Color(255, 255, 255));
        txtOtros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOtros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtOtros.setFocusable(false);
        ventanainformeCompras.add(txtOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 590, 150, 30));

        lblOtros.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblOtros.setForeground(new java.awt.Color(255, 255, 255));
        lblOtros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtros.setText("OTROS");
        ventanainformeCompras.add(lblOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 570, 70, -1));

        panelInformeCaja.setBackground(new java.awt.Color(51, 102, 255));
        panelInformeCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelInformeCaja.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombreCajero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreCajero.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreCajero.setText("a");
        panelInformeCaja.add(lblNombreCajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 25));

        lblCajero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCajero.setForeground(new java.awt.Color(255, 255, 255));
        lblCajero.setText("Cajero");
        panelInformeCaja.add(lblCajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 25));

        lblCaja.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(255, 255, 255));
        lblCaja.setText("Num Caja");
        panelInformeCaja.add(lblCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 60, 25));

        lblNumCaja.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNumCaja.setForeground(new java.awt.Color(255, 255, 255));
        lblNumCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumCaja.setText("a");
        panelInformeCaja.add(lblNumCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 60, 25));

        btnImprimiResumen1.setText("Imprimir Resumen");
        btnImprimiResumen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimiResumen1ActionPerformed(evt);
            }
        });
        panelInformeCaja.add(btnImprimiResumen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 140, 30));

        btnImprimirDetalle1.setText("Imprimir Detalle");
        btnImprimirDetalle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirDetalle1ActionPerformed(evt);
            }
        });
        panelInformeCaja.add(btnImprimirDetalle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 10, 140, 30));

        lblCierre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCierre.setForeground(new java.awt.Color(255, 255, 255));
        lblCierre.setText("Cierre");
        panelInformeCaja.add(lblCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, 25));

        lblApertura.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblApertura.setForeground(new java.awt.Color(255, 255, 255));
        lblApertura.setText("Apertura");
        panelInformeCaja.add(lblApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 25));

        txtApertura.setEditable(false);
        txtApertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelInformeCaja.add(txtApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 150, -1));

        txtCierre.setEditable(false);
        txtCierre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelInformeCaja.add(txtCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 150, -1));

        ventanainformeCompras.add(panelInformeCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1300, 50));

        panelInformeVentas.setBackground(new java.awt.Color(51, 102, 255));
        panelInformeVentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelInformeVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDesde.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDesde.setForeground(new java.awt.Color(255, 255, 255));
        lblDesde.setText("Desde");
        panelInformeVentas.add(lblDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 25));

        fecha_Desde.setDateFormatString("dd-MM-yyyy");
        fecha_Desde.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fecha_Desde.setOpaque(false);
        panelInformeVentas.add(fecha_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 25));

        lblHasta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHasta.setForeground(new java.awt.Color(255, 255, 255));
        lblHasta.setText("Hasta:");
        panelInformeVentas.add(lblHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 25));

        fecha_Hasta.setDateFormatString("dd-MM-yyyy");
        fecha_Hasta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fecha_Hasta.setOpaque(false);
        panelInformeVentas.add(fecha_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 150, 25));

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
        panelInformeVentas.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        btnImprimiResumen.setText("Imprimir Resumen");
        btnImprimiResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimiResumenActionPerformed(evt);
            }
        });
        panelInformeVentas.add(btnImprimiResumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 140, 30));

        btnImprimirDetalle.setText("Imprimir Detalle");
        btnImprimirDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirDetalleActionPerformed(evt);
            }
        });
        panelInformeVentas.add(btnImprimirDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 10, 140, 30));

        ventanainformeCompras.add(panelInformeVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 1300, 50));

        txtEgresos.setEditable(false);
        txtEgresos.setBackground(new java.awt.Color(255, 255, 255));
        txtEgresos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEgresos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEgresos.setFocusable(false);
        ventanainformeCompras.add(txtEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 590, 150, 30));

        lblEgresos.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblEgresos.setForeground(new java.awt.Color(255, 255, 255));
        lblEgresos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEgresos.setText("EGRESOS");
        ventanainformeCompras.add(lblEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 70, -1));

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

    private void grdVentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdVentasMousePressed
        /*int filaSeleccionada = this.grdBuscar.getSelectedRow();
        try {
            FrmCompras2.txtCodArticulo.setText(String.valueOf(grdBuscar.getValueAt(filaSeleccionada,0)));
            FrmBuscarArticulo.this.dispose();
            FrmCompras2.txtCodArticulo.requestFocus();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
        }*/
    }//GEN-LAST:event_grdVentasMousePressed

    private void grdVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdVentasKeyPressed

    }//GEN-LAST:event_grdVentasKeyPressed

    private void grdVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdVentasKeyReleased
        //DefaultTableModel tm=(DefaultTableModel)grdbcli.getModel();
        /*if(evt.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            int fila = this.grdBuscar.getSelectedRow();
            FrmCompras2.txtCodArticulo.setText(String.valueOf(grdBuscar.getValueAt(fila,0)));
            FrmBuscarArticulo.this.dispose();
            FrmCompras2.txtCodArticulo.requestFocus();
        }*/
    }//GEN-LAST:event_grdVentasKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarFecha()) {
            this.actualizarGrilla();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnActualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnActualizarKeyPressed

    }//GEN-LAST:event_btnActualizarKeyPressed

    private void btnImprimiResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimiResumenActionPerformed
        this.llamarReporteVentas();
    }//GEN-LAST:event_btnImprimiResumenActionPerformed

    private void btnImprimirDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirDetalleActionPerformed
        JTable tabla = this.grdVentas;
        int fila = tabla.getSelectedRow();
        if(fila > -1){
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            String codVenta = tabla.getValueAt(fila, 0).toString();                        
            
            String [] datos = bd.obtenerRegistro("select fecha_venta, ruc_cliente, razon_social_cliente, concepto_movimiento, "
                    + "timbrado_venta, num_comprobante_venta from ventas_cabecera where cod_venta_cabecera=" + codVenta);
            String fecha = datos[0];
            String ruc = datos[1];
            String cliente = datos[2];
            String condicion = datos[3];
            String timbrado = datos[4];
            String num_coprobante = datos[5];
            String vencimiento = "";
            String mont = tabla.getValueAt(fila, 5).toString();
            String total = "";
            if(!mont.isEmpty()){
                String numeroConSeparador = formato.format(Float.parseFloat(mont));
                total = String.valueOf(numeroConSeparador);                
            }
            
            if(condicion.equals("VENTA CREDITO")){
                vencimiento = bd.obtenerRegistro("select fecha_vencimiento_credito from credito_cliente where cod_venta    =" + codVenta)[0];
            }
            
            String [] nomParamet = {"cod_venta","fecha_venta","ruc_cliente","nombre_cliente","condicion_venta","vencimiento_credito", "timbrado_venta","num_comprobante", "total_venta"};
            String [] paramet = {codVenta, fecha, ruc, cliente, condicion, vencimiento, timbrado, num_coprobante, total};
            this.llamarReporte(nomParamet, paramet);
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una compra para ver su detalle");
        }
    }//GEN-LAST:event_btnImprimirDetalleActionPerformed

    private void btnImprimiResumen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimiResumen1ActionPerformed
        String url = "/reportes/reporteVentasPorCaja.jasper";
        String [] nomParamet = {"estado_venta",
            "cod_caja",
            "nombre_cajero",
            "fecha_apertura",
            "fecha_cierre",
            "total_efectivo",
            "total_otros",
            "total_egreso",
            "total_caja"
        };
        String [] parametro = {"ACTIVO", 
            this.lblNumCaja.getText(),
            this.lblNombreCajero.getText(),
            this.txtApertura.getText(),
            this.txtCierre.getText(),
            this.txtEfectivo.getText(),
            this.txtOtros.getText(),
            this.txtEgresos.getText(),
            this.txtTotalcaja.getText()
        };
        String [] nomP = {"cod_caja"};
        int [] param = {Integer.parseInt(lblNumCaja.getText())};
        bd.llamarReporteConInt(url, nomParamet, parametro, nomP, param);
    }//GEN-LAST:event_btnImprimiResumen1ActionPerformed

    private void btnImprimirDetalle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirDetalle1ActionPerformed
        JTable tabla = this.grdVentas;
        int fila = tabla.getSelectedRow();
        if(fila > -1){
            if(tabla.getValueAt(fila, 2).toString().equals("INGRESO")){
                DecimalFormat formato = new DecimalFormat("#,##0.00");
                String codVenta = tabla.getValueAt(fila, 0).toString();                        
                String url = "/reportes/reporteVentaDetalle.jasper";

                String [] datos = bd.obtenerRegistro("select fecha_venta, "
                        + "ruc_cliente, "
                        + "razon_social_cliente, "
                        + "concepto_movimiento, "
                        + "timbrado_venta, "
                        + "num_comprobante_venta "
                        + "from ventas_cabecera where cod_venta_cabecera=" + codVenta);
                String fecha = datos[0];
                String ruc = datos[1];
                String cliente = datos[2];
                String condicion = datos[3];
                String timbrado = datos[4];
                String num_coprobante = datos[5];
                String vencimiento = "";
                String total = tabla.getValueAt(fila, 7).toString();

                if(condicion.equals("VENTA CREDITO")){
                    vencimiento = bd.obtenerRegistro("select fecha_vencimiento_credito from credito_cliente where cod_venta    =" + codVenta)[0];
                }

                String [] nomParamet = {"cod_venta","fecha_venta","ruc_cliente","nombre_cliente","condicion_venta","vencimiento_credito", "timbrado_venta","num_comprobante", "total_venta"};
                String [] paramet = {codVenta, fecha, ruc, cliente, condicion, vencimiento, timbrado, num_coprobante, total};
                bd.llamarReporte(url,nomParamet, paramet);                
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una compra para ver su detalle");
        }
        
        
    }//GEN-LAST:event_btnImprimirDetalle1ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmInformeVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInformeVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInformeVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInformeVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmInformeVentas("1", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JToggleButton btnImprimiResumen;
    private javax.swing.JToggleButton btnImprimiResumen1;
    private javax.swing.JToggleButton btnImprimirDetalle;
    private javax.swing.JToggleButton btnImprimirDetalle1;
    private com.toedter.calendar.JDateChooser fecha_Desde;
    private com.toedter.calendar.JDateChooser fecha_Hasta;
    public static javax.swing.JTable grdVentas;
    private javax.swing.JLabel lblApertura;
    private javax.swing.JLabel lblCaja;
    private javax.swing.JLabel lblCajero;
    private javax.swing.JLabel lblCierre;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblEfectivo;
    private javax.swing.JLabel lblEgresos;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JLabel lblNombreCajero;
    private javax.swing.JLabel lblNumCaja;
    private javax.swing.JLabel lblOtros;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelInformeCaja;
    private javax.swing.JPanel panelInformeVentas;
    private javax.swing.JScrollPane scpContenedor;
    private javax.swing.JTextField txtApertura;
    private javax.swing.JTextField txtCierre;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtEgresos;
    private javax.swing.JTextField txtOtros;
    private javax.swing.JTextField txtTotalcaja;
    private javax.swing.JDesktopPane ventanainformeCompras;
    // End of variables declaration//GEN-END:variables
}
