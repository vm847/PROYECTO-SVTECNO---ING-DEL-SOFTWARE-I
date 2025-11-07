package ventanas.cajasCasiListo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import servicios.Fechas;
import servicios.Grilla;

/**
 *
 * @author Asus
 */
public class FrmCajas extends javax.swing.JFrame {
    Grilla miGrilla1 = new Grilla();
    BaseDatos1 bd = new BaseDatos1();
    Fechas fe = new Fechas();
    String usuarioLogeado;
    String rol;
    String date1,date2;
    
    public FrmCajas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.dateDesde.setMaxSelectableDate(new java.util.Date());
        this.dateHasta.setMaxSelectableDate(new java.util.Date());
        this.refrescar();
    }

    public FrmCajas(String usuLogeado, String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("GESTION DE CAJAS - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        this.usuarioLogeado = usuLogeado;
        this.rol = rol;
        this.dateDesde.setMaxSelectableDate(new java.util.Date());
        this.dateHasta.setMaxSelectableDate(new java.util.Date());
        this.refrescar();
    }
    
    private void refrescar(){
        bd.mostrarFechaJDateChooser(dateDesde, fe.obtenerFechaMenosDias(-20));
        bd.mostrarFechaJDateChooser(dateHasta, fe.obtenerFechaActual());
        this.actualizarGrilla();
    }
    
    public void actualizarGrilla(){
        String fechaAntes = fe.formatearFechaHoraJDateChooserDesde(dateDesde);
        String fechaDespues = fe.formatearFechaHoraJDateChooserHasta(dateHasta);
        
        String tabla = "cajas ca";
        String [] campos = {"ca.cod_caja", "us.nom_usuario", "ca.fecha_apertura_caja", "ca.fecha_cierre_caja", "ca.usuario_reapertura", 
            "ca.usuario_insercion", "ca.fecha_insercion", "ca.usuario_modificacion", "ca.fecha_modificacion"};
        String condicion = "JOIN usuarios us ON ca.cod_usuario = us.cod_usuario "
                + "where ca.fecha_insercion >= '" + fechaAntes + "' AND ca.fecha_insercion <= '" + fechaDespues + "'";
        miGrilla1.cargarGrilla(grdCajas, tabla, campos, condicion);
        miGrilla1.formatoFechaGrilla(grdCajas,2);
        miGrilla1.formatoFechaGrilla(grdCajas,3);
        miGrilla1.formatoFechaGrilla(grdCajas,6);
        miGrilla1.formatoFechaGrilla(grdCajas,8);
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
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        dateDesde = new com.toedter.calendar.JDateChooser();
        dateHasta = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        scpContenedor = new javax.swing.JScrollPane();
        grdCajas = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        itmReportePorFecha = new javax.swing.JMenuItem();
        itmReportePorCajero = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateDesde.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateDesde.setForeground(new java.awt.Color(0, 102, 255));
        dateDesde.setDateFormatString("dd MM yyyy");
        dateDesde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateDesdePropertyChange(evt);
            }
        });
        jPanel2.add(dateDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 130, 30));

        dateHasta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dateHasta.setForeground(new java.awt.Color(0, 102, 255));
        dateHasta.setDateFormatString("dd MM yyyy");
        dateHasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateHastaPropertyChange(evt);
            }
        });
        jPanel2.add(dateHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 130, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hasta");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 40, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Codigo");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 30));

        btnNuevo.setBackground(new java.awt.Color(204, 204, 204));
        btnNuevo.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel2.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 70, 40));

        btnModificar.setBackground(new java.awt.Color(204, 204, 204));
        btnModificar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 70, 40));

        btnEliminar.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 70, 40));

        btnCerrar.setBackground(new java.awt.Color(204, 204, 204));
        btnCerrar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 70, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 980, 60));

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Habilitaciones");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, 38));

        scpContenedor.setBackground(new java.awt.Color(255, 255, 255));
        scpContenedor.setAutoscrolls(true);

        grdCajas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, null));
        grdCajas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        grdCajas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CAJERO", "APERTURA", "CIERRE", "REAPERTURA", "INSERSION", "FECHA INSERCION", "MODIFICACION", "FECHA MODIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdCajas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        grdCajas.setFillsViewportHeight(true);
        grdCajas.setRowMargin(3);
        grdCajas.setShowGrid(true);
        scpContenedor.setViewportView(grdCajas);
        if (grdCajas.getColumnModel().getColumnCount() > 0) {
            grdCajas.getColumnModel().getColumn(0).setPreferredWidth(75);
            grdCajas.getColumnModel().getColumn(1).setPreferredWidth(200);
            grdCajas.getColumnModel().getColumn(2).setPreferredWidth(150);
            grdCajas.getColumnModel().getColumn(3).setPreferredWidth(150);
            grdCajas.getColumnModel().getColumn(4).setPreferredWidth(100);
            grdCajas.getColumnModel().getColumn(5).setPreferredWidth(150);
            grdCajas.getColumnModel().getColumn(6).setPreferredWidth(150);
            grdCajas.getColumnModel().getColumn(7).setPreferredWidth(150);
            grdCajas.getColumnModel().getColumn(8).setPreferredWidth(150);
        }

        jPanel4.add(scpContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 350));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 78, 980, 350));

        jMenu1.setText("Menu");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reportes");

        itmReportePorFecha.setText("Reporte de cajas");
        itmReportePorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmReportePorFechaActionPerformed(evt);
            }
        });
        jMenu2.add(itmReportePorFecha);

        itmReportePorCajero.setText("Reporte por Cajero");
        itmReportePorCajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmReportePorCajeroActionPerformed(evt);
            }
        });
        jMenu2.add(itmReportePorCajero);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        JDNuevaCaja jN = new JDNuevaCaja(this, true, this, this.usuarioLogeado);
        jN.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jN.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = this.grdCajas.getSelectedRow();
        if(fila > -1){
            String codigo = this.grdCajas.getValueAt(fila, 0).toString();
            JDEliminarCaja jM = new JDEliminarCaja(this, true, this, codigo, usuarioLogeado);
            jM.setDefaultCloseOperation(HIDE_ON_CLOSE);
            jM.setVisible(true);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = this.grdCajas.getSelectedRow();
        if(fila > -1){
            String codigo = this.grdCajas.getValueAt(fila, 0).toString();
            JDModificarCaja jM = new JDModificarCaja(this, true, this, codigo, usuarioLogeado);
            jM.setDefaultCloseOperation(HIDE_ON_CLOSE);
            jM.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void dateDesdePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateDesdePropertyChange
        if(this.dateDesde.getDate() != null && this.dateHasta.getDate() != null){
            if(validarFecha()){            
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_dateDesdePropertyChange

    private void dateHastaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateHastaPropertyChange
        if(this.dateDesde.getDate() != null && this.dateHasta.getDate() != null){
            if(validarFecha()){            
                this.actualizarGrilla();
            }
        }
    }//GEN-LAST:event_dateHastaPropertyChange

    private void itmReportePorCajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmReportePorCajeroActionPerformed
        JComboBox cbo = new JComboBox();
        String [][] datos = bd.obtenerRegistros("select c.cod_caja, u.nom_usuario from cajas c "
                + "JOIN usuarios u ON c.cod_usuario = u.cod_usuario"
                + " GROUP BY u.nom_usuario");
        if(datos != null){
            for (int x = 0; x < datos.length; x++) {
                bd.agregarItemCombo(cbo, Integer.parseInt(datos[x][0]), datos[x][1]);
            }
        }
        int imput;
        imput = JOptionPane.showConfirmDialog(this, cbo, "SELECCIONE AL CAJERO", JOptionPane.DEFAULT_OPTION);
        
        if(imput == JOptionPane.OK_OPTION){
            String cajero = cbo.getSelectedItem().toString();
            
            String desde = fe.formatearFechaHoraJDateChooserDesde(dateDesde);
            String hasta = fe.formatearFechaHoraJDateChooserHasta(dateHasta);
            java.sql.Date date_desde = new java.sql.Date(dateDesde.getDate().getTime()); 
            java.sql.Date date_hasta = new java.sql.Date(dateHasta.getDate().getTime());
            Map<String,Object> parametros = new java.util.HashMap<>();

            parametros.put("fecha_desde", date_desde);
            parametros.put("fecha_hasta", date_hasta);
            parametros.put("desde", desde);
            parametros.put("hasta", hasta);
            parametros.put("nom_usuario", cajero);

            String url = "/reportes/reporteCajasPorUsuario.jasper";
            bd.llamarReporteConFechas(url, parametros);
        }
    }//GEN-LAST:event_itmReportePorCajeroActionPerformed

    private void itmReportePorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmReportePorFechaActionPerformed
        String desde = fe.formatearFechaHoraJDateChooserDesde(dateDesde);
        String hasta = fe.formatearFechaHoraJDateChooserHasta(dateHasta);
        java.sql.Date date_desde = new java.sql.Date(dateDesde.getDate().getTime()); 
        java.sql.Date date_hasta = new java.sql.Date(dateHasta.getDate().getTime());
        Map<String,Object> parametros = new java.util.HashMap<>();
        
        parametros.put("fecha_desde", date_desde);
        parametros.put("fecha_hasta", date_hasta);
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);
        
        String url = "/reportes/reporteCajas.jasper";
        
        
        bd.llamarReporteConFechas(url, parametros);
    }//GEN-LAST:event_itmReportePorFechaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCajas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCajas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCajas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCajas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCajas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private com.toedter.calendar.JDateChooser dateDesde;
    private com.toedter.calendar.JDateChooser dateHasta;
    public static javax.swing.JTable grdCajas;
    private javax.swing.JMenuItem itmReportePorCajero;
    private javax.swing.JMenuItem itmReportePorFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane scpContenedor;
    // End of variables declaration//GEN-END:variables
}
