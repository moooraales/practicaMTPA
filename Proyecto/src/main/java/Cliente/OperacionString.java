package Cliente;

public class OperacionString {
    /**
    * Montaria una cadena con formato a;b;c;
    * @param cadena
    * @return String
    */
    public static String montarCadena(String[] cadena){
        String mensaje = "";
        for(int i = 0; i<cadena.length; i++){
            mensaje += cadena[i] + ";";
        }
        return mensaje;
    }
    /**
    * Comprobaria si el nombre tiene el formato correcto
    * @param nombre
    * @return boolean
    */
    public static boolean validarNombre(String nombre) {
        boolean ok = false;
        if(nombre.substring(0,1).equals("#") && !nombre.substring(1, 2).equals(" ")){
            ok = true;
        }
        return ok;
    }
}
