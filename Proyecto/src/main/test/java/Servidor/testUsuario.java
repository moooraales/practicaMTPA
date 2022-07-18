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
        String expResult = "#pedrito";
        String result = usuario1.getNombre();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Error en el test");
        }
    }

    //Test del metodo getPassword
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        String expResult = "password321";
        String result = usuario1.getPassword();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Error en el test");
        }
    }

   
    //Test del metodo setPartidasGanadas
    @Test
    public void testSetPartidasGanadas() {
        System.out.println("setPartidasGanadas");
        int expResult = 3;
        usuario1.setPartidasGanadas(expResult);
        int result = usuario1.getPartidasGanadas();
        assertEquals(expResult, result);
        if(expResult!=result){
            fail("Test Fallido");
        }
    }

    //Test del metodo getPartidasGanadas
    @Test
    public void testGetPartidasGanadas() {
        System.out.println("getPartidasGanadas");
        int expResult = 10;
        int result = usuario1.getPartidasGanadas();
        assertEquals(expResult, result);
        if(expResult!=result){
            fail("Test Fallido");
        }
    }

    //Test del metodo getListaRetos
    @Test
    public void testGetListaRetos() {
        System.out.println("getListaRetos");
        ArrayList<Reto> expResult = new ArrayList<>();
        expResult.add(reto);
        ArrayList<Reto> result = usuario1.getListaRetos();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

   //Test del metodo getListaUsuarios
    @Test
    public void testGetListaUsuarios() {
        System.out.println("getListaUsuarios");
        ArrayList<Usuario> expResult = new ArrayList<>();
        expResult.add(usuario1);
        expResult.add(usuario2);
        ArrayList<Usuario> result = Usuario.getListaUsuarios();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    //Test del metodo setListaUsuarios
    @Test
    public void testSetListaUsuarios() {
        System.out.println("setListaUsuarios");
        ArrayList<Usuario> expResult = new ArrayList<>();
        expResult.add(usuario1);
        Usuario.setListaUsuarios(expResult);
        ArrayList<Usuario> result = Usuario.getListaUsuarios();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    //Test del metodo addPartidasGanadas
    @Test
    public void testAddPartidasGanadas() {
        System.out.println("addPartidasGanadas");
        int expResult = 1;
        usuario1.addPartidasGanadas();
        int result = usuario1.getPartidasGanadas();
        assertEquals(expResult, result);
        if(expResult!=result){
            fail("Test Fallido");
        }
    }
    
}