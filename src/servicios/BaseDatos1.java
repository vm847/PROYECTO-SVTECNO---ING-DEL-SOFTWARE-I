package servicios;

import com.toedter.calendar.JDateChooser;
import java.awt.Image;
import java.sql.Blob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class BaseDatos1 {
    private static String host = "localhost";
    private static String usuBD = "root";
    private static String contraUsuBD = "1234";
    private static String baseDatos="bdsvtecno";
    public static Connection conexion = null;
    Connection conn = null;
    private static Statement sentencia;
    public String directorioReportes = "C:\\SVTecno\\src\\reportes\\";
    
    public Connection conect(){
        estaConectado();
        return conexion;
    }
    
    public BaseDatos1(){
        estaConectado();
    }
    
    public String getBD(){
        return baseDatos;
    }
    public String getHost(){
        return host;
    }
    public String getUsuBD(){
        return usuBD;
    }
    public String getContrBD(){
        return contraUsuBD;
    }
    
    public Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection("jdbc:mariadb://"+host+":3307/"+baseDatos,usuBD,contraUsuBD);
            }
            return conexion;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexion \n"+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        
    }
    public void cierraConexion(){
        try {
            if(!conexion.isClosed() && conexion != null){
                conexion.close();
            }
        } catch (Exception e) {
        }
    }
    
    public boolean estaConectado(){
        if (conexion != null)
            return true;

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conexion = DriverManager.getConnection("jdbc:mariadb://"+host+":3307/"+baseDatos,usuBD,contraUsuBD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Conexion fallida");
            return false;
        }
        return true;
    }
    public static String reemplazarBarraInvertida(String cadena) {
        return cadena.replace("\\", "/");
    }
    public void iniciarTransaccion(){
        try {
            conexion.setAutoCommit(false);   
            System.out.println("START TRANSACTION;");
        } catch (Exception e) {
        }
    }
    public void cancelarTransaccion(boolean cancelar){
        if(!cancelar){
            try {
                conexion.rollback();  
                System.out.println("ROLBACK;");
            } catch (Exception e) {
            }
        }        
    }
    public void finalizarTransaccin(){
        try {
            conexion.commit();
            System.out.println("COMMIT;");
        } catch (Exception e) {
        }        
    }
    public boolean insertarRegistro(String tabla,  String valores){
        int resultado;
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            System.out.println("insert into "+tabla+" values ("+valores+")");
            resultado = s.executeUpdate("insert into "+tabla+" values ("+valores+")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error al insertar \n"+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean insertarRegistro(String tabla, String campos, String valores){
        int resultado;
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "insert into "+tabla+" ("+campos+") values ("+valores+")";
            System.out.println(sql);
            resultado = s.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"Base de Datos actualizada");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error al insertar \n"+ e.getMessage() , "Atencion C001",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean modificarRegistro(String tabla, String campos, String criterio){
        int resultado;
        try {
            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            
            // Se realiza la consulta.
           System.out.println("update "+tabla+" set "+campos+" where " +criterio);
            resultado = s.executeUpdate("update "+tabla+" set "+campos+" where " +criterio);
        } catch (SQLException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n"+e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    public boolean borrarRegistro(String tabla, String condicion){
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            String sql = "delete from "+tabla+" where "+condicion;
            System.out.println("borrar --  " + sql);
            s.executeUpdate(sql);
                
        } catch (SQLException e) {
            if (e.getErrorCode()==1451){
                JOptionPane.showMessageDialog(null, "El registro esta relacionado con otros registros\nno podrá borrarlo." ,
                        "Atencion", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio Un error" + e.getMessage(), "Atencion",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            return false;
        }
        return true;
    }
    public String obtenerCodMaximoRegistro(String tabla, String campoCodigo){
        ResultSet rs = null;
        String cod = null; // Inicializa el array para un solo registro
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT IFNULL(MAX( " + campoCodigo + ")+1, 1) AS codigo_maximo from " + tabla);

            // Si hay resultados
            if (rs.next()) {
                cod = rs.getString(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Atención",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("select " + campoCodigo + " from " + tabla);
        }
        return cod;
    }
    public int obtenerCodigo(String sql){
        ResultSet rs = null;
        int cod = 0;
        try{
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);
            rs.next();
            cod = rs.getInt(1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocurrio Un error"+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sql);
        }
        
        return cod;
    }
    public String[] obtenerRegistro(String sql) {
        ResultSet rs = null;
        String[] datos = null; // Inicializa el array para un solo registro
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);

            // Si hay resultados
            if (rs.next()) {
                // Inicializa el array con el número de columnas
                int columnCount = rs.getMetaData().getColumnCount();
                datos = new String[columnCount];

                // Rellenar el array con los valores de las columnas
                for (int i = 0; i < columnCount; i++) {
                    datos[i] = rs.getString(i + 1); // Obtiene el valor de cada columna
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Atención",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sql);
        }
        return datos;
    }
    public String[][] obtenerRegistros(String sql) {
        ResultSet rs = null;
        String[][] datos = null;

        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);

            if (rs.next()) {
                // Obtiene el número de columnas
                int columnCount = rs.getMetaData().getColumnCount();

                // Mueve el cursor al primer registro
                rs.beforeFirst();

                // Cuenta el número de filas
                int rowCount = 0;
                ResultSet tmpRs = rs;
                while (tmpRs.next()) {
                    rowCount++;
                }
                // Regresa al primer registro
                rs.beforeFirst();

                // Inicializa la matriz con el número de filas y columnas
                datos = new String[rowCount][columnCount];
                // Rellenar la matriz con los valores de las columnas
                int fila = 0;
                while (rs.next()) {
                    for (int columna = 0; columna < columnCount; columna++) {
                        datos[fila][columna] = rs.getString(columna + 1); // Obtiene el valor de cada columna
                    }
                    fila++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Atención", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sql);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar ResultSet: " + e.getMessage());
            }
        }
        return datos;
    }
    public ResultSet consultar(String sql) {
        ResultSet rs = null;
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio Un error"+ e.getMessage() , "Atencion",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sql);
        }
        return rs;
    }
    public void VaciarCombo(JComboBox combo){
        combo.removeAllItems();
        ArrayList<Combo> camposCombo;
        camposCombo = new ArrayList();
                 //Recorrer los registros
        camposCombo.add(new Combo(0, "[Seleccione]"));

    }
    public void llenarCombo(JComboBox combo, String campos, String tabla){
         ResultSet rsC = null;
         try{
             sentencia = (Statement)conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             rsC = sentencia.executeQuery("select " + campos + " from " + tabla);
             ArrayList<Combo> camposCombo;
             camposCombo = new ArrayList();
             while (rsC.next()) {
                 camposCombo.add(new Combo(rsC.getInt(1), rsC.getString(2)));
             }
             for (Combo nombre: camposCombo){
                 combo.addItem(nombre);
             }
         }catch(SQLException e) {
             JOptionPane.showMessageDialog(null, "Error al llenar combo\n" + e.getMessage()  , "Llenar Combo - "  + combo.getName(), JOptionPane.ERROR_MESSAGE);
         }

     }
    public void llenarComboHasta20(JComboBox combo, String campos, String tabla){
         ResultSet rsC = null;
         try{
             sentencia = (Statement)conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             rsC = sentencia.executeQuery("select " + campos + " from " + tabla);
             ArrayList<Combo> camposCombo;
             camposCombo = new ArrayList();
             int cont = 0;
             while (rsC.next() && cont <= 10) {
                 camposCombo.add(new Combo(rsC.getInt(1), rsC.getString(2)));
                 cont++;
             }
             for (Combo nombre: camposCombo){
                 combo.addItem(nombre);
             }
         }catch(SQLException e) {
             JOptionPane.showMessageDialog(null, "Error al llenar combo\n" + e.getMessage()  , "Llenar Combo - "  + combo.getName(), JOptionPane.ERROR_MESSAGE);
         }

    }
    public void agregarItemCombo(JComboBox combo, int codigo, String nombre) {
        combo.addItem(new Combo(codigo, nombre));
        seleccionarItemComboBD(combo, codigo);
    }
    public int obtenerCodCombo(JComboBox miCombo){
        Combo cbo=(Combo) miCombo.getSelectedItem();
        return cbo.getCodigo();
    }
    public String obtenerNombreCombo(JComboBox miCombo){
        Combo cbo=(Combo) miCombo.getSelectedItem();
        return cbo.getNombre();
    }
    
    public String obtenerFechaHoraActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return fechaHoraActual.format(formato);
    }
    public String obtenerFechaActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaHoraActual.format(formato);
    }
    public boolean seleccionarItemComboBD(JComboBox miCombo, int codigo) {
        // Recorre el modelo del JComboBox para buscar el ítem
        for (int i = 0; i < miCombo.getItemCount(); i++) {
            // Obtener el objeto Combo actual del JComboBox
            Combo comboItem = (Combo) miCombo.getItemAt(i);
            if (comboItem.getCodigo() == codigo) {
                miCombo.setSelectedIndex(i); // Selecciona el ítem si coincide
                return true;
            }
        }
        return false;
    }
    public void seleccionarItemCombo(JComboBox miCombo, String item) {
        // Recorre el modelo del JComboBox para buscar el ítem
        for (int i = 0; i < miCombo.getItemCount(); i++) {
            if (miCombo.getItemAt(i).equals(item)) {
                miCombo.setSelectedIndex(i); // Selecciona el ítem si coincide
                break;
            }
        }
    }
    public String formatoFechaBDaJava(String fecha) {
        try {
            // Definir el formato de entrada
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
            // Analizar la fecha en el formato de entrada
            Date fechaParseada = formatoEntrada.parse(fecha);

            // Definir el formato de salida
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            // Devolver la fecha en el nuevo formato
            return formatoSalida.format(fechaParseada);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejar error, podría devolverse un mensaje o valor de error
        }
    }
    public String formatoFechaJavaBD(String fecha) {
        try {
            // Definir el formato de entrada
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
            // Analizar la fecha en el formato de entrada
            Date fechaParseada = formatoEntrada.parse(fecha);

            // Definir el formato de salida
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
            // Devolver la fecha en el nuevo formato
            return formatoSalida.format(fechaParseada);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejar error, podría devolverse un mensaje o valor de error
        }
    }
    public void mostrarFechaJDateChooser(JDateChooser dateChooser, String fecha) {
        // Formato de entrada (yyyy-MM-dd)
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");

        // Formato de salida (dd-MM-yyyy)
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Convertir la cadena de fecha a un objeto Date
            Date date = formatoEntrada.parse(fecha);

            // Establecer el valor en el JDateChooser (solo la parte de la fecha)
            dateChooser.setDate(date);

            // Actualizar el formato de visualización del JDateChooser
            dateChooser.setDateFormatString(formatoSalida.toPattern());

        } catch (ParseException e) {
            // Manejo de excepción si el formato de fecha no es correcto
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }
    public boolean compararFecha(String fecha) {
        // Formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            // Parsear fecha
            Date fechaParseada = sdf.parse(fecha);

            // Fecha actual
            Date fechaActual = new Date();

            // Calendario
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaActual);

            // Restar una semana
            calendario.add(Calendar.DAY_OF_YEAR, -7);
            Date fechaSemanaAtras = calendario.getTime();

            // Comparar fechas
            if (fechaParseada.before(fechaSemanaAtras)) {
                return false; // Más de una semana de antigüedad
            } else {
                return true; // Menos de una semana de antigüedad
            }
        } catch (ParseException e) {
            System.out.println("Error parseando fecha: " + e.getMessage());
            return false;
        }
    }
    public String formatoFecha(JDateChooser fecha){ 
        Date fechaSeleccionada = fecha.getDate();
        if (fechaSeleccionada != null) { // Verificar si se seleccionó una fecha
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fechaParaBaseDatos = formatoFecha.format(fechaSeleccionada);
            return fechaParaBaseDatos;
        } else {
            System.out.println("No se ha seleccionado ninguna fecha.");
            return null;
        }
    }
    
    
    public void llamarReporte(String url, String [] nomParamet, String [] paramet){
        try {
            conn = obtenerConexion();
            JasperReport reporte = null; 
            
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(url));
            Map<String,Object> parametros = new java.util.HashMap<>();
            
            for (int x = 0; x < nomParamet.length; x++) {
                parametros.put(nomParamet[x], paramet[x]);
            }
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    public void llamarReporteConInt(String url, String [] nomParamet, String [] paramet, String [] nomPa, int [] parametr){
        try {
            conn = obtenerConexion();
            JasperReport reporte = null; 
            
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(url));
            Map<String,Object> parametros = new java.util.HashMap<>();
            
            for (int x = 0; x < nomParamet.length; x++) {
                parametros.put(nomParamet[x], paramet[x]);
            }
            for (int x = 0; x < nomPa.length; x++) {
                parametros.put(nomPa[x], parametr[x]);
            }
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    public void llamarReporteConFechas(String url, Map<String,Object> parametros){
        try {
            conn = obtenerConexion();
            JasperReport reporte = null;

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(url));
            
            
            JasperPrint jp = JasperFillManager.fillReport(reporte,parametros, conn);
            JasperViewer view = new JasperViewer(jp, false);
             
            view.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar informe " +e.getMessage());   
        }
    }
    
    public boolean verificarDuplicidadString(String tabla, String campo, String valor, String campoCodigo, String codigo){
        String sql = "SELECT " + campo + " FROM "
                + tabla + " WHERE " + campo +" = '"+ valor + "' AND " 
                + campoCodigo + " != " + codigo + ";";
        System.out.println(sql);
        String [] dato = this.obtenerRegistro(sql);
        System.out.println("dato antes" + dato);
        if(dato != null){
            System.out.println("dato despues" + dato[0]);
            return true;
        }
        return false;
    }
    public String verificarDuplicidadNumComprobante(String numComprobante, String codProveedor){
        String [] dato = this.obtenerRegistro("SELECT cod_compra_cabecera FROM compras_cabecera"
                + " WHERE num_comprobante_compra='" + numComprobante + "' AND cod_proveedor=" + codProveedor + " AND estado_compra='ACTIVO'" );
        if(dato != null){
            return dato[0];
        }
        return "-1";
    }
    public boolean verificarDuplicidadInt(String tabla, String campo, int valor, String campoCodigo, String codigo){
        String [] dato = this.obtenerRegistro("SELECT " + campo + " FROM "
                + tabla + " WHERE " + campo +" = "+ valor + " AND " 
                + campoCodigo + " != " + codigo + ";");
        System.out.println("dato antes" + dato);
        if(dato != null){
            System.out.println("dato despues " + dato[0]);
            return true;
        }
        return false;
    }
    public void mostrarImg(String tabla, String campo, String condicion, JLabel contenedor){
        ResultSet rs = null;
        try {
            Statement s = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select " + campo + " from " + tabla + " where " + condicion;
            rs = s.executeQuery(sql);

            if (rs.next()) {
                System.out.println("mostrar imagen   ");
                Blob blob = rs.getBlob(campo);
                byte[] data = blob.getBytes(1,(int) blob.length());
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new ByteArrayInputStream(data));
                } catch (Exception e) {
                }
                
                ImageIcon icono = new ImageIcon(img);
                ImageIcon miImg = new ImageIcon(icono.getImage().getScaledInstance(contenedor.getWidth(), contenedor.getHeight(), Image.SCALE_SMOOTH));
                contenedor.setIcon(miImg);
            }
        } catch (Exception e) {
        }
    }
    public static String agregarSeparadorMiles(String numero) {
        try {
            // Convertir la cadena a un número
            double valorNumerico = Double.parseDouble(numero);
            // Crear un formato de número con separador de miles
            NumberFormat formato = NumberFormat.getInstance(Locale.US);
            return formato.format(valorNumerico);
        } catch (NumberFormatException e) {
            // Manejo de error si el string no es un número válido
            return "Error: El valor ingresado no es un número válido.";
        }
    }
}

// CLASE PARA MANIPULAR COMBO
class Combo {
    
    private int codigo;
    private String nombre;
    
    public Combo(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String toString(){
        return this.getNombre();
    }
    
    public int toInt(){
        return this.getCodigo();
    }
    
}