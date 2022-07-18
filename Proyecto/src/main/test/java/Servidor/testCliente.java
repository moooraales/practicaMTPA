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
        alUsuario = new ArrayList<>();
        socketLog = new Socket();
        socketSinLog = new Socket();
        usuarioLog = new Usuario("#pedrito","password321");
        clienteLog.setUsuario(usuarioLog);
        usuarioSinLog = new Usuario("#juanito","12345pass");
        alCliente.add(clienteLog);
        alCliente.add(clienteSinLog);
        Cliente.setListaClientesActivos(alCliente);
        alUsuario.add(usuarioLog);
        alUsuario.add(usuarioSinLog);
        Usuario.setListaUsuarios(alUsuario);
    }

    //test de getListaClientesActivos
    @Test
    public void testGetListaClientesActivos() {
        System.out.println("getListaClientesActivos");
        ArrayList<Cliente> expResultado = new ArrayList<>();
        ArrayList<Cliente> resultado;
        expResultado.add(clienteLog);
        expResultado.add(clienteSinLog);

        resultado = Cliente.getListaClientesActivos();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de getCliente
    @Test
    public void testGetCliente() {
        System.out.println("getCliente");
        Socket expResultado = socketLog;
        Socket resultado = clienteLog.getCliente();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de get Usuario
    @Test
    public void testGetUsuario() {
        System.out.println("getUsuario");
        Usuario expResultado = usuarioLog;
        Usuario resultado = clienteLog.getUsuario();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de setUsuario
    @Test
    public void testSetUsuario() {
        System.out.println("setUsuario");
        Usuario expResultado = new Usuario("nachito","password");
        clienteLog.setUsuario(expResultado);
        Usuario resultado = clienteLog.getUsuario();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de listarClientesLogeados
    @Test
    public void testListarClientesLogueados() {
        System.out.println("listarClientesLogueados");
        ArrayList<Cliente> expResultado = new ArrayList<>();
        expResultado.add(clienteLog);
        ArrayList<Cliente> resultado = Cliente.listarClientesLogueados();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de listarUsuariosLogeados
    @Test
    public void testListarUsuariosLogueados() {
        System.out.println("listarUsuariosLogueados");
        ArrayList<Usuario> expResultado = new ArrayList<>();
        expResultado.add(usuarioLog);
        ArrayList<Usuario> resultado = Cliente.listarUsuariosLogueados();
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }

    //test de buscarClienteLogeado, con y sin log
    @Test
    public void testBuscarClienteLogueado_Usuario_OK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResultado = clienteLog;
        Cliente resultado = Cliente.buscarClienteLogueado(usuarioLog);
        assertEquals(expResultado, resultado);
        if(!expResultado.equals(resultado)){
            fail("Error en el test");
        }
    }
    
    @Test
    public void testBuscarClienteLogueado_Usuario_NOK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResultado = null;
        Cliente resultado = Cliente.buscarClienteLogueado(usuarioSinLog);
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

    //test de buscar cliente logeado, que este y no este
    @Test
    public void testBuscarClienteLogueado_String_OK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResultado = clienteLog;
        Cliente resultado = Cliente.buscarClienteLogueado(usuarioLog.getNombre());
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }
    @Test
    public void testBuscarClienteLogueado_String_NOK() {
        System.out.println("buscarClienteLogueado");
        Cliente expResultado = null;
        Cliente resultado = Cliente.buscarClienteLogueado(usuarioSinLog.getNombre());
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

    //test del metodo desconectar
    @Test
    public void testDesconectar() throws IOException {
        System.out.println("desconectar");
        ArrayList<Cliente> expResultado = new ArrayList<>();
        clienteLog.desconectar();
        clienteSinLog.desconectar();
        ArrayList<Cliente> resultado = Cliente.getListaClientesActivos();
        assertEquals(expResultado,resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

    //test de opcionRegistrar, que deje y que no deje
    @Test
    public void testOpcionRegistrar_OK() {
        System.out.println("opcionRegistrar");
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("true");
        String[] solicitud = "registrar;#usuario0;1234;".split(";");
        ArrayList<String> resultado = clienteSinLog.opcionRegistrar(solicitud);
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }
    @Test
    public void testOpcionRegistrar_NOK() {
        System.out.println("opcionRegistrar");
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("false");
        expResultado.add(new RegistroException().toString());
        String[] solicitud = "registrar;#usuario1;2345;".split(";");
        ArrayList<String> resultado = clienteSinLog.opcionRegistrar(solicitud);
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

    //test de opcionLogin, que valga y que no valga
    @Test
    public void testOpcionLogin_OK() {
        System.out.println("opcionLogin");
        
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("true");
        String[] solicitud = "login;#juanito;12345pass;".split(";");
        ArrayList<String> resultado = clienteSinLog.opcionLogin(solicitud);
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }
    @Test
    public void testOpcionLogin_NOK() {
        System.out.println("opcionLogin");
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("false");
        expResultado.add(new LoginException().toString());
        String[] solicitud = "login;#jaimito;contrasenia;".split(";");
        ArrayList<String> resultado = clienteSinLog.opcionLogin(solicitud);
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

    //test del metodo listarUsuariosActivos, que haya y que no haya
    @Test
    public void testOpcionListarUsuariosActivos_OK() {
        System.out.println("opcionListarUsuariosActivos");
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("true");
        expResultado.add("#pedrito");
        ArrayList<String> resultado = clienteSinLog.opcionListarUsuariosActivos();
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }
    @Test
    public void testOpcionListarUsuariosActivos_NOK() throws IOException {
        System.out.println("opcionListarUsuariosActivos");
        ArrayList<String> expResultado = new ArrayList<>();
        expResultado.add("false");
        expResultado.add(new NotFoundException().toString());
        clienteLog.desconectar();
        ArrayList<String> resultado = clienteSinLog.opcionListarUsuariosActivos();
        assertEquals(expResultado, resultado);
        if(!resultado.equals(expResultado)){
            fail("Error en el test");
        }
    }

}

   