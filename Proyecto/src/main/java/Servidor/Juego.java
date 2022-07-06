package Servidor;

public class Juego {
    private Usuario local;
    private Usuario visitante;
    private opcionJuego opcionLocal;
    private opcionJuego opcionVisitante;
    private int tiempoJugada;   // En milisegundos

    // Constructores

    public Juego(Usuario local, Usuario visitante) {    
        this(local,visitante,null,null);
    }
    
    public Juego(Usuario local, Usuario visitante, opcionJuego opcionLocal, opcionJuego opcionVisitante) {    
        this.local = local;
        this.visitante = visitante;
        this.opcionLocal = opcionLocal;
        this.opcionVisitante = opcionVisitante;
        this.tiempoJugada = 10000;
    }

    // Set & Get

    public void setOpcionLocal(opcionJuego opcionLocal) {
        this.opcionLocal = opcionLocal;
    }

    public void setOpcionVisitante(opcionJuego opcionVisitante) {
        this.opcionVisitante = opcionVisitante;
    }
    
    public opcionJuego getOpcionLocal() {
        return opcionLocal;
    }

    public opcionJuego getOpcionVisitante() {
        return opcionVisitante;
    }
    
    public int getTiempoJugada() {
        return tiempoJugada;
    }

    //Metodos

    protected Usuario obtenerGanador(){
        Usuario ganador;
        int resultado;

        if(opcionLocal==null){
            ganador = visitante;
        }else if(opcionVisitante == null){
            ganador = local;
        }else{
            resultado = opcionLocal.contra(opcionVisitante);
            switch (resultado) {
                case 1:
                    ganador = local;
                    break;
                case -1:
                    ganador = visitante;
                    break;
                default:
                    //Empate
                    ganador = null;
                    break;
                }
            }
        }
        return ganador;
    }

    public Usuario obtenerResultado(){
        boolean localActivo = true;
        boolean visitanteActivo = true;
        Usuario ganador;
        
        // Comprueba estado jugadores
        if(Cliente.buscarClienteLogueado(local) == null){
            localActivo = false;
        }
        if(Cliente.buscarClienteLogueado(visitante) == null){
            visitanteActivo = false;
        }
        
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
