package Servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static Servidor.Usuario.registrar;

public class UsuarioDatosPersistentes {
    private static final String FicheroUsers = "usuarios.txt";

    //Carga los datos del fichero
    public static boolean cargarUsuarios(){
        return cargarUsuarios(FicheroUsers);
    }
//Carga los datos de los usuarios del fichero de usuarios
    public static boolean cargarUsuarios(String nombreFichero){
        boolean ok;
        File fichero = new File(nombreFichero);
        Scanner s = null;
        try {
            s = new Scanner(fichero);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String [] datos=linea.split(";");
                registrar(String.valueOf(datos[0]),String.valueOf(datos[1]), 
                        Integer.valueOf(datos[2]));
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
             System.err.print("ERROR: "+ex+" NO SE ENCUENTRA EL FICHERO");
        } finally {
            try {
                if (s != null)
                    s.close();
                ok = true;
            } catch (Exception ex2) {
                System.err.print("ERROR: "+ex2+" Cerrado de fichero");
                ok = false;
            }
        }
        return ok;
    }

    //Guarda los datos de los usuarios en el fichero
    public static boolean guardarUsuarios(){
        return guardarUsuarios(FicheroUsers);
    }


    //Guarda los datos de los usuarios en el fichero usuarios
    public static boolean guardarUsuarios(String nombreFichero){
        boolean ok;
        FileWriter fichero;
        try {
            fichero = new FileWriter(nombreFichero);
            for(int i=0;i<Usuario.getListaUsuarios().size();i++){
                fichero.write(Usuario.getListaUsuarios().get(i).getNombre()+ ";" 
                        +Usuario.getListaUsuarios().get(i).getPassword() + ";" +
                        Usuario.getListaUsuarios().get(i).getPartidasGanadas()
                        + ";\n");
            }
            fichero.close();
            ok = true;
        } catch (IOException ex) {
            System.err.print("ERROR: "+ex+" NO SE HAN PODIDO ALMACENAR LOS DATOS");
            ok = false;
        }
        return ok;
    }
}