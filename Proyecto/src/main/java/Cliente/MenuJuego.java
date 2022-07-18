package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Cliente.Interfaces.Menus.MenuOpcionJuego;

import static java.lang.System.in;

public class MenuJuego {
   
    public static void menuJuego(String jugador) throws IOException{
        Servidor servidor = Servidor.getServidor();
        String opcion;
        int rondas;
        BufferedReader teclado = new BufferedReader(new InputStreamReader(in));
        String [] marcador;
        String [] ganador;
        do{
            
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
