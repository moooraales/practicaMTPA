package Cliente;

import java.awt.EventQueue;
import java.io.IOException;

public class PrincipalCliente{
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Estableciendo conexión con el servidor...");
        try{
            EventQueue.invokeLater(new Cliente.Interfaces.Juego.Interfaz());
            Servidor servidor = Servidor.getServidor();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
