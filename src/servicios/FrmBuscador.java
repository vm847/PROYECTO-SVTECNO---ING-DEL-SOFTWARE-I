/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package servicios;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import ventanas.Compras.FrmCompras;
import servicios.Grilla;
import ventanas.Compras.FrmCompras;

/**
 *
 * @author PC
 */
public class FrmBuscador extends javax.swing.JFrame {
    Grilla miGrilla1 = new Grilla();
    private FrmCompras frmCompras;
    String tablaA;
    
    public FrmBuscador() {
        initComponents();
    }

    public FrmBuscador(FrmCompras frmCompras, String tabla, String [] campos, String condicion, String [] cabeceras, int [] ancho) {
        initComponents();
        this.tablaA = tabla;
        this.frmCompras = frmCompras; 
        miGrilla1.configurarModelo(grdBuscar, cabeceras, ancho);
        miGrilla1.cargarGrilla(grdBuscar, tabla, campos, condicion); 
        this.condiciones();
        grdBuscar.requestFocus();
        grdBuscar.setRowSelectionInterval(0,0);
    }
    
    private void condiciones(){
        grdBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                }
            }
        });
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdBuscar = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Buscar"));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        grdBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "RUC", "Telefono", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grdBuscarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grdBuscar);
        if (grdBuscar.getColumnModel().getColumnCount() > 0) {
            grdBuscar.getColumnModel().getColumn(0).setPreferredWidth(200);
            grdBuscar.getColumnModel().getColumn(0).setMaxWidth(200);
            grdBuscar.getColumnModel().getColumn(1).setPreferredWidth(500);
            grdBuscar.getColumnModel().getColumn(2).setPreferredWidth(300);
            grdBuscar.getColumnModel().getColumn(3).setPreferredWidth(300);
            grdBuscar.getColumnModel().getColumn(4).setPreferredWidth(400);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addComponent(txtBuscar))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if(this.tablaA.equals("proveedores")){
            String tabla = "proveedores";
            String [] campos = {"cod_proveedor", "nombre_proveedor", "ruc_proveedor", "telefono_proveedor", "correo_proveedor"};
            miGrilla1.cargarGrilla(grdBuscar, tabla, campos, " where nombre_proveedor LIKE '%" + this.txtBuscar.getText() + "%'" +
                    " OR ruc_proveedor LIKE '%"+ this.txtBuscar.getText() + "%'");
        }
        if(this.tablaA.equals("productos p")){
                String tabla = "productos p";
                String [] campos = {"p.cod_producto", "p.nombre_producto", "m.nombre_marca", "p.precio_producto", "p.existencia_producto", "p.iva_producto"};
                miGrilla1.cargarGrilla(grdBuscar, tabla, campos, "  join marcas m on p.cod_marca = m.cod_marca "
                        + "where p.nombre_producto LIKE '%" + this.txtBuscar.getText() + "%'" +
                        " OR m.nombre_marca LIKE '%"+ this.txtBuscar.getText() + "%'");
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void grdBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grdBuscarMouseClicked
        if(evt.getClickCount() == 2){
            System.out.println(this.tablaA);
            int fila = this.grdBuscar.getSelectedRow();
            if(fila != -1){
                if(this.tablaA.equals("proveedores")){
                    frmCompras.setProveedor(this.grdBuscar.getValueAt(fila, 0).toString(), this.grdBuscar.getValueAt(fila, 1).toString(),
                    this.grdBuscar.getValueAt(fila, 2).toString());
                    this.dispose();                    
                }
                if(this.tablaA.equals("productos p")){
                    String codigo = this.grdBuscar.getValueAt(fila, 0).toString();
                    String nombre = this.grdBuscar.getValueAt(fila, 1).toString();
                    String precio = this.grdBuscar.getValueAt(fila, 3).toString();
                    String iva = this.grdBuscar.getValueAt(fila, 5).toString();
                    frmCompras.setProducto(codigo, nombre, precio, iva);
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_grdBuscarMouseClicked

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
            java.util.logging.Logger.getLogger(FrmBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBuscador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBuscador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grdBuscar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
