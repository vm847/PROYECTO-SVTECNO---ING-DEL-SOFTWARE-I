
package ventanas.presupuesto;

import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDatos1;
import javax.swing.JTable;
import servicios.Fechas;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;
import ventas.FrmVentas;



public class FrmPresupuesto extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Grilla gr = new Grilla();
    Fechas fe = new Fechas();
    String usuarioLogeado = "VLOPEZ";
    String rol = "ADMIN";
    String accion = "nuevo";
    private String codPres;
    
    public FrmPresupuesto(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.usuarioLogeado = usuLogeado;
        this.rol=rol;
        this.setTitle("PRESUPUESTOS - " + usuarioLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.restricciones();
        this.nuevo();
    }
    
    private void restricciones(){   
        aj.soloNumeros(txtCodCliente, 5);
        aj.soloNumeros(txtCodProducto, 5);
        aj.soloNumeros(txtCantProducto, 5);
        aj.soloNumeros(txtDescuento, 10);
        
        aj.presionarEnter(txtCodigo, txtCodCliente);
        aj.presionarEnter(txtCodCliente, txtCodProducto);
        aj.presionarEnter(txtCodProducto, txtCantProducto);
        aj.presionarEnter(txtCantProducto, btnAgregarProducto);
        aj.presionarEnter(txtDescuento, btnAgregarProducto);
        aj.presionarEnter(txtPrecioProducto, btnAgregarProducto);
        aj.enterActionPerformed(btnAgregarProducto);
        
    }
    
    private void nuevo(){
        this.accion = "nuevo";
        this.limpiarCampos();
        this.limpiarCamposDetalle();
        this.habilitarCampos(true);   
        fe.mostrarFechaJDateChooser(dateFecha,fe.obtenerFechaActual());
        this.codPres = bd.obtenerCodMaximoRegistro("presupuestos", "cod_presupuesto");
        this.txtCodCliente.requestFocus();
    }
    
    public void setCliente(String codigo, String cliente, String ruc) {
        this.txtCodCliente.setText(codigo);
        this.txtNomCliente.setText(cliente);
        this.txtRucCliente.setText(ruc);
        
    }
    
    public void setProducto(String codigo, String nombre, String precio, String iva) {
        this.txtCodProducto.setText(codigo);
        this.txtDescripcionProducto.setText(nombre);
        this.txtPrecioProducto.setText(precio);
        bd.seleccionarItemCombo(this.cboIva, iva);
        this.calcularTotal();
        this.btnAgregarProducto.setEnabled(true);
        this.btnAgregarProducto.requestFocus();
    }
    
    private void limpiarCampos(){
        this.txtCodigo.setText(null);
        this.txtCodCliente.setText(null);
        this.txtRucCliente.setText(null);
        this.txtNomCliente.setText(null);
        this.lblPendiente.setIcon(null);
        this.dateFecha.setDate(null);
        this.lblUsuarioCreacion.setText(null);
        this.lblUsuarioModificacion.setText(null);
        this.lblFechaCreacion.setText(null);
        this.lblFechaModificacion.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) this.grdDetalle.getModel();
        modelo.setRowCount(0);
        this.txtCodProducto.setText(null);
        this.txtDescripcionProducto.setText(null);
        this.txtCantProducto.setText("1");
        this.txtDescuento.setText("0");
        this.txtPrecioProducto.setText(null);
        this.txtTotal.setText(null);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTA");
        bd.agregarItemCombo(cboIva, 1, "5%");
        bd.agregarItemCombo(cboIva, 2, "10%");
        this.cboIva.setSelectedIndex(2);
        this.btnAgregarProducto.setEnabled(false);
        this.btnEliminarProducto.setEnabled(false);
        this.txtIva5.setText(null);
        this.txtIva10.setText(null);
        this.txtExentas.setText(null);
        this.txtTotalIva.setText(null);
        this.txtTotalGravado.setText(null);
        this.txtTotalGeneral.setText(null);
        this.txtTotalGeneral1.setText(null);
        this.txtCodigo.setText(null); 
        this.txtRucCliente.requestFocus();
        this.btnAnular.setEnabled(false);        
    }
    
    private void limpiarCamposDetalle(){
        this.txtCodProducto.setText(null);
        this.txtDescripcionProducto.setText(null);
        this.txtPrecioProducto.setText(null);
        this.txtCantProducto.setText("1");
        this.txtDescuento.setText("0");
        this.txtTotal.setText(null);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTA");
        bd.agregarItemCombo(cboIva, 1, "5%");
        bd.agregarItemCombo(cboIva, 2, "10%");
        this.cboIva.setSelectedIndex(2);
    }
        
    private void habilitarCampos(boolean estado){
        this.txtCodCliente.setEditable(estado);
        this.btnBuscarCliente.setEnabled(estado);
        this.dateFecha.setFocusable(estado);
        this.btnBuscarProducto.setEnabled(estado);
        this.txtCodProducto.setEditable(estado);
        this.txtCantProducto.setEditable(estado);
        this.txtDescuento.setEditable(estado);
        this.btnGuardar.setEnabled(estado);
        this.btnImprimir.setVisible(!estado);
        this.btnFacturarPresupuesto.setVisible(!estado);
        
    }    
    
    private void agregarProducto(){
        JTable tabla = this.grdDetalle;
        int filas = this.grdDetalle.getRowCount();

            String codigo = txtCodProducto.getText();
            String nombre = txtDescripcionProducto.getText();
            String cantidad = txtCantProducto.getText();
            String iva = cboIva.getSelectedItem().toString();
            String total = txtTotal.getText();
            boolean existe = false;

            if(!codigo.isEmpty()){
                if(iva == "EXENTA"){
                    agregarFilaDesdeCampos(codigo, nombre, cantidad, total, "", "");
                    limpiarCamposDetalle();
                    txtCodProducto.requestFocus();
                }else{
                    if(iva == "5%"){
                        agregarFilaDesdeCampos(codigo, nombre, cantidad, "", total, "");
                        limpiarCamposDetalle();
                        txtCodProducto.requestFocus();
                    }else{
                        agregarFilaDesdeCampos(codigo, nombre, cantidad, "", "", total);
                        limpiarCamposDetalle();
                        txtCodProducto.requestFocus();
                    }
                }
            }
            
    }
    
    private void agregarFilaDesdeCampos(String codigo, String nombre, String cantidad, String exentas, String iva5, String iva10) {
        DefaultTableModel modelo = (DefaultTableModel) grdDetalle.getModel();
        Object[] fila = {codigo, nombre, cantidad, exentas, iva5, iva10};
        modelo.addRow(fila);
        this.calcularSubTotales();
        gr.alinearDerecha(grdDetalle, 5);
        gr.alinearDerecha(grdDetalle, 4);
        gr.alinearDerecha(grdDetalle, 3);
    }
        
    private void calcularTotal(){
        float cantidad = Float.parseFloat(this.txtCantProducto.getText());
        float precio = Float.parseFloat(this.txtPrecioProducto.getText());
        float descuento = Float.parseFloat(this.txtDescuento.getText());
        float total = 0;
        if(cantidad > 0){
            total = cantidad * precio;
            if(descuento > 0){
                if(descuento < total ){
                    total = total - descuento;
                    this.txtTotal.setText(String.valueOf(total));
                }else{
                    this.txtTotal.setText(String.valueOf(total));
                    this.txtDescuento.setText("0");
                }
            }else{
                this.txtTotal.setText(String.valueOf(total));
            }
        }
    }
    
    private void calcularSubTotales() {
        JTable tabla = this.grdDetalle;
        int filas = tabla.getRowCount();
        float iva5 = 0;
        float iva10 = 0;
        float exenta = 0;
        float totalIva = 0;
        float gravado = 0;
        float totalGeneral = 0;

        if (filas > 0) {
            for (int x = 0; x < filas; x++) {
                // Verificar si el valor en la columna 3 es válido
                if (!tabla.getValueAt(x, 3).toString().isEmpty()) {
                    exenta += Float.parseFloat(tabla.getValueAt(x, 3).toString());
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 3).toString());
                }

                // Verificar si el valor en la columna 4 es válido
                if (!tabla.getValueAt(x, 4).toString().isEmpty()) {
                    iva5 += (Float.parseFloat(tabla.getValueAt(x, 4).toString())) / 21;
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 4).toString());
                }

                // Verificar si el valor en la columna 5 es válido
                if (!tabla.getValueAt(x, 5).toString().isEmpty()) {
                    iva10 += (Float.parseFloat(tabla.getValueAt(x, 5).toString())) / 11;
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 5).toString());
                }
            }

            totalIva = iva5 + iva10;
            gravado = totalGeneral - totalIva;
            this.txtIva5.setText(bd.agregarSeparadorMiles(String.format("%.0f", iva5)));
            this.txtIva10.setText(bd.agregarSeparadorMiles(String.format("%.0f", iva10)));
            this.txtExentas.setText(bd.agregarSeparadorMiles(String.format("%.0f", exenta)));
            this.txtTotalIva.setText(bd.agregarSeparadorMiles(String.format("%.0f", totalIva)));
            this.txtTotalGravado.setText(bd.agregarSeparadorMiles(String.format("%.0f", gravado)));
            this.txtTotalGeneral.setText(bd.agregarSeparadorMiles(String.format("%.0f", totalGeneral))); // Cambié para usar el campo correcto
            this.txtTotalGeneral1.setText(String.format("%.0f", totalGeneral)); // Cambié para usar el campo correcto
        } else {
            // Si no hay filas, limpiar los campos
            this.txtIva5.setText(null);
            this.txtIva10.setText(null);
            this.txtExentas.setText(null);
            this.txtTotalIva.setText(null);
            this.txtTotalGravado.setText(null);
            this.txtTotalGeneral.setText(null);
            this.txtTotalGeneral1.setText(null);
        }
    }
   
    private void guardarDetalle(){
        JTable tabla = this.grdDetalle;
        int filas = tabla.getRowCount();
        
        String campos = "cod_presupuesto_detalle, "
                + "cod_presupuesto_cabecera, "
                + "cod_producto, "
                + "precio_producto, "
                + "cantidad_producto, "
                + "iva_producto";

        for(int x = 0; x < filas; x++){
            String codPresupuestoD = bd.obtenerCodMaximoRegistro("presupuesto_detalle", "cod_presupuesto_detalle");
            String codProducto = tabla.getValueAt(x, 0).toString();
            String cantProducto = tabla.getValueAt(x, 2).toString();
            String precProducto;
            String ivaProducto;
            if(!tabla.getValueAt(x, 3).toString().isEmpty()){
                precProducto = tabla.getValueAt(x, 3).toString();
                ivaProducto = "EXENTA";
            }else{
                if(!tabla.getValueAt(x, 4).toString().isEmpty()){
                    precProducto = tabla.getValueAt(x, 4).toString();
                    ivaProducto = "5%";
                }else{
                    precProducto = tabla.getValueAt(x, 5).toString();
                    ivaProducto = "10%";
                }
            }
            String Valores = "" + codPresupuestoD + "," 
                    + this.codPres + "," 
                    + codProducto + ","
                    + precProducto + "," 
                    + cantProducto + ",'" 
                    + ivaProducto + "'";
            bd.insertarRegistro("presupuesto_detalle", campos, Valores);
        }
        JOptionPane.showMessageDialog(null, "PRESUPUESTO GUARDADO");
        this.Anterior(-1);
    }
    
    private void Anterior(int a){
        int codigo = codigo = Integer.parseInt(this.codPres) - 1 - a;
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
        this.accion = "anterior";
        String sql = "select * from presupuestos where cod_presupuesto = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos == null){
            if(codigo >= 0){
                Anterior(a + 1);    
            }
        }else{
            this.mostrarPresupuesto(datos[0]);
            this.codPres = String.valueOf(codigo);
        }
    }
    
    private void Posterior(int a){
        int codigo  = Integer.parseInt(this.codPres) + 1 + a;         
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
        String sql = "select * from presupuestos where cod_presupuesto = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        
        if(datos != null){
            this.mostrarPresupuesto(datos[0]);
            this.codPres = String.valueOf(codigo);  
        }
    }
    
    private void mostrarPresupuesto(String codPresupuesto){
        System.out.println(codPresupuesto);
        this.codPres = codPresupuesto;
        String sql = 
                "SELECT * FROM presupuestos where cod_presupuesto = " + codPresupuesto ;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos != null){
            this.txtCodigo.setText(datos[0]);
            this.txtCodCliente.setText(datos[1]);
            String [] cliente = bd.obtenerRegistro("select ruc_cliente, concat(nombre_cliente,', ',apellido_cliente) from clientes where cod_cliente =" + datos[1]);
            this.txtRucCliente.setText(cliente[0]);
            this.txtNomCliente.setText(cliente[1]);
            bd.mostrarFechaJDateChooser(dateFecha, datos[2]);
            if(datos[3].equals("ACTIVO")){
                Icon pendiente = new ImageIcon(getClass().getResource("/iconos/rojo.png"));
                this.lblPendiente.setIcon(pendiente);
            }else{
                this.lblPendiente.setIcon(null);
            }
            this.lblUsuarioCreacion.setText(datos[5]);
            this.lblUsuarioModificacion.setText(datos[6]);
            this.lblFechaCreacion.setText(datos[7]);
            this.lblFechaModificacion.setText(datos[8]);
            DefaultTableModel modelo = (DefaultTableModel) grdDetalle.getModel();
            modelo.setRowCount(0);
            this.obtenerDetalle(Integer.parseInt(codPresupuesto));
        }else{
            JOptionPane.showMessageDialog(null, "NO HAY DATOS");
        }
    }
    
    private void obtenerDetalle(int codPresupuesto){
        System.out.println("codPresupuesto " + codPresupuesto);
        String sql =    "SELECT pd.cod_producto,\n" +
                        "p.nombre_producto, \n" +
                        "pd.cantidad_producto, \n" +
                        "pd.precio_producto, \n" +
                        "pd.iva_producto \n" +
                        "FROM presupuesto_detalle pd \n" +
                        "JOIN productos p ON pd.cod_producto = p.cod_producto\n" +
                        "WHERE pd.cod_presupuesto_cabecera =" + codPresupuesto;
        String [][] datos = bd.obtenerRegistros(sql);
        if(datos != null){
            for(int x = 0; x < datos.length; x++){
                String codigo = null;
                String nombre = null;
                float cantidad = 0;
                float precio = 0;  
                String iva = null;
                float total;
                for(int i = 0; i < datos[x].length; i++){
                    codigo = datos[x][0];
                    nombre = datos[x][1];
                    cantidad = Float.parseFloat(datos[x][2]);
                    precio = Float.parseFloat(datos[x][3]);
                    iva = datos[x][4];
                }
                if(iva.equals("EXENTA")){
                    total = cantidad * precio; 
                    this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), String.format("%.0f", total), "", "");
                }else{
                    if(iva.equals("5%")){
                        total = cantidad * precio;
                        this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), "", String.format("%.0f", total), "");
                    }else{
                        total = cantidad * precio;
                        this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), "", "", String.format("%.0f", total));
                    }
                }   

            }
        }else{
            JOptionPane.showMessageDialog(null, "NO HAY DETALLE DE PRESUPUESTO");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        btnAnterior = new javax.swing.JToggleButton();
        btnSiguiente = new javax.swing.JToggleButton();
        btnNuevo = new javax.swing.JToggleButton();
        txtCodigo = new javax.swing.JTextField();
        txtCodCliente = new javax.swing.JTextField();
        txtNomCliente = new javax.swing.JTextField();
        txtRucCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        btnImprimir = new javax.swing.JToggleButton();
        btnGuardar = new javax.swing.JToggleButton();
        btnAnular = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        btnInforme = new javax.swing.JToggleButton();
        lblPendiente = new javax.swing.JLabel();
        btnFacturarPresupuesto = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        btnBuscarProducto = new javax.swing.JButton();
        txtCodProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCantProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        cboIva = new javax.swing.JComboBox<>();
        txtTotal = new javax.swing.JTextField();
        btnAgregarProducto = new javax.swing.JToggleButton();
        btnEliminarProducto = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdDetalle = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lblDescripcionProducto = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblCodProducto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtExentas = new javax.swing.JTextField();
        txtIva5 = new javax.swing.JTextField();
        txtIva10 = new javax.swing.JTextField();
        txtTotalIva = new javax.swing.JTextField();
        txtTotalGravado = new javax.swing.JTextField();
        txtTotalGeneral = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTotalGeneral1 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(51, 102, 255));
        panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/anterior.png"))); // NOI18N
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        panel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 30));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/siguiente.png"))); // NOI18N
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        panel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 40, 30));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        panel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 40, 30));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        panel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 90, 30));

        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClienteFocusLost(evt);
            }
        });
        panel1.add(txtCodCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, 30));

        txtNomCliente.setEditable(false);
        txtNomCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtNomCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNomCliente.setFocusable(false);
        panel1.add(txtNomCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 350, 30));

        txtRucCliente.setEditable(false);
        txtRucCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRucCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRucCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRucClienteFocusLost(evt);
            }
        });
        panel1.add(txtRucCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 130, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Fecha");
        panel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cidigo");
        panel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 40, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Nombre o Razon Social");
        panel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 140, 30));

        dateFecha.setBackground(new java.awt.Color(255, 255, 255));
        dateFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateFecha.setDateFormatString("dd-MM-yyyy");
        dateFecha.setFocusable(false);
        panel1.add(dateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 140, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("RUC/CI");
        panel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 50, 30));

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });
        panel1.add(btnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 30, 30));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        panel1.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 10, 40, 40));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 10, 40, 40));

        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        panel1.add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 10, 40, 40));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        panel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 40, 40));

        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        panel1.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 10, 40, 40));
        panel1.add(lblPendiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 70, 70));

        btnFacturarPresupuesto.setText("Facturar");
        btnFacturarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarPresupuestoActionPerformed(evt);
            }
        });
        panel1.add(btnFacturarPresupuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 60, 140, 30));

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1270, 130));

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, 30));

        txtCodProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCodProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProductoFocusLost(evt);
            }
        });
        txtCodProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProductoActionPerformed(evt);
            }
        });
        jPanel4.add(txtCodProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 70, 30));

        txtDescripcionProducto.setEditable(false);
        txtDescripcionProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescripcionProducto.setFocusable(false);
        jPanel4.add(txtDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 440, 30));

        txtCantProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCantProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantProductoFocusLost(evt);
            }
        });
        jPanel4.add(txtCantProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 80, 30));

        txtPrecioProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPrecioProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioProductoFocusLost(evt);
            }
        });
        jPanel4.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 120, 30));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescuento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescuentoFocusLost(evt);
            }
        });
        jPanel4.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, 140, 30));

        cboIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXENTAS", "5%", "10%" }));
        cboIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(cboIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 30, -1, 30));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 120, 30));

        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnAgregarProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAgregarProductoFocusGained(evt);
            }
        });
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 30, 35, 30));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 30, 35, 30));

        grdDetalle.setBackground(new java.awt.Color(0, 153, 255));
        grdDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Cantidad", "Exentas", "IVA 5%", "IVA 10%"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdDetalle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        grdDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdDetalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdDetalle);
        if (grdDetalle.getColumnModel().getColumnCount() > 0) {
            grdDetalle.getColumnModel().getColumn(0).setMaxWidth(200);
            grdDetalle.getColumnModel().getColumn(2).setMinWidth(100);
            grdDetalle.getColumnModel().getColumn(2).setPreferredWidth(100);
            grdDetalle.getColumnModel().getColumn(2).setMaxWidth(200);
            grdDetalle.getColumnModel().getColumn(3).setMinWidth(100);
            grdDetalle.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdDetalle.getColumnModel().getColumn(3).setMaxWidth(400);
            grdDetalle.getColumnModel().getColumn(4).setMinWidth(100);
            grdDetalle.getColumnModel().getColumn(4).setPreferredWidth(150);
            grdDetalle.getColumnModel().getColumn(4).setMaxWidth(400);
            grdDetalle.getColumnModel().getColumn(5).setMinWidth(100);
            grdDetalle.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdDetalle.getColumnModel().getColumn(5).setMaxWidth(400);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 69, 1250, 306));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Total");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 10, -1, 20));

        lblDescripcionProducto.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblDescripcionProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcionProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescripcionProducto.setText("Descripcion");
        jPanel4.add(lblDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 110, 20));

        lblCantidad.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("Cantidad");
        jPanel4.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 60, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Precio");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 40, 20));

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Descuento");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 70, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("IVA");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 30, 20));

        lblCodProducto.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblCodProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblCodProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodProducto.setText("Codigo");
        jPanel4.add(lblCodProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 50, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 1272, 380));

        jPanel5.setBackground(new java.awt.Color(51, 102, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtExentas.setEditable(false);
        txtExentas.setBackground(new java.awt.Color(255, 255, 255));
        txtExentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtExentas.setFocusable(false);
        jPanel5.add(txtExentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        txtIva5.setEditable(false);
        txtIva5.setBackground(new java.awt.Color(255, 255, 255));
        txtIva5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIva5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIva5.setFocusable(false);
        jPanel5.add(txtIva5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 141, 30));

        txtIva10.setEditable(false);
        txtIva10.setBackground(new java.awt.Color(255, 255, 255));
        txtIva10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIva10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIva10.setFocusable(false);
        jPanel5.add(txtIva10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 140, 30));

        txtTotalIva.setEditable(false);
        txtTotalIva.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalIva.setFocusTraversalPolicyProvider(true);
        txtTotalIva.setFocusable(false);
        jPanel5.add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 140, 30));

        txtTotalGravado.setEditable(false);
        txtTotalGravado.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGravado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGravado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalGravado.setFocusable(false);
        jPanel5.add(txtTotalGravado, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 140, 30));

        txtTotalGeneral.setEditable(false);
        txtTotalGeneral.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGeneral.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalGeneral.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalGeneral.setFocusable(false);
        jPanel5.add(txtTotalGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, 250, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("TOTAL");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, 90, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Exentas");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 50, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("5%");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 50, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("10%");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 50, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Total IVA");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 60, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Total Gravadas");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 90, -1));

        txtTotalGeneral1.setEditable(false);
        txtTotalGeneral1.setBackground(new java.awt.Color(51, 102, 255));
        txtTotalGeneral1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalGeneral1.setForeground(new java.awt.Color(51, 102, 255));
        txtTotalGeneral1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGeneral1.setBorder(null);
        txtTotalGeneral1.setFocusable(false);
        jPanel5.add(txtTotalGeneral1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 30, 30));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 1272, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1289, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String codCompra = this.codPres;
        String codCli = this.txtCodCliente.getText();   
        String fecha = bd.formatoFecha(this.dateFecha);
        int items = this.grdDetalle.getRowCount();
        String total= this.txtTotalGeneral1.getText();
        
        if(this.accion.equals("nuevo")){
            if(!codCli.isEmpty()){
                if(this.dateFecha.getDate() != null){
                        if(items > 0){
                            if(items <= 10){
                                
                                String campos = "cod_presupuesto, "
                                        + "cod_cliente,"
                                        + "fecha_presupuesto,"
                                        + "estado_presupuesto,"
                                        + "usuario_insercion,"
                                        + "fecha_insercion";
                                String valores = this.codPres + ","
                                        + codCli + ","
                                        + "'" + fecha + "',"
                                        + "'ACTIVO',"
                                        + "'" + usuarioLogeado + "',"
                                        + "NOW()";
                                
                                bd.insertarRegistro("presupuestos", campos, valores);
                                this.guardarDetalle();
                            }else{
                                JOptionPane.showMessageDialog(null, "No se pueden cargar mas de 10 articulos por presupuesto");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debes cargar un producto");
                            this.txtCodProducto.requestFocus();
                        }           
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese la fecha");
                    this.dateFecha.requestFocus();
                }                    
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione al Cliente");
                this.btnBuscarCliente.requestFocus();
            }                    
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void grdDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdDetalleMouseClicked
        if(this.grdDetalle.getSelectedRow()>=0 && accion == "nuevo"){
            this.btnEliminarProducto.setEnabled(true);
        }else{
            this.btnEliminarProducto.setEnabled(false);
        }
    }//GEN-LAST:event_grdDetalleMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        if(this.txtCodigo.getText().isEmpty()){
            this.Anterior(0); 
        }else{
            int txtcodigo = Integer.parseInt( this.txtCodigo.getText())-1;
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("presupuestos", "cod_presupuesto")) - 1;      
            if(txtcodigo < codigo && txtcodigo > 0){
                this.mostrarPresupuesto(String.valueOf(txtcodigo));      
            }
        }
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        if(this.txtCodigo.getText().isEmpty()){
            this.Posterior(0); 
        }else{
            int txtcodigo = Integer.parseInt( this.txtCodigo.getText())+1;
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("presupuestos", "cod_presupuesto"));     
            if(txtcodigo < codigo && txtcodigo > 0){
                this.mostrarPresupuesto(String.valueOf(txtcodigo));
            }
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        String codigo = this.txtCodigo.getText();
        String [] datos = bd.obtenerRegistro("Select estado_presupuesto, num_facturacion from presupuestos where cod_presupuesto = " + codigo);
        int confirmacion = JOptionPane.showConfirmDialog(null, 
        "¿Estás seguro de que deseas eliminar este registro?", 
        "Confirmación de Eliminación", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE);
        
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if(datos[0].equals("ACTIVO")){//SI ES INACTIVO SIGNIFICA QUE YA FUE FACTURADO
                JTable tabla = this.grdDetalle;
                int filas = tabla.getRowCount();
                
                for (int x = 0; x < filas; x++) {
                    String codDetalle = tabla.getValueAt(x, 0).toString();
                    bd.borrarRegistro("presupuesto_detalle", "cod_presupuesto_cabecera=" + codigo);
                }
                bd.borrarRegistro("presupuestos", "cod_presupuesto=" + codigo);
                JOptionPane.showMessageDialog(  null, "PRESUPUESTO ELIMINADO");
                this.nuevo();
            }else{
                JOptionPane.showMessageDialog(null, "ESTE PRESUPUESTO NO SE PUEDE BORRAR POR QUE YA FUE FACTURA - MOVIMIENTO NUMERO: " + datos[2]);
            }
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtRucClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRucClienteFocusLost

    }//GEN-LAST:event_txtRucClienteFocusLost

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        if(accion.equals("nuevo")){                  
            String tabla = "productos p";
            String [] cabeceras = {"Codigo", "Producto", "Marca", "Precio", "Existencia", "IVA"};
            String [] campos = {"p.cod_producto", "p.nombre_producto", "m.nombre_marca", "p.precio_producto", "p.existencia_producto", "p.iva_producto"};
            String condicion = " join marcas m on p.cod_marca = m.cod_marca where p.estado_producto='ACTIVO'";
            int [] ancho = {200,500,300,300,200,200};
            JDBuscador fbp = new JDBuscador(this, true, this, tabla, campos, condicion, cabeceras, ancho);
            fbp.setVisible(true);  
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtCodProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProductoFocusLost
        if(!this.txtCodProducto.getText().isEmpty()){              
            String sql = "Select cod_producto,nombre_producto,precio_producto,iva_producto from productos "
            + "where cod_producto = "+ this.txtCodProducto.getText() + " AND estado_producto='ACTIVO'";
            
            if(bd.obtenerRegistro(sql) != null){
                String [] datos = Arrays.copyOf(bd.obtenerRegistro(sql), bd.obtenerRegistro(sql).length);
                this.txtDescripcionProducto.setText(datos[1]);
                this.txtPrecioProducto.setText(datos[2]);
                bd.seleccionarItemCombo(this.cboIva, datos[3]);
                this.calcularTotal();
                this.btnAgregarProducto.setEnabled(true);
            }else{
                this.limpiarCamposDetalle();
            }
        }
    }//GEN-LAST:event_txtCodProductoFocusLost

    private void txtCodProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProductoActionPerformed

    private void txtCantProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantProductoFocusLost
        if(this.accion.equals("nuevo")){
            if(this.txtCantProducto.getText().isEmpty()){
                this.txtCantProducto.setText("1");
                this.txtPrecioProducto.requestFocus();
            }else{
                if(!this.txtPrecioProducto.getText().isEmpty()){
                    this.calcularTotal();
                }
            }
        }
    }//GEN-LAST:event_txtCantProductoFocusLost

    private void txtDescuentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescuentoFocusLost
        if(this.accion.equals("nuevo")){
            if(this.txtDescuento.getText().isEmpty()){
                this.txtDescuento.setText("0");
            }else{
                if(!this.txtPrecioProducto.getText().isEmpty()){
                    this.calcularTotal();
                    this.btnAgregarProducto.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txtDescuentoFocusLost

    private void btnAgregarProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAgregarProductoFocusGained

    }//GEN-LAST:event_btnAgregarProductoFocusGained

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        if(this.accion.equals("nuevo")){
            if(!this.txtPrecioProducto.getText().isEmpty()){
                this.calcularTotal();
                this.agregarProducto();
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese el precio");
            }
        }
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        int fila = this.grdDetalle.getSelectedRow();
        if (fila != -1) { // Verificar que se haya seleccionado una fila
            DefaultTableModel model = (DefaultTableModel) this.grdDetalle.getModel(); // Obtener el modelo de la tabla
            model.removeRow(fila); // Eliminar la fila seleccionada
            this.calcularSubTotales();
        }    
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void txtCodClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClienteFocusLost
        String codigo = this.txtCodCliente.getText();
        if(!codigo.isEmpty()){
            String [] cliente = bd.obtenerRegistro("select concat(nombre_cliente,' ', apellido_cliente), ruc_cliente  from clientes where cod_cliente=" + codigo);
            if(cliente != null){
                this.txtNomCliente.setText(cliente[0]);
                this.txtRucCliente.setText(cliente[1]);
            }
        }
    }//GEN-LAST:event_txtCodClienteFocusLost

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        if(accion.equals("nuevo")){
            String tabla = "clientes";
            String [] campos = {"cod_cliente", "concat(nombre_cliente,' ', apellido_cliente)", "ruc_cliente", "telefono_cliente"};
            String [] cabeceras = {"Codigo", "Cliente", "RUC", "Telefono"};
            int [] ancho = {200,500,300,300};
            JDBuscador fb = new JDBuscador(this, true, this, tabla, campos, "",cabeceras, ancho);
            fb.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            fb.setVisible(true);
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        String url = "/reportes/reportePresupuestoDetalle.jasper";
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        String cod = this.txtCodigo.getText();                        

        String [] datos = bd.obtenerRegistro(   "select " + 
                                                "fecha_presupuesto, \n" +
                                                "cl.ruc_cliente, \n" +
                                                "CONCAT(cl.apellido_cliente, ', ', cl.nombre_cliente) AS cliente, \n" +
                                                "SUM(pd.cantidad_producto*pd.precio_producto) AS monto\n" +
                                                "from presupuestos pr \n" +
                                                "JOIN clientes cl ON pr.cod_cliente=cl.cod_cliente\n" +
                                                "JOIN presupuesto_detalle pd ON pr.cod_presupuesto=pd.cod_presupuesto_cabecera\n" +
                                                "where pr.cod_presupuesto=" + cod);
        if(datos != null){
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
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        FrmInformePresupuestos iv = new FrmInformePresupuestos(this.usuarioLogeado, this.rol, "TODOS", "TODOS LOS PRESUPUESTOS");
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_btnInformeActionPerformed

    private void txtPrecioProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioProductoFocusLost

    }//GEN-LAST:event_txtPrecioProductoFocusLost

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        if(!this.txtCodigo.getText().isEmpty()){
            int cod = Integer.parseInt(this.txtCodigo.getText());
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("presupuestos", "cod_presupuesto")); 
            if(cod < codigo && cod > 0){
                    this.mostrarPresupuesto(String.valueOf(cod));  
                    this.codPres = String.valueOf(cod);
            }else{
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO EL REGISTRO");
                this.nuevo();
            }
        }
    }//GEN-LAST:event_txtCodigoFocusLost

    private void btnFacturarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarPresupuestoActionPerformed
        String cod = this.txtCodigo.getText();
        String [] presupuesto = bd.obtenerRegistro("select estado_presupuesto, num_facturacion from presupuestos where cod_presupuesto =" + cod);
        if(presupuesto[0].equals("ACTIVO")){
            FrmVentas fr = new FrmVentas(usuarioLogeado, this.rol, cod);
            fr.setDefaultCloseOperation(HIDE_ON_CLOSE);
            fr.setVisible(true);            
        }else{
            JOptionPane.showMessageDialog(null, "ESTE PRESUPUESTO YA ESTA FACTURADO - MOVIMIENTO NUMERO: " + presupuesto[1]);
        }
    }//GEN-LAST:event_btnFacturarPresupuestoActionPerformed

    
    
   
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmPresupuesto dialog = new FrmPresupuesto("VLOPEZ","ADMIN");
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
    private javax.swing.JToggleButton btnAgregarProducto;
    private javax.swing.JToggleButton btnAnterior;
    private javax.swing.JToggleButton btnAnular;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JToggleButton btnEliminarProducto;
    private javax.swing.JToggleButton btnFacturarPresupuesto;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JToggleButton btnImprimir;
    private javax.swing.JToggleButton btnInforme;
    private javax.swing.JToggleButton btnNuevo;
    private javax.swing.JToggleButton btnSiguiente;
    private javax.swing.JComboBox<String> cboIva;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JTable grdDetalle;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCodProducto;
    private javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblPendiente;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JPanel panel1;
    private javax.swing.JTextField txtCantProducto;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodProducto;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtExentas;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    private javax.swing.JTextField txtNomCliente;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtRucCliente;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalGeneral;
    private javax.swing.JTextField txtTotalGeneral1;
    private javax.swing.JTextField txtTotalGravado;
    private javax.swing.JTextField txtTotalIva;
    // End of variables declaration//GEN-END:variables
}
