package productos;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import servicios.BaseDatos1;
import ventanas.principal.FrmAjustes;

/**
 *
 * @author Asus
 */
public class JDNuevoProducto extends javax.swing.JDialog {
    BaseDatos1 bd = new BaseDatos1();
    FrmAjustes aj = new FrmAjustes();
    Connection conn = null;
    private boolean img1cargado = false;
    private boolean img2cargado = false;
    private String rutaImg1 = "";
    private String rutaImg2 = "";
    String usuarioLogeado;
    private FrmProductos frmProductos;

    public JDNuevoProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.restricciones();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.txtCodigo.setText(bd.obtenerCodMaximoRegistro("productos", "cod_producto"));
        bd.llenarComboHasta20(this.cboClasificacion, "cod_clasificacion, nombre_clasificacion", "clasificacion WHERE estado_clasificacion = 'ACTIVO'");
        bd.llenarComboHasta20(this.cboMarca, "cod_marca, nombre_marca", "marcas where estado_marca = 'ACTIVO'");
    }

    public JDNuevoProducto(java.awt.Frame parent, boolean modal, FrmProductos frmProductos, String usuLogeado) {
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
        this.txtNombre.requestFocus();
    }


    private void restricciones(){
        aj.f1ActionPerformed(cboClasificacion, btnBuscarClasificacion);
        aj.f1ActionPerformed(cboMarca, btnBuscarMarca);
        aj.soloMayusculas(txtNombre, 50);
        aj.soloNumeros(txtPrecio, 10);
        aj.soloNumeros(txtStock, 10);
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
            }else{
                this.rutaImg2 = rutaImg;
            }
            this.mostrarImg(contenedor, rutaImg);
            return true;
        }
        return false;
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

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
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
        jLabel16 = new javax.swing.JLabel();
        txtCodBarras = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEditable(false);
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setToolTipText("");
        txtCodigo.setBorder(null);
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 10, 0));

        cboClasificacion.setToolTipText("CLASIFICACION (F1)");
        cboClasificacion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 170, 30));

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setToolTipText("DESCRIPCION DEL PRODUCTO");
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 400, 30));

        cboMarca.setToolTipText("MARCA (F1)");
        cboMarca.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 170, 30));

        cboIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXENTAS", "5%", "10%" }));
        cboIva.setSelectedIndex(2);
        cboIva.setToolTipText("TIPO DE IVA");
        cboIva.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(cboIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 170, 30));

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setToolTipText("PRECIO");
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 170, 30));

        txtStock.setEditable(false);
        txtStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStock.setToolTipText("STOCK");
        txtStock.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtStock.setFocusable(false);
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 170, 30));

        btnNuevaImg2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevaImg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevaImg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaImg2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaImg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 180, 40, 40));

        btnCancelar.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cerrar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 90, 40));

        jLabel1.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insersion");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 130, 16));

        lblUsuarioCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 130, 16));

        lblFechaCreacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaCreacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 130, 16));

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Modificacion");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 130, 16));

        lblUsuarioModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblUsuarioModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUsuarioModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 130, 16));

        lblFechaModificacion.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        lblFechaModificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblFechaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 130, 16));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Clasificacion");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 50, 30));

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
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 40, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Stock");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 40, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Descripcion");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, 30));

        btnGuardar1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, 90, 40));

        img2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(img2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 150, 150));

        img1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(img1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 150, 150));

        btnEliminarImg2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminarImg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarImg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImg2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarImg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 40, 40));

        btnBuscarMarca.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnBuscarMarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, -1, -1));

        btnBuscarClasificacion.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnBuscarClasificacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscarClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClasificacionActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, -1, -1));

        btnNuevaImg1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnNuevaImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btnNuevaImg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaImg1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 40, 40));

        btnEliminarImg1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnEliminarImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btnEliminarImg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImg1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 40, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Cod Barras");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        txtCodBarras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodBarras.setToolTipText("CODIGO DE BARRAS");
        txtCodBarras.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtCodBarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 170, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Comentario");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 70, 30));

        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        txtComentario.setToolTipText("COMENTARIOS");
        jScrollPane2.setViewportView(txtComentario);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 400, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        String codBarras = this.txtCodBarras.getText();
        String nombre = this.txtNombre.getText();
        int codClasificacion = bd.obtenerCodCombo(this.cboClasificacion);
        int codMarca = bd.obtenerCodCombo(this.cboMarca);
        String precio = this.txtPrecio.getText();
        String comentario = this.txtComentario.getText();
        String stock = "0";
        String iva = this.cboIva.getSelectedItem().toString();
        if(!nombre.isEmpty()){
            if(!precio.isEmpty()){
                String campos = "cod_producto,"
                        + "cod_barras,"
                        + "nombre_producto,"
                        + "cod_clasificacion,"
                        + "cod_marca,"
                        + "precio_producto,"
                        + "existencia_producto,"
                        + "iva_producto,"
                        + "coment_producto,"
                        + "usuario_insercion,"
                        + "fecha_insercion";
                String valores = codigo
                        +",'" + codBarras
                        +"','" + nombre
                        +"'," + codClasificacion
                        + "," + codMarca
                        + "," + precio
                        + "," + stock
                        + ",'" + iva
                        + "','" + comentario
                        + "','" + usuarioLogeado
                        + "',NOW()";
                bd.insertarRegistro("productos", campos, valores);
                if(img1cargado){
                    this.guardarImagenes(rutaImg1, "img1_producto", Integer.parseInt(codigo));
                }
                if(img1cargado){
                    this.guardarImagenes(rutaImg2, "img2_producto", Integer.parseInt(codigo));
                }
                frmProductos.actualizarGrilla();
                this.dispose();
                JOptionPane.showMessageDialog(null, "Nuevo Producto Guardado", "Registro Guardado", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "INGRESE EL PRECIO DEL PRODUCTO");
            }            
        }else{
            JOptionPane.showMessageDialog(null, "INGRESE EL NOMBRE DEL PRODUCTO");
        }

    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnEliminarImg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImg2ActionPerformed
        this.img2.setIcon(null);
        this.img2cargado = false;
    }//GEN-LAST:event_btnEliminarImg2ActionPerformed

    private void btnBuscarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMarcaActionPerformed
        String tabla = "marcas";
        String [] campos = {"cod_marca", "nombre_marca"};
        String condicion = " where estado_marca = 'ACTIVO'";
        String [] cabeceras = {"Codigo","Nombre"};
        int [] ancho = {200, 300};
        JDBuscador jb = new JDBuscador(this, true, this, null, tabla, campos, condicion, cabeceras, ancho);
        jb.setVisible(true);
    }//GEN-LAST:event_btnBuscarMarcaActionPerformed

    private void btnBuscarClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClasificacionActionPerformed
        String tabla = "clasificacion";
        String [] campos = {"cod_clasificacion", "nombre_clasificacion"};
        String condicion = " WHERE estado_clasificacion = 'ACTIVO'";
        String [] cabeceras = {"Codigo","Nombre"};
        int [] ancho = {200, 300};
        JDBuscador jb = new JDBuscador(this, true, this, null, tabla, campos, condicion, cabeceras, ancho);
        jb.setVisible(true);
    }//GEN-LAST:event_btnBuscarClasificacionActionPerformed

    private void btnNuevaImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaImg1ActionPerformed
        this.img1cargado = this.insertarImagen(this.img1, "1");
    }//GEN-LAST:event_btnNuevaImg1ActionPerformed

    private void btnEliminarImg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImg1ActionPerformed
        this.img1.setIcon(null);
        this.img1cargado = false;
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
            java.util.logging.Logger.getLogger(JDNuevoProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDNuevoProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDNuevoProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDNuevoProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDNuevoProducto dialog = new JDNuevoProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblFechaModificacion;
    private javax.swing.JLabel lblUsuarioCreacion;
    private javax.swing.JLabel lblUsuarioModificacion;
    private javax.swing.JTextField txtCodBarras;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables

}
