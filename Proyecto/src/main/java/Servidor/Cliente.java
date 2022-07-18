package Servidor;

import Servidor.inicioSesionException;
import Servidor.notFoundException;
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

public static Cliente buscarClienteLogueado(String nombreUsuario){
    ArrayList<Cliente> listaClientes = listarClientesLogueados();
    Cliente resultado = null;
    for(int i = 0 ; i < listaClientes.size() ; i++){
        if(listaClientes.get(i).getUsuario().getNombre().equals(nombreUsuario)){
            resultado = listaClientes.get(i);
        }
    }
    return resultado;
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
        respuesta.add(new inicioSesionException().toString());
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

private void enviar(String mensaje) throws IOException{
    byte[] mensajeCoded = mensaje.getBytes();
    cliente.getOutputStream().write(mensajeCoded);
}

public void enviar(ArrayList<String> mensaje) throws IOException{
    String mensajeConcat="";
    for (String parametro : mensaje) {
        mensajeConcat+=parametro+";";
    }
    enviar(mensajeConcat);
}

public String[] recibir() throws IOException{
    byte[] respuestaCoded = new byte[255];
    String respuesta;
    String [] datos;
    cliente.getInputStream().read(respuestaCoded);
    respuesta = new String(respuestaCoded);
    datos = respuesta.split(";");
    return datos;
}

//Elimina los retos de un usuario y lo quita de clientes activos
public void desconectar() throws IOException{
    if(usuario!=null){
        usuario.eliminarRetos();
    }
    this.getCliente().close();
    listaClientesActivos.remove(this);
}

public ArrayList<String> opcionRegistrar(String[] solicitud){
    ArrayList<String> respuesta = new ArrayList<>();
    if(Usuario.registrar(solicitud[1],solicitud[2])){
        respuesta.add("true");
    }else{
        respuesta.add("false");
        respuesta.add(new RegistroException().toString());
    }
    return respuesta;
}

@Override
    public void run(){
        try{
            String [] solicitud;
            ArrayList<String> respuesta = new ArrayList<>();
            while(true){
                solicitud = recibir();
                respuesta.clear();
                switch(solicitud[0]){
                    case "registrar":
                        respuesta=opcionRegistrar(solicitud);
                        enviar(respuesta);
                        break;
                    case "login":
                        respuesta=opcionLogin(solicitud);
                        enviar(respuesta);
                        break;
                    case "listarUsuariosActivos":
                        respuesta=opcionListarUsuariosActivos();
                        enviar(respuesta);
                        break;
                    case "retarJugador":
                        respuesta=opcionRetarJugador(solicitud);
                        enviar(respuesta);
                        break;
                    case "listarRetos":
                        respuesta=opcionListarRetos();
                        enviar(respuesta);
                        break;
                    case "responderReto":
                        respuesta=opcionResponderReto(solicitud);
                        enviar(respuesta);
                        break;
                    case "jugar":
                        respuesta=opcionJugar(solicitud);
                        enviar(respuesta);
                        break;
                    case "actualizarMarcador":
                        respuesta=opcionActualizarMarcador(solicitud);
                        enviar(respuesta);
                        break;
                    case "rondasRestantes":
                        respuesta=opcionRondasRestantes(solicitud);
                        enviar(respuesta);
                        break;
                    case "verRanking":
                        respuesta=opcionVerRanking();
                        enviar(respuesta);
                        break;
               }
            }
            
        } catch(IOException | InterruptedException ex){
            if(ex.getMessage().equals("Connection reset")){
                System.out.println("LOG: Conexion perdida con el cliente.");
            }else if(ex.getMessage().equals("Socket closed")){
                System.out.println("LOG: Conexion con el cliente finalizada.");
            }else{
                System.out.println("LOG: "+new GeneralError().toString()+ex.getMessage());
            }
        }
        try {
            this.desconectar();
        } catch (IOException ex) {
            System.out.println("LOG: "+new GeneralError().toString()+":"+ex.getMessage());
        }
        
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
        respuesta.add(new notFoundException().toString());
    }
    return respuesta;
}


    
}
