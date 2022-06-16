package Cliente;

import java.io.IOException;
import static java.lang.Integer.parseInt;
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

    /*Paso de mensajes */
    /**
     * Recibe texto del servidor y lo divide en un array
     * @return String[] con parametros recibidos
     * @throws IOException 
     */
    public String[] recibir() throws IOException{
        byte[] respuestaCoded = new byte[255];
        String respuesta;
        String [] datos;
        socket.getInputStream().read(respuestaCoded);
        respuesta = new String(respuestaCoded);
        datos = respuesta.split(";");
        return datos;
    }

    /**
    * Envia texto al servidor
    * @throws IOException Puede lanzar una IOException
    * @param mensaje Mensaje en formato valor1;valor2;valor3; que será enviado
    * al servidor
    */
    public void enviar(String mensaje) throws IOException{
        System.out.println(mensaje);
        byte[] mensajeCoded = mensaje.getBytes();
        socket.getOutputStream().write(mensajeCoded);
    }

    /*Menu inicial*/
    /**
    * Enviaria la cadena montada al servidor con los datos del registro
    * @param nombre
    * @param password
    */
    public void registrar(String nombre, String password) throws IOException{
        String[] cadena = {"registrar", nombre, password};
        enviar(OperacionString.montarCadena(cadena));
    }

     /*Menu opcion juego*/
    /**
    * Enviaria la cadena montada al servidor para poder listar los usuarios
    * activos
    * @throws java.io.IOException
    */
    public void listarUsuariosActivos()throws IOException{
        String[] cadena = {"listarUsuariosActivos"};
        enviar(OperacionString.montarCadena(cadena));
    }

    /**
    * Enviaria la cadena montada al servidor para poder retar a un jugador
    * @param nombre
    * @throws java.io.IOException
    */
    public void retarJugador(String nombre) throws IOException{
        String[] cadena = {"retarJugador", nombre};
        enviar(OperacionString.montarCadena(cadena));
    }

    /**
    * Enviaria la cadena montada al servidor para poder ver el ranking
    * activos
    * @throws java.io.IOException
    */
    public void verRanking() throws IOException{
        String[] cadena = {"verRanking"};
        enviar(OperacionString.montarCadena(cadena));
    }

    /**
    * Enviaria la cadena montada al servidor para poder aceptar un reto
    * @param usuarioRetador
    * @param validacion
    * @throws java.io.IOException
    */
    public void aceptarReto(String usuarioRetador, String validacion) throws IOException{
        String[] cadena = {"aceptarReto", usuarioRetador, validacion};
        enviar(OperacionString.montarCadena(cadena));
    }

    /**
    * Enviaria la cadena montada al servidor para poder responder un reto
    * de forma positiva o negativa
    * @param nombre
    * @param ok
    * @throws java.io.IOException
    */
    public void responderReto(String nombre, String ok) throws IOException{
        enviar("responderReto;"+nombre+";"+ok+";");
    }
    public void listarRetos() throws IOException{
        enviar("listarRetos;");
    }

    /*Menu juego*/

    /**
    * Enviaria la cadena montada al servidor con los datos de la jugada y el
    * jugador contra el que esta jugando
    * @param usuario
    * @param opcion
    * @return String[]
    * @throws java.io.IOException
    */
    public String[] elegirJugada(String usuario, String opcion) throws IOException{
        String [] cadena = {"jugar", usuario, opcion};
        enviar(OperacionString.montarCadena(cadena));
        String [] ganador = recibir();
        return ganador;
    }

    /**
    * Enviaria la cadena montada al servidor con los datos del jugador contra 
    * el que esta jugando para actualizar el marcador
    * @param jugador
    * @return String[]
    * @throws java.io.IOException
    */
    public String[] actualizarMarcador(String jugador) throws IOException{
        String [] cadena = {"actualizarMarcador",jugador};
        enviar(OperacionString.montarCadena(cadena));
        String [] marcador = recibir();
        return marcador;
    }


     /**
    * Enviaria la cadena montada al servidor con los datos del jugador contra 
    * el que esta jugando para recibir las rondas restantes
    * @param jugador
    * @return String[]
    * @throws java.io.IOException
    */
    public int dameRondas(String jugador) throws IOException{
        String [] cadena = {"rondasRestantes",jugador};
        enviar(OperacionString.montarCadena(cadena));
        String [] rondasRestantes = recibir();
        if(rondasRestantes[0].equals("true")){
            System.out.println("Rondas restantes:"+rondasRestantes[1]);
        }else{
            System.out.println(rondasRestantes[1]);
        }
        return parseInt(rondasRestantes[1]);
    }

    /**
    * Enviaria la cadena montada al servidor con los datos del jugador contra 
    * el que esta jugando para recibir las rondas restantes
    * @param jugador
    * @return String[]
    * @throws java.io.IOException
    */
    public String dameRondas2(String jugador) throws IOException{
        String [] cadena = {"rondasRestantes",jugador};
        enviar(OperacionString.montarCadena(cadena));
        String [] rondasRestantes = recibir();
        return (rondasRestantes[1]);
    }  

}