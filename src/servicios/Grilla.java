

package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import servicios.BaseDatos1;

class DecimalRenderer extends DefaultTableCellRenderer {
    DecimalFormat formatter;
    DecimalRenderer(String pattern){
        this(new DecimalFormat(pattern));
    }
    DecimalRenderer(DecimalFormat formatter){
        this.formatter = formatter;
        setHorizontalAlignment (JLabel.RIGHT);
    }
}
public class Grilla {
BaseDatos1 bd =new BaseDatos1();

    

    public void configurarModelo(JTable nombreGrilla, String[] columnas, int[] ancho) {
        DefaultTableModel dm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición de celdas
            }
        };
        // Establecer columnas
        dm.setColumnIdentifiers(columnas);

        // Deshabilitar edición de celdas
        dm.setColumnCount(columnas.length);
        dm.setRowCount(0);


        // No permitir reordenar columnas
        nombreGrilla.getTableHeader().setReorderingAllowed(false);

        // Establecer modelo en la grilla
        nombreGrilla.setModel(dm);

        // Establecer ancho de columnas
        for (int cont = 0; cont < columnas.length; cont++) {
            nombreGrilla.getColumnModel().getColumn(cont).setPreferredWidth(ancho[cont]);
        }
    }

    public void alinearCabecera(JTable grilla,int alineacion){
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) grilla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(alineacion);
    }
    public void alinear(JTable grilla, String columna){
        final DecimalFormat formato = new DecimalFormat("###,##0.00");
        grilla.getColumn(columna).setCellRenderer(new DecimalRenderer (formato));

    }
    public void alinearCentrar(JTable grid, int col){
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        grid.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }
    public void alinearDerecha(JTable grid, int col){
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        grid.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }
    
    public void cargarGrilla(JTable nombregrilla, String tabla, String[] campos,String condicion){
        String sql="Select ";
        DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
        this.vaciar(nombregrilla);
        for (int cont=0;cont<campos.length;cont++){
            if (cont <1){
                sql=sql+campos[cont];
            }else{
                sql=sql+ ", " +campos[cont];
            }
        }
        try{
            sql=sql+" from "+tabla+ " " + condicion;
//            System.out.println(sql);
            ResultSet rs =bd.consultar(sql);
            String [] valores= new String[campos.length];
            int fila=0;
            while(rs.next()){
                for (int cont2=0;cont2<campos.length;cont2++){
                    valores[cont2]=rs.getString(cont2+1);
                }
                
                modelo.addRow(new Object[]{});
                for (int col=0; col < campos.length;col++)
                    if(valores[col]!=null){
                        modelo.setValueAt(valores[col],fila,col);
                    }
                fila =fila+1;
            }
            nombregrilla.setModel(modelo);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Error al intentar cargar la grilla"+ex.toString(), "Grilla",JOptionPane.INFORMATION_MESSAGE);
        }  
}
    
    public void agregarFilaGrilla(JTable nombregrilla, String [] fila){
        DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
        modelo.addRow(fila);
    }
    
    public void cargarGrilla2(JTable nombregrilla, String tabla, String[] campos,String condicion, String join){
        String sql="Select ";
        DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
        this.vaciar(nombregrilla);
        for (int cont=0;cont<campos.length;cont++){
            if (cont <1){
                sql=sql+campos[cont];
            }else{
                sql=sql+ ", " +campos[cont];
            }
        }
        try{
            sql=sql+" from "+tabla + join + condicion;
//            JOptionPane.showMessageDialog(null, sql);
            ResultSet rs =bd.consultar(sql);
            String [] valores= new String[campos.length];
            int fila=0;
            while(rs.next()){
                for (int cont2=0;cont2<campos.length;cont2++){
                    valores[cont2]=rs.getString(cont2+1);
                }
                
                modelo.addRow(new Object[]{});
                for (int col=0; col < campos.length;col++)
                    if(valores[col]!=null){
                        modelo.setValueAt(valores[col],fila,col);
                    }
                fila =fila+1;
            }
            nombregrilla.setModel(modelo);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Error al intentar cargar la grilla"+ex.toString(), "Grilla",JOptionPane.INFORMATION_MESSAGE);
        }  
}

    private boolean esNumero(String cadena){
        try {
            Integer.valueOf(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
      }

    public void setSeparadorMiles(JTable nombreGrilla,int columna){
         final DecimalFormat formato = new DecimalFormat("###,##0");        
         try {
             for(int i=0;i<nombreGrilla.getRowCount();i++){
                 if(nombreGrilla.getValueAt(i, columna)!=null){
                 double dato=Integer.parseInt(nombreGrilla.getValueAt(i, columna).toString());                 
//                 JOptionPane.showMessageDialog(null, dato);
                 nombreGrilla.setValueAt(formato.format(dato), i, columna);
                 }
             }
         } catch (NumberFormatException nfe){
        }
    }
    public void ocultarColumna(JTable nombreGrilla,int col){
        nombreGrilla.getColumnModel().getColumn(col).setMaxWidth(0);
        nombreGrilla.getColumnModel().getColumn(col).setMinWidth(0);
        nombreGrilla.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(0);
        nombreGrilla.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }


public void filtrarGrilla(JTable nombregrilla, String texto, int columna){
    DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
    TableRowSorter gridFiltrado = new TableRowSorter(modelo);
    gridFiltrado.setRowFilter(RowFilter.regexFilter(texto,columna));
    nombregrilla.setRowSorter(gridFiltrado);
}
public void insertarFila(JTable nombregrilla, Object[] valores){
    DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
    modelo.addRow(valores);
}
public void borrarFila(JTable nombreGrilla){
    int filaAborrar=nombreGrilla.getSelectedRow();
    if(filaAborrar>-1){
        DefaultTableModel modelo=(DefaultTableModel)nombreGrilla.getModel();
        modelo.removeRow(filaAborrar);
    }else{
        JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para borrar","Grilla",JOptionPane.INFORMATION_MESSAGE);
    }
}
public void filtrarGrilla(JTable nombreGrilla, String texto){
        DefaultTableModel modelo = (DefaultTableModel) nombreGrilla.getModel();
        TableRowSorter gridFiltrado = new TableRowSorter(modelo);
        gridFiltrado.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0,1,2,3,4,5,6,7,8,9,10));
        nombreGrilla.setRowSorter(gridFiltrado);
}

public void vaciar(JTable nombregrilla){
    DefaultTableModel modelo = (DefaultTableModel) nombregrilla.getModel();
    nombregrilla.selectAll();
    int[] filas = nombregrilla.getSelectedRows();
    for (int i= (filas.length - 1); i >=0; --i)
        modelo.removeRow(i);
}

    public void cerrarObjeto(){
        bd.cierraConexion();
    }
    
    public void formatoFechaGrilla(JTable nombreGrilla, int columna){
        DefaultTableModel modelo=(DefaultTableModel)nombreGrilla.getModel();
        int filas = nombreGrilla.getRowCount();
        
        for (int x = 0; x < filas; x++) {
            Object fechaSinFormato = nombreGrilla.getValueAt(x, columna);
            if(fechaSinFormato != null){
                String fechaFormateada = formatearFecha(fechaSinFormato.toString());
                modelo.setValueAt(fechaFormateada, x, columna);
            }            
        }
        
    }
    public String formatearFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaDate);
        } catch (ParseException e) {
            return "-1";
        }
    }
    public String formatearFechaHora(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaDate);
        } catch (ParseException e) {
            return "-1";
        }
    }
}

