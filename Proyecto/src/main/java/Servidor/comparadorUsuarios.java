package Servidor;

import java.util.Comparator;

//Clase que compara las partidas ganadas por dos usuarios
public class comparadorUsuarios implements Comparator<Usuario>{

    @Override
    public int compare(Usuario u1, Usuario u2) {
        return new Integer(u2.getPartidasGanadas()).compareTo(u1.getPartidasGanadas());
    }
    
}
