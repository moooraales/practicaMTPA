package Cliente;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EsperadorJuego extends Thread{
    public String rival;
    public EsperadorJuego(String rival){
        this.rival = rival;
        this.start();
    }
    @Override
    public void run(){
        Servidor servidor;
        boolean espera = false;
        try {
            do{
                Thread.sleep(1000);
                servidor = Servidor.getServidor();
                servidor.listarRetos();
                String usuarios [] = servidor.recibir();
                for(int i=1;i<usuarios.length-1;i+=2){
                    espera = false;
                    String uRival = usuarios[i];
                    String estado = usuarios[i+1];
                    if(rival.equals(uRival)){
                        if(estado.equals("Aceptado")){
                            EventQueue.invokeLater(new Cliente.Interfaces.Juego.Conecta4(8,8));
                            espera = false;
                        }else if(estado.equals("Pendiente")){
                            espera = true;
                        }
                    }
                }
            }while(espera);
        } catch (IOException ex) {
            Logger.getLogger(EsperadorJuego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EsperadorJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
