package servicios;

import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class Fechas {
    public boolean verificarFormatoFecha(JDateChooser fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd MM yyyy");
        formato.setLenient(false); // no permitir fechas inválidas

        try {
            Date fechaSeleccionada = fecha.getDate();
            String fechaString = formato.format(fechaSeleccionada);
            formato.parse(fechaString); // verificar si la fecha es válida
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public boolean compararFechas(String fechaDesde, String fechaHasta) {
        if (fechaDesde != null && fechaHasta != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("comparar fechas fecha desde => " + fechaDesde);
//            System.out.println("comparar fechas fecha hasta => " + fechaHasta);

            try {
                Date fechaDesdeDate = df.parse(fechaDesde);
                Date fechaHastaDate = df.parse(fechaHasta);

                if (fechaDesdeDate.after(fechaHastaDate)) {
                    JOptionPane.showMessageDialog(null, "La fecha de inicio debe ser menor o igual a la fecha final ", "Atención", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error al parsear las fechas", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }
        return false;
    }
    public boolean compararFechas_yyyy_MM_dd(String fechaDesde, String fechaHasta) {
        if (fechaDesde != null && fechaHasta != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("comparar fechas fecha desde => " + fechaDesde);
//            System.out.println("comparar fechas fecha hasta => " + fechaHasta);

            try {
                Date fechaDesdeDate = df.parse(fechaDesde);
                Date fechaHastaDate = df.parse(fechaHasta);

                if (fechaDesdeDate.after(fechaHastaDate)) {
                    return false;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error al parsear las fechas", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }
        return false;
    }
    public boolean compararFechas_dd_MM_yyyy(String fechaDesde, String fechaHasta) {
        if (fechaDesde != null && fechaHasta != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//            System.out.println("comparar fechas fecha desde => " + fechaDesde);
//            System.out.println("comparar fechas fecha hasta => " + fechaHasta);

            try {
                Date fechaDesdeDate = df.parse(fechaDesde);
                Date fechaHastaDate = df.parse(fechaHasta);

                if (fechaDesdeDate.after(fechaHastaDate)) {
                    //JOptionPane.showMessageDialog(null, "La fecha de inicio debe ser menor o igual a la fecha final ", "Atención", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error al parsear las fechas", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }
        return false;
    }
    public String sumarDias(JDateChooser fecha,String dias) {
        Date fechaS = fecha.getDate();
//        System.out.println(fechaS);

        // Calendario
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaS);

        // Sumar días
        calendario.add(Calendar.DAY_OF_YEAR, Integer.parseInt(dias));

        // Fecha resultante
        Date fechaFutura = calendario.getTime();

        // Formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Devolver fecha formateada
        return sdf.format(fechaFutura);
    }
    public String obtenerFechaMenosDias(int dias) {
        Date fechaS = new Date();
        System.out.println(fechaS);

        // Calendario
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaS);

        // Sumar días
        calendario.add(Calendar.DAY_OF_YEAR, dias);

        // Fecha resultante
        Date fechaDespues = calendario.getTime();

        // Formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Devolver fecha formateada
        return sdf.format(fechaDespues);
    }
    public String obtenerFechaMenosDiasFormatoNormal(int dias) {
        Date fechaS = new Date();
        System.out.println(fechaS);

        // Calendario
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaS);

        // Sumar días
        calendario.add(Calendar.DAY_OF_YEAR, dias);

        // Fecha resultante
        Date fechaDespues = calendario.getTime();

        // Formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        // Devolver fecha formateada
        return sdf.format(fechaDespues);
    }
    
    public boolean verificarFecha(JDateChooser dateChooser) {
        Date fechaSeleccionada = dateChooser.getDate();
        Date fechaActual = new Date();

        if (fechaSeleccionada.after(fechaActual)) {
            return false; // La fecha seleccionada es posterior a la fecha actual
        } else {
            return true; // La fecha seleccionada es anterior o igual a la fecha actual
        }
    }

    
    public String calcularVencimientoCredito(String fecha, String plazo, int cuotas) {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); // Establece el formato de fecha
        Date fechaActual;
        try {
            fechaActual = formato.parse(fecha); // Parsea la fecha
//            System.out.println("fecha en calcularVencimiento " + fechaActual);
        } catch (ParseException e) {
            return "Error al parsear la fecha";
        }

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaActual);

        if(plazo.equals("SEMANAL")){
            calendario.add(Calendar.WEEK_OF_YEAR, (1*cuotas));            
        }else{
            if(plazo.equals("QUINCENAL")){
                calendario.add(Calendar.WEEK_OF_YEAR, (2*cuotas));            
            }else{
                if(plazo.equals("MENSUAL")){
                    calendario.add(Calendar.MONTH, (1*cuotas));            
                }else{
                    if(plazo.equals("TRIMESTRAL")){
                        calendario.add(Calendar.MONTH, (3*cuotas));            
                    }else{
                        if(plazo.equals("SEMESTRAL")){
                            calendario.add(Calendar.MONTH, (6*cuotas));
                        }else{
                            calendario.add(Calendar.YEAR, (1*cuotas));
                        }
                    }
                }
            }
        }
        Date fechaFutura = calendario.getTime();
//        System.out.println("fecha vencimiento " + formato.format(fechaFutura));
        return formato.format(fechaFutura);
    }
    
    public String obtenerFechaHoraActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHoraActual.format(formato);
    }
    public String obtenerFechaActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaHoraActual.format(formato);
    }
    public String obtenerFechaActualFormatoNormal() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaHoraActual.format(formato);
    }
    
    public String formatoFechaBDaJava(String fecha) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaParseada = formatoEntrada.parse(fecha);
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            return formatoSalida.format(fechaParseada);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejar error, podría devolverse un mensaje o valor de error
        }
    }
    
    public String formatoFechaJavaBD(String fecha) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaParseada = formatoEntrada.parse(fecha);
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
            return formatoSalida.format(fechaParseada);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejar error, podría devolverse un mensaje o valor de error
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

    public String formatoNumFactura(String numero) {
        // Asegura que el número tenga 7 dígitos o menos
        if (numero.length() > 7) {
            throw new IllegalArgumentException("Número demasiado grande");
        }

        // Rellena con ceros a la izquierda hasta tener 7 dígitos
        String numero7Digitos = String.format("%07d", Integer.parseInt(numero));

        // Divide el número en tres partes
        String parte1 = "001";
        String parte2 = "001";
        String parte3 = numero7Digitos;

        // Retorna el número en el formato deseado
        return String.format("%s-%s-%s", parte1, parte2, parte3);
    }

    
    //Convierte la fecha del dateChooser en formato yyyy-MM-dd
    public String formatoFecha(JDateChooser fecha){ 
        Date fechaSeleccionada = fecha.getDate();
        if (fechaSeleccionada != null) { // Verificar si se seleccionó una fecha
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fechaParaBaseDatos = formatoFecha.format(fechaSeleccionada);
            return fechaParaBaseDatos;
        } else {
            System.out.println("No se ha seleccionado ninguna fecha.");
            return "-1";
        }
    }
    //Convierte la fecha del dateChooser en formato yyyy-MM-dd
    public String formatoFechaNormal(JDateChooser fecha){ 
        Date fechaSeleccionada = fecha.getDate();
        if (fechaSeleccionada != null) { // Verificar si se seleccionó una fecha
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            String fechaParaBaseDatos = formatoFecha.format(fechaSeleccionada);
            return fechaParaBaseDatos;
        } else {
            System.out.println("No se ha seleccionado ninguna fecha.");
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
    public String formatearFechaHoraJDateChooser(JDateChooser fecha) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(fecha.getDate() != null){
            Date fechaDate = fecha.getDate();
            return formatoSalida.format(fechaDate);            
        }
        return "-1";
    }
    public String formatearFechaHoraJDateChooserDesde(JDateChooser fecha) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        if(fecha.getDate() != null){
            Date fechaDate = fecha.getDate();
            return formatoSalida.format(fechaDate);            
        }
        return "-1";
    }
    public String formatearFechaHoraJDateChooserHasta(JDateChooser fecha) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        if(fecha.getDate() != null){
            Date fechaDate = fecha.getDate();
            return formatoSalida.format(fechaDate);            
        }
        return "-1";
    }
    public void mostrarFechaJDateChooser(JDateChooser dateChooser, String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = formatoEntrada.parse(fecha);
            dateChooser.setDate(date);
            dateChooser.setDateFormatString(formatoSalida.toPattern());
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }
    public void mostrarFechaJDateChooserFormatoNormal(JDateChooser dateChooser, String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = formatoEntrada.parse(fecha);
            dateChooser.setDate(date);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }
    public void mostrarFechaHoraJDateChooser(JDateChooser dateChooser, String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date date = formatoEntrada.parse(fecha);
            dateChooser.setDate(date);
            dateChooser.setDateFormatString(formatoSalida.toPattern());

        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }
    }
}
