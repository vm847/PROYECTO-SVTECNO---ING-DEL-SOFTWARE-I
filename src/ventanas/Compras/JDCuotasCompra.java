package ventanas.Compras;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import servicios.BaseDatos1;
import servicios.Fechas;
import servicios.Grilla;

/**
 *
 * @author Asus
 */
public class JDCuotasCompra extends javax.swing.JDialog {
    Grilla miGrilla = new Grilla();
    Fechas fe = new Fechas();
    BaseDatos1 bd = new BaseDatos1();
    FrmCompras frmcompras;
    String plazo;
    int numCuotas;
    String fechaC;
    String fechaVenUltimaCuota;
    Float monto;
    String [][] cuotas;
    String accion = "nuevo";
    
    
    
    
    public JDCuotasCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.txtCuotas.setText("1");
        this.plazo = "trimestral";
        this.fechaC = "29-11-2024";
        this.numCuotas = 10;
        this.monto = Float.parseFloat("50000");
        this.generarCuotas(plazo, numCuotas, monto);
        this.accion = "generar";
    }
    
    
    public JDCuotasCompra(java.awt.Frame parent, boolean modal, FrmCompras frmCompras, Float monto, String fechaC, String [][] cuotas) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmcompras = frmCompras;
        this.fechaC = fechaC;
        this.monto = monto;
        if(cuotas != null){
            mostrarCuotas(cuotas);
            this.accion = "mostrar";
        }else{
            this.btnGenerar.setVisible(true);
            this.txtCuotas.setEditable(true);
            this.cboPlazo.setEnabled(true);
            this.accion= "generar";
            this.plazo = "MENSUAL";
            this.numCuotas = 1;
            this.generarCuotas(plazo, numCuotas, monto);
        }
    }
    
    public JDCuotasCompra(java.awt.Frame parent, boolean modal, FrmCompras frmCompras, String [][] cuotas) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmcompras = frmCompras;
        this.mostrarCuotas(cuotas);
        this.accion = "mostrar";
    }
    
    private void generarCuotas(String plazo, int cuotas, float monto){
        this.txtCuotas.setText(String.valueOf(cuotas));
        bd.seleccionarItemCombo(cboPlazo, plazo);
        
        DefaultTableModel tabla = (DefaultTableModel) this.grdCuotas.getModel();
        miGrilla.vaciar(this.grdCuotas);
        float montoCuota = monto / cuotas;
        String [][] cuotasC = new String[cuotas][4];
        
        for (int x = 0; x < cuotas; x++) {
            tabla.addRow(new Object[]{});
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        tabla.setValueAt(x+1,x,i);
                        cuotasC[x][i] = String.valueOf(x+1);
                        break;
                    case 1:
                        tabla.setValueAt(montoCuota,x,i);
                        cuotasC[x][i] = String.valueOf(montoCuota);
                        break;
                    case 2:
                        tabla.setValueAt(calcularVencimientos(x+1, plazo),x,i);
                        cuotasC[x][i] = String.valueOf(calcularVencimientos(x+1, plazo));
                        break;
                    case 3:
                        tabla.setValueAt("activo",x,i);
                        cuotasC[x][i] = "activo";
                        break;
                    default:
                        break;
                }                
            }
            this.cuotas = cuotasC;
            this.calcularVencimientoCredito();
            //System.out.println(this.cuotas[0][0] + "\n" + this.cuotas[0][1] + "\n" + this.cuotas[0][2] + "\n" + this.cuotas[0][3]);
        }
