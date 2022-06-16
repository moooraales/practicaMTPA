package Servidor;

import Servidor.Excepcion.AutoRetoException;
import Servidor.Excepcion.GeneralError;
import Servidor.Excepcion.LoginException;
import Servidor.Excepcion.NotFoundException;
import Servidor.Excepcion.PendingRetoException;
import Servidor.Excepcion.RegistroException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente extends Thread{
    private static ArrayList<Cliente> listaClientesActivos = new ArrayList<>();

    private Socket cliente;
    private Usuario usuario;


    public Cliente(){
    }
    
    public Cliente(Socket socketCliente){
        this.cliente = socketCliente;
        this.usuario = null;
        this.start();
    }

//Getters y setters
public static ArrayList<Cliente> getListaClientesActivos() {
    return listaClientesActivos;
}

public static void setListaClientesActivos(ArrayList<Cliente> listaClientesActivos) {
    Cliente.listaClientesActivos = listaClientesActivos;
}

public Socket getCliente() {
    return cliente;
}

public void setCliente(Socket cliente) {
    this.cliente = cliente;
}

public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
}

//Permite que el usuario inicie sesion en el sistema
public ArrayList<String> opcionLogin(String[] solicitud){
    ArrayList<String> respuesta = new ArrayList<>();
    Usuario usuarioEncontrado = Usuario.login(solicitud[1], solicitud[2]);
    if(usuarioEncontrado!=null){
        this.setUsuario(usuarioEncontrado);
        respuesta.add("true");
    }else{
        respuesta.add("false");
        respuesta.add(new LoginException().toString());
    }
    return respuesta;
}

//Muestra los clientes que estan activos
public static ArrayList<Cliente> listarClientesLogueados(){
    ArrayList<Cliente> lista = new ArrayList<>();
    for(int i = 0 ; i < listaClientesActivos.size() ; i++){
        if(listaClientesActivos.get(i).getUsuario()!=null){
            lista.add(listaClientesActivos.get(i));
        }
    }
    return lista;
}

//Muestra clientes que han iniciado sesion en el sistema
public static ArrayList<Usuario> listarUsuariosLogueados(){
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Cliente> listaClientes = listarClientesLogueados();
    for(int i = 0 ; i < listaClientes.size() ; i++){
        listaUsuarios.add(listaClientes.get(i).getUsuario());
    }
    return listaUsuarios;
}

//Muestra los usuarios que han iniciado sesión, es decir, que están activos
//Sirve para ver a qué usuarios se les puede retar
public ArrayList<String> opcionListarUsuariosActivos(){
    ArrayList<String> respuesta = new ArrayList<>();
    ArrayList<Usuario> usuarios = listarUsuariosLogueados();
    if(usuarios.size()>0){
        respuesta.add("true");
        for(int i=0; i<usuarios.size();i++){
            if(!usuarios.get(i).equals(usuario)){
                respuesta.add(usuarios.get(i).getNombre());
            }                
        }
    }else{
        respuesta.add("false");
        respuesta.add(new NotFoundException().toString());
    }
    return respuesta;
}
    
}
