package Cliente;

import java.awt.EventQueue;
import java.io.IOException;

public class PrincipalCliente{
    public static void main(String[] args) throws InterruptedException{
<<<<<<< HEAD
        System.out.println("Estableciendo conexión con el servidor...");
        try{
            EventQueue.invokeLater( new Cliente.Interfaces.Menus.MenuInicial());
=======
        System.out.println("ESTABLECIENDO CONEXION, ESPERE");
        try{
            EventQueue.invokeLater( new Cliente.interfaces.MenuInicial());
>>>>>>> 741bc9cb04d39582c2f1fcdfd09b4e55d3ac3fdc
            Servidor servidor = Servidor.getServidor();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
