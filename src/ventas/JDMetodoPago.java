package ventas;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDatos1;
import servicios.Grilla;

/**
 *
 * @author Asus
 */
public class JDMetodoPago extends javax.swing.JDialog {
    Grilla gr = new Grilla();
    BaseDatos1 bd = new BaseDatos1();
    FrmVentas frmVentas;
    String usuarioLogeado;
    String rol;
    Float monto;
    String [][] metodosPago = null;    
    String codVenta = "";
    String condicion = "";
    String accion = "nuevo";
    
    public JDMetodoPago(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.nuevo();
        this.lblTotal.setText(String.valueOf(10000));
        this.txtMonto.setText(String.valueOf(10000));
        this.txtTotalVenta.setText(String.valueOf(10000));
    }
    
    public JDMetodoPago(java.awt.Frame parent, boolean modal, FrmVentas frmVentas, Float monto, String condicion) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmVentas = frmVentas;        
        this.nuevo();
        this.lblTotal.setText(String.valueOf(monto));
        this.txtMonto.setText(String.valueOf(monto));
        this.txtTotalVenta.setText(String.valueOf(monto));
        this.condicion = condicion;
    }
    
    //CONSTRUCTOR PARA VER EL METODO DE PAGO
    public JDMetodoPago(java.awt.Frame parent, boolean modal, String usuLogeado, String rol, FrmVentas frmVentas, String codVenta) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmVentas = frmVentas;
        if(!rol.equals("ADMIN")){
            this.mostrarMetodoPago(codVenta);
            this.mostrarTotal();
            this.cboMetodoPago.setVisible(false);
            this.txtMonto.setVisible(false);
            this.cboBanco.setVisible(false);
            this.lblMetodoPago.setVisible(false);
            this.lblMonto.setVisible(false);
            this.lblBanco.setVisible(false);
            this.lblTotalMetodoPago.setVisible(false);
            this.txtTotalMetodosPago.setVisible(false);
            this.btnAñadir.setVisible(false);
            this.btnEliminar.setVisible(false);            
        }else{
            this.nuevo();
            this.mostrarMetodoPagoAdmin(codVenta);
            this.mostrarTotal();  
            this.codVenta = codVenta;
            this.accion = "modificar";
        }
    }
    
    //CONSTRUCTOR PARA VER EL METODO DE PAGO
    public JDMetodoPago(java.awt.Frame parent, boolean modal, String usuLog, String codVenta) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.mostrarMetodoPago(codVenta);
        this.mostrarTotal();
        
        this.cboMetodoPago.setVisible(false);
        this.txtMonto.setVisible(false);
        this.cboBanco.setVisible(false);
        this.lblMetodoPago.setVisible(false);
        this.lblMonto.setVisible(false);
        this.lblBanco.setVisible(false);
        this.lblTotalMetodoPago.setVisible(false);
        this.txtTotalMetodosPago.setVisible(false);
        this.btnAñadir.setVisible(false);
        this.btnEliminar.setVisible(false);
    }
   
    
    
    
    private void nuevo(){
        bd.agregarItemCombo(cboMetodoPago, 0, "EFECTIVO");
        bd.agregarItemCombo(cboMetodoPago, 1, "TARJETA DEBITO");
        bd.agregarItemCombo(cboMetodoPago, 2, "TARJETA CREDITO");
        bd.agregarItemCombo(cboMetodoPago, 3, "PAGO QR");
        bd.agregarItemCombo(cboMetodoPago, 4, "TRANSFERENCIA");
        bd.agregarItemCombo(cboMetodoPago, 5, "CHEQUE");
        this.cboMetodoPago.setSelectedIndex(0);
        bd.llenarComboHasta20(cboBanco, "cod_banco, nombre_banco", "bancos");
    }
    
    private void mostrarMetodoPagoAdmin(String codVenta){
        DefaultTableModel tabla = (DefaultTableModel) this.grdMetodosPago.getModel();
        gr.vaciar(this.grdMetodosPago);
        String [][] metodos = bd.obtenerRegistros("SELECT mp.metodo_pago_venta, mp.monto_venta, ba.nombre_banco  FROM metodo_pago_venta mp\n" +
                                                    " LEFT JOIN bancos ba ON mp.cod_banco = ba.cod_banco" +
                                                    " WHERE mp.cod_venta = " + codVenta);
        if(metodos != null){
            for (int x = 0; x < metodos.length; x++) {
                tabla.addRow(new Object[]{});
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            tabla.setValueAt(metodos[x][0],x,i);
                            break;
                        case 1:
                            tabla.setValueAt(metodos[x][1],x,i);
                            break;
                        case 2:
                            tabla.setValueAt(metodos[x][2],x,i);
                            break;
                        default:
                            break;
                    }                
                }
            }
            gr.alinearDerecha(grdMetodosPago, 1);
            this.calcularTotal();
        }
    }
    
    private void mostrarMetodoPago(String codVenta){
        DefaultTableModel tabla = (DefaultTableModel) this.grdMetodosPago.getModel();
        gr.vaciar(this.grdMetodosPago);
        String [][] metodos = bd.obtenerRegistros("SELECT mp.metodo_pago_venta, mp.monto_venta, ba.nombre_banco  FROM metodo_pago_venta mp\n" +
                                                    " LEFT JOIN bancos ba ON mp.cod_banco = ba.cod_banco" +
                                                    " WHERE mp.cod_venta = " + codVenta);
        if(metodos != null){
            for (int x = 0; x < metodos.length; x++) {
                tabla.addRow(new Object[]{});
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            tabla.setValueAt(metodos[x][0],x,i);
                            break;
                        case 1:
                            tabla.setValueAt(metodos[x][1],x,i);
                            break;
                        case 2:
                            tabla.setValueAt(metodos[x][2],x,i);
                            break;
                        default:
                            break;
                    }                
                }
            }
            gr.alinearDerecha(grdMetodosPago, 1);
            this.calcularTotal();
        }
        
    }
    
    private void calcularTotal(){
        JTable tabla = this.grdMetodosPago;
        int filas = tabla.getRowCount();
        float total = 0;
        
        for (int x = 0; x < filas; x++) {
            total += Float.parseFloat(tabla.getValueAt(x, 1).toString());
        }
        this.txtTotalMetodosPago.setText(String.valueOf(total));
    }
    
    private void mostrarTotal(){
        JTable tabla = this.grdMetodosPago;
        int filas = tabla.getRowCount();
        float total = 0;
        
        for (int x = 0; x < filas; x++) {
            total += Float.parseFloat(tabla.getValueAt(x, 1).toString());
        }
        this.lblTotal.setText(String.valueOf(total));
        this.txtTotalVenta.setText(String.valueOf(total));
    }
    
    private void guardarMetodoPago(){
        bd.borrarRegistro("metodo_pago_venta", "cod_venta=" + this.codVenta);
        for (int x = 0; x < this.metodosPago.length; x++) {
            String codMetodoPago = bd.obtenerCodMaximoRegistro("metodo_pago_venta", "cod_metodo_pago_venta");
            String codVenta = this.codVenta;
            String metodo = "";
            String monto = "";
            String banco = "";
            
            for (int i = 0; i < this.metodosPago[x].length; i++) {
                switch (i) {
                    case 0:
                        metodo = this.metodosPago[x][i];
                        break;
                    case 1:
                        monto = this.metodosPago[x][i];
                        break;
                    case 2:
                        banco = this.metodosPago[x][i];
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
        this.dispose();
    }
    
    
    
    
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdMetodosPago = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JToggleButton();
        btnAñadir = new javax.swing.JToggleButton();
        lblMonto = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JToggleButton();
        lblTotalMetodoPago = new javax.swing.JLabel();
        cboBanco = new javax.swing.JComboBox<>();
        lblMetodoPago = new javax.swing.JLabel();
        txtTotalMetodosPago = new javax.swing.JTextField();
        lblBanco = new javax.swing.JLabel();
        btnCerrar1 = new javax.swing.JToggleButton();
        jLabel26 = new javax.swing.JLabel();
        cboMetodoPago = new javax.swing.JComboBox<>();
        btnGuardar1 = new javax.swing.JToggleButton();
        txtTotalVenta = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grdMetodosPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Metodo", "Monto", "Banco"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdMetodosPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grdMetodosPagoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(grdMetodosPago);
        if (grdMetodosPago.getColumnModel().getColumnCount() > 0) {
            grdMetodosPago.getColumnModel().getColumn(1).setPreferredWidth(150);
            grdMetodosPago.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 410, 180));

        lblTotal.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("000");
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 210, 30));

        txtMonto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 130, 35));

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 80, 30));

        btnAñadir.setBackground(new java.awt.Color(51, 204, 0));
        btnAñadir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAñadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadir.setText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });
        jPanel1.add(btnAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 80, 30));

        lblMonto.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        lblMonto.setForeground(new java.awt.Color(255, 255, 255));
        lblMonto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonto.setText("Monto");
        jPanel1.add(lblMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, 30));

        btnGuardar.setBackground(new java.awt.Color(51, 204, 0));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 80, 30));

        lblTotalMetodoPago.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblTotalMetodoPago.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalMetodoPago.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalMetodoPago.setText("Total Metodos Pago");
        jPanel1.add(lblTotalMetodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 180, 30));

        cboBanco.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 130, 35));

        lblMetodoPago.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        lblMetodoPago.setForeground(new java.awt.Color(255, 255, 255));
        lblMetodoPago.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMetodoPago.setText("Metodo Pago");
        jPanel1.add(lblMetodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        txtTotalMetodosPago.setEditable(false);
        txtTotalMetodosPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalMetodosPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtTotalMetodosPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 180, 35));

        lblBanco.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        lblBanco.setForeground(new java.awt.Color(255, 255, 255));
        lblBanco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBanco.setText("Banco");
        jPanel1.add(lblBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 130, 30));

        btnCerrar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCerrar1.setText("Cancelar");
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 80, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Monto Total");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 140, 30));

        cboMetodoPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboMetodoPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboMetodoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMetodoPagoItemStateChanged(evt);
            }
        });
        jPanel1.add(cboMetodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 130, 35));

        btnGuardar1.setBackground(new java.awt.Color(51, 204, 0));
        btnGuardar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar1.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 80, 30));

        txtTotalVenta.setEditable(false);
        txtTotalVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 180, 35));

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Total Venta");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 180, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        gr.borrarFila(grdMetodosPago);
        this.calcularTotal();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(Float.parseFloat(this.txtTotalVenta.getText()) == Float.parseFloat(this.txtTotalMetodosPago.getText())){
            int filas = this.grdMetodosPago.getRowCount();
            String [][] metPago = new String[filas][3];
            if(filas > -1 && filas <= 10){
                for(int x = 0; x < filas; x++){
                    for (int i = 0; i < 3; i++) {
                        switch (i) {
                            case 0:
                                metPago[x][i] = this.grdMetodosPago.getValueAt(x, i).toString();
                                break;
                            case 1:
                                metPago[x][i] = this.grdMetodosPago.getValueAt(x, i).toString();
                                break;
                            case 2:
                                if(this.grdMetodosPago.getValueAt(x, i) != null){
                                    metPago[x][i] = this.grdMetodosPago.getValueAt(x, i).toString();                  
                                }else{
                                    metPago[x][i] = "NULL";
                                }
                                break;
                            default:
                                throw new AssertionError();
                        }
                        
                    }
                }
                this.metodosPago = metPago;
                if(this.accion.equals("nuevo")){
                    if(condicion.equals("VENTA CONTADO")){
                        this.dispose();                
                        frmVentas.guardarVentaContado(metPago);
                    }else if(condicion.equals("PAGO CUOTA")){
                        this.dispose();                
                        frmVentas.guardarPagoCuota(metPago);
                    }
                }else{
                    this.guardarMetodoPago();
                }
            }else{
                JOptionPane.showMessageDialog(null, "El metodo de pago no puede ser menor a 1 ni mayor a 10");
            }
        }else{
            JOptionPane.showMessageDialog(null, "El total metodos de pago y de venta deben coincidir");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrar1ActionPerformed

    private void cboMetodoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMetodoPagoItemStateChanged
        String metodo = this.cboMetodoPago.getSelectedItem().toString();
        if(metodo.equals("TRANSFERENCIA") || metodo.equals("CHEQUE")){
            this.cboBanco.setVisible(true);
            this.lblBanco.setVisible(true);
        }else{
            this.cboBanco.setVisible(false);
            this.lblBanco.setVisible(false);
        }
    }//GEN-LAST:event_cboMetodoPagoItemStateChanged

    private void grdMetodosPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grdMetodosPagoKeyPressed
        int fila = this.grdMetodosPago.getSelectedRow();
        if(fila > -1){
            this.btnEliminar.setEnabled(true);
        }else{
            this.btnEliminar.setEnabled(false);            
        }
    }//GEN-LAST:event_grdMetodosPagoKeyPressed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        if(!this.txtMonto.getText().isEmpty()){
            String metodoPago = this.cboMetodoPago.getSelectedItem().toString();
            String monto = this.txtMonto.getText();
            String cboBanco = this.cboBanco.getSelectedItem().toString();
            if(metodoPago.equals("TRANSFERENCIA") || metodoPago.equals("CHEQUE")){
                String [] fila = {metodoPago,monto,cboBanco};                
                gr.agregarFilaGrilla(this.grdMetodosPago, fila);
                gr.alinearDerecha(grdMetodosPago, 1);
            }else{
                String [] fila = {metodoPago,monto};
                gr.agregarFilaGrilla(this.grdMetodosPago, fila);
                gr.alinearDerecha(grdMetodosPago, 1);
            }
            this.txtMonto.setText(null);
            this.calcularTotal();
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese el monto");
        }
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(JDMetodoPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDMetodoPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDMetodoPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDMetodoPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDMetodoPago dialog = new JDMetodoPago(new javax.swing.JFrame(), true, "ADMIN", "13");
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
    private javax.swing.JToggleButton btnAñadir;
    private javax.swing.JToggleButton btnCerrar1;
    private javax.swing.JToggleButton btnEliminar;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JToggleButton btnGuardar1;
    private javax.swing.JComboBox<String> cboBanco;
    private javax.swing.JComboBox<String> cboMetodoPago;
    private javax.swing.JTable grdMetodosPago;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanco;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalMetodoPago;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtTotalMetodosPago;
    private javax.swing.JTextField txtTotalVenta;
    // End of variables declaration//GEN-END:variables
}
