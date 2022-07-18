package Servidor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class testPartidasSorter {
    
    public testComparadorUsuarios() {
    }

    //Test de comparador usuarios, comprueba que usuario1 tiene mas partidas, que 2 tiene mas, y que tienen las mismas
    @Test
    public void test1() {
        System.out.println("compare");
        Usuario u1 = new Usuario("#pedrito","password321",3);
        Usuario u2 = new Usuario("#juanito","12345pass",7);
        comparadorUsuarios instance = new comparadorUsuarios();
        int expResultado = -1;
        int resultado = instance.compare(u1, u2);
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }
    @Test
    public void test2() {
        System.out.println("compare");
        Usuario u1 = new Usuario("#pedrito","password321",3);
        Usuario u2 = new Usuario("#juanito","12345pass",7);
        comparadorUsuarios instance = new comparadorUsuarios();
        int expResultado = 1;
        int resultado = instance.compare(u2, u1);
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }
    @Test
    public void testIgual() {
        System.out.println("compare");
        Usuario u1 = new Usuario("#pedrito","password321",3);
        Usuario u2 = new Usuario("#juanito","12345pass",7);
        comparadorUsuarios instance = new comparadorUsuarios();
        int expResultado = 0;
        int resultado = instance.compare(u2, u1);
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }
    
}