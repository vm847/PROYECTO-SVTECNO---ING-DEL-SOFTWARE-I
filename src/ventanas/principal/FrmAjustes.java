package ventanas.principal;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Asus
 */
public class FrmAjustes extends javax.swing.JFrame {
    public int limiteCuotas = 160;
    
    public FrmAjustes() {
        initComponents();
    }
    
    public void soloMayusculas(JTextField campo, int longitud){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
                // Limitar a un máximo de caracteres
                if (campo.getText().length() >= longitud && !Character.isISOControl(c)) {
                    e.consume(); 
                }
            }
        });
    }
    public void soloNumeros(JTextField campo, int longitud){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isISOControl(c)) {
                    e.consume(); // Evita que el carácter se escriba en el campo
                }
                // Limitar a un máximo de caracteres
                if (campo.getText().length() >= longitud && !Character.isISOControl(c)) {
                    e.consume(); 
                }
            }
        });
    }
    public void longitudMaxima(JTextField campo, int longitud){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Limitar a un máximo de caracteres
                if (campo.getText().length() >= longitud && !Character.isISOControl(c)) {
                    e.consume(); 
                }
            }
        });
    }
    public void ruc(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '-') {
                    e.consume(); // Evita que el carácter se escriba en el campo
                }
                // Limitar a un máximo de 15 caracteres
                if (campo.getText().length() >= 15 && !Character.isISOControl(c)) {
                    e.consume(); // Evita que se ingrese más texto si ya hay 15 caracteres
                }
            }
        });
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Validar el formato al perder el foco
                if (!campo.getText().matches("\\d{5,13}-\\d{1}") && !campo.getText().matches("\\d{5,15}") && !campo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ser xxxxxxxx-x");
                    campo.requestFocus(); // Volver a enfocar el campo
                }
            }
        });
    }
    public void telefono(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '+' && c != '-') {
                    e.consume(); // Evita que el carácter se escriba en el campo
                }
                // Limitar a un máximo de 15 caracteres
                if (campo.getText().length() >= 20 && !Character.isISOControl(c)) {
                    e.consume(); // Evita que se ingrese más texto si ya hay 15 caracteres
                }
            }
        });
    }
    public void presionarEnter(JComponent campo, JComponent destino){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    destino.requestFocus();
                }
            }
        });
    }
    public void enterActionPerformed(JComponent campo){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(campo instanceof AbstractButton) {
                        ((AbstractButton) campo).doClick();
                    } else if (campo instanceof JTextField) {
                        ((JTextField) campo).postActionEvent();
                    }
                }
            }
        });
    }
    public void f1ActionPerformed(JComponent campo, JComponent actionPermed){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    if(actionPermed instanceof AbstractButton) {
                        ((AbstractButton) actionPermed).doClick();
                    } else if (actionPermed instanceof JTextField) {
                        ((JTextField) actionPermed).postActionEvent();
                    }
                }
            }
        });
    }
    public void numComprobante(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isISOControl(c) && c != '-') {
                    e.consume(); // Evita que el carácter se escriba en el campo
                }
                // Limitar a un máximo de 15 caracteres
                if (campo.getText().length() >= 15 && !Character.isISOControl(c)) {
                    e.consume(); // Evita que se ingrese más texto si ya hay 15 caracteres
                }
            }
        });
        // Agregar un listener de foco para controlar la edición
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Al obtener el foco, seleccionar solo los últimos 7 dígitos
                campo.selectAll();
                campo.select(8, 15);
            }
        });
    }

    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmAjustes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAjustes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAjustes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAjustes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAjustes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
