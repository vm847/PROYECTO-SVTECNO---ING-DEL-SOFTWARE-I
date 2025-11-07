/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventanas.principal;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import ventanas.presupuesto.FrmPresupuesto;
import servicios.BaseDatos1;
import servicios.JDBackup;
import ventanas.UsuariosListo.FrmUsuarios;
import ventanas.bancosListo.FrmBancos;
import ventanas.marcasListo.FrmMarcas;
import ventanas.clasificacionesListo.FrmClasificaciones;
import ventanas.clientesListo.FrmClientes;
import ventanas.proveedoresListo.FrmProveedores;
import productos.FrmProductos;
import ventanas.Compras.FrmCompras;
import ventanas.Compras.FrmInformeCompras;
import ventanas.UsuariosListo.JDCambioContra;
import ventas.FrmVentas;
import ventanas.lineaCredito.FrmLineaCredito;
import ventanas.cajasCasiListo.FrmCajas;
import ventanas.presupuesto.FrmInformePresupuestos;
import ventas.FrmInformeVentas;

/**
 *
 * @author Asus
 */
public class FrmVentanaPrincipal extends javax.swing.JFrame {
    String usuarioLogeado;
    String rolUsuario;
    BaseDatos1 bd = new BaseDatos1();
    Connection conn = null;
    
    
    public FrmVentanaPrincipal() {
        initComponents();
        this.noCerrar();
        this.usuarioLogeado = "VLOPEZ";
        this.rolUsuario = "ADMIN";
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("VENTANA PRINCIPAL - " + usuarioLogeado + " - " + rolUsuario);
        this.verificarPermisos(rolUsuario);
    }
    