//        this.txtCuotas.setText(String.valueOf(cuotas));
        this.txtMontoCredito.setText(String.valueOf(monto));
        this.calcularSaldoRestante();
    }
    private void mostrarCuotas(String [][] cuotas){
        DefaultTableModel tabla = (DefaultTableModel) this.grdCuotas.getModel();
        miGrilla.vaciar(this.grdCuotas);
        
        for (int x = 0; x < cuotas.length; x++) {
            tabla.addRow(new Object[]{});
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        tabla.setValueAt(cuotas[x][0],x,i);
                        break;
                    case 1:
                        tabla.setValueAt(cuotas[x][1],x,i);
                        break;
                    case 2:
                        tabla.setValueAt(cuotas[x][2],x,i);
                        break;
                    case 3:
                        tabla.setValueAt(cuotas[x][3],x,i);
                        break;
                    default:
                        break;
                }                
            }
        }
        this.calcularSaldoRestante();
        this.btnGuardar.setEnabled(false);
        this.btnGenerar.setEnabled(false);
    }
    private String calcularVencimientos(int cuotas, String plazo){
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date fechaActual = formato.parse(this.fechaC);
            Date fechaFutura;
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.setTime(fechaActual);
//            System.out.println("Fecha Inicio = " + formato.format(fechaInicio.getTime()));

            switch (plazo) {
                case "SEMANAL":
                    fechaInicio.add(Calendar.WEEK_OF_YEAR, cuotas);
                    break;
                case "QUINCENAL":
                    fechaInicio.add(Calendar.WEEK_OF_YEAR, cuotas*2);
                    break;
                case "MENSUAL":
                    fechaInicio.add(Calendar.MONTH, cuotas);
//                    System.out.println("Fecha Inicio en cuota = " + cuotas + "    -   " + formato.format(fechaInicio.getTime()));
                    break;
                case "TRIMESTRAL":
                    fechaInicio.add(Calendar.MONTH, cuotas*3); 
                    break;
                case "SEMESTRAL":
                    fechaInicio.add(Calendar.MONTH, cuotas*6);
                    break;
                case "ANUAL":
                    fechaInicio.add(Calendar.YEAR, cuotas);
                    break;
                default:
                    break;
            }
            fechaFutura = fechaInicio.getTime();
//            System.out.println(formato.format(fechaInicio.getTime()));
            this.fechaVenUltimaCuota = formato.format(fechaFutura);
            return formato.format(fechaFutura);
        } catch (Exception e) {
            System.out.println("Ocurrio un error al generar la cuota");
        }
        return "-1";
    }
    
    private void calcularSaldoRestante(){
        JTable tabla = this.grdCuotas;
        int filas = tabla.getRowCount();
        float total = 0;
        
        for (int x = 0; x < filas; x++) {
            if(tabla.getValueAt(x, 3).toString().equals("activo")){
                total += Float.parseFloat(tabla.getValueAt(x, 1).toString());
            }
        }
        this.txtSaldoRestante.setText(String.valueOf(total));
    }
    private void calcularVencimientoCredito(){
            String plazo = this.cboPlazo.getSelectedItem().toString();
            int cuotas = Integer.parseInt(this.txtCuotas.getText());
            String fechaVen = fe.calcularVencimientoCredito(fechaC, plazo, cuotas);
//            System.out.println("fecha vencimiento" + fechaVen);
            this.txtVencimiento.setText(fechaVen);
//            fe.mostrarFechaJDateChooserFormatoNormal(this.dateFechaVencimiento, fechaVen);     
    }
    public void setCuota(String numCuota, String monto, String fechaV, int fila){
        this.grdCuotas.setValueAt(monto, fila, 1);
        this.grdCuotas.setValueAt(fechaV, fila, 2);
        this.calcularSaldoRestante();
    }
    
    private void leerCuotas(){
        JTable tabla = this.grdCuotas;
        int filas = tabla.getRowCount();
        String [][] cuotaTemp = new String[filas][4];
        
        for (int x = 0; x < filas; x++) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
//                        System.out.println("fila " + x + " columna " + i + " = " + tabla.getValueAt(x, i).toString());
                        cuotaTemp[x][i] = tabla.getValueAt(x, i).toString();
                        break;
                    case 1:
//                        System.out.println("fila " + x + " columna " + i + " = " + tabla.getValueAt(x, i).toString());
                        cuotaTemp[x][i] = tabla.getValueAt(x, i).toString();
                        break;
                    case 2:
//                        System.out.println("fila " + x + " columna " + i + " = " + tabla.getValueAt(x, i).toString());
                        cuotaTemp[x][i] = tabla.getValueAt(x, i).toString();
                        break;
                    case 3:
//                        System.out.println("fila " + x + " columna " + i + " = " + tabla.getValueAt(x, i).toString());
                        cuotaTemp[x][i] = tabla.getValueAt(x, i).toString();
                        break;
                    default:
                        break;
                }                
            }
