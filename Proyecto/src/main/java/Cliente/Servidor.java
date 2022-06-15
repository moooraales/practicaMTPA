package Cliente;

import java.io.IOException;
import java.net.Socket;

public class Servidor{
    private static Servidor servidorUnico;
    private final Socket socket;
    
    private Servidor() throws IOException{
        socket = new Socket("localhost",505);
    }
    public static Servidor getServidor() throws IOException{
        if(servidorUnico==null){
            servidorUnico = new Servidor();
        }
        return servidorUnico;
    }
}