    public FrmVentanaPrincipal(boolean login, String usuLogeado, String rol) {
        initComponents();
        this.noCerrar();
        this.usuarioLogeado = usuLogeado;
        this.rolUsuario = rol;
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("VENTANA PRINCIPAL - " + usuLogeado + " - " + rol);
        this.setIconImage(new ImageIcon(getClass().getResource("/iconos/svtIcono.jpeg")).getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.verificarPermisos(rol);
    }
    
    private void noCerrar(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Agregar listener de eventos para el botón de cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Mostrar diálogo de confirmación
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Desea finalizar la Sesion?", 
                "SERRAR SESION", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.INFORMATION_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void mostrarReporteClientes(){
        try {
            
            conn = bd.conexion;
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/reporteUsuariosGeneral.jasper"));
            Map<String,Object> parametros = new java.util.HashMap<>();
            
            parametros.put("estado_usuario", "activo");
            parametros.put("usuario_logeado", "vlopez");
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe "+ e.getMessage());   
        }

    }
    
    private void verificarPermisos(String rol){
        switch (rol) {
            case "CAJERO":
                this.itmMantenimientoClasificaciones.setVisible(false);
                this.itmMantenimientoMarcas.setVisible(false);
                this.itmMantenimientoProductos.setVisible(false);
                this.itmMantenimientoProveedores.setVisible(false);
                this.itmMantenimientoUsuarios.setVisible(false);
                this.itmCompras.setVisible(false);
                this.itmLineaCredito.setVisible(false);
                this.itmBackUp.setVisible(false);
                this.itmInformeCompras.setVisible(false);
                this.itmComprasAnuladas.setVisible(false);
                this.itmDeudasProveedor.setVisible(false);
                this.itmCreditosProveedor.setVisible(false);
                this.itmUsuarios.setVisible(false);
                this.itmClasificaciones.setVisible(false);
                this.itmMarcas.setVisible(false);
                this.itmProveedores.setVisible(false);                
                break;
            case "ENCARGADO STOCK":                
                this.menuGestiones.setVisible(false);
                this.itmMantenimientoClientes.setVisible(false);
                this.itmCajas.setVisible(false);
                this.itmLineaCredito.setVisible(false);
                this.itmMantenimientoBancos.setVisible(false);
                this.itmMantenimientoUsuarios.setVisible(false);
                this.itmVentas.setVisible(false);
                this.itmBackUp.setVisible(false);
                this.itmUsuarios.setVisible(false);
                this.itmBancos.setVisible(false);
                this.itmClientes.setVisible(false);
                this.itmVentas.setVisible(false);
                this.itmInformeVentas.setVisible(false);
                this.itmInformeVentasAnuladas.setVisible(false);
                this.itmPresupuestos.setVisible(false);
                this.itmInformePresupuestos.setVisible(false);
                this.itmCreditosClientes.setVisible(false);
                this.itmDeudasClientes.setVisible(false);
                break;
            case "SECRETARIO":
                this.itmLineaCredito.setVisible(false);
                this.itmMantenimientoUsuarios.setVisible(false);
                this.itmUsuarios.setVisible(false);
                break;
            default:
        }
    }
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuGestiones1 = new javax.swing.JMenu();
        itmCambiarContraseña = new javax.swing.JMenuItem();
        itmBackUp = new javax.swing.JMenuItem();
        itmBackUp1 = new javax.swing.JMenuItem();
        menuMantenimientos = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itmMantenimientoUsuarios = new javax.swing.JMenuItem();
        itmMantenimientoBancos = new javax.swing.JMenuItem();
        itmMantenimientoMarcas = new javax.swing.JMenuItem();
        itmMantenimientoClasificaciones = new javax.swing.JMenuItem();
        itmMantenimientoClientes = new javax.swing.JMenuItem();
        itmMantenimientoProveedores = new javax.swing.JMenuItem();
        itmMantenimientoProductos = new javax.swing.JMenuItem();
        menuTransacciones = new javax.swing.JMenu();
        itmCompras = new javax.swing.JMenuItem();
        itmVentas = new javax.swing.JMenuItem();
        itmPresupuestos = new javax.swing.JMenuItem();
        menuGestiones = new javax.swing.JMenu();
        itmLineaCredito = new javax.swing.JMenuItem();
        itmCajas = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        itmInformePresupuestos = new javax.swing.JMenuItem();
        itmInformeCompras = new javax.swing.JMenuItem();
        itmComprasAnuladas = new javax.swing.JMenuItem();
        itmDeudasProveedor = new javax.swing.JMenuItem();
        itmCreditosProveedor = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itmInformeVentas = new javax.swing.JMenuItem();
        itmInformeVentasAnuladas = new javax.swing.JMenuItem();
        itmDeudasClientes = new javax.swing.JMenuItem();
        itmCreditosClientes = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itmUsuarios = new javax.swing.JMenuItem();
        itmBancos = new javax.swing.JMenuItem();
        itmClasificaciones = new javax.swing.JMenuItem();
        itmMarcas = new javax.swing.JMenuItem();
        itmProductos = new javax.swing.JMenuItem();
        itmClientes = new javax.swing.JMenuItem();
        itmProveedores = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/svtecno 1020.jpg"))); // NOI18N

        menuGestiones1.setText("Opciones");

        itmCambiarContraseña.setText("Cambiar Contraseña");
        itmCambiarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmCambiarContraseñaActionPerformed(evt);
            }
        });
        menuGestiones1.add(itmCambiarContraseña);

        itmBackUp.setText("Copia de seguridad");
        itmBackUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmBackUpActionPerformed(evt);
            }
        });
        menuGestiones1.add(itmBackUp);

        itmBackUp1.setText("Cerrar Sesion");
        itmBackUp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmBackUp1ActionPerformed(evt);
            }
        });
        menuGestiones1.add(itmBackUp1);

        jMenuBar1.add(menuGestiones1);

        menuMantenimientos.setText("Mantenimientos");
        menuMantenimientos.add(jSeparator1);

        itmMantenimientoUsuarios.setText("Mantenimiento de Usuarios");
        itmMantenimientoUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoUsuariosActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoUsuarios);

        itmMantenimientoBancos.setText("Mantenimiento de Bancos");
        itmMantenimientoBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoBancosActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoBancos);

        itmMantenimientoMarcas.setText("Mantenimiento de Marcas");
        itmMantenimientoMarcas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoMarcasActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoMarcas);

        itmMantenimientoClasificaciones.setText("Mantenimiento de Clasificaciones");
        itmMantenimientoClasificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoClasificacionesActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoClasificaciones);

        itmMantenimientoClientes.setText("Mantenimiento de Clientes");
        itmMantenimientoClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoClientesActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoClientes);

        itmMantenimientoProveedores.setText("Mantenimiento de Proveedores");
        itmMantenimientoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoProveedoresActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoProveedores);

        itmMantenimientoProductos.setText("Mantenimiento de Productos");
        itmMantenimientoProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMantenimientoProductosActionPerformed(evt);
            }
        });
        menuMantenimientos.add(itmMantenimientoProductos);

        jMenuBar1.add(menuMantenimientos);

        menuTransacciones.setText("Transacciones");

        itmCompras.setText("Compras");
        itmCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmComprasActionPerformed(evt);
            }
        });
        menuTransacciones.add(itmCompras);

        itmVentas.setText("Ventas");
        itmVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmVentasActionPerformed(evt);
            }
        });
        menuTransacciones.add(itmVentas);

        itmPresupuestos.setText("Presupuestos");
        itmPresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmPresupuestosActionPerformed(evt);
            }
        });
        menuTransacciones.add(itmPresupuestos);

        jMenuBar1.add(menuTransacciones);

        menuGestiones.setText("Gestiones");

        itmLineaCredito.setText("Gestion de Lineas de Credito");
        itmLineaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmLineaCreditoActionPerformed(evt);
            }
        });
        menuGestiones.add(itmLineaCredito);

        itmCajas.setText("Gestion de Cajas");
        itmCajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmCajasActionPerformed(evt);
            }
        });
        menuGestiones.add(itmCajas);

        jMenuBar1.add(menuGestiones);

        menuReportes.setText("Reportes");

        itmInformePresupuestos.setText("Presupuestos");
        itmInformePresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmInformePresupuestosActionPerformed(evt);
            }
        });
        menuReportes.add(itmInformePresupuestos);

        itmInformeCompras.setText("Compras");
        itmInformeCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmInformeComprasActionPerformed(evt);
            }
        });
        menuReportes.add(itmInformeCompras);

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
        menuReportes.add(jSeparator2);

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
        menuReportes.add(jSeparator3);

        itmUsuarios.setText("Usuarios");
        itmUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmUsuariosActionPerformed(evt);
            }
        });
        menuReportes.add(itmUsuarios);

        itmBancos.setText("Bancos");
        itmBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmBancosActionPerformed(evt);
            }
        });
        menuReportes.add(itmBancos);

        itmClasificaciones.setText("Clasificaciones");
        itmClasificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmClasificacionesActionPerformed(evt);
            }
        });
        menuReportes.add(itmClasificaciones);

        itmMarcas.setText("Marcas");
        itmMarcas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMarcasActionPerformed(evt);
            }
        });
        menuReportes.add(itmMarcas);

        itmProductos.setText("Productos");
        itmProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmProductosActionPerformed(evt);
            }
        });
        menuReportes.add(itmProductos);

        itmClientes.setText("Clientes");
        itmClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmClientesActionPerformed(evt);
            }
        });
        menuReportes.add(itmClientes);

        itmProveedores.setText("Proveedores");
        itmProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmProveedoresActionPerformed(evt);
            }
        });
        menuReportes.add(itmProveedores);

        jMenuBar1.add(menuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmMantenimientoClasificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoClasificacionesActionPerformed
       FrmClasificaciones clas = new FrmClasificaciones(this.usuarioLogeado, this.rolUsuario);
       clas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       clas.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoClasificacionesActionPerformed

    private void itmMantenimientoUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoUsuariosActionPerformed
        FrmUsuarios usu = new FrmUsuarios(this.usuarioLogeado, this.rolUsuario);
        usu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        usu.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoUsuariosActionPerformed

    private void itmMantenimientoBancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoBancosActionPerformed
        FrmBancos banco = new FrmBancos(this.usuarioLogeado, this.rolUsuario);
        banco.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        banco.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoBancosActionPerformed

    private void itmMantenimientoMarcasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoMarcasActionPerformed
        FrmMarcas marcas = new FrmMarcas(this.usuarioLogeado, this.rolUsuario);
        marcas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        marcas.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoMarcasActionPerformed

    private void itmMantenimientoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoClientesActionPerformed
        FrmClientes clie = new FrmClientes(this.usuarioLogeado, this.rolUsuario);
        clie.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        clie.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoClientesActionPerformed

    private void itmMantenimientoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoProveedoresActionPerformed
        FrmProveedores prove = new FrmProveedores(this.usuarioLogeado, this.rolUsuario);
        prove.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        prove.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoProveedoresActionPerformed

    private void itmMantenimientoProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMantenimientoProductosActionPerformed
        FrmProductos produ = new FrmProductos(this.usuarioLogeado, this.rolUsuario);
        produ.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        produ.setVisible(true);
    }//GEN-LAST:event_itmMantenimientoProductosActionPerformed

    private void itmComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmComprasActionPerformed
        FrmCompras compras = new FrmCompras(this.usuarioLogeado, this.rolUsuario);
        compras.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        compras.setVisible(true);
    }//GEN-LAST:event_itmComprasActionPerformed

    private void itmVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmVentasActionPerformed
        FrmVentas ventas = new FrmVentas(this.usuarioLogeado, this.rolUsuario);
        ventas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ventas.setVisible(true);
    }//GEN-LAST:event_itmVentasActionPerformed

    private void itmLineaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmLineaCreditoActionPerformed
        FrmLineaCredito lcredit = new FrmLineaCredito(this.usuarioLogeado, this.rolUsuario);
        lcredit.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        lcredit.setVisible(true);
    }//GEN-LAST:event_itmLineaCreditoActionPerformed

    private void itmCajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmCajasActionPerformed
        FrmCajas caja = new FrmCajas(this.usuarioLogeado, this.rolUsuario);
        caja.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        caja.setVisible(true);
    }//GEN-LAST:event_itmCajasActionPerformed

    private void itmInformeComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmInformeComprasActionPerformed
        FrmInformeCompras fcom = new FrmInformeCompras("ACTIVO");
        fcom.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fcom.setVisible(true);
    }//GEN-LAST:event_itmInformeComprasActionPerformed

    private void itmComprasAnuladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmComprasAnuladasActionPerformed
        FrmInformeCompras fcom = new FrmInformeCompras("INACTIVO");
        fcom.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fcom.setVisible(true);
    }//GEN-LAST:event_itmComprasAnuladasActionPerformed

    private void itmDeudasProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeudasProveedorActionPerformed
        FrmInformeCompras fr = new FrmInformeCompras("ACTIVO", "DEUDAS PROVEEDORES");
        fr.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fr.setVisible(true);
    }//GEN-LAST:event_itmDeudasProveedorActionPerformed

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

    private void itmDeudasClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeudasClientesActionPerformed
        FrmInformeVentas iv = new FrmInformeVentas();
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_itmDeudasClientesActionPerformed

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
        FrmInformeVentas iv = new FrmInformeVentas(cabeceras, tamaños, tabla, campos, condicion, "");
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_itmCreditosClientesActionPerformed

    private void itmUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmUsuariosActionPerformed
        JComboBox cboRol = new JComboBox();
        String [][] datos = bd.obtenerRegistros("select rol_usuario from usuarios group by rol_usuario");
        if(datos != null){
            bd.agregarItemCombo(cboRol, 0, "TODOS");
            for (int x = 0; x < datos.length; x++) {
                bd.agregarItemCombo(cboRol, x+1, datos[x][0]);
            }
        }
        cboRol.setSelectedIndex(0);
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("ROL: "));
        panel.add(cboRol);
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ROL", JOptionPane.DEFAULT_OPTION);
        
        if(imput == JOptionPane.OK_OPTION){
            String rol = cboRol.getSelectedItem().toString();
            String estado = cboEstado.getSelectedItem().toString();
            System.out.println(estado + " - " + rol);
            
            
            if(estado.equals("TODOS") && rol.equals("TODOS")){
                String [] nomParamet = {};
                String [] paramet = {};
                System.out.println("/reportes/reporteUsuarios.jasper");
                bd.llamarReporte("/reportes/reporteUsuarios.jasper", nomParamet, paramet);
            }else if(estado.equals("TODOS") && !rol.equals("TODOS")){
                String [] nomParamet = {"rol_usuario"};
                String [] paramet = {rol};
                System.out.println("/reportes/reporteUsuarioPorRol.jasper");
                bd.llamarReporte("/reportes/reporteUsuarioPorRol.jasper", nomParamet, paramet);                    
            }else if(!estado.equals("TODOS") && rol.equals("TODOS")){
                String [] nomParamet = {"estado_usuario"};
                String [] paramet = {estado};
                System.out.println("/reportes/reporteUsuariosEstado.jasper");
                bd.llamarReporte("/reportes/reporteUsuariosEstado.jasper", nomParamet, paramet); 
            }else if(!estado.equals("TODOS") && !rol.equals("TODOS")){
                String [] nomParamet = {"estado_usuario", "rol_usuario"};
                String [] paramet = {estado,rol};
                System.out.println("/reportes/reporteUsuariosRolEstado.jasper");
                bd.llamarReporte("/reportes/reporteUsuariosRolEstado.jasper", nomParamet, paramet); 
            }
        }
    }//GEN-LAST:event_itmUsuariosActionPerformed

    private void itmBancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmBancosActionPerformed
        JComboBox cboTipoCuenta = new JComboBox();
        bd.agregarItemCombo(cboTipoCuenta, 0, "TODOS");
        bd.agregarItemCombo(cboTipoCuenta, 1, "CUENTA CORRIENTE");
        bd.agregarItemCombo(cboTipoCuenta, 2, "CAJA DE AHORRO");
        cboTipoCuenta.setSelectedIndex(0);
        
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("TIPO: "));
        panel.add(cboTipoCuenta);
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String tipo = cboTipoCuenta.getSelectedItem().toString();
            String estado = cboEstado.getSelectedItem().toString();
            System.out.println(estado + " - " + tipo);

            if(estado.equals("TODOS") && tipo.equals("TODOS")){
                String url = "/reportes/reporteBancos.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);
                
            }else if(estado.equals("TODOS") && !tipo.equals("TODOS")){
                String url = "/reportes/reporteBancosTipoCuenta.jasper";
                String [] nomParamet = {"tipo_cuenta_banco"};
                String [] paramet = {tipo};
                bd.llamarReporte(url, nomParamet, paramet);
                
            }else if(!estado.equals("TODOS") && tipo.equals("TODOS")){
                String url = "/reportes/reporteBancosEstado.jasper";
                String [] nomParamet = {"estado_banco"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);
                
            }else if(!estado.equals("TODOS") && !tipo.equals("TODOS")){
                String url = "/reportes/reporteBancosTipoEstado.jasper";
                String [] nomParamet = {"estado_usuario", "tipo_cuenta_banco"};
                String [] paramet = {estado,tipo};
                bd.llamarReporte(url, nomParamet, paramet);
            }
        }
    }//GEN-LAST:event_itmBancosActionPerformed

    private void itmClasificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmClasificacionesActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));;
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();

            if(estado.equals("TODOS")){
                String url = "/reportes/reporteClasificaciones.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteClasificacionesEstado.jasper";
                String [] nomParamet = {"estado_clasificacion"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

            }
        }
    }//GEN-LAST:event_itmClasificacionesActionPerformed

    private void itmMarcasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMarcasActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));;
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();

            if(estado.equals("TODOS")){
                String url = "/reportes/reporteMarcas.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteMarcasEstado.jasper";
                String [] nomParamet = {"estado_marca"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

            }
        }
    }//GEN-LAST:event_itmMarcasActionPerformed

    private void itmProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmProductosActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        JComboBox cboMarca = new JComboBox();
        String [][] marcas = bd.obtenerRegistros(
                "select p.cod_marca, m.nombre_marca from productos p\n" +
                "JOIN marcas m ON p.cod_marca=m.cod_marca\n" +
                "GROUP BY m.nombre_marca"
        );
        if(marcas != null){
            bd.agregarItemCombo(cboMarca, 0, "TODOS");
            for (int x = 0; x < marcas.length; x++) {
                bd.agregarItemCombo(cboMarca, Integer.parseInt(marcas[x][0]), marcas[x][1]);
            }
        }
        cboMarca.setSelectedIndex(0);
        
        JComboBox cboClasificaciones = new JComboBox();
        String [][] clasi = bd.obtenerRegistros(
                "select p.cod_clasificacion, c.nombre_clasificacion from productos p\n" +
                "JOIN clasificacion c ON p.cod_clasificacion=c.cod_clasificacion\n" +
                "GROUP BY c.nombre_clasificacion"
        );
        if(clasi != null){
            bd.agregarItemCombo(cboClasificaciones, 0, "TODOS");
            for (int x = 0; x < clasi.length; x++) {
                bd.agregarItemCombo(cboClasificaciones, Integer.parseInt(clasi[x][0]), clasi[x][1]);
            }
        }
        cboClasificaciones.setSelectedIndex(0);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.add(new JLabel("ESTADO: "));
        panel.add(cboEstado);
        panel.add(new JLabel("MARCA:"));
        panel.add(cboMarca);
        panel.add(new JLabel("CLASIFICACION:"));
        panel.add(cboClasificaciones);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ROL", JOptionPane.DEFAULT_OPTION);
        
        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();
            String marca = cboMarca.getSelectedItem().toString();
            String clasifi = cboClasificaciones.getSelectedItem().toString();
            
            
            if(estado.equals("TODOS") && marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductos.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);        
            }else if(!estado.equals("TODOS") && marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosEstado.jasper";
                String [] nomParamet = {"estado_producto"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);                        
            }else if(estado.equals("TODOS") && !marca.equals("TODOS") && clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosMarca.jasper";
                String [] nomParamet = {"marca_producto"};
                String [] paramet = {marca};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(estado.equals("TODOS") && marca.equals("TODOS") && !clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosClasificacion.jasper";
                String [] nomParamet = {"clasificacion_producto"};
                String [] paramet = {clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && !marca.equals("TODOS") && clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosEstadoMarca.jasper";
                String [] nomParamet = {"estado_producto", "marca_producto"};
                String [] paramet = {estado, marca};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && marca.equals("TODOS") && !clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosEstadoClasificacion.jasper";
                String [] nomParamet = {"estado_producto", "clasificacion_producto"};
                String [] paramet = {estado, clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(estado.equals("TODOS") && !marca.equals("TODOS") && !clasifi.equals("TODOS")){                
                String url = "/reportes/reporteProductosMarcaClasificacion.jasper";
                String [] nomParamet = {"marca_producto", "clasificacion_producto"};
                String [] paramet = {marca, clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }else if(!estado.equals("TODOS") && !marca.equals("TODOS") && !clasifi.equals("TODOS")){
                String url = "/reportes/reporteProductosEstadoMarcaClasificacion.jasper";
                String [] nomParamet = {"estado_producto","marca_producto", "clasificacion_producto"};
                String [] paramet = {estado, marca, clasifi};
                bd.llamarReporte(url, nomParamet, paramet);    
            }
        }
    }//GEN-LAST:event_itmProductosActionPerformed

    private void itmClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmClientesActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));;
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();

            if(estado.equals("TODOS")){
                String url = "/reportes/reporteClientes.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteClientesEstado.jasper";
                String [] nomParamet = {"estado_cliente"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

            }
        }
    }//GEN-LAST:event_itmClientesActionPerformed

    private void itmProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmProveedoresActionPerformed
        JComboBox cboEstado = new JComboBox();
        bd.agregarItemCombo(cboEstado, 0, "TODOS");
        bd.agregarItemCombo(cboEstado, 1, "ACTIVO");
        bd.agregarItemCombo(cboEstado, 2, "INACTIVO");
        cboEstado.setSelectedIndex(0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));;
        panel.add(new JLabel("ESTADO:"));
        panel.add(cboEstado);
        int imput;
        imput = JOptionPane.showConfirmDialog(this, panel, "SELECCIONE EL ESTADO", JOptionPane.DEFAULT_OPTION);

        if(imput == JOptionPane.OK_OPTION){
            String estado = cboEstado.getSelectedItem().toString();

            if(estado.equals("TODOS")){
                String url = "/reportes/reporteProveedores.jasper";
                String [] nomParamet = {};
                String [] paramet = {};
                bd.llamarReporte(url, nomParamet, paramet);

            }else{
                String url = "/reportes/reporteProveedoresEstado.jasper";
                String [] nomParamet = {"estado_proveedor"};
                String [] paramet = {estado};
                bd.llamarReporte(url, nomParamet, paramet);

            }
        }
    }//GEN-LAST:event_itmProveedoresActionPerformed

    private void itmPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmPresupuestosActionPerformed
        FrmPresupuesto fp = new FrmPresupuesto(usuarioLogeado, rolUsuario);
        fp.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fp.setVisible(true);
    }//GEN-LAST:event_itmPresupuestosActionPerformed

    private void itmCambiarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmCambiarContraseñaActionPerformed
        JDCambioContra jc = new JDCambioContra(this, true, this, usuarioLogeado, rolUsuario);
        jc.setVisible(true);
    }//GEN-LAST:event_itmCambiarContraseñaActionPerformed

    private void itmBackUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmBackUpActionPerformed
        JDBackup bc = new JDBackup(this, true, this.usuarioLogeado);
        bc.setVisible(true);
    }//GEN-LAST:event_itmBackUpActionPerformed

    private void itmInformePresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmInformePresupuestosActionPerformed
        FrmInformePresupuestos iv = new FrmInformePresupuestos(this.usuarioLogeado, this.rolUsuario, "TODOS", "TODOS LOS PRESUPUESTOS");
        iv.setDefaultCloseOperation(HIDE_ON_CLOSE);
        iv.setVisible(true);
    }//GEN-LAST:event_itmInformePresupuestosActionPerformed

    private void itmBackUp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmBackUp1ActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Desea finalizar la Sesion?", 
            "SERRAR SESION", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
            if (confirmacion == JOptionPane.YES_OPTION) {
                this.dispose();
            }
    }//GEN-LAST:event_itmBackUp1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem itmBackUp;
    public javax.swing.JMenuItem itmBackUp1;
    public javax.swing.JMenuItem itmBancos;
    public javax.swing.JMenuItem itmCajas;
    public javax.swing.JMenuItem itmCambiarContraseña;
    public javax.swing.JMenuItem itmClasificaciones;
    public javax.swing.JMenuItem itmClientes;
    public javax.swing.JMenuItem itmCompras;
    public javax.swing.JMenuItem itmComprasAnuladas;
    public javax.swing.JMenuItem itmCreditosClientes;
    public javax.swing.JMenuItem itmCreditosProveedor;
    public javax.swing.JMenuItem itmDeudasClientes;
    public javax.swing.JMenuItem itmDeudasProveedor;
    public javax.swing.JMenuItem itmInformeCompras;
    public javax.swing.JMenuItem itmInformePresupuestos;
    public javax.swing.JMenuItem itmInformeVentas;
    public javax.swing.JMenuItem itmInformeVentasAnuladas;
    public javax.swing.JMenuItem itmLineaCredito;
    public javax.swing.JMenuItem itmMantenimientoBancos;
    public javax.swing.JMenuItem itmMantenimientoClasificaciones;
    public javax.swing.JMenuItem itmMantenimientoClientes;
    public javax.swing.JMenuItem itmMantenimientoMarcas;
    public javax.swing.JMenuItem itmMantenimientoProductos;
    public javax.swing.JMenuItem itmMantenimientoProveedores;
    public javax.swing.JMenuItem itmMantenimientoUsuarios;
    public javax.swing.JMenuItem itmMarcas;
    public javax.swing.JMenuItem itmPresupuestos;
    public javax.swing.JMenuItem itmProductos;
    public javax.swing.JMenuItem itmProveedores;
    public javax.swing.JMenuItem itmUsuarios;
    public javax.swing.JMenuItem itmVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator7;
    public javax.swing.JMenu menuGestiones;
    public javax.swing.JMenu menuGestiones1;
    public javax.swing.JMenu menuMantenimientos;
    private javax.swing.JMenu menuReportes;
    public javax.swing.JMenu menuTransacciones;
    // End of variables declaration//GEN-END:variables
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
            java.util.logging.Logger.getLogger(FrmVentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVentanaPrincipal().setVisible(true);
            }
        });
    }

}
