package Cliente;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuOpcionJuego {
    static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    
    public static void menuOpcionJuego() throws IOException{
        String opcion;
        do{
            System.out.println("1-Jugar contra otro jugador"
                    + "\n2-Listar Jugadores disponibles\n3-Ver ranking\n"
                    + "4-Ver retos\n0-Salir");
            System.out.println("Elige la opcion:\n");
            opcion = teclado.readLine();
            switch(opcion){
            case "1": //Buscar jugador
                retarJugador();
                break;
            case "2": //Listar jugadores activos
                listarUsuariosActivos();
                break;
            case "3": //Ver ranking
                verRanking();
                break;
            case "4": //Comprobar retos
                comprobarRetos();
                break;
            case "0": //Salir
                System.out.println("Adios.");
                System.exit(0);
                break;
            }
        }while(!"0".equals(opcion));
    }
    
    public static void retarJugador() throws IOException{
        System.out.println("Escribe el nombre del jugador a retar:");
        String jugador = teclado.readLine();
        boolean ok = OperacionString.validarNombre(jugador);
        if(ok == true){
            enviarReto(jugador);
        }else{
            System.out.println("El formato del nombre es incorrecto.\n");
        }  
    }
   
    public static void listarUsuariosActivos() throws IOException{
        String [] cadena;
        Servidor servidor = Servidor.getServidor();
        servidor.listarUsuariosActivos();
        cadena = servidor.recibir();
        if(cadena[0].equals("true")){
            for(int i = 1; i<cadena.length; i++){
                System.out.println(cadena[i]);
            }
            System.out.println("¿Quiere jugar contra alguno?");
            System.out.println("0-no, 1-si");
            String validacion = teclado.readLine();
            if(validacion.equals("1")){
                retarJugador();
            }
        }else{
            System.out.println(cadena[1]);
        }
    }
   
    public static void verRanking() throws IOException{
        String [] cadena;
        Servidor servidor = Servidor.getServidor();
        servidor.verRanking();
        cadena = servidor.recibir();
        if((cadena[0]).equals("true")){
            for(int i = 1; i<cadena.length; i+=2){
                System.out.println(cadena[i]+" puntos: "+cadena[i+1]);
            }
        }else{
            System.out.println(cadena[1]);
        }
    }
   
    public static void comprobarRetos() throws IOException{
        Servidor servidor = Servidor.getServidor();
        String [] respuesta;
        servidor.enviar("listarRetos;");
        respuesta = servidor.recibir();
        if((respuesta[0]).equals("true")){
            System.out.println("Retos:");
            for(int i = 0; i<respuesta.length; i++){
                System.out.println(respuesta[i]);
            }
            System.out.println("¿Quieres aceptar algun reto?");
            System.out.println("0-no, 1-si");
            String validacion = teclado.readLine();
            if(validacion.equals("1")){
                System.out.println("Escribe el nombre del jugador");
                String nombre = teclado.readLine();
                boolean ok = OperacionString.validarNombre(nombre);
                if(ok==true){
                    aceptarReto(nombre);
                }else{
                    System.out.println("El formato del nombre es incorrecto.\n");
                }
            }else{
                System.out.println("¿Quieres eliminar algun reto?");
                System.out.println("0-no, 1-si");
                validacion = teclado.readLine();
                if(validacion.equals("1")){
                    System.out.println("Escribe el nombre del jugador");
                    String nombre = teclado.readLine();
                    boolean ok = OperacionString.validarNombre(nombre);
                    if(ok==true){
                        rechazarReto(nombre);
                    }else{
                        System.out.println("El formato del nombre es incorrecto.\n");
                    }
                }
            }
        }else{
            System.out.println(respuesta[1]);
        }
    }
  
    public static String[] aceptarReto(String nombre) throws IOException{
        Servidor servidor = Servidor.getServidor();
        String [] respuesta;
        servidor.responderReto(nombre, "true");
        respuesta = servidor.recibir();
        System.out.println("Respuesta:");
        if(respuesta[0].equals("false")){
            System.out.println(respuesta[1]);
        }else{
            jugarIU(nombre);
        }
        return respuesta;
    }
   
    public static String[] rechazarReto(String nombre) throws IOException{
        Servidor servidor = Servidor.getServidor();
        String [] respuesta;
        servidor.responderReto(nombre, "false");
        respuesta = servidor.recibir();
        return respuesta;
    }
 
    public static String[] enviarReto(String nombre) throws IOException{
        Servidor servidor = Servidor.getServidor();
        String [] respuesta;
        servidor.retarJugador(nombre);
        respuesta = servidor.recibir();
        if(respuesta[0].equals("false")){
            System.out.println(respuesta[1]);
        }else{
            jugarIU(nombre);
            
        }
        return respuesta;
    }
   
    public static void jugarIU(String nombre){
       
        
        EsperadorJuego es = new EsperadorJuego(nombre);
        EventQueue.invokeLater( new Cliente.Interfaces.Menus.MenuOpcionJuego());
        
    }
  
    public static void jugarConsola(String nombre) throws IOException{
        MenuJuego.menuJuego(nombre);
    }
}
