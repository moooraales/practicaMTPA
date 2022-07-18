package Cliente;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.Socket;

public class Servidor {
    private static Servidor servidorUnico;
    private final Socket socket;
    
    private Servidor() throws IOException{
        socket = new Socket("localhost",2048);
    }
    public static Servidor getServidor() throws IOException{
        if(servidorUnico==null){
            servidorUnico = new Servidor();
        }
        return servidorUnico;
    }
    
    //Paso de mensajes
    public String[] recibir() throws IOException{
        byte[] respuestaCoded = new byte[255];
        String respuesta;
        String [] datos;
        socket.getInputStream().read(respuestaCoded);
        respuesta = new String(respuestaCoded);
        datos = respuesta.split(";");
        return datos;
    }
    //envia texto al servidor
    public void enviar(String mensaje) throws IOException{
        System.out.println(mensaje);
        byte[] mensajeCoded = mensaje.getBytes();
        socket.getOutputStream().write(mensajeCoded);
    }

    //envia datos registro
    public void registrar(String nombre, String password) throws IOException{
        String[] cadena = {"registrar", nombre, password};
        enviar(OperacionString.montarCadena(cadena));
    }
    //envia datos login
    public void login(String nombre, String password) throws IOException{
        String[] cadena = {"login", nombre, password};
        enviar(OperacionString.montarCadena(cadena));
    }
    
    //lista los usuarios
    public void listarUsuariosActivos()throws IOException{
        String[] cadena = {"listarUsuariosActivos"};
        enviar(OperacionString.montarCadena(cadena));
    }
    //envia reto
    public void retarJugador(String nombre) throws IOException{
        String[] cadena = {"retarJugador", nombre};
        enviar(OperacionString.montarCadena(cadena));
    }
    //envia ver ranking
    public void verRanking() throws IOException{
        String[] cadena = {"verRanking"};
        enviar(OperacionString.montarCadena(cadena));
    }
   //envia acepto reto
    public void aceptarReto(String usuarioRetador, String validacion) throws IOException{
        String[] cadena = {"aceptarReto", usuarioRetador, validacion};
        enviar(OperacionString.montarCadena(cadena));
    }
    //envia respuesta reto
    public void responderReto(String nombre, String ok) throws IOException{
        enviar("responderReto;"+nombre+";"+ok+";");
    }
    public void listarRetos() throws IOException{
        enviar("listarRetos;");
    }
    
   
    //envia actualziar marcador
    public String[] actualizarMarcador(String jugador) throws IOException{
        String [] cadena = {"actualizarMarcador",jugador};
        enviar(OperacionString.montarCadena(cadena));
        String [] marcador = recibir();
        return marcador;
    }
  
}
