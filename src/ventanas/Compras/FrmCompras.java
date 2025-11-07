
package ventanas.Compras;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDatos1;
import javax.swing.JTable;
import servicios.Fechas;
import servicios.Grilla;
import ventanas.principal.FrmAjustes;

public class FrmCompras extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    Grilla gr = new Grilla();
    Fechas fe = new Fechas();
    FrmAjustes aj = new FrmAjustes();
    String usuarioLogeado;
    String rol="admin";
    String accion = "nuevo";
    
    public FrmCompras() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.restricciones();
        this.nuevo();
    }
    
    public FrmCompras(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("VENTANA COMPRAS - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;        
        this.nuevo();
        this.restricciones();
    }
    
    
    private void restricciones(){
        this.dateFechaComprobante.setMaxSelectableDate(new java.util.Date());
        aj.presionarEnter(cboTipoMto, btnBuscarProveedor);
        aj.presionarEnter(dateFechaComprobante, txtTimbrado);
        aj.presionarEnter(txtTimbrado, txtNumComprobante);
        aj.presionarEnter(txtNumComprobante, txtCodProducto);
        aj.enterActionPerformed(btnBuscarProveedor);
        aj.enterActionPerformed(btnAgregarProducto);
        aj.presionarEnter(txtCodProducto, txtCantProducto);
        aj.presionarEnter(txtCantProducto, txtPrecioProducto);
        aj.presionarEnter(txtPrecioProducto, btnAgregarProducto);
        aj.soloNumeros(txtTimbrado, 10);
        aj.numComprobante(txtNumComprobante);
        aj.soloNumeros(txtCodProducto, 5);
        aj.soloNumeros(txtPrecioProducto, 10);
        aj.soloNumeros(txtCantProducto, 5);
        txtDescuento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isISOControl(c)) {
                    e.consume(); // Evita que el carácter se escriba en el campo
                }
            }
        });
    }    
    public void setProveedor(String codigo, String proveedor, String ruc){
        this.txtCodProveedor.setText(codigo);
        this.txtRucProveedor.setText(ruc);
        if(!bd.seleccionarItemComboBD(cboProveedores, Integer.parseInt(codigo))){
            bd.agregarItemCombo(cboProveedores, Integer.parseInt(codigo), proveedor);
        }
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
    private void nuevo(){
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("compras_cabecera", "cod_compra_cabecera"));
        bd.VaciarCombo(cboProveedores);
        bd.agregarItemCombo(cboProveedores, -1, "INDEFINIDO");
        bd.llenarComboHasta20(cboProveedores, "cod_proveedor,nombre_proveedor", "proveedores where estado_proveedor='ACTIVO'");
        bd.VaciarCombo(cboTipoMto);
        bd.agregarItemCombo(cboTipoMto, 0, "COMPRA CONTADO");
        bd.agregarItemCombo(cboTipoMto, 1, "COMPRA CREDITO");
        bd.agregarItemCombo(cboTipoMto, 2, "PAGO CUOTA");
        this.txtCodProveedor.setText(null);
        this.cboProveedores.setSelectedIndex(0);
        this.cboTipoMto.setSelectedIndex(0);
        this.dateFechaComprobante.setDate(null);
        this.btnCredito.setVisible(false);
        this.noEsPagoCuota();
        this.txtTimbrado.setText(null);
        this.txtNumComprobante.setText(null);
        this.lblUsuarioCreacion.setText(null);
        this.lblUsuarioModificacion.setText(null);
        this.lblFechaCreacion.setText(null);
        this.lblFechaModificacion.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) this.grdDetalleCompra.getModel();
        modelo.setRowCount(0);
        this.limpiarCamposDetalle();
        this.btnAgregarProducto.setEnabled(false);
        this.btnEliminarProducto.setEnabled(false);
        this.txtIva5.setText(null);
        this.txtIva10.setText(null);
        this.txtExentas.setText(null);
        this.txtTotalIva.setText(null);
        this.txtTotalGravado.setText(null);
        this.txtTotalGeneral.setText(null); 
        this.txtTotalGeneral1.setText(null); 
        this.btnBuscarProveedor.requestFocus();
        this.btnAnular.setEnabled(false);
        this.cboEstadoCompra.setVisible(false);
    }
    private void limpiarCamposDetalle(){
        this.txtCodProducto.setText(null);
        this.txtDescripcionProducto.setText(null);
        this.txtPrecioProducto.setText(null);
        this.txtCantProducto.setText("1");
        this.txtDescuento.setText("0");
        this.txtTotal.setText(null);
        this.cboIva.setSelectedIndex(2);
        this.btnAgregarProducto.setEnabled(false);
    }
    private void habilitarCampos(boolean estado){
        this.txtTimbrado.setEditable(estado);
        this.dateFechaComprobante.setEnabled(estado);
        this.btnBuscarProveedor.setEnabled(estado);
        this.btnBuscarProducto.setEnabled(estado);
        this.txtCodProducto.setEditable(estado);
        this.txtCantProducto.setEditable(estado);
        this.cboIva.setEnabled(estado);
        this.txtDescuento.setEditable(estado);
        this.btnGuardar.setEnabled(estado);
        
    }    
    private void agregarProducto(){
        JTable tabla = this.grdDetalleCompra;
        int filas = tabla.getRowCount();
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String codigo = txtCodProducto.getText();
        String nombre = txtDescripcionProducto.getText();
        String cantidad = txtCantProducto.getText();
        float precio = Float.parseFloat(this.txtPrecioProducto.getText());
        String iva = cboIva.getSelectedItem().toString();
        String total = txtTotal.getText();
        boolean existe = false;
        
        if(tipoMto.equals("PAGO CUOTA")){
            if(filas > -1){
                for (int x = 0; x < filas; x++) {
                    String codigoCuota = this.grdDetalleCompra.getValueAt(x, 0).toString();
                    if(codigoCuota.equals(codigo)){  
                        existe = true;
                        System.out.println(codigoCuota + " - " + codigo + " - " + existe);
                    }
                }
                if(!existe){
                    agregarFilaDesdeCampos(codigo, nombre, cantidad, precio, "", "", total);
                    limpiarCamposDetalle();
                    txtCodProducto.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null, "No puedes agregar la misma cuota dos veces");
                }
            }else{
                agregarFilaDesdeCampos(codigo, nombre, cantidad, precio, "", "", total);
                limpiarCamposDetalle();
                txtCodProducto.requestFocus();
            }
        }else{
            if(!codigo.isEmpty()){
                if(iva == "EXENTAS"){
                    agregarFilaDesdeCampos(codigo, nombre, cantidad, precio, total, "", "");
                    limpiarCamposDetalle();
                    txtCodProducto.requestFocus();
                }else{
                    if(iva == "5%"){
                        agregarFilaDesdeCampos(codigo, nombre, cantidad, precio, "", total, "");
                        limpiarCamposDetalle();
                        txtCodProducto.requestFocus();
                    }else{
                        agregarFilaDesdeCampos(codigo, nombre, cantidad, precio, "", "", total);
                        limpiarCamposDetalle();
                        txtCodProducto.requestFocus();
                    }
                }                                
            }
        }
    }
    private void agregarFilaDesdeCampos(String codigo, String nombre, String cantidad, float precio, String exentas, String iva5, String iva10) {
        DefaultTableModel modelo = (DefaultTableModel) grdDetalleCompra.getModel();
        Object[] fila = {codigo, nombre, cantidad, precio, exentas, iva5, iva10};
        modelo.addRow(fila);
        this.calcularSubTotales();
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
        JTable tabla = this.grdDetalleCompra;
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
                if (!tabla.getValueAt(x, 4).toString().isEmpty()) {
                    exenta += Float.parseFloat(tabla.getValueAt(x, 4).toString());
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 4).toString());
                }

                // Verificar si el valor en la columna 4 es válido
                if (!tabla.getValueAt(x, 5).toString().isEmpty()) {
                    iva5 += (Float.parseFloat(tabla.getValueAt(x, 5).toString())) / 21;
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 5).toString());
                }

                // Verificar si el valor en la columna 5 es válido
                if (!tabla.getValueAt(x, 6).toString().isEmpty()) {
                    iva10 += (Float.parseFloat(tabla.getValueAt(x, 6).toString())) / 11;
                    totalGeneral += Float.parseFloat(tabla.getValueAt(x, 6).toString());
                }
            }

            totalIva = iva5 + iva10;
            gravado = totalGeneral - totalIva;
            //JOptionPane.showMessageDialog(null,"Exentas = " + exenta + " iva5 = " + iva5 + "iva10 = " + iva10
            //+ "ivaTotal = " + totalIva + "gravado =  " + gravado + " Total = " + totalGeneral);
            // Actualizar los campos de texto con valores formateados
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
        JTable tabla = this.grdDetalleCompra;
        int filas = tabla.getRowCount();
        String concepto = this.cboTipoMto.getSelectedItem().toString();
        int codCompraCabecera = Integer.parseInt(this.txtCodigo.getText());
        
        if(concepto.equals("PAGO CUOTA")){
            for (int x = 0; x < filas; x++) {
                String codCuota = tabla.getValueAt(x, 0).toString();
                String [] cuota = bd.obtenerRegistro(   "SELECT \n" +
                                                        "cod_cuota_credito_proveedor, \n" +
                                                        "monto_cuota \n" +
                                                        "FROM cuotas_credito_proveedor \n" +
                                                        "WHERE cod_cuota_credito_proveedor = "+ codCuota
                                                    );
                float montoCuota = Float.parseFloat(cuota[1]);
                String valores = "estado_cuota='INACTIVO', fecha_pago_cuota=NOW(), num_movimiento=" + codCompraCabecera;
                String condicion = "cod_cuota_credito_proveedor=" + codCuota;
                bd.modificarRegistro("cuotas_credito_proveedor", valores, condicion);
                
                String [] credito = bd.obtenerRegistro("SELECT " +
                        " cp.cod_credito_proveedor, " +
                        " cp.saldo_restante_credito " +
                        " FROM credito_proveedor cp\n" +
                        " JOIN cuotas_credito_proveedor ccp ON cp.cod_credito_proveedor=ccp.cod_credito_proveedor\n" +
                        " WHERE ccp.cod_cuota_credito_proveedor = "+ codCuota);
                
                float saldoRestante = Float.parseFloat(credito[1]) - montoCuota;
                String estado = "ACTIVO";
                if(saldoRestante == 0){
                    estado = "INACTIVO";
                }
                String valoresCredito = "saldo_restante_credito=" + saldoRestante + ", estado_credito='" + estado + "'";
                String condicionCredito = "cod_credito_proveedor=" + credito[0];
                bd.modificarRegistro("credito_proveedor", valoresCredito, condicionCredito);
            }
        }else{
            String codCompraDetalle = bd.obtenerCodMaximoRegistro("compras_detalle", "cod_compra_detalle");
            String campos = "cod_compra_detalle,"
                    + "cod_compra_cabecera,"
                    + "cod_producto,"
                    + "cant_producto,"
                    + "prec_producto,"
                    + "iva_producto";

            for(int x = 0; x < filas; x++){
                String codProducto = tabla.getValueAt(x, 0).toString();
                String cantProducto = tabla.getValueAt(x, 2).toString();
                float exixtencia = Float.parseFloat(bd.obtenerRegistro("select existencia_producto from productos where cod_producto=" + codProducto)[0]) + Float.parseFloat(cantProducto);
                String precProducto;
                String ivaProducto;
                if(!tabla.getValueAt(x, 4).toString().isEmpty()){
                    precProducto = tabla.getValueAt(x, 4).toString();
                    ivaProducto = "EXENTA";
                }else{
                    if(!tabla.getValueAt(x, 5).toString().isEmpty()){
                        precProducto = tabla.getValueAt(x, 5).toString();
                        ivaProducto = "5%";
                    }else{
                        precProducto = tabla.getValueAt(x, 6).toString();
                        ivaProducto = "10%";
                    }
                }

                String Valores = "" + codCompraDetalle + "," + codCompraCabecera + "," + codProducto + ","
                        + cantProducto + "," + precProducto + ",'" + ivaProducto + "'";
                bd.insertarRegistro("compras_detalle", campos, Valores);
                bd.modificarRegistro("productos", "existencia_producto=" + exixtencia, "cod_producto=" + codProducto);

            }
        }
    }
    private void compraAnterior(int a){
        int codigo = Integer.parseInt(this.txtCodigo.getText()) - 1 - a;
        String sql = "select * from compras_cabecera where cod_compra_cabecera = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos == null){
            if(codigo >= 0){
                //System.err.println("No Hay datos contador = " + a);
                compraAnterior(a + 1);    
            }else{
                JOptionPane.showMessageDialog(null,"Estas en el Primer Registro");
            }
        }else{
            if(datos[8].equals("ACTIVO")){
                if(datos[1].equals("COMPRA CREDITO")){
                    this.btnCredito.setVisible(true);
                }else{
                    this.btnCredito.setVisible(false);                    
                }                 
                this.txtCodigo.setText(datos[0]);
                bd.VaciarCombo(cboTipoMto);
                bd.agregarItemCombo(cboTipoMto, 0, datos[1]);
                this.txtCodProveedor.setText(datos[2]);
                bd.VaciarCombo(cboProveedores);
                bd.agregarItemCombo(cboProveedores, 0, datos[3]);
                this.txtRucProveedor.setText(datos[4]);
                bd.mostrarFechaJDateChooser(dateFechaComprobante, datos[5]);
                this.txtTimbrado.setText(datos[6]);
                this.txtNumComprobante.setText(datos[7]);
                bd.seleccionarItemCombo(cboEstadoCompra, datos[8]);
                this.lblUsuarioCreacion.setText(datos[9]);
                this.lblUsuarioModificacion.setText(datos[10]);
                this.lblFechaCreacion.setText(datos[11]);
                this.lblFechaModificacion.setText(datos[12]);

                DefaultTableModel modelo = (DefaultTableModel) grdDetalleCompra.getModel();
                modelo.setRowCount(0);
                this.obtenerDetalleCompra(codigo);
            }else{
                compraAnterior(a + 1);
            }
        }
    }
    private void compraPosterior(int a){
        int codigo = Integer.parseInt(this.txtCodigo.getText()) + 1 + a;
        String sql = "select * from compras_cabecera where cod_compra_cabecera = " + codigo;
        String sql2 = "select MAX(cod_compra_cabecera) from compras_cabecera";
        String [] datos = bd.obtenerRegistro(sql);
        String [] codMax = bd.obtenerRegistro(sql2);
        
        if(datos == null){
            JOptionPane.showMessageDialog(null,"Estas en el Ultimo Registro");
        }else{
            if(datos[8].equals("ACTIVO")){
                if(datos[1].equals("COMPRA CREDITO")){
                    this.btnCredito.setVisible(true);
                }else{
                    this.btnCredito.setVisible(false);                    
                }               
                this.txtCodigo.setText(datos[0]);
                bd.VaciarCombo(cboTipoMto);
                bd.agregarItemCombo(cboTipoMto, 0, datos[1]);
                this.txtCodProveedor.setText(datos[2]);
                bd.VaciarCombo(cboProveedores);
                bd.agregarItemCombo(cboProveedores, 0, datos[3]);
                this.txtRucProveedor.setText(datos[4]);
                bd.mostrarFechaJDateChooser(dateFechaComprobante, datos[5]);
                this.txtTimbrado.setText(datos[6]);
                this.txtNumComprobante.setText(datos[7]);
                bd.seleccionarItemCombo(cboEstadoCompra, datos[8]);
                this.lblUsuarioCreacion.setText(datos[9]);
                this.lblUsuarioModificacion.setText(datos[10]);
                this.lblFechaCreacion.setText(datos[11]);
                this.lblFechaModificacion.setText(datos[12]);
                
                DefaultTableModel modelo = (DefaultTableModel) grdDetalleCompra.getModel();
                modelo.setRowCount(0);
                this.obtenerDetalleCompra(codigo);
            }else{
                compraPosterior(a + 1);
            }   
        }
    }
    private void obtenerDetalleCompra(int codCompra){
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        if(tipoMto.equals("PAGO CUOTA")){
            String [][] cuotas = bd.obtenerRegistros(
                    "   SELECT \n" +
                    "   ccp.cod_cuota_credito_proveedor,\n" +
                    "   pr.nombre_proveedor,\n" +
                    "   '1' AS cantidad,\n" +
                    "   ccp.monto_cuota\n" +
                        "FROM cuotas_credito_proveedor ccp\n" +
                    "   JOIN credito_proveedor cp ON ccp.cod_credito_proveedor=cp.cod_credito_proveedor\n" +
                    "   JOIN compras_cabecera cc ON cp.cod_compra=cc.cod_compra_cabecera\n" +
                    "   JOIN proveedores pr ON cc.cod_proveedor=pr.cod_proveedor\n" +
                    "   WHERE ccp.estado_cuota = 'INACTIVO' AND ccp.num_movimiento=" + codCompra
            );
            if(cuotas != null){
                for(int x = 0; x < cuotas.length; x++){
                    String codigo = null;
                    String nombre = null;
                    float cantidad = 0;
                    float precio = 0;  
                    String iva = null;
                    float total;
                    
                    codigo = cuotas[x][0];
                    nombre = cuotas[x][1];
                    cantidad = Float.parseFloat(cuotas[x][2]);
                    precio = Float.parseFloat(cuotas[x][3]);
                    
                    total = cantidad * precio;
                    this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), precio, "", "", String.format("%.0f", total));                    
                }
            }           
            
        }else{
            String sql = "SELECT c.cod_producto, p.nombre_producto, c.cant_producto, c.prec_producto, c.iva_producto FROM compras_detalle c\n" +
                        "JOIN productos p ON c.cod_producto = p.cod_producto\n" +
                        "WHERE cod_compra_cabecera =" + codCompra;
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
                        if(i == 0){
                            codigo = datos[x][i];
                        }
                        if(i == 1){
                            nombre = datos[x][i];
                        }
                        if(i == 2){
                            cantidad = Float.parseFloat(datos[x][i]);
                        }
                        if(i == 3){
                            precio = Float.parseFloat(datos[x][i]);
                        }
                        if(i == 4){
                            iva = datos[x][i];
                        }
                    }
                    if(iva.equals("EXENTA")){
                        total = cantidad * precio; 
                        this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), precio, String.format("%.0f", total), "", "");
                    }else{
                        if(iva.equals("5%")){
                            total = cantidad * precio;
                            this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), precio, "", String.format("%.0f", total), "");
                        }else{
                            total = cantidad * precio;
                            this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), precio, "", "", String.format("%.0f", total));
                        }
                    }

                }
            }            
        }
    }
    public void guardarCompraCredito(String cuotasC, String plazoC, String vencimientoC, String [][] cuotas){
        String codCompra = this.txtCodigo.getText();
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String codPr = String.valueOf(bd.obtenerCodCombo(cboProveedores));
        String [] datos = bd.obtenerRegistro("select * from proveedores where cod_proveedor = " + codPr);        
        String rucP = datos[1];
        String nomP = bd.obtenerNombreCombo(cboProveedores);
        String timbrado = this.txtTimbrado.getText();
        String numComp = this.txtNumComprobante.getText();
        String subTotal = this.txtTotalGeneral1.getText();
        String numCuotas = cuotasC;
        String plazoCuotas = plazoC;
        String fecaVen = vencimientoC;
        
        
        String fechaC = bd.formatoFecha(this.dateFechaComprobante);
        String campos = "cod_compra_cabecera,"
                + "tipo_movimiento,"
                + "cod_proveedor,"
                + "nom_proveedor,"
                + "ruc_proveedor,"
                + "fecha_comprobante,"
                + "timbrado_compra,"
                + "num_comprobante_compra,"
                + "usuario_insercion,"
                + "usuario_modificacion,"
                + "fecha_insercion,"
                + "fecha_modificacion";
        String valores = codCompra + ",'" + tipoMto + "'," + codPr + ",'" + nomP + "','" + rucP + "','" + fechaC + "','"
                + timbrado + "','" + numComp + "','" + usuarioLogeado + "', NULL, NOW(), NULL";
        String tabla = "compras_cabecera";
        bd.insertarRegistro(tabla, campos, valores);

        this.guardarDetalle();

        String codCredito = bd.obtenerCodMaximoRegistro("credito_proveedor", "cod_credito_proveedor");

                 
        
        String CamposCredito = " cod_credito_proveedor, "
                + "cod_compra, "
                + "fecha_inicio_credito,"
                + "fecha_vencimiento_credito, "
                + "num_cuotas_credito, "
                + "plazo_credito, "
                + "saldo_restante_credito, "
                + "estado_credito";
        String valoresCredito = codCredito + ","
                + "" + codCompra + ","
                + "'" + fechaC + "',"
                + "'" + fecaVen + "',"
                + "" + numCuotas + ","
                + "'" + plazoCuotas + "',"
                + "" + subTotal + ","
                + "'activo'" ;
        String tablaC = "credito_proveedor";
        bd.insertarRegistro(tablaC, CamposCredito, valoresCredito);
        
        for (int x = 0; x < cuotas.length; x++) {
            String codCuota = bd.obtenerCodMaximoRegistro("cuotas_credito_proveedor", "cod_cuota_credito_proveedor");
            String numCuota = "";
            String montoCuota = "";
            String fechaVencimientoCuota = "";
            String estadoCuota = "";
            
            String camposCuota = "cod_cuota_credito_proveedor,"
                    + "cod_credito_proveedor,"
                    + "numero_cuota,"
                    + "monto_cuota,"
                    + "fecha_vencimiento_cuota,"
                    + "estado_cuota";
            
            for (int i = 0; i < cuotas[x].length; i++) {
                switch (i) {
                    case 0:
                        numCuota = cuotas[x][i];
                        break;
                    case 1:
                        montoCuota = cuotas[x][i];
                        break;
                    case 2:
                        fechaVencimientoCuota = cuotas[x][i];
                        break;
                    case 3:
                        estadoCuota = cuotas[x][i];
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            String valoresCuota = "" + codCuota + ","
                    + "" + codCredito + ","
                    + "" + numCuota + ","
                    + "" + montoCuota + ","
                    + "'" + fe.formatoFechaJavaBD(fechaVencimientoCuota) + "',"
                    + "'" + estadoCuota + "'";
            System.out.println(valoresCuota);
            bd.insertarRegistro("cuotas_credito_proveedor", camposCuota, valoresCuota);
        }
        
        JOptionPane.showMessageDialog(null, "Movimiento Guardado");
        this.accion = "guardado";
        this.compraAnterior(-1);
    }
    private void esPagoCuota(){
        this.lblTimbrado.setVisible(false);
        this.txtTimbrado.setVisible(false);
        this.lblNumComprobante.setVisible(false);
        this.txtNumComprobante.setVisible(false);
    }
    private void noEsPagoCuota(){
        this.lblTimbrado.setVisible(true);
        this.txtTimbrado.setVisible(true);
        this.lblNumComprobante.setVisible(true);
        this.txtNumComprobante.setVisible(true);
        
    }

    private boolean verificarNumComprobante(){
        if(!txtNumComprobante.getText().isEmpty()){
            // Validar el formato al perder el foco
            if (txtNumComprobante.getText().matches("\\d{3}-\\d{3}-\\d{7}")) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ser xxx-xxx-xxxxxxx");
        txtNumComprobante.requestFocus(); // Volver a enfocar el campo
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pantallaCompras = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdDetalleCompra = new javax.swing.JTable();
        btnBuscarProducto = new javax.swing.JButton();
        txtCodProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCantProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        cboIva = new javax.swing.JComboBox<>();
        btnAgregarProducto = new javax.swing.JToggleButton();
        btnEliminarProducto = new javax.swing.JToggleButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtIva5 = new javax.swing.JTextField();
        txtIva10 = new javax.swing.JTextField();
        txtExentas = new javax.swing.JTextField();
        txtTotalIva = new javax.swing.JTextField();
        txtTotalGravado = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTotalGeneral1 = new javax.swing.JTextField();
        txtTotalGeneral = new javax.swing.JTextField();
        JPanel = new javax.swing.JPanel();
        dateFechaComprobante = new com.toedter.calendar.JDateChooser();
        btnCredito = new javax.swing.JToggleButton();
        txtTimbrado = new javax.swing.JTextField();
        txtNumComprobante = new javax.swing.JTextField();
        cboEstadoCompra = new javax.swing.JComboBox<>();
        txtCodCredito = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        lblNumComprobante = new javax.swing.JLabel();
        lblTimbrado = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JToggleButton();
        btnAnular = new javax.swing.JToggleButton();
        btnCerrar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnAnterior = new javax.swing.JToggleButton();
        btnSiguiente = new javax.swing.JToggleButton();
        btnNuevo = new javax.swing.JToggleButton();
        txtCodigo = new javax.swing.JTextField();
        cboTipoMto = new javax.swing.JComboBox<>();
        cboProveedores = new javax.swing.JComboBox<>();
        btnBuscarProveedor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodProveedor = new javax.swing.JTextField();
        txtRucProveedor = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        itmInformeComprasAnuladas = new javax.swing.JMenuItem();
        itmComprasAnuladas = new javax.swing.JMenuItem();
        itmDeudasProveedor = new javax.swing.JMenuItem();
        itmCreditosProveedor = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pantallaCompras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pantallaCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DETALLE DE COMPRA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grdDetalleCompra.setBackground(new java.awt.Color(0, 153, 255));
        grdDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Cantidad", "PrecioU", "Exentas", "IVA 5%", "IVA 10%"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdDetalleCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        grdDetalleCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdDetalleCompraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdDetalleCompra);
        if (grdDetalleCompra.getColumnModel().getColumnCount() > 0) {
            grdDetalleCompra.getColumnModel().getColumn(0).setMaxWidth(200);
            grdDetalleCompra.getColumnModel().getColumn(2).setMinWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(2).setPreferredWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(2).setMaxWidth(200);
            grdDetalleCompra.getColumnModel().getColumn(3).setMinWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdDetalleCompra.getColumnModel().getColumn(3).setMaxWidth(200);
            grdDetalleCompra.getColumnModel().getColumn(4).setMinWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(4).setPreferredWidth(150);
            grdDetalleCompra.getColumnModel().getColumn(4).setMaxWidth(400);
            grdDetalleCompra.getColumnModel().getColumn(5).setMinWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdDetalleCompra.getColumnModel().getColumn(5).setMaxWidth(400);
            grdDetalleCompra.getColumnModel().getColumn(6).setMinWidth(100);
            grdDetalleCompra.getColumnModel().getColumn(6).setPreferredWidth(150);
            grdDetalleCompra.getColumnModel().getColumn(6).setMaxWidth(400);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1190, 270));

        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 40, 30));

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
        jPanel4.add(txtCodProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 70, 30));

        txtDescripcionProducto.setEditable(false);
        txtDescripcionProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescripcionProducto.setFocusable(false);
        jPanel4.add(txtDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 380, 30));

        txtCantProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCantProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantProductoFocusLost(evt);
            }
        });
        jPanel4.add(txtCantProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 80, 30));

        txtPrecioProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 120, 30));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescuento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescuentoFocusLost(evt);
            }
        });
        jPanel4.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 140, 30));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 120, 30));

        cboIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXENTAS", "5%", "10%" }));
        cboIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(cboIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 40, -1, 30));

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
        jPanel4.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 40, 35, 30));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 40, 35, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, -1, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Codigo");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Concepto");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Cantidad");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 60, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Precio");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 40, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Descuento");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, -1, 20));

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("IVA");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, 20));

        pantallaCompras.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 1210, 360));

        jPanel5.setBackground(new java.awt.Color(51, 102, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LIQUIDACION DE IVA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtIva5.setEditable(false);
        txtIva5.setBackground(new java.awt.Color(255, 255, 255));
        txtIva5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIva5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIva5.setFocusable(false);
        jPanel5.add(txtIva5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 141, 30));

        txtIva10.setEditable(false);
        txtIva10.setBackground(new java.awt.Color(255, 255, 255));
        txtIva10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIva10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIva10.setFocusable(false);
        jPanel5.add(txtIva10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 140, 30));

        txtExentas.setEditable(false);
        txtExentas.setBackground(new java.awt.Color(255, 255, 255));
        txtExentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtExentas.setFocusable(false);
        jPanel5.add(txtExentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 140, 30));

        txtTotalIva.setEditable(false);
        txtTotalIva.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalIva.setFocusTraversalPolicyProvider(true);
        txtTotalIva.setFocusable(false);
        jPanel5.add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 140, 30));

        txtTotalGravado.setEditable(false);
        txtTotalGravado.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGravado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGravado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalGravado.setFocusable(false);
        jPanel5.add(txtTotalGravado, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 140, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Exentas");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("IVA 5%");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("IVA10%");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, 20));

        jLabel21.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Total IVA");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, 20));

        jLabel22.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Total Gravado");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, 20));

        jLabel23.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Total General");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 40, -1, 20));

        txtTotalGeneral1.setEditable(false);
        txtTotalGeneral1.setBackground(new java.awt.Color(51, 102, 255));
        txtTotalGeneral1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalGeneral1.setForeground(new java.awt.Color(51, 102, 255));
        txtTotalGeneral1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGeneral1.setBorder(null);
        txtTotalGeneral1.setFocusable(false);
        jPanel5.add(txtTotalGeneral1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 30, 20));

        txtTotalGeneral.setEditable(false);
        txtTotalGeneral.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGeneral.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txtTotalGeneral.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalGeneral.setFocusable(false);
        jPanel5.add(txtTotalGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 320, 50));

        pantallaCompras.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 1210, 80));

        JPanel.setBackground(new java.awt.Color(51, 102, 255));
        JPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateFechaComprobante.setBackground(new java.awt.Color(255, 255, 255));
        dateFechaComprobante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateFechaComprobante.setDateFormatString("dd-MM-yyyy");
        dateFechaComprobante.setFocusable(false);
        dateFechaComprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dateFechaComprobanteFocusLost(evt);
            }
        });
        dateFechaComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dateFechaComprobanteMouseExited(evt);
            }
        });
        dateFechaComprobante.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dateFechaComprobanteComponentHidden(evt);
            }
        });
        dateFechaComprobante.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateFechaComprobantePropertyChange(evt);
            }
        });
        JPanel.add(dateFechaComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 120, 30));

        btnCredito.setText("CREDITO");
        btnCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreditoActionPerformed(evt);
            }
        });
        JPanel.add(btnCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 90, 30));

        txtTimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbrado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JPanel.add(txtTimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 200, 30));

        txtNumComprobante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumComprobante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNumComprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumComprobanteFocusLost(evt);
            }
        });
        JPanel.add(txtNumComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 200, 30));

        cboEstadoCompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "activo", "inactivo" }));
        cboEstadoCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)), "Estado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        cboEstadoCompra.setFocusable(false);
        JPanel.add(cboEstadoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 7, 0, 0));

        txtCodCredito.setEditable(false);
        txtCodCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCredito.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)), "Numero Comprobante"));
        txtCodCredito.setFocusable(false);
        txtCodCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCreditoActionPerformed(evt);
            }
        });
        JPanel.add(txtCodCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 60, 0, 1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Insersion");
        JPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 120, 15));

        lblUsuarioModificacion.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JPanel.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 120, 15));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Modificacion");
        JPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 120, 15));

        lblFechaCreacion.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JPanel.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 120, 15));

        lblFechaModificacion.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JPanel.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 120, 15));

        lblUsuarioCreacion.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JPanel.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 120, 15));

        lblNumComprobante.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblNumComprobante.setForeground(new java.awt.Color(255, 255, 255));
        lblNumComprobante.setText("Num Comp");
        JPanel.add(lblNumComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 70, 30));

        lblTimbrado.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        lblTimbrado.setForeground(new java.awt.Color(255, 255, 255));
        lblTimbrado.setText("Timbrado");
        JPanel.add(lblTimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Fecha");
        JPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        pantallaCompras.add(JPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 590, 150));

        jPanel3.setBackground(new java.awt.Color(51, 102, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 30));

        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 40, 30));

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 40, 30));

        pantallaCompras.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 230, 150));

        jPanel6.setBackground(new java.awt.Color(51, 102, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/anterior.png"))); // NOI18N
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel6.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 30));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/siguiente.png"))); // NOI18N
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel6.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 40, 30));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel6.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 40, 30));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.setFocusable(false);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });
        jPanel6.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 200, 30));

        cboTipoMto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMPRA CONTADO", "COMPRA CREDITO" }));
        cboTipoMto.setToolTipText("");
        cboTipoMto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboTipoMto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTipoMtoItemStateChanged(evt);
            }
        });
        cboTipoMto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cboTipoMtoFocusLost(evt);
            }
        });
        jPanel6.add(cboTipoMto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 200, 30));

        cboProveedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboProveedoresItemStateChanged(evt);
            }
        });
        cboProveedores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cboProveedoresFocusLost(evt);
            }
        });
        cboProveedores.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboProveedoresPropertyChange(evt);
            }
        });
        jPanel6.add(cboProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 220, 30));

        btnBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedorActionPerformed(evt);
            }
        });
        jPanel6.add(btnBuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 40, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipo de Movimiento");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Proveedor");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, 30));

        txtCodProveedor.setEditable(false);
        txtCodProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)), "Numero Comprobante"));
        txtCodProveedor.setFocusable(false);
        txtCodProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProveedorActionPerformed(evt);
            }
        });
        jPanel6.add(txtCodProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 10, 0));

        txtRucProveedor.setEditable(false);
        txtRucProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRucProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)), "Numero Comprobante"));
        txtRucProveedor.setFocusable(false);
        jPanel6.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(756, 93, 0, 0));

        pantallaCompras.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 370, 150));

        jMenu1.setText("File");

        jMenuItem3.setText("Pantalla Principal");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        menuReportes.setText("Reportes");

        itmInformeComprasAnuladas.setText("Compras");
        itmInformeComprasAnuladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmInformeComprasAnuladasActionPerformed(evt);
            }
        });
        menuReportes.add(itmInformeComprasAnuladas);

        itmComprasAnuladas.setText("Compras Anuladas");
        itmComprasAnuladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmComprasAnuladasActionPerformed(evt);
            }
        });
        menuReportes.add(itmComprasAnuladas);

        itmDeudasProveedor.setText("Deudas Proveedor");
        itmDeudasProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeudasProveedorActionPerformed(evt);
            }
        });
        menuReportes.add(itmDeudasProveedor);

        itmCreditosProveedor.setText("Creditos Proveedor");
        itmCreditosProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmCreditosProveedorActionPerformed(evt);
            }
        });
        menuReportes.add(itmCreditosProveedor);

        jMenuBar1.add(menuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pantallaCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pantallaCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(this.accion.equals("nuevo")){
            if(!this.cboProveedores.getSelectedItem().toString().equals("INDEFINIDO")){
                String codCompra = this.txtCodigo.getText();
                String tipoMto = this.cboTipoMto.getSelectedItem().toString();
                String codPr = String.valueOf(bd.obtenerCodCombo(cboProveedores));
                String [] datos = bd.obtenerRegistro("select * from proveedores where cod_proveedor = " + codPr);        
                String rucP = datos[1];
                String nomP = bd.obtenerNombreCombo(cboProveedores);
                String timbrado = this.txtTimbrado.getText();
                String numComp = this.txtNumComprobante.getText();
                String subTotal = this.txtTotalGeneral1.getText();
                int items = this.grdDetalleCompra.getRowCount();
                
                
                
                if(this.dateFechaComprobante.getDate() != null ){
                    if(tipoMto.equals("COMPRA CONTADO")){
                        if(!timbrado.isEmpty()){
                            if(verificarNumComprobante()){
                                if(bd.verificarDuplicidadNumComprobante(numComp, codPr) == "-1"){
                                    if(items > -1){                                                                                   
                                        String fechaC = bd.formatoFecha(this.dateFechaComprobante);
                                        String campos = "cod_compra_cabecera,"
                                                + "tipo_movimiento,"
                                                + "cod_proveedor,"
                                                + "nom_proveedor,"
                                                + "ruc_proveedor,"
                                                + "fecha_comprobante,"
                                                + "timbrado_compra,"
                                                + "num_comprobante_compra,"
                                                + "estado_compra,"
                                                + "usuario_insercion,"
                                                + "usuario_modificacion,"
                                                + "fecha_insercion,"
                                                + "fecha_modificacion";
                                        String valores = codCompra + ","
                                                + "'" + tipoMto + "',"
                                                + "" + codPr + ","
                                                + "'" + nomP + "',"
                                                + "'" + rucP + "',"
                                                + "'" + fechaC + "',"
                                                + "'" + timbrado + "',"
                                                + "'" + numComp + "',"
                                                + "'activo',"
                                                + "'" + usuarioLogeado + "', "
                                                + "NULL, "
                                                + "NOW(), "
                                                + "NULL";
                                        String tabla = "compras_cabecera";
                                        bd.insertarRegistro(tabla, campos, valores);

                                        this.guardarDetalle();
                                        JOptionPane.showMessageDialog(null, "Movimiento Guardado");
                                        this.accion = "guardado";
                                        this.compraAnterior(-1);
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Debes cargar un producto");
                                        this.txtCodProducto.requestFocus();
                                    }                    
                                }else{
                                    JOptionPane.showMessageDialog(null, "Este numero de comprobante ya esta registrada en la compra numero " + bd.verificarDuplicidadNumComprobante(numComp, codPr));
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "Ingrese el numero del comprobante");
                                this.txtNumComprobante.requestFocus();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Ingrese el numero de timbrado");
                            this.txtTimbrado.requestFocus();
                        }       
                    }else{
                        if(tipoMto.equals("COMPRA CREDITO")){
                            if(!timbrado.isEmpty()){
                                if(!numComp.isEmpty()){
                                    if(bd.verificarDuplicidadNumComprobante(numComp, codPr) == "-1"){
                                        if(items > -1){
                                            if(!this.txtTotalGeneral1.getText().isEmpty()){
                                                String fechaC = fe.formatoFechaBDaJava(fe.formatoFecha(dateFechaComprobante));
                                                Float monto = Float.parseFloat(this.txtTotalGeneral1.getText());
                                                JDCuotasCompra fc = new JDCuotasCompra(this, true, this, monto, fechaC, null);
                                                fc.setVisible(true);
                                            }
                                        }else{
                                            JOptionPane.showMessageDialog(null, "Debes cargar un producto");
                                            this.txtCodProducto.requestFocus();
                                        }                                        
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Este numero de comprobante ya esta registrada en la compra numero " + bd.verificarDuplicidadNumComprobante(numComp, codPr));
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "Ingrese el numero del comprobante");
                                    this.txtNumComprobante.requestFocus();
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "Ingrese el numero de timbrado");
                                this.txtTimbrado.requestFocus();
                            }                
                        }else{
                            if(tipoMto.equals("PAGO CUOTA")){
                                if(items > -1){
                                    if(!this.txtTotalGeneral1.getText().isEmpty()){
                                        String fechaC = bd.formatoFecha(this.dateFechaComprobante);
                                        String campos = "cod_compra_cabecera,"
                                                + "tipo_movimiento,"
                                                + "cod_proveedor,"
                                                + "nom_proveedor,"
                                                + "ruc_proveedor,"
                                                + "fecha_comprobante,"
                                                + "estado_compra,"
                                                + "usuario_insercion,"
                                                + "usuario_modificacion,"
                                                + "fecha_insercion,"
                                                + "fecha_modificacion";
                                        String valores = codCompra + ","
                                                + "'" + tipoMto + "',"
                                                + "" + codPr + ","
                                                + "'" + nomP + "',"
                                                + "'" + rucP + "',"
                                                + "'" + fechaC + "',"
                                                + "'activo',"
                                                + "'" + usuarioLogeado + "', "
                                                + "NULL, "
                                                + "NOW(), "
                                                + "NULL";
                                        String tabla = "compras_cabecera";
                                        bd.insertarRegistro(tabla, campos, valores);
                                        this.guardarDetalle();
                                        JOptionPane.showMessageDialog(null, "MOVIMIENTO GUARDADO");
                                        this.accion = "guardado";
                                        this.compraAnterior(-1);                                        
                                    }
                                }
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese la fecha del comprobante");
                    this.dateFechaComprobante.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione el proveedor");
                this.btnBuscarProveedor.requestFocus();
            }                    
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        if(this.accion.equals("nuevo")){
            if(!this.txtPrecioProducto.getText().isEmpty()){
                this.calcularTotal();
                this.agregarProducto();                 
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese el precio del producto");
            }
        }
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        String codigoAutorizacion = JOptionPane.showInputDialog(this, "Ingrese el código de autorización para eliminar la fila:");

        // Verificar si se ingresó un código
        if (codigoAutorizacion != null && !codigoAutorizacion.trim().isEmpty()) {
            // Comprobar si el código es correcto
            if (codigoAutorizacion.equals("123")){
                int fila = this.grdDetalleCompra.getSelectedRow();
                if (fila != -1) { // Verificar que se haya seleccionado una fila
                    DefaultTableModel model = (DefaultTableModel) this.grdDetalleCompra.getModel(); // Obtener el modelo de la tabla
                    model.removeRow(fila); // Eliminar la fila seleccionada
                    this.calcularSubTotales();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Codigo de Seguridad Incorrecto");
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        if(accion.equals("nuevo")){
            String tipoMto = this.cboTipoMto.getSelectedItem().toString();
            if(tipoMto.equals("PAGO CUOTA")){
                if(!this.txtCodProveedor.getText().isEmpty()){
                    String [] cabeceras = {"COD", "PROVEEDOR", "COD COMPRA", "FECHA COMPRA", "NUM COMPROBANTE", "MONTO", "VENCIMIENTO"};
                    int [] ancho = {50,300,100,150,150,150, 150};
                    String tabla = "cuotas_credito_proveedor ccp ";
                    String [] campos = {
                        "ccp.cod_cuota_credito_proveedor",
                        "CONCAT(pr.cod_proveedor,' - ',pr.nombre_proveedor) AS proveedor",
                        "cc.cod_compra_cabecera",
                        "cc.fecha_comprobante",
                        "cc.num_comprobante_compra",
                        "ccp.monto_cuota",
                        "ccp.fecha_vencimiento_cuota"
                    };
                    String condicion =  "   JOIN credito_proveedor cp ON ccp.cod_credito_proveedor=cp.cod_credito_proveedor\n" +
                                        "   JOIN compras_cabecera cc ON cp.cod_compra=cc.cod_compra_cabecera\n" +
                                        "   JOIN proveedores pr ON cc.cod_proveedor=pr.cod_proveedor\n" +
                                        "   WHERE ccp.estado_cuota = 'ACTIVO' AND pr.cod_proveedor =" + this.txtCodProveedor.getText();

                    JDBuscador fbp = new JDBuscador(this, true, this,tabla,campos,condicion,cabeceras,ancho);
                    fbp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    fbp.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor para ver los creditos pendientes");
                }
            }else{
                String tabla = "productos p";
                String [] cabeceras = {"Codigo", "Producto", "Marca", "Precio", "Existencia", "IVA"};
                String [] campos = {"p.cod_producto", "p.nombre_producto", "m.nombre_marca", "p.precio_producto", "p.existencia_producto", "p.iva_producto"};
                String condicion = " join marcas m on p.cod_marca = m.cod_marca";
                int [] ancho = {200,500,300,300,200,200};
                FrmBuscador fbp = new FrmBuscador(this,tabla,campos,condicion,cabeceras,ancho);
                fbp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                fbp.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtCodProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProductoFocusLost
        if(!this.txtCodProducto.getText().isEmpty()){
            if(!this.cboTipoMto.getSelectedItem().toString().equals("PAGO CUOTA")){
                String sql = "Select cod_producto,nombre_producto,precio_producto,iva_producto from productos "
                + "where cod_producto = "+ this.txtCodProducto.getText();
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
            }else{
                JOptionPane.showMessageDialog(null, "Usa el buscador para ver las cuotas pendientes de pago");
            }
        }
    }//GEN-LAST:event_txtCodProductoFocusLost

    private void btnAgregarProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAgregarProductoFocusGained

    }//GEN-LAST:event_btnAgregarProductoFocusGained

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

    private void grdDetalleCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdDetalleCompraMouseClicked
        if(this.grdDetalleCompra.getSelectedRow()>=0 && accion == "nuevo"){
            this.btnEliminarProducto.setEnabled(true);
        }else{
            this.btnEliminarProducto.setEnabled(false);
        }
    }//GEN-LAST:event_grdDetalleCompraMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.accion = "nuevo";
        this.nuevo();
        this.habilitarCampos(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void cboTipoMtoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipoMtoItemStateChanged
        
    }//GEN-LAST:event_cboTipoMtoItemStateChanged

    private void btnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedorActionPerformed
        if(accion.equals("nuevo")){
            String tabla = "proveedores";
            String [] campos = {"cod_proveedor", "nombre_proveedor", "ruc_proveedor", "telefono_proveedor", "correo_proveedor"};
            String [] cabeceras = {"Codigo", "Proveedor", "RUC", "Telefono", "Correo"};
            int [] ancho = {200,500,300,300,300};
            JDBuscador fb = new JDBuscador(this, true, this, tabla, campos, "",cabeceras, ancho);
            fb.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            fb.setVisible(true);
        }
    }//GEN-LAST:event_btnBuscarProveedorActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        this.accion = "anterior";
        this.compraAnterior(0);
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        this.accion = "posterior";
        this.compraPosterior(0);
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void txtCodProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProductoActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        if(rol.equals("ADMIN")){
            int codigo = Integer.parseInt(this.txtCodigo.getText());
            String [] datos = bd.obtenerRegistro("Select "
                    + "cod_compra_cabecera, "
                    + "tipo_movimiento, "
                    + "fecha_insercion, "
                    + "estado_compra "
                    + "from compras_cabecera "
                    + "where cod_compra_cabecera = " + codigo);

            String tipoMto = datos[1];
            String fechaHoraInsercion = datos[2];
            if(!fe.compararFecha(fechaHoraInsercion)){
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar este registro?", 
                "Confirmación de Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

                // Comprueba la elección del usuario
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if(tipoMto.equals("COMPRA CONTADO")){
                        bd.modificarRegistro("compras_cabecera", "estado_compra = 'INACTIVO'", "cod_compra_cabecera = " + codigo);
                        String [][] detalleCompra = bd.obtenerRegistros(
                            "SELECT\n" +
                            "cod_compra_detalle,\n" +
                            "cod_producto,\n" +
                            "cant_producto\n" +
                            "FROM compras_detalle\n" +
                            "WHERE cod_compra_cabecera = " + codigo
                        );
                        for(int x = 0; x < detalleCompra.length; x++) {
                            String codDetalle= detalleCompra[x][0];
                            String codProducto = detalleCompra[x][1];
                            String cantidad = detalleCompra[x][2];
                            
                            String [] producto = bd.obtenerRegistro(
                                "SELECT\n" +
                                "existencia_producto\n" +
                                "FROM productos\n" +
                                "WHERE cod_producto = " + codProducto
                            );
                             
                            float exitencia = Float.parseFloat(producto[0]) - Float.parseFloat(cantidad);
                            
                            bd.modificarRegistro("productos", "existencia_producto=" + exitencia, "cod_producto=" + codProducto);
                            
                        }
                        
                        JOptionPane.showMessageDialog(  null, "Compra Anulada");
                        this.nuevo();
                        this.accion = "nuevo";
                    }else{
                        if(tipoMto.equals("COMPRA CREDITO")){  
                            String codCredito = bd.obtenerRegistro("select "
                                    + "cod_credito_proveedor "
                                    + "from credito_proveedor "
                                    + "where cod_compra =" + codigo)[0];
                            String [][] cuotaCredito = bd.obtenerRegistros("select "
                                    + "estado_cuota "
                                    + "from cuotas_credito_proveedor "
                                    + "where cod_credito_proveedor=" + codCredito);
                            boolean tienePago = false;
                            for (int x = 0; x < cuotaCredito.length; x++) {
                                if(cuotaCredito[x][0].equals("INACTIVO")){
                                    tienePago = true;
                                }
                            }
                            if(!tienePago){
                                bd.modificarRegistro("compras_cabecera", "estado_compra = 'INACTIVO'", "cod_compra_cabecera = " + codigo);
                                String [][] detalleCompra = bd.obtenerRegistros(
                                    "SELECT\n" +
                                    "cod_compra_detalle,\n" +
                                    "cod_producto,\n" +
                                    "cant_producto\n" +
                                    "FROM compras_detalle\n" +
                                    "WHERE cod_compra_cabecera = " + codigo
                                );
                                for(int x = 0; x < detalleCompra.length; x++) {
                                    String codDetalle= detalleCompra[x][0];
                                    String codProducto = detalleCompra[x][1];
                                    String cantidad = detalleCompra[x][2];

                                    String [] producto = bd.obtenerRegistro(
                                        "SELECT\n" +
                                        "existencia_producto\n" +
                                        "FROM productos\n" +
                                        "WHERE cod_producto = " + codProducto
                                    );

                                    float exitencia = Float.parseFloat(producto[0]) - Float.parseFloat(cantidad);

                                    bd.modificarRegistro("productos", "existencia_producto=" + exitencia, "cod_producto=" + codProducto);

                                }
                                bd.borrarRegistro("cuotas_credito_proveedor", "cod_credito_proveedor=" + codCredito);
                                bd.borrarRegistro("credito_proveedor", "cod_credito_proveedor=" + codCredito);
                                JOptionPane.showMessageDialog(null, "Compra Anulada");
                                this.nuevo();
                                this.accion = "nuevo";
                            }else{
                                JOptionPane.showMessageDialog(null, "No puedes borrar una compra credito que ya cuenta con pago");
                            }
                        }else{
                            bd.modificarRegistro("compras_cabecera", "estado_compra = 'INACTIVO'", "cod_compra_cabecera = " + codigo);
                            String [][] cuotas = bd.obtenerRegistros(
                                    "   SELECT \n" +
                                    "   cod_cuota_credito_proveedor,\n" +
                                    "   monto_cuota\n" +
                                        "FROM cuotas_credito_proveedor\n" +
                                    "   WHERE estado_cuota = 'INACTIVO' AND num_movimiento=" + codigo
                            );
                            for (int x = 0; x < cuotas.length; x++) {
                                String codCuota = cuotas[x][0];
                                String valoresCuota = "estado_cuota='ACTIVO', fecha_pago_cuota= NULL, num_movimiento= NULL";
                                String condicionCuota = "cod_cuota_credito_proveedor=" + codCuota;
                                bd.modificarRegistro("cuotas_credito_proveedor", valoresCuota, condicionCuota);
                                
                                float montoCuota = Float.parseFloat(cuotas[x][1]);
                                String [] credito = bd.obtenerRegistro("SELECT " +
                                    " cp.cod_credito_proveedor, " +
                                    " cp.saldo_restante_credito " +
                                    " FROM credito_proveedor cp\n" +
                                    " JOIN cuotas_credito_proveedor ccp ON cp.cod_credito_proveedor=ccp.cod_credito_proveedor\n" +
                                    " WHERE ccp.cod_cuota_credito_proveedor = "+ codCuota
                                );
                                float saldoRestante = Float.parseFloat(credito[1]) + montoCuota;
                                String estado = "INACTIVO";
                                if(saldoRestante >= 0){
                                    estado = "ACTIVO";
                                }
                                bd.modificarRegistro("credito_proveedor", "saldo_restante_credito=" + saldoRestante + ", estado_credito='" + estado + "'", "cod_credito_proveedor=" + credito[0]);
                            }
                            
                            
                            JOptionPane.showMessageDialog(  null, "Compra Anulada");
                            this.nuevo();
                            this.accion = "nuevo";
                        }
                    }                    
                }else {
                    System.out.println("Operación cancelada.");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "No se puede borrar una compra con mas de una semana de antiguedad");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Solo usuario administrador puede anular compras");
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtCodProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProveedorActionPerformed

    private void txtCodCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodCreditoActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void dateFechaComprobanteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dateFechaComprobanteFocusLost
        
    }//GEN-LAST:event_dateFechaComprobanteFocusLost

    private void dateFechaComprobanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFechaComprobanteMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFechaComprobanteMouseExited

    private void dateFechaComprobanteComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dateFechaComprobanteComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFechaComprobanteComponentHidden

    private void dateFechaComprobantePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateFechaComprobantePropertyChange

    }//GEN-LAST:event_dateFechaComprobantePropertyChange

    private void txtNumComprobanteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumComprobanteFocusLost
        if(!txtNumComprobante.getText().isEmpty()){
            // Validar el formato al perder el foco
            if (!txtNumComprobante.getText().matches("\\d{3}-\\d{3}-\\d{7}")) {
                JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ser xxx-xxx-xxxxxxx");
                txtNumComprobante.requestFocus(); // Volver a enfocar el campo
            }
        }
    }//GEN-LAST:event_txtNumComprobanteFocusLost

    private void cboProveedoresPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboProveedoresPropertyChange
              
    }//GEN-LAST:event_cboProveedoresPropertyChange

    private void btnCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreditoActionPerformed
        String codCompra = this.txtCodigo.getText();
        String codCredito = bd.obtenerRegistro("select cod_credito_proveedor from credito_proveedor where cod_compra =" + codCompra)[0];
        String [][] cuotas = bd.obtenerRegistros("select numero_cuota,monto_cuota,fecha_vencimiento_cuota,estado_cuota from cuotas_credito_proveedor where cod_credito_proveedor=" + codCredito);
        JDCuotasCompra jC = new JDCuotasCompra(this, true, this, cuotas);
        jC.setVisible(true);
    }//GEN-LAST:event_btnCreditoActionPerformed

    private void itmComprasAnuladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmComprasAnuladasActionPerformed
        FrmInformeCompras fcom = new FrmInformeCompras("INACTIVO");
        fcom.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fcom.setVisible(true);
    }//GEN-LAST:event_itmComprasAnuladasActionPerformed

    private void itmInformeComprasAnuladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmInformeComprasAnuladasActionPerformed
        FrmInformeCompras fcom = new FrmInformeCompras("ACTIVO");
        fcom.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fcom.setVisible(true);
    }//GEN-LAST:event_itmInformeComprasAnuladasActionPerformed

    private void itmCreditosProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmCreditosProveedorActionPerformed
    String [] cabeceras = {"COD","PROVEEDOR","CUOTAS","PLAZO","INICIO","VENCIMIENTO","TOTAL CREDITO","SALDO PENDIENTE","ESTADO"};
    int [] tamaños = {50,400,100,100,100,120,120,150,150,100};
    String tabla = " credito_proveedor cp ";
    String [] campos = {
        "cp.cod_credito_proveedor",
        "CONCAT(pr.cod_proveedor,' - ',pr.nombre_proveedor) AS proveedor",
        "cp.num_cuotas_credito",
        "cp.plazo_credito",
        "cp.fecha_inicio_credito",
        "cp.fecha_vencimiento_credito",
        "SUM(ccp.monto_cuota) AS monto",
        "cp.saldo_restante_credito",
        "cp.estado_credito"
    };
    String condicion =  "   JOIN compras_cabecera cc ON cp.cod_compra=cc.cod_compra_cabecera\n" +
                        "   JOIN proveedores pr ON cc.cod_proveedor=pr.cod_proveedor\n" +
                        "   JOIN cuotas_credito_proveedor ccp ON cp.cod_credito_proveedor=ccp.cod_credito_proveedor\n" +
                        "   GROUP BY cp.cod_credito_proveedor";
    
    FrmInformeCompras fcom = new FrmInformeCompras(cabeceras, tamaños, tabla, campos, condicion, "creditos proveedor");
    fcom.setDefaultCloseOperation(HIDE_ON_CLOSE);
    fcom.setVisible(true);    
    }//GEN-LAST:event_itmCreditosProveedorActionPerformed

    private void cboProveedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboProveedoresItemStateChanged
        
    }//GEN-LAST:event_cboProveedoresItemStateChanged

    private void cboProveedoresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboProveedoresFocusLost
        this.txtCodProveedor.setText(String.valueOf(bd.obtenerCodCombo(cboProveedores)));
        this.limpiarCamposDetalle();
        gr.vaciar(grdDetalleCompra);
    }//GEN-LAST:event_cboProveedoresFocusLost

    private void cboTipoMtoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboTipoMtoFocusLost
        String condicion = this.cboTipoMto.getSelectedItem().toString();
        if(this.accion.equals("nuevo")){
            if(condicion.equals("PAGO CUOTA")){
                this.esPagoCuota();            
            }else{
                this.noEsPagoCuota();
            }
            this.limpiarCamposDetalle();
            gr.vaciar(grdDetalleCompra);
        }
    }//GEN-LAST:event_cboTipoMtoFocusLost

    private void itmDeudasProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeudasProveedorActionPerformed
        FrmInformeCompras fr = new FrmInformeCompras("ACTIVO", "DEUDAS PROVEEDORES");
        fr.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fr.setVisible(true);
    }//GEN-LAST:event_itmDeudasProveedorActionPerformed

    
    
   
    
    
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
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCompras dialog = new FrmCompras("VLOPEZ","ADMIN");
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
    private javax.swing.JPanel JPanel;
    private javax.swing.JToggleButton btnAgregarProducto;
    private javax.swing.JToggleButton btnAnterior;
    private javax.swing.JToggleButton btnAnular;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JToggleButton btnCredito;
    private javax.swing.JToggleButton btnEliminarProducto;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JToggleButton btnNuevo;
    private javax.swing.JToggleButton btnSiguiente;
    private javax.swing.JComboBox<String> cboEstadoCompra;
    private javax.swing.JComboBox<String> cboIva;
    private javax.swing.JComboBox<String> cboProveedores;
    private javax.swing.JComboBox<String> cboTipoMto;
    private com.toedter.calendar.JDateChooser dateFechaComprobante;
    private javax.swing.JTable grdDetalleCompra;
    public javax.swing.JMenuItem itmComprasAnuladas;
    public javax.swing.JMenuItem itmCreditosProveedor;
    public javax.swing.JMenuItem itmDeudasProveedor;
    public javax.swing.JMenuItem itmInformeComprasAnuladas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblNumComprobante;
    private javax.swing.JLabel lblTimbrado;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JMenu menuReportes;
    private javax.swing.JPanel pantallaCompras;
    private javax.swing.JTextField txtCantProducto;
    private javax.swing.JTextField txtCodCredito;
    private javax.swing.JTextField txtCodProducto;
    private javax.swing.JTextField txtCodProveedor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtExentas;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    private javax.swing.JTextField txtNumComprobante;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtTimbrado;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalGeneral;
    private javax.swing.JTextField txtTotalGeneral1;
    private javax.swing.JTextField txtTotalGravado;
    private javax.swing.JTextField txtTotalIva;
    // End of variables declaration//GEN-END:variables
}
