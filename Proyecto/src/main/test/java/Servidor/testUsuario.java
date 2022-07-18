package Servidor;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class testUsuario {
    static Usuario usuario1;
    static Usuario usuario2;
    static Reto reto;
    public UsuarioTest(){
        }
    
    //Declaramos lso datos necesarios para los test
    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario("#pedrito","password321",3);
        usuario2 = new Usuario("#juanito","12345pass",7);
        Usuario.getListaUsuarios().clear();
        Usuario.getListaUsuarios().add(usuario1);
        Usuario.getListaUsuarios().add(usuario2);
        reto = new Reto(usuario1,usuario2);
        usuario1.getListaRetos().add(reto);
        usuario2.getListaRetos().add(reto);
        
    }
    

    //Test del metodo getNombre
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        String expResultado = "#pedrito";
        String resultado = usuario1.getNombre();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //Test del metodo getPassword
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        String expResultado = "password321";
        String resultado = usuario1.getPassword();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

   
    //Test del metodo setPartidasGanadas
    @Test
    public void testSetPartidasGanadas() {
        System.out.println("setPartidasGanadas");
        int expResultado = 3;
        usuario1.setPartidasGanadas(expResultado);
        int resultado = usuario1.getPartidasGanadas();
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }

    //Test del metodo getPartidasGanadas
    @Test
    public void testGetPartidasGanadas() {
        System.out.println("getPartidasGanadas");
        int expResultado = 10;
        int resultado = usuario1.getPartidasGanadas();
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }

    //Test del metodo getListaRetos
    @Test
    public void testGetListaRetos() {
        System.out.println("getListaRetos");
        ArrayList<Reto> expResultado = new ArrayList<>();
        expResultado.add(reto);
        ArrayList<Reto> resultado = usuario1.getListaRetos();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

   //Test del metodo getListaUsuarios
    @Test
    public void testGetListaUsuarios() {
        System.out.println("getListaUsuarios");
        ArrayList<Usuario> expResultado = new ArrayList<>();
        expResultado.add(usuario1);
        expResultado.add(usuario2);
        ArrayList<Usuario> resultado = Usuario.getListaUsuarios();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //Test del metodo setListaUsuarios
    @Test
    public void testSetListaUsuarios() {
        System.out.println("setListaUsuarios");
        ArrayList<Usuario> expResultado = new ArrayList<>();
        expResultado.add(usuario1);
        Usuario.setListaUsuarios(expResultado);
        ArrayList<Usuario> resultado = Usuario.getListaUsuarios();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //Test del metodo addPartidasGanadas
    @Test
    public void testAddPartidasGanadas() {
        System.out.println("addPartidasGanadas");
        int expResultado = 1;
        usuario1.addPartidasGanadas();
        int resultado = usuario1.getPartidasGanadas();
        assertEquals(expResultado, resultado);
        if(expResultado!=resultado){
            fail("Error en el test");
        }
    }
    
}