
package ventanas.ventas;

import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDatos1;
import javax.swing.JTable;
import ventanas.presupuesto.FrmInformePresupuestos;
import servicios.Fechas;
import servicios.Grilla;
import ventanas.cajasCasiListo.FrmCajas;
import ventanas.principal.FrmAjustes;



public class FrmVentas extends javax.swing.JFrame {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Grilla gr = new Grilla();
    Fechas fe = new Fechas();
    String usuarioLogeado = "VLOPEZ";
    String rol = "ADMIN";
    String accion = "nuevo";
    String tipo = "venta";
    private String codVenta;
    public String codPresupuesto = null;
    
    public FrmVentas(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.usuarioLogeado = usuLogeado;
        this.rol=rol;
        this.setTitle("VENTAS - " + usuarioLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.restricciones();
        this.nuevo();
    }
    
    //ESTE CONSTRUCCTOR SE EJECUTA AL DARLE CLICK EN FACTURAR PRESUPUESTO
    public FrmVentas(String usuLogeado, String rol, String codPresupuesto) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.nuevo();
        this.usuarioLogeado = usuLogeado;
        this.rol=rol;
        this.codPresupuesto = codPresupuesto;
        this.setTitle("VENTAS - " + usuarioLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.restricciones();
        this.facturarPresupuesto(codPresupuesto);
        this.presupuesto(true);
    }
    
    private void restricciones(){   
        aj.soloNumeros(txtCodCliente, 5);
        aj.soloNumeros(txtTimbrado, 8);
        aj.numComprobante(txtNumComprobante);
        aj.soloNumeros(txtCodProducto, 5);
        aj.soloNumeros(txtCantProducto, 5);
        aj.soloNumeros(txtDescuento, 10);
        
        aj.presionarEnter(txtCodigo, cboTipoMto);
        aj.presionarEnter(txtCodCliente, txtCodProducto);
        aj.presionarEnter(txtCodProducto, txtCantProducto);
        aj.presionarEnter(txtCantProducto, btnAgregarProducto);
        aj.presionarEnter(txtDescuento, btnAgregarProducto);
        aj.presionarEnter(txtCaja, txtCodProducto);
        aj.presionarEnter(txtNumComprobante, txtCodProducto);
        aj.presionarEnter(cboConceptoEgreso, txtPrecioProducto);
        aj.presionarEnter(txtPrecioProducto, btnAgregarProducto);
        aj.enterActionPerformed(btnAgregarProducto);
        
    }
    
    private void nuevo(){
        this.accion = "nuevo";
        this.codPresupuesto = null;
        this.presupuesto(false);
        this.limpiarCampos();
        this.esIngreso();
        this.limpiarCamposDetalle();
        this.habilitarCampos(true);   
        fe.mostrarFechaJDateChooser(dateFechaVenta,fe.obtenerFechaActual());
        this.codVenta = bd.obtenerCodMaximoRegistro("ventas_cabecera", "cod_venta_cabecera");
        this.txtTimbrado.setText("16187993");
        this.txtInicioVigencia.setText("01-01-2025");
        this.txtFinVigencia.setText("31-01-2026");
        this.obtenerNumCaja();
        this.obtenerNumComprobante();
        this.txtCodCliente.requestFocus();
    }
        
    private void presupuesto(boolean estado){
        this.cboTipoMto.setEnabled(!estado);
        this.cboTipoMto.setSelectedIndex(0);
        bd.VaciarCombo(cboConcepto);
        bd.agregarItemCombo(cboConcepto, 0, "VENTA CONTADO");
        bd.agregarItemCombo(cboConcepto, 1, "VENTA CREDITO");
        this.cboConcepto.setSelectedIndex(0);
        this.txtCodCliente.setEnabled(!estado);
        this.txtCodProducto.setEnabled(!estado);
        this.btnBuscarCliente.setEnabled(!estado);
        this.btnBuscarProducto.setEnabled(!estado);
    }
    
    private void obtenerNumCaja(){
        int codCaja = 0;
        String [][] cajas = bd.obtenerRegistros("SELECT * FROM cajas WHERE fecha_cierre_caja IS NULL "
                + "AND cod_usuario =(SELECT cod_usuario FROM usuarios WHERE nom_usuario='" + usuarioLogeado + "')");
        if(cajas != null){
            for (int x = 0; x < cajas.length; x++) {
                if(Integer.parseInt(cajas[x][0]) > codCaja){
                    codCaja = Integer.parseInt(cajas[x][0]);
                }
                
                this.txtCaja.setText(String.valueOf(codCaja));
            }
        }
    }
    
    private void obtenerNumComprobante(){
        String [][] ventas = bd.obtenerRegistros("SELECT IFNULL(num_comprobante_venta,'001-001-0000000'), IFNULL(estado_venta, 'ACTIVO'), IFNULL(anulacion_fisica, 'INACTIVO') FROM ventas_cabecera");
        int max=0;
        
        if(ventas != null){
            for (int x = 0; x < ventas.length; x++) {
                if(ventas[x][1].equals("ACTIVO") || ventas[x][2].equals("ACTIVO")){
                    if(obtenerUltimos7Digitos(ventas[x][0]) > max){
                        max = obtenerUltimos7Digitos(ventas[x][0]);
                    }
                }
            }
        }
        
        String cadena = String.format("%07d", max+1);
        this.txtNumComprobante.setText(String.format("001-001-%s", cadena));
    }
    
    public int obtenerUltimos7Digitos(String cadena) {
        // Eliminar los guiones
        String cadenaSinGuiones = cadena.replace("-", "");

        // Obtener los últimos 7 dígitos
        String ultimos7Digitos = cadenaSinGuiones.substring(cadenaSinGuiones.length() - 7);

        // Convertir a entero
        int resultado = Integer.parseInt(ultimos7Digitos);

        return resultado;
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
    
    public void setCuota(String codigo, String nombre, String precio, String iva){
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
        bd.VaciarCombo(cboTipoMto);        
        bd.VaciarCombo(cboConcepto);
        bd.agregarItemCombo(cboTipoMto, 0, "INGRESO");
        bd.agregarItemCombo(cboTipoMto, 1, "EGRESO");
        this.cboTipoMto.setSelectedIndex(0);
        bd.agregarItemCombo(cboConcepto, 0, "VENTA CONTADO");
        bd.agregarItemCombo(cboConcepto, 1, "VENTA CREDITO");
        bd.agregarItemCombo(cboConcepto, 2, "PAGO CUOTA");
        this.cboConcepto.setSelectedIndex(0);
        this.txtCodCliente.setText(null);
        this.txtRucCliente.setText(null);
        this.txtNomCliente.setText(null);
        this.dateFechaVenta.setDate(null);
        this.txtTimbrado.setText(null);
        this.txtNumComprobante.setText(null);
        this.txtInicioVigencia.setText(null);
        this.txtFinVigencia.setText(null);
        this.txtCaja.setText(null);
        this.obtenerNumCaja();
        this.lblUsuarioCreacion.setText(null);
        this.lblUsuarioModificacion.setText(null);
        this.lblFechaCreacion.setText(null);
        this.lblFechaModificacion.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) this.grdDetalleVenta.getModel();
        modelo.setRowCount(0);
        this.txtCodProducto.setText(null);
        this.txtDescripcionProducto.setText(null);
        this.txtCantProducto.setText("1");
        this.txtDescuento.setText("0");
        this.txtPrecioProducto.setText(null);
        this.txtTotal.setText(null);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTAS");
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
        this.btnVerCuotas.setVisible(false);
        this.btnMetodosPago.setVisible(false);
        
    }
    
    private void limpiarCamposDetalle(){
        this.txtCodProducto.setText(null);
        this.txtDescripcionProducto.setText(null);
        this.txtPrecioProducto.setText(null);
        this.txtCantProducto.setText("1");
        this.txtDescuento.setText("0");
        this.txtTotal.setText(null);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTAS");
        bd.agregarItemCombo(cboIva, 1, "5%");
        bd.agregarItemCombo(cboIva, 2, "10%");
        this.cboIva.setSelectedIndex(2);
        if(this.cboTipoMto.getSelectedItem().toString().equals("INGRESO")){
            this.btnAgregarProducto.setEnabled(false);            
        }
    }
        
    private void habilitarCampos(boolean estado){
        this.txtCodCliente.setEditable(estado);
        this.btnBuscarCliente.setEnabled(estado);
        this.txtTimbrado.setEditable(estado);
        this.txtNumComprobante.setEditable(estado);
        this.dateFechaVenta.setFocusable(estado);
        this.btnBuscarProducto.setEnabled(estado);
        this.txtCodProducto.setEditable(estado);
        this.txtCantProducto.setEditable(estado);
        this.txtDescuento.setEditable(estado);
        this.btnGuardar.setEnabled(estado);
        this.btnImprimir.setEnabled(!estado);
        
    }    
    
    private void agregarProducto(){
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        JTable tabla = this.grdDetalleVenta;
        int filas = this.grdDetalleVenta.getRowCount();
        
        if(tipoMto.equals("INGRESO")){
            String codigo = txtCodProducto.getText();
            String nombre = txtDescripcionProducto.getText();
            String cantidad = txtCantProducto.getText();
            String iva = cboIva.getSelectedItem().toString();
            String total = txtTotal.getText();
            String condicion = this.cboConcepto.getSelectedItem().toString();
            boolean existe = false;
            if(condicion.equals("PAGO CUOTA")){
                if(filas > -1){
                    for (int x = 0; x < filas; x++) {
                        String codigoCuota = this.grdDetalleVenta.getValueAt(x, 0).toString();
                        if(codigoCuota.equals(codigo)){  
                            existe = true;
                            System.out.println(codigoCuota + " - " + codigo + " - " + existe);
                        }
                    }
                    if(!existe){
                        agregarFilaDesdeCampos(codigo, nombre, cantidad, "", "", total);
                        limpiarCamposDetalle();
                        txtCodProducto.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null, "No puedes agregar la misma cuota dos veces");
                    }
                }else{
                    agregarFilaDesdeCampos(codigo, nombre, cantidad, "", "", total);
                    limpiarCamposDetalle();
                    txtCodProducto.requestFocus();
                }
            }else{
                if(!codigo.isEmpty()){
                    if(iva == "EXENTAS"){
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
        }else{
            String codigo = String.valueOf(filas + 1);
            String concepto = this.cboConceptoEgreso.getSelectedItem().toString();
            String cantidad = "1";
            String total = this.txtTotal.getText();
            this.agregarFilaDesdeCampos(codigo, concepto, cantidad, "", "", total);
            this.limpiarCamposDetalle();
            this.cboConceptoEgreso.requestFocus();
            
        }
    }
    
    private void agregarFilaDesdeCampos(String codigo, String nombre, String cantidad, String exentas, String iva5, String iva10) {
        DefaultTableModel modelo = (DefaultTableModel) grdDetalleVenta.getModel();
        Object[] fila = {codigo, nombre, cantidad, exentas, iva5, iva10};
        modelo.addRow(fila);
        this.calcularSubTotales();
        gr.alinearDerecha(grdDetalleVenta, 5);
        gr.alinearDerecha(grdDetalleVenta, 4);
        gr.alinearDerecha(grdDetalleVenta, 3);
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
        JTable tabla = this.grdDetalleVenta;
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
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String concepto = this.cboConcepto.getSelectedItem().toString();
        JTable tabla = this.grdDetalleVenta;
        int filas = tabla.getRowCount();
        String codVentaCabecera = this.codVenta;
        String codCliente = this.txtCodCliente.getText();
        if(tipoMto.equals("INGRESO")){
            if(concepto.equals("PAGO CUOTA")){
                for (int x = 0; x < filas; x++) {
                    String codCuota = tabla.getValueAt(x, 0).toString();
                    String [] cuota = bd.obtenerRegistro(   "SELECT \n" +
                                                            "cod_cuotas_credito, \n" +
                                                            "monto_cuota \n" +
                                                            "FROM cuotas_credito_cliente \n" +
                                                            "WHERE cod_cuotas_credito = "+ codCuota);
                    String valores = "estado_cuota='INACTIVO', fecha_pago_cuota=NOW(), num_movimiento=" + codVentaCabecera;
                    String condicion = "cod_cuotas_credito=" + codCuota;
                    bd.modificarRegistro("cuotas_credito_cliente", valores, condicion);



                    String [] credito = bd.obtenerRegistro("SELECT cc.cod_credito_cliente, cc.cuotas_pagadas_credito, cc.saldo_restante_credito FROM credito_cliente cc\n" +
                                                            "JOIN cuotas_credito_cliente ccc ON cc.cod_credito_cliente=ccc.cod_credito_cliente\n" +
                                                            "WHERE ccc.cod_cuotas_credito = "+ codCuota);
                    int cuotasPagadas = Integer.parseInt(credito[1]) + 1;

                    float montoCuota = Float.parseFloat(cuota[1]);
                    float saldoRestante = Float.parseFloat(credito[2]) - montoCuota;
                    String estado = "ACTIVO";
                    if(saldoRestante == 0){
                        estado = "INACTIVO";
                    }
                    String valoresCredito = "cuotas_pagadas_credito=" + cuotasPagadas + ", saldo_restante_credito=" + saldoRestante + ", estado_credito='" + estado + "'";
                    String condicionCredito = "cod_credito_cliente=" + credito[0];
                    bd.modificarRegistro("credito_cliente", valoresCredito, condicionCredito);

                    String [] lineaCredito = bd.obtenerRegistro("select \n" +
                                                                "cod_linea_credito,\n" +
                                                                "monto_total,\n" +
                                                                "saldo_disponible \n" +
                                                                "from lineas_credito\n" +
                                                                "where cod_cliente =" + codCliente);
                    float saldoDisponible = Float.parseFloat(lineaCredito[2]) + montoCuota;
                    bd.modificarRegistro("lineas_credito", "saldo_disponible=" + saldoDisponible, "cod_linea_credito=" + lineaCredito[0]);
                }
            }else{            
                String campos = "cod_venta_detalle, cod_venta_cabecera, cod_producto, precio_producto, cantidad_producto, iva_producto";

                for(int x = 0; x < filas; x++){
                    String codVentaDetalle = bd.obtenerCodMaximoRegistro("ventas_detalle", "cod_venta_detalle");
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
                    String Valores = "" + codVentaDetalle + "," + codVentaCabecera + "," + codProducto + ","
                            + precProducto + "," + cantProducto + ",'" + ivaProducto + "'";
                    bd.insertarRegistro("ventas_detalle", campos, Valores);
                    float existencia = Float.parseFloat(bd.obtenerRegistro("select existencia_producto from productos where cod_producto = " + codProducto)[0]);
                    float nuevaExistencia = existencia - Float.parseFloat(cantProducto);
                    bd.modificarRegistro("productos", "existencia_producto=" + nuevaExistencia, "cod_producto=" + codProducto);
                    if(this.codPresupuesto != null){
                        bd.modificarRegistro("presupuestos", "estado_presupuesto='INACTIVO', num_facturacion=" + codVenta, "cod_presupuesto=" + this.codPresupuesto);
                    }
                }        
            }   
        }else{
            for (int x = 0; x < filas; x++) {
                String codDetalleEgreso = bd.obtenerCodMaximoRegistro("detalle_egreso_venta", "cod_detalle_egreso");
                String conceptoEgreso = tabla.getValueAt(x, 1).toString();
                String montoEgreso = tabla.getValueAt(x, 5).toString();
                
                String camposEgreso = "cod_detalle_egreso, cod_venta_cabecera, concepto_egreso, monto_egreso";
                String valoresEgreso = codDetalleEgreso + ", " + codVentaCabecera + ", '" + conceptoEgreso + "', " + montoEgreso;
                bd.insertarRegistro("detalle_egreso_venta", camposEgreso, valoresEgreso);
            }
        }
    }
    
    private void ventaAnterior(int a){
        int codigo = codigo = Integer.parseInt(this.codVenta) - 1 - a;
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
        this.accion = "anterior";
        String sql = "select * from ventas_cabecera where cod_venta_cabecera = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos == null){
            if(codigo >= 0){
                ventaAnterior(a + 1);    
            }
        }else{
            if(datos[10].equals("ACTIVO")){
                this.mostrarVenta(datos[0]);
                this.codVenta = String.valueOf(codigo);
            }else{
                ventaAnterior(a + 1);
            }
        }
    }
    
    private void ventaPosterior(int a){
        int codigo  = Integer.parseInt(this.codVenta) + 1 + a;         
        this.habilitarCampos(false);
        this.btnAnular.setEnabled(true);
        String sql = "select * from ventas_cabecera where cod_venta_cabecera = " + codigo;
        String [] datos = bd.obtenerRegistro(sql);
        
        if(datos != null){
            if(datos[10].equals("ACTIVO")){
                this.mostrarVenta(datos[0]);
                this.codVenta = String.valueOf(codigo);                
            }else{
                ventaPosterior(a + 1);
            }   
        }
    }
    
    private void mostrarVenta(String codVenta){
        this.codVenta = codVenta;
        String sql = 
                "SELECT * FROM ventas_cabecera where cod_venta_cabecera = " + codVenta ;
        String [] datos = bd.obtenerRegistro(sql);
        if(datos != null){
            this.txtCodigo.setText(datos[0]);
            bd.VaciarCombo(cboTipoMto);
            bd.agregarItemCombo(cboTipoMto, 0, datos[1]);
            this.verificarTipoMto();
            bd.VaciarCombo(cboConcepto);
            bd.agregarItemCombo(cboConcepto, 0, datos[2]);
            this.txtCaja.setText(datos[3]);
            this.txtCodCliente.setText(datos[4]);
            this.txtRucCliente.setText(datos[5]);
            this.txtNomCliente.setText(datos[6]);
            bd.mostrarFechaJDateChooser(dateFechaVenta, datos[7]);
            this.txtTimbrado.setText(datos[8]);
            this.txtNumComprobante.setText(datos[9]);
            this.lblUsuarioCreacion.setText(datos[11]);
            this.lblUsuarioModificacion.setText(datos[12]);
            this.lblFechaCreacion.setText(datos[13]);
            this.lblFechaModificacion.setText(datos[14]);
            DefaultTableModel modelo = (DefaultTableModel) grdDetalleVenta.getModel();
            modelo.setRowCount(0);
            this.obtenerDetalleCompra(Integer.parseInt(codVenta), datos[1], datos[2]);

            if(datos[1].equals("INGRESO")){
                if(datos[2].equals("VENTA CREDITO")){
                    this.btnVerCuotas.setVisible(true);
                    this.btnMetodosPago.setVisible(false);
                }else{
                    this.btnVerCuotas.setVisible(false);
                    this.btnMetodosPago.setVisible(true);
                }
            }
        }
    }
    
    private void obtenerDetalleCompra(int codVenta, String tipoMto, String condicion){
        String sql = "";
        if(tipoMto.equals("INGRESO")){    
            if(condicion.equals("PAGO CUOTA")){
                sql =    "SELECT ccc.cod_cuotas_credito, \n" +
                                "CONCAT(cl.nombre_cliente,', ',cl.apellido_cliente) AS cliente,\n" +
                                "'1' AS cantidad,\n" +
                                "ccc.monto_cuota,\n" +
                                "'10%' AS iva\n" +
                                "FROM cuotas_credito_cliente ccc\n" +
                                "JOIN credito_cliente cc ON ccc.cod_credito_cliente=cc.cod_credito_cliente\n" +
                                "JOIN ventas_cabecera vc ON cc.cod_venta = vc.cod_venta_cabecera\n" +
                                "JOIN clientes cl ON vc.cod_cliente = cl.cod_cliente\n" +
                                "WHERE ccc.estado_cuota = 'INACTIVO' AND ccc.num_movimiento = "+ codVenta;
            }else{            
                sql = "SELECT v.cod_producto, p.nombre_producto, v.cantidad_producto, v.precio_producto, v.iva_producto FROM ventas_detalle v\n" +
                            "JOIN productos p ON v.cod_producto = p.cod_producto\n" +
                            "WHERE cod_venta_cabecera =" + codVenta;
            }
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
                        this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), String.format("%.0f", precio), "", "");
                    }else{
                        if(iva.equals("5%")){
                            this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), "", String.format("%.0f", precio), "");
                        }else{
                            this.agregarFilaDesdeCampos(codigo, nombre, String.format("%.0f", cantidad), "", "", String.format("%.0f", precio));
                        }
                    }   

                }
            }
        }else{
            sql =   "SELECT \n" +
                    "concepto_egreso,\n" +
                    "monto_egreso\n" +
                    "FROM detalle_egreso_venta\n" +
                    "WHERE cod_venta_cabecera =" + codVenta;
            String [][] egresosDetalle = bd.obtenerRegistros(sql);
            if(egresosDetalle != null){
                for (int x = 0; x < egresosDetalle.length; x++) {
                    String codigo = String.valueOf(x + 1);
                    String concepto = egresosDetalle[x][0];
                    String cantidad = "1";
                    String precio = egresosDetalle[x][1];
                    this.agregarFilaDesdeCampos(codigo, concepto,  cantidad, "", "", precio);                    
                }
            }
        }
    }
   
    public void guardarVentaCredito(String numCuotas, String plazo, String vencimiento, String [][] cuotas){
        String codVenta = this.codVenta;
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String concepto = this.cboConcepto.getSelectedItem().toString();
        String fechaVenta = bd.formatoFecha(this.dateFechaVenta);
        String codCli = this.txtCodCliente.getText();
        String rucCli = this.txtRucCliente.getText();
        String nomCli= this.txtNomCliente.getText();
        String numCaja = this.txtCaja.getText();
        String timbrado = this.txtTimbrado.getText();
        String numComp = this.txtNumComprobante.getText();
        String subTotal = this.txtTotalGeneral1.getText();        
        int items = this.grdDetalleVenta.getRowCount();
        String [] lineaCredito = bd.obtenerRegistro("select cod_linea_credito, saldo_disponible, fecha_vencimiento from lineas_credito where cod_cliente=" + codCli);
        
        
        if(lineaCredito != null){
            if(fe.compararFechas_dd_MM_yyyy(fechaVenta, lineaCredito[2])){
                if(Float.parseFloat(subTotal) <= Float.parseFloat(lineaCredito[1])){  
                    String campos = "cod_venta_cabecera, "
                        + "tipo_movimiento, "
                        + "concepto_movimiento, "
                        + "cod_caja, "
                        + "cod_cliente, "
                        + "ruc_cliente, "
                        + "razon_social_cliente, "
                        + "fecha_venta, "
                        + "timbrado_venta, "
                        + "num_comprobante_venta, "
                        + "estado_venta, "
                        + "usuario_insercion, "
                        + "fecha_insercion";

                    String valores = codVenta + ","
                            + "'" + tipoMto + "',"
                            + "'" + concepto + "',"
                            + "" + numCaja + ","
                            + "" + codCli + ","
                            + "'" + rucCli + "',"
                            + "'" + nomCli + "',"
                            + "'" + fechaVenta + "',"
                            + "'" + timbrado + "',"
                            + "'" + numComp + "',"
                            + "'ACTIVO',"
                            + "'" + usuarioLogeado + "',"
                            + "NOW()";
                    String tabla = "ventas_cabecera";
                    bd.insertarRegistro(tabla, campos, valores);
                    this.guardarDetalle();

                    String codCredito = bd.obtenerCodMaximoRegistro("credito_cliente", "cod_credito_cliente");


                    String CamposCredito = " cod_credito_cliente, "
                            + "cod_venta, "
                            + "numero_cuotas_credito,"
                            + "cuotas_pagadas_credito, "
                            + "plazo_cuotas_credito, "
                            + "fecha_inicio_credito, "
                            + "fecha_vencimiento_credito, "
                            + "saldo_restante_credito, "
                            + "estado_credito";
                    String valoresCredito = codCredito + ","
                            + "" + codVenta + ","
                            + "" + numCuotas + ","
                            + "0,"
                            + "'" + plazo + "',"
                            + "'" + fechaVenta + "',"
                            + "'" + vencimiento + "',"
                            + "" + subTotal + ","
                            + "'ACTIVO'" ;
                    String tablaC = "credito_cliente";
                    bd.insertarRegistro(tablaC, CamposCredito, valoresCredito);

                    for (int x = 0; x < cuotas.length; x++) {
                        String codCuota = bd.obtenerCodMaximoRegistro("cuotas_credito_cliente", "cod_cuotas_credito");
                        String numCuota = "";
                        String montoCuota = "";
                        String fechaVencimientoCuota = "";
                        String estadoCuota = "";

                        String camposCuota = "cod_cuotas_credito,"
                                + "cod_credito_cliente,"
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
                        bd.insertarRegistro("cuotas_credito_cliente", camposCuota, valoresCuota);
                    }
                    float saldo = Float.parseFloat(lineaCredito[1]) - Float.parseFloat(subTotal);
                    bd.modificarRegistro("lineas_credito", "saldo_disponible=" + (saldo), "cod_linea_credito=" + lineaCredito[0]);

                    JOptionPane.showMessageDialog(null, "Movimiento Guardado");
                    this.accion = "guardado";
                    this.ventaAnterior(-1);
                }else{
                    JOptionPane.showMessageDialog(null, "El cliente no tiene linea de credito suficiente para esta operacion");
                }
            }else{
                JOptionPane.showMessageDialog(null, "La linea de credito esta vencida");
            }
        }else{
            JOptionPane.showMessageDialog(null, "EL CLIENTE NO CUENTA CON UNA LINEA DE CREDITO");
        }   
    }
    
    public void guardarVentaContado(String [][] metodoPago){
        String codCompra = this.codVenta;
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String concepto = this.cboConcepto.getSelectedItem().toString();
        String fechaVent = bd.formatoFecha(this.dateFechaVenta);
        String codCli = this.txtCodCliente.getText();
        String rucCli = this.txtRucCliente.getText();
        String nomCli= this.txtNomCliente.getText();
        String numCaja = this.txtCaja.getText();
        String timbrado = this.txtTimbrado.getText();
        String numComp = this.txtNumComprobante.getText();
        
        String campos = "cod_venta_cabecera, tipo_movimiento, concepto_movimiento, cod_caja, cod_cliente, ruc_cliente, razon_social_cliente, "
                + "fecha_venta, timbrado_venta, num_comprobante_venta, estado_venta, usuario_insercion, fecha_insercion";

        String valores = codCompra + ",'" + tipoMto + "','" + concepto + "'," + numCaja + "," + codCli + ",'" + rucCli + "','"
                + nomCli + "','" + fechaVent + "','" + timbrado + "','" + numComp + "','ACTIVO','" + usuarioLogeado + "',NOW()";
        String tabla = "ventas_cabecera";
        bd.insertarRegistro(tabla, campos, valores);
        this.guardarMetodoPago(metodoPago);
        this.guardarDetalle();
        
        JOptionPane.showMessageDialog(null, "MOVIMIENTO GUARDADO");
        this.accion = "guardado";
        this.ventaAnterior(-1);
    }
   
    public void guardarEgreso(){
        String codCompra = this.codVenta;
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String fechaVent = bd.formatoFecha(this.dateFechaVenta);
        String codCli = this.txtCodCliente.getText();
        String rucCli = this.txtRucCliente.getText();
        String nomCli= this.txtNomCliente.getText();
        String numCaja = this.txtCaja.getText();
        
        String campos = "cod_venta_cabecera, "
                + "tipo_movimiento, "
                + "cod_caja, "
                + "cod_cliente, "
                + "ruc_cliente, "
                + "razon_social_cliente, "
                + "fecha_venta, "
                + "estado_venta, "
                + "usuario_insercion, "
                + "fecha_insercion";

        String valores = codCompra + ","
                + "'" + tipoMto + "',"
                + "" + numCaja + ","
                + "" + codCli + ","
                + "'" + rucCli + "',"
                + "'" + nomCli + "',"
                + "'" + fechaVent + "',"
                + "'ACTIVO',"
                + "'" + usuarioLogeado + "'"
                + ",NOW()";
        String tabla = "ventas_cabecera";
        bd.insertarRegistro(tabla, campos, valores);
        this.guardarDetalle();
        JOptionPane.showMessageDialog(null, "Movimiento Guardado");
        this.accion = "guardado";
        this.ventaAnterior(-1);
    }
    
    public void guardarPagoCuota(String [][] metodoPago){
        String codCompra = this.codVenta;
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String concepto = this.cboConcepto.getSelectedItem().toString();
        String fechaVent = bd.formatoFecha(this.dateFechaVenta);
        String codCli = this.txtCodCliente.getText();
        String rucCli = this.txtRucCliente.getText();
        String nomCli= this.txtNomCliente.getText();
        String numCaja = this.txtCaja.getText();
        
        String campos = "cod_venta_cabecera, tipo_movimiento, concepto_movimiento, cod_caja, cod_cliente, ruc_cliente, razon_social_cliente, "
                + "fecha_venta, estado_venta, usuario_insercion, fecha_insercion";

        String valores = codCompra + ",'" + tipoMto + "','" + concepto + "'," + numCaja + "," + codCli + ",'" + rucCli + "','"
                + nomCli + "','" + fechaVent + "','ACTIVO','" + usuarioLogeado + "',NOW()";
        String tabla = "ventas_cabecera";
        bd.insertarRegistro(tabla, campos, valores);
        this.guardarMetodoPago(metodoPago);
        this.guardarDetalle();
        JOptionPane.showMessageDialog(null, "Movimiento Guardado");
        this.accion = "guardado";
        this.ventaAnterior(-1);
    }
    
    public void guardarMetodoPago(String [][] metodoPago){
        for (int x = 0; x < metodoPago.length; x++) {
            String codMetodoPago = bd.obtenerCodMaximoRegistro("metodo_pago_venta", "cod_metodo_pago_venta");
            String codVenta = this.codVenta;
            String metodo = "";
            String monto = "";
            String banco = "";
            
            for (int i = 0; i < metodoPago[x].length; i++) {
                switch (i) {
                    case 0:
                        metodo = metodoPago[x][i];
                        break;
                    case 1:
                        monto = metodoPago[x][i];
                        break;
                    case 2:
                        banco = metodoPago[x][i];
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            if(!banco.equals("NULL")){
                int codBanco = bd.obtenerCodigo("select cod_banco from bancos where nombre_banco = '" + banco + "'");                    
                String campos = "cod_metodo_pago_venta, cod_venta, metodo_pago_venta, monto_venta, cod_banco";
                String valores = "" + codMetodoPago + "," + codVenta + ",'" + metodo + "'," + monto + "," + codBanco;
                bd.insertarRegistro("metodo_pago_venta", campos, valores);                
            }else{
                String campos = "cod_metodo_pago_venta, cod_venta, metodo_pago_venta, monto_venta";
                String valores = "" + codMetodoPago + "," + codVenta + ",'" + metodo + "'," + monto;
                bd.insertarRegistro("metodo_pago_venta", campos, valores);   
            }
        }
    }
   
    private String verifiacarStock(){
        JTable tabla = this.grdDetalleVenta;
        int filas = tabla.getRowCount();
        
        for (int x = 0; x < filas; x++) {
            float cantidad = Float.parseFloat(tabla.getValueAt(x, 2).toString());
            String codigo = tabla.getValueAt(x, 0).toString();
            float existencia = Float.parseFloat(bd.obtenerRegistro("select existencia_producto from productos where cod_producto =" + codigo)[0]);
            System.out.println("exitencia del producto = " + existencia);
            System.out.println("cantidad del producto = " + cantidad);
            if(cantidad > existencia){
                return codigo;
            }
            
        }
        
        return "-1";
    }
    
    private boolean verificarNumCaja(){
        String numCaja = this.txtCaja.getText();
        if(!numCaja.isEmpty()){
            String [] cajas = bd.obtenerRegistro("select c.cod_caja, u.nom_usuario from cajas c "
                    + " join usuarios u on c.cod_usuario=u.cod_usuario"
                    + " where cod_caja=" + numCaja + " and fecha_cierre_caja IS NULL");
            if(cajas != null){
                for (int x = 0; x < cajas.length; x++) {
                    if(cajas[1].equals(usuarioLogeado) || rol.equals("ADMIN")){
                        return true;
                    }else{
                        JOptionPane.showMessageDialog(null, "El numero de caja no corresponde al cajero logeado");
                    }                    
                }
            }else{
                JOptionPane.showMessageDialog(null, "El numero de caja no existe o esta cerrada");
            }
        }else{
            JOptionPane.showMessageDialog(null, "El numero de caja no puede estar vacia");
            this.txtCaja.requestFocus();
        }
        
        return false;
    }
    
    private void esPagoCuota(){
        this.txtTimbrado.setText(null);
        this.txtNumComprobante.setText(null);
        this.txtInicioVigencia.setText(null);
        this.txtFinVigencia.setText(null);
        this.txtTimbrado.setEditable(false);
        this.txtNumComprobante.setEditable(false);
        
        this.txtCantProducto.setEditable(false);
        this.txtDescuento.setEditable(false);
        this.txtPrecioProducto.setEditable(false);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "10%");
    }
    
    private void noEsPagoCuota(){
        this.txtTimbrado.setText("16187993");
        this.txtInicioVigencia.setText("01-01-2025");
        this.txtFinVigencia.setText("31-01-2026");
        this.txtTimbrado.setEditable(true);
        this.txtNumComprobante.setEditable(true);
        this.obtenerNumComprobante();
        
        this.txtCantProducto.setEditable(true);
        this.txtDescuento.setEditable(true);
        this.txtPrecioProducto.setEditable(true);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTAS");
        bd.agregarItemCombo(cboIva, 1, "5%");
        bd.agregarItemCombo(cboIva, 2, "10%");
        this.cboIva.setSelectedIndex(0);
    }
    
    
    private void esIngreso(){
        this.lblConcepto.setVisible(true);
        this.cboConcepto.setVisible(true);
        this.txtTimbrado.setText("16187993");
        this.txtInicioVigencia.setText("01-01-2025");
        this.txtFinVigencia.setText("31-01-2026");
        this.txtTimbrado.setEditable(true);
        this.txtNumComprobante.setEditable(true);
        this.obtenerNumComprobante();
        this.limpiarCamposDetalle();
        gr.vaciar(grdDetalleVenta);
        this.lblCodProducto.setVisible(true);
        this.lblDescripcionProducto.setVisible(true);
        this.lblCantidad.setVisible(true);
        this.txtCodProducto.setVisible(true);
        this.txtPrecioProducto.setEditable(false);
        this.txtDescripcionProducto.setVisible(true);

        this.txtDescuento.setVisible(true);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "EXENTAS");
        bd.agregarItemCombo(cboIva, 1, "5%");
        bd.agregarItemCombo(cboIva, 2, "10%");
        this.btnAgregarProducto.setEnabled(false);
        
        this.txtCantProducto.setVisible(true);
        this.cboConceptoEgreso.setVisible(false);
    }
    
    private void esEgreso(){
        this.lblConcepto.setVisible(false);
        this.cboConcepto.setVisible(false);
        this.txtTimbrado.setText(null);
        this.txtInicioVigencia.setText(null);
        this.txtFinVigencia.setText(null);
        this.txtTimbrado.setEditable(false);
        this.txtNumComprobante.setText(null);
        this.txtNumComprobante.setEditable(false);
        this.limpiarCamposDetalle();
        gr.vaciar(grdDetalleVenta);this.lblCodProducto.setVisible(true);
        this.lblDescripcionProducto.setVisible(false);
        this.lblCantidad.setVisible(false);
        this.txtCodProducto.setVisible(false);
        this.txtDescripcionProducto.setVisible(false);
        this.txtCantProducto.setVisible(false);
        this.cboConceptoEgreso.setVisible(true);
        this.txtPrecioProducto.setEditable(true);
        this.txtDescripcionProducto.setVisible(false);
        
        this.txtDescuento.setVisible(false);
        bd.VaciarCombo(cboIva);
        bd.agregarItemCombo(cboIva, 0, "10%");
        this.btnAgregarProducto.setEnabled(true);
        
        bd.VaciarCombo(cboConceptoEgreso);
        bd.agregarItemCombo(cboConceptoEgreso, 0, "GASTOS VARIOS");
        bd.agregarItemCombo(cboConceptoEgreso, 1, "PAGOS POR SERVICIOS");
        this.cboConceptoEgreso.setSelectedIndex(0);
    }
    
    private void verificarTipoMto(){
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        if(tipoMto.equals("INGRESO")){
            this.esIngreso();
        }else{
            this.esEgreso();            
        }
    }
    
    public void facturarPresupuesto(String codPresupuesto){
        String [] presupuesto = bd.obtenerRegistro( "SELECT\n" +
                                                    "pr.cod_presupuesto,\n" +
                                                    "pr.cod_cliente,\n" +
                                                    "CONCAT(cl.apellido_cliente,', ', cl.nombre_cliente) AS cliente,\n" +
                                                    "cl.ruc_cliente\n" +
                                                    "FROM presupuestos pr\n" +
                                                    "JOIN clientes cl ON pr.cod_cliente=cl.cod_cliente\n" +
                                                    "WHERE pr.cod_presupuesto=" + codPresupuesto);
        if(presupuesto != null){
            this.txtCodCliente.setText(presupuesto[1]);
            this.txtNomCliente.setText(presupuesto[2]);
            this.txtRucCliente.setText(presupuesto[3]);

            String [][] detallePresupuesto = bd.obtenerRegistros(   "SELECT\n" +
                                                                    "pd.cod_producto AS codigo,\n" +
                                                                    "p.nombre_producto as concepto,\n" +
                                                                    "pd.cantidad_producto AS cantidad,\n" +
                                                                    "pd.precio_producto as precio,\n" +
                                                                    "pd.iva_producto\n" +
                                                                    "FROM presupuesto_detalle pd\n" +
                                                                    "JOIN productos p ON pd.cod_producto = p.cod_producto\n" +
                                                                    "WHERE pd.cod_presupuesto_cabecera =" + codPresupuesto);
            if(detallePresupuesto != null){
                for (int x = 0; x < detallePresupuesto.length; x++) {
                    String codigo = null;
                    String nombre = null;
                    float cantidad = 0;
                    float precio = 0;  
                    String iva = null;
                    float total;
                    for(int i = 0; i < detallePresupuesto[x].length; i++){
                        codigo = detallePresupuesto[x][0];
                        nombre = detallePresupuesto[x][1];
                        cantidad = Float.parseFloat(detallePresupuesto[x][2]);
                        precio = Float.parseFloat(detallePresupuesto[x][3]);
                        iva = detallePresupuesto[x][4];
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
            }
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
        cboTipoMto = new javax.swing.JComboBox<>();
        cboConcepto = new javax.swing.JComboBox<>();
        txtCaja = new javax.swing.JTextField();
        txtCodCliente = new javax.swing.JTextField();
        txtNomCliente = new javax.swing.JTextField();
        txtRucCliente = new javax.swing.JTextField();
        btnVerCuotas = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblConcepto = new javax.swing.JLabel();
        dateFechaVenta = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        btnMetodosPago = new javax.swing.JToggleButton();
        btnBuscarCaja = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cboConceptoEgreso = new javax.swing.JComboBox<>();
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
        grdDetalleVenta = new javax.swing.JTable();
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
        jPanel3 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JToggleButton();
        btnGuardar = new javax.swing.JToggleButton();
        btnAnular = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        btnInforme = new javax.swing.JToggleButton();
        jPanel7 = new javax.swing.JPanel();
        txtTimbrado = new javax.swing.JTextField();
        txtNumComprobante = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtInicioVigencia = new javax.swing.JTextField();
        txtFinVigencia = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuReportes = new javax.swing.JMenu();
        itmInformeVentas = new javax.swing.JMenuItem();
        itmInformeVentasAnuladas = new javax.swing.JMenuItem();
        itmDeudasClientes = new javax.swing.JMenuItem();
        itmCreditosClientes = new javax.swing.JMenuItem();
        itmPresupuestos = new javax.swing.JMenuItem();

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
        panel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 132, 30));

        cboTipoMto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESO", "EGRESO" }));
        cboTipoMto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboTipoMto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cboTipoMtoFocusLost(evt);
            }
        });
        panel1.add(cboTipoMto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 100, 30));

        cboConcepto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VENTA CONTADO", "VENTA CREDITO" }));
        cboConcepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboConcepto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cboConceptoFocusLost(evt);
            }
        });
        cboConcepto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cboConceptoMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cboConceptoMouseReleased(evt);
            }
        });
        cboConcepto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboConceptoPropertyChange(evt);
            }
        });
        panel1.add(cboConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 140, 30));

        txtCaja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel1.add(txtCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 100, 30));

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

        btnVerCuotas.setBackground(new java.awt.Color(63, 248, 63));
        btnVerCuotas.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnVerCuotas.setText("Cuotas");
        btnVerCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCuotasActionPerformed(evt);
            }
        });
        panel1.add(btnVerCuotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 80, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tipo");
        panel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 30, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Fecha");
        panel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Codigo");
        panel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 40, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Nombre o Razon Social");
        panel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 140, 30));

        lblConcepto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblConcepto.setForeground(new java.awt.Color(255, 255, 255));
        lblConcepto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConcepto.setText("Concepto");
        panel1.add(lblConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 60, 30));

        dateFechaVenta.setBackground(new java.awt.Color(255, 255, 255));
        dateFechaVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateFechaVenta.setDateFormatString("dd-MM-yyyy");
        dateFechaVenta.setFocusable(false);
        panel1.add(dateFechaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 140, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Caja");
        panel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, 50, 30));

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

        btnMetodosPago.setBackground(new java.awt.Color(63, 248, 63));
        btnMetodosPago.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnMetodosPago.setText("Metodo P");
        btnMetodosPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodosPagoActionPerformed(evt);
            }
        });
        panel1.add(btnMetodosPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, 80, -1));

        btnBuscarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCajaActionPerformed(evt);
            }
        });
        panel1.add(btnBuscarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 30, 30));

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 820, 130));

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.add(cboConceptoEgreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 660, 30));

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

        txtPrecioProducto.setEditable(false);
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

        grdDetalleVenta.setBackground(new java.awt.Color(0, 153, 255));
        grdDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        grdDetalleVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        grdDetalleVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdDetalleVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdDetalleVenta);
        if (grdDetalleVenta.getColumnModel().getColumnCount() > 0) {
            grdDetalleVenta.getColumnModel().getColumn(0).setMaxWidth(200);
            grdDetalleVenta.getColumnModel().getColumn(2).setMinWidth(100);
            grdDetalleVenta.getColumnModel().getColumn(2).setPreferredWidth(100);
            grdDetalleVenta.getColumnModel().getColumn(2).setMaxWidth(200);
            grdDetalleVenta.getColumnModel().getColumn(3).setMinWidth(100);
            grdDetalleVenta.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdDetalleVenta.getColumnModel().getColumn(3).setMaxWidth(400);
            grdDetalleVenta.getColumnModel().getColumn(4).setMinWidth(100);
            grdDetalleVenta.getColumnModel().getColumn(4).setPreferredWidth(150);
            grdDetalleVenta.getColumnModel().getColumn(4).setMaxWidth(400);
            grdDetalleVenta.getColumnModel().getColumn(5).setMinWidth(100);
            grdDetalleVenta.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdDetalleVenta.getColumnModel().getColumn(5).setMaxWidth(400);
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

        jPanel3.setBackground(new java.awt.Color(51, 102, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel3.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 40, 40));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, 40));

        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 40, 40));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 40, 40));

        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informe.png"))); // NOI18N
        btnInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeActionPerformed(evt);
            }
        });
        jPanel3.add(btnInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 40, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 10, 160, 130));

        jPanel7.setBackground(new java.awt.Color(51, 102, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimbrado.setEditable(false);
        txtTimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbrado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(txtTimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 180, 30));

        txtNumComprobante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumComprobante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNumComprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumComprobanteFocusLost(evt);
            }
        });
        jPanel7.add(txtNumComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 180, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("N° Factura");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 60, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Timbrado");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 30));

        txtInicioVigencia.setEditable(false);
        txtInicioVigencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicioVigencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(txtInicioVigencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 30));

        txtFinVigencia.setEditable(false);
        txtFinVigencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinVigencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.add(txtFinVigencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 120, 30));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 270, 130));

        menuReportes.setText("Reportes");

        itmInformeVentas.setText("Ventas");
        itmInformeVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmInformeVentasActionPerformed(evt);
            }
        });
        menuReportes.add(itmInformeVentas);

        itmInformeVentasAnuladas.setText("Ventas Anuladas");
        itmInformeVentasAnuladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmInformeVentasAnuladasActionPerformed(evt);
            }
        });
        menuReportes.add(itmInformeVentasAnuladas);

        itmDeudasClientes.setText("Deudas Clientes");
        itmDeudasClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeudasClientesActionPerformed(evt);
            }
        });
        menuReportes.add(itmDeudasClientes);

        itmCreditosClientes.setText("Creditos Clientes");
        itmCreditosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmCreditosClientesActionPerformed(evt);
            }
        });
        menuReportes.add(itmCreditosClientes);

        itmPresupuestos.setText("Ver Presupuestos");
        itmPresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmPresupuestosActionPerformed(evt);
            }
        });
        menuReportes.add(itmPresupuestos);

        jMenuBar1.add(menuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1289, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String codCompra = this.codVenta;
        String tipoMto = this.cboTipoMto.getSelectedItem().toString();
        String concepto = this.cboConcepto.getSelectedItem().toString();
        String fechaVent = bd.formatoFecha(this.dateFechaVenta);
        String codCli = this.txtCodCliente.getText();
        String rucCli = this.txtRucCliente.getText();
        String nomCli= this.txtNomCliente.getText();
        String numCaja = this.txtCaja.getText();
        String timbrado = this.txtTimbrado.getText();
        String numComp = this.txtNumComprobante.getText();      
        int items = this.grdDetalleVenta.getRowCount();
        String totalVenta = this.txtTotalGeneral1.getText();
        
        if(this.accion.equals("nuevo")){
            if(!codCli.isEmpty()){
                if(this.dateFechaVenta.getDate() != null){
                    if(this.verificarNumCaja()){
                        if(items > 0){
                            if(items <= 10){
                                if(tipoMto.equals("INGRESO")){
                                    if(concepto.equals("VENTA CONTADO")){                                        
                                        if(verifiacarStock().equals("-1")){
                                            JDMetodoPago mt = new JDMetodoPago(this, true, this, Float.parseFloat(totalVenta), concepto);
                                            mt.setVisible(true);
                                        }else{
                                            JOptionPane.showMessageDialog(null, "La exitencia del producto " + verifiacarStock() + " es insuficiente");
                                        }                                
                                    }else if(concepto.equals("VENTA CREDITO")){
                                        if(verifiacarStock().equals("-1")){
                                            String fechaC = fe.formatoFechaBDaJava(fe.formatoFecha(dateFechaVenta));
                                            Float monto = Float.parseFloat(this.txtTotalGeneral1.getText());
                                            JDCuotasVenta fc = new JDCuotasVenta(this, true, this, monto, fechaC, null);
                                            fc.setVisible(true);
                                        }else{
                                            JOptionPane.showMessageDialog(null, "La exitencia del producto " + verifiacarStock() + " es insuficiente");
                                        } 
                                    }else if(concepto.equals("PAGO CUOTA")){
                                        JDMetodoPago mt = new JDMetodoPago(this, true, this, Float.parseFloat(totalVenta), concepto);
                                        mt.setVisible(true);
                                    }                                    
                                }else{
                                    //CUANDO ES UN EGRESO
                                    this.guardarEgreso();
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "No se pueden cargar mas de 10 articulos por venta");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debes cargar un producto");
                            this.txtCodProducto.requestFocus();
                        }
                    }                    
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese la fecha del comprobante");
                    this.dateFechaVenta.requestFocus();
                }                    
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione al Cliente");
                this.btnBuscarCliente.requestFocus();
            }                    
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void grdDetalleVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdDetalleVentaMouseClicked
        if(this.grdDetalleVenta.getSelectedRow()>=0 && accion == "nuevo" && this.codPresupuesto == null){
            this.btnEliminarProducto.setEnabled(true);
        }else{
            this.btnEliminarProducto.setEnabled(false);
        }
    }//GEN-LAST:event_grdDetalleVentaMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        if(this.txtCodigo.getText().isEmpty()){
            this.ventaAnterior(0); 
        }else{
            int txtcodigo = Integer.parseInt( this.txtCodigo.getText())-1;
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("ventas_cabecera", "cod_venta_cabecera")) - 1;      
            if(txtcodigo < codigo && txtcodigo > 0){
                String estado = bd.obtenerRegistro("select estado_venta from ventas_cabecera WHERE cod_venta_cabecera =" + txtcodigo)[0];
                if(estado != null){
                    if(estado.equals("ACTIVO")){
                        this.mostrarVenta(String.valueOf(txtcodigo));    
                    }else{
                        ventaAnterior(0);
                    }                    
                }
            }
        }
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        if(this.txtCodigo.getText().isEmpty()){
            this.ventaPosterior(0); 
        }else{
            int txtcodigo = Integer.parseInt( this.txtCodigo.getText())+1;
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("ventas_cabecera", "cod_venta_cabecera")) - 1;   
            if(txtcodigo < codigo && txtcodigo > 0){
                String estado = bd.obtenerRegistro("select estado_venta from ventas_cabecera WHERE cod_venta_cabecera =" + txtcodigo)[0];
                if(estado != null){
                    if(estado.equals("ACTIVO")){
                        this.mostrarVenta(String.valueOf(txtcodigo));   
                    }else{
                        ventaPosterior(0);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        if(rol.equals("ADMIN")){
            String codigo = this.txtCodigo.getText();
            String codCliente = this.txtCodCliente.getText();
            String [] datos = bd.obtenerRegistro("Select cod_venta_cabecera, concepto_movimiento, estado_venta from ventas_cabecera where cod_venta_cabecera = " + codigo);
            String tipoMto = datos[1];

            if(tipoMto.equals("VENTA CONTADO")){
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar este registro?", 
                "Confirmación de Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    int confirmacion2 = JOptionPane.showConfirmDialog(null, 
                    "¿Anular Fisicamente?", 
                    "Confirmación de Eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                    if (confirmacion2 == JOptionPane.YES_OPTION) {
                        bd.modificarRegistro("ventas_cabecera", "estado_venta = 'INACTIVO', anulacion_fisica='ACTIVO'", "cod_venta_cabecera = " + codigo);
                        String [][] detalleVenta = bd.obtenerRegistros(
                                " SELECT\n" +
                                "vd.cod_producto,\n" +
                                "vd.cantidad_producto\n" +
                                "FROM ventas_detalle vd\n" +
                                "JOIN ventas_cabecera vc ON vd.cod_venta_cabecera=vc.cod_venta_cabecera\n" +
                                "WHERE vd.cod_venta_cabecera =" + codigo
                        );
                        for (int x = 0; x < detalleVenta.length; x++) {
                            String [] producto = bd.obtenerRegistro("select existencia_producto from productos");
                            float existencia = Float.parseFloat(producto[0]) + Float.parseFloat(detalleVenta[x][1]);
                            bd.modificarRegistro("productos", "existencia_producto=" + existencia, "cod_producto=" + detalleVenta[x][0]);
                        }
                        String [] presupuesto = bd.obtenerRegistro("SELECT * FROM presupuestos WHERE num_facturacion=" + codigo);
                        if(presupuesto != null){
                            bd.modificarRegistro("presupuestos", "estado_presupuesto='ACTIVO', num_facturacion=(NULL)", "cod_presupuesto=" + presupuesto[0]);
                        }
                        JOptionPane.showMessageDialog(  null, "VENTA ANULADA");
                        this.nuevo();
                    }else{
                        bd.modificarRegistro("ventas_cabecera", "estado_venta = 'INACTIVO', anulacion_fisica='INACTIVO'", "cod_venta_cabecera = " + codigo);
                        String [][] detalleVenta = bd.obtenerRegistros(
                                " SELECT\n" +
                                "vd.cod_producto,\n" +
                                "vd.cantidad_producto\n" +
                                "FROM ventas_detalle vd\n" +
                                "JOIN ventas_cabecera vc ON vd.cod_venta_cabecera=vc.cod_venta_cabecera\n" +
                                "WHERE vd.cod_venta_cabecera =" + codigo
                        );
                        for (int x = 0; x < detalleVenta.length; x++) {
                            String [] producto = bd.obtenerRegistro("select existencia_producto from productos");
                            float existencia = Float.parseFloat(producto[0]) + Float.parseFloat(detalleVenta[x][1]);
                            bd.modificarRegistro("productos", "existencia_producto=" + existencia, "cod_producto=" + detalleVenta[x][0]);
                        }
                        String [] presupuesto = bd.obtenerRegistro("SELECT * FROM presupuestos WHERE num_facturacion=" + codigo);
                        if(presupuesto != null){
                            bd.modificarRegistro("presupuestos", "estado_presupuesto='ACTIVO', num_facturacion=(NULL)", "cod_presupuesto=" + presupuesto[0]);
                        }
                        JOptionPane.showMessageDialog(  null, "VENTA ANULADA");
                        this.nuevo();
                    }
                }
            }else if(tipoMto.equals("VENTA CREDITO")){  
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar este registro?", 
                "Confirmación de Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    int confirmacion2 = JOptionPane.showConfirmDialog(null, 
                    "¿Anular Fisicamente?", 
                    "Confirmación de Eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                    if (confirmacion2 == JOptionPane.YES_OPTION) {
                        String codCredito = bd.obtenerRegistro("select cod_credito_cliente from credito_cliente where cod_venta =" + codigo)[0];
                        String [][] cuotaCredito = bd.obtenerRegistros("select estado_cuota from cuotas_credito_cliente where cod_credito_cliente=" + codCredito);
                        boolean tienePago = false;
                        for (int x = 0; x < cuotaCredito.length; x++) {
                            System.out.println("estado cuota credito   " + cuotaCredito[x][0]);
                            if(cuotaCredito[x][0].equals("INACTIVO")){
                                tienePago = true;
                            }
                            System.out.println(cuotaCredito[x][0] + "\n");
                        }
                        if(!tienePago){
                            bd.modificarRegistro("ventas_cabecera", "estado_venta = 'INACTIVO', anulacion_fisica='ACTIVO'", "cod_venta_cabecera = " + codigo);
                            String [][] detalleVenta = bd.obtenerRegistros(
                                    " SELECT\n" +
                                    "vd.cod_producto,\n" +
                                    "vd.cantidad_producto\n" +
                                    "FROM ventas_detalle vd\n" +
                                    "JOIN ventas_cabecera vc ON vd.cod_venta_cabecera=vc.cod_venta_cabecera\n" +
                                    "WHERE vd.cod_venta_cabecera =" + codigo
                            );
                            for (int x = 0; x < detalleVenta.length; x++) {
                                String [] producto = bd.obtenerRegistro("select existencia_producto from productos");
                                float existencia = Float.parseFloat(producto[0]) + Float.parseFloat(detalleVenta[x][1]);
                                bd.modificarRegistro("productos", "existencia_producto=" + existencia, "cod_producto=" + detalleVenta[x][0]);
                            }
                            bd.borrarRegistro("cuotas_credito_cliente", "cod_credito_cliente=" + codCredito);
                            bd.borrarRegistro("credito_cliente", "cod_credito_cliente=" + codCredito);
                            String [] presupuesto = bd.obtenerRegistro("SELECT * FROM presupuestos WHERE num_facturacion=" + codigo);
                            if(presupuesto != null){
                                bd.modificarRegistro("presupuestos", "estado_presupuesto='ACTIVO', num_facturacion=(NULL)", "cod_presupuesto=" + presupuesto[0]);
                            }
                            JOptionPane.showMessageDialog(null, "VENTA ANULADA");
                            this.nuevo();
                        }else{
                            JOptionPane.showMessageDialog(null, "No puedes borrar una venta credito que ya cuenta con pago");
                        }
                    }else{
                        String codCredito = bd.obtenerRegistro("select cod_credito_cliente from credito_cliente where cod_venta =" + codigo)[0];
                        String [][] cuotaCredito = bd.obtenerRegistros("select estado_cuota from cuotas_credito_cliente where cod_credito_cliente=" + codCredito);
                        boolean tienePago = false;
                        for (int x = 0; x < cuotaCredito.length; x++) {
                            System.out.println("estado cuota credito   " + cuotaCredito[x][0]);
                            if(cuotaCredito[x][0].equals("INACTIVO")){
                                tienePago = true;
                            }
                            System.out.println(cuotaCredito[x][0] + "\n");
                        }
                        if(!tienePago){
                            bd.modificarRegistro("ventas_cabecera", "estado_venta = 'INACTIVO', anulacion_fisica='INACTIVO'", "cod_venta_cabecera = " + codigo);
                            String [][] detalleVenta = bd.obtenerRegistros(
                                    " SELECT\n" +
                                    "vd.cod_producto,\n" +
                                    "vd.cantidad_producto\n" +
                                    "FROM ventas_detalle vd\n" +
                                    "JOIN ventas_cabecera vc ON vd.cod_venta_cabecera=vc.cod_venta_cabecera\n" +
                                    "WHERE vd.cod_venta_cabecera =" + codigo
                            );
                            for (int x = 0; x < detalleVenta.length; x++) {
                                String [] producto = bd.obtenerRegistro("select existencia_producto from productos");
                                float existencia = Float.parseFloat(producto[0]) + Float.parseFloat(detalleVenta[x][1]);
                                bd.modificarRegistro("productos", "existencia_producto=" + existencia, "cod_producto=" + detalleVenta[x][0]);
                            }
                            bd.borrarRegistro("cuotas_credito_cliente", "cod_credito_cliente=" + codCredito);
                            bd.borrarRegistro("credito_cliente", "cod_credito_cliente=" + codCredito);
                            String [] presupuesto = bd.obtenerRegistro("SELECT * FROM presupuestos WHERE num_facturacion=" + codigo);
                            if(presupuesto != null){
                                bd.modificarRegistro("presupuestos", "estado_presupuesto='ACTIVO', num_facturacion=(NULL)", "cod_presupuesto=" + presupuesto[0]);
                            }
                            JOptionPane.showMessageDialog(null, "VENTA ANULADA");
                            this.nuevo();
                        }else{
                            JOptionPane.showMessageDialog(null, "No puedes borrar una venta credito que ya cuenta con pago");
                        }
                    }
                }
            }else if(tipoMto.equals("PAGO CUOTA")){
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Estás seguro de que deseas eliminar este registro?", 
                "Confirmación de Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    bd.modificarRegistro("ventas_cabecera", "estado_venta = 'INACTIVO'", "cod_venta_cabecera = " + codigo);
                    String [][] cuotas = bd.obtenerRegistros(   "SELECT " +
                                                                "cod_cuotas_credito,\n" +
                                                                "cod_credito_cliente,\n" +
                                                                "numero_cuota,\n" +
                                                                "monto_cuota,\n" +
                                                                "estado_cuota\n" +
                                                                "FROM cuotas_credito_cliente\n" +
                                                                "WHERE num_movimiento =" + codigo);
                    for (int x = 0; x < cuotas.length; x++) {
                        String [] credito = bd.obtenerRegistro( "SELECT \n" +
                                                                "cc.cod_credito_cliente,\n" +
                                                                "cc.cod_venta,\n" +
                                                                "cc.cuotas_pagadas_credito,\n" +
                                                                "cc.saldo_restante_credito\n" +
                                                                "FROM credito_cliente cc\n" +
                                                                "JOIN cuotas_credito_cliente ccc ON cc.cod_credito_cliente=ccc.cod_credito_cliente\n" +
                                                                "WHERE ccc.cod_cuotas_credito= " + cuotas[x][0]);
                        String valoresCuota = "estado_cuota='ACTIVO'";
                        String condicionCuota = "cod_cuotas_credito=" + cuotas[x][0];
                        bd.modificarRegistro("cuotas_credito_cliente", valoresCuota, condicionCuota);
                        
                        int cuotasPagadas = Integer.parseInt(credito[2]) - 1;
                        float montoCuota = Float.parseFloat(cuotas[x][3]);
                        Float saldoRestante = Float.parseFloat(credito[3]) + montoCuota;
                        String estado = "INACTIVO";
                        if(saldoRestante > 0){
                            estado = "ACTIVO";
                        }
                        String valoresCredito = "cuotas_pagadas_credito=" + cuotasPagadas + 
                                ", saldo_restante_credito=" + saldoRestante +
                                ", estado_credito='" + estado + "'";
                        String condicionCredito = "cod_credito_cliente=" + cuotas[x][1];
                        bd.modificarRegistro("credito_cliente", valoresCredito, condicionCredito);
                        
                        String [] lineaCredito = bd.obtenerRegistro("select \n" +
                                                                    "cod_linea_credito,\n" +
                                                                    "monto_total,\n" +
                                                                    "saldo_disponible \n" +
                                                                    "from lineas_credito\n" +
                                                                    "where cod_cliente =" + codCliente);
                        float saldoDisponible = Float.parseFloat(lineaCredito[2]) - montoCuota;
                        bd.modificarRegistro("lineas_credito", "saldo_disponible=" + saldoDisponible, "cod_linea_credito=" + lineaCredito[0]);
                    }
                    JOptionPane.showMessageDialog(null, "VENTA ANULADA");
                    this.nuevo();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Solo usuarios Administrador pueden anular las ventas");
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnVerCuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCuotasActionPerformed
        String codVenta = this.txtCodigo.getText();
        String codCredito = bd.obtenerRegistro("select cod_credito_cliente from credito_cliente where cod_venta =" + codVenta)[0];
        String [][] cuotas = bd.obtenerRegistros("select numero_cuota, monto_cuota, fecha_vencimiento_cuota, estado_cuota from cuotas_credito_cliente where cod_credito_cliente=" + codCredito);
        JDCuotasVenta jC = new JDCuotasVenta(this, true, this, cuotas);
        jC.setVisible(true);
    }//GEN-LAST:event_btnVerCuotasActionPerformed

    private void txtRucClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRucClienteFocusLost

    }//GEN-LAST:event_txtRucClienteFocusLost

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        if(accion.equals("nuevo")){
            String tipoMto = this.cboTipoMto.getSelectedItem().toString();
            String concepto = this.cboConcepto.getSelectedItem().toString();
            if(tipoMto.equals("INGRESO")){
                if(concepto.equals("PAGO CUOTA")){                    
                    if(!this.txtCodCliente.getText().isEmpty()){
                        String tabla = " cuotas_credito_cliente ccc ";
                        String [] cabeceras = {"CODIGO", "CLIENTE", "COD VENTA", "FECHA VENTA", "NUM COMPROBANTE", "MONTO", "VENCIMIENTO"};
                        int [] ancho = {100,300,100,100,100,100, 200};
                        String [] campos = {
                            "ccc.cod_cuotas_credito", 
                            "CONCAT(cl.nombre_cliente,', ', cl.apellido_cliente)",
                            "vc.cod_venta_cabecera",
                            "vc.fecha_venta", 
                            "vc.num_comprobante_venta", 
                            "ccc.monto_cuota", 
                            "ccc.fecha_vencimiento_cuota"
                        };
                        String condicion =  " JOIN credito_cliente cc ON ccc.cod_credito_cliente=cc.cod_credito_cliente\n" +
                                            " JOIN ventas_cabecera vc ON cc.cod_venta=vc.cod_venta_cabecera\n" +
                                            " JOIN clientes cl ON vc.cod_cliente=cl.cod_cliente\n" +
                                            " WHERE ccc.estado_cuota = 'ACTIVO' AND cl.cod_cliente = " + this.txtCodCliente.getText();
                        JDBuscador fbp = new JDBuscador(this, true, this, tabla, campos, condicion, cabeceras, ancho);
                        fbp.setVisible(true); 
                    }else{
                        JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente");
                    }
                }else{
                    String tabla = "productos p";
                    String [] cabeceras = {"Codigo", "Producto", "Marca", "Precio", "Existencia", "IVA"};
                    String [] campos = {"p.cod_producto", "p.nombre_producto", "m.nombre_marca", "p.precio_producto", "p.existencia_producto", "p.iva_producto"};
                    String condicion = " join marcas m on p.cod_marca = m.cod_marca where p.estado_producto='ACTIVO'";
                    int [] ancho = {200,500,300,300,200,200};
                    JDBuscador fbp = new JDBuscador(this, true, this, tabla, campos, condicion, cabeceras, ancho);
                    fbp.setVisible(true);                    
                }
            }else{
                
            }
            
        }
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void txtCodProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProductoFocusLost
        if(!this.txtCodProducto.getText().isEmpty()){
            if(this.cboConcepto.getSelectedItem().toString().equals("PAGO CUOTA")){
                JOptionPane.showMessageDialog(null, "Usa la lupa para obtener las cuotas pendientes de pago");
                this.txtCodProducto.setText(null);
            }else{                
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
        String codigoAutorizacion = JOptionPane.showInputDialog(this, "Ingrese el código de autorización para eliminar la fila:");

        // Verificar si se ingresó un código
        if (codigoAutorizacion != null && !codigoAutorizacion.trim().isEmpty()) {
            // Comprobar si el código es correcto
            if (codigoAutorizacion.equals("6741475.Victor")){
                int fila = this.grdDetalleVenta.getSelectedRow();
                if (fila != -1) { // Verificar que se haya seleccionado una fila
                    DefaultTableModel model = (DefaultTableModel) this.grdDetalleVenta.getModel(); // Obtener el modelo de la tabla
                    model.removeRow(fila); // Eliminar la fila seleccionada
                    this.calcularSubTotales();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Codigo de Seguridad Incorrecto");
            }
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
            if(this.cboConcepto.getSelectedItem().toString().equals("PAGO CUOTA")){
                this.limpiarCamposDetalle();
                gr.vaciar(grdDetalleVenta);
                this.calcularSubTotales();
            }
            String tabla = "clientes";
            String [] campos = {"cod_cliente", "concat(nombre_cliente,' ', apellido_cliente)", "ruc_cliente", "telefono_cliente"};
            String [] cabeceras = {"Codigo", "Cliente", "RUC", "Telefono"};
            int [] ancho = {200,500,300,300};
            JDBuscador fb = new JDBuscador(this, true, this, tabla, campos, "",cabeceras, ancho);
            fb.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            fb.setVisible(true);
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void txtNumComprobanteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumComprobanteFocusLost
        if(!txtNumComprobante.getText().isEmpty()){
            // Validar el formato al perder el foco
            if (!txtNumComprobante.getText().matches("\\d{3}-\\d{3}-\\d{7}")) {
                JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ser xxx-xxx-xxxxxxx");
                txtNumComprobante.requestFocus(); // Volver a enfocar el campo
            }
        }
    }//GEN-LAST:event_txtNumComprobanteFocusLost

    private void itmDeudasClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeudasClientesActionPerformed
        FrmInformeVentas iv = new FrmInformeVentas();
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_itmDeudasClientesActionPerformed

    private void itmInformeVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmInformeVentasActionPerformed
        FrmInformeVentas iv = new FrmInformeVentas("ACTIVO");
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);        
    }//GEN-LAST:event_itmInformeVentasActionPerformed

    private void itmInformeVentasAnuladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmInformeVentasAnuladasActionPerformed
        FrmInformeVentas iv = new FrmInformeVentas("INACTIVO");
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);        
    }//GEN-LAST:event_itmInformeVentasAnuladasActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        String codVenta = this.txtCodigo.getText();                        
        String url = "/reportes/reporteVentaDetalle.jasper";

        String [] datos = bd.obtenerRegistro("select "
                + "fecha_venta, " 
                + "ruc_cliente, "
                + "razon_social_cliente, "
                + "COALESCE(concepto_movimiento,''), "
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
        String total = "";

        if(condicion.equals("VENTA CREDITO")){
            vencimiento = bd.obtenerRegistro("select fecha_vencimiento_credito from credito_cliente where cod_venta    =" + codVenta)[0];
        }

        String [] nomParamet = {"cod_venta","fecha_venta","ruc_cliente","nombre_cliente","condicion_venta","vencimiento_credito", "timbrado_venta","num_comprobante", "total_venta"};
        String [] paramet = {codVenta, fecha, ruc, cliente, condicion, vencimiento, timbrado, num_coprobante, total};
        bd.llamarReporte(url,nomParamet, paramet);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeActionPerformed
        String caja = this.txtCaja.getText();
        if(!caja.isEmpty()){
            FrmInformeVentas iv = new FrmInformeVentas(caja, "x");
            iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
            iv.setVisible(true);            
        }else{
            JOptionPane.showMessageDialog(null, "El numero de caja esta vacia");
        }
    }//GEN-LAST:event_btnInformeActionPerformed

    private void cboConceptoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboConceptoMouseEntered

    }//GEN-LAST:event_cboConceptoMouseEntered

    private void cboConceptoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboConceptoMouseReleased

    }//GEN-LAST:event_cboConceptoMouseReleased

    private void cboConceptoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboConceptoPropertyChange
  
    }//GEN-LAST:event_cboConceptoPropertyChange

    private void cboConceptoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboConceptoFocusLost
        String concepto = this.cboConcepto.getSelectedItem().toString();
        if(this.codPresupuesto == null){
            if(concepto.equals("PAGO CUOTA")){
                this.esPagoCuota();
            }else{
                this.noEsPagoCuota();
            }
            this.limpiarCamposDetalle();
            gr.vaciar(grdDetalleVenta);
        }
    }//GEN-LAST:event_cboConceptoFocusLost

    private void btnMetodosPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodosPagoActionPerformed
        String codVenta = this.txtCodigo.getText();
        JDMetodoPago jm = new JDMetodoPago(this, true, this.usuarioLogeado, this.rol, this, codVenta);
        jm.setVisible(true);          
    }//GEN-LAST:event_btnMetodosPagoActionPerformed

    private void btnBuscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCajaActionPerformed
        FrmCajas fc = new FrmCajas(usuarioLogeado, rol);
        fc.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fc.setVisible(true);
    }//GEN-LAST:event_btnBuscarCajaActionPerformed

    private void itmCreditosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmCreditosClientesActionPerformed
        String [] cabeceras = {"COD","CLIENTE","CUOTAS","PAGADAS","PLAZO","INICIO","VENCIMIENTO","TOTAL CREDITO","SALDO PENDIENTE","ESTADO"};
        int [] tamaños = {50,400,100,100,100,120,120,120,120,100};
        String tabla = " credito_cliente cc ";
        String [] campos = {
            "cc.cod_credito_cliente",
            "concat(cl.cod_cliente,' - ', cl.nombre_cliente, ', ', cl.apellido_cliente) as cliente",
            "cc.numero_cuotas_credito",
            "cc.cuotas_pagadas_credito",
            "cc.plazo_cuotas_credito",
            "cc.fecha_inicio_credito",
            "cc.fecha_vencimiento_credito",
            "sum(ccc.monto_cuota) as total_credito",
            "cc.saldo_restante_credito",
            "cc.estado_credito"
        };
        String condicion =  " join ventas_cabecera vc on cc.cod_venta=vc.cod_venta_cabecera\n" +
                            " JOIN clientes cl on vc.cod_cliente=cl.cod_cliente\n" +
                            " JOIN cuotas_credito_cliente ccc on cc.cod_credito_cliente=ccc.cod_credito_cliente\n" +
                            " GROUP BY cc.cod_credito_cliente";
        FrmInformeVentas iv = new FrmInformeVentas(cabeceras, tamaños, tabla, campos, condicion, rol);
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_itmCreditosClientesActionPerformed

    private void cboTipoMtoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboTipoMtoFocusLost
        this.verificarTipoMto();
    }//GEN-LAST:event_cboTipoMtoFocusLost

    private void txtPrecioProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioProductoFocusLost
        if(this.cboTipoMto.getSelectedItem().toString().equals("EGRESO")){
            this.txtTotal.setText(this.txtPrecioProducto.getText());
        }
    }//GEN-LAST:event_txtPrecioProductoFocusLost

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        if(!this.txtCodigo.getText().isEmpty()){
            int cod = Integer.parseInt(this.txtCodigo.getText());
            int codigo = Integer.parseInt(bd.obtenerCodMaximoRegistro("ventas_cabecera", "cod_venta_cabecera")) - 1; 
            if(cod < codigo && cod > 0){
                String estado = bd.obtenerRegistro("select estado_venta from ventas_cabecera WHERE cod_venta_cabecera =" + cod)[0];
                if(estado.equals("ACTIVO")){
                    this.mostrarVenta(String.valueOf(cod));    
                }else{
                    JOptionPane.showMessageDialog(null, "NO SE ENCONTRO EL REGISTRO");
                    this.nuevo();
                }
            }else{
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO EL REGISTRO");
                this.nuevo();
            }
        }
    }//GEN-LAST:event_txtCodigoFocusLost

    private void itmPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmPresupuestosActionPerformed
        FrmInformePresupuestos fp = new FrmInformePresupuestos(this, usuarioLogeado, rol, "TODOS", "TODOS LOS PRESUPUESTOS");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fp.setVisible(true);
    }//GEN-LAST:event_itmPresupuestosActionPerformed

    
    
   
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmVentas dialog = new FrmVentas("VLOPEZ","ADMIN");
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
    private javax.swing.JButton btnBuscarCaja;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JToggleButton btnEliminarProducto;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JToggleButton btnImprimir;
    private javax.swing.JToggleButton btnInforme;
    private javax.swing.JToggleButton btnMetodosPago;
    private javax.swing.JToggleButton btnNuevo;
    private javax.swing.JToggleButton btnSiguiente;
    private javax.swing.JToggleButton btnVerCuotas;
    private javax.swing.JComboBox<String> cboConcepto;
    private javax.swing.JComboBox<String> cboConceptoEgreso;
    private javax.swing.JComboBox<String> cboIva;
    private javax.swing.JComboBox<String> cboTipoMto;
    private com.toedter.calendar.JDateChooser dateFechaVenta;
    private javax.swing.JTable grdDetalleVenta;
    public javax.swing.JMenuItem itmCreditosClientes;
    public javax.swing.JMenuItem itmDeudasClientes;
    public javax.swing.JMenuItem itmInformeVentas;
    public javax.swing.JMenuItem itmInformeVentasAnuladas;
    public javax.swing.JMenuItem itmPresupuestos;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCodProducto;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    public javax.swing.JMenu menuReportes;
    private javax.swing.JPanel panel1;
    private javax.swing.JTextField txtCaja;
    private javax.swing.JTextField txtCantProducto;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodProducto;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtExentas;
    private javax.swing.JTextField txtFinVigencia;
    private javax.swing.JTextField txtInicioVigencia;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    private javax.swing.JTextField txtNomCliente;
    private javax.swing.JTextField txtNumComprobante;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtRucCliente;
    private javax.swing.JTextField txtTimbrado;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalGeneral;
    private javax.swing.JTextField txtTotalGeneral1;
    private javax.swing.JTextField txtTotalGravado;
    private javax.swing.JTextField txtTotalIva;
    // End of variables declaration//GEN-END:variables
}
