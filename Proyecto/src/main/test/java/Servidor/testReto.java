package Servidor;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testReto {
    static Usuario local;
    static Usuario visitante;
    static Reto reto;
    static Sala sala;
    static estadoReto estadoPendiente;
    static estadoReto estadoAceptado;
    
    public testReto() {
    }
    
    @BeforeEach
    public void setUp() {
        local = new Usuario("#pedrito","password321");
        visitante = new Usuario("#juanito","12345pass");
        reto = new Reto(local, visitante);
        sala = new Sala(local, visitante);
        estadoPendiente= estadoReto.Pendiente;
        estadoAceptado= estadoReto.Aceptado;
        local.getListaRetos().add(0,reto);
        visitante.getListaRetos().add(0,reto);
    }

    //test del metodo getLocal
    @Test
    public void testGetLocal() {
        System.out.println("getLocal");
        Usuario expResultado = local;
        Usuario resultado = reto.getLocal();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test del metodo getVisitante
    @Test
    public void testGetVisitante() {
        System.out.println("getVisitante");
        Usuario expResultado = visitante;
        Usuario resultado = reto.getVisitante();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test del metodo getEstado
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        estadoReto expResultado = estadoReto.Pendiente;
        estadoReto resultado = reto.getEstado();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test del metodo setEstado, tanto para aceptado como para pendiente
    @Test
    public void testSetEstado_Pendiente() {
        System.out.println("setEstado");
        estadoReto expResultado = estadoReto.Pendiente;
        reto.setEstado(estadoPendiente);
        estadoReto resultado = reto.getEstado();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }
    @Test
    public void testSetEstado_Aceptado() {
        System.out.println("setEstado");
        estadoReto expResultado = estadoReto.Aceptado;
        reto.setEstado(estadoAceptado);
        estadoReto resultado = reto.getEstado();
        assertEquals(expResultado, resultado);
        assertNotEquals(reto.getSala(), null);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test del metodo setSala
    @Test
    public void testSetSala() {
        System.out.println("setSala");
        Sala expResultado = sala;
        reto.setSala(sala);
        Sala resultado = reto.getSala();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }
    
    //test del metodo getSala
    @Test
    public void testGetSala() {
        System.out.println("getSala");
        Sala expResultado = sala;
        reto.setSala(sala);
        Sala resultado = reto.getSala();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

}
