/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ventanas.productosListo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import servicios.BaseDatos1;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDModificarProducto extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Connection conn = null;
    private boolean img1cargado = false;
    private boolean img1Borrado = false;
    private boolean img2cargado = false;
    private boolean img2Borrado = false;
    private String rutaImg1 = "";
    private String rutaImg2 = "";
    private FrmProductos frmProductos;
    String usuarioLogeado;
    
    public JDModificarProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("productos", "cod_producto"));
        bd.llenarComboHasta20(this.cboClasificacion, "cod_clasificacion, nombre_clasificacion", "clasificacion WHERE estado_clasificacion = 'ACTIVO'");
        bd.llenarComboHasta20(this.cboMarca, "cod_marca, nombre_marca", "marcas where estado_marca = 'ACTIVO'");
        this.mostrarRegistro("1");
    }
    
    public JDModificarProducto(java.awt.Frame parent, boolean modal, FrmProductos frmProductos, String usuLogeado, String codigo) {
        super(parent, modal);
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.frmProductos = frmProductos;
        this.setTitle("Nuevo Producto - " + usuLogeado);
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("productos", "cod_producto"));
        bd.llenarComboHasta20(this.cboClasificacion, "cod_clasificacion, nombre_clasificacion", "clasificacion WHERE estado_clasificacion = 'ACTIVO'");
        bd.llenarComboHasta20(this.cboMarca, "cod_marca, nombre_marca", "marcas where estado_marca = 'ACTIVO'");
        this.mostrarRegistro(codigo);
        this.txtNombre.requestFocus();
    }
    
    private void restricciones(){
        aj.soloMayusculas(txtNombre, 50);
        aj.soloNumeros(txtPrecio, 10);
        aj.soloNumeros(txtStock, 10);
    }
    
    private boolean insertarImagen(JLabel contenedor, String imagenNum){
        String rutaImg = "";
        JFileChooser buscador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG y PNG", "jpg","png");
        buscador.setFileFilter(filtro);

        int respuesta = buscador.showOpenDialog(this);

        if(respuesta == JFileChooser.APPROVE_OPTION){
            rutaImg = buscador.getSelectedFile().getPath();
            if(imagenNum.equals("1")){
                this.rutaImg1 = rutaImg;
                System.out.println("Ruta img 1 " + imagenNum);                
            }else{
                this.rutaImg2 = rutaImg;
                System.out.println("Ruta img 2 " + imagenNum); 
            }
            this.mostrarImg(contenedor, rutaImg);
            return true;
        }
        return false;
    }
    
    public void setClasificacion(int codigo, String clasificacion){
        if(!bd.seleccionarItemComboBD(cboClasificacion, codigo)){
            bd.agregarItemCombo(cboClasificacion, codigo, clasificacion);
        }
    }
    public void setMarca(int codigo, String marca){
        if(!bd.seleccionarItemComboBD(cboMarca, codigo)){
            bd.agregarItemCombo(cboMarca, codigo, marca);
        }
    }
    
    private void mostrarImg(JLabel contenedor, String rutaImg){
        Image img = new ImageIcon(rutaImg).getImage();
        System.out.println("ruta img " + rutaImg);
        System.out.println("img " + img);
        ImageIcon miImg = new ImageIcon(img.getScaledInstance(contenedor.getWidth(), contenedor.getHeight(), Image.SCALE_SMOOTH));
        contenedor.setIcon(miImg);
    }
    
    private void guardarImagenes(String rutaImagen1, String campo, int codProducto) {
        System.out.println("Ruta Img " + rutaImagen1);
        conn = bd.obtenerConexion();
        System.out.println("Conexion =  " + conn);
        PreparedStatement pstmt = null;
        FileInputStream fis1 = null;
        try {
            pstmt = conn.prepareStatement("UPDATE productos SET " + campo + " = ? WHERE cod_producto = ?");
            fis1 = new FileInputStream(new File(rutaImagen1));

            pstmt.setBinaryStream(1, fis1, (int) new File(rutaImagen1).length());
            pstmt.setInt(2, codProducto);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar imágenes: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer archivos: " + e.getMessage());
        } finally {
            try {
                if (fis1 != null) fis1.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error al cerrar archivos: " + e.getMessage());
            }
        } 
    }
    
    private void mostrarRegistro(String codigo){
        String sql = "select "
                + "pr.cod_producto,"
                + "pr.nombre_producto,"
                + "pr.img1_producto,"
                + "pr.img2_producto,"
                + "pr.cod_clasificacion,"
                + "cl.nombre_clasificacion,"
                + "pr.cod_marca,"
                + "ma.nombre_marca,"
                + "pr.precio_producto,"
                + "pr.existencia_producto,"
                + "pr.iva_producto,"
                + "pr.estado_producto,"
                + "pr.usuario_insercion,"
                + "pr.usuario_modificacion,"
                + "pr.fecha_insercion,"
                + "pr.fecha_modificacion"
                + " from productos pr "
                + "JOIN clasificacion cl ON pr.cod_clasificacion=cl.cod_clasificacion "
                + "JOIN marcas ma ON pr.cod_marca=ma.cod_marca "
                + " where cod_producto =" + codigo + ";";
        String [] datos = bd.obtenerRegistro(sql);
        
        this.txtCodigo.setText(datos[0]);
        this.txtNombre.setText(datos[1]);
        if(datos[2]!=null){
            bd.mostrarImg("productos", "img1_producto", "cod_producto = " + codigo, img1);         
        }
        if(datos[3]!=null){
            bd.mostrarImg("productos", "img2_producto", "cod_producto = " + codigo, img2);
        }
        if(!bd.seleccionarItemComboBD(cboClasificacion, Integer.parseInt(datos[4])))
            this.setClasificacion(Integer.parseInt(datos[4]), datos[5]);
        if(!bd.seleccionarItemComboBD(cboMarca, Integer.parseInt(datos[6])))
            this.setMarca(Integer.parseInt(datos[4]), datos[7]);
        this.txtPrecio.setText(datos[8]);
        this.txtStock.setText(datos[9]);
        bd.seleccionarItemCombo(cboIva, datos[10]);
        bd.seleccionarItemCombo(cboEstado, datos[11]);
        this.lblUsuarioCreacion.setText(datos[12]);
        this.lblUsuarioModificacion.setText(datos[13]);
        this.lblFechaCreacion.setText(datos[14]);
        this.lblFechaModificacion.setText(datos[15]);
        this.txtNombre.requestFocus();
    }
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        cboClasificacion = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        cboMarca = new javax.swing.JComboBox<>();
        cboIva = new javax.swing.JComboBox<>();
        txtPrecio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        btnNuevaImg2 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblUsuarioCreacion = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUsuarioModificacion = new javax.swing.JLabel();
        lblFechaModificacion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnGuardar1 = new javax.swing.JButton();
        img2 = new javax.swing.JLabel();
        img1 = new javax.swing.JLabel();
        btnEliminarImg2 = new javax.swing.JButton();
        btnBuscarMarca = new javax.swing.JButton();
        btnBuscarClasificacion = new javax.swing.JButton();
        btnNuevaImg1 = new javax.swing.JButton();
        btnEliminarImg1 = new javax.swing.JButton();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 120, 30));

        cboClasificacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 150, 30));

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 400, 30));

        cboMarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 150, 30));

        cboIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXENTAS", "5%", "10%" }));
        cboIva.setSelectedIndex(2);
        cboIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 160, 30));

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 190, 30));

        txtStock.setEditable(false);
        txtStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 160, 30));

        btnNuevaImg2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevaImg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevaImg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaImg2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaImg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 40, 40));

        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 90, 40));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 130, 16));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 130, 16));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Clasificacion");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 80, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Codigo");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Precio");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 50, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Marca");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("IVA");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 40, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Stock");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 40, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Producto");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, 30));

        btnGuardar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 90, 40));

        img2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(img2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 150, 150));

        img1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(img1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 150, 150));

        btnEliminarImg2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminarImg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarImg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImg2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarImg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, 40, 40));

        btnBuscarMarca.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnBuscarMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarMarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 30, 30));

        btnBuscarClasificacion.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnBuscarClasificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscarMin.png"))); // NOI18N
        btnBuscarClasificacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscarClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClasificacionActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 30, 30));

        btnNuevaImg1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevaImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevaImg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaImg1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 40, 40));

        btnEliminarImg1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminarImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarImg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImg1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, 40, 40));

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        cboEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 160, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Estado");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 40, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaImg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaImg2ActionPerformed
        this.img2cargado = this.insertarImagen(this.img2, "2");
    }//GEN-LAST:event_btnNuevaImg2ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        String codigo = this.txtCodigo.getText();
        String nombre = this.txtNombre.getText();
        int codClasificacion = bd.obtenerCodCombo(this.cboClasificacion);
        int codMarca = bd.obtenerCodCombo(this.cboMarca);
        String precio = this.txtPrecio.getText();
        String iva = this.cboIva.getSelectedItem().toString();
        if(!nombre.isEmpty()){
            if(!precio.isEmpty()){
                String valores = "nombre_producto='" + nombre
                + "',cod_clasificacion=" + codClasificacion
                + ",cod_marca=" + codMarca
                + ",precio_producto=" + precio
                + ",iva_producto='" + iva
                + "',usuario_modificacion='" + usuarioLogeado
                + "',fecha_modificacion=NOW()";
                bd.modificarRegistro("productos", valores, "cod_producto=" + codigo);
                if(img1cargado){
                    this.guardarImagenes(rutaImg1, "img1_producto", Integer.parseInt(codigo));
                }else{
                    if(img1Borrado){
                        bd.modificarRegistro("productos", "img1_producto=null", "cod_producto=" +codigo);
                    }
                }
                if(img2cargado){
                    this.guardarImagenes(rutaImg2, "img2_producto", Integer.parseInt(codigo));
                }else{
                    if(img2Borrado){
                        bd.modificarRegistro("productos", "img2_producto=null", "cod_producto=" +codigo);                            
                    }
                }
                frmProductos.actualizarGrilla();
                this.dispose();
                JOptionPane.showMessageDialog(null, "Producto Actualizado", "Registro Actualizado", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnEliminarImg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImg2ActionPerformed
        this.img2.setIcon(null);
        this.img2Borrado = true;
    }//GEN-LAST:event_btnEliminarImg2ActionPerformed

    private void btnBuscarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMarcaActionPerformed
        String tabla = "marcas";
        String [] campos = {"cod_marca", "nombre_marca"};
        String condicion = " where estado_marca = 'ACTIVO'";
        String [] cabeceras = {"Codigo","Nombre"};
        int [] ancho = {200, 300};
        JDBuscador jb = new JDBuscador(this, true, null, this, tabla, campos, condicion, cabeceras, ancho);
        jb.setVisible(true);
    }//GEN-LAST:event_btnBuscarMarcaActionPerformed

    private void btnBuscarClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClasificacionActionPerformed
        String tabla = "clasificacion";
        String [] campos = {"cod_clasificacion", "nombre_clasificacion"};
        String condicion = " WHERE estado_clasificacion = 'ACTIVO'";
        String [] cabeceras = {"Codigo","Nombre"};
        int [] ancho = {200, 300};
        JDBuscador jb = new JDBuscador(this, true, null, this, tabla, campos, condicion, cabeceras, ancho);
        jb.setVisible(true);
    }//GEN-LAST:event_btnBuscarClasificacionActionPerformed

    private void btnNuevaImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaImg1ActionPerformed
        this.img1cargado = this.insertarImagen(this.img1, "1");
    }//GEN-LAST:event_btnNuevaImg1ActionPerformed

    private void btnEliminarImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImg1ActionPerformed
        this.img1.setIcon(null);
        this.img1Borrado = true;
    }//GEN-LAST:event_btnEliminarImg1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDModificarProducto dialog = new JDModificarProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscarClasificacion;
    private javax.swing.JButton btnBuscarMarca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarImg1;
    private javax.swing.JButton btnEliminarImg2;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnNuevaImg1;
    private javax.swing.JButton btnNuevaImg2;
    private javax.swing.JComboBox<String> cboClasificacion;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboIva;
    private javax.swing.JComboBox<String> cboMarca;
    private javax.swing.JLabel img1;
    private javax.swing.JLabel img2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
