package Servidor;

import java.util.ArrayList;

public class Reto {
    //El usuario local es el retador y el visitante es el retado
    private Usuario local;      
    private Usuario visitante;      
    private estadoReto estado;
    private Sala sala;
    
    // Constructores
    
    public Reto(Usuario local, Usuario visitante){
        this.local = local;
        this.visitante = visitante;
        this.estado = estadoReto.Pendiente;
        this.sala = new Sala(local,visitante);
    }

    //Getters y setters
    
    public Usuario getLocal() {
        return local;
    }
    
    public Usuario getVisitante() {
        return visitante;
    }

    public estadoReto getEstado() {
        return estado;
    }

    public void setEstado(estadoReto estado) {
        this.estado = estado;
    }
    
    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
    public Sala getSala() {
        return sala;
    }
    
    //Metodo que elimina un reto tanto del retador como del retado
    public void eliminar(){
        local.getListaRetos().remove(this);
        visitante.getListaRetos().remove(this);
    }

    //Metodo que muestra los retos activos de los usuarios que estan logeados en el servidor
    public static ArrayList<Reto> listarRetosActivos(){
        return listarRetosActivos(Cliente.listarUsuariosLogueados());
    }

    //Metodo que muestra los retos activos de una lista de usuarios
    public static ArrayList<Reto> listarRetosActivos(ArrayList<Usuario> listaUsuarios){
        ArrayList<Reto> respuesta = new ArrayList<>();
        ArrayList<Reto> retosUsuario;
        Reto reto;
        for(int i=0;i<listaUsuarios.size();i++){
            retosUsuario = listaUsuarios.get(i).getListaRetos();
            for(int j=0; j<retosUsuario.size();j++){
                reto = retosUsuario.get(j);
                if(!respuesta.contains(reto)){
                    respuesta.add(reto);
                }
            }
        }
        return respuesta;
    }
}