package Servidor;

import java.util.ArrayList;

public class Ranking {
    private static Ranking rankingGeneral;
    private final int nUsuarios = 3;
    private ArrayList<Usuario> usuarios = new ArrayList<>(nUsuarios);
    
    // Constructores
    
    private Ranking(){
    }
    
    public static Ranking getRanking(){
        if(rankingGeneral == null){
            rankingGeneral = new Ranking();
        }
        rankingGeneral.actualizar();
        return rankingGeneral;
    }
    
    //Getters y setters
    
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    
 
    //Metodo que actualiza los valores del ranking, ordenando por partidas ganadas
    public void actualizar(){
        ArrayList<Usuario> usuariosBD = Usuario.getListaUsuarios();
        usuariosBD.sort(new comparadorUsuarios());
        usuarios.clear();
        for(int i=0;i<usuariosBD.size() && i<nUsuarios;i++){
            usuarios.add(usuariosBD.get(i));
        }
    }
    
}