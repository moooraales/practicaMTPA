package Servidor;

public class Sala {
    private final Usuario local;
    private final Usuario visitante;
    private final int rondas;
    private int puntosLocal;
    private int puntosVisitante;
    private Juego juegoEnCurso;

    // Constructores

    public Sala(Usuario local, Usuario visitante) {
        this.local = local;
        this.visitante = visitante;
        this.rondas = 3;
        this.puntosLocal = 0;
        this.puntosVisitante = 0;
        this.juegoEnCurso = new Juego(local, visitante);
    }

    // Set & Get
    
    public Usuario getLocal() {
            return local;
        }
    
        public Usuario getVisitante() {
            return visitante;
        }
    
        public int getRondas() {
            return rondas;
        }
    
        public int getPuntosLocal() {
            return puntosLocal;
        }
    
        public int getPuntosVisitante() {
            return puntosVisitante;
        }
        
        public void setJuegoEnCurso(Juego juegoEnCurso) {
            this.juegoEnCurso = juegoEnCurso;
        }
        
        public Juego getJuegoEnCurso() {
            return juegoEnCurso;
        }
        
        // Metodos
        
        private void addPunto(Usuario ganador){
            if(ganador.equals(local)){
                puntosLocal++;
            }else if(ganador.equals(visitante)){
                puntosVisitante++;
            }
        }
        
        public void sumarPunto(Usuario actual, Usuario ganador){
            if(actual.equals(ganador)){
                addPunto(ganador);
                if(puntosLocal + puntosVisitante == rondas){
                    ganador.addPartidasGanadas();
                    Ranking.getRanking().actualizar();
                }
            }
        }
}
