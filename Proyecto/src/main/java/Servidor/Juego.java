package Servidor;

public class Juego {
    private Usuario local;
    private Usuario visitante;


    // Constructores

    public Juego(Usuario local, Usuario visitante) {    
        this(local,visitante);
    }
    
    public Juego(Usuario local, Usuario visitante) {    
        this.local = local;
        this.visitante = visitante;

    }


    //Metodos

    public void obtenerGanador(){

    }
    public Usuario obtenerResultado(){
        boolean localActivo = true;
        boolean visitanteActivo = true;
        Usuario ganador;
        
        // Ganador en funcion de inactividad
        if(!localActivo && !visitanteActivo){
            ganador = null;
        }else if(!localActivo){
            ganador = visitante;
        }else if(!visitanteActivo){
            ganador = local;
        }else{  // Ganador por jugada
            ganador = obtenerGanador();
        }
        return ganador;
    }
}

