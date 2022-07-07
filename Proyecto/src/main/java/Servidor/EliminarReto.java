package Servidor;

import java.util.ArrayList;

public class EliminarReto extends Thread{

    public EliminarReto(){
        this.start();
    }

    @Override
    public void run(){

        while(true){
            try{
                Thread.sleep(millis: 5000);
                eliminarElReto();
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void eliminarElReto(){
        ArrayList<Reto> retos = Reto.listarRetosActivos();
        Reto reto;
        Sala sala;
        int local, visitante, rondas;
        for(int i = 0;i < retos.size(); i++){
            reto = retos.get(i);
            sala = reto.getSala();

            if(reto.getEstado().equals(estadoReto.Aceptado)){
                local = sala.getPuntosLocal();
                visitante = sala.getPuntosVisitante();
                rondas = sala.getRondas();
                if(local+visitante==rondas){
                    reto.eliminar();

                }
            }
        }
    }
    
}