//            System.out.print("num " + cuotaTemp[x][0] + " monto " + cuotaTemp[x][1] + " fecha " + cuotaTemp[x][2] + " estado " + cuotaTemp[x][3] );
            this.cuotas = cuotaTemp;
        }
    }
    
    
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdCuotas = new javax.swing.JTable();
        txtCuotas = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cboPlazo = new javax.swing.JComboBox<>();
        lblVencimiento = new javax.swing.JLabel();
        txtSaldoRestante = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        txtMontoCredito = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JToggleButton();
        btnGenerar = new javax.swing.JToggleButton();
        jLabel21 = new javax.swing.JLabel();
        txtVencimiento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grdCuotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "N°", "Monto", "Vencimiento", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdCuotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdCuotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdCuotas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 420, 410));

        txtCuotas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCuotas.setText("1");
        txtCuotas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCuotas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCuotasFocusLost(evt);
            }
        });
        jPanel1.add(txtCuotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 130, 35));

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Cuotas");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 30));

        cboPlazo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMANAL", "QUINCENAL", "MENSUAL", "TRIMESTRAL", "SEMESTRAL", "ANUAL" }));
        cboPlazo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboPlazo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPlazoItemStateChanged(evt);
            }
        });
        jPanel1.add(cboPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 130, 35));

        lblVencimiento.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        lblVencimiento.setForeground(new java.awt.Color(255, 255, 255));
        lblVencimiento.setText("Vencim.");
        jPanel1.add(lblVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        txtSaldoRestante.setEditable(false);
        txtSaldoRestante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoRestante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtSaldoRestante, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 180, 35));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Saldo Restante");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, -1, 20));

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCerrar.setText("Cancelar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 80, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Monto Credito");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, 20));

        txtMontoCredito.setEditable(false);
        txtMontoCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoCredito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtMontoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 180, 35));

        btnGuardar.setBackground(new java.awt.Color(51, 204, 0));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 80, 30));

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 130, 35));

        jLabel21.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Plazo");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 50, 30));

        txtVencimiento.setEditable(false);
        txtVencimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVencimiento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtVencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVencimientoFocusLost(evt);
            }
        });
        jPanel1.add(txtVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 130, 35));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCuotasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCuotasFocusLost
        this.accion = "generar";
    }//GEN-LAST:event_txtCuotasFocusLost

    private void cboPlazoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPlazoItemStateChanged
        this.plazo = this.cboPlazo.getSelectedItem().toString();
        this.accion= "generar";
    }//GEN-LAST:event_cboPlazoItemStateChanged

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(this.accion.equals("generado")){
            if(this.txtMontoCredito.getText().equals(this.txtSaldoRestante.getText())){
                String numCuotas = this.txtCuotas.getText();
                String plazo = this.cboPlazo.getSelectedItem().toString();
                String vencimiento = fe.formatoFechaJavaBD(this.txtVencimiento.getText());
                this.leerCuotas();
                this.dispose();
                frmcompras.guardarCompraCredito(numCuotas, plazo, vencimiento, this.cuotas);
            }else{
                JOptionPane.showMessageDialog(null, "El Monto y el saldo deben coincidir");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debes hacer clic en generar para poder guardar");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        String plazo = this.cboPlazo.getSelectedItem().toString();
        if(this.accion.equals("generar")){
            if(!this.txtCuotas.getText().isEmpty()){
                int cuotas = Integer.parseInt(this.txtCuotas.getText());
                this.generarCuotas(plazo, cuotas, this.monto);
                this.calcularSaldoRestante();
            }else{
                this.txtCuotas.setText("1");
                this.generarCuotas(plazo, 1, this.monto);
                this.calcularSaldoRestante();
            }
            this.accion = "generado";
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void grdCuotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdCuotasMouseClicked
        if(evt.getClickCount() == 2 && this.accion != "mostrar"){
            int fila = this.grdCuotas.getSelectedRow();
            String cuotaN = this.grdCuotas.getValueAt(fila, 0).toString();
            String montoC = this.grdCuotas.getValueAt(fila, 1).toString();
            String fechaV = this.grdCuotas.getValueAt(fila, 2).toString();
            String estado = this.grdCuotas.getValueAt(fila, 3).toString();
            JDModificarCuota jM = new JDModificarCuota(this, true, this, fechaC, cuotaN, montoC, fechaV, estado, fila);
            jM.setVisible(true);
        }
    }//GEN-LAST:event_grdCuotasMouseClicked

    private void txtVencimientoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVencimientoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVencimientoFocusLost

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
            java.util.logging.Logger.getLogger(JDCuotasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDCuotasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDCuotasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDCuotasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDCuotasCompra dialog = new JDCuotasCompra(new javax.swing.JFrame(), true);
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
    private javax.swing.JToggleButton btnCerrar;
    private javax.swing.JToggleButton btnGenerar;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JComboBox<String> cboPlazo;
    private javax.swing.JTable grdCuotas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblVencimiento;
    private javax.swing.JTextField txtCuotas;
    private javax.swing.JTextField txtMontoCredito;
    private javax.swing.JTextField txtSaldoRestante;
    private javax.swing.JTextField txtVencimiento;
    // End of variables declaration//GEN-END:variables
}
