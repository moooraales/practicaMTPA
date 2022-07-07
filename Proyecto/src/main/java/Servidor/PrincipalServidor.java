package Servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class PrincipalServidor {
    static boolean arrancado = true;
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(2048);
        System.out.println("Servidor iniciado.");
        UsuarioDatosPersistentes.cargarUsuarios();
        MenuServidor mS = new MenuServidor();
        GuardaryBorrar oR = new GuardaryBorrar();
        while(true){
            while(arrancado){
                //System.out.println("DEBUG: Servidor en escucha");
                Socket socketCliente = server.accept();
                //System.out.println("DEBUG: Conectando nuevo usuario");
                Cliente.getListaClientesActivos().add(new Cliente(socketCliente));
            }
        }
    }
}
