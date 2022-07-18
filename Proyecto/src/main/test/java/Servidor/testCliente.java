package Servidor;

import Servidor.Excepcion.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testCliente {
    static Cliente clienteLog,clienteSinLog;
    static Usuario usuarioLog,usuarioSinLog;
    static Socket socketLog,socketSinLog;
    static ArrayList<Usuario> alUsuario;
    static ArrayList<Cliente> alCliente;
    
    public testCliente() {
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("Before Test");
        alCliente = new ArrayList<>();
        alUsuario = new ArrayList<>();
        socketLog = new Socket();
        socketSinLog = new Socket();
        clienteLog = new Cliente(socketLog);
        usuarioLog = new Usuario("#pedrito","password321");
        clienteLog.setUsuario(usuarioLog);
        clienteSinLog = new Cliente(socketSinLog);
        usuarioSinLog = new Usuario("#juanito","12345pass");
        alCliente.add(clienteLog);
        alCliente.add(clienteSinLog);
        Cliente.setListaClientesActivos(alCliente);
        alUsuario.add(usuarioLog);
        alUsuario.add(usuarioSinLog);
        Usuario.setListaUsuarios(alUsuario);
    }

    /**
     * Test of getListaClientesActivos method, of class Cliente.
     */
    @Test
    public void testGetListaClientesActivos() {
        System.out.println("getListaClientesActivos");
        ArrayList<Cliente> expResult = new ArrayList<>();
        ArrayList<Cliente> result;
        expResult.add(clienteLog);
        expResult.add(clienteSinLog);

        result = Cliente.getListaClientesActivos();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of getCliente method, of class Cliente.
     */
    @Test
    public void testGetCliente() {
        System.out.println("getCliente");
        Socket expResult = socketLog;
        Socket result = clienteLog.getCliente();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of getUsuario method, of class Cliente.
     */
    @Test
    public void testGetUsuario() {
        System.out.println("getUsuario");
        Usuario expResult = usuarioLog;
        Usuario result = clienteLog.getUsuario();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of setUsuario method, of class Cliente.
     */
    @Test
    public void testSetUsuario() {
        System.out.println("setUsuario");
        Usuario expResult = new Usuario("usuario3","1234");
        clienteLog.setUsuario(expResult);
        Usuario result = clienteLog.getUsuario();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of listarClientesLogueados method, of class Cliente.
     */
    @Test
    public void testListarClientesLogueados() {
        System.out.println("listarClientesLogueados");
        ArrayList<Cliente> expResult = new ArrayList<>();
        expResult.add(clienteLog);
        ArrayList<Cliente> result = Cliente.listarClientesLogueados();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of listarUsuariosLogueados method, of class Cliente.
     */
    @Test
    public void testListarUsuariosLogueados() {
        System.out.println("listarUsuariosLogueados");
        ArrayList<Usuario> expResult = new ArrayList<>();
        expResult.add(usuarioLog);
        ArrayList<Usuario> result = Cliente.listarUsuariosLogueados();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of buscarClienteLogueado method, of class Cliente.
     */
    @Test
    public void testBuscarClienteLogueado_Usuario_OK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResult = clienteLog;
        Cliente result = Cliente.buscarClienteLogueado(usuarioLog);
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
            fail("Test Fallido");
        }
    }
    
    @Test
    public void testBuscarClienteLogueado_Usuario_NOK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResult = null;
        Cliente result = Cliente.buscarClienteLogueado(usuarioSinLog);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of buscarClienteLogueado method, of class Cliente.
     */
    @Test
    public void testBuscarClienteLogueado_String_OK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResult = clienteLog;
        Cliente result = Cliente.buscarClienteLogueado(usuarioLog.getNombre());
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testBuscarClienteLogueado_String_NOK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResult = null;
        Cliente result = Cliente.buscarClienteLogueado(usuarioSinLog.getNombre());
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of desconectar method, of class Cliente.
     * @throws java.io.IOException
     */
    @Test
    public void testDesconectar() throws IOException {
        System.out.println("desconectar");
        ArrayList<Cliente> expResult = new ArrayList<>();
        clienteLog.desconectar();
        clienteSinLog.desconectar();
        ArrayList<Cliente> result = Cliente.getListaClientesActivos();
        assertEquals(expResult,result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of opcionRegistrar method, of class Cliente.
     */
    @Test
    public void testOpcionRegistrar_OK() {
        System.out.println("opcionRegistrar");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        String[] solicitud = "registrar;#usuario0;1234;".split(";");
        ArrayList<String> result = clienteSinLog.opcionRegistrar(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionRegistrar_NOK() {
        System.out.println("opcionRegistrar");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new RegistroException().toString());
        String[] solicitud = "registrar;#usuario1;2345;".split(";");
        ArrayList<String> result = clienteSinLog.opcionRegistrar(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of opcionLogin method, of class Cliente.
     */
    @Test
    public void testOpcionLogin_OK() {
        System.out.println("opcionLogin");
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        String[] solicitud = "login;#usuario2;1234;".split(";");
        ArrayList<String> result = clienteSinLog.opcionLogin(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionLogin_NOK() {
        System.out.println("opcionLogin");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new LoginException().toString());
        String[] solicitud = "login;#usuario0;1234;".split(";");
        ArrayList<String> result = clienteSinLog.opcionLogin(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of opcionListarUsuariosActivos method, of class Cliente.
     */
    @Test
    public void testOpcionListarUsuariosActivos_OK() {
        System.out.println("opcionListarUsuariosActivos");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        expResult.add("#usuario1");
        ArrayList<String> result = clienteSinLog.opcionListarUsuariosActivos();
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionListarUsuariosActivos_NOK() throws IOException {
        System.out.println("opcionListarUsuariosActivos");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new NotFoundException().toString());
        clienteLog.desconectar();
        ArrayList<String> result = clienteSinLog.opcionListarUsuariosActivos();
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of opcionRetarJugador method, of class Cliente.
     */
    @Test
    public void testOpcionRetarJugador_OK() throws Exception {
        System.out.println("opcionRetarJugador");
        
        Cliente clienteLog2 = new Cliente(new Socket());
        Usuario usuarioLog2 = new Usuario("#usuario2","1234");
        clienteLog2.setUsuario(usuarioLog2);
        Cliente.getListaClientesActivos().add(clienteLog2);
        Usuario.getListaUsuarios().add(usuarioLog2);
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        String[] solicitud = "retarJugador;#usuario1;".split(";");
        ArrayList<String> result = clienteLog2.opcionRetarJugador(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionRetarJugador_NOK_AutoReto() throws Exception {
        System.out.println("opcionRetarJugador");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new AutoRetoException().toString());
        String[] solicitud = "retarJugador;#usuario1;".split(";");
        ArrayList<String> result = clienteLog.opcionRetarJugador(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionRetarJugador_NOK_PendingReto() throws Exception {
        System.out.println("opcionRetarJugador");
                
        Cliente clienteLog2 = new Cliente(new Socket());
        Usuario usuarioLog2 = new Usuario("#usuario2","1234");
        clienteLog2.setUsuario(usuarioLog2);
        alCliente.add(clienteLog2);
        Cliente.setListaClientesActivos(alCliente);
        alUsuario.add(usuarioLog2);
        Usuario.setListaUsuarios(alUsuario);
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new PendingRetoException().toString());
        String[] solicitud = "retarJugador;#usuario1;".split(";");
        clienteLog2.opcionRetarJugador(solicitud);
        ArrayList<String> result = clienteLog2.opcionRetarJugador(solicitud);
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }

    /**
     * Test of opcionListarRetos method, of class Cliente.
     */
    @Test
    public void testOpcionListarRetos_OK_Pendiente() throws Exception {
        System.out.println("opcionListarRetos");
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        expResult.add("#usuario2");
        expResult.add("Pendiente");
        
        usuarioLog.addReto(usuarioSinLog);
        ArrayList<String> result = clienteLog.opcionListarRetos();
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionListarRetos_OK_Aceptado() throws Exception {
        System.out.println("opcionListarRetos");
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("true");
        expResult.add("#usuario2");
        expResult.add("Aceptado");
        
        Reto reto = new Reto(usuarioLog, usuarioSinLog);
        reto.setEstado(estadoReto.Aceptado);
        usuarioLog.getListaRetos().add(reto);
        ArrayList<String> result = clienteLog.opcionListarRetos();
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
    @Test
    public void testOpcionListarRetos_NOK() throws Exception {
        System.out.println("opcionListarRetos");
        
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("false");
        expResult.add(new NotFoundException().toString());

        ArrayList<String> result = clienteLog.opcionListarRetos();
        assertEquals(expResult, result);
        if(!result.equals(expResult)){
            fail("Test Fallido");
        }
    }
   
}
