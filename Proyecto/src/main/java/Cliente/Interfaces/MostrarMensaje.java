package Cliente.Interfaces;

import javax.swing.JOptionPane;

public class MostrarMensaje {
    /**
    * Muestra mensaje de error
    * @param err
    */
    public static void mostrarError(String err){
        JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.WARNING_MESSAGE);
    }
    /**
    * Muestra mensaje
    * @param mensaje
    */
    public static void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Notificacion", JOptionPane.INFORMATION_MESSAGE);
    }
}
