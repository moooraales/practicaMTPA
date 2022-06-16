package Cliente;

import java.awt.EventQueue;
import java.io.IOException;

public class PrincipalCliente{
    public static void main(String[] args) throws InterruptedException{
        System.out.println("ESTABLECIENDO CONEXION, ESPERE");
        try{
            EventQueue.invokeLater( new Cliente.interfaces.MenuInicial());
            Servidor servidor = Servidor.getServidor();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
