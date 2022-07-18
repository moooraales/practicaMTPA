package Servidor;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testRanking {
    static Ranking ranking;
    static Usuario usuario1;
    static Usuario usuario2;
    static Usuario usuario3;
    public testRanking() {
    }
    
    @BeforeEach
    public void setUp() {
        ranking = Ranking.getRanking();
        usuario1 = new Usuario("#pedrito","password321",3);
        usuario2 = new Usuario("#juanito","12345pass",2);
        usuario3 = new Usuario("#nachito","password",1);
        Usuario.getListaUsuarios().add(usuario1);
        Usuario.getListaUsuarios().add(usuario2);
        Usuario.getListaUsuarios().add(usuario3);
        ranking.getUsuarios().clear();
        ranking.getUsuarios().add(usuario1);
        ranking.getUsuarios().add(usuario2);
    }

    //test del metodo getUsuarios
    @Test
    public void testGetUsuarios() {
        System.out.println("getUsuarios");
        ArrayList<Usuario> expResultado = new ArrayList<>();
        expResultado.add(usuario1);
        expResultado.add(usuario2);
        
        ArrayList<Usuario> resultado = ranking.getUsuarios();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test del metodo actualizar
    @Test
    public void testActualizar() {
        System.out.println("actualizar");
        ArrayList<Usuario> expResultado = new ArrayList<>();
        expResultado.add(usuario1);
        expResultado.add(usuario2);
        expResultado.add(usuario3);
        ranking.actualizar();
        ArrayList<Usuario> resultado = ranking.getUsuarios();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }
    
}