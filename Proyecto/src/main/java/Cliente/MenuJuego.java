package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Cliente.Interfaces.Menus.MenuOpcionJuego;

import static java.lang.System.in;

public class MenuJuego {
    /**
    * Genera un menu para poder seleccionar las opciones del juego 
    * (Piedra, Papel y Tijeras), mandando las jugadas al servidor 
    * con {@link Cliente.Servidor#enviar(java.lang.String)} 
    * y recibiendo los datos de las rondas y el marcador con 
    * {@link Cliente.Servidor#recibir()} 
    * @author Luis Enrique Muñoz
    * @param jugador
    * @throws java.io.IOException
    * @since 1.0
    * @version 1.0 
    */
    public static void menuJuego(String jugador) throws IOException{
        Servidor servidor = Servidor.getServidor();
        String opcion;
        int rondas;
        BufferedReader teclado = new BufferedReader(new InputStreamReader(in));
        String [] marcador;
        String [] ganador;
        do{
            System.out.println("0-Piedra\n1-Papel\n2-Tijeras");
            opcion = teclado.readLine();
                
            switch(opcion){
                case "0":
                    servidor.enviar("jugar;"+jugador+";"+opcion+";");
                    break;
                case "1":
                    servidor.enviar("jugar;"+jugador+";"+opcion+";");
                    break;
                case "2":
                    servidor.enviar("jugar;"+jugador+";"+opcion+";");
                    break;
            }
            ganador = servidor.recibir();
            for(int i=1;i<ganador.length;i++){
                System.out.print(ganador[i]);
            }
            marcador = servidor.actualizarMarcador(jugador);
            rondas = servidor.dameRondas(jugador);
            for(int i=0;i<marcador.length;i++){
                System.out.println(marcador[i]);
            }
        }while(rondas!=0);
        Cliente.MenuOpcionJuego.rechazarReto(jugador);
    }
}